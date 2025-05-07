package com.youlai.mall.member.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 会员地址实体类
 *
 * @author Ray.Hao
 * @since 2022/2/12
 */
@TableName("ums_address")
@Getter
@Setter
public class AddressEntity extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 收货人姓名
     */
    private String recipientName;

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
     * 详细地址
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
