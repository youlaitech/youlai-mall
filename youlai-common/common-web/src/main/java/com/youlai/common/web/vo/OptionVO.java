package com.youlai.common.web.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Select选择器默认Option属性
 *
 * @author haoxr
 * @date 2022/1/22
 */
@ApiModel("Select选择器默认Option属性")
@Data
@NoArgsConstructor
public class OptionVO<T> {

    public OptionVO(T value, String label) {
        this.value = value;
        this.label = label;
    }

    @ApiModelProperty("选项的值")
    private T value;

    @ApiModelProperty("选项的标签，若不设置则默认与value相同")
    private String label;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<OptionVO> children;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @ApiModelProperty("是否禁用该选项，默认false")
    public Boolean disabled;

}