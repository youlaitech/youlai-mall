package com.youlai.admin.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.entity.SysDictType;
import com.youlai.admin.pojo.form.DictTypeForm;
import com.youlai.admin.pojo.vo.dict.DictTypePageVO;
import org.mapstruct.Mapper;

/**
 * 字典类型对象转换器
 *
 * @author haoxr
 * @date 2022/6/8
 */
@Mapper(componentModel = "spring")
public interface DictTypeConverter {

    Page<DictTypePageVO> entity2Page(Page<SysDictType> page);

    DictTypeForm entity2Form(SysDictType entity);

    SysDictType form2Entity(DictTypeForm entity);
}
