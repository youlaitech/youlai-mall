package com.youlai.common.security.userdetails;

import cn.hutool.core.collection.CollectionUtil;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.security.dto.UserAuthDTO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;


/**
 * 系统管理用户认证信息
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2021/9/27
 */
@Data
public class SysUserDetails implements UserDetails {

    /**
     * 扩展字段：用户ID
     */
    private Long userId;

    /**
     * 扩展字段：部门ID
     */
    private Long deptId;

    /**
     * 默认字段
     */
    private String username;
    private String password;
    private Boolean enabled;

    Collection<? extends GrantedAuthority> authorities;

    public SysUserDetails() {

    }

    /**
     * 系统管理用户
     */
    public SysUserDetails(UserAuthDTO user) {
        this.setUserId(user.getUserId());
        this.setUsername(user.getUsername());
        this.setDeptId(user.getDeptId());
        this.setPassword("{bcrypt}" + user.getPassword());
        this.setEnabled(GlobalConstants.STATUS_YES.equals(user.getStatus()));
        if (CollectionUtil.isNotEmpty(user.getRoles())) {
            authorities = CollectionUtil.emptyIfNull(user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toSet()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
