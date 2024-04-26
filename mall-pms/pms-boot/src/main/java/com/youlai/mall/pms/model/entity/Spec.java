package com.youlai.mall.pms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * 规格实体类
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
@TableName("pms_spu_spec")
public class Spec implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 规格主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * SPU ID
     */
    private Long spuId;

    /**
     * 规格名称
     */
    private String name;
}
