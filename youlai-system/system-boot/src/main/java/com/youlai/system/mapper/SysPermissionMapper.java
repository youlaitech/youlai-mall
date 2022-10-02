package com.youlai.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.system.pojo.entity.SysPermission;
import com.youlai.system.pojo.query.PermPageQuery;
import com.youlai.system.pojo.vo.perm.PermPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 获取权限分页列表
     *
     * @param page
     * @param queryParams
     * @return
     */
    List<PermPageVO> listPermPages(Page<PermPageVO> page, PermPageQuery queryParams);

    /**
     * 权限<->有权限的角色集合
     *
     * @return
     */
    List<SysPermission> listPermRoles();

    /**
     * 根据角色编码集合获取按钮权限
     *
     * @param roles
     * @return
     */
    List<String> listBtnPermByRoles(List<String> roles);


}
