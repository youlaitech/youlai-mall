package com.youlai.system.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * 字典分页VO
 *
 * @author Ray
 * @since 0.0.1
 */
@Schema(description = "字典分页对象")
@Getter
@Setter
public class DictPageVO {

    @Schema(description = "字典ID")
    private Long id;

    @Schema(description = "字典名称")
    private String name;

    @Schema(description = "字典编码")
    private String code;

    @Schema(description = "字典状态（1-启用，0-禁用）")
    private Integer status;

    @Schema(description = "字典项列表")
    private List<DictItem> dictItems;

    @Schema(description = "字典")
    @Getter
    @Setter
    public static class DictItem {

        @Schema(description = "字典项ID")
        private Long id;

        @Schema(description = "字典项名称")
        private String name;

        @Schema(description = "字典项值")
        private String value;

        @Schema(description = "排序")
        private Integer sort;

        @Schema(description = "状态（1-启用，0-禁用）")
        private Integer status;

    }

}
