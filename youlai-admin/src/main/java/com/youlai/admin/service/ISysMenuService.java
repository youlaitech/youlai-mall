package com.youlai.admin.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.domain.entity.SysMenu;
import com.youlai.admin.domain.vo.MenuVO;
import com.youlai.admin.domain.vo.RouterVO;
import com.youlai.admin.domain.vo.TreeSelectVO;

import java.util.List;

public interface ISysMenuService extends IService<SysMenu> {

    List<MenuVO> listForTableData(LambdaQueryWrapper<SysMenu> baseQuery);

    List<TreeSelectVO> listForTreeSelect(LambdaQueryWrapper<SysMenu> baseQuery);

    List listForRoute();
}
