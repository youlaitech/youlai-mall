package com.youlai.codegen.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.exception.BusinessException;
import com.youlai.codegen.config.CodegenProperties;
import com.youlai.codegen.enums.JavaTypeEnum;
import com.youlai.codegen.model.entity.GenConfig;
import com.youlai.codegen.model.entity.GenFieldConfig;
import com.youlai.codegen.model.query.TablePageQuery;
import com.youlai.codegen.model.vo.CodePreviewVO;
import com.youlai.codegen.model.vo.TablePageVO;
import com.youlai.codegen.service.DatabaseService;
import com.youlai.codegen.service.GenConfigService;
import com.youlai.codegen.service.GenFieldConfigService;
import com.youlai.codegen.service.CodegenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器服务实现类
 *
 * @author haoxr
 * @since 4.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CodegenServiceImpl implements CodegenService {

    private final DatabaseService databaseService;
    private final GenConfigService genConfigService;
    private final GenFieldConfigService genFieldConfigService;

    private final CodegenProperties codegenProperties;

    /**
     * 数据表分页列表
     *
     * @param queryParams 查询参数
     * @return 分页结果
     */
    @Override
    public Page<TablePageVO> getTablePage(TablePageQuery queryParams) {
        // 设置排除的表
        List<String> excludeTables = codegenProperties.getExcludeTables();
        queryParams.setExcludeTables(excludeTables);
        Page<TablePageVO> page = databaseService.getTablePage(queryParams);

        // 遍历表数据，从生成配置表中判断是否已配置
        enhanceTablePageWithConfig(page);
        return page;
    }

    /**
     * 关联主库的配置表，判断是否已配置
     *
     * @param page 分页数据
     */
    @DS("master")
    public void enhanceTablePageWithConfig(Page<TablePageVO> page) {
        List<TablePageVO> records = page.getRecords();
        for (TablePageVO record : records) {
            GenConfig genConfig = genConfigService.getOne(new LambdaQueryWrapper<GenConfig>()
                    .eq(GenConfig::getTableName, record.getTableName())
            );
            record.setIsConfigured(genConfig != null ? 1 : 0);
        }
    }

    /**
     * 获取预览生成代码
     *
     * @param tableName 表名
     * @return 预览数据
     */
    @Override
    public List<CodePreviewVO> getCodePreviewData(String tableName) {

        List<CodePreviewVO> list = new ArrayList<>();

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
        Map<String, CodegenProperties.TemplateConfig> templateConfigs = codegenProperties.getTemplateConfigs();
        for (Map.Entry<String, CodegenProperties.TemplateConfig> templateConfigEntry : templateConfigs.entrySet()) {
            CodePreviewVO previewVO = new CodePreviewVO();

            CodegenProperties.TemplateConfig templateConfig = templateConfigEntry.getValue();

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
            // 组合成文件路径：src/main/java/com/youlai/mall/order/controller
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
     * @param packageName    包名 com.youlai.mall
     * @param moduleName     模块名 order
     * @param subPackageName 子包名 controller
     * @param entityName     实体类名 order
     * @return 文件路径 src/main/java/com/youlai/mall/order/controller
     */
    private String getFilePath(String templateName, String moduleName, String packageName, String subPackageName, String entityName) {
        String path;
        if ("MapperXml".equals(templateName)) {
            path = (codegenProperties.getBackendAppName()
                    + File.separator
                    + "src" + File.separator + "main" + File.separator + "resources"
                    + File.separator + subPackageName
            );
        } else if ("API".equals(templateName)) {
            path = (codegenProperties.getFrontendAppName()
                    + File.separator
                    + "src" + File.separator + subPackageName
            );
        } else if ("VIEW".equals(templateName)) {
            path = (codegenProperties.getFrontendAppName()
                    + File.separator + "src"
                    + File.separator + subPackageName
                    + File.separator + moduleName
                    + File.separator + StrUtil.toSymbolCase(entityName, '-')
            );
        } else {
            path = (codegenProperties.getBackendAppName()
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
    private String getCodeContent(CodegenProperties.TemplateConfig templateConfig, GenConfig genConfig, List<GenFieldConfig> fieldConfigs) {

        Map<String, Object> bindMap = new HashMap<>();

        String entityName = genConfig.getEntityName();

        bindMap.put("packageName", genConfig.getPackageName());
        bindMap.put("moduleName", genConfig.getModuleName());
        bindMap.put("subpackageName", templateConfig.getSubpackageName());
        bindMap.put("date", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
        bindMap.put("entityName", entityName);
        bindMap.put("tableName", genConfig.getTableName());
        bindMap.put("author", genConfig.getAuthor());
        // UserTest → userTest
        bindMap.put("lowerFirstEntityName", StrUtil.lowerFirst(entityName));
        // UserTest → user-test
        bindMap.put("kebabCaseEntityName", StrUtil.toSymbolCase(entityName, '-'));
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

        return template.render(bindMap);
    }

    /**
     * 下载代码
     *
     * @param tableNames 表名数组，支持多张表。
     * @return 压缩文件字节数组
     */
    @Override
    public byte[] downloadCode(String[] tableNames) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ZipOutputStream zip = new ZipOutputStream(outputStream)) {

            // 遍历每个表名，生成对应的代码并压缩到 zip 文件中
            for (String tableName : tableNames) {
                generateAndZipCode(tableName, zip);
            }

            return outputStream.toByteArray();

        } catch (IOException e) {
            log.error("Error while generating zip for code download", e);
            throw new RuntimeException("Failed to generate code zip file", e);
        }
    }

    /**
     * 根据表名生成代码并压缩到zip文件中
     *
     * @param tableName 表名
     * @param zip       压缩文件输出流
     */
    private void generateAndZipCode(String tableName, ZipOutputStream zip) {
        List<CodePreviewVO> codePreviewList = getCodePreviewData(tableName);

        for (CodePreviewVO codePreview : codePreviewList) {
            String fileName = codePreview.getFileName();
            String content = codePreview.getContent();
            String path = codePreview.getPath();

            try {
                // 创建压缩条目
                ZipEntry zipEntry = new ZipEntry(path + File.separator + fileName);
                zip.putNextEntry(zipEntry);

                // 写入文件内容
                zip.write(content.getBytes(StandardCharsets.UTF_8));

                // 关闭当前压缩条目
                zip.closeEntry();

            } catch (IOException e) {
                log.error("Error while adding file {} to zip", fileName, e);
            }
        }
    }


}
