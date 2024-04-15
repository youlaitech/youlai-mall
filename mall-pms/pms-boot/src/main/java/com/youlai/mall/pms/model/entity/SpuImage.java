package com.youlai.mall.pms.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品图片
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
@TableName("pms_spu_image")
public class SpuImage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long spuId;

    private String imgUrl;
}
