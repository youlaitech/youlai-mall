package com.youlai.system.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * 字典数据项分页VO
 *
 * @author Ray
 * @since 0.0.1
 */
@Schema(description = "字典数据分页对象")
@Getter
@Setter
public class DictVO {

    @Schema(description = "字典名称")
    private String name;

    @Schema(description = "字典编码")
    private String dictCode;

    @Schema(description = "字典数据集合")
    private List<DictData> dictDataList;

    @Schema(description = "字典数据")
    @Getter
    @Setter
    public static class DictData {

        @Schema(description = "字典数据值")
        private String value;

        @Schema(description = "字典数据标签")
        private String label;

        @Schema(description = "标签类型")
        private String tagType;
    }

}
