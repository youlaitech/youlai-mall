package com.youlai.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.common.constant.AdminConstants;
import com.youlai.admin.pojo.entity.SysDept;
import com.youlai.admin.pojo.vo.DeptVO;
import com.youlai.admin.mapper.SysDeptMapper;
import com.youlai.admin.pojo.vo.TreeVO;
import com.youlai.admin.service.ISysDeptService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Override
    public List<DeptVO> listDeptVO(LambdaQueryWrapper<SysDept> baseQuery) {
        List<SysDept> deptList = this.baseMapper.selectList(baseQuery);
        List<DeptVO> list = recursionForTree(AdminConstants.ROOT_DEPT_ID, deptList);
        return list;
    }

    @Override
    public List<TreeVO> listTreeVO(LambdaQueryWrapper<SysDept> baseQuery) {
        List<SysDept> deptList = this.baseMapper.selectList(baseQuery);
        List<TreeVO> list = recursionForTreeSelect(AdminConstants.ROOT_DEPT_ID, deptList);
        return list;
    }

    /**
     * 递归生成部门表格数据
     * @param parentId
     * @param deptList
     * @return
     */
    public static List<DeptVO> recursionForTree(Long parentId, List<SysDept> deptList) {
        List<DeptVO> list = new ArrayList<>();
        Optional.ofNullable(deptList).orElse(new ArrayList<>())
                .stream()
                .filter(dept -> dept.getParentId().equals(parentId))
                .forEach(dept -> {
                    DeptVO deptVO = new DeptVO();
                    BeanUtil.copyProperties(dept, deptVO);
                    List<DeptVO> children = recursionForTree(dept.getId(), deptList);
                    deptVO.setChildren(children);
                    list.add(deptVO);
                });
        return list;
    }


    /**
     * 递归生成部门树形下拉数据
     * @param parentId
     * @param deptList
     * @return
     */
    public static List<TreeVO> recursionForTreeSelect(long parentId, List<SysDept> deptList) {
        List<TreeVO> list = new ArrayList<>();
        Optional.ofNullable(deptList).orElse(new ArrayList<>())
                .stream()
                .filter(dept -> dept.getParentId().equals(parentId))
                .forEach(dept -> {
                    TreeVO treeVO = new TreeVO();
                    treeVO.setId(dept.getId());
                    treeVO.setLabel(dept.getName());
                    List<TreeVO> children = recursionForTreeSelect(dept.getId(), deptList);
                    treeVO.setChildren(children);
                    list.add(treeVO);
                });
        return list;
    }

}
