package com.youlai.mall.ums.controller.app;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.common.web.util.JwtUtils;
import com.youlai.mall.pms.pojo.vo.ProductHistoryVO;
import com.youlai.mall.ums.pojo.entity.UmsMember;
import com.youlai.mall.ums.pojo.dto.MemberDTO;
import com.youlai.mall.ums.service.IUmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

@Api(tags = "【移动端】会员服务")
@RestController
@RequestMapping("/app-api/v1/members")
@Slf4j
@AllArgsConstructor
public class MemberController {

    private IUmsMemberService iUmsMemberService;

    @ApiOperation(value = "获取会员信息")
    @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result<MemberDTO> getById(
            @PathVariable Long id
    ) {
        MemberDTO memberDTO = new MemberDTO();
        UmsMember user = iUmsMemberService.getOne(
                new LambdaQueryWrapper<UmsMember>()
                        .select(UmsMember::getId, UmsMember::getNickName, UmsMember::getMobile, UmsMember::getBalance)
                        .eq(UmsMember::getId, id)
        );
        if (user != null) {
            BeanUtil.copyProperties(user, memberDTO);
        }
        return Result.success(memberDTO);
    }

    @ApiOperation(value = "获取会员实体信息")
    @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/detail/{id}")
    public Result<UmsMember> getMemberEntityById(
            @PathVariable Long id
    ) {
        UmsMember user = iUmsMemberService.getById(id);
        if (user == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }
        return Result.success(user);
    }

    @ApiOperation(value = "根据openid获取会员信息")
    @ApiImplicitParam(name = "openid", value = "微信身份唯一标识", required = true, paramType = "path", dataType = "String")
    @GetMapping("/openid/{openid}")
    public Result<UmsMember> getByOpenid(
            @PathVariable String openid
    ) {
        UmsMember member = iUmsMemberService.getOne(new LambdaQueryWrapper<UmsMember>()
                .eq(UmsMember::getOpenid, openid));
        if (member == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }
        return Result.success(member);
    }

    @ApiOperation(value = "新增会员")
    @ApiImplicitParam(name = "member", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsMember")
    @PostMapping
    public Result<Long> add(@RequestBody UmsMember member) {
        boolean status = iUmsMemberService.save(member);
        if (status) {
            return Result.success(member.getId());
        } else {
            return Result.failed();
        }
    }

    @ApiOperation(value = "修改会员")
    @PutMapping("/{id}")
    public <T> Result<T> add(@PathVariable Long id, @RequestBody UmsMember user) {
        boolean status = iUmsMemberService.updateById(user);
        return Result.judge(status);
    }

    @ApiOperation(value = "获取登录会员信息")
    @GetMapping("/me")
    public Result<MemberDTO> getMemberInfo() {
        Long userId = JwtUtils.getUserId();
        UmsMember member = iUmsMemberService.getById(userId);
        if (member == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }
        MemberDTO memberDTO = new MemberDTO();
        BeanUtil.copyProperties(member, memberDTO);
        return Result.success(memberDTO);
    }


    @ApiOperation(value = "修改会员积分")
    @PutMapping("/{id}/points")
    public <T> Result<T> updatePoint(@PathVariable Long id, @RequestParam Integer num) {
        UmsMember user = iUmsMemberService.getById(id);
        user.setPoint(user.getPoint() + num);
        boolean result = iUmsMemberService.updateById(user);
        return Result.judge(result);
    }

    @ApiOperation(value = "扣减会员余额")
    @PutMapping("/current/balances/_deduct")
    public <T> Result<T> deductBalance(@RequestParam Long balances) {
        Long userId = JwtUtils.getUserId();
        boolean result = iUmsMemberService.update(new LambdaUpdateWrapper<UmsMember>()
                .setSql("balance = balance - " + balances)
                .eq(UmsMember::getId, userId)
        );
        return Result.judge(result);
    }

    @ApiOperation(value = "添加浏览历史")
    @PostMapping("/view/history")
    public <T> Result<T> addProductViewHistory(@RequestBody ProductHistoryVO product) {
        Long userId = null;
        try {
            userId = JwtUtils.getUserId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        iUmsMemberService.addProductViewHistory(product, userId);
        return Result.success();
    }

    @ApiOperation(value = "获取浏览历史")
    @GetMapping("/view/history")
    public Result<Set<ProductHistoryVO>> getProductViewHistory() {
        try {
            Long userId = JwtUtils.getUserId();
            Set<ProductHistoryVO> historyList = iUmsMemberService.getProductViewHistory(userId);
            return Result.success(historyList);
        } catch (Exception e) {
            return Result.success(Collections.emptySet());
        }
    }
}
