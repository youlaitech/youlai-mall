package com.youlai.mall.sms.model.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 广告分页列表查询对象
 *
 * @author haoxr
 * @since 2021/7/11
 */
@Schema(description = "广告分页查询对象")
@Data
public class AdvertPageQuery extends BasePageQuery {

    @Schema(description="关键字")
    private String keywords;

}
