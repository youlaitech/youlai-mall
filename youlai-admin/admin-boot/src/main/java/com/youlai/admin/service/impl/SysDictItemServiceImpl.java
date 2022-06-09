package com.youlai.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.admin.mapper.SysDictItemMapper;
import com.youlai.admin.pojo.entity.SysDictItem;
import com.youlai.admin.pojo.form.DictItemForm;
import com.youlai.admin.pojo.query.DictItemPageQuery;
import com.youlai.admin.pojo.vo.dict.DictItemPageVO;
import com.youlai.admin.service.SysDictItemService;
import org.springframework.stereotype.Service;


/**
 * 字典数据项业务实现类
 */
@Service
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService {

    /**
     * 字典数据项分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public Page<DictItemPageVO> listPageDictItems(DictItemPageQuery queryParams) {
        return null;
    }

    /**
     * 字典数据项表单详情
     *
     * @param id 字典数据项ID
     * @return
     */
    @Override
    public DictItemForm getDictItemFormDetail(Long id) {
        return null;
    }

    /**
     * 新增字典数据项
     *
     * @param dictItemForm 字典数据项表单
     * @return
     */
    @Override
    public boolean saveDictItem(DictItemForm dictItemForm) {
        return false;
    }

    /**
     * 修改字典数据项
     *
     * @param id           字典数据项ID
     * @param dictItemForm 字典数据项表单
     * @return
     */
    @Override
    public boolean updateDictItem(Long id, DictItemForm dictItemForm) {
        return false;
    }

    /**
     * 删除字典数据项
     *
     * @param ids 字典数据项ID，多个以英文逗号(,)分割
     * @return
     */
    @Override
    public boolean deleteDictItems(String ids) {
        return false;
    }
}
