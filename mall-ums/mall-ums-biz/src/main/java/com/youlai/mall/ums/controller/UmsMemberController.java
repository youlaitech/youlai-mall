package com.youlai.mall.ums.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.core.constant.AuthConstants;
import com.youlai.common.core.result.Result;
import com.youlai.common.core.result.ResultCode;
import com.youlai.mall.ums.api.dto.MemberDTO;
import com.youlai.mall.ums.api.entity.UmsMember;
import com.youlai.mall.ums.api.vo.MemberVO;
import com.youlai.mall.ums.service.IUmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "会员接口")
@RestController
@RequestMapping("/members")
@Slf4j
public class UmsMemberController {
    @Autowired
    private IUmsMemberService iUmsMemberService;

    @ApiOperation(value = "根据Openid获取会员信息", httpMethod = "GET")
    @ApiImplicitParam(name = "openid", value = "用户唯一标识", required = true, paramType = "path", dataType = "String")
    @GetMapping("/member/{openid}")
    public Result<MemberDTO> loadMemberByOpenid(@PathVariable String openid) {
        MemberDTO memberDTO = null;
        UmsMember member = iUmsMemberService.getOne(
                new LambdaQueryWrapper<UmsMember>()
                        .eq(UmsMember::getOpenid, openid));
        if (member != null) {
            memberDTO = new MemberDTO();
            BeanUtil.copyProperties(member, memberDTO);
            return Result.success(memberDTO);
        }
        return Result.success(memberDTO);
    }


    @ApiOperation(value = "新增会员", httpMethod = "POST")
    @ApiImplicitParam(name = "member", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsMember")
    @PostMapping
    public Result add(@RequestBody UmsMember member) {
        boolean status = iUmsMemberService.save(member);
        return Result.status(status);
    }

    @ApiOperation(value = "获取当前请求的会员信息", httpMethod = "GET")
    @GetMapping("/me")
    public Result getCurrentMemberInfo(HttpServletRequest request) {
        String payload = request.getHeader(AuthConstants.JWT_PAYLOAD_KEY);
        JSONObject jsonObject = JSONUtil.parseObj(payload);
        Long id = jsonObject.getLong("id");
        UmsMember member = iUmsMemberService.getById(id);

        if (member == null) {
            return Result.custom(ResultCode.USER_ACCOUNT_NOT_EXIST);
        }

        MemberVO memberVO = new MemberVO();
        BeanUtil.copyProperties(member, memberVO);
        return Result.success(memberVO);
    }

}
