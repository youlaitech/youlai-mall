package com.youlai.mall.pms.pojo.bo.admin;

import com.youlai.mall.pms.pojo.domain.PmsInventory;
import com.youlai.mall.pms.pojo.domain.PmsProductAttrValue;
import com.youlai.mall.pms.pojo.domain.PmsProductSpecValue;
import com.youlai.mall.pms.pojo.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBO {

    private ProductDTO product;

    private List<PmsProductAttrValue> attrs;

    private List<PmsProductSpecValue> specs;

    private List<PmsInventory> skuList;

}
