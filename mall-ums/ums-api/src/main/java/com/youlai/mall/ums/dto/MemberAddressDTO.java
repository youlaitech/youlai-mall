package com.youlai.mall.ums.dto;

import com.youlai.common.constraint.CheckCityValid;
import com.youlai.common.constraint.CityType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

/**
 * 会员地址传输层对象
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/2/12 15:57
 */
@Data
public class MemberAddressDTO {

    @NotNull(message = "{id.positive}")
    @Positive(message = "{id.positive}")
    private Long memberId;

    private String consigneeName;

    @Pattern(regexp = "^1(3\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$", message = "{phone.valid}")
    private String consigneeMobile;

    @CheckCityValid(CityType.PROVINCE)
    private String province;

    @CheckCityValid(CityType.CITY)
    private String city;

    @CheckCityValid(CityType.AREA)
    private String area;

    @Length(min = 1, max = 100, message = "{text.length.min}，{text.length.max}")
    private String detailAddress;

    private Integer defaulted;

}



