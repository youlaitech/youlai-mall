package com.youlai.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.common.constant.SystemConstants;
import com.youlai.admin.pojo.entity.SysDept;
import com.youlai.admin.pojo.vo.DeptVO;
import com.youlai.admin.mapper.SysDeptMapper;
import com.youlai.admin.pojo.vo.TreeVO;
import com.youlai.admin.service.ISysDeptService;
import com.youlai.common.constant.GlobalConstants;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 部门业务类
 *
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 * @date 2021-08-22
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {


    /**
     * 部门表格（Table）层级列表
     *
     * @param name 部门名称
     * @return
     */
    @Override
    public List<DeptVO> listTable(Integer status, String name) {
        List<SysDept> deptList = this.list(new LambdaQueryWrapper<SysDept>()
                .orderByAsc(SysDept::getSort));
        List<DeptVO> deptTableList = recursionTableList(SystemConstants.ROOT_DEPT_ID, deptList);
        return deptTableList;
    }

    /**
     * 递归生成部门表格层级列表
     *
     * @param parentId
     * @param deptList
     * @return
     */
    public static List<DeptVO> recursionTableList(Long parentId, List<SysDept> deptList) {
        List<DeptVO> deptTableList = new ArrayList<>();
        Optional.ofNullable(deptList).orElse(new ArrayList<>())
                .stream()
                .filter(dept -> dept.getParentId().equals(parentId))
                .forEach(dept -> {
                    DeptVO deptVO = new DeptVO();
                    BeanUtil.copyProperties(dept, deptVO);
                    List<DeptVO> children = recursionTableList(dept.getId(), deptList);
                    deptVO.setChildren(children);
                    deptTableList.add(deptVO);
                });
        return deptTableList;
    }


    /**
     * 部门下拉（Select）层级列表
     *
     * @return
     */
    @Override
    public List<TreeVO> listSelect() {
        List<SysDept> deptList = this.list(new LambdaQueryWrapper<SysDept>()
                .eq(SysDept::getStatus, GlobalConstants.STATUS_YES)
                .orderByAsc(SysDept::getSort)
        );
        List<TreeVO> deptSelectList = recursionSelectList(SystemConstants.ROOT_DEPT_ID, deptList);
        return deptSelectList;
    }


    /**
     * 递归生成部门表格层级列表
     *
     * @param parentId
     * @param deptList
     * @return
     */
    public static List<TreeVO> recursionSelectList(long parentId, List<SysDept> deptList) {
        List<TreeVO> deptSelectList = new ArrayList<>();
        Optional.ofNullable(deptList).orElse(new ArrayList<>())
                .stream()
                .filter(dept -> dept.getParentId().equals(parentId))
                .forEach(dept -> {
                    TreeVO treeVO = new TreeVO();
                    treeVO.setId(dept.getId());
                    treeVO.setLabel(dept.getName());
                    List<TreeVO> children = recursionSelectList(dept.getId(), deptList);
                    treeVO.setChildren(children);
                    deptSelectList.add(treeVO);
                });
        return deptSelectList;
    }


    /**
     * 保存（新增/修改）部门
     *
     * @param dept
     * @return
     */
    @Override
    public Long saveDept(SysDept dept) {
        String treePath = getDeptTreePath(dept);
        dept.setTreePath(treePath);
        this.saveOrUpdate(dept);
        return dept.getId();
    }

    /**
     * 删除部门
     *
     * @param ids 部门ID，多个以英文逗号,拼接字符串
     * @return
     */
    @Override
    public boolean deleteByIds(String ids) {
        AtomicBoolean result = new AtomicBoolean(true);
        List<String> idList = Arrays.asList(ids.split(","));
        // 删除部门及子部门
        Optional.ofNullable(idList).orElse(new ArrayList<>()).forEach(id ->
                result.set(this.remove(new LambdaQueryWrapper<SysDept>()
                        .eq(SysDept::getId, id)
                        .or()
                        .apply("concat (',',tree_path,',') like concat('%,',{0},',%')", id)))
        );
        return result.get();
    }


    /**
     * 获取部门级联路径
     *
     * @param dept
     * @return
     */
    private String getDeptTreePath(SysDept dept) {
        Long parentId = dept.getParentId();
        String treePath;
        if (parentId.equals(SystemConstants.ROOT_DEPT_ID)) {
            treePath = String.valueOf(SystemConstants.ROOT_DEPT_ID);
        } else {
            SysDept parentDept = this.getById(parentId);
            treePath = Optional.ofNullable(parentDept).map(item -> item.getTreePath() + "," + item.getId()).orElse(Strings.EMPTY);
        }
        return treePath;
    }


}
