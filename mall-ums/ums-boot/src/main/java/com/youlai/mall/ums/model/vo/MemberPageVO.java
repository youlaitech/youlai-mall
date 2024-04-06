package com.youlai.mall.ums.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.youlai.mall.ums.model.entity.UmsAddress;
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
    @Schema(description="会员ID")
    private Long id;

    private Integer gender;

    private String nickName;

    private String mobile;

    private LocalDate birthday;

    private String avatarUrl;

    private String city;

    private String country;

    private String language;

    private String province;

    private Integer status;

    private Long balance;

    private List<UmsAddress> addressList;

}
