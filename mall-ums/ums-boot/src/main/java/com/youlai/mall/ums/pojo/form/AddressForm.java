package com.youlai.mall.ums.pojo.form;

import com.youlai.common.constraint.CheckCityValid;
import com.youlai.common.constraint.CityType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

/**
 * 地址表单对象
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/2/12 15:57
 */
@ApiModel("地址表单对象")
@Data
public class AddressForm {

    @ApiModelProperty("地址ID")
    private Long id;

    @ApiModelProperty("收货人姓名")
    private String consigneeName;

    @ApiModelProperty("收货人手机号")
    @Pattern(regexp = "^1(3\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$", message = "{phone.valid}")
    private String consigneeMobile;

    @ApiModelProperty("省")
    @CheckCityValid(CityType.PROVINCE)
    private String province;

    @ApiModelProperty("市")
    @CheckCityValid(CityType.CITY)
    private String city;

    @ApiModelProperty("区")
    @CheckCityValid(CityType.AREA)
    private String area;

    @ApiModelProperty("详细地址")
    @Length(min = 1, max = 100, message = "{text.length.min}，{text.length.max}")
    private String detailAddress;

    @ApiModelProperty("是否默认地址(1:是;0:否)")
    private Integer defaulted;

}



