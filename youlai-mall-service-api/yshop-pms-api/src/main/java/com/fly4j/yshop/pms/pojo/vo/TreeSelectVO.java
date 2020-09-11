package com.fly4j.yshop.pms.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class TreeSelectVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelectVO> children;
}
