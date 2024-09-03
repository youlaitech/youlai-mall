package com.youlai.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.system.model.entity.Dict;
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

    Page<DictPageVO> convertToPageVo(Page<Dict> page);

    DictForm convertToForm(Dict entity);

    Dict convertToEntity(DictForm entity);
}
