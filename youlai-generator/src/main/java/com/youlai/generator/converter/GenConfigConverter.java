package com.youlai.generator.converter;

import com.youlai.generator.model.entity.GenConfig;
import com.youlai.generator.model.entity.GenFieldConfig;
import com.youlai.generator.model.form.GenConfigForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * 代码生成配置转换器
 *
 * @author Ray
 * @since 2.10.0
 */
@Mapper(componentModel = "spring")
public interface GenConfigConverter {

    @Mapping(source = "genConfig.tableName", target = "tableName")
    @Mapping(source = "genConfig.businessName", target = "businessName")
    @Mapping(source = "genConfig.moduleName", target = "moduleName")
    @Mapping(source = "genConfig.packageName", target = "packageName")
    @Mapping(source = "genConfig.entityName", target = "entityName")
    @Mapping(source = "genConfig.author", target = "author")
    @Mapping(source = "fieldConfigs", target = "fieldConfigs")
    GenConfigForm toGenConfigForm(GenConfig genConfig, List<GenFieldConfig> fieldConfigs);

    List<GenConfigForm.FieldConfig> toGenFieldConfigForm(List<GenFieldConfig> fieldConfigs);

    GenConfigForm.FieldConfig toGenFieldConfigForm(GenFieldConfig genFieldConfig);

    GenConfig toGenConfig(GenConfigForm formData);

    List<GenFieldConfig> toGenFieldConfig(List<GenConfigForm.FieldConfig> fieldConfigs);

    GenFieldConfig toGenFieldConfig(GenConfigForm.FieldConfig fieldConfig);

}