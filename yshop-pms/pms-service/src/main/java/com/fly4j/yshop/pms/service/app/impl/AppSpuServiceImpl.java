package com.fly4j.yshop.pms.service.app.impl;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.yshop.pms.mapper.PmsSpuMapper;
import com.fly4j.yshop.pms.pojo.dto.app.AppSkuDTO;
import com.fly4j.yshop.pms.pojo.dto.app.AppSpuDTO;
import com.fly4j.yshop.pms.pojo.entity.PmsAttribute;
import com.fly4j.yshop.pms.pojo.entity.PmsSpec;
import com.fly4j.yshop.pms.pojo.entity.PmsSpu;
import com.fly4j.yshop.pms.service.IPmsAttributeService;
import com.fly4j.yshop.pms.service.IPmsSpecService;
import com.fly4j.yshop.pms.service.app.IAppSpuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AppSpuServiceImpl extends ServiceImpl<PmsSpuMapper, PmsSpu> implements IAppSpuService {

    @Autowired
    private IPmsAttributeService iPmsAttributeService;

    @Autowired
    private IPmsSpecService iPmsSpecService;

    @Override
    public AppSpuDTO getSpuDetail(Long id) {
        AppSpuDTO spuDTO = new AppSpuDTO();

        // 1、基本信息
        PmsSpu spu = this.baseMapper.selectById(id);
        if (spu != null) {
            BeanUtil.copyProperties(spu, spuDTO);
            String pic_urls = spu.getPic_urls();
            if (StringUtils.isNotBlank(pic_urls)) {
                List<String> pic_url_list = JSONArray.parseArray(spu.getPic_urls(), String.class);
                spuDTO.setPic_urls(pic_url_list);
            }
        }

        // 2、属性列表
        List<PmsAttribute> attributeList = iPmsAttributeService
                .list(new LambdaQueryWrapper<PmsAttribute>().eq(PmsAttribute::getSpu_id, id));
        spuDTO.setAttribute_list(attributeList);

        // 3、规格列表
        List<PmsSpec> specList = iPmsSpecService.list(new LambdaQueryWrapper<PmsSpec>().eq(PmsSpec::getSpu_id, id));
        Map<String, List<PmsSpec>> specMap = new HashMap<>();
        for (int i = 0; i < specList.size(); i++) {
            PmsSpec pmsSpec = specList.get(i);
            if (specMap.containsKey(pmsSpec.getName())) {
                List<PmsSpec> pmsSpecs = specMap.get(pmsSpec.getName());
                pmsSpecs.add(pmsSpec);
                specMap.put(pmsSpec.getName(), pmsSpecs);
            } else {
                List<PmsSpec> pmsSpecs = new ArrayList<>();
                pmsSpecs.add(pmsSpec);
                specMap.put(pmsSpec.getName(), pmsSpecs);
            }
        }

        List<AppSkuDTO.Tree> treeList = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, List<PmsSpec>> entry : specMap.entrySet()) {
            AppSkuDTO.Tree tree = new AppSkuDTO.Tree();
            tree.setK(entry.getKey()); // k
            List<AppSkuDTO.V> vList = new ArrayList<>();
            List<PmsSpec> entryValue = entry.getValue();
            if (entryValue != null && entryValue.size() > 0) {
                entryValue.forEach(item -> {
                    AppSkuDTO.V v = new AppSkuDTO.V();
                    v.setId(item.getId().toString());
                    v.setName(item.getValue());
                    v.setImgUrl(item.getPic_url());
                    v.setPreviewImgUrl(item.getPic_url());
                    vList.add(v);
                });
            }
            tree.setV(vList);// v
            tree.setK_s("s" + ++i);// k_s
            treeList.add(tree);
        }
        return spuDTO;
    }
}
