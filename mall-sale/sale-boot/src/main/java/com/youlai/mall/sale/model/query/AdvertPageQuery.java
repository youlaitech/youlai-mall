package com.youlai.mall.sale.model.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 广告分页列表查询对象
 *
 * @author Ray.Hao
 * @since 2021/7/11
 */
@EqualsAndHashCode(callSuper = false)
@Schema(description = "广告分页查询对象")
@Data
public class AdvertPageQuery extends BasePageQuery {

    @Schema(description="关键字")
    private String keywords;

}
