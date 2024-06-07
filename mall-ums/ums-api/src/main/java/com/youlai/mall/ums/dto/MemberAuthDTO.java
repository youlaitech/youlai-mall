package com.youlai.mall.ums.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员信息
 *
 * @author Ray
 * @since 2022/2/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberAuthDTO {

    /**
     * 会员ID
     */
    private Long id;

    /**
     * 会员名(openId、mobile)
     */
    private String username;

    /**
     * 状态(1:正常；0：禁用)
     */
    private Integer status;
}
