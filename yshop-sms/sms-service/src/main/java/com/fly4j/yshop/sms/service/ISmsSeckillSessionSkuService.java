package com.fly4j.yshop.sms.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.yshop.sms.pojo.dto.SmsSeckillSessionSkuDTO;
import com.fly4j.yshop.sms.pojo.entity.SmsSeckillSessionSku;


public interface ISmsSeckillSessionSkuService extends IService<SmsSeckillSessionSku> {

    boolean save(SmsSeckillSessionSkuDTO smsSeckillSessionSkuDTO);
}
