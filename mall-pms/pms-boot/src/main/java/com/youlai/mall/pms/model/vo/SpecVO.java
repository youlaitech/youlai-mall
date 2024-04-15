package com.youlai.mall.pms.model.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 *  VO
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
public class SpecVO implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
         * 规格主键
         */

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
