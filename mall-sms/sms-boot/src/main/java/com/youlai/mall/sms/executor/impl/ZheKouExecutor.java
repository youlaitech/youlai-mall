package com.youlai.mall.sms.executor.impl;

import cn.hutool.core.collection.CollUtil;
import com.youlai.mall.sms.executor.AbstractExecutor;
import com.youlai.mall.sms.executor.RuleExecutor;
import com.youlai.mall.sms.pojo.enums.RuleFlagEnum;
import com.youlai.mall.sms.pojo.vo.CouponTemplateVO;
import com.youlai.mall.sms.pojo.vo.SettlementInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xinyi
 * @desc: 折扣优惠券结算规则执行器
 * @date 2021/7/11
 */
@Slf4j
@Component
public class ZheKouExecutor extends AbstractExecutor implements RuleExecutor {
    @Override
    public RuleFlagEnum ruleConfig() {
        return RuleFlagEnum.ZHEKOU;
    }

    @Override
    public SettlementInfoVO computeRule(SettlementInfoVO settlement) {
        Long goodsCostSum = goodsCostSum(settlement.getGoodsInfos());
        // 判断商品类型与优惠券是否满足条件
        SettlementInfoVO probability = processGoodsTypeNotSatisfy(settlement, goodsCostSum);
        if (probability != null) {
            log.debug("ZheKou Coupon Is Not Match To GoodsType.");
            return probability;
        }

        CouponTemplateVO template = settlement.getCouponAndTemplateInfos().get(0).getTemplate();
        Long base = template.getRule().getDiscount().getBase();
        double quota = template.getRule().getDiscount().getQuota();
        if (goodsCostSum < base) {
            log.debug("Current Goods Cost Sum < ZheKou Coupon Base.");
            settlement.setPay(goodsCostSum);
            settlement.setCouponAndTemplateInfos(CollUtil.empty(List.class));
            return settlement;
        }
        //计算使用优惠券之后的价格
        Long  pay = Long.parseLong(goodsCostSum * (quota * 1.0 / 100) + "");
        settlement.setPay(pay > minCost() ? pay : minCost());

        log.debug("Use ZheKou Coupon Make Goods Cost From {} To {}",goodsCostSum,settlement.getPay());
        return settlement;
    }
}
