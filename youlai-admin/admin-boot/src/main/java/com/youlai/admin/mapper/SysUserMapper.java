package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.dto.AuthUserDTO;
import com.youlai.admin.pojo.entity.SysUser;
import com.youlai.admin.pojo.query.UserPageQuery;
import com.youlai.admin.pojo.vo.user.UserFormVO;
import com.youlai.admin.pojo.vo.user.UserPageVO;
import com.youlai.common.mybatis.annotation.DataPermission;
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
     * @param pageParam
     * @param queryParam
     * @return
     */
    @DataPermission(deptAlias = "d")
    List<UserPageVO> listUsersWithPage(Page<UserPageVO> pageParam, UserPageQuery queryParam);

    /**
     * 根据用户ID获取用户详情
     *
     * @param userId
     * @return
     */
    UserFormVO getUserFormDetail(Long userId);

    /**
     * 根据用户名获取认证信息
     *
     * @param username
     * @return
     */
    AuthUserDTO getAuthInfoByUsername(String username);


}
