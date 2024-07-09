package com.youlai.system.model.form;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 字典表单对象
 *
 * @author Ray Hao
 * @since 2.9.0
 */
@Schema(description = "字典")
@Data
public class DictForm {

    @Schema(description = "字典ID",example = "1")
    private Long id;

    @Schema(description = "字典名称",example = "性别")
    private String name;

    @Schema(description = "字典编码", example ="gender")
    private String code;

    @Schema(description = "字典状态（1-启用，0-禁用）", example = "1")
    private Integer status;

    @Schema(description = "字典数据项列表",example = "[{\"id\":1,\"name\":\"男\",\"value\":\"1\",\"sort\":1,\"status\":1},{\"id\":2,\"name\":\"女\",\"value\":\"2\",\"sort\":2,\"status\":1}]")
    private List<DictItem> dictItems;

    @Schema(description = "字典数据项")
    @Getter
    @Setter
    public static class DictItem {

        @Schema(description = "字典ID")
        private Long id;

        @Schema(description = "字典名称")
        private String name;

        @Schema(description = "字典值")
        private String value;

        @Schema(description = "排序")
        private Integer sort;

        @Schema(description = "状态（1-启用，0-禁用）")
        private Integer status;

    }


}
