package com.fly4j.yshop.ums.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.core.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UmsAddress extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="地址id",hidden=true)
    private Integer id;

    @ApiModelProperty(value="收货人名称",example="张三")
    private String name;

    @ApiModelProperty(value="用户表的用户ID",example="9527")
    private Long user_id;

    @ApiModelProperty(value="行政区域表的省ID",example="110000")
    private String province;

    @ApiModelProperty(value="行政区域表的市ID",example="110100")
    private String city;

    @ApiModelProperty(value="行政区域表的区县ID",example="110101")
    private String area;

    @ApiModelProperty(value="详细收货地址",example="北京市东城区")
    private String address_detail;

    @ApiModelProperty(value="地区编码",example="110101")
    private String area_code;

    @ApiModelProperty(value="邮政编码",example="100010")
    private String zip_code;

    @ApiModelProperty(value="手机号码",example="18856887888")
    private String mobile;

    @ApiModelProperty(value="是否默认地址",example="1")
    private Integer is_default;
}
