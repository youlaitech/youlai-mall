package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.youlai.admin.pojo.entity.SysDept;
import com.youlai.common.mybatis.annotation.DataPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    // @DataPermission
    @Override
    List<SysDept> selectList(@Param(Constants.WRAPPER) Wrapper<SysDept> queryWrapper);
}
