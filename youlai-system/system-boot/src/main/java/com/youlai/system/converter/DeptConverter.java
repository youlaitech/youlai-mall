package com.youlai.system.converter;

import com.youlai.system.model.entity.Dept;
import com.youlai.system.model.form.DeptForm;
import com.youlai.system.model.vo.DeptVO;
import org.mapstruct.Mapper;

/**
 * 部门对象转换器
 *
 * @author Ray
 * @since 2022/7/29
 */
@Mapper(componentModel = "spring")
public interface DeptConverter {

    DeptForm convertToForm(Dept entity);
    DeptVO entity2Vo(Dept entity);

    Dept convertToForm(DeptForm deptForm);

}