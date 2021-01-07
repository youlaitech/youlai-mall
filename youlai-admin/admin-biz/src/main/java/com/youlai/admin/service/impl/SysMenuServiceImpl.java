package com.youlai.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.common.AdminConstant;
import com.youlai.admin.pojo.SysMenu;
import com.youlai.admin.pojo.vo.MenuVO;
import com.youlai.admin.pojo.vo.RouterVO;
import com.youlai.admin.pojo.vo.TreeSelectVO;
import com.youlai.admin.mapper.SysMenuMapper;
import com.youlai.admin.service.ISysMenuService;
import com.youlai.common.core.constant.SystemConstants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author haoxr
 * @date 2020-11-06
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public List<MenuVO> listForTree(LambdaQueryWrapper<SysMenu> baseQuery) {
        List<SysMenu> menuList = this.baseMapper.selectList(baseQuery);
        List<MenuVO> list = recursionForTree(AdminConstant.ROOT_MENU_ID, menuList);
        return list;
    }

    @Override
    public List<TreeSelectVO> listForTreeSelect(LambdaQueryWrapper<SysMenu> baseQuery) {
        List<SysMenu> menuList = this.list(baseQuery);
        List<TreeSelectVO> list = recursionForTreeSelect(AdminConstant.ROOT_MENU_ID, menuList);
        return list;
    }

    @Override
    public List listForRouter() {
        List<SysMenu> menuList = this.baseMapper.listForRouter();
        List<RouterVO> list = recursionForRoutes(AdminConstant.ROOT_MENU_ID, menuList);
        return list;
    }

    // 递归生成路由
    private List<RouterVO> recursionForRoutes(Long parentId, List<SysMenu> menuList) {
        List<RouterVO> list = new ArrayList<>();
        Optional.ofNullable(menuList).ifPresent(menus -> menus.stream().filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    RouterVO routerVO = new RouterVO();
                    routerVO.setName(menu.getName());
                    if ( AdminConstant.ROOT_MENU_ID.equals(parentId)) {
                        routerVO.setAlwaysShow(Boolean.TRUE);
                        routerVO.setPath("/" + menu.getPath());
                    } else {
                        routerVO.setPath(menu.getPath());
                    }
                    routerVO.setRedirect(menu.getRedirect());
                    routerVO.setComponent(
                            StrUtil.isNotBlank(menu.getComponent()) ?
                                    menu.getComponent() :
                                    "Layout");

                    routerVO.setMeta(routerVO.new Meta(
                            menu.getTitle(),
                            menu.getIcon(),
                            menu.getRoles()
                    ));
                    // 菜单显示隐藏
                    routerVO.setHidden(!SystemConstants.VISIBLE_SHOW_VALUE.equals(menu.getVisible()) ? true : false);
                    List<RouterVO> children = recursionForRoutes(menu.getId(), menuList);
                    routerVO.setChildren(children);
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
                    menuVO.setChildren(children);
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
    public static List<TreeSelectVO> recursionForTreeSelect(Long parentId, List<SysMenu> menuList) {
        List<TreeSelectVO> list = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .forEach(menu -> {
                    TreeSelectVO treeSelectVO = new TreeSelectVO();
                    treeSelectVO.setId(menu.getId());
                    treeSelectVO.setLabel(menu.getTitle());
                    List<TreeSelectVO> children = recursionForTreeSelect(menu.getId(), menuList);
                    treeSelectVO.setChildren(children);
                    list.add(treeSelectVO);
                });
        return list;
    }

}
