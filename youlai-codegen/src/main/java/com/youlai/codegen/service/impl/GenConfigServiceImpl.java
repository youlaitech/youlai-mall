package com.youlai.codegen.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.core.exception.BusinessException;
import com.youlai.common.enums.EnvEnum;
import com.youlai.codegen.config.CodegenProperties;
import com.youlai.codegen.converter.CodegenConverter;
import com.youlai.codegen.enums.FormTypeEnum;
import com.youlai.codegen.enums.JavaTypeEnum;
import com.youlai.codegen.enums.QueryTypeEnum;
import com.youlai.codegen.mapper.DatabaseMapper;
import com.youlai.codegen.mapper.GenConfigMapper;
import com.youlai.codegen.model.bo.ColumnMetaData;
import com.youlai.codegen.model.bo.TableMetaData;
import com.youlai.codegen.model.entity.GenConfig;
import com.youlai.codegen.model.entity.GenFieldConfig;
import com.youlai.codegen.model.form.GenConfigForm;
import com.youlai.codegen.service.GenConfigService;
import com.youlai.codegen.service.GenFieldConfigService;
import com.youlai.system.api.MenuFeignClient;
import com.youlai.system.dto.CodegenMenuDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 数据库服务实现类
 *
 * @author Ray
 * @since 2.10.0
 */
@Service
@RequiredArgsConstructor
public class GenConfigServiceImpl extends ServiceImpl<GenConfigMapper, GenConfig> implements GenConfigService {

    private final DatabaseMapper databaseMapper;
    private final CodegenProperties codegenProperties;
    private final GenFieldConfigService genFieldConfigService;
    private final CodegenConverter codegenConverter;
    private final MenuFeignClient menuFeignClient;

    @Value("${spring.profiles.active}")
    private String springProfilesActive;

    /**
     * 获取代码生成配置
     *
     * @param tableName 表名 eg: sys_user
     * @return 代码生成配置
     */
    @Override
    public GenConfigForm getGenConfigFormData(String tableName, String datasourceKey) {
        // 查询表生成配置
        GenConfig genConfig = this.getOne(new LambdaQueryWrapper<>(GenConfig.class)
                .eq(GenConfig::getTableName, tableName)
        );

        // 是否有代码生成配置
        boolean hasGenConfig = genConfig != null;
        // 如果没有代码生成配置，则根据表的元数据生成默认配置
        if (genConfig == null) {
            TableMetaData tableMetadata = databaseMapper.getTableMetadata(tableName, datasourceKey);
            Assert.isTrue(tableMetadata != null, "未找到表元数据");
            genConfig = new GenConfig();
            genConfig.setTableName(tableName);

            String tableComment = tableMetadata.getTableComment();
            if (StrUtil.isNotBlank(tableComment)) {
                genConfig.setBusinessName(tableComment.replace("表", ""));
            }
            // 实体类名 = 表名去掉前缀后转驼峰，前缀默认为下划线分割的第一个元素
            String entityName = StrUtil.toCamelCase(StrUtil.removePrefix(tableName, tableName.split("_")[0]));
            genConfig.setEntityName(entityName);

            // 默认配置
            CodegenProperties.DefaultConfig defaultConfig = codegenProperties.getDefaultConfig();
            genConfig.setPackageName(defaultConfig.getPackageName());
            genConfig.setModuleName(defaultConfig.getModuleName());
            genConfig.setAuthor(defaultConfig.getAuthor());
        }

        // 根据表的列 + 已经存在的字段生成配置 得到 组合后的字段生成配置
        List<GenFieldConfig> genFieldConfigs = new ArrayList<>();

        // 获取表的列
        List<ColumnMetaData> tableColumns = databaseMapper.getTableColumns(tableName, datasourceKey);
        if (CollectionUtil.isNotEmpty(tableColumns)) {
            try {
                DynamicDataSourceContextHolder.push("master");
                // 查询字段生成配置
                List<GenFieldConfig> fieldConfigList = genFieldConfigService.list(new LambdaQueryWrapper<GenFieldConfig>()
                        .eq(GenFieldConfig::getConfigId, genConfig.getId())
                        .orderByAsc(GenFieldConfig::getFieldSort)
                );

                Integer maxSort = fieldConfigList.stream()
                        .map(GenFieldConfig::getFieldSort)
                        .filter(Objects::nonNull)
                        .max(Integer::compareTo)
                        .orElse(0);
                for (ColumnMetaData tableColumn : tableColumns) {
                    // 根据列名获取字段生成配置
                    String columnName = tableColumn.getColumnName();
                    GenFieldConfig fieldConfig = fieldConfigList.stream()
                            .filter(item -> StrUtil.equals(item.getColumnName(), columnName))
                            .findFirst()
                            .orElseGet(() -> createDefaultFieldConfig(tableColumn));
                    if (fieldConfig.getFieldSort() == null) {
                        fieldConfig.setFieldSort(++maxSort);
                    }
                    // 根据列类型设置字段类型
                    String fieldType = fieldConfig.getFieldType();
                    if (StrUtil.isBlank(fieldType)) {
                        String javaType = JavaTypeEnum.getJavaTypeByColumnType(fieldConfig.getColumnType());
                        fieldConfig.setFieldType(javaType);
                    }
                    // 如果没有代码生成配置，则默认展示在列表和表单
                    if (!hasGenConfig) {
                        fieldConfig.setIsShowInList(1);
                        fieldConfig.setIsShowInForm(1);
                    }
                    genFieldConfigs.add(fieldConfig);
                }
            } finally {
                DynamicDataSourceContextHolder.poll();
            }
        }
        // 对genFieldConfigs按照fieldSort排序
        genFieldConfigs = genFieldConfigs.stream()
                .sorted(Comparator.comparing(GenFieldConfig::getFieldSort))
                .toList();
        GenConfigForm genConfigForm = codegenConverter.toGenConfigForm(genConfig, genFieldConfigs);

        genConfigForm.setFrontendAppName(codegenProperties.getFrontendAppName());
        genConfigForm.setBackendAppName(codegenProperties.getBackendAppName());
        return genConfigForm;
    }

    /**
     * 创建默认字段配置
     *
     * @param columnMetaData 表字段元数据
     * @return
     */
    private GenFieldConfig createDefaultFieldConfig(ColumnMetaData columnMetaData) {
        GenFieldConfig fieldConfig = new GenFieldConfig();
        fieldConfig.setColumnName(columnMetaData.getColumnName());
        fieldConfig.setColumnType(columnMetaData.getDataType());
        fieldConfig.setFieldComment(columnMetaData.getColumnComment());
        fieldConfig.setFieldName(StrUtil.toCamelCase(columnMetaData.getColumnName()));
        fieldConfig.setIsRequired("YES".equals(columnMetaData.getIsNullable()) ? 1 : 0);

        if ("date".equals(fieldConfig.getColumnType())) {
            fieldConfig.setFormType(FormTypeEnum.DATE);
        } else if ("datetime".equals(fieldConfig.getColumnType())) {
            fieldConfig.setFormType(FormTypeEnum.DATE_TIME);
        } else {
            fieldConfig.setFormType(FormTypeEnum.INPUT);
        }

        fieldConfig.setQueryType(QueryTypeEnum.EQ);
        fieldConfig.setMaxLength(columnMetaData.getCharacterMaximumLength());
        return fieldConfig;
    }

    /**
     * 保存代码生成配置
     *
     * @param formData 代码生成配置表单
     */
    @Override
    public void saveGenConfig(GenConfigForm formData) {
        GenConfig genConfig = codegenConverter.toGenConfig(formData);
        this.saveOrUpdate(genConfig);

        // 如果选择上级菜单且当前环境不是生产环境，则新增菜单
        Long parentMenuId = formData.getParentMenuId();
        if (parentMenuId != null && EnvEnum.PROD.getValue().equals(springProfilesActive)) {
            CodegenMenuDTO codegenMenuDTO = codegenConverter.toCodegenMenuDTO(parentMenuId, genConfig);
            menuFeignClient.addMenuForCodegen(codegenMenuDTO);
        }

        List<GenFieldConfig> genFieldConfigs = codegenConverter.toGenFieldConfig(formData.getFieldConfigs());

        if (CollectionUtil.isEmpty(genFieldConfigs)) {
            throw new BusinessException("字段配置不能为空");
        }
        genFieldConfigs.forEach(genFieldConfig -> {
            genFieldConfig.setConfigId(genConfig.getId());
        });
        genFieldConfigService.saveOrUpdateBatch(genFieldConfigs);
    }

    /**
     * 删除代码生成配置
     *
     * @param tableName 表名
     */
    @Override
    public void deleteGenConfig(String tableName) {
        GenConfig genConfig = this.getOne(new LambdaQueryWrapper<GenConfig>()
                .eq(GenConfig::getTableName, tableName));

        boolean result = this.remove(new LambdaQueryWrapper<GenConfig>()
                .eq(GenConfig::getTableName, tableName)
        );
        if (result) {
            genFieldConfigService.remove(new LambdaQueryWrapper<GenFieldConfig>()
                    .eq(GenFieldConfig::getConfigId, genConfig.getId())
            );
        }
    }

}
