package com.fly.cloud.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly.common.pojo.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

}
