package com.youlai.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.model.Option;
import com.youlai.system.model.entity.DictItem;
import com.youlai.system.model.form.DictItemForm;
import com.youlai.system.model.vo.DictPageVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 字典项 对象转换器
 *
 * @author Ray
 * @since 2022/6/8
 */
@Mapper(componentModel = "spring")
public interface DictDataConverter {

    Page<DictPageVO> toPageVo(Page<DictItem> page);

    DictItemForm toForm(DictItem entity);

    DictItem toEntity(DictItemForm formFata);

    Option<Long> toOption(DictItem dictItem);
    List<Option<Long>> toOption(List<DictItem> dictData);
}
