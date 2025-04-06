package com.youlai.system.model.query;

import cn.hutool.db.sql.Direction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.youlai.common.base.BasePageQuery;
import com.youlai.common.core.annotation.validation.ValidField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户分页查询对象
 *
 * @author haoxr
 * @since 2022/1/14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户分页查询对象")
public class UserPageQuery extends BasePageQuery {

    @Schema(description = "关键字(用户名/昵称/手机号)")
    private String keywords;

    @Schema(description = "用户状态")
    private Integer status;

    @Schema(description = "部门ID")
    private Long deptId;

    @Schema(description = "角色ID")
    private List<Long> roleIds;

    @Schema(description = "创建时间范围")
    private List<String> createTime;

    @Schema(description = "排序的字段")
    @ValidField(allowedValues = {"create_time", "update_time"})
    private String field;

    @Schema(description = "排序方式（正序:ASC；反序:DESC）")
    private Direction direction;

    /**
     * 是否超级管理员
     */
    @JsonIgnore
    @Schema(hidden = true)
    private Boolean isRoot;

}
