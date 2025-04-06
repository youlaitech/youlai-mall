package com.youlai.system.model.query;


import com.youlai.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description ="字典项分页查询对象")
public class DictItemPageQuery extends BasePageQuery {

    @Schema(description="关键字(字典项值/字典项名称)")
    private String keywords;

    @Schema(description="字典编码")
    private String dictCode;

}
