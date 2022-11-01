package com.youlai.common.mybatis.handler;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.youlai.common.base.IBaseEnum;
import com.youlai.common.enums.DataScopeEnum;
import com.youlai.common.mybatis.annotation.DataPermission;
import com.youlai.common.security.util.SecurityUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;

import java.lang.reflect.Method;

/**
 * 数据权限控制器
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2021-12-10 13:28
 */
@Slf4j
public class MyDataPermissionHandler implements DataPermissionHandler {

    public static final String DPET_ID_COLUMN_NAME = "dept_id";
    public static final String USER_ID_COLUMN_NAME = "create_by";


    @Override
    @SneakyThrows
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        // 超级管理员不受数据权限控制
        if (SecurityUtils.isRoot()) {
            return where;
        }
        Class<?> clazz = Class.forName(mappedStatementId.substring(0, mappedStatementId.lastIndexOf(".")));
        String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(".") + 1);
        clazz.getAnnotatedSuperclass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            DataPermission annotation = method.getAnnotation(DataPermission.class);
            if (ObjectUtils.isNotEmpty(annotation)
                    && (method.getName().equals(methodName) || (method.getName() + "_COUNT").equals(methodName))) {
                return dataScopeFilter(annotation.deptAlias(), annotation.userAlias(), where);
            }
        }
        return where;
    }

    /**
     * 构建过滤条件
     *
     * @param where 当前查询条件
     * @return 构建后查询条件
     */
    public static Expression dataScopeFilter(String deptAlias, String userAlias, Expression where) {


        String deptColumnName = DPET_ID_COLUMN_NAME;
        if (StrUtil.isNotBlank(deptAlias)) {
            deptColumnName = deptAlias + "." + DPET_ID_COLUMN_NAME;
        }

        String userColumnName = USER_ID_COLUMN_NAME;
        if (StrUtil.isNotBlank(userAlias)) {
            userColumnName = userAlias + "." + USER_ID_COLUMN_NAME;
        }


        // 获取当前用户的数据权限
        Integer dataScope = SecurityUtils.getDataScope();


        DataScopeEnum dataScopeEnum = IBaseEnum.getEnumByValue(dataScope, DataScopeEnum.class);

        Long deptId = null, userId = null;
        String appendSqlStr = null;

        switch (dataScopeEnum) {
            case DEPT_AND_SUB:
                deptId = SecurityUtils.getDeptId();
                appendSqlStr = deptColumnName + " IN ( SELECT id FROM sys_dept WHERE id = " + deptId + " or find_in_set( " + deptId + " , tree_path ) )";
                break;
            case DEPT:
                deptId = SecurityUtils.getDeptId();
                appendSqlStr = deptColumnName + "=" + deptId;
                break;
            case SELF:
                userId = SecurityUtils.getUserId();
                appendSqlStr = userColumnName + "=" + userId;
                break;
        }

        Expression appendExpression = null;
        if (StrUtil.isNotBlank(appendSqlStr)) {
            try {
                appendExpression = CCJSqlParserUtil.parseCondExpression(appendSqlStr);
            } catch (JSQLParserException e) {
                throw new RuntimeException(e);
            }
        }

        if (appendExpression == null) {
            return where;
        }

        return new AndExpression(where, appendExpression);
    }


}

