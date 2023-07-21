package com.youlai.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.constant.SystemConstants;
import com.youlai.common.enums.MenuTypeEnum;
import com.youlai.common.enums.StatusEnum;
import com.youlai.system.converter.MenuConverter;
import com.youlai.system.mapper.SysMenuMapper;
import com.youlai.system.model.bo.RouteBO;
import com.youlai.system.model.entity.SysMenu;
import com.youlai.system.model.form.MenuForm;
import com.youlai.system.model.query.MenuQuery;
import com.youlai.system.model.vo.MenuVO;
import com.youlai.common.web.model.Option;
import com.youlai.system.model.vo.RouteVO;
import com.youlai.system.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单业务实现类
 *
 * @author haoxr
 * @since 2020/11/06
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final MenuConverter menuConverter;

    /**
     * 菜单列表
     *
     * @param queryParams {@link MenuQuery}
     */
    @Override
    public List<MenuVO> listMenus(MenuQuery queryParams) {
        List<SysMenu> menus = this.list(new LambdaQueryWrapper<SysMenu>()
                .like(StrUtil.isNotBlank(queryParams.getKeywords()), SysMenu::getName, queryParams.getKeywords())
                .orderByAsc(SysMenu::getSort)
        );

        Set<Long> parentIds = menus.stream()
                .map(SysMenu::getParentId)
                .collect(Collectors.toSet());

        Set<Long> menuIds = menus.stream()
                .map(SysMenu::getId)
                .collect(Collectors.toSet());

        List<Long> rootIds = CollectionUtil.subtractToList(parentIds, menuIds); // 求差集，得到 parentIds 中 menuIds 没有的元素

        List<MenuVO> list = new ArrayList<>();
        for (Long rootId : rootIds) {
            list.addAll(recurMenus(rootId, menus)); // 递归
        }
        return list;
    }

    /**
     * 保存菜单
     */
    @Override
    public boolean saveMenu(MenuForm menuForm) {
        String path = menuForm.getPath();

        MenuTypeEnum menuType = menuForm.getType();  // 菜单类型
        switch (menuType) {
            case CATALOG -> { // 目录
                if (NumberUtil.equals(menuForm.getParentId(), 0) && !path.startsWith("/")) {
                    menuForm.setPath("/" + path); // 一级目录需以 / 开头
                }
                menuForm.setComponent("Layout");
            }
            case EXTLINK -> // 外链
                    menuForm.setComponent(null);
        }
        SysMenu entity = menuConverter.form2Entity(menuForm);

        String treePath = generateMenuTreePath(menuForm.getParentId());
        entity.setTreePath(treePath);
        return this.saveOrUpdate(entity);
    }

    /**
     * 菜单下拉数据
     */
    @Override
    public List<Option> listMenuOptions() {
        List<SysMenu> menuList = this.list(new LambdaQueryWrapper<SysMenu>().orderByAsc(SysMenu::getSort));
        return recurMenuOptions(SystemConstants.ROOT_NODE_ID, menuList);
    }

    /**
     * 路由列表
     */
    @Override
    //@Cacheable(cacheNames = "system", key = "'routes'")
    public List<RouteVO> listRoutes() {
        List<RouteBO> menuList = this.baseMapper.listRoutes();
        return recurRoutes(SystemConstants.ROOT_NODE_ID, menuList);
    }

    /**
     * 递归生成菜单路由层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private List<RouteVO> recurRoutes(Long parentId, List<RouteBO> menuList) {
        return CollectionUtil.emptyIfNull(menuList).stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> {
                    RouteVO routeVO = new RouteVO();
                    MenuTypeEnum menuTypeEnum = menu.getType();
                    if (MenuTypeEnum.MENU.equals(menuTypeEnum)) {
                        String routeName = StringUtils.capitalize(StrUtil.toCamelCase(menu.getPath(), '-')); // 路由 name 需要驼峰，首字母大写
                        routeVO.setName(routeName); //  根据name路由跳转 this.$router.push({name:xxx})
                    }
                    routeVO.setPath(menu.getPath()); // 根据path路由跳转 this.$router.push({path:xxx})
                    routeVO.setRedirect(menu.getRedirectUrl());
                    routeVO.setComponent(menu.getComponent());

                    RouteVO.Meta meta = new RouteVO.Meta();
                    meta.setTitle(menu.getName());
                    meta.setIcon(menu.getIcon());
                    meta.setRoles(menu.getRoles());
                    meta.setHidden(StatusEnum.DISABLE.getValue().equals(menu.getVisible()));
                    meta.setKeepAlive(true);
                    routeVO.setMeta(meta);

                    List<RouteVO> children = recurRoutes(menu.getId(), menuList);
                    routeVO.setChildren(children);
                    return routeVO;
                }).toList();
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
        return this.update(new LambdaUpdateWrapper<SysMenu>()
                .eq(SysMenu::getId, menuId)
                .set(SysMenu::getVisible, visible)
        );
    }

    /**
     * 获取角色权限集合
     *
     * @param roles
     * @return
     */
    @Override
    public Set<String> listRolePerms(Set<String> roles) {
        Set<String> perms = this.baseMapper.listRolePerms(roles);
        return perms;
    }

    /**
     * 获取菜单表单数据
     *
     * @param id 菜单ID
     * @return
     */
    @Override
    public MenuForm getMenuForm(Long id) {
        SysMenu entity = this.getById(id);
        MenuForm menuForm = menuConverter.entity2Form(entity);
        return menuForm;
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return
     */
    @Override
    public boolean deleteMenu(Long id) {
        if (id != null) {
            this.remove(new LambdaQueryWrapper<SysMenu>()
                    .eq(SysMenu::getId, id)
                    .or()
                    .apply("CONCAT (',',tree_path,',') LIKE CONCAT('%,',{0},',%')", id));
        }
        return true;
    }

    /**
     * 递归生成菜单列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private List<MenuVO> recurMenus(Long parentId, List<SysMenu> menuList) {
        return CollectionUtil.emptyIfNull(menuList)
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(entity -> {
                    MenuVO menuVO = menuConverter.entity2Vo(entity);
                    List<MenuVO> children = recurMenus(entity.getId(), menuList);
                    menuVO.setChildren(children);
                    return menuVO;
                }).toList();
    }

    /**
     * 递归生成菜单下拉层级列表
     *
     * @param parentId 父级ID
     * @param menuList 菜单列表
     * @return
     */
    private static List<Option> recurMenuOptions(Long parentId, List<SysMenu> menuList) {
        List<Option> menus = CollectionUtil.emptyIfNull(menuList).stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> new Option(menu.getId(), menu.getName(), recurMenuOptions(menu.getId(), menuList)))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return menus;
    }


    /**
     * 部门路径生成
     *
     * @param parentId 父ID
     * @return 父节点路径以英文逗号(, )分割，eg: 1,2,3
     */
    private String generateMenuTreePath(Long parentId) {
        String treePath = null;
        if (SystemConstants.ROOT_NODE_ID.equals(parentId)) {
            treePath = String.valueOf(parentId);
        } else {
            SysMenu parent = this.getById(parentId);
            if (parent != null) {
                treePath = parent.getTreePath() + "," + parent.getId();
            }
        }
        return treePath;
    }


}
