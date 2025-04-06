package com.youlai.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 用户和角色关联表
 *
 * @author Rya.Hao
 * @since 2022/12/17
 */
@TableName("sys_user_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
}