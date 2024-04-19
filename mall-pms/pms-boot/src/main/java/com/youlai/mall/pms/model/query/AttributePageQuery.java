package com.youlai.mall.pms.model.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 属性分页查询对象
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
@Schema(description ="属性分页查询对象")
@Data
public class AttributePageQuery extends BasePageQuery {

    @Schema(description="关键字")
    private String keywords;

}
