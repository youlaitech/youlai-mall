package com.youlai.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.constant.SystemConstants;
import com.youlai.common.enums.MenuTypeEnum;
import com.youlai.admin.mapper.SysMenuMapper;
import com.youlai.admin.pojo.entity.SysMenu;
import com.youlai.admin.pojo.entity.SysPermission;
import com.youlai.admin.pojo.vo.menu.ResourceVO;
import com.youlai.admin.pojo.vo.menu.RouteVO;
import com.youlai.admin.pojo.vo.menu.MenuVO;
import com.youlai.admin.service.SysMenuService;
import com.youlai.admin.service.SysPermissionService;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.web.domain.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 菜单业务实现类
 *
 * @author haoxr
 * @date 2020/11/06
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysPermissionService permissionService;

    /**
     * 菜单表格树形列表
     */
    @Override
    public List<MenuVO> listMenus(String name) {
        List<SysMenu> menus = this.list(new LambdaQueryWrapper<SysMenu>()
                .like(StrUtil.isNotBlank(name), SysMenu::getName, name)
                .orderByAsc(SysMenu::getSort)
        );

        Set<Long> cacheMenuIds = menus.stream().map(menu -> menu.getId()).collect(Collectors.toSet());

        List<MenuVO> tableMenus = menus.stream().map(menu -> {
            Long parentId = menu.getParentId();
            // parentId不在当前菜单ID的列表，说明为顶级菜单ID，根据此ID作为递归的开始条件节点
            if (!cacheMenuIds.contains(parentId)) {
                cacheMenuIds.add(parentId);
                return recurTableMenus(parentId, menus);
            }
            return new LinkedList<MenuVO>();
        }).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
        return tableMenus;
    }


    /**
     * 保存菜单
     */
    @Override
    public boolean saveMenu(SysMenu menu) {
        String path = menu.getPath();

        MenuTypeEnum menuType = menu.getType();  // 菜单类型
        switch (menuType) {
            case CATALOG: // 目录
                Assert.isTrue(path.startsWith("/"), "目录路由路径格式错误，必须以/开始");
                menu.setComponent("Layout");
                break;
            case EXTLINK: // 外链
                menu.setComponent(null);
                break;
        }

        boolean result = this.saveOrUpdate(menu);
        if (result == true) {
            permissionService.refreshPermRolesRules();
        }
        return result;
    }

    /**
     * 菜单下拉数据
     */
    @Override
    public List<Option> listMenuOptions() {
        List<SysMenu> menuList = this.list(new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getSort));
        List<Option> menus = recurMenus(SystemConstants.ROOT_MENU_ID, menuList);
        return menus;
    }

    /**
     * 路由列表
     */
    @Override
    @Cacheable(cacheNames = "system", key = "'routes'")
    public List<RouteVO> listRoutes() {
        List<SysMenu> menuList = this.baseMapper.listRoutes();
        List<RouteVO> list = recurRoutes(SystemConstants.ROOT_MENU_ID, menuList);
        return list;
    }

    /**
     * 递归生成菜单路由层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private List<RouteVO> recurRoutes(Long parentId, List<SysMenu> menuList) {
        List<RouteVO> list = new ArrayList<>();
        Optional.ofNullable(menuList).ifPresent(menus -> menus.stream().filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    RouteVO routeVO = new RouteVO();

                    MenuTypeEnum menuTypeEnum = menu.getType();

                    if (MenuTypeEnum.MENU.equals(menuTypeEnum)) {
                        routeVO.setName(menu.getPath()); //  根据name路由跳转 this.$router.push({name:xxx})
                    }
                    routeVO.setPath(menu.getPath()); // 根据path路由跳转 this.$router.push({path:xxx})
                    routeVO.setRedirect(menu.getRedirect());
                    routeVO.setComponent(menu.getComponent());

                    RouteVO.Meta meta = new RouteVO.Meta();
                    meta.setTitle(menu.getName());
                    meta.setIcon(menu.getIcon());
                    meta.setRoles(menu.getRoles());
                    meta.setHidden(!GlobalConstants.STATUS_YES.equals(menu.getVisible()));
                    meta.setKeepAlive(true);


                    routeVO.setMeta(meta);
                    List<RouteVO> children = recurRoutes(menu.getId(), menuList);
                    // 含有子节点的目录设置为可见
                    boolean alwaysShow = CollectionUtil.isNotEmpty(children) && children.stream().anyMatch(item -> item.getMeta().getHidden().equals(false));
                    meta.setAlwaysShow(alwaysShow);
                    routeVO.setChildren(children);

                    list.add(routeVO);
                }));
        return list;
    }


    /**
     * 获取菜单资源树形列表
     *
     * @return
     */
    @Override
    public ResourceVO getResource() {
        List<SysMenu> menuList = this.list(new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getSort));
        List<SysPermission> permList = permissionService.list();

        ResourceVO resource = new ResourceVO();
        List<ResourceVO.MenuOption> menus = recurResources(SystemConstants.ROOT_MENU_ID, menuList, permList);
        resource.setMenus(menus);

        List<ResourceVO.PermOption> perms = Optional.ofNullable(permList).orElse(new ArrayList<>()).stream()
                .map(perm -> new ResourceVO.PermOption(perm.getMenuId(), perm.getId(), perm.getName()))
                .collect(Collectors.toList());
        resource.setPerms(perms);

        return resource;
    }

    /**
     * 修改菜单显示状态
     *
     * @param menuId  菜单ID
     * @param visible 是否显示(1->显示；2->隐藏)
     * @return
     */
    @Override
    public boolean updateMenuVisible(Long menuId, Integer visible) {
        boolean result = this.update(new LambdaUpdateWrapper<SysMenu>()
                .eq(SysMenu::getId, menuId)
                .set(SysMenu::getVisible, visible)
        );
        if (result) {
            permissionService.refreshPermRolesRules();
        }
        return result;
    }

    /**
     * 递归生成菜单表格层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private static List<MenuVO> recurTableMenus(Long parentId, List<SysMenu> menuList) {
        List<MenuVO> tableMenus = Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> {
                    MenuVO menuVO = new MenuVO();
                    BeanUtil.copyProperties(menu, menuVO);
                    List<MenuVO> children = recurTableMenus(menu.getId(), menuList);
                    menuVO.setChildren(children);
                    return menuVO;
                }).collect(Collectors.toList());
        return tableMenus;
    }

    /**
     * 递归生成菜单下拉层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private static List<Option> recurMenus(Long parentId, List<SysMenu> menuList) {
        List<Option> menus = Optional.ofNullable(menuList).orElse(new ArrayList<>()).stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> new Option(menu.getId(), menu.getName(), recurMenus(menu.getId(), menuList)))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return menus;
    }

    /**
     * 递归生成菜单下拉层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private static List<ResourceVO.MenuOption> recurResources(Long parentId, List<SysMenu> menuList, List<SysPermission> permList) {
        List<ResourceVO.MenuOption> menus = Optional.ofNullable(menuList).orElse(new ArrayList<>()).stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> {
                    Long menuId = menu.getId();

                    ResourceVO.MenuOption menuOption = new ResourceVO.MenuOption();
                    menuOption.setValue(menu.getId());
                    menuOption.setLabel(menu.getName());
                    List<ResourceVO.MenuOption> children = recurResources(menu.getId(), menuList, permList);

                    long count = permList.stream().filter(perm -> perm.getMenuId().equals(menuId)).count();

                    // 如果该菜单下有权限，添加一个节点存放权限数据
                    if (count > 0) {
                        ResourceVO.MenuOption permOption = new ResourceVO.MenuOption();
                        permOption.setIsPerm(true);
                        permOption.setValue(-1l);
                        permOption.setPermPid(menuId);
                        children.add(permOption);
                    }
                    menuOption.setChildren(children);

                    return menuOption;
                }).collect(Collectors.toList());
        return menus;
    }


    /**
     * 清理路由缓存
     */
    @Override
    @CacheEvict(cacheNames = "system", key = "'routes'")
    public void cleanCache() {
    }

}
