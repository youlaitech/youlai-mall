package com.youlai.common.constant;

/**
 * 全局常量
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2021/10/30 9:32
 */
public interface GlobalConstants {

    /**
     * 全局状态-是
     */
    Integer STATUS_YES = 1;

    /**
     * 超级管理员角色编码
     */
    String ROOT_ROLE_CODE = "ROOT";

    /**
     * [ {接口路径:[角色编码]},...]
     */
    String URL_PERM_ROLES_KEY = "system:perm_roles_rule:url";

    /**
     * [{按钮权限:[角色编码]},...]
     */
    String BTN_PERM_ROLES_KEY = "system:perm_roles_rule:btn";

}
