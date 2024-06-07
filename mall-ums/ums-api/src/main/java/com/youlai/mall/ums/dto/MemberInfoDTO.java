package com.youlai.mall.ums.dto;

import lombok.Data;


/**
 * 会员传输层对象
 *
 * @author Ray
 * @since 2022/2/12
 */
@Data
public class MemberInfoDTO {

    private String nickName;

    private String avatarUrl;

    private Long balance;

}
