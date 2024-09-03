package com.youlai.generator.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.exception.BusinessException;
import com.youlai.generator.config.GeneratorProperties;
import com.youlai.generator.converter.GenConfigConverter;
import com.youlai.generator.enums.FormTypeEnum;
import com.youlai.generator.enums.JavaTypeEnum;
import com.youlai.generator.enums.QueryTypeEnum;
import com.youlai.generator.mapper.DatabaseMapper;
import com.youlai.generator.model.bo.ColumnMetaData;
import com.youlai.generator.model.bo.TableMetaData;
import com.youlai.generator.model.entity.GenConfig;
import com.youlai.generator.model.entity.GenFieldConfig;
import com.youlai.generator.model.form.GenConfigForm;
import com.youlai.generator.model.query.TablePageQuery;
import com.youlai.generator.model.vo.GeneratorPreviewVO;
import com.youlai.generator.model.vo.TablePageVO;
import com.youlai.generator.service.GenConfigService;
import com.youlai.generator.service.GenFieldConfigService;
import com.youlai.generator.service.GeneratorService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipOutputStream;

/**
 * 数据库服务实现类
 *
 * @author Ray
 * @since 2.10.0
 */
@Service
@RequiredArgsConstructor
public class GeneratorServiceImpl implements GeneratorService {

    private final DatabaseMapper databaseMapper;
    private final GeneratorProperties generatorProperties;
    private final GenConfigService genConfigService;
    private final GenFieldConfigService genFieldConfigService;
    private final GenConfigConverter genConfigConverter;
   /* private final MenuService menuService;*/

    @Value("${spring.profiles.active}")
    private String springProfilesActive;

    /**
     * 数据表分页列表
     *
     * @param queryParams 查询参数
     * @return 分页结果
     */
    public Page<TablePageVO> getTablePage(TablePageQuery queryParams) {
        Page<TablePageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        // 设置排除的表
        List<String> excludeTables = generatorProperties.getExcludeTables();
        queryParams.setExcludeTables(excludeTables);

        return databaseMapper.getTablePage(page, queryParams);
    }

    /**
     * 获取代码生成配置
     *
     * @param tableName 表名 eg: sys_user
     * @return 代码生成配置
     */
    @Override
    public GenConfigForm getGenConfigFormData(String tableName) {
        // 查询表生成配置
        GenConfig genConfig = genConfigService.getOne(
                new LambdaQueryWrapper<>(GenConfig.class)
                        .eq(GenConfig::getTableName, tableName)
                        .last("LIMIT 1")
        );

        // 是否有代码生成配置
        boolean hasGenConfig = genConfig != null;

        // 如果没有代码生成配置，则根据表的元数据生成默认配置
        if (genConfig == null) {
            TableMetaData tableMetadata = databaseMapper.getTableMetadata(tableName);
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

            genConfig.setPackageName("com.youlai");
            genConfig.setModuleName(generatorProperties.getDefaultConfig().getModuleName()); // 默认模块名
            genConfig.setAuthor(generatorProperties.getDefaultConfig().getAuthor());
        }

        // 根据表的列 + 已经存在的字段生成配置 得到 组合后的字段生成配置
        List<GenFieldConfig> genFieldConfigs = new ArrayList<>();

        // 获取表的列
        List<ColumnMetaData> tableColumns = databaseMapper.getTableColumns(tableName);
        if (CollectionUtil.isNotEmpty(tableColumns)) {
            // 查询字段生成配置
            List<GenFieldConfig> fieldConfigList = genFieldConfigService.list(
                    new LambdaQueryWrapper<GenFieldConfig>()
                            .eq(GenFieldConfig::getConfigId, genConfig.getId())
                            .orderByAsc(GenFieldConfig::getFieldSort)
            );
            Integer maxSort = fieldConfigList.stream()
                    .map(GenFieldConfig::getFieldSort)
                    .filter(Objects::nonNull) // 过滤掉空值
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
        }
        //对genFieldConfigs按照fieldSort排序
        genFieldConfigs = genFieldConfigs.stream().sorted(Comparator.comparing(GenFieldConfig::getFieldSort)).toList();
        GenConfigForm genConfigForm = genConfigConverter.toGenConfigForm(genConfig, genFieldConfigs);

        genConfigForm.setFrontendAppName(generatorProperties.getFrontendAppName());
        genConfigForm.setBackendAppName(generatorProperties.getBackendAppName());
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

        if (fieldConfig.getColumnType().equals("date")) {
            fieldConfig.setFormType(FormTypeEnum.DATE);
        } else if (fieldConfig.getColumnType().equals("datetime")) {
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
        GenConfig genConfig = genConfigConverter.toGenConfig(formData);
        genConfigService.saveOrUpdate(genConfig);

        // 如果选择上级菜单
        Long parentMenuId = formData.getParentMenuId();
        if (parentMenuId != null && springProfilesActive.equals("dev")) {
           // menuService.saveMenu(parentMenuId, genConfig);
        }

        List<GenFieldConfig> genFieldConfigs = genConfigConverter.toGenFieldConfig(formData.getFieldConfigs());

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
        GenConfig genConfig = genConfigService.getOne(new LambdaQueryWrapper<GenConfig>()
                .eq(GenConfig::getTableName, tableName));

        boolean result = genConfigService.remove(new LambdaQueryWrapper<GenConfig>()
                .eq(GenConfig::getTableName, tableName)
        );
        if (result) {
            genFieldConfigService.remove(new LambdaQueryWrapper<GenFieldConfig>()
                    .eq(GenFieldConfig::getConfigId, genConfig.getId())
            );
        }
    }


    /**
     * 获取预览生成代码
     *
     * @param tableName 表名
     * @return 预览数据
     */
    @Override
    public List<GeneratorPreviewVO> getTablePreviewData(String tableName) {

        List<GeneratorPreviewVO> list = new ArrayList<>();

        GenConfig genConfig = genConfigService.getOne(new LambdaQueryWrapper<GenConfig>()
                .eq(GenConfig::getTableName, tableName)
        );
        if (genConfig == null) {
            throw new BusinessException("未找到表生成配置");
        }

        List<GenFieldConfig> fieldConfigs = genFieldConfigService.list(new LambdaQueryWrapper<GenFieldConfig>()
                .eq(GenFieldConfig::getConfigId, genConfig.getId())
                .orderByAsc(GenFieldConfig::getFieldSort)

        );
        if (CollectionUtil.isEmpty(fieldConfigs)) {
            throw new BusinessException("未找到字段生成配置");
        }

        // 遍历模板配置
        Map<String, GeneratorProperties.TemplateConfig> templateConfigs = generatorProperties.getTemplateConfigs();
        for (Map.Entry<String, GeneratorProperties.TemplateConfig> templateConfigEntry : templateConfigs.entrySet()) {
            GeneratorPreviewVO previewVO = new GeneratorPreviewVO();

            GeneratorProperties.TemplateConfig templateConfig = templateConfigEntry.getValue();

            /* 1. 生成文件名 UserController */
            // User Role Menu Dept
            String entityName = genConfig.getEntityName();
            // Controller Service Mapper Entity
            String templateName = templateConfigEntry.getKey();
            // .java .ts .vue
            String extension = templateConfig.getExtension();

            // 文件名 UserController.java
            String fileName = getFileName(entityName, templateName, extension);
            previewVO.setFileName(fileName);

            /* 2. 生成文件路径 */
            // 包名：com.youlai.boot
            String packageName = genConfig.getPackageName();
            // 模块名：system
            String moduleName = genConfig.getModuleName();
            // 子包名：controller
            String subpackageName = templateConfig.getSubpackageName();
            // 组合成文件路径：src/main/java/com/youlai/boot/system/controller
            String filePath = getFilePath(templateName, moduleName, packageName, subpackageName, entityName);
            previewVO.setPath(filePath);

            /* 3. 生成文件内容 */
            // 将模板文件中的变量替换为具体的值 生成代码内容
            String content = getCodeContent(templateConfig, genConfig, fieldConfigs);
            previewVO.setContent(content);

            list.add(previewVO);
        }
        return list;
    }

    /**
     * 生成文件名
     *
     * @param entityName   实体类名 UserController
     * @param templateName 模板名 Entity
     * @param extension    文件后缀 .java
     * @return 文件名
     */
    private String getFileName(String entityName, String templateName, String extension) {
        if ("Entity".equals(templateName)) {
            return entityName + extension;
        } else if ("MapperXml".equals(templateName)) {
            return entityName + "Mapper" + extension;
        } else if ("API".equals(templateName)) {
            return StrUtil.toSymbolCase(entityName, '-') + extension;
        } else if ("VIEW".equals(templateName)) {
            return "index.vue";
        }
        return entityName + templateName + extension;
    }

    /**
     * 生成文件路径
     *
     * @param templateName   模板名 Entity
     * @param moduleName     模块名 system
     * @param packageName    包名 com.youlai
     * @param subPackageName 子包名 controller
     * @param entityName     实体类名 UserController
     * @return 文件路径 src/main/java/com/youlai/system/controller
     */
    private String getFilePath(String templateName, String moduleName, String packageName, String subPackageName, String entityName) {
        String path;
        if ("MapperXml".equals(templateName)) {
            path = (generatorProperties.getBackendAppName()
                    + File.separator
                    + "src" + File.separator + "main" + File.separator + "resources"
                    + File.separator + subPackageName
            );
        } else if ("API".equals(templateName)) {
            path = (generatorProperties.getFrontendAppName()
                    + File.separator
                    + "src" + File.separator + subPackageName
            );
        } else if ("VIEW".equals(templateName)) {
            path = (generatorProperties.getFrontendAppName()
                    + File.separator + "src"
                    + File.separator + subPackageName
                    + File.separator + moduleName
                    + File.separator + StrUtil.toSymbolCase(entityName, '-')
            );
        } else {
            path = (generatorProperties.getBackendAppName()
                    + File.separator
                    + "src" + File.separator + "main" + File.separator + "java"
                    + File.separator + packageName
                    + File.separator + moduleName
                    + File.separator + subPackageName
            );
        }

        // subPackageName = model.entity => model/entity
        path = path.replace(".", File.separator);

        return path;
    }

    /**
     * 生成代码内容
     *
     * @param templateConfig 模板配置
     * @param genConfig      生成配置
     * @param fieldConfigs   字段配置
     * @return 代码内容
     */
    private String getCodeContent(GeneratorProperties.TemplateConfig templateConfig, GenConfig genConfig, List<GenFieldConfig> fieldConfigs) {

        Map<String, Object> bindMap = new HashMap<>();

        String entityName = genConfig.getEntityName();

        bindMap.put("packageName", genConfig.getPackageName());
        bindMap.put("moduleName", genConfig.getModuleName());
        bindMap.put("subpackageName", templateConfig.getSubpackageName());
        bindMap.put("date", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
        bindMap.put("entityName", entityName);
        bindMap.put("tableName", genConfig.getTableName());
        bindMap.put("author", genConfig.getAuthor());
        bindMap.put("lowerFirstEntityName", StrUtil.lowerFirst(entityName)); // UserTest → userTest
        bindMap.put("kebabCaseEntityName", StrUtil.toSymbolCase(entityName, '-')); // UserTest → user-test
        bindMap.put("businessName", genConfig.getBusinessName());
        bindMap.put("fieldConfigs", fieldConfigs);

        boolean hasLocalDateTime = false;
        boolean hasBigDecimal = false;
        boolean hasRequiredField = false;

        for (GenFieldConfig fieldConfig : fieldConfigs) {

            if ("LocalDateTime".equals(fieldConfig.getFieldType())) {
                hasLocalDateTime = true;
            }
            if ("BigDecimal".equals(fieldConfig.getFieldType())) {
                hasBigDecimal = true;
            }
            if (ObjectUtil.equals(fieldConfig.getIsRequired(), 1)) {
                hasRequiredField = true;
            }
            fieldConfig.setTsType(JavaTypeEnum.getTsTypeByJavaType(fieldConfig.getFieldType()));


        }

        bindMap.put("hasLocalDateTime", hasLocalDateTime);
        bindMap.put("hasBigDecimal", hasBigDecimal);
        bindMap.put("hasRequiredField", hasRequiredField);

        TemplateEngine templateEngine = TemplateUtil.createEngine(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = templateEngine.getTemplate(templateConfig.getTemplatePath());
        String content = template.render(bindMap);

        return content;
    }

    /**
     * 下载代码
     *
     * @param tableNames 表名，可以支持多张表。
     * @return 压缩文件字节数组
     */
    @Override
    public byte[] downloadCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames) {
            generatorCode(tableName, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 根据表名生成代码并且压缩到zip文件中
     *
     * @param tableName 单个表名
     * @param zip       压缩文件
     */
    private void generatorCode(String tableName, ZipOutputStream zip) {
        List<GeneratorPreviewVO> previewVOList = getTablePreviewData(tableName);
        for (GeneratorPreviewVO previewVO : previewVOList) {
            String fileName = previewVO.getFileName();
            String content = previewVO.getContent();
            String path = previewVO.getPath();
            try {
                zip.putNextEntry(new java.util.zip.ZipEntry(path + File.separator + fileName));
                zip.write(content.getBytes(StandardCharsets.UTF_8));
                zip.closeEntry();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
