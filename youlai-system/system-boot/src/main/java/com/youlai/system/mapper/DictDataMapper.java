package com.youlai.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.model.Option;
import com.youlai.system.model.entity.DictData;
import com.youlai.system.model.query.DictDataPageQuery;
import com.youlai.system.model.vo.DictDataPageVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典数据映射层
 *
 * @author Ray Hao
 * @since 2.9.0
 */
@Mapper
public interface DictDataMapper extends BaseMapper<DictData> {

    /**
     * 字典数据分页列表
     */
    Page<DictDataPageVO> getDictDataPage(Page<DictDataPageVO> page, DictDataPageQuery queryParams);

    /**
     * 根据字典编码获取字典数据列表
     */
    Option listDictDataByDictCode(String dictCode);
}




