package com.youlai.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.system.model.entity.Dict;
import com.youlai.system.model.form.DictForm;
import com.youlai.system.model.query.DictPageQuery;
import com.youlai.system.model.vo.DictPageVO;
import com.youlai.system.model.vo.DictVO;

import java.util.List;

/**
 * 数据字典业务接口
 *
 * @author haoxr
 * @since 2022/10/12
 */
public interface DictService extends IService<Dict> {

    /**
     * 字典分页列表
     *
     * @param queryParams 分页查询对象
     * @return
     */
    Page<DictPageVO> getDictPage(DictPageQuery queryParams);

    /**
     * 获取字典表单详情
     *
     * @param id 字典ID
     * @return
     */
    DictForm getDictForm(Long id);

    /**
     * 新增字典
     *
     * @param dictForm 字典表单
     * @return
     */
    boolean saveDict(DictForm dictForm);

    /**
     * 修改字典
     *
     * @param id
     * @param dictForm 字典表单
     * @return
     */
    boolean updateDict(Long id, DictForm dictForm);

    /**
     * 删除字典
     *
     * @param ids 字典ID，多个以英文逗号(,)分割
     * @return
     */
    void deleteDictByIds(List<String> ids);

    /**
     * 获取字典列表（包含字典数据）
     *
     * @return 字典列表
     */
    List<DictVO> getAllDictWithData();
}
