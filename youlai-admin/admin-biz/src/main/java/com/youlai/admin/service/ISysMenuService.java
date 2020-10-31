package com.youlai.admin.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.entity.SysMenu;
import com.youlai.admin.vo.MenuVO;
import com.youlai.admin.vo.TreeSelectVO;

import java.util.List;

public interface ISysMenuService extends IService<SysMenu> {

    List<MenuVO> listForTableData(LambdaQueryWrapper<SysMenu> baseQuery);

    List<TreeSelectVO> listForTreeSelect(LambdaQueryWrapper<SysMenu> baseQuery);

    List listForRouter();
}
