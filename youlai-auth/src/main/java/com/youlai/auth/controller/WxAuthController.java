package com.youlai.auth.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.youlai.auth.domain.WxLoginInfo;
import com.youlai.common.core.result.Result;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx/auth")
public class WxAuthController {

    @Autowired
    private WxMaService wxService;

    @PostMapping("/login")
    public Result loginByWx(@RequestBody WxLoginInfo wxLoginInfo) throws WxErrorException {
        String code = wxLoginInfo.getCode();
        if (code == null) {
            return Result.error();
        }
        WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
        if(session==null){
            return Result.error();
        }
        String openid = session.getOpenid();
        String secretKey = session.getSessionKey();
        return Result.success();
    }

}
