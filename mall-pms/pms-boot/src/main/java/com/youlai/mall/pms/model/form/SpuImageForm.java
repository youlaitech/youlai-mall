package com.youlai.mall.pms.model.form;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品图片 表单对象
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
public class SpuImageForm implements Serializable {

    private static final long serialVersionUID = 1L;


private Long spuId;


private String imgUrl;
}
