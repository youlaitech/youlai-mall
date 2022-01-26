package com.youlai.admin.mapper;

/**
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/1/24 21:52
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.admin.pojo.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author haoxr
 * @date 2020-11-06
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> listRoutes();

}
