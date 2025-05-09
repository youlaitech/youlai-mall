package com.youlai.mall.product.service.client;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.entity.CategoryEntity;
import com.youlai.mall.product.model.vo.client.ClientCategoryVO;

import java.util.List;

public interface ClientCategoryService extends IService<CategoryEntity> {

    List<ClientCategoryVO> listCategories();

}
