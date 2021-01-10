package com.youlai.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.mapper.SysPermissionMapper;
import com.youlai.admin.pojo.SysPermission;
import com.youlai.admin.pojo.vo.TreeVO;
import com.youlai.admin.service.ISysPermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Override
    public List<SysPermission> listForPermissionRoles() {
        return this.baseMapper.listForPermissionRoles();
    }

    @Override
    public List<TreeVO> listForTree(LambdaQueryWrapper<SysPermission> baseQuery) {
        List<TreeVO> list = new ArrayList<>();
        List<SysPermission> resources = this.list(baseQuery);
        Optional.ofNullable(resources).orElse(new ArrayList<>()).forEach(item -> {
            TreeVO treeVO = new TreeVO();
            treeVO.setId(item.getId());
            treeVO.setLabel(item.getName() + "：" + item.getPermission());
            list.add(treeVO);
        });
        return list;
    }
}
