package com.youlai.system.pojo.query;


import com.youlai.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("字典类型分页查询对象")
@Data
public class DictTypePageQuery extends BasePageQuery {

    @ApiModelProperty("关键字(类型名称/类型编码)")
    private String keywords;

}
