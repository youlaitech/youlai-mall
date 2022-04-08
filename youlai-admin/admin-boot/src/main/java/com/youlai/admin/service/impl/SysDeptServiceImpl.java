package com.youlai.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.common.constant.SystemConstants;
import com.youlai.admin.mapper.SysDeptMapper;
import com.youlai.admin.pojo.entity.SysDept;
import com.youlai.admin.pojo.vo.dept.DeptVO;
import com.youlai.admin.service.ISysDeptService;
import com.youlai.admin.service.ISysUserService;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.web.vo.OptionVO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * 部门业务类
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2021-08-22
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    private final ISysUserService iSysUserService;

    /**
     * 部门表格（Table）层级列表
     *
     * @param name 部门名称
     * @return
     */
    @Override
    public List<DeptVO> listTableDepartments(Integer status, String name) {
        List<SysDept> deptList = this.list(
                new LambdaQueryWrapper<SysDept>()
                        .like(StrUtil.isNotBlank(name), SysDept::getName, name)
                        .eq(Validator.isNotNull(status), SysDept::getStatus, status)
                        .orderByAsc(SysDept::getSort)
        );
        return recursion(deptList);
    }

    /**
     * 递归生成部门表格层级列表
     *
     * @param deptList 部门列表
     * @return 部门列表
     */
    private static List<DeptVO> recursion(List<SysDept> deptList) {
        List<DeptVO> deptTableList = new ArrayList<>();
        // 保存所有节点的 id
        Set<Long> nodeIdSet = deptList.stream()
                .map(SysDept::getId)
                .collect(Collectors.toSet());
        for (SysDept sysDept : deptList) {
            // 不在节点 id 集合中存在的 id 即为顶级节点 id, 递归生成列表
            Long parentId = sysDept.getParentId();
            if (!nodeIdSet.contains(parentId)) {
                deptTableList.addAll(recursionTableList(parentId, deptList));
                nodeIdSet.add(parentId);
            }
        }
        // 如果结果列表为空说明所有的节点都是独立分散的, 直接转换后返回
        if (deptTableList.isEmpty()) {
            return deptList.stream()
                    .map(item -> {
                        DeptVO deptVO = new DeptVO();
                        BeanUtil.copyProperties(item, deptVO);
                        return deptVO;
                    })
                    .collect(Collectors.toList());
        }
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
    public List<OptionVO> listTreeSelectDepartments() {
        List<SysDept> deptList = this.list(new LambdaQueryWrapper<SysDept>()
                .eq(SysDept::getStatus, GlobalConstants.STATUS_YES)
                .orderByAsc(SysDept::getSort)
        );
        List<OptionVO> deptSelectList = recursionTreeSelectList(SystemConstants.ROOT_DEPT_ID, deptList);
        return deptSelectList;
    }


    /**
     * 递归生成部门表格层级列表
     *
     * @param parentId
     * @param deptList
     * @return
     */
    public static List<OptionVO> recursionTreeSelectList(long parentId, List<SysDept> deptList) {
        List<OptionVO> deptTreeSelectList = new ArrayList<>();
        Optional.ofNullable(deptList).orElse(new ArrayList<>())
                .stream()
                .filter(dept -> dept.getParentId().equals(parentId))
                .forEach(dept -> {
                    OptionVO optionVO = new OptionVO(dept.getId(), dept.getName());
                    List<OptionVO> children = recursionTreeSelectList(dept.getId(), deptList);
                    if (CollectionUtil.isNotEmpty(children)) {
                        optionVO.setChildren(children);
                    }
                    deptTreeSelectList.add(optionVO);
                });
        return deptTreeSelectList;
    }


    /**
     * 保存（新增/修改）部门
     *
     * @param dept
     * @return
     */
    @Override
    public Long saveDept(SysDept dept) {
        // 生成部门树路径
        String treePath = generateDeptTreePath(dept);
        dept.setTreePath(treePath);

        boolean result = this.saveOrUpdate(dept);
        Assert.isTrue(result, "保存部门出错");
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
     * 生成部门路径
     *
     * @param dept
     * @return
     */
    private String generateDeptTreePath(SysDept dept) {
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
