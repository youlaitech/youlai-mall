package com.youlai.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.common.AdminConstant;
import com.youlai.admin.domain.entity.SysMenu;
import com.youlai.admin.domain.entity.SysMenu;
import com.youlai.admin.domain.vo.MenuVO;
import com.youlai.admin.domain.vo.MenuVO;
import com.youlai.admin.domain.vo.TreeSelectVO;
import com.youlai.admin.mapper.SysMenuMapper;
import com.youlai.admin.service.ISysMenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {


    @Override
    public List<MenuVO> listForTableData(LambdaQueryWrapper<SysMenu> baseQuery) {
        List<SysMenu> deptList = this.baseMapper.selectList(baseQuery);
        List<MenuVO> list = recursionForTableData(AdminConstant.ROOT_DEPT_ID, deptList);
        return list;
    }



    @Override
    public List<TreeSelectVO> listForTreeSelect(LambdaQueryWrapper<SysMenu> baseQuery) {
        List<SysMenu> deptList = this.baseMapper.selectList(baseQuery);
        List<TreeSelectVO> list = recursionForTreeSelect(AdminConstant.ROOT_DEPT_ID, deptList);
        return list;
    }



    /**
     * 递归生成部门表格数据
     * @param parentId
     * @param deptList
     * @return
     */
    public static List<MenuVO> recursionForTableData(int parentId, List<SysMenu> deptList) {
        List<MenuVO> list = new ArrayList<>();
        Optional.ofNullable(deptList).orElse(new ArrayList<>())
                .stream()
                .filter(dept -> dept.getParentId().equals(parentId))
                .forEach(dept -> {
                    MenuVO deptVO = new MenuVO();
                    BeanUtil.copyProperties(dept, deptVO);
                    List<MenuVO> children = recursionForTableData(dept.getId(), deptList);
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
    public static List<TreeSelectVO> recursionForTreeSelect(int parentId, List<SysMenu> deptList) {
        List<TreeSelectVO> list = new ArrayList<>();
        Optional.ofNullable(deptList).orElse(new ArrayList<>())
                .stream()
                .filter(dept -> dept.getParentId().equals(parentId))
                .forEach(dept -> {
                    TreeSelectVO treeSelectVO = new TreeSelectVO();
                    treeSelectVO.setId(dept.getId());
                    treeSelectVO.setLabel(dept.getName());
                    List<TreeSelectVO> children = recursionForTreeSelect(dept.getId(), deptList);
                    treeSelectVO.setChildren(children);
                    list.add(treeSelectVO);
                });
        return list;
    }

}
