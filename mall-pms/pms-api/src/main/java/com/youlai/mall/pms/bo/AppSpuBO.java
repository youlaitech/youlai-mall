package com.youlai.mall.pms.bo;

import com.youlai.mall.pms.pojo.PmsSku;
import com.youlai.mall.pms.pojo.PmsSpuAttribute;
import com.youlai.mall.pms.pojo.PmsSpuSpecification;
import com.youlai.mall.pms.pojo.dto.PmsSpuDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppSpuBO {

    private PmsSpuDTO spu;

    private List<PmsSpuAttribute> attributes;

    private List<SpecItem> specs;

    private List<PmsSku> skuList;


    @Data
   public static class SpecItem {
        private String key;
        private Set<String> values;
    }
}
