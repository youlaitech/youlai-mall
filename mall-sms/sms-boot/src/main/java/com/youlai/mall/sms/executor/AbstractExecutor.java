package com.youlai.mall.sms.executor;

import cn.hutool.core.collection.CollUtil;
import com.youlai.mall.sms.pojo.vo.SettlementInfoVO;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xinyi
 * @desc：规则执行器抽象类，定义通用方法
 * @date 2021/7/11
 */
public class AbstractExecutor {

    /**
     * 校验商品类型与优惠券是匹配
     * 1、这里实现的是但品类优惠券校验，多品类优惠券自行实现
     * 2、商品只需要有一个优惠券要求的商品类型进行匹配
     *
     * @return
     */
    protected boolean isGoodsTypeSatisfy(SettlementInfoVO settlement) {
        List<String> goodsType = settlement.getGoodsInfos().stream().map(SettlementInfoVO.GoodsInfo::getType).collect(Collectors.toList());
        List<String> templateGoodsType = settlement.getCouponAndTemplateInfos().get(0).getTemplate().getRule().getGoodsCategories();
        if (CollUtil.isEmpty(templateGoodsType)){
            return true;
        }
        return CollectionUtils.isNotEmpty(CollectionUtils.intersection(goodsType, templateGoodsType));
    }

    /**
     * 处理商品类型与优惠券限制不匹配情况
     * @param settlement 用户传递的结算信息
     * @param goodsSum 商品总价
     * @return 返回已经修改过的结算信息
     */
    protected SettlementInfoVO processGoodsTypeNotSatisfy(SettlementInfoVO settlement,Long goodsSum){
        boolean isGoodsTypeSatisfy = isGoodsTypeSatisfy(settlement);
        if (!isGoodsTypeSatisfy){
            // 当商品类型不满足时，直接返回总价，并清空优惠券
            settlement.setPay(goodsSum);
            settlement.setCouponAndTemplateInfos(CollUtil.empty(List.class));
        }
        return null;
    }

    /**
     * 校验当前两张优惠券是否可以够用
     * 即校验 TemplateRule 中的 weight 是否满足条件
     *
     * @param c1
     * @param c2
     * @return
     */
    protected boolean isTemplateCanShared(SettlementInfoVO.CouponAndTemplateInfo c1, SettlementInfoVO.CouponAndTemplateInfo c2) {
        String c1Key = c1.getTemplate().getCode() + String.format("%04d", c1.getTemplate().getId());
        String c2Key = c2.getTemplate().getCode() + String.format("%04d", c2.getTemplate().getId());

        List<String> allSharedKeysForC1 = new ArrayList<>();
        allSharedKeysForC1.add(c1Key);
//        allSharedKeysForC1.addAll(JSON.parseObject(c1.getTemplate().getRule().getWeight(), List.class));

        List<String> allSharedKeysForC2 = new ArrayList<>();
        allSharedKeysForC2.add(c2Key);
//        allSharedKeysForC2.addAll(JSON.parseObject(c2.getTemplate().getRule().getWeight(), List.class));

        return CollectionUtils.isSubCollection(Arrays.asList(c1Key, c2Key), allSharedKeysForC1)
                || CollectionUtils.isSubCollection(Arrays.asList(c1Key, c2Key), allSharedKeysForC2);

    }

    /**
     * 计算商品总价
     * @param goodsInfos
     * @return
     */
    protected Long goodsCostSum(List<SettlementInfoVO.GoodsInfo> goodsInfos){
        return goodsInfos.stream().mapToLong(g -> g.getCount()*g.getCount()).sum();
    }

    /**
     * 最小支付费用(1分钱)
     * @return
     */
    protected Long minCost(){
        return 0L;
    }



}
