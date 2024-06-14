package com.youlai.mall.member.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 会员视图层对象
 *
 * @author <a href="mailto:xianrui0365@163.com">Ray</a>
 * @since 2022/2/12 21:13
 */
@Schema(description = "会员视图层对象")
@Data
public class MemberDTO {

    @Schema(description="会员ID")
    private Long id;

    @Schema(description="会员昵称")
    private String nickName;

    @Schema(description="会员头像地址")
    private String avatarUrl;

    @Schema(description="会员手机号")
    private String mobile;

    @Schema(description="会员余额(单位:分)")
    private Long balance;

}
