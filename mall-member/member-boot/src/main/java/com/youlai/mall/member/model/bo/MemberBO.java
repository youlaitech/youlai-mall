package com.youlai.mall.member.model.bo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * 会员业务对象
 *
 * @author Ray Hao
 * @since 2022/2/12
 */
@Data
public class MemberBO {

    /**
     * 会员唯一标识ID
     */
    private Long id;

    /**
     * 性别（0：未知，1：男，2：女）
     */
    private Integer gender;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 头像URL
     */
    private String avatarUrl;

    /**
     * 城市
     */
    private String city;

    /**
     * 国家
     */
    private String country;

    /**
     * 语言
     */
    private String language;

    /**
     * 省份
     */
    private String province;

    /**
     * 状态（比如：激活，暂停等）
     */
    private Integer status;

    /**
     * 余额
     */
    private Long balance;

    /**
     * 地址列表
     */
    private List<AddressBO> addresses;

}