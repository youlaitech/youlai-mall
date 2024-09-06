package com.youlai.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.system.dto.CodegenMenuDTO;
import com.youlai.system.model.entity.Menu;
import com.youlai.system.model.form.MenuForm;
import com.youlai.system.model.query.MenuQuery;
import com.youlai.system.model.vo.MenuVO;
import com.youlai.common.core.model.Option;
import com.youlai.system.model.vo.RouteVO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Set;

/**
 * 菜单业务接口
 * 
 * @author Ray
 * @since 2020/11/06
 */
public interface MenuService extends IService<Menu> {

    /**
     * 获取菜单表格列表
     */
    List<MenuVO> listMenus(MenuQuery queryParams);


    /**
     * 获取菜单下拉列表
     */
    List<Option> listMenuOptions();

    /**
     * 新增菜单
     */
    boolean saveMenu(MenuForm menuForm);

    /**
     * 获取菜单路由列表
     */
    List<RouteVO> listRoutes(Set<String> roles);

    /**
     * 修改菜单显示状态
     * 
     * @param menuId 菜单ID
     * @param visible 是否显示(1-显示 0-隐藏)
     * @return
     */
    boolean updateMenuVisible(Long menuId, Integer visible);

    /**
     * 获取菜单表单数据
     */
    MenuForm getMenuForm(Long id);

    /**
     * 删除菜单
     */
    boolean deleteMenu(Long id);

    /**
     * 保存生成代码菜单
     */
    boolean createCodegenMenu(@Valid CodegenMenuDTO data);
}
