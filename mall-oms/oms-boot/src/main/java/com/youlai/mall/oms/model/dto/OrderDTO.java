package com.youlai.mall.oms.model.dto;

import com.youlai.mall.oms.model.entity.OmsOrder;
import com.youlai.mall.oms.model.entity.OmsOrderItem;
import com.youlai.mall.ums.dto.MemberDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author huawei
 * @desc
 * @email huawei_code@163.com
 * @since 2021/1/19
 */
@Data
@Accessors(chain = true)
public class OrderDTO {

    private OmsOrder order;

    private List<OmsOrderItem> orderItems;

    private MemberDTO member;

}
