package com.youlai.auth.service;

import com.youlai.admin.pojo.dto.UserDTO;
import com.youlai.admin.api.UserFeignService;
import com.youlai.auth.domain.User;
import com.youlai.common.constant.AuthConstants;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.common.web.exception.BizException;
import com.youlai.common.web.util.RequestUtils;
import com.youlai.mall.ums.pojo.dto.AuthMemberDTO;
import com.youlai.mall.ums.api.UmsMemberFeignService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * 自定义用户认证和授权
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserFeignService userFeignService;
    private UmsMemberFeignService memberFeignService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String clientId = RequestUtils.getAuthClientId();

        User user = null;
        Result result;
        switch (clientId) {
            case AuthConstants.ADMIN_CLIENT_ID: // 后台用户
                result = userFeignService.getUserByUsername(username);
                if (ResultCode.SUCCESS.getCode().equals(result.getCode())) {
                    UserDTO userDTO = (UserDTO) result.getData();
                    user = new User(userDTO);
                } else {
                    throw new BizException(ResultCode.getValue(result.getCode()));
                }
                break;
            case AuthConstants.WEAPP_CLIENT_ID: // 小程序会员
                result = memberFeignService.getUserByOpenid(username);
                if (ResultCode.SUCCESS.getCode().equals(result.getCode())) {
                    AuthMemberDTO authMemberDTO = (AuthMemberDTO) result.getData();
                    user = new User(authMemberDTO);
                } else {
                    throw new BizException(ResultCode.getValue(result.getCode()));
                }
                break;
        }
        if (user == null) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        }
        if (!user.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!user.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return user;
    }

}
