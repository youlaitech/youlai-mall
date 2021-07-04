package com.youlai.mall.sms.shedule;

import cn.hutool.core.collection.CollUtil;
import com.youlai.mall.sms.pojo.domain.SmsCouponTemplate;
import com.youlai.mall.sms.pojo.vo.TemplateRuleVO;
import com.youlai.mall.sms.service.ISmsCouponTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
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
     * 下线已过期的优惠券模板
     */
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void offlineCouponTemplate() {
        log.info("Start To Expired CouponTemplate.");
        // 查询未过期的优惠券模板
        List<SmsCouponTemplate> templates = couponTemplateService.findAllNotExpiredTemplate(1);
        if (CollUtil.isEmpty(templates)) {
            log.info("Done To Expired CouponTemplate.");
            return;
        }

        Date nowTime = new Date();
        List<SmsCouponTemplate> expiredTemplates = new ArrayList<>(templates.size());
        for (SmsCouponTemplate template : templates) {
            TemplateRuleVO rule = template.getRule();
            if (rule.getExpiration().getDeadline() < nowTime.getTime()) {
                template.setExpired(0);
                expiredTemplates.add(template);
            }
        }
        couponTemplateService.updateBatchById(expiredTemplates);
        log.info("Update Expired CouponTemplate Num:{}", expiredTemplates.size());
        log.info("Done To Expired CouponTemplate.");
    }
}
