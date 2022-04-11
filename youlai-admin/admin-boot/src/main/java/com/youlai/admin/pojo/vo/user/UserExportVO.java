package com.youlai.admin.pojo.vo.user;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户导出视图对象
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/4/11 8:46
 */

@Data
@ColumnWidth(20)
public class UserExportVO {

    @ExcelProperty(value = "用户名")
    private String username;

    @ExcelProperty(value = "用户昵称")
    private String nickname;

    @ExcelProperty(value = "部门")
    private String deptName;

    @ExcelProperty(value = "性别")
    private String gender;

    @ExcelProperty(value = "手机号码")
    private String mobile;

    @ExcelProperty(value = "邮箱")
    private String email;

    @ExcelProperty(value = "创建时间")
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime gmtCreate;


}
