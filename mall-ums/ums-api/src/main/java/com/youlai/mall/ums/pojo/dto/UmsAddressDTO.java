package com.youlai.mall.ums.pojo.dto;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
public class UmsAddressDTO implements Serializable {

    private Long id;

    private Long userId;

    private String name;

    private String mobile;

    private String province;

    private String city;

    private String area;

    private String address;

    private String zipCode;

    private Integer defaulted;
}
