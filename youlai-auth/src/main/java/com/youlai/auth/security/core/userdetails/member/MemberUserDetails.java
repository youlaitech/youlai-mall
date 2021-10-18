package com.youlai.auth.security.core.userdetails.member;

import com.youlai.common.constant.GlobalConstants;
import com.youlai.mall.ums.pojo.dto.MemberAuthDTO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;


/**
 * 用户认证信息
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxianrui</a>
 * @date 2021/9/27
 */
@Data
public class MemberUserDetails implements UserDetails {

    private Long userId;
    private String username;
    private Boolean enabled;

    /**
     * 认证方式
     */
    private String authenticationMethod;


    /**
     * 小程序会员用户体系
     *
     * @param member 小程序会员用户认证信息
     */
    public MemberUserDetails(MemberAuthDTO member) {
        this.setUserId(member.getUserId());
        this.setUsername(member.getUsername());
        this.setEnabled(GlobalConstants.STATUS_YES.equals(member.getStatus()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new HashSet<>();
        return collection;
    }

    @Override
    public String getPassword() {
        return null;
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
