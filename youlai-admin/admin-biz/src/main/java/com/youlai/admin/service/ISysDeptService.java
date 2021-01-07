package com.youlai.admin.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.pojo.SysDept;
import com.youlai.admin.pojo.vo.DeptVO;
import com.youlai.admin.pojo.vo.TreeSelectVO;

import java.util.List;

public interface ISysDeptService extends IService<SysDept> {

    List<DeptVO> listForTree(LambdaQueryWrapper<SysDept> baseQuery);

    List<TreeSelectVO> listForTreeSelect(LambdaQueryWrapper<SysDept> baseQuery);
}
