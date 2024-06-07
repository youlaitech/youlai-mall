package com.youlai.mall.product.model.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 品牌分页列表查询对象
 *
 * @author Ray
 * @since 2021/7/11
 */
@Schema(description = "品牌分页查询对象")
@Data
public class BrandPageQuery extends BasePageQuery {

    @Schema(description="关键字")
    private String keywords;

}
