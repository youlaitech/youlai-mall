package com.youlai.mall.pms.bo;

import com.youlai.mall.pms.pojo.PmsSku;
import com.youlai.mall.pms.pojo.PmsSpu;
import com.youlai.mall.pms.pojo.PmsSpuAttribute;
import com.youlai.mall.pms.pojo.PmsSpuSpecification;
import lombok.Data;

import java.util.List;

@Data
public class PmsSpuBO {

    private PmsSpu spu;

    private List<PmsSpuAttribute> attributes;

    private List<PmsSpuSpecification> specifications;

    private List<PmsSku> skuList;

}
