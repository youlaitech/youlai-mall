package com.youlai.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 级联视图对象
 */
@Data
@Accessors(chain = true)
public class ValueLabel {

    private Long value;

    private String label;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ValueLabel> children;
}
