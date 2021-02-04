package com.youlai.common.web.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;

@Data
@Accessors(chain = true)
public class CascaderVO {

    private String value;

    private String label;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CascaderVO> children;
}
