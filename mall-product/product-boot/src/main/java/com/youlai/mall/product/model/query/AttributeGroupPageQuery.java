package com.youlai.mall.product.model.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.youlai.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 属性组分页查询对象
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
@Schema(description ="属性组分页查询对象")
@Data
public class AttributeGroupPageQuery extends BasePageQuery {

    @Schema(description="关键字")
    private String keywords;

    @Schema(description="创建时间-开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String startTime;

    @Schema(description="创建时间-结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String endTime;


}
