package com.youlai.mall.order.enums;


import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 审批状态枚举
 *
 * @author huawei
 * @since 2.0.0
 */
@Getter
public enum ApprovalStatusEnum implements IBaseEnum<Integer> {

    WAITING_APPROVAL(0, "等待审批"),
    APPROVED(1, "已批准"),
    REJECTED(-1, "已拒绝");

    ApprovalStatusEnum(int value, String label) {
        this.value = value;
        this.label = label;
    }

    private final Integer value;

    private final String label;
}
