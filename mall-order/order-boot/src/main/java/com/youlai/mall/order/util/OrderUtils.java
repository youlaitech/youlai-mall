package com.youlai.mall.order.util;

import com.youlai.mall.order.enums.OrderTypeEnum;

/**
 * 订单工具类
 *
 * @author haoxr
 * @since 2024/6/7
 */
public class OrderUtils {

    private OrderUtils() {
        // 私有构造方法，防止实例化
    }
    /**
     * 生成商户订单号
     *
     * @param memberId  会员ID
     * @param orderType 订单类型枚举
     * @return 生成的订单号
     */
    public static String generateTradeNo(Long memberId, OrderTypeEnum orderType) {
        // 时间戳简化处理，只取后10位
        String timestamp = Long.toString(System.currentTimeMillis()).substring(3);
        // 使用4位随机数，或者更高位数的字母数字组合
        String randomDigits = randomAlphanumeric(4);
        // 用户ID进行哈希处理，并取固定长度，例如8位
        String hashedUserId = String.format("%08d", memberId.hashCode() & 0xFFFFFFF);
        // 组合生成订单号
        return orderType.getValue() + timestamp + randomDigits + hashedUserId;
    }


    /**
     * 生成随机字母数字组合
     *
     * @param count 生成的长度
     * @return 随机字母数字组合
     */
    private static String randomAlphanumeric(int count) {
        // 生成随机字母数字组合的逻辑
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(candidateChars.charAt((int) (Math.random() * candidateChars.length())));
        }
        return sb.toString();
    }

}
