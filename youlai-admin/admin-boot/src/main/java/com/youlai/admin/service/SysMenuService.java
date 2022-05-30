package com.youlai.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.pojo.entity.SysMenu;
import com.youlai.admin.pojo.vo.menu.TableMenuVO;
import com.youlai.admin.pojo.vo.menu.RouteVO;
import com.youlai.common.web.vo.OptionVO;

import java.util.List;

/**
 * @author haoxr
 * @date 2020/11/06
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 菜单表格(Table)层级列表
     *
     * @param name 菜单名称
     * @return
     */
    List<TableMenuVO> listTableMenus(String name);


    /**
     * 菜单下拉(Select)层级列表
     *
     * @return
     */
    List<OptionVO> listMenus();

    /**
     * 新增菜单
     *
     * @param menu
     * @return
     */
    boolean saveMenu(SysMenu menu);

    /**
     * 清理路由缓存
     */
    void cleanCache();

    /**
     * 获取路由列表(适配Vue3的vue-next-router)
     *
     * @return
     */
    List<RouteVO> listNextRoutes();

    /**
     * 
     * @param name
     * @return
     */
    List<OptionVO> listMenuPerms(String name);
}
