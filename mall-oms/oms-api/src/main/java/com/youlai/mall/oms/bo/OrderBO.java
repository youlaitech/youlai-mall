package com.youlai.mall.oms.bo;


import com.youlai.mall.oms.pojo.OmsOrder;
import com.youlai.mall.oms.pojo.OmsOrderItem;
import com.youlai.mall.ums.pojo.dto.MemberDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderBO {

    private OmsOrder order;

    private List<OmsOrderItem> orderItems;

    private MemberDTO member;

}
