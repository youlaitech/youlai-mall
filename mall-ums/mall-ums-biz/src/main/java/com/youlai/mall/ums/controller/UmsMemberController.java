package com.youlai.mall.ums.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.core.result.Result;
import com.youlai.mall.ums.api.dto.MemberDTO;
import com.youlai.mall.ums.api.entity.UmsMember;
import com.youlai.mall.ums.service.IUmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result<UmsMember> loadMemberByOpenid(@PathVariable String openid) {
        UmsMember umsMember = iUmsMemberService.getOne(
                new LambdaQueryWrapper<UmsMember>()
                        .eq(UmsMember::getOpenid, openid));
        return Result.success(umsMember);
    }


    @ApiOperation(value = "新增会员", httpMethod = "POST")
    @ApiImplicitParam(name = "member", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsMember")
    @PostMapping
    public Result add(@RequestBody UmsMember umsMember) {
        boolean status = iUmsMemberService.save(umsMember);
        return Result.status(status);
    }

}
