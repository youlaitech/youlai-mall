package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.entity.SysPermission;
import com.youlai.admin.pojo.vo.PermissionVO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {


    List<SysPermission> listPermRoles();

    List<String> listBtnPermByRoles(List<String> roles);

    List<PermissionVO> list(Page<PermissionVO> page, String name, Long menuId);
}
