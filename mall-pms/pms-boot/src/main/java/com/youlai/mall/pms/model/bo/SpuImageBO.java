package com.youlai.mall.pms.model.bo;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 商品图片
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
public class SpuImageBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long spuId;

    private String imgUrl;
}
