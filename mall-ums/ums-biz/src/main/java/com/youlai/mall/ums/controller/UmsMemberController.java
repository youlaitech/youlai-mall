package com.youlai.mall.ums.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.core.constant.AuthConstants;
import com.youlai.common.core.result.Result;
import com.youlai.common.core.result.ResultCode;
import com.youlai.common.web.util.WebUtils;
import com.youlai.mall.ums.dto.MemberDTO;
import com.youlai.mall.ums.dto.MemberInfoDTO;
import com.youlai.mall.ums.pojo.UmsMember;
import com.youlai.mall.ums.vo.MemberVO;
import com.youlai.mall.ums.service.IUmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Api(tags = "会员接口")
@RestController
@RequestMapping("/members")
@Slf4j
@AllArgsConstructor
public class UmsMemberController {
    @Autowired
    private IUmsMemberService iUmsMemberService;

    @ApiOperation(value = "获取会员信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员唯一标识", required = true, paramType = "path", dataType = "Object"),
            @ApiImplicitParam(name = "queryMode", defaultValue = "1", value = "查询模式：1-认证会员 2-订单会员", paramType = "query", dataType = "Integer")
    })

    @GetMapping("/{id}")
    public Result getMember(
            @PathVariable Object id,
            @RequestParam Integer queryMode
    ) {
        if (queryMode.equals(1)) { // 认证会员信息
            UmsMember member = iUmsMemberService.getOne(new LambdaQueryWrapper<UmsMember>()
                    .eq(UmsMember::getOpenid, id));
            if (member == null) {
                return Result.failed(ResultCode.USER_NOT_EXIST);
            }
            MemberDTO memberDTO = new MemberDTO();
            BeanUtil.copyProperties(member, memberDTO);
            return Result.success(memberDTO);
        } else if (queryMode.equals(2)) { // 订单会员信息
            MemberInfoDTO memberInfoDTO = new MemberInfoDTO();
            UmsMember member = iUmsMemberService.getOne(
                    new LambdaQueryWrapper<UmsMember>()
                            .select(UmsMember::getId, UmsMember::getNickname, UmsMember::getMobile)
                            .eq(UmsMember::getId, id)
            );
            if (member != null) {
                BeanUtil.copyProperties(member, memberInfoDTO);
            }
            return Result.success(memberInfoDTO);
        }

        return Result.failed("查询模式错误");
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
    public Result getCurrentMemberInfo() {
        Long memberId = WebUtils.getUserId();
        UmsMember member = iUmsMemberService.getById(memberId);

        if (member == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }

        MemberVO memberVO = new MemberVO();
        BeanUtil.copyProperties(member, memberVO);
        return Result.success(memberVO);
    }
}
