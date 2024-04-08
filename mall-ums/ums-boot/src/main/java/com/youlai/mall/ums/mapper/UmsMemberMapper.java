package com.youlai.mall.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.ums.model.bo.MemberBO;
import com.youlai.mall.ums.model.entity.UmsMember;
import com.youlai.mall.ums.model.query.MemberPageQuery;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 会员访问层
 *
 * @author Ray Hao
 * @since 2024/4/7
 */
@Mapper
public interface UmsMemberMapper extends BaseMapper<UmsMember> {


    /**
     * 分页查询会员列表
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return
     */
    Page<MemberBO> listPagedMembers(Page<MemberBO> page, MemberPageQuery queryParams);
}
