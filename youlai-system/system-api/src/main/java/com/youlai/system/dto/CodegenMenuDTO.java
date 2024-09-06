package com.youlai.system.dto;

import lombok.Data;

/**
 * 代码生成器菜单传输对象
 *
 * @author Ray
 * @since 4.0.0
 */
@Data
public class CodegenMenuDTO {

    /**
     * 上级菜单ID(e.g. 1)
     */
    private Long parentMenuId;

    /**
     * 业务名称(e.g. 用户管理)
     */
    private String businessName;

    /**
     * 模块名称， e.g. system
     */
    private String moduleName;

    /**
     * 实体名称， e.g. User
     */
    private String entityName;


}
