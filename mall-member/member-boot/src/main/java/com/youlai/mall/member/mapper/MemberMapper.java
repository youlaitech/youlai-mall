package com.youlai.mall.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.member.model.bo.MemberBO;
import com.youlai.mall.member.model.entity.Member;
import com.youlai.mall.member.model.query.MemberPageQuery;
import org.apache.ibatis.annotations.Mapper;


/**
 * 会员访问层
 *
 * @author Ray.Hao
 * @since 2024/4/7
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {


    /**
     * 分页查询会员列表
     *
     * @param page 分页对象
     * @param queryParams 查询参数
     * @return
     */
    Page<MemberBO> getMemberPage(Page<MemberBO> page, MemberPageQuery queryParams);
}
