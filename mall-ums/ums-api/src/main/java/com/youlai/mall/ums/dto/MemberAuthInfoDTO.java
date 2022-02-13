package com.youlai.mall.ums.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 会员认证传输层对象
 *
 * @author haoxr
 * @date 2022/2/12
 */
@Data
@Accessors(chain = true)
public class MemberAuthInfoDTO {

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 会员名(openId、mobile)
     */
    private String username;

    /**
     * 状态(1:正常；0：禁用)
     */
    private Integer status;
}
