package com.youlai.mall.sms.executor;

import com.youlai.common.web.exception.BizException;
import com.youlai.mall.sms.pojo.enums.CouponCategoryEnum;
import com.youlai.mall.sms.pojo.enums.RuleFlagEnum;
import com.youlai.mall.sms.pojo.vo.SettlementInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xinyi
 * @desc: 优惠券结算规则执行管理器
 * @date 2021/7/13
 */
@Slf4j
@Component
public class ExecutorManager implements BeanPostProcessor {

    /**
     * 规则执行器映射
     */
    private static Map<RuleFlagEnum, RuleExecutor> executorIndex = new HashMap<>(RuleFlagEnum.values().length);

    public SettlementInfoVO computeRule(SettlementInfoVO settlement){
        SettlementInfoVO result = null;

        // 单类优惠券
        if (settlement.getCouponAndTemplateInfos().size() == 1){
            CouponCategoryEnum category = settlement.getCouponAndTemplateInfos().get(0).getTemplate().getCategory();
            switch (category){
                case MANJIAN:
                    settlement = executorIndex.get(RuleFlagEnum.MANJIAN).computeRule(settlement);
                    break;
                case LIJIAN:
                    settlement = executorIndex.get(RuleFlagEnum.LIJIAN).computeRule(settlement);
                    break;
                case ZHEKOU:
                    settlement = executorIndex.get(RuleFlagEnum.ZHEKOU).computeRule(settlement);
                    break;
            }
        }else {
            List<CouponCategoryEnum> categories = settlement.getCouponAndTemplateInfos().stream().map(obj -> {
                return obj.getTemplate().getCategory();
            }).collect(Collectors.toList());
            if (categories.size() > 2){
                throw new BizException("Not Support Form More Template Category");
            }
            if (categories.contains(CouponCategoryEnum.MANJIAN) && categories.contains(CouponCategoryEnum.ZHEKOU)){
                settlement = executorIndex.get(RuleFlagEnum.MANJIAN_ZHEKOU).computeRule(settlement);
            }else {
                throw new BizException("Not Support Form Other Template Category");
            }
        }

        return result;
    }



    /**
     * 在bean初始化之前去执行
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        if (!(bean instanceof RuleExecutor)) {
            return bean;
        }
        RuleExecutor executor = (RuleExecutor) bean;
        RuleFlagEnum ruleFlag = executor.ruleConfig();
        if (executorIndex.containsKey(ruleFlag)) {
            throw new IllegalStateException("There is already an executor for rule flag: " + ruleFlag);
        }
        log.info("Load executor {} for rule flag {}.", executor.getClass(), ruleFlag);
        executorIndex.put(ruleFlag, executor);
        return bean;
    }

    /**
     * 在bean初始化之后去执行
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }
}
