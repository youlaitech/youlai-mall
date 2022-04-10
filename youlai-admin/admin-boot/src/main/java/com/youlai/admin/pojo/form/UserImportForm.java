package com.youlai.admin.pojo.form;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.apache.catalina.User;

import java.util.List;

/**
 * 用户导入表单对象
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/4/10 20:15
 */
@Data
public class UserImportForm {

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 导入的用户列表
     */
    private List<UserItem> userList;

    @Data
    public class UserItem {
        @ExcelProperty(value = "用户名")
        private String username;

        @ExcelProperty(value = "用户昵称")
        private String nickname;

        @ExcelProperty(value = "性别")
        private String genderLabel;

        @ExcelProperty(value = "手机号码")
        private String mobile;

        @ExcelProperty(value = "邮箱")
        private String email;
    }

}
