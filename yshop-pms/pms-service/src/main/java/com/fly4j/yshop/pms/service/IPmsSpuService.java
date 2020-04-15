package com.fly4j.yshop.pms.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.yshop.pms.pojo.dto.PmsSpuDTO;
import com.fly4j.yshop.pms.pojo.entity.PmsSpu;


public interface IPmsSpuService extends IService<PmsSpu> {

    boolean add(PmsSpuDTO pmsSpuDTO);
}
