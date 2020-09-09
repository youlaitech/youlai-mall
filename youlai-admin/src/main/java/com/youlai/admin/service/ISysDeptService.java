package com.youlai.admin.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.domain.entity.SysDept;
import com.youlai.admin.domain.vo.DeptVO;
import com.youlai.admin.domain.vo.TreeSelectVO;

import java.util.List;

public interface ISysDeptService extends IService<SysDept> {

    List<DeptVO> listForTableData(LambdaQueryWrapper<SysDept> baseQuery);

    List<TreeSelectVO> listForTreeSelect(LambdaQueryWrapper<SysDept> baseQuery);
}
