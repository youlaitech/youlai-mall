package com.youlai.admin.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.dto.UserAuthDTO;
import com.youlai.admin.pojo.entity.SysUser;
import com.youlai.admin.pojo.query.UserPageQuery;
import com.youlai.admin.pojo.vo.UserDetailVO;

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
    IPage<SysUser> list(UserPageQuery userPageQuery);

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
     * 根据用户名获取认证用户信息，携带角色和密码
     *
     * @param username
     * @return
     */
    UserAuthDTO getByUsername(String username);

    /**
     * 根据用户ID获取用户详情
     *
     * @param userId
     * @return
     */
    UserDetailVO getUserDetailById(Long userId);
}
