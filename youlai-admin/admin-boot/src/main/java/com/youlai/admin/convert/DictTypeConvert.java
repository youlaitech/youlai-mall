package com.youlai.admin.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.entity.SysDictType;
import com.youlai.admin.pojo.form.DictTypeForm;
import com.youlai.admin.pojo.vo.dict.DictTypePageVO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * 字典类型对象转换器
 *
 * @author haoxr
 * @date 2022/6/8
 */
@Mapper(componentModel = "spring")
public interface DictTypeConvert {

    Page<DictTypePageVO> entity2Page(Page<SysDictType> page);

    DictTypeForm entity2Form(SysDictType entity);

    @InheritInverseConfiguration(name="entity2Form")
    SysDictType form2Entity(DictTypeForm entity);
}
