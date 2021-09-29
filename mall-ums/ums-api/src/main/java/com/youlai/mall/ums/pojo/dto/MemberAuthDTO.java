package com.youlai.mall.ums.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberAuthDTO {

    private Long userId;
    private String openId;
    private Integer status;
}
