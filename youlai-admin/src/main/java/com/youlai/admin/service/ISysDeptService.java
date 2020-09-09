package com.youlai.admin.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.entity.SysDept;
import com.youlai.admin.vo.DeptVO;
import com.youlai.admin.vo.TreeSelectVO;

import java.util.List;

public interface ISysDeptService extends IService<SysDept> {

    List<DeptVO> listForTableData(LambdaQueryWrapper<SysDept> baseQuery);

    List<TreeSelectVO> listForTreeSelect(LambdaQueryWrapper<SysDept> baseQuery);
}
