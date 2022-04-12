package com.youlai.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.dto.AuthUserDTO;
import com.youlai.admin.pojo.entity.SysUser;
import com.youlai.admin.pojo.query.UserPageQuery;
import com.youlai.admin.pojo.vo.user.UserExportVO;
import com.youlai.admin.pojo.vo.user.UserDetailVO;
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
     * @param page
     * @param queryParams 查询参数
     * @return
     */
    @DataPermission(deptAlias = "d")
    List<UserPageVO> listUsersPage(Page<UserPageVO> page, UserPageQuery queryParams);

    /**
     * 获取用户表单详情
     *
     * @param userId 用户ID
     * @return
     */
    UserDetailVO getUserDetail(Long userId);

    /**
     * 根据用户名获取认证信息
     *
     * @param username
     * @return
     */
    AuthUserDTO getAuthInfoByUsername(String username);

    /**
     * 获取导出用户列表
     *
     * @param queryParams
     * @return
     */
    @DataPermission(deptAlias = "d")
    List<UserExportVO> listExportUsers(UserPageQuery queryParams);
}
