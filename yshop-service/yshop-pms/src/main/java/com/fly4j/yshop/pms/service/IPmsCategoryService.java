package com.fly4j.yshop.pms.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.yshop.pms.pojo.dto.admin.PmsCategoryDTO;
import com.fly4j.yshop.pms.pojo.entity.PmsCategory;
import com.fly4j.yshop.pms.pojo.vo.CascaderVO;

import java.util.List;
import java.util.Map;


public interface IPmsCategoryService extends IService<PmsCategory> {

    List<CascaderVO> cascadeList();

    List<PmsCategoryDTO> treeList(Map<String, Object> paramMap);
}
