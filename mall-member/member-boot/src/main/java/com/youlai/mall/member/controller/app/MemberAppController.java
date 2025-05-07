package com.youlai.mall.member.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.result.Result;
import com.youlai.common.security.util.SecurityUtils;
import com.youlai.mall.member.dto.MemberAddressDTO;
import com.youlai.mall.member.dto.MemberAuthDTO;
import com.youlai.mall.member.dto.MemberRegisterDTO;
import com.youlai.mall.member.model.dto.MemberDTO;
import com.youlai.mall.member.model.entity.MemberEntity;
import com.youlai.mall.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "App-会员管理")
@RestController
@RequestMapping("/app-api/v1/members")
@RequiredArgsConstructor
public class MemberAppController {

    private final MemberService memberService;

    @Operation(summary = "根据会员ID获取OpenId")
    @GetMapping("/{memberId}/openid")
    public Result<String> getMemberOpenId(
            @Parameter(description = "会员ID") @PathVariable Long memberId
    ) {
        MemberEntity memberEntity = memberService.getOne(new LambdaQueryWrapper<MemberEntity>()
                .eq(MemberEntity::getId, memberId)
                .select(MemberEntity::getOpenid));
        String openid = memberEntity.getOpenid();
        return Result.success(openid);
    }

    @Operation(summary = "新增会员")
    @PostMapping
    public Result<Long> addMember(@RequestBody MemberRegisterDTO member) {
        Long memberId = memberService.addMember(member);
        return Result.success(memberId);
    }

    @Operation(summary = "获取登录会员信息")
    @GetMapping("/me")
    public Result<MemberDTO> getCurrMemberInfo() {
        MemberDTO memberDTO = memberService.getCurrMemberInfo();
        return Result.success(memberDTO);
    }

    @Operation(summary = "扣减会员余额", hidden = true)
    @PutMapping("/balances/deduct")
    public Result deductMemberBalance(
            @RequestParam Long deductionAmount
    ) {

        Long memberId = SecurityUtils.getMemberId();
        boolean result = memberService.deductMemberBalance(memberId, deductionAmount);
        return Result.judge(result);
    }

    @Operation(summary = "根据OpenId获取会员认证信息", hidden = true)
    @GetMapping("/openid/{openid}")
    public Result<MemberAuthDTO> getMemberByOpenid(@Parameter(description = "微信唯一身份标识") @PathVariable String openid) {
        MemberAuthDTO memberAuthInfo = memberService.getMemberByOpenid(openid);
        return Result.success(memberAuthInfo);
    }

    @Operation(summary = "根据手机号获取会员认证信息", hidden = true)
    @GetMapping("/mobile/{mobile}")
    public Result<MemberAuthDTO> getMemberByMobile(
            @Parameter(description = "手机号码") @PathVariable String mobile
    ) {
        MemberAuthDTO memberAuthInfo = memberService.getMemberByMobile(mobile);
        return Result.success(memberAuthInfo);
    }

    @Operation(summary = "获取当前会员的地址列表")
    @GetMapping("/addresses")
    public Result<List<MemberAddressDTO>> listCurrentMemberAddresses() {
        Long memberId = SecurityUtils.getMemberId();
        List<MemberAddressDTO> addresses = memberService.listMemberAddress(memberId);
        return Result.success(addresses);
    }

}
