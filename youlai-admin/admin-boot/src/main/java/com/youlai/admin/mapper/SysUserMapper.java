package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.dto.UserAuthDTO;
import com.youlai.admin.pojo.entity.SysUser;
import com.youlai.admin.pojo.query.UserPageQuery;
import com.youlai.admin.pojo.vo.UserDetailVO;
import com.youlai.common.mybatis.handler.InterceptorIgnore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户持久层
 *
 * @author haoxr
 * @date 2022/1/14
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 获取用户分页列表
     *
     * @param page
     * @param queryParam
     * @return
     */
    @InterceptorIgnore(deptAlias = "d")
    List<SysUser> list(Page<SysUser> page, UserPageQuery queryParam);

    UserAuthDTO getByUsername(String username);

    /**
     * 根据用户ID获取用户详情
     *
     * @param userId
     * @return
     */
    UserDetailVO getUserDetailById(Long userId);
}
