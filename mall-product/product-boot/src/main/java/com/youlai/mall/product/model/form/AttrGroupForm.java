package com.youlai.mall.product.model.form;

import com.youlai.mall.product.model.vo.AttrVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 属性组表单对象
 *
 * @author Ray Hao
 * @since 2024-04-19
 */
@Getter
@Setter
@Schema(description = "属性组表单对象")
public class AttrGroupForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "属性组主键")
    private Long id;

    @Schema(description = "属性组名称")
    private String name;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "排序")
    private Short sort;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "属性列表")
    private List<Attr> attrs;

    @Schema(description = "属性")
    @Getter
    @Setter
    public static class Attr {
        @Schema(description = "属性主键")
        private Long id;

        @Schema(description = "属性名称")
        private String name;

        @Schema(description = "属性输入类型：1->手动输入；2->列表选择")
        private Integer inputType;

        @Schema(description = "可选列表(仅当input_type是2使用)",example = "1920*1080,2560*1440,3840*2160")
        private String options;

        @Schema(description = "排序")
        private Integer sort;
    }

}
