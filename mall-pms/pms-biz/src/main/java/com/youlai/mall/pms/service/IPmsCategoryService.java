package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.common.web.vo.CascaderVO;
import com.youlai.mall.pms.pojo.PmsCategory;
import com.youlai.mall.pms.vo.PmsCategoryVO;

import java.util.List;

public interface IPmsCategoryService extends IService<PmsCategory> {

    List<PmsCategoryVO> listForTree(PmsCategory category);

    List<CascaderVO> listForCascader(PmsCategory category);
}
