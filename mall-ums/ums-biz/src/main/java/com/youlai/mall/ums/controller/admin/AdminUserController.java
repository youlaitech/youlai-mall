package com.youlai.mall.ums.controller.admin;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.constant.SystemConstants;
import com.youlai.common.core.enums.QueryModeEnum;
import com.youlai.common.core.result.Result;
import com.youlai.mall.ums.pojo.UmsUser;
import com.youlai.mall.ums.service.IUmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "会员接口")
@RestController
@RequestMapping("/api.admin/v1/users")
@Slf4j
public class AdminUserController {

    @Autowired
    private IUmsUserService iUmsUserService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryMode", paramType = "query", dataType = "QueryModeEnum"),
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "nickname", value = "会员昵称", paramType = "query", dataType = "String")
    })
    @GetMapping
    public Result list(
            String queryMode,
            Integer page,
            Integer limit,
            String nickname
    ) {
        QueryModeEnum queryModeEnum = QueryModeEnum.getValue(queryMode);
        LambdaQueryWrapper<UmsUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(UmsUser::getDeleted, SystemConstants.DELETED_VALUE);
        switch (queryModeEnum) {
            default: // PAGE
                queryWrapper.like(StrUtil.isNotBlank(nickname), UmsUser::getNickname, nickname);
                IPage<UmsUser> result = iUmsUserService.list(new Page<>(page, limit), new UmsUser().setNickname(nickname));
                return Result.success(result.getRecords(), result.getTotal());
        }
    }

    @ApiOperation(value = "获取会员信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result getMemberById(
            @PathVariable Long id
    ) {
        UmsUser user = iUmsUserService.getById(id);
        return Result.success(user);
    }

    @ApiOperation(value = "修改会员", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资源id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "member", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsMember")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Integer id,
            @RequestBody UmsUser user) {
        boolean status = iUmsUserService.updateById(user);
        return Result.status(status);
    }


    @ApiOperation(value = "修改会员【部分更新】", httpMethod = "PATCH")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "member", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsMember")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Long id, @RequestBody UmsUser user) {
        LambdaUpdateWrapper<UmsUser> updateWrapper = new LambdaUpdateWrapper<UmsUser>().eq(UmsUser::getId, id);
        updateWrapper.set(user.getStatus() != null, UmsUser::getStatus, user.getStatus());
        boolean status = iUmsUserService.update(updateWrapper);
        return Result.status(status);
    }

    @ApiOperation(value = "修改会员积分", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "num", value = "积分数量", required = true, paramType = "query", dataType = "Integer")
    })
    @PutMapping("/{id}/point")
    public Result updatePoint(@PathVariable Long id, @RequestParam Integer num) {
        UmsUser user = iUmsUserService.getById(id);
        user.setPoint(user.getPoint() + num);
        boolean result = iUmsUserService.updateById(user);
        try {
            Thread.sleep(15 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.status(result);
    }

    @ApiOperation(value = "删除会员【逻辑删除】", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        boolean status = iUmsUserService.update(new LambdaUpdateWrapper<UmsUser>()
                .in(UmsUser::getId, Arrays.asList(ids.split(",")))
                .set(UmsUser::getDeleted, SystemConstants.DELETED_VALUE));
        return Result.status(status);
    }


    @Autowired
    private RestTemplate restTemplate;


    @Value("${zzf.CreateOrderURL}")
    private String createOrderURL;


    @Value("${zzf.FindOrderURL}")
    private String findOrderURL;

    @Value("${zzf.AppKey}")
    private String appKey;


    @Value("${zzf.AppSecret}")
    private String appSecret;


    @ApiOperation(value = "会员充值", httpMethod = "POST")
    @PostMapping("/{id}/recharge")
    public Result recharge(@PathVariable Long id) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json;charset=utf-8"));
        headers.set("Payment-Key", appKey);
        headers.set("Payment-Secret", appSecret);

        Map<String, Object> params = new HashMap<>();
        params.put("price", 0);
        params.put("name", "");
        //postMethod.addParameter("callbackurl", "用来通知指定地址");
        //postMethod.addParameter("reurl", "跳转地址");
        //postMethod.addParameter("thirduid", "用户存放您的用户ID");
        //postMethod.addParameter("remarks", "备注");
        //postMethod.addParameter("other", "其他信息");

        HttpEntity<Map> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(createOrderURL, httpEntity, String.class);
        int statusCode = responseEntity.getStatusCode().value();
        if (statusCode == HttpStatus.SC_OK) {
            String responseBody = responseEntity.getBody();
        }

        return Result.success();
    }


}
