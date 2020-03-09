package com.fly.shop.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TreeSelectVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelectVO> children;
}
