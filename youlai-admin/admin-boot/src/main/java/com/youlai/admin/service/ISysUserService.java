package com.youlai.admin.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.dto.AuthUserDTO;
import com.youlai.admin.pojo.entity.SysUser;
import com.youlai.admin.pojo.form.UserForm;
import com.youlai.admin.pojo.form.UserImportForm;
import com.youlai.admin.pojo.query.UserPageQuery;
import com.youlai.admin.pojo.vo.user.UserDetailVO;
import com.youlai.admin.pojo.vo.user.UserExportVO;
import com.youlai.admin.pojo.vo.user.UserPageVO;

import java.io.IOException;
import java.util.List;

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
     * @param userForm 用户表单对象
     * @return
     */
    boolean saveUser(UserForm userForm);

    /**
     * 修改用户
     *
     * @param userId 用户ID
     * @param userForm 用户表单对象
     * @return
     */
    boolean updateUser(Long userId,UserForm userForm);

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
    UserDetailVO getUserDetail(Long userId);

    /**
     * 导入用户
     *
     * @param userImportForm
     * @return
     */
    String importUsers( UserImportForm userImportForm) throws IOException;

    /**
     * 获取导出用户列表
     *
     * @param queryParams
     * @return
     */
    List<UserExportVO> listExportUsers(UserPageQuery queryParams);
}
