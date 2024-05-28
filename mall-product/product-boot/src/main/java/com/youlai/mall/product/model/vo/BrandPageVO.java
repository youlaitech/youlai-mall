package com.youlai.mall.product.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 品牌分页视图对象
 *
 * @author Ray Hao
 * @since 2024/05/10
 */
@Data
@Schema(description = "品牌分页视图对象")
public class BrandPageVO implements Serializable {

    @Schema(description = "品牌的ID", example = "1")
    private Long id;

    @Schema(description = "品牌的名称", example = "Apple")
    private String name;

    @Schema(description = "品牌的LOGO图片的URL地址", example = "https://example.com/apple_logo.png")
    private String logoUrl;

    @Schema(description = "品牌的首字母", example = "A")
    private String firstLetter;

    @Schema(description = "用于展示在列表中的排序权重，数值越小，排序越靠前", example = "1")
    private Integer sort;

    @Schema(description = "是否显示（0：不显示，1：显示）", example = "1")
    private Integer isVisible;

    @Schema(description = "品牌的简介", example = "Apple Inc. is an American multinational technology company...")
    private String description;

    @Schema(description = "创建时间", example = "2024-05-10 10:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String createTime;
}
