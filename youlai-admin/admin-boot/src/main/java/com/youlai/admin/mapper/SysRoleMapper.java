package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.youlai.admin.pojo.entity.SysDept;
import com.youlai.admin.pojo.entity.SysRole;
import com.youlai.admin.pojo.entity.SysUser;
import com.youlai.common.mybatis.annotation.DataPermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {


    @DataPermission()
    @Override
    List<SysRole> selectList(@Param(Constants.WRAPPER) Wrapper<SysRole> queryWrapper);

    @DataPermission()
    <E extends IPage<SysRole>> E selectPage(E page, @Param("ew") Wrapper<SysRole> queryWrapper);

}
