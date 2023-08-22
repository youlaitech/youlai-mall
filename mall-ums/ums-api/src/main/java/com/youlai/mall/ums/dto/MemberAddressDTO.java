package com.youlai.mall.ums.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员地址传输层对象
 *
 * @author haoxr
 * @date 2022/2/12 15:57
 */
@Data
public class MemberAddressDTO {

    private Long id;

    private Long memberId;

    private String consigneeName;

    private String consigneeMobile;

    private String province;

    private String city;

    private String area;

    private String detailAddress;

    @ApiModelProperty("是否默认(1:是;0:否)")
    private Integer defaulted;

}



