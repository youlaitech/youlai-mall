package com.youlai.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.system.model.entity.SysDict;
import com.youlai.system.model.form.DictForm;
import com.youlai.system.model.vo.DictPageVO;
import org.mapstruct.Mapper;

/**
 * 字典 对象转换器
 *
 * @author Ray Hao
 * @since 2022/6/8
 */
@Mapper(componentModel = "spring")
public interface DictConverter {

    Page<DictPageVO> convertToPageVo(Page<SysDict> page);

    DictForm convertToForm(SysDict entity);

    SysDict convertToEntity(DictForm entity);
}
