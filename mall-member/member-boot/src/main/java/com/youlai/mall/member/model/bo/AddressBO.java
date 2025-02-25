package com.youlai.mall.member.model.bo;

import lombok.Data;

/**
 * 地址业务对象
 *
 * @author Ray.Hao
 * @since 2024/4/8
 */
@Data
public class AddressBO {

    /**
     * 地址ID
     */
    private Long id;

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 收货人姓名
     */
    private String recipientName ;

    /**
     * 收货人电话
     */
    private String recipientMobile;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 街道地址
     */
    private String street;

    /**
     * 邮编
     */
    private String postalCode;

    /**
     * 是否默认地址(1:是；0:否)
     */
    private Integer isDefault;
}
