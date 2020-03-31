package com.fly4j.shop.marketing.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.shop.marketing.pojo.dto.SpikeGoodsDTO;
import com.fly4j.shop.marketing.pojo.dto.SpikePeriodGoodsDTO;
import com.fly4j.shop.marketing.pojo.dto.SpikePeriodGoodsListDTO;
import com.fly4j.shop.marketing.pojo.entity.SpikePeriodGoodsEntity;
import com.fly4j.shop.marketing.remote.IRemoteGoodsService;
import com.fly4j.shop.marketing.service.ISpikePeriodGoodsService;
import com.netflix.discovery.converters.Auto;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/spikePeriodGoods")
public class SpikePeriodGoodsController {

    @Autowired
    private IRemoteGoodsService iRemoteGoodsService;

    @Autowired
    private ISpikePeriodGoodsService iSpikePeriodGoodsService;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @GetMapping("/spikeId/{spikeId}/spikePeriodId/{spikePeriodId}")
    public R<List<SpikePeriodGoodsDTO>> list(@PathVariable Long spikeId, @PathVariable Long spikePeriodId) {
        List<SpikePeriodGoodsEntity> list = iSpikePeriodGoodsService.list(new LambdaQueryWrapper<SpikePeriodGoodsEntity>()
                .eq(SpikePeriodGoodsEntity::getSpikeId, spikeId)
                .eq(SpikePeriodGoodsEntity::getSpikePeriodId, spikePeriodId)
                .orderByAsc(SpikePeriodGoodsEntity::getSort));
        List<SpikePeriodGoodsDTO> returnList = new ArrayList<>();
        list.forEach(item -> {
            SpikePeriodGoodsDTO spikeGoodsRelationDTO = dozerBeanMapper.map(item, SpikePeriodGoodsDTO.class);
            SpikeGoodsDTO goods = iRemoteGoodsService.spikeGoods(item.getGoodsId()).getData();
            if (goods != null) {
                spikeGoodsRelationDTO.setGoodsName(goods.getGoodsName());
                spikeGoodsRelationDTO.setGoodsPrice(goods.getGoodsPrice());
                spikeGoodsRelationDTO.setGoodsSn(goods.getGoodsSn());
                spikeGoodsRelationDTO.setGoodsStock(goods.getGoodsStock());
            }
            returnList.add(spikeGoodsRelationDTO);
        });
        return R.ok(returnList);
    }


    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<SpikeGoodsDTO>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SpikeGoodsDTO spikeGoodsDTO) {
        R<Page<SpikeGoodsDTO>> data = iRemoteGoodsService.page(pageNum, pageSize, spikeGoodsDTO);
        return data;
    }

    @GetMapping("/{id}")
    public R get(@PathVariable("id") Integer id) {
        SpikePeriodGoodsEntity spikePeriodGoodsEntity = iSpikePeriodGoodsService.getById(id);
        SpikeGoodsDTO goods = iRemoteGoodsService.spikeGoods(spikePeriodGoodsEntity.getGoodsId()).getData();
        SpikePeriodGoodsDTO spikeGoodsRelationDTO = new SpikePeriodGoodsDTO();
        BeanUtil.copyProperties(spikePeriodGoodsEntity, spikeGoodsRelationDTO);
        if (goods != null) {
            spikeGoodsRelationDTO.setGoodsName(goods.getGoodsName());
            spikeGoodsRelationDTO.setGoodsPrice(goods.getGoodsPrice());
            spikeGoodsRelationDTO.setGoodsSn(goods.getGoodsSn());
            spikeGoodsRelationDTO.setGoodsStock(goods.getGoodsStock());
        }
        return R.ok(spikeGoodsRelationDTO);
    }

    @PostMapping
    public R add(@RequestBody SpikePeriodGoodsListDTO spikePeriodGoodsListDTO) {
        boolean result = iSpikePeriodGoodsService.add(spikePeriodGoodsListDTO);
        if (result) {
            return R.ok("新增成功");
        } else {
            return R.failed("新增失败");
        }
    }

    @DeleteMapping("/{ids}")
    public R delete(@PathVariable Long[] ids) {
        boolean status = iSpikePeriodGoodsService.removeByIds(Arrays.asList(ids));
        if (status) {
            return R.ok("删除成功");
        } else {
            return R.failed("删除失败");
        }
    }
}
