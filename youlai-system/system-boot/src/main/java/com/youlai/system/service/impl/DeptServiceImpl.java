package com.youlai.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.enums.StatusEnum;
import com.youlai.system.converter.DeptConverter;
import com.youlai.system.mapper.DeptMapper;
import com.youlai.system.model.entity.Dept;
import com.youlai.system.model.form.DeptForm;
import com.youlai.system.model.query.DeptQuery;
import com.youlai.system.model.vo.DeptVO;
import com.youlai.common.core.model.Option;
import com.youlai.system.service.DeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 部门业务实现类
 *
 * @author Ray
 * @since 2021-08-22
 */
@Service
@RequiredArgsConstructor
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {


    private final DeptConverter deptConverter;

    /**
     * 部门列表
     */
    @Override
    public List<DeptVO> listDepartments(DeptQuery queryParams) {
        // 查询参数
        String keywords = queryParams.getKeywords();
        Integer status = queryParams.getStatus();

        // 查询数据
        List<Dept> deptList = this.list(
                new LambdaQueryWrapper<Dept>()
                        .like(StrUtil.isNotBlank(keywords), Dept::getName, keywords)
                        .eq(status != null, Dept::getStatus, status)
                        .orderByAsc(Dept::getSort)
        );

        Set<Long> deptIds = deptList.stream()
                .map(Dept::getId)
                .collect(Collectors.toSet());

        Set<Long> parentIds = deptList.stream()
                .map(Dept::getParentId)
                .collect(Collectors.toSet());

        List<Long> rootIds = CollectionUtil.subtractToList(parentIds, deptIds);

        List<DeptVO> list = new ArrayList<>();
        for (Long rootId : rootIds) {
            list.addAll(recurDeptList(rootId, deptList));
        }
        return list;
    }

    /**
     * 递归生成部门树形列表
     *
     * @param parentId
     * @param deptList
     * @return
     */
    public List<DeptVO> recurDeptList(Long parentId, List<Dept> deptList) {
        return deptList.stream()
                .filter(dept -> dept.getParentId().equals(parentId))
                .map(dept -> {
                    DeptVO deptVO = deptConverter.toVo(dept);
                    List<DeptVO> children = recurDeptList(dept.getId(), deptList);
                    deptVO.setChildren(children);
                    return deptVO;
                }).collect(Collectors.toList());
    }

    /**
     * 部门下拉选项
     *
     * @return 部门下拉List集合
     */
    @Override
    public List<Option> listDeptOptions() {

        List<Dept> deptList = this.list(new LambdaQueryWrapper<Dept>()
                .eq(Dept::getStatus, StatusEnum.ENABLE.getValue())
                .select(Dept::getId, Dept::getParentId, Dept::getName)
                .orderByAsc(Dept::getSort)
        );

        Set<Long> parentIds = deptList.stream()
                .map(Dept::getParentId)
                .collect(Collectors.toSet());

        Set<Long> deptIds = deptList.stream()
                .map(Dept::getId)
                .collect(Collectors.toSet());

        List<Long> rootIds = CollectionUtil.subtractToList(parentIds, deptIds);

        List<Option> list = new ArrayList<>();
        for (Long rootId : rootIds) {
            list.addAll(recurDeptTreeOptions(rootId, deptList));
        }
        return list;
    }

    /**
     * 保存部门
     *
     * @param formData
     * @return
     */
    @Override
    public Long saveDept(DeptForm formData) {
        Dept entity = deptConverter.toEntity(formData);
        // 部门层级路径
        String treePath = buildTreePath(formData.getParentId());
        entity.setTreePath(treePath);
        // 保存部门并返回部门ID
        this.saveOrUpdate(entity);
        return entity.getId();
    }

    /**
     * 递归生成部门表格层级列表
     *
     * @param parentId
     * @param deptList
     * @return
     */
    public static List<Option> recurDeptTreeOptions(long parentId, List<Dept> deptList) {
        List<Option> list = CollectionUtil.emptyIfNull(deptList).stream()
                .filter(dept -> dept.getParentId().equals(parentId))
                .map(dept -> {
                    Option option = new Option(dept.getId(), dept.getName());
                    List<Option> children = recurDeptTreeOptions(dept.getId(), deptList);
                    if (CollectionUtil.isNotEmpty(children)) {
                        option.setChildren(children);
                    }
                    return option;
                })
                .collect(Collectors.toList());
        return list;
    }


    /**
     * 删除部门
     *
     * @param ids 部门ID，多个以英文逗号,拼接字符串
     * @return
     */
    @Override
    public void deleteByIds(String ids) {
        // 删除部门及子部门
        if (StrUtil.isNotBlank(ids)) {
            String[] deptIds = ids.split(",");
            for (String deptId : deptIds) {
                this.remove(new LambdaQueryWrapper<Dept>()
                        .eq(Dept::getId, deptId)
                        .or()
                        .apply("CONCAT (',',tree_path,',') LIKE CONCAT('%,',{0},',%')", deptId));
            }
        }
    }

    /**
     * 获取部门详情
     *
     * @param deptId
     * @return
     */
    @Override
    public DeptForm getDeptForm(Long deptId) {

        Dept entity = this.getOne(new LambdaQueryWrapper<Dept>()
                .eq(Dept::getId, deptId)
                .select(
                        Dept::getId,
                        Dept::getName,
                        Dept::getParentId,
                        Dept::getStatus,
                        Dept::getSort
                ));

        return deptConverter.toForm(entity);
    }


    /**
     * 构建部门层级路径
     *
     * @param parentId 父ID
     * @return 父节点路径以英文逗号(, )分割，eg: 1,2,3
     */
    private String buildTreePath(Long parentId) {
        String treePath = null;
        if (GlobalConstants.ROOT_NODE_ID.equals(parentId)) {
            treePath = String.valueOf(parentId);
        } else {
            Dept parent = this.getById(parentId);
            if (parent != null) {
                treePath = parent.getTreePath() + "," + parent.getId();
            }
        }
        return treePath;
    }
}
