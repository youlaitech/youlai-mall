package com.youlai.mall.ums.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.common.web.util.MemberUtils;
import com.youlai.mall.pms.pojo.vo.ProductHistoryVO;
import com.youlai.mall.ums.dto.MemberAuthInfoDTO;
import com.youlai.mall.ums.dto.MemberDTO;
import com.youlai.mall.ums.pojo.entity.UmsMember;
import com.youlai.mall.ums.pojo.vo.MemberVO;
import com.youlai.mall.ums.service.IUmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

@Api(tags = "「移动端」会员管理")
@RestController
@RequestMapping("/app-api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final IUmsMemberService memberService;

    @ApiOperation(value = "根据会员ID获取openid")
    @GetMapping("/{memberId}/openid")
    public Result<String> getMemberById(
            @ApiParam("会员ID") @PathVariable Long memberId
    ) {
        UmsMember member = memberService.getOne(
                new LambdaQueryWrapper<UmsMember>()
                        .eq(UmsMember::getId, memberId)
                        .select(UmsMember::getOpenid)
        );
        String openid = member.getOpenid();
        return Result.success(openid);
    }

    @ApiOperation(value = "新增会员")
    @PostMapping
    public Result<Long> addMember(@RequestBody MemberDTO member) {
        Long memberId = memberService.addMember(member);
        return Result.success(memberId);
    }

    @ApiOperation(value = "获取登录会员信息")
    @GetMapping("/me")
    public Result<MemberVO> getCurrentMemberInfo() {
        MemberVO memberVO = memberService.getCurrentMemberInfo();
        return Result.success(memberVO);
    }

    @ApiOperation(value = "扣减会员余额")
    @PutMapping("/current/balances/_deduct")
    public <T> Result<T> deductBalance(@RequestParam Long balances) {
        Long memberId = MemberUtils.getMemberId();
        boolean result = memberService.update(new LambdaUpdateWrapper<UmsMember>()
                .setSql("balance = balance - " + balances)
                .eq(UmsMember::getId, memberId)
        );
        return Result.judge(result);
    }

    @ApiOperation(value = "添加浏览历史")
    @PostMapping("/view/history")
    public <T> Result<T> addProductViewHistory(@RequestBody ProductHistoryVO product) {
        Long memberId = MemberUtils.getMemberId();
        memberService.addProductViewHistory(product, memberId);
        return Result.success();
    }

    @ApiOperation(value = "获取浏览历史")
    @GetMapping("/view/history")
    public Result<Set<ProductHistoryVO>> getProductViewHistory() {
        try {
            Long memberId = MemberUtils.getMemberId();
            Set<ProductHistoryVO> historyList = memberService.getProductViewHistory(memberId);
            return Result.success(historyList);
        } catch (Exception e) {
            return Result.success(Collections.emptySet());
        }
    }

    @ApiOperation(value = "根据 openid 获取会员认证信息")
    @GetMapping("/openid/{openid}")
    public Result<MemberAuthInfoDTO> getByOpenid(
            @ApiParam("微信身份标识") @PathVariable String openid
    ) {
        MemberAuthInfoDTO memberAuthInfo = memberService.getByOpenid(openid);
        if (memberAuthInfo == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }
        return Result.success(memberAuthInfo);
    }

    /**
     * 根据手机号获取会员认证信息
     *
     * @param mobile
     * @return
     */
    @GetMapping("/mobile/{mobile}")
    public Result<MemberAuthInfoDTO> getByMobile(
            @ApiParam("手机号码") @PathVariable String mobile
    ) {
        MemberAuthInfoDTO memberAuthInfo = memberService.getByMobile(mobile);
        if (memberAuthInfo == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }
        return Result.success(memberAuthInfo);
    }



}
