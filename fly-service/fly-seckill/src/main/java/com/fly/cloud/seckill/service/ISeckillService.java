package com.fly.cloud.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fly.cloud.seckill.exception.RepeatKillException;
import com.fly.cloud.seckill.exception.SeckillCloseException;
import com.fly.cloud.seckill.exception.SeckillException;
import com.fly.cloud.seckill.pojo.dto.Exposer;
import com.fly.cloud.seckill.pojo.dto.SeckillExecution;
import com.fly.cloud.seckill.pojo.entity.Seckill;

import java.math.BigDecimal;
import java.util.List;

public interface ISeckillService extends IService<Seckill> {
    /**
     * 获取所有的秒杀商品列表
     *
     * @return
     */
    List<Seckill> findAll();

    /**
     * 获取某一条商品秒杀信息
     *
     * @param seckillId
     * @return
     */
    Seckill findById(long seckillId);

    /**
     * 秒杀开始时输出暴露秒杀的地址
     * 否者输出系统时间和秒杀时间
     *
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀的操作
     *
     * @param seckillId
     * @param userPhone
     * @param money
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, BigDecimal money, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException;
}

