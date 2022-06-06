package com.youlai.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.pojo.entity.SysMenu;
import com.youlai.admin.pojo.vo.menu.ResourceVO;
import com.youlai.admin.pojo.vo.menu.TableMenuVO;
import com.youlai.admin.pojo.vo.menu.RouteVO;
import com.youlai.common.web.domain.Option;

import java.util.List;

/**
 * @author haoxr
 * @date 2020/11/06
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取菜单表格列表
     *
     * @param name 菜单名称
     * @return
     */
    List<TableMenuVO> listTableMenus(String name);


    /**
     * 获取菜单下拉列表
     *
     * @return
     */
    List<Option> listMenus();

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
     * 获取路由列表
     *
     * @return
     */
    List<RouteVO> listRoutes();

    /**
     * 获取资源
     *
     * @return
     */
    ResourceVO getResource();
}
