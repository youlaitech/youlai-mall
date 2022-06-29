package com.youlai.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.pojo.entity.SysDept;
import com.youlai.admin.pojo.query.DeptQuery;
import com.youlai.admin.pojo.vo.dept.DeptVO;
import com.youlai.common.web.domain.Option;

import java.util.List;

/**
 * 菜单路由业务接口
 *
 * @author haoxr
 * @date 2021-08-22
 */
public interface SysDeptService extends IService<SysDept> {
    /**
     * 部门列表
     *
     * @return
     */
    List<DeptVO> listDepts(DeptQuery queryParams);

    /**
     * 部门树形下拉（TreeSelect）层级列表
     *
     * @return
     */
    List<Option> lisetDeptOptions();

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
