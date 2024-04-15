package com.youlai.mall.pms.model.dto;


import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * 属性 DTO
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
public class AttributeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
         * 属性主键
         */

private Long id;

        /**
         * 属性组主键
         */

private Long attributeGroupId;

        /**
         * 属性名称
         */

private String name;

        /**
         * 输入录入方式：1-手动输入，2-从列表选择
         */

private Byte inputType;

        /**
         * 逗号分割的可选值列表，仅当input_type是2使用
         */

private String options;

        /**
         * 创建时间
         */

private LocalDateTime createTime;

        /**
         * 更新时间
         */

private LocalDateTime updateTime;
}
