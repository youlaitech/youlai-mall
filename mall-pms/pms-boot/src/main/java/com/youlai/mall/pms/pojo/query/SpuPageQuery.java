package com.youlai.mall.pms.pojo.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  商品分页查询对象
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/2/5 13:09
 */

@ApiModel("商品分页查询对象")
@Data
public class SpuPageQuery extends BasePageQuery {

    @ApiModelProperty("关键字")
    private String keywords;

    @ApiModelProperty("商品分类ID")
    private Long categoryId;

    @ApiModelProperty("排序字段名")
    private String sortField;

    @ApiModelProperty("排序规则(asc:升序;desc:降序)")
    private String sort;

}
