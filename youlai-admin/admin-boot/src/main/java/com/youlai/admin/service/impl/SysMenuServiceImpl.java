package com.youlai.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.common.constant.SystemConstants;
import com.youlai.admin.common.enums.MenuTypeEnum;
import com.youlai.admin.mapper.SysMenuMapper;
import com.youlai.admin.pojo.entity.SysMenu;
import com.youlai.admin.pojo.entity.SysPermission;
import com.youlai.admin.pojo.vo.menu.TableMenuVO;
import com.youlai.admin.pojo.vo.menu.RouteVO;
import com.youlai.admin.service.SysMenuService;
import com.youlai.admin.service.SysPermissionService;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.web.vo.OptionVO;
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
    public List<TableMenuVO> listTableMenus(String name) {
        List<SysMenu> menus = this.list(new LambdaQueryWrapper<SysMenu>()
                .like(StrUtil.isNotBlank(name), SysMenu::getName, name)
                .orderByAsc(SysMenu::getSort)
        );

        Set<Long> cacheMenuIds = menus.stream().map(menu -> menu.getId()).collect(Collectors.toSet());

        List<TableMenuVO> tableMenus = menus.stream().map(menu -> {
            Long parentId = menu.getParentId();
            // parentId不在当前菜单ID的列表，说明为顶级菜单ID，根据此ID作为递归的开始条件节点
            if (!cacheMenuIds.contains(parentId)) {
                cacheMenuIds.add(parentId);
                return recurTableMenus(parentId, menus);
            }
            return new LinkedList<TableMenuVO>();
        }).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
        return tableMenus;
    }


    /**
     * 保存菜单路由
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
    public List<OptionVO> listMenus() {
        List<SysMenu> menuList = this.list(new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getSort));
        List<OptionVO> menus = recursionMenus(SystemConstants.ROOT_MENU_ID, menuList);
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
                    routeVO.setRedirect(menu.getRedirect());

                    RouteVO.Meta meta = new RouteVO.Meta();
                    meta.setTitle(menu.getName());
                    meta.setIcon(menu.getIcon());
                    meta.setRoles(menu.getRoles());
                    meta.setHidden(!GlobalConstants.STATUS_YES.equals(menu.getVisible()));
                    meta.setKeepAlive(true);
                    meta.setAlwaysShow(true);

                    routeVO.setMeta(meta);
                    List<RouteVO> children = recurRoutes(menu.getId(), menuList);
                    routeVO.setChildren(children);

                    list.add(routeVO);
                }));
        return list;
    }


    /**
     * 获取菜单权限树形列表
     *
     * @param name
     * @return
     */
    @Override
    public List<OptionVO> listMenuPerms(String name) {
        List<SysMenu> menuList = this.list(new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getSort));
        List<SysPermission> permList = permissionService.list();
        List<OptionVO> menus = recursionMenuPerms(SystemConstants.ROOT_MENU_ID, menuList, permList);
        return menus;
    }

    /**
     * 递归生成菜单表格层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private static List<TableMenuVO> recurTableMenus(Long parentId, List<SysMenu> menuList) {
        List<TableMenuVO> tableMenus = Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> {
                    TableMenuVO tableMenuVO = new TableMenuVO();
                    BeanUtil.copyProperties(menu, tableMenuVO);
                    List<TableMenuVO> children = recurTableMenus(menu.getId(), menuList);
                    tableMenuVO.setChildren(children);
                    return tableMenuVO;
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
    private static List<OptionVO> recursionMenus(Long parentId, List<SysMenu> menuList) {
        List<OptionVO> menus = Optional.ofNullable(menuList).orElse(new ArrayList<>()).stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> new OptionVO(menu.getId(), menu.getName(), recursionMenus(menu.getId(), menuList)))
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
    private static List<OptionVO> recursionMenuPerms(Long parentId, List<SysMenu> menuList, List<SysPermission> permList) {
        List<OptionVO> menus = Optional.ofNullable(menuList).orElse(new ArrayList<>()).stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> {

                    OptionVO option = new OptionVO("m_" + menu.getId(), menu.getName());
                    List<OptionVO> children = recursionMenuPerms(menu.getId(), menuList, permList);
                    List<OptionVO> permChildren = permList.stream().filter(perm -> perm.getMenuId().equals(menu.getId()))
                            .map(perm -> new OptionVO("p_" + perm.getId(), perm.getName()))
                            .collect(Collectors.toList());
                    children.addAll(permChildren);
                    option.setChildren(children);
                    return option;
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
