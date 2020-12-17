package com.youlai.mall.pms.search.pojo;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关系映射
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelationshipMapping {

    /**
     * 类型; 推荐join
     */
    private String type;

    /**
     * 是否开启全局预加载,加快查询;
     * 此参数只支持text和keyword，keyword默认可用，而text需要设置fielddata属性
     */
    @JsonProperty(value = "eager_global_ordinals")
    private Boolean eagerGlobalOrdinals;

    /**
     * 关系描述("parentDocName": ["sonDocName" ...])
     */
    private JSONObject relations;
}

