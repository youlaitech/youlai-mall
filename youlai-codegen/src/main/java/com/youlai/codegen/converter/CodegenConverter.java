package com.youlai.codegen.converter;

import com.youlai.codegen.model.entity.GenConfig;
import com.youlai.codegen.model.entity.GenFieldConfig;
import com.youlai.codegen.model.form.GenConfigForm;
import com.youlai.system.dto.CodegenMenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * 代码生成配置转换器
 *
 * @author Ray.Hao
 * @since 2.10.0
 */
@Mapper(componentModel = "spring")
public interface CodegenConverter {

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

    @Mapping(source = "parentMenuId", target = "parentMenuId")
    CodegenMenuDTO toCodegenMenuDTO(Long parentMenuId, GenConfig genConfig);

}