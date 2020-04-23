package com.fly4j.yshop.pms.service.app.impl;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.yshop.pms.mapper.PmsSpuMapper;
import com.fly4j.yshop.pms.pojo.dto.app.AppAttributeDTO;
import com.fly4j.yshop.pms.pojo.dto.app.AppSkuDTO;
import com.fly4j.yshop.pms.pojo.dto.app.AppGoodsDetailDTO;
import com.fly4j.yshop.pms.pojo.dto.app.AppSpecDTO;
import com.fly4j.yshop.pms.pojo.entity.PmsAttribute;
import com.fly4j.yshop.pms.pojo.entity.PmsSku;
import com.fly4j.yshop.pms.pojo.entity.PmsSpec;
import com.fly4j.yshop.pms.pojo.entity.PmsSpu;
import com.fly4j.yshop.pms.service.IPmsAttributeService;
import com.fly4j.yshop.pms.service.IPmsSkuService;
import com.fly4j.yshop.pms.service.IPmsSpecService;
import com.fly4j.yshop.pms.service.app.IAppSpuService;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class AppSpuServiceImpl extends ServiceImpl<PmsSpuMapper, PmsSpu> implements IAppSpuService {

    @Autowired
    private IPmsAttributeService iPmsAttributeService;

    @Autowired
    private IPmsSpecService iPmsSpecService;

    @Autowired
    private IPmsSkuService iPmsSkuService;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public AppGoodsDetailDTO getGoodsDetail(Long id) {
        AppGoodsDetailDTO spuDTO = new AppGoodsDetailDTO();

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
        List<AppAttributeDTO> attributeDTOList = new ArrayList<>();
        if (attributeList != null && attributeList.size() > 0) {
            attributeDTOList = attributeList.stream().map(item -> dozerBeanMapper.map(item, AppAttributeDTO.class))
                    .collect(Collectors.toList());
        }
        spuDTO.setAttribute_list(attributeDTOList);

        // 3、规格信息
        List<PmsSpec> specList = iPmsSpecService.list(new LambdaQueryWrapper<PmsSpec>().
                eq(PmsSpec::getSpu_id, id));
        List<AppSpecDTO> appSpecDTOList = new ArrayList<>();
        if (specList!=null&&specList.size()>0){
            appSpecDTOList = specList.stream().map(item -> dozerBeanMapper.map(item, AppSpecDTO.class))
                    .collect(Collectors.toList());
        }
        spuDTO.setSpec_list(appSpecDTOList);

        // 4、SKU列表
        AppSkuDTO appSkuDTO = new AppSkuDTO();
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
        List<List<String>> specificationsList = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, List<PmsSpec>> entry : specMap.entrySet()) {
            List<String> specifications = new ArrayList<>();
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
                    specifications.add(item.getValue());
                });
            }
            tree.setV(vList);// v
            tree.setK_s("s" + ++i);// k_s
            treeList.add(tree);
            specificationsList.add(specifications);
        }

        appSkuDTO.setTree(treeList);

        List<AppSkuDTO.Sku> list = new ArrayList<>();
        List<PmsSku> pmsSkuList = iPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpu_id, id));
        List<List<String>> skuSpecListList = pmsSkuList.stream().map(item -> JSON.parseArray(item.getSpecs(), String.class))
                .collect(Collectors.toList());
        boolean none_sku = true; //是否是无规格商品
        int stock_num = 0;
        if (skuSpecListList != null && skuSpecListList.size() > 0) {
            if (specificationsList != null && specificationsList.size() > 0) {
                List<List<String>> specificationsListList = getDescartes(specificationsList);
                if (specificationsListList != null && specificationsListList.size() > 0) {
                    for (int m = 0; m < skuSpecListList.size(); m++) {
                        for (List<String> specificationList : specificationsListList) {
                            AppSkuDTO.Sku sku = new AppSkuDTO.Sku();
                            if (skuSpecListList.get(m).containsAll(specificationList)) {
                                if (skuSpecListList.get(m).size() >= 1) {
                                    none_sku = false;
                                    sku.setId(pmsSkuList.get(m).getId());
                                    sku.setPrice(new BigDecimal(100).multiply(pmsSkuList.get(m).getPrice()));
                                    int stockNum = pmsSkuList.get(m).getStock() - pmsSkuList.get(m).getStock_locked();
                                    sku.setStock_num(stockNum);
                                    stock_num += stockNum;
                                    sku.setS1("0");
                                    sku.setS2("0");
                                    sku.setS3("0");
                                    String value = skuSpecListList.get(m).get(0);//黑色
                                    sku.setS1(
                                            specList.stream().filter(item -> item.getValue().equals(value))
                                                    .map(item -> item.getId()).collect(Collectors.toList())
                                                    .get(0).toString()
                                    );
                                }

                                if (skuSpecListList.get(m).size() >= 2) {
                                    String value = skuSpecListList.get(m).get(1);//6G
                                    sku.setS2(
                                            specList.stream().filter(item -> item.getValue().equals(value))
                                                    .map(item -> item.getId()).collect(Collectors.toList())
                                                    .get(0).toString()
                                    );
                                }

                                if (skuSpecListList.get(m).size() >= 3) {
                                    String value = skuSpecListList.get(m).get(2);//128G
                                    sku.setS3(
                                            specList.stream().filter(item -> item.getValue().equals(value))
                                                    .map(item -> item.getId()).collect(Collectors.toList())
                                                    .get(0).toString()
                                    );
                                }
                            }
                            if (sku.getId() != null) {
                                list.add(sku);
                            }
                        }
                    }
                }
            }
        }
        appSkuDTO.setStock_num(stock_num);
        appSkuDTO.setNone_sku(none_sku);
        appSkuDTO.setPrice("0.00");

        appSkuDTO.setList(list);
        spuDTO.setSku(appSkuDTO);
        return spuDTO;
    }


    private static <T> List<List<T>> getDescartes(List<List<T>> list) {
        List<List<T>> returnList = new ArrayList<>();
        descartesRecursive(list, 0, returnList, new ArrayList<T>());
        return returnList;
    }


    /**
     * 递归实现
     * 原理：从原始list的0开始依次遍历到最后
     *
     * @param originalList 原始list
     * @param position     当前递归在原始list的position
     * @param returnList   返回结果
     * @param cacheList    临时保存的list
     */
    private static <T> void descartesRecursive(List<List<T>> originalList, int position, List<List<T>> returnList, List<T> cacheList) {
        List<T> originalItemList = originalList.get(position);
        for (int i = 0; i < originalItemList.size(); i++) {
            //最后一个复用cacheList，节省内存
            List<T> childCacheList = (i == originalItemList.size() - 1) ? cacheList : new ArrayList<>(cacheList);
            childCacheList.add(originalItemList.get(i));
            if (position == originalList.size() - 1) {//遍历到最后退出递归
                returnList.add(childCacheList);
                continue;
            }
            descartesRecursive(originalList, position + 1, returnList, childCacheList);
        }
    }


}
