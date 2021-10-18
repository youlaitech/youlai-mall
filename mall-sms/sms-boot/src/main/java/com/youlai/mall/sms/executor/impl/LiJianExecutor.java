package com.youlai.mall.sms.executor.impl;

import com.youlai.mall.sms.executor.AbstractExecutor;
import com.youlai.mall.sms.executor.RuleExecutor;
import com.youlai.mall.sms.pojo.enums.RuleFlagEnum;
import com.youlai.mall.sms.pojo.vo.CouponTemplateVO;
import com.youlai.mall.sms.pojo.vo.SettlementInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author xinyi
 * @desc: 立减优惠券结算规则执行器
 * @date 2021/7/11
 */
@Slf4j
@Component
public class LiJianExecutor extends AbstractExecutor implements RuleExecutor {
    @Override
    public RuleFlagEnum ruleConfig() {
        return RuleFlagEnum.LIJIAN;
    }

    @Override
    public SettlementInfoVO computeRule(SettlementInfoVO settlement) {
        Long goodsCostSum = goodsCostSum(settlement.getGoodsInfos());
        // 判断商品类型与优惠券是否满足条件
        SettlementInfoVO probability = processGoodsTypeNotSatisfy(settlement, goodsCostSum);
        if (probability != null) {
            log.debug("LiJian Coupon Is Not Match To GoodsType.");
            return probability;
        }
        CouponTemplateVO template = settlement.getCouponAndTemplateInfos().get(0).getTemplate();
        Long quota = template.getRule().getDiscount().getQuota();
        //计算使用优惠券之后的价格
        Long pay = goodsCostSum - quota;
        settlement.setPay(pay > minCost() ? pay : minCost());
        log.debug("Use LiJian Coupon Make Goods Cost From {} To {}",goodsCostSum,settlement.getPay());
        return settlement;
    }
}
