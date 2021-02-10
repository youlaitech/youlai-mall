package com.youlai.admin.pojo.dto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RolePermissionDTO {
    private Long roleId;
    private List<Long> permissionIds;
    private Integer type;

    private Long moduleId;
}
