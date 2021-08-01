package com.youlai.mall.sms.shedule;

import cn.hutool.core.collection.CollUtil;
import com.youlai.mall.sms.pojo.domain.SmsCouponTemplate;
import com.youlai.mall.sms.pojo.enums.CouponOfferStateEnum;
import com.youlai.mall.sms.pojo.enums.CouponUsedStateEnum;
import com.youlai.mall.sms.pojo.enums.CouponVerifyStateEnum;
import com.youlai.mall.sms.service.ISmsCouponTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xinyi
 * @desc：定时清理已过期的优惠券模板
 * @date 2021/7/3
 */
@Slf4j
@Component
public class ScheduleTask {

    @Autowired
    private ISmsCouponTemplateService couponTemplateService;

    /**
     * 更新所有优惠券发放状态，使用状态
     */
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void offlineCouponTemplate() {
        log.info("Start To Expired CouponTemplate.");
        // 查询所有已审核，使用状态未结束的优惠券模板
        List<SmsCouponTemplate> templates = couponTemplateService.findAllNotExpiredTemplate(CouponVerifyStateEnum.VERIFY.getCode(),CouponUsedStateEnum.FINISH.getCode());
        if (CollUtil.isEmpty(templates)) {
            log.info("Done To Expired CouponTemplate.");
            return;
        }

        Long nowTime = System.currentTimeMillis();
        List<SmsCouponTemplate> expiredTemplates = new ArrayList<>(templates.size());
        for (SmsCouponTemplate template : templates) {
            if (template.getOfferState().getCode() < CouponOfferStateEnum.FINISH.getCode()) {
                if (nowTime.compareTo(template.getOfferEndTime()) > 0) {
                    template.setOfferState(CouponOfferStateEnum.FINISH);
                } else if (nowTime.compareTo(template.getOfferStartTime()) > 0) {
                    template.setOfferState(CouponOfferStateEnum.GOING);
                    // TODO 发送rabbitmq消息，批量生成优惠券码，发放优惠券
                }
            }

            if (template.getUsedState().getCode() < CouponUsedStateEnum.FINISH.getCode()) {
                if (nowTime.compareTo(template.getUsedEndTime()) > 0) {
                    template.setUsedState(CouponUsedStateEnum.FINISH);
                    // TODO 发送rabbitmq消息，批量更新用户已领取未使用优惠券状态为已过期
                } else if (nowTime.compareTo(template.getUsedStartTime()) > 0) {
                    template.setUsedState(CouponUsedStateEnum.GOING);
                }
            }
        }

        couponTemplateService.updateBatchById(expiredTemplates);
        log.info("Update Expired CouponTemplate Num:{}", expiredTemplates.size());
        log.info("Done To Expired CouponTemplate.");
    }
}
