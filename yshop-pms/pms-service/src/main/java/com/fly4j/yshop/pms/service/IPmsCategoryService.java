package com.fly4j.yshop.pms.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.yshop.pms.pojo.entity.PmsCategory;
import com.fly4j.yshop.pms.pojo.vo.CascaderVO;

import java.util.List;


public interface IPmsCategoryService extends IService<PmsCategory> {

     List<CascaderVO> cascadeList();
}
