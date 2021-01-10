package com.youlai.admin.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class PermissionVO {
    List<TreeVO> permissions;
    List<Long> checkedKeys;
}
