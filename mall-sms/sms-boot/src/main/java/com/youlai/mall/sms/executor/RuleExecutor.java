package com.youlai.mall.sms.executor;

import com.youlai.mall.sms.pojo.enums.RuleFlagEnum;
import com.youlai.mall.sms.pojo.vo.SettlementInfoVO;

/**
 * @author xinyi
 * @desc: 优惠券模板规则处理器接口
 * @date 2021/7/11
 */
public interface RuleExecutor {

    /**
     * 规则类型标记
     * @return {@RuleFlagEnum}
     */
    RuleFlagEnum ruleConfig();

    /**
     * 优惠券规则计算
     * @param settlement
     * @return
     */
    SettlementInfoVO computeRule(SettlementInfoVO settlement);
}
