package com.youlai.mall.pms.search.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Properties Mapping
 */
@Data
@NoArgsConstructor
public class PropertiesMapping {

    /**
     * 字段名称
     */
    @NonNull
    private String fieldName;

    /**
     * 字段映射(与关系映射互斥)
     */
    private FieldMapping fieldMapping;

    /**
     * 关系映射（与字段映射互斥）
     */
    private RelationshipMapping relationshipMapping;

    public PropertiesMapping(@NonNull String fieldName, FieldMapping fieldMapping) {
        this.fieldName = fieldName;
        this.fieldMapping = fieldMapping;
    }

    public PropertiesMapping(@NonNull String fieldName, RelationshipMapping relationshipMapping) {
        this.fieldName = fieldName;
        this.relationshipMapping = relationshipMapping;
    }
}

