package com.youlai.mall.oms.pojo.vo;

import com.youlai.common.base.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author huawei
 * @desc
 * @email huawei_code@163.com
 * @date 2021/2/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListVO extends BaseVO {

    /**
     * time : 2019-04-06 11:37
     * state : 9
     * goodsList : [{"title":"古黛妃 短袖t恤女 春夏装2019新款韩版宽松","price":179.5,"image":"https://img13.360buyimg.com/n8/jfs/t1/30343/20/1029/481370/5c449438Ecb46a15b/2b2adccb6dc742fd.jpg","number":1,"attr":"珊瑚粉 M"}]
     */

    private Long id;
    private String orderSn;
    private Long totalAmount;
    private Long payAmount;
    private Date gmtCreate;

    private Integer totalQuantity;
    private String time;
    private Integer status;
    private String statusDesc;
    private List<OrderItemBean> orderItemLIst;

    @Data
    public static class OrderItemBean extends BaseVO {

        /**
         * title : 古黛妃 短袖t恤女 春夏装2019新款韩版宽松
         * price : 179.5
         * image : https://img13.360buyimg.com/n8/jfs/t1/30343/20/1029/481370/5c449438Ecb46a15b/2b2adccb6dc742fd.jpg
         * number : 1
         * attr : 珊瑚粉 M
         */
        private Long id;
        private Long orderId;
        private String skuId;
        private String skuPic;
        private Integer skuQuantity;
        private Long skuTotalPrice;
        private String title;
        private Long price;
        private String image;
        private Integer number;
        private String attr;


    }
}
