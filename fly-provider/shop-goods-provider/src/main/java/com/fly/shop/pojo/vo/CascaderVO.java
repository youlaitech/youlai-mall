package com.fly.shop.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class CascaderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 节点value */
    private String value;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CascaderVO> children;
}
