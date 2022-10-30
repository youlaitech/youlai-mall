package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.entity.SysRole;
import com.youlai.admin.pojo.query.RolePageQuery;
import com.youlai.common.mybatis.annotation.DataPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {



    @DataPermission(deptAlias = "d",userAlias = "u")
    Page<SysRole> listRolePages(Page<SysRole> page, RolePageQuery queryParams,boolean isRoot,String rootCode);



    @DataPermission(deptAlias = "d",userAlias = "u")
    List<SysRole> listDeptOptions(boolean isRoot,String rootCode);
}
