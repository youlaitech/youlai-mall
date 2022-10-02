package com.youlai.system.mapper;

/**
 * @author haoxr
 * @date 2022/1/24 21:52
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.system.pojo.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> listRoutes();

}
