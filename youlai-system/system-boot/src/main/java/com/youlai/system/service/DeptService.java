package com.youlai.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.common.core.model.Option;
import com.youlai.system.model.entity.Dept;
import com.youlai.system.model.form.DeptForm;
import com.youlai.system.model.query.DeptQuery;
import com.youlai.system.model.vo.DeptVO;

import java.util.List;

/**
 * 部门业务接口
 *
 * @author haoxr
 * @since 2021/8/22
 */
public interface DeptService extends IService<Dept> {
    /**
     * 部门列表
     *
     * @return 部门列表
     */
    List<DeptVO> getDeptList(DeptQuery queryParams);

    /**
     * 部门树形下拉选项
     *
     * @return 部门树形下拉选项
     */
    List<Option<Long>> listDeptOptions();

    /**
     * 新增部门
     *
     * @param formData 部门表单
     * @return 部门ID
     */
    Long saveDept(DeptForm formData);

    /**
     * 修改部门
     *
     * @param deptId  部门ID
     * @param formData 部门表单
     * @return 部门ID
     */
    Long updateDept(Long deptId, DeptForm formData);

    /**
     * 删除部门
     *
     * @param ids 部门ID，多个以英文逗号,拼接字符串
     * @return 是否成功
     */
    boolean deleteByIds(String ids);

    /**
     * 获取部门详情
     *
     * @param deptId 部门ID
     * @return 部门详情
     */
    DeptForm getDeptForm(Long deptId);
}
