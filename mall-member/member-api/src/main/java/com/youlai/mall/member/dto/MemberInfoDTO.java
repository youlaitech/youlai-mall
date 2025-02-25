package com.youlai.mall.member.dto;

import lombok.Data;


/**
 * 会员传输层对象
 *
 * @author Ray.Hao
 * @since 2022/2/12
 */
@Data
public class MemberInfoDTO {

    private String nickName;

    private String avatarUrl;

    private Long balance;

}
