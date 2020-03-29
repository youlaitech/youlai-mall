package com.fly4j.shop.marketing.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.shop.marketing.pojo.dto.SeckillGoodsDTO;
import com.fly4j.shop.marketing.pojo.dto.SeckillGoodsRelationDTO;
import com.fly4j.shop.marketing.pojo.entity.SeckillGoodsRelation;
import com.fly4j.shop.marketing.remote.IRemoteGoodsService;
import com.fly4j.shop.marketing.service.ISeckillGoodsRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/seckillGoods")
public class SeckillGoodsRelationController {

    @Autowired
    private IRemoteGoodsService iRemoteGoodsService;

    @Autowired
    private ISeckillGoodsRelationService iSeckillGoodsRelationService;


    @GetMapping("/seckillId/{seckillId}/seckillSessionId/{seckillSessionId}")
    public R<List<SeckillGoodsRelationDTO>> list(@PathVariable Long seckillId, @PathVariable Long seckillSessionId) {
        List<SeckillGoodsRelation> list = iSeckillGoodsRelationService.list(new LambdaQueryWrapper<SeckillGoodsRelation>()
                .eq(SeckillGoodsRelation::getSeckillId, seckillId)
                .eq(SeckillGoodsRelation::getSeckillSessionId, seckillSessionId)
                .orderByAsc(SeckillGoodsRelation::getSort));
        List<SeckillGoodsRelationDTO> returnList = new ArrayList<>();
        list.forEach(item -> {
            SeckillGoodsRelationDTO seckillGoodsRelationDTO = new SeckillGoodsRelationDTO();
            BeanUtil.copyProperties(item, seckillGoodsRelationDTO);
            SeckillGoodsDTO goods = iRemoteGoodsService.getByGoodsId(item.getGoodsId()).getData();
            if (goods != null) {
                seckillGoodsRelationDTO.setGoodsName(goods.getGoodsName());
                seckillGoodsRelationDTO.setGoodsPrice(goods.getGoodsPrice());
                seckillGoodsRelationDTO.setGoodsSn(goods.getGoodsSn());
                seckillGoodsRelationDTO.setGoodsStock(goods.getGoodsStock());
            }
            returnList.add(seckillGoodsRelationDTO);
        });
        return R.ok(returnList);
    }


    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<SeckillGoodsDTO>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SeckillGoodsDTO seckillGoodsDTO) {
        R<Page<SeckillGoodsDTO>> data = iRemoteGoodsService.page(pageNum, pageSize);
        return data;
    }

    @GetMapping("/{id}")
    public R get(@PathVariable("id") Integer id) {
        SeckillGoodsRelation seckillGoodsRelation = iSeckillGoodsRelationService.getById(id);
        SeckillGoodsDTO goods = iRemoteGoodsService.getByGoodsId(seckillGoodsRelation.getGoodsId()).getData();
        SeckillGoodsRelationDTO seckillGoodsRelationDTO = new SeckillGoodsRelationDTO();
        if (goods != null) {
            seckillGoodsRelationDTO.setGoodsName(goods.getGoodsName());
            seckillGoodsRelationDTO.setGoodsPrice(goods.getGoodsPrice());
            seckillGoodsRelationDTO.setGoodsSn(goods.getGoodsSn());
            seckillGoodsRelationDTO.setGoodsStock(goods.getGoodsStock());
        }
        return R.ok(seckillGoodsRelationDTO);
    }

    @PostMapping
    public R add(@RequestBody SeckillGoodsRelation seckillGoodsRelation) {
        boolean result = iSeckillGoodsRelationService.save(seckillGoodsRelation);
        if (result) {
            return R.ok("新增成功");
        } else {
            return R.failed("新增失败");
        }
    }

    @PutMapping("/{id}")
    public R update(@PathVariable("id") Integer id, @RequestBody  SeckillGoodsRelation seckillGoodsRelation) {
        boolean result = iSeckillGoodsRelationService.updateById(seckillGoodsRelation);
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }


    @DeleteMapping("/{ids}")
    public R delete(@PathVariable Long[] ids) {
        boolean status = iSeckillGoodsRelationService.removeByIds(Arrays.asList(ids));
        if (status) {
            return R.ok("删除成功");
        } else {
            return R.failed("删除失败");
        }
    }


}
