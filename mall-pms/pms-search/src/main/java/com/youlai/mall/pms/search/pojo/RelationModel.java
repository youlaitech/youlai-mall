package com.youlai.mall.pms.search.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.elasticsearch.common.Nullable;

/**
 * 关系Model
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationModel {

    /**
     * 关系名称
     */
    @NonNull
    private String name;

    /**
     * 父文档ID
     */
    @Nullable
    private String parent;

    public RelationModel(String name) {
        this.name = name;
    }
}

