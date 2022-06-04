package com.youlai.common.web.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Select选择器默认Option属性对象
 *
 * @author haoxr
 * @date 2022/1/22
 */
@ApiModel("Select选择器默认Option属性")
@Data
@NoArgsConstructor
public class Option<T> {

    public Option(T value, String label) {
        this.value = value;
        this.label = label;
    }

    public Option(T value, String label, List<Option> children) {
        this.value = value;
        this.label = label;
        this.children= children;
    }

    @ApiModelProperty("选项的值")
    private T value;

    @ApiModelProperty("选项的标签，若不设置则默认与value相同")
    private String label;

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<Option> children;


}