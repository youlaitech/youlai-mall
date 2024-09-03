package com.youlai.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.model.Option;
import com.youlai.system.model.entity.DictItem;
import com.youlai.system.model.form.DictForm;
import com.youlai.system.model.vo.DictPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 字典项 对象转换器
 *
 * @author Ray
 * @since 2022/6/8
 */
@Mapper(componentModel = "spring")
public interface DictItemConverter {

    Page<DictPageVO> convertToPageVo(Page<DictItem> page);

    DictForm convertToForm(DictItem entity);

    DictItem convertToEntity(DictForm.DictItem dictFormDictItems);
    List<DictItem> convertToEntity(List<DictForm.DictItem> dictFormDictItems);

    DictForm.DictItem convertToDictFormDictItem(DictItem entity);
    List<DictForm.DictItem> convertToDictFormDictItem(List<DictItem> entities);

    @Mappings({
            @Mapping(target = "value", source = "id"),
            @Mapping(target = "label", source = "name")
    })
    Option convertToOption(DictItem dictItem);
    List<Option> convertToOption(List<DictItem> dictItems);
}
