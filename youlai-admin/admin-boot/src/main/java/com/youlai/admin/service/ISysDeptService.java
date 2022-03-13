package com.youlai.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.pojo.entity.SysDept;
import com.youlai.admin.pojo.vo.dept.DeptVO;
import com.youlai.common.web.vo.OptionVO;

import java.util.List;

/**
 * 菜单路由业务接口
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2021-08-22
 */
public interface ISysDeptService extends IService<SysDept> {
    /**
     * 部门表格（Table）层级列表
     *
     * @param status 部门状态： 1-开启 0-禁用
     * @param name
     * @return
     */
    List<DeptVO> listTableDepartments(Integer status, String name);

    /**
     * 部门树形下拉（TreeSelect）层级列表
     *
     * @return
     */
    List<OptionVO> listTreeSelectDepartments();

    /**
     * 新增/修改部门
     *
     * @param dept
     * @return
     */
    Long saveDept(SysDept dept);

    /**
     * 删除部门
     *
     * @param ids 部门ID，多个以英文逗号,拼接字符串
     * @return
     */
    boolean deleteByIds(String ids);
}
