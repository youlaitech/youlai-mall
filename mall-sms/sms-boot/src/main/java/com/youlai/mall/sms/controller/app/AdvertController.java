package com.youlai.mall.sms.controller.app;

import com.youlai.common.result.Result;
import com.youlai.mall.sms.pojo.vo.AdBannerVO;
import com.youlai.mall.sms.service.SmsAdvertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "「移动端」营销广告")
@RestController
@RequestMapping("/app-api/v1/adverts")
@Slf4j
@AllArgsConstructor
public class AdvertController {

    private SmsAdvertService smsAdvertService;
    @ApiOperation(value = "广告横幅列表")
    @GetMapping("/banners")
    public Result<List<AdBannerVO>> listAdBanners() {
        List<AdBannerVO> list = smsAdvertService.listAdBanners();
        return Result.success(list);
    }
}
