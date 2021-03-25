package com.youlai.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.common.constant.AdminConstants;
import com.youlai.admin.pojo.entity.SysMenu;
import com.youlai.admin.pojo.vo.MenuVO;
import com.youlai.admin.pojo.vo.RouterVO;
import com.youlai.admin.mapper.SysMenuMapper;
import com.youlai.admin.pojo.vo.TreeVO;
import com.youlai.admin.service.ISysMenuService;
import com.youlai.common.constant.GlobalConstants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @Author haoxr
 * @Date 2020-11-06
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public List<MenuVO> listMenuVO(LambdaQueryWrapper<SysMenu> baseQuery) {
        List<SysMenu> menuList = this.baseMapper.selectList(baseQuery);
        List<MenuVO> list = recursionForTree(AdminConstants.ROOT_MENU_ID, menuList);
        return list;
    }

    @Override
    public List<TreeVO> listTreeVO(LambdaQueryWrapper<SysMenu> baseQuery) {
        List<SysMenu> menuList = this.list(baseQuery);
        List<TreeVO> list = recursionForTreeSelect(AdminConstants.ROOT_MENU_ID, menuList);
        return list;
    }

    @Override
    public List<RouterVO> listRouterVO() {
        List<SysMenu> menuList = this.baseMapper.listForRouter();
        List<RouterVO> list = recursionForRoutes(AdminConstants.ROOT_MENU_ID, menuList);
        return list;
    }

    // 递归生成路由
    private List<RouterVO> recursionForRoutes(Long parentId, List<SysMenu> menuList) {
        List<RouterVO> list = new ArrayList<>();
        Optional.ofNullable(menuList).ifPresent(menus -> menus.stream().filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    RouterVO routerVO = new RouterVO();
                    routerVO.setName(menu.getName());
                    if (AdminConstants.ROOT_MENU_ID.equals(parentId)) {
                        routerVO.setPath(menu.getPath());
                        routerVO.setComponent("Layout");
                    } else {
                        routerVO.setPath(menu.getPath());// 显示在地址栏上
                        routerVO.setComponent(menu.getComponent());
                    }
                    routerVO.setRedirect(menu.getRedirect());
                    routerVO.setMeta(routerVO.new Meta(
                            menu.getName(),
                            menu.getIcon(),
                            menu.getRoles()
                    ));
                    // 菜单显示隐藏
                    routerVO.setHidden(!GlobalConstants.VISIBLE_SHOW_VALUE.equals(menu.getVisible()) ? true : false);
                    List<RouterVO> children = recursionForRoutes(menu.getId(), menuList);
                    routerVO.setChildren(children);
                    if(CollectionUtil.isNotEmpty(children)){
                        routerVO.setAlwaysShow(Boolean.TRUE); // 显示子节点
                    }
                    list.add(routerVO);
                }));
        return list;
    }

    /**
     * 递归生成部门表格数据
     *
     * @param parentId
     * @param menuList
     * @return
     */
    public static List<MenuVO> recursionForTree(Long parentId, List<SysMenu> menuList) {
        List<MenuVO> list = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    MenuVO menuVO = new MenuVO();
                    BeanUtil.copyProperties(menu, menuVO);
                    List<MenuVO> children = recursionForTree(menu.getId(), menuList);
                    if (CollectionUtil.isNotEmpty(children)) {
                        menuVO.setChildren(children);
                    }
                    list.add(menuVO);
                });
        return list;
    }


    /**
     * 递归生成部门树形下拉数据
     *
     * @param parentId
     * @param menuList
     * @return
     */
    public static List<TreeVO> recursionForTreeSelect(Long parentId, List<SysMenu> menuList) {
        List<TreeVO> list = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    TreeVO treeVO = new TreeVO();
                    treeVO.setId(menu.getId());
                    treeVO.setLabel(menu.getName());
                    List<TreeVO> children = recursionForTreeSelect(menu.getId(), menuList);
                    if (CollectionUtil.isNotEmpty(children)) {
                        treeVO.setChildren(children);
                    }
                    list.add(treeVO);
                });
        return list;
    }

}
