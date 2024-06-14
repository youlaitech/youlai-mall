package com.youlai.mall.member.model.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.youlai.common.base.BaseEntity;
import lombok.Data;

import java.time.LocalDate;

/**
 * 会员实体类
 *
 * @author Ray Hao
 * @since 2022/2/12
 */
@Data
public class Member extends BaseEntity {

    /**
     * 会员性别
     * 1 - 男性
     * 2 - 女性
     * 0 - 未知
     */
    private Integer gender;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 会员生日
     */
    private LocalDate birthday;

    /**
     * 头像的URL地址
     */
    private String avatarUrl;

    /**
     * 会员的微信OpenID
     * 用于微信平台识别用户
     */
    private String openid;

    /**
     * 会员的微信会话密钥
     * 用于解密微信数据
     */
    private String sessionKey;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 所在国家
     */
    private String country;

    /**
     * 语言
     */
    private String language;

    /**
     * 所在省份
     */
    private String province;

    /**
     * 会员状态：1 - 启用，0 - 禁用
     */
    private Integer status;

    /**
     * 账户余额
     */
    private Long balance;

    /**
     * 是否删除的标志
     * 0 - 未删除，1 - 已删除
     */
    @TableLogic(delval = "1", value = "0")
    private Integer isDeleted;

    /**
     * 积分
     */
    private Integer point;

}
