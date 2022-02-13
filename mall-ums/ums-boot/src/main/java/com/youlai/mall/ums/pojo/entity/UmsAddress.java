package com.youlai.mall.ums.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import lombok.Data;

/**
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/2/12 16:12
 */
@Data
public class UmsAddress extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 收货人姓名
     */
    private String consigneeName;

    /**
     * 收货人联系方式
     */
    private String consigneeMobile;

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
    private String area;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 是否默认地址(1:是；0:否)
     */
    private Integer defaulted;

}
