package com.youlai.mall.ums.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.common.security.util.SecurityUtils;
import com.youlai.mall.pms.model.vo.ProductHistoryVO;
import com.youlai.mall.ums.dto.MemberAddressDTO;
import com.youlai.mall.ums.dto.MemberAuthDTO;
import com.youlai.mall.ums.dto.MemberRegisterDto;
import com.youlai.mall.ums.model.entity.UmsMember;
import com.youlai.mall.ums.model.vo.MemberVO;
import com.youlai.mall.ums.service.IUmsMemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Tag(name = "「移动端」会员管理")
@RestController
@RequestMapping("/app-api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final IUmsMemberService memberService;

    @Operation(summary= "根据会员ID获取openid")
    @GetMapping("/{memberId}/openid")
    public Result<String> getMemberById(@Parameter(name = "会员ID") @PathVariable Long memberId) {
        UmsMember member = memberService.getOne(new LambdaQueryWrapper<UmsMember>()
                .eq(UmsMember::getId, memberId)
                .select(UmsMember::getOpenid));
        String openid = member.getOpenid();
        return Result.success(openid);
    }

    @Operation(summary= "新增会员")
    @PostMapping
    public Result<Long> addMember(@RequestBody MemberRegisterDto member) {
        Long memberId = memberService.addMember(member);
        return Result.success(memberId);
    }

    @Operation(summary= "获取登录会员信息")
    @GetMapping("/me")
    public Result<MemberVO> getCurrMemberInfo() {
        MemberVO memberVO = memberService.getCurrMemberInfo();
        return Result.success(memberVO);
    }

    @Operation(summary= "扣减会员余额")
    @PutMapping("/{memberId}/balances/_deduct")
    public Result deductBalance(@PathVariable Long memberId, @RequestParam Long amount) {
        boolean result = memberService.update(new LambdaUpdateWrapper<UmsMember>()
                .setSql("balance = balance - " + amount)
                .eq(UmsMember::getId, memberId));
        return Result.judge(result);
    }

    @Operation(summary= "添加浏览历史")
    @PostMapping("/view/history")
    public <T> Result<T> addProductViewHistory(@RequestBody ProductHistoryVO product) {
        Long memberId = SecurityUtils.getMemberId();
        memberService.addProductViewHistory(product, memberId);
        return Result.success();
    }

    @Operation(summary= "获取浏览历史")
    @GetMapping("/view/history")
    public Result<Set<ProductHistoryVO>> getProductViewHistory() {
        Long memberId = SecurityUtils.getMemberId();
        Set<ProductHistoryVO> historyList = memberService.getProductViewHistory(memberId);
        return Result.success(historyList);

    }

    @Operation(summary= "根据 openid 获取会员认证信息")
    @GetMapping("/openid/{openid}")
    public Result<MemberAuthDTO> getByOpenid(@Parameter(name = "微信身份标识") @PathVariable String openid) {
        MemberAuthDTO memberAuthInfo = memberService.getByOpenid(openid);
        if (memberAuthInfo == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }
        return Result.success(memberAuthInfo);
    }

    @Operation(summary= "根据手机号获取会员认证信息",hidden = true)
    @GetMapping("/mobile/{mobile}")
    public Result<MemberAuthDTO> getMemberByMobile(
            @Parameter(name = "手机号码") @PathVariable String mobile
    ) {
        MemberAuthDTO memberAuthInfo = memberService.getMemberByMobile(mobile);
        if (memberAuthInfo == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }
        return Result.success(memberAuthInfo);
    }

    @Operation(summary ="获取会员地址列表")
    @GetMapping("/{memberId}/addresses")
    public Result<List<MemberAddressDTO>> listMemberAddress(
            @Parameter(name = "会员ID") @PathVariable Long memberId
    ) {
        List<MemberAddressDTO> addresses = memberService.listMemberAddress(memberId);
        return Result.success(addresses);
    }

    @Operation(summary= "「实验室」重置会员余额", hidden = true)
    @PutMapping("/{memberId}/balance/_reset")
    public Result resetBalance(@PathVariable Long memberId) {
        boolean result = memberService.update(
                new LambdaUpdateWrapper<UmsMember>()
                        .eq(UmsMember::getId, memberId)
                        .set(UmsMember::getBalance, 10000000l));
        return Result.judge(result);
    }

}
