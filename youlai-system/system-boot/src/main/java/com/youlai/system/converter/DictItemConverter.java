package com.youlai.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.web.model.Option;
import com.youlai.system.model.entity.SysDictItem;
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

    Page<DictPageVO> convertToPageVo(Page<SysDictItem> page);

    DictForm convertToForm(SysDictItem entity);

    SysDictItem convertToEntity(DictForm.DictItem dictFormDictItems);
    List<SysDictItem> convertToEntity(List<DictForm.DictItem> dictFormDictItems);

    DictForm.DictItem convertToDictFormDictItem(SysDictItem entity);
    List<DictForm.DictItem> convertToDictFormDictItem(List<SysDictItem> entities);

    @Mappings({
            @Mapping(target = "value", source = "id"),
            @Mapping(target = "label", source = "name")
    })
    Option convertToOption(SysDictItem dictItem);
    List<Option> convertToOption(List<SysDictItem> dictItems);
}
