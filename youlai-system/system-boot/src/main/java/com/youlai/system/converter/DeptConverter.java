package com.youlai.system.converter;

import com.youlai.system.pojo.entity.SysDept;
import com.youlai.system.pojo.form.DeptForm;
import com.youlai.system.pojo.vo.dept.DeptDetailVO;
import com.youlai.system.pojo.vo.dept.DeptVO;
import org.mapstruct.Mapper;

/**
 * 部门对象转换器
 *
 * @author haoxr
 * @date 2022/7/29
 */
@Mapper(componentModel = "spring")
public interface DeptConverter {

    DeptForm entity2Form(SysDept entity);

    DeptVO entity2Vo(SysDept entity);

    SysDept form2Entity(DeptForm deptForm);

}