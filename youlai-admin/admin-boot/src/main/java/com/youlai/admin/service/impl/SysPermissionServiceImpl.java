package com.youlai.admin.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.mapper.SysPermissionMapper;
import com.youlai.admin.pojo.entity.SysPermission;
import com.youlai.admin.pojo.query.PermissionPageQuery;
import com.youlai.admin.pojo.vo.permission.PermissionPageVO;
import com.youlai.admin.service.ISysPermissionService;
import com.youlai.common.constant.GlobalConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 权限业务实现类
 *
 * @author haoxr
 * @date 2022/1/22
 */
@Service
@RequiredArgsConstructor
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    private final RedisTemplate redisTemplate;

    /**
     * 获取权限分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public IPage<PermissionPageVO> listPermissionsWithPage(PermissionPageQuery queryParams) {
        Page<PermissionPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<PermissionPageVO> list = this.baseMapper.listPermissionsWithPage(page, queryParams);
        page.setRecords(list);
        return page;
    }

    /**
     * 根据角色编码集合获取按钮权限
     *
     * @param roles 角色权限编码集合
     * @return
     */
    @Override
    public List<String> listBtnPermByRoles(List<String> roles) {
        List<String> perms = this.baseMapper.listBtnPermByRoles(roles);
        return perms;
    }

    /**
     * 获取权限和拥有权限的角色映射
     *
     * @return
     */
    @Override
    public List<SysPermission> listPermRoles() {
        return this.baseMapper.listPermRoles();
    }

    /**
     * 刷新权限角色缓存
     *
     * @return
     */
    @Override
    public boolean refreshPermRolesRules() {
        redisTemplate.delete(Arrays.asList(GlobalConstants.URL_PERM_ROLES_KEY, GlobalConstants.BTN_PERM_ROLES_KEY));
        List<SysPermission> permissions = this.listPermRoles();
        if (CollectionUtil.isNotEmpty(permissions)) {
            // 初始化URL【权限->角色(集合)】规则
            List<SysPermission> urlPermList = permissions.stream()
                    .filter(item -> StrUtil.isNotBlank(item.getUrlPerm()))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(urlPermList)) {
                Map<String, List<String>> urlPermRoles = new HashMap<>();
                urlPermList.stream().forEach(item -> {
                    String perm = item.getUrlPerm();
                    List<String> roles = item.getRoles();
                    urlPermRoles.put(perm, roles);
                });
                redisTemplate.opsForHash().putAll(GlobalConstants.URL_PERM_ROLES_KEY, urlPermRoles);
                redisTemplate.convertAndSend("cleanRoleLocalCache", "true");
            }
            // 初始化URL【按钮->角色(集合)】规则
            List<SysPermission> btnPermList = permissions.stream()
                    .filter(item -> StrUtil.isNotBlank(item.getBtnPerm()))
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(btnPermList)) {
                Map<String, List<String>> btnPermRoles = MapUtil.newHashMap();
                btnPermList.stream().forEach(item -> {
                    String perm = item.getBtnPerm();
                    List<String> roles = item.getRoles();
                    btnPermRoles.put(perm, roles);
                });
                redisTemplate.opsForHash().putAll(GlobalConstants.BTN_PERM_ROLES_KEY, btnPermRoles);
            }
        }
        return true;
    }


}
