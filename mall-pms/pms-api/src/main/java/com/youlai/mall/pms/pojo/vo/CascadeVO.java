package com.youlai.mall.pms.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 级联视图对象
 */
@Data
@Accessors(chain = true)
public class CascadeVO {

    private Long value;

    private String label;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CascadeVO> children;
}
