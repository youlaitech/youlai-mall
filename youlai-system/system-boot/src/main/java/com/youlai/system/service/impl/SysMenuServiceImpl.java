package com.youlai.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.system.converter.MenuConverter;
import com.youlai.common.constant.SystemConstants;
import com.youlai.common.enums.MenuTypeEnum;
import com.youlai.system.mapper.SysMenuMapper;
import com.youlai.system.pojo.entity.SysMenu;
import com.youlai.system.pojo.entity.SysPermission;
import com.youlai.system.pojo.vo.menu.ResourceVO;
import com.youlai.system.pojo.vo.menu.RouteVO;
import com.youlai.system.pojo.vo.menu.MenuVO;
import com.youlai.system.service.SysMenuService;
import com.youlai.system.service.SysPermissionService;
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
    private final MenuConverter menuConverter;

    /**
     * 菜单表格树形列表
     */
    @Override
    public List<MenuVO> listMenus(String name) {
        List<SysMenu> menuList = this.list(new LambdaQueryWrapper<SysMenu>()
                .like(StrUtil.isNotBlank(name), SysMenu::getName, name)
                .orderByAsc(SysMenu::getSort)
        );

        Set<Long> menuIds = menuList.stream().map(menu -> menu.getId()).collect(Collectors.toSet());

        List<MenuVO> menus = menuList
                .stream()
                .map(menu -> {
                    Long parentId = menu.getParentId();
                    // parentId 如果不在菜单id集合中，即为根节点，作为递归的根节点
                    if (!menuIds.contains(parentId)) {
                        menuIds.add(parentId); // 避免根节点重复递归
                        return recurMenuList(parentId, menuList);
                    }
                    return Collections.EMPTY_LIST;
                })
                .collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
        return menus;
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
            case BUTTON:
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
        List<SysMenu> menuList = this.list(new LambdaQueryWrapper<SysMenu>()
                .select(SysMenu::getId, SysMenu::getName,SysMenu::getParentId)
                .in(SysMenu::getType, Arrays.asList("C", "M", "E"))
                .orderByAsc(SysMenu::getSort)
        );
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
    public List<ResourceVO> listResources() {
        List<SysMenu> menuList = this.list(new LambdaQueryWrapper<SysMenu>()
                .orderByAsc(SysMenu::getSort));

        List<SysPermission> permList = permissionService.list();

        List<ResourceVO> resources = recurResources(SystemConstants.ROOT_MENU_ID, menuList, permList);

        return resources;
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
    private List<MenuVO> recurMenuList(Long parentId, List<SysMenu> menuList) {
        List<MenuVO> tableMenus = Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(entity -> {
                    MenuVO menuVO = menuConverter.entity2VO(entity);
                    List<MenuVO> children = recurMenuList(entity.getId(), menuList);
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
        List<Option> menus = null;
        if (CollectionUtil.isNotEmpty(menuList)) {
            menus = menuList.stream()
                    .filter(menu -> menu.getParentId().equals(parentId))
                    .map(menu -> new Option(menu.getId(), menu.getName(), recurMenus(menu.getId(), menuList)))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        }
        return menus;
    }

    /**
     * 递归生成资源（菜单+权限）树形列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private static List<ResourceVO> recurResources(Long parentId, List<SysMenu> menuList, List<SysPermission> permList) {
        List<ResourceVO> menus = Optional.ofNullable(menuList).orElse(new ArrayList<>()).stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> {
                    Long menuId = menu.getId();

                    ResourceVO resourceVO = new ResourceVO();
                    resourceVO.setValue(menu.getId());
                    resourceVO.setLabel(menu.getName());

                    List<ResourceVO> children = recurResources(menu.getId(), menuList, permList);
                    resourceVO.setChildren(children);

                    List<Option> perms = permList.stream().filter(perm -> perm.getMenuId()
                                    .equals(menuId))
                            .map(item -> new Option<>(item.getId(), item.getName()))
                            .collect(Collectors.toList());
                    resourceVO.setPerms(perms);
                    return resourceVO;
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
