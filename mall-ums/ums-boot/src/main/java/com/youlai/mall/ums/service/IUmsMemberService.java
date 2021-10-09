package com.youlai.mall.ums.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.vo.ProductHistoryVO;
import com.youlai.mall.ums.pojo.entity.UmsMember;

import java.util.Set;

public interface IUmsMemberService extends IService<UmsMember> {

    IPage<UmsMember> list(Page<UmsMember> page, String nickname);

    void addProductViewHistory(ProductHistoryVO product, Long userId);

    Set<ProductHistoryVO> getProductViewHistory(Long userId);
}
