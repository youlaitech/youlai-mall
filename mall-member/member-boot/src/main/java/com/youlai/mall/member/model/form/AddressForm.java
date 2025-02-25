package com.youlai.mall.member.model.form;

import com.youlai.common.core.annotation.validation.ValidCity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Pattern;

/**
 * 地址表单对象
 *
 * @author Ray.Hao
 * @since 2022/2/12
 */
@Schema(description = "地址表单对象")
@Data
public class AddressForm {

    @Schema(description = "地址ID")
    private Long id;

    @Schema(description = "收货人姓名")
    private String recipientName;

    @Schema(description = "收货人手机号")
    @Pattern(regexp = "^1(3\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$", message = "{phone.valid}")
    private String recipientMobile;

    @Schema(description = "省")
    @ValidCity(CityType.PROVINCE)
    private String province;

    @Schema(description = "市")
    @ValidCity(CityType.CITY)
    private String city;

    @Schema(description = "区")
    @ValidCity(CityType.DISTRICT)
    private String district;

    @Schema(description = "街道地址")
    @Length(min = 1, max = 100, message = "{text.length.min}，{text.length.max}")
    private String street;

    @Schema(description = "是否默认地址(1:是;0:否)")
    private Integer isDefault;

}



