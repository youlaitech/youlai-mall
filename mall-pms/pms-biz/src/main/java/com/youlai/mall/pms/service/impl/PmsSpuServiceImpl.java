package com.youlai.mall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.pms.bo.PmsSpuBO;
import com.youlai.mall.pms.pojo.PmsSku;
import com.youlai.mall.pms.pojo.PmsSpu;
import com.youlai.mall.pms.pojo.PmsSpuAttribute;
import com.youlai.mall.pms.pojo.PmsSpuSpecification;
import com.youlai.mall.pms.mapper.PmsSpuMapper;
import com.youlai.mall.pms.service.IPmsSkuService;
import com.youlai.mall.pms.service.IPmsSpuAttributeService;
import com.youlai.mall.pms.service.IPmsSpuService;
import com.youlai.mall.pms.service.IPmsSpuSpecificationService;
import com.youlai.mall.pms.pojo.dto.PmsSpuDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Service
@AllArgsConstructor
public class PmsSpuServiceImpl extends ServiceImpl<PmsSpuMapper, PmsSpu> implements IPmsSpuService {

    private IPmsSkuService iPmsSkuService;
    private IPmsSpuAttributeService iPmsSpuAttributeService;
    private IPmsSpuSpecificationService iPmsSpuSpecificationService;


    @Override
    public IPage<PmsSpu> list(Page<PmsSpu> page, PmsSpu spu) {
        List<PmsSpu> list = this.baseMapper.list(page, spu);
        page.setRecords(list);
        return page;
    }

    @Override
    public boolean add(PmsSpuBO spuBO) {
        PmsSpuDTO spuDTO = spuBO.getSpu();
        List<PmsSpuAttribute> attributes = spuBO.getAttributes();
        List<PmsSpuSpecification> specifications = spuBO.getSpecifications();
        List<PmsSku> skuList = spuBO.getSkuList();

        // spu保存
        PmsSpu spu = new PmsSpu();
        BeanUtil.copyProperties(spuDTO, spu);
        if (spuDTO.getPics() != null) {
            String album = JSONUtil.toJsonStr(spuDTO.getPics());
            spu.setAlbum(album);
        }
        this.save(spu);

        // 属性保存
        Optional.ofNullable(attributes).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSpuAttributeService.saveBatch(attributes);
        });

        // 规格保存
        Optional.ofNullable(specifications).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSpuSpecificationService.saveBatch(specifications);
        });

        // sku保存
        Optional.ofNullable(skuList).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSkuService.saveBatch(skuList);
        });

        return true;
    }

    @Override
    public PmsSpuBO getBySpuId(Long id) {
        // spu
        PmsSpuDTO spuVO = new PmsSpuDTO();
        PmsSpu spu = this.getById(id);
        BeanUtil.copyProperties(spu, spuVO);

        if (StrUtil.isNotBlank(spu.getAlbum())) {
            // spu专辑图片转换处理 json字符串 -> List
            List<String> pics = JSONUtil.toList(JSONUtil.parseArray(spu.getAlbum()), String.class);
            spuVO.setPics(pics);
        }

        // 属性
        List<PmsSpuAttribute> attributes = iPmsSpuAttributeService.
                list(new LambdaQueryWrapper<PmsSpuAttribute>().eq(PmsSpuAttribute::getSpuId, id));

        // 规格
        List<PmsSpuSpecification> specifications = iPmsSpuSpecificationService.list(
                new LambdaQueryWrapper<PmsSpuSpecification>().eq(PmsSpuSpecification::getSpuId, id)
        );

        // sku
        List<PmsSku> skuList = iPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, id));

        // 组合
        PmsSpuBO spuBO = new PmsSpuBO(spuVO, attributes, specifications, skuList);
        return spuBO;
    }

    @Override
    public boolean removeBySpuIds(List<Long> spuIds) {
        Optional.ofNullable(spuIds).ifPresent(
                ids -> ids.forEach(spuId -> {
                    // sku
                    iPmsSkuService.remove(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));

                    // 规格
                    iPmsSpuSpecificationService.remove(new LambdaQueryWrapper<PmsSpuSpecification>().eq(PmsSpuSpecification::getSpuId, spuId));

                    // 属性
                    iPmsSpuAttributeService.remove(new LambdaQueryWrapper<PmsSpuAttribute>().eq(PmsSpuAttribute::getSpuId, spuId));

                    // spu
                    this.removeById(spuId);
                })
        );
        return true;
    }

    @Override
    public boolean updateById(PmsSpuBO spuBO) {
        // spu
        PmsSpuDTO spuDTO = spuBO.getSpu();
        PmsSpu spu = new PmsSpu();
        BeanUtil.copyProperties(spuDTO, spu);
        if (CollectionUtil.isNotEmpty(spuDTO.getPics())) {
            String album = JSONUtil.toJsonStr(spuDTO.getPics());
            spu.setAlbum(album);
        }
        this.updateById(spu);

        // 属性保存
        List<PmsSpuAttribute> attributes = spuBO.getAttributes();
        Optional.ofNullable(attributes).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSpuAttributeService.saveBatch(attributes);
        });

        // 规格保存
        List<PmsSpuSpecification> specifications = spuBO.getSpecifications();
        Optional.ofNullable(specifications).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSpuSpecificationService.saveBatch(specifications);
        });

        // sku保存
        List<PmsSku> skuList = spuBO.getSkuList();
        Optional.ofNullable(skuList).ifPresent(list -> {
            list.forEach(item -> item.setSpuId(spu.getId()));
            iPmsSkuService.saveBatch(skuList);
        });
        return true;
    }
}
