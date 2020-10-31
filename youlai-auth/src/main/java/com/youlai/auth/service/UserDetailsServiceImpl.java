package com.youlai.auth.service;

import com.youlai.admin.dto.UserDTO;
import com.youlai.admin.feign.RemoteAdminService;
import com.youlai.auth.domain.User;
import com.youlai.common.core.constant.AuthConstants;
import com.youlai.common.core.result.Result;
import com.youlai.common.core.result.ResultCode;
import com.youlai.mall.ums.dto.MemberDTO;
import com.youlai.mall.ums.feign.RemoteUmsMemberService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


/**
 * 自定义用户认证和授权
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private RemoteAdminService remoteAdminService;
    private RemoteUmsMemberService remoteUmsMemberService;
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String clientId = request.getParameter("client_id");
        User user = null;
        switch (clientId) {
            case AuthConstants.ADMIN_CLIENT_ID: // 后台用户
                Result<UserDTO> userResult = remoteAdminService.loadUserByUsername(username);
                if (userResult == null || !ResultCode.SUCCESS.getCode().equals(userResult.getCode())) {
                    throw new UsernameNotFoundException("用户不存在");
                }
                UserDTO userDTO = userResult.getData();
                userDTO.setClientId(clientId);
                user = new User(userDTO);
                break;
            case AuthConstants.WEAPP_CLIENT_ID: // 小程序会员
                Result<MemberDTO> memberResult = remoteUmsMemberService.loadMemberByOpenid(username);
                if (memberResult == null || !ResultCode.SUCCESS.getCode().equals(memberResult.getCode())) {
                    throw new UsernameNotFoundException("会员不存在");
                }
                MemberDTO memberDTO = memberResult.getData();
                memberDTO.setClientId(clientId);
                user = new User(memberDTO);
                break;
        }
        if (!user.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!user.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        } else if (!user.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("该账户的登录凭证已过期，请重新登录!");
        }
        return user;


    }

}
