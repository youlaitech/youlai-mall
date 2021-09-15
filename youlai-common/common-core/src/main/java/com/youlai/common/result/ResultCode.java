package com.youlai.common.result;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author haoxr
 * @date 2020-06-23
 **/
@AllArgsConstructor
@NoArgsConstructor
public enum ResultCode implements IResultCode, Serializable {

    SUCCESS("00000", "一切ok"),

    USER_ERROR("A0001", "用户端错误"),
    USER_LOGIN_ERROR("A0200", "用户登录异常"),

    USER_NOT_EXIST("A0201", "用户不存在"),
    USER_ACCOUNT_LOCKED("A0202", "用户账户被冻结"),
    USER_ACCOUNT_INVALID("A0203", "用户账户已作废"),

    USERNAME_OR_PASSWORD_ERROR("A0210", "用户名或密码错误"),
    PASSWORD_ENTER_EXCEED_LIMIT("A0211", "用户输入密码次数超限"),
    CLIENT_AUTHENTICATION_FAILED("A0212", "客户端认证失败"),
    TOKEN_INVALID_OR_EXPIRED("A0230", "token无效或已过期"),
    TOKEN_ACCESS_FORBIDDEN("A0231", "token已被禁止访问"),

    AUTHORIZED_ERROR("A0300", "访问权限异常"),
    ACCESS_UNAUTHORIZED("A0301", "访问未授权"),
    FORBIDDEN_OPERATION("A0302", "演示环境禁止修改、删除重要数据，请本地部署后测试"),


    PARAM_ERROR("A0400", "用户请求参数错误"),
    RESOURCE_NOT_FOUND("A0401", "请求资源不存在"),
    PARAM_IS_NULL("A0410", "请求必填参数为空"),

    USER_UPLOAD_FILE_ERROR("A0700", "用户上传文件异常"),
    USER_UPLOAD_FILE_TYPE_NOT_MATCH("A0701", "用户上传文件类型不匹配"),
    USER_UPLOAD_FILE_SIZE_EXCEEDS("A0702", "用户上传文件太大"),
    USER_UPLOAD_IMAGE_SIZE_EXCEEDS("A0703", "用户上传图片太大"),

    SYSTEM_EXECUTION_ERROR("B0001", "系统执行出错"),
    SYSTEM_EXECUTION_TIMEOUT("B0100", "系统执行超时"),
    SYSTEM_ORDER_PROCESSING_TIMEOUT("B0100", "系统订单处理超时"),

    SYSTEM_DISASTER_RECOVERY_TRIGGER("B0200", "系统容灾功能被出发"),
    FLOW_LIMITING("B0210", "系统限流"),
    DEGRADATION("B0220", "系统功能降级"),

    SYSTEM_RESOURCE_ERROR("B0300", "系统资源异常"),
    SYSTEM_RESOURCE_EXHAUSTION("B0310", "系统资源耗尽"),
    SYSTEM_RESOURCE_ACCESS_ERROR("B0320", "系统资源访问异常"),
    SYSTEM_READ_DISK_FILE_ERROR("B0321", "系统读取磁盘文件失败"),

    CALL_THIRD_PARTY_SERVICE_ERROR("C0001", "调用第三方服务出错"),
    MIDDLEWARE_SERVICE_ERROR("C0100", "中间件服务出错"),
    INTERFACE_NOT_EXIST("C0113", "接口不存在"),

    MESSAGE_SERVICE_ERROR("C0120", "消息服务出错"),
    MESSAGE_DELIVERY_ERROR("C0121", "消息投递出错"),
    MESSAGE_CONSUMPTION_ERROR("C0122", "消息消费出错"),
    MESSAGE_SUBSCRIPTION_ERROR("C0123", "消息订阅出错"),
    MESSAGE_GROUP_NOT_FOUND("C0124", "消息分组未查到"),

    DATABASE_ERROR("C0300", "数据库服务出错"),
    DATABASE_TABLE_NOT_EXIST("C0311", "表不存在"),
    DATABASE_COLUMN_NOT_EXIST("C0312", "列不存在"),
    DATABASE_DUPLICATE_COLUMN_NAME("C0321", "多表关联中存在多个相同名称的列"),
    DATABASE_DEADLOCK("C0331", "数据库死锁"),
    DATABASE_PRIMARY_KEY_CONFLICT("C0341", "主键冲突");

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    private String code;

    private String msg;

    @Override
    public String toString() {
        return "{" +
                "\"code\":\"" + code + '\"' +
                ", \"msg\":\"" + msg + '\"' +
                '}';
    }


    public static ResultCode getValue(String code){
        for (ResultCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return SYSTEM_EXECUTION_ERROR; // 默认系统执行错误
    }
}
