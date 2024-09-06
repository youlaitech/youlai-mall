package com.youlai.codegen.config;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.map.MapUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 代码生成配置属性
 *
 * @author Ray
 * @since 2.11.0
 */
@Component
@ConfigurationProperties(prefix = "codegen")
@Data
public class CodegenProperties {


    /**
     * 默认配置
     */
    private DefaultConfig defaultConfig ;

    /**
     * 模板配置
     */
    private Map<String, TemplateConfig> templateConfigs = MapUtil.newHashMap(true);

    /**
     * 后端应用名
     */
    private String backendAppName;

    /**
     * 前端应用名
     */
    private String frontendAppName;

    /**
     * 下载文件名
     */
    private String downloadFileName;

    /**
     * 排除数据表
     */
    private List<String> excludeTables;

    /**
     * 模板配置
     */
    @Data
    public static class TemplateConfig {

        /**
         * 模板路径 (e.g. /templates/codegen/controller.java.vm)
         */
        private String templatePath;

        /**
         * 子包名 (e.g. controller/service/mapper/model)
         */
        private String subpackageName;

        /**
         * 文件扩展名，如 .java
         */
        private String extension = FileNameUtil.EXT_JAVA;

    }

    /**
     * 默认配置
     */
    @Data
    public static class DefaultConfig {

        /**
         * 作者 (e.g. Ray)
         */
        private String author;

        /**
         * 默认模块名(e.g. system)
         */
        private String moduleName;

    }


}
