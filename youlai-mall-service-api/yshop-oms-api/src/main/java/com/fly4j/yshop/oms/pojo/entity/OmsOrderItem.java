package com.fly4j.yshop.oms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class OmsOrderItem extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(hidden = true)
    private Long id;
    @ApiModelProperty(hidden = true)
    private Long order_id;
    @ApiModelProperty(hidden = true)
    private String order_sn;

    @ApiModelProperty(value = "商品ID",example="1250430782352474113")
    private Long spu_id;

    @ApiModelProperty(value = "商品名称",example="小米手机5")
    private String spu_name;

    @ApiModelProperty(value = "品牌ID",example="1")
    private String spu_brand;

    @ApiModelProperty(value = "分类ID",example="1249374117918228482")
    private Long spu_category_id;

    @ApiModelProperty(value = "库存ID",example="1250430784122470401")
    private Long sku_id;

    @ApiModelProperty(value = "sku价格",example="1799.00")
    private BigDecimal sku_price;

    @ApiModelProperty(value = "sku销量",example="100")
    private Integer sku_quantity;

    @ApiModelProperty(value = "sku规格",example="['白色', '3G+32G']")
    private String sku_specs;

    @ApiModelProperty(value = "sku图片地址",example="http://101.37.69.49/group1/M00/00/02/rBACvV6XFwiAAxiSAADUyQY7C5A313.jpg")
    private String pic_url;
}
