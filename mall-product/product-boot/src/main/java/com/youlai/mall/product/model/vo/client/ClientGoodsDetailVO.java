package com.youlai.mall.product.model.vo.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 商品详情视图对象
 * <p>
 * @author Ray.Hao
 * @since 2024/5/20
 */
@Schema(description = "商品详情")
@Data
public class ClientGoodsDetailVO implements Serializable {

  @Schema(description = "商品ID")
  private Long id;

  @Schema(description = "商品名称", example = "Apple iPhone 12")
  private String name;

  @Schema(description = "商品主图URL", example = "https://youlai-mall.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg")
  private String imgUrl;

  @Schema(description = "商品图册(详情页轮播图)")
  private List<String> imgList;

  @Schema(description = "商品单位", example = "台")
  private String unit;

  @Schema(description = "商品描述", example = "Apple iPhone 12 64GB 绿色")
  private String description;

  @Schema(description = "商品详情", example = "<p>Apple iPhone 12 64GB 绿色</p>")
  private String detail;

  @Schema(description = "属性列表", example = "[{\"id\":\"1\",\"value\":\"2K\"},{\"id\":\"2\",\"value\":\"6英寸\"}]")
  private List<AttrValue> attrList;

  @Schema(description = "SKU列表", example = "[{\"name\":\"白色 64GB\",\"price\":699900,\"stock\":100,\"imgUrl\":\"https://youlai.oss-cn-shenzhen.aliyuncs.com/images/2021/04/23/1619163660000.jpg\",\"specValues\":[{\"id\":\"1\",\"value\":\"白色\"},{\"id\":\"2\",\"value\":\"64GB\"}]}]")
  private List<Sku> skuList;

  @Schema(description = "SKU")
  @Data
  public static class Sku {

    @Schema(description = "SKU ID")
    private Long id;

    @Schema(description = "SKU编码", example = "白色 64GB")
    private String skuCode;

    @Schema(description = "价格(单位：分)")
    private Long price;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "商品图片URL")
    private String imgUrl;

    @Schema(description = "规格列表", example = "[{\"id\":\"1\",\"value\":\"白色\"},{\"id\":\"2\",\"value\":\"64GB\"}]")
    private List<SpecValue> specList;

  }

  @Schema(description = "规格值")
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class SpecValue {

    @Schema(description = "规格名称", example = "颜色")
    private String name;

    @Schema(description = "规格值", example = "白色")
    private String value;

  }

  @Schema(description = "属性值对象")
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class AttrValue {

    @Schema(description = "属性名称", example = "分辨率")
    private String name;

    @Schema(description = "属性值", example = "2K")
    private String value;

  }

}
