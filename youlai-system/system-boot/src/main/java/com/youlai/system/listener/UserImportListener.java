package com.youlai.system.listener;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.constant.SystemConstants;
import com.youlai.common.enums.StatusEnum;
import com.youlai.common.result.ExcelResult;
import com.youlai.system.converter.UserConverter;
import com.youlai.system.enums.DictCodeEnum;
import com.youlai.system.model.dto.UserImportDTO;
import com.youlai.system.model.entity.*;
import com.youlai.system.service.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户导入监听器
 * <p>
 * <a href="https://easyexcel.opensource.alibaba.com/docs/current/quickstart/read#%E6%9C%80%E7%AE%80%E5%8D%95%E7%9A%84%E8%AF%BB%E7%9A%84%E7%9B%91%E5%90%AC%E5%99%A8">最简单的读的监听器</a>
 *
 * @author Ray
 * @since 2022/4/10
 */
@Slf4j
public class UserImportListener extends AnalysisEventListener<UserImportDTO> {

    /**
     * Excel 导入结果
     */
    @Getter
    private final ExcelResult excelResult;

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;
    private final UserRoleService userRoleService;

    private final List<Role> roleList;
    private final List<Dept> deptList;
    private final List<DictItem> genderList;

    /**
     * 当前行
     */
    private Integer currentRow = 1;

    /**
     * 构造方法
     * <p>在构造方法中给需要查询的内容查询好，尽量避免每条数据查询一次</p>
     */
    public UserImportListener() {
        this.userService = SpringUtil.getBean(UserService.class);
        this.passwordEncoder = SpringUtil.getBean(PasswordEncoder.class);
        this.userRoleService = SpringUtil.getBean(UserRoleService.class);
        this.userConverter = SpringUtil.getBean(UserConverter.class);
        this.roleList = SpringUtil.getBean(RoleService.class)
                .list(new LambdaQueryWrapper<Role>().eq(Role::getStatus, StatusEnum.ENABLE.getValue())
                        .select(Role::getId, Role::getCode));
        this.deptList = SpringUtil.getBean(DeptService.class)
                .list(new LambdaQueryWrapper<Dept>().select(Dept::getId, Dept::getCode));
        this.genderList = SpringUtil.getBean(DictItemService.class)
                .list(new LambdaQueryWrapper<DictItem>().eq(DictItem::getDictCode, DictCodeEnum.GENDER.getValue()));
        this.excelResult = new ExcelResult();
    }

    /**
     * 每一条数据解析都会来调用
     * <p>
     * 1. 数据校验；全字段校验
     * 2. 数据持久化；
     *
     * @param userImportDTO 一行数据，类似于 {@link AnalysisContext#readRowHolder()}
     */
    @Override
    public void invoke(UserImportDTO userImportDTO, AnalysisContext analysisContext) {
        log.info("解析到一条用户数据:{}", JSONUtil.toJsonStr(userImportDTO));

        boolean validation = true;
        String errorMsg = "第" + currentRow + "行数据校验失败：";
        String username = userImportDTO.getUsername();
        if (StrUtil.isBlank(username)) {
            errorMsg += "用户名为空；";
            validation = false;
        } else {
            long count = userService.count(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
            if (count > 0) {
                errorMsg += "用户名已存在；";
                validation = false;
            }
        }

        String nickname = userImportDTO.getNickname();
        if (StrUtil.isBlank(nickname)) {
            errorMsg += "用户昵称为空；";
            validation = false;
        }

        String mobile = userImportDTO.getMobile();
        if (StrUtil.isBlank(mobile)) {
            errorMsg += "手机号码为空；";
            validation = false;
        } else {
            if (!Validator.isMobile(mobile)) {
                errorMsg += "手机号码不正确；";
                validation = false;
            }
        }

        if (validation) {
            // 校验通过，持久化至数据库
            User entity = userConverter.toEntity(userImportDTO);
            entity.setPassword(passwordEncoder.encode(SystemConstants.DEFAULT_PASSWORD));   // 默认密码
            // 性别逆向翻译 根据字典标签得到字典值
            String genderLabel = userImportDTO.getGenderLabel();
            entity.setGender(getGenderValue(genderLabel));
            // 角色解析
            String roleCodes = userImportDTO.getRoleCodes();
            List<Long> roleIds = getRoleIds(roleCodes);
            // 部门解析
            String deptCode = userImportDTO.getDeptCode();
            entity.setDeptId(getDeptId(deptCode));

            boolean saveResult = userService.save(entity);
            if (saveResult) {
                excelResult.setValidCount(excelResult.getValidCount() + 1);
                // 保存用户角色关联
                if (CollectionUtil.isNotEmpty(roleIds)) {
                    List<UserRole> userRoles = roleIds.stream()
                            .map(roleId -> new UserRole(entity.getId(), roleId))
                            .collect(Collectors.toList());
                    userRoleService.saveBatch(userRoles);
                }
            } else {
                excelResult.setInvalidCount(excelResult.getInvalidCount() + 1);
                errorMsg += "第" + currentRow + "行数据保存失败；";
                excelResult.getMessageList().add(errorMsg);
            }
        } else {
            excelResult.setInvalidCount(excelResult.getInvalidCount() + 1);
            excelResult.getMessageList().add(errorMsg);
        }
        currentRow++;
    }


    /**
     * 根据角色编码获取角色ID
     *
     * @param roleCodes 角色编码 逗号分隔
     * @return 角色ID集合
     */
    private List<Long> getRoleIds(String roleCodes) {
        if (StrUtil.isNotBlank(roleCodes)) {
            String[] split = roleCodes.split(",");
            if (split.length > 0) {
                List<Long> roleIds = new ArrayList<>();
                for (String roleCode : split) {
                    this.roleList.stream().filter(r -> r.getCode().equals(roleCode))
                            .findFirst().ifPresent(role -> roleIds.add(role.getId()));
                }
                return roleIds.stream().distinct().toList();
            }
        }
        return Collections.emptyList();
    }

    /**
     * 根据部门编码获取部门ID
     *
     * @param deptCode 部门编码
     * @return 部门ID
     */
    private Long getDeptId(String deptCode) {
        if (StrUtil.isNotBlank(deptCode)) {
            return this.deptList.stream().filter(r -> r.getCode().equals(deptCode))
                    .findFirst().map(Dept::getId).orElse(null);
        }
        return null;
    }

    /**
     * 根据性别标签获取性别值
     *
     * @param genderLabel 性别标签
     * @return 性别值
     */
    private Integer getGenderValue(String genderLabel) {
        if (StrUtil.isNotBlank(genderLabel)) {
            return this.genderList.stream()
                    .filter(r -> r.getLabel().equals(genderLabel))
                    .findFirst()
                    .map(DictItem::getValue)
                    .map(Convert::toInt)
                    .orElse(null);
        }
        return null;
    }

    /**
     * 所有数据解析完成会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！");
    }

}
