package com.youlai.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.system.pojo.entity.SysRole;
import com.youlai.system.pojo.query.RolePageQuery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {


    /**
     * 角色分页列表
     *
     * @param page
     * @param queryParams
     * @return
     */
    Page<SysRole> listRolePages(Page<SysRole> page, RolePageQuery queryParams);
}
