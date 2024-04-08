package com.youlai.mall.ums.model.vo;

import com.youlai.mall.ums.model.bo.AddressBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * 会员分页对象
 *
 * @author Ray Hao
 * @since 2022/2/12
 */
@Schema(description = "会员分页对象")
@Data
public class MemberPageVO  {
    @Schema(description = "会员ID")
    private Long id;

    @Schema(description = "性别：1男，2女，0未知")
    private Integer gender;

    @Schema(description = "性别文本")
    private String  genderLabel;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "手机号码")
    private String mobile;

    @Schema(description = "生日")
    private LocalDate birthday;

    @Schema(description = "头像URL")
    private String avatarUrl;

    @Schema(description = "所在城市")
    private String city;

    @Schema(description = "所在国家")
    private String country;

    @Schema(description = "语言")
    private String language;

    @Schema(description = "所在省份")
    private String province;

    @Schema(description = "会员状态：1正常，0禁用")
    private Integer status;

    @Schema(description = "账户余额")
    private Long balance;

    @Schema(description = "地址列表")
    private List<AddressBO> addresses;

}
