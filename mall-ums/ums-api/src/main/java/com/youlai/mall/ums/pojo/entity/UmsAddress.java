package com.youlai.mall.ums.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import com.youlai.common.constraints.CheckCityValid;
import com.youlai.common.constraints.CityType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UmsAddress extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    // @NotNull(message = "{id.positive}")
    // @Positive(message = "{id.positive}")
    private Long memberId;

    @Length(min = 2, max = 8, message = "{text.length.min}，{text.length.max}")
    private String name;

    @Pattern(regexp = "^1(3\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$", message = "{phone.valid}")
    private String mobile;

    @CheckCityValid(CityType.PROVINCE)
    private String province;

    @CheckCityValid(CityType.CITY)
    private String city;

    @CheckCityValid(CityType.AREA)
    private String area;

    @Length(min = 1, max = 100, message = "{text.length.min}，{text.length.max}")
    private String address;

    @Pattern(regexp = "^[0-9]{6}$", message = "{zipcode.valid}")
    private String zipCode;

    private Integer defaulted;
}
