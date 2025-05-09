package com.youlai.mall.product.model.vo.client;

import lombok.Data;

import java.util.List;

/**
 * 客户端分类视图对象
 *
 * @author Ray.Hao
 * @since 4.0.0
 */
@Data
public class ClientCategoryVO {

    private Long id;

    private String name;

    private String iconUrl;

    private List<ClientCategoryVO> children;

}
