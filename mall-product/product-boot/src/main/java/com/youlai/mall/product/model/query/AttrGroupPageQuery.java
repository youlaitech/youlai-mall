package com.youlai.mall.product.model.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 属性分页查询对象
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
@Schema(description ="属性分页查询对象")
@Data
public class AttrGroupPageQuery extends BasePageQuery {

    @Schema(description="关键字(属性名称/属性组名称/分类名称)")
    private String keywords;

    @Schema(description="分类ID")
    private Long categoryId;

    @Schema(description="属性组ID")
    private Long attrGroupId;

    @Schema(description="创建时间-开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String startTime;

    @Schema(description="创建时间-结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String endTime;
    
}
