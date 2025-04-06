package com.youlai.common.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel导出响应结构体
 *
 * @author Theo
 * @since 2025/1/14 11:46:08
 */
@Data
public class ExcelResult {

    /**
     * 响应码，来确定是否导入成功
     */
    private String code;

    /**
     * 有效条数
     */
    private Integer validCount;

    /**
     * 无效条数
     */
    private Integer invalidCount;

    /**
     * 错误提示信息
     */
    private List<String> messageList;

    public ExcelResult() {
        this.code = ResultCode.SUCCESS.getCode();
        this.validCount = 0;
        this.invalidCount = 0;
        this.messageList = new ArrayList<>();
    }
}
