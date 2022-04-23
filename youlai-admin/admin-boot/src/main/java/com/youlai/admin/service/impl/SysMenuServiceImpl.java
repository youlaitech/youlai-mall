package com.youlai.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.common.constant.SystemConstants;
import com.youlai.admin.common.enums.MenuTypeEnum;
import com.youlai.admin.mapper.SysMenuMapper;
import com.youlai.admin.pojo.entity.SysMenu;
import com.youlai.admin.pojo.vo.menu.MenuTableVO;
import com.youlai.admin.pojo.vo.menu.NextRouteVO;
import com.youlai.admin.pojo.vo.menu.RouteVO;
import com.youlai.admin.service.ISysMenuService;
import com.youlai.admin.service.ISysPermissionService;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.web.vo.OptionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 菜单业务类
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2020-11-06
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    private final ISysPermissionService permissionService;

    /**
     * 菜单表格（Table）层级列表
     *
     * @param name 菜单名称
     * @return
     */
    @Override
    public List<MenuTableVO> listTableMenus(String name) {
        List<SysMenu> menuList = this.list(
                new LambdaQueryWrapper<SysMenu>()
                        .like(StrUtil.isNotBlank(name), SysMenu::getName, name)
                        .orderByAsc(SysMenu::getSort)
        );
        return recursion(menuList);
    }

    /**
     * 递归生成菜单表格层级列表
     *
     * @param menuList 菜单列表
     * @return 菜单列表
     */
    private static List<MenuTableVO> recursion(List<SysMenu> menuList) {
        List<MenuTableVO> menuTableList = new ArrayList<>();
        // 保存所有节点的 id
        Set<Long> nodeIdSet = menuList.stream()
                .map(SysMenu::getId)
                .collect(Collectors.toSet());
        for (SysMenu sysMenu : menuList) {
            // 不在节点 id 集合中存在的 id 即为顶级节点 id, 递归生成列表
            Long parentId = sysMenu.getParentId();
            if (!nodeIdSet.contains(parentId)) {
                menuTableList.addAll(recursionTableList(parentId, menuList));
                nodeIdSet.add(parentId);
            }
        }
        // 如果结果列表为空说明所有的节点都是独立分散的, 直接转换后返回
        if (menuTableList.isEmpty()) {
            return menuList.stream()
                    .map(item -> {
                        MenuTableVO menuTableVO = new MenuTableVO();
                        BeanUtil.copyProperties(item, menuTableVO);
                        return menuTableVO;
                    })
                    .collect(Collectors.toList());
        }
        return menuTableList;
    }

    /**
     * 递归生成菜单表格层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private static List<MenuTableVO> recursionTableList(Long parentId, List<SysMenu> menuList) {
        List<MenuTableVO> menuTableList = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    MenuTableVO menuTableVO = new MenuTableVO();
                    BeanUtil.copyProperties(menu, menuTableVO);
                    List<MenuTableVO> children = recursionTableList(menu.getId(), menuList);

                    if (CollectionUtil.isNotEmpty(children)) {
                        menuTableVO.setChildren(children);
                    }
                    menuTableList.add(menuTableVO);
                });
        return menuTableList;
    }


    /**
     * 菜单下拉（Select）层级列表
     *
     * @return
     */
    @Override
    public List<OptionVO> listSelectMenus() {
        List<SysMenu> menuList = this.list(new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getSort));
        List<OptionVO> menuSelectList = recursionSelectList(SystemConstants.ROOT_MENU_ID, menuList);
        return menuSelectList;
    }


    /**
     * 递归生成菜单下拉层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private static List<OptionVO> recursionSelectList(Long parentId, List<SysMenu> menuList) {
        List<OptionVO> menuSelectList = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    OptionVO optionVO = new OptionVO(menu.getId(), menu.getName());
                    List<OptionVO> children = recursionSelectList(menu.getId(), menuList);
                    if (CollectionUtil.isNotEmpty(children)) {
                        optionVO.setChildren(children);
                    }
                    menuSelectList.add(optionVO);
                });
        return menuSelectList;
    }


    /**
     * 新增菜单
     *
     * @param menu
     * @return
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

        boolean result = this.save(menu);
        if (result == true) {
            permissionService.refreshPermRolesRules();
        }
        return result;
    }

    /**
     * 修改菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean updateMenu(SysMenu menu) {
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

        boolean result = this.updateById(menu);
        if (result == true) {
            permissionService.refreshPermRolesRules();
        }
        return result;
    }


    /**
     * 清理路由缓存
     */
    @Override
    @CacheEvict(cacheNames = "system", key = "'routes'")
    public void cleanCache() {
    }

    /**
     * 获取路由列表(适配Vue3的vue-next-router)
     *
     * @return
     */
    @Override
    @Cacheable(cacheNames = "system", key = "'routes'")
    public List<NextRouteVO> listNextRoutes() {
        List<SysMenu> menuList = this.baseMapper.listRoutes();
        List<NextRouteVO> list = recursionNextRoute(SystemConstants.ROOT_MENU_ID, menuList);
        return list;
    }


    /**
     * 递归生成菜单路由层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private List<NextRouteVO> recursionNextRoute(Long parentId, List<SysMenu> menuList) {
        List<NextRouteVO> list = new ArrayList<>();
        Optional.ofNullable(menuList).ifPresent(menus -> menus.stream().filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    NextRouteVO nextRouteVO = new NextRouteVO();

                    MenuTypeEnum menuTypeEnum = menu.getType();

                    if (MenuTypeEnum.MENU.equals(menuTypeEnum)) {
                        nextRouteVO.setName(menu.getPath()); //  根据name路由跳转 this.$router.push({path:xxx})
                    }
                    nextRouteVO.setPath(menu.getPath()); // 根据path路由跳转 this.$router.push({name:xxx})
                    nextRouteVO.setRedirect(menu.getRedirect());
                    nextRouteVO.setComponent(menu.getComponent());
                    nextRouteVO.setRedirect(menu.getRedirect());

                    NextRouteVO.Meta meta = new NextRouteVO.Meta();
                    meta.setTitle(menu.getName());
                    meta.setIcon(menu.getIcon());
                    meta.setRoles(menu.getRoles());
                    meta.setHidden(!GlobalConstants.STATUS_YES.equals(menu.getVisible()));
                    meta.setKeepAlive(true);

                    nextRouteVO.setMeta(meta);
                    List<NextRouteVO> children = recursionNextRoute(menu.getId(), menuList);
                    nextRouteVO.setChildren(children);
                    list.add(nextRouteVO);
                }));
        return list;
    }
}
