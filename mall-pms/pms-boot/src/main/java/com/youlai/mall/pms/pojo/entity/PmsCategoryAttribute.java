package com.youlai.mall.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 商品分类属性/规格
 *
 * @author haoxr
 * @date 2022/7/2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PmsCategoryAttribute extends BaseEntity {

    @TableId(type=IdType.AUTO)
    private Long id;

    /**
     * 商品分类ID
     */
    private Long categoryId;

    /**
     * 属性/规格名称
     */
    private String name;

    /**
     * 类型(1:规格;2:属性;)
     */
    private Integer type;
}
