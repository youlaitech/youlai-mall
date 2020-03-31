package com.fly4j.shop.goods.api;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.shop.goods.pojo.dto.SpikeGoodsDTO;
import com.fly4j.shop.goods.service.IGoodsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 秒杀商品接口
 */
@RestController
@RequestMapping("/api/v1/spikeGoods")
public class SpikeGoodsAPI {

    @Resource
    private IGoodsService iGoodsService;

    @GetMapping("/pageNum/{pageNum}/pageSize/{pageSize}")
    public R<Page<SpikeGoodsDTO>> page(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SpikeGoodsDTO spikeGoodsDTO) {
        Page<SpikeGoodsDTO> page = new Page<>(pageNum, pageSize);
        Page<SpikeGoodsDTO> data =  iGoodsService.selectPage(page, spikeGoodsDTO);
        return R.ok(data);
    }


    @GetMapping("/{id}")
    public R<SpikeGoodsDTO> spikeGoods(@PathVariable("id") Long id){
        SpikeGoodsDTO data=iGoodsService.selectById(id);
        return R.ok(data);
    }

}
