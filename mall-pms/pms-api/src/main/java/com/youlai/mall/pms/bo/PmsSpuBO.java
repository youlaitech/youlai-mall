package com.youlai.mall.pms.bo;

import com.youlai.mall.pms.entity.PmsSku;
import com.youlai.mall.pms.entity.PmsSpu;
import com.youlai.mall.pms.entity.PmsSpuAttribute;
import com.youlai.mall.pms.entity.PmsSpuSpecification;
import lombok.Data;

import java.util.List;

@Data
public class PmsSpuBO {

    private PmsSpu spu;

    private List<PmsSpuAttribute> attributes;

    private List<PmsSpuSpecification> specifications;

    private List<PmsSku> skuList;

}
