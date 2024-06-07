package com.youlai.system.model.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限分页查询对象
 *
 * @author Ray
 * @since 2022/1/14
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "权限分页查询对象")
@Data
public class PermPageQuery extends BasePageQuery {

    @Schema(description="权限名称")
    private String name;

    @Schema(description="菜单ID")
    private Long menuId;

}
