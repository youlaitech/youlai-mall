package com.youlai.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.pojo.entity.SysMenu;
import com.youlai.admin.pojo.vo.MenuVO;
import com.youlai.admin.pojo.vo.RouteVO;
import com.youlai.admin.pojo.vo.SelectVO;
import com.youlai.admin.pojo.vo.TreeSelectVO;

import java.util.List;

/**
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 * @date 2020-11-06
 */
public interface ISysMenuService extends IService<SysMenu> {


    /**
     * 菜单表格（Table）层级列表
     *
     * @param name 菜单名称
     * @return
     */
    List<MenuVO> listTable(String name);


    /**
     * 菜单下拉（Select）层级列表
     *
     * @return
     */
    List<SelectVO> listSelect();


    /**
     * 菜单路由（Route）层级列表
     *
     * @return
     */
    List<RouteVO> listRoute();

    /**
     * 菜单下拉(TreeSelect)层级列表
     *
     * @return
     */
    List<TreeSelectVO> listTreeSelect();
}
