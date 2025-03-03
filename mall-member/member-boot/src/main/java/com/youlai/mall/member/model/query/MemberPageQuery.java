package com.youlai.mall.member.model.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户分页查询对象
 *
 * @author Ray.Hao
 * @since 2022/1/14
 */
@EqualsAndHashCode(callSuper = false)
@Schema(description = "会员分页查询对象")
@Data
public class MemberPageQuery extends BasePageQuery {

    @Schema(description="关键字(会员名称/手机号)")
    private String keywords;

}
