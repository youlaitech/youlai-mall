package com.youlai.mall.ums.controller.app;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.common.core.result.Result;
import com.youlai.common.core.result.ResultCode;
import com.youlai.common.web.util.WebUtils;
import com.youlai.mall.ums.pojo.UmsMember;
import com.youlai.mall.ums.pojo.dto.AuthMemberDTO;
import com.youlai.mall.ums.pojo.dto.MemberDTO;
import com.youlai.mall.ums.pojo.vo.MemberVO;
import com.youlai.mall.ums.service.IUmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "会员接口")
@RestController
@RequestMapping("/api.app/v1/members")
@Slf4j
@AllArgsConstructor
public class AppMemberController {

    private IUmsMemberService iUmsMemberService;

    @ApiOperation(value = "获取会员信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result getMemberById(
            @PathVariable Long id
    ) {
        MemberDTO memberDTO = new MemberDTO();
        UmsMember member = iUmsMemberService.getOne(
                new LambdaQueryWrapper<UmsMember>()
                        .select(UmsMember::getId, UmsMember::getNickname, UmsMember::getMobile)
                        .eq(UmsMember::getId, id)
        );
        if (member != null) {
            BeanUtil.copyProperties(member, memberDTO);
        }
        return Result.success(memberDTO);
    }

    @ApiOperation(value = "根据openid获取会员信息", httpMethod = "GET")
    @ApiImplicitParam(name = "openid", value = "微信身份唯一标识", required = true, paramType = "path", dataType = "String")
    @GetMapping("/openid/{openid}")
    public Result getMemberByOpenid(
            @PathVariable String openid
    ) {
        UmsMember member = iUmsMemberService.getOne(new LambdaQueryWrapper<UmsMember>()
                .eq(UmsMember::getOpenid, openid));
        if (member == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }
        AuthMemberDTO authMemberDTO = new AuthMemberDTO();
        BeanUtil.copyProperties(member, authMemberDTO);
        return Result.success(authMemberDTO);
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
    public Result getMemberInfo() {
        Long memberId = WebUtils.getUserId();
        UmsMember member = iUmsMemberService.getById(memberId);
        if (member == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }
        MemberVO memberVO = new MemberVO();
        BeanUtil.copyProperties(member, memberVO);
        return Result.success(memberVO);
    }


    @ApiOperation(value = "修改会员积分", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "num", value = "积分数量", required = true, paramType = "query", dataType = "Integer")
    })
    @PutMapping("/{id}/point")
    public Result updatePoint(@PathVariable Long id, @RequestParam Integer num) {
        UmsMember member = iUmsMemberService.getById(id);
        member.setPoint(member.getPoint() + num);
        boolean result = iUmsMemberService.updateById(member);

        try {
            Thread.sleep(15 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //throw new RuntimeException("增加会员积分失败");

        return Result.status(result);
    }
}
