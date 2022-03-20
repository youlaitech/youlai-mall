package com.youlai.admin.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.dto.AuthUserDTO;
import com.youlai.admin.pojo.entity.SysUser;
import com.youlai.admin.pojo.query.UserPageQuery;
import com.youlai.admin.pojo.vo.user.UserFormVO;
import com.youlai.admin.pojo.vo.user.UserPageVO;

/**
 * 用户业务接口
 *
 * @author haoxr
 * @date 2022/1/14
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 用户分页列表
     *
     * @return
     */
    IPage<UserPageVO> listUsersPage(UserPageQuery queryParams);

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    boolean saveUser(SysUser user);

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    boolean updateUser(SysUser user);

    /**
     * 根据用户名获取认证信息
     *
     * @param username
     * @return
     */
    AuthUserDTO getAuthInfoByUsername(String username);

    /**
     * 根据用户ID获取用户详情
     *
     * @param userId
     * @return
     */
    UserFormVO getUserFormDetail(Long userId);
}
