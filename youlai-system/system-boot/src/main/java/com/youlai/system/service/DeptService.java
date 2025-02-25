package com.youlai.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.system.model.entity.Dept;
import com.youlai.system.model.form.DeptForm;
import com.youlai.system.model.query.DeptQuery;
import com.youlai.system.model.vo.DeptVO;
import com.youlai.common.core.model.Option;

import java.util.List;

/**
 * 部门业务接口
 *
 * @author Ray.Hao
 * @since 2021/8/22
 */
public interface DeptService extends IService<Dept> {
    /**
     * 部门列表
     */
    List<DeptVO> listDepartments(DeptQuery queryParams);

    /**
     * 部门树形下拉选项
     */
    List<Option> listDeptOptions();

    /**
     * 新增部门
     */
    Long saveDept(DeptForm formData);

    /**
     * 删除部门
     *
     * @param ids 部门ID，多个以英文逗号,拼接字符串
     */
    void deleteByIds(String ids);

    /**
     * 获取部门详情
     *
     * @param deptId
     * @return
     */
    DeptForm getDeptForm(Long deptId);
}
