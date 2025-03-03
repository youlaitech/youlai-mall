package com.youlai.system.model.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 系统配置查询对象
 *
 * @author Theo
 * @since 2024-7-29 11:38:00
 */
@Schema(description = "系统配置分页查询")
@EqualsAndHashCode(callSuper = false)
@Data
public class ConfigPageQuery extends BasePageQuery {

    @Schema(description="关键字(配置项名称/配置项值)")
    private String keywords;
}
