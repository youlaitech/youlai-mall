package com.youlai.admin.pojo.form;
import lombok.Data;

import java.util.List;

/**
 * 角色权限传输层对象
 */
@Data
public class RolePermForm {

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 权限ID集合
     */
    private List<Long> permIds;

}
