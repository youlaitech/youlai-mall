package com.youlai.mall.ums.dto;

import lombok.Data;

/**
 * 会员地址传输层对象
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
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

    private Integer defaulted;

}



