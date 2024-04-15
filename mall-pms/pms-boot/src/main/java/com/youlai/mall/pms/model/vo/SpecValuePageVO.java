package com.youlai.mall.pms.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 *  分页VO
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
public class SpecValuePageVO implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
         * 规格值ID
         */

    private Long id;

        /**
         * 规格ID
         */

    private Long specId;

        /**
         * 规格值
         */

    private String value;
}
