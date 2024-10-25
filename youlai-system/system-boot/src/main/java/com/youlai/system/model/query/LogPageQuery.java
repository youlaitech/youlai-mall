package com.youlai.system.model.query;

import com.youlai.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

/**
 * 日志分页查询对象
 *
 * @author Ray
 * @since 2.10.0
 */
@Schema(description = "日志分页查询对象")
@EqualsAndHashCode(callSuper = false)
@Data
public class LogPageQuery extends BasePageQuery {

    @Schema(description="关键字(日志内容/请求路径/请求方法/地区/浏览器/终端系统)")
    private String keywords;

    @Schema(description="操作时间范围")
    List<String> createTime;

}
