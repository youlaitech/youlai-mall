package com.youlai.mall.ums.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员地址传输层对象
 *
 * @author haoxr
 * @since 2022/2/12 15:57
 */
@Data
public class MemberAddressDTO {

    private Long id;

    private Long memberId;

    @ApiModelProperty("收货人姓名")
    private String consigneeName;

    @ApiModelProperty("收货人手机号")
    private String consigneeMobile;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区域")
    private String area;

    @ApiModelProperty("详细地址")
    private String detailAddress;

    @ApiModelProperty("是否默认地址（1:是;0:否）")
    private Integer defaulted;

}



