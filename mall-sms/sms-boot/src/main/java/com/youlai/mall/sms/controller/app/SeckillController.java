package com.youlai.mall.sms.controller.app;

import com.youlai.common.result.Result;
import com.youlai.mall.sms.pojo.vo.SmsSeckillSkuVO;
import com.youlai.mall.sms.service.ISeckillService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huawei
 * @desc 秒杀活动管理
 * @email huawei_code@163.com
 * @date 2021/3/7
 */
@Api(tags = "「移动端」秒杀活动管理")
@RestController("APPSeckillController")
@RequestMapping("/api-app/v1/seckill")
@Slf4j
public class SeckillController {

    @Autowired
    private ISeckillService ISeckillService;

    @GetMapping
    public Result getCurrentSeckillSession() {
        List<SmsSeckillSkuVO> currentSeckills = ISeckillService.getCurrentSeckillSession();
        return Result.success();
    }
}
