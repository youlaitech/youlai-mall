package com.youlai.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.pms.pojo.entity.PmsSpu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 */
@Mapper
public interface PmsSpuMapper extends BaseMapper<PmsSpu> {

    List<PmsSpu> list( String name, Long categoryId);
}
