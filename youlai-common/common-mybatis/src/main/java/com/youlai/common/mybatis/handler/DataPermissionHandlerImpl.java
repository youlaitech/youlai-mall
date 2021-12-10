package com.youlai.common.mybatis.handler;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;

import java.lang.reflect.Method;

/**
 * 部门数据权限
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2021-12-10 13:28
 */
@Slf4j
public class DataPermissionHandlerImpl implements DataPermissionHandler {

    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        try {
            Class<?> clazz = Class.forName(mappedStatementId.substring(0, mappedStatementId.lastIndexOf(".")));
            String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(".") + 1);
            clazz.getAnnotatedSuperclass();

            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                InterceptorIgnore annotation = method.getAnnotation(InterceptorIgnore.class);
                if (ObjectUtils.isNotEmpty(annotation) && (method.getName().equals(methodName) || (method.getName() + "_COUNT").equals(methodName))) {
                    // 获取当前的用户
                    log.info(annotation.dataPermission());
                    //LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
                    //if (ObjectUtils.isNotEmpty(loginUser) && ObjectUtils.isNotEmpty(loginUser.getUser()) && !loginUser.getUser().isAdmin()) {
                    //    return dataScopeFilter(loginUser.getUser(), annotation.value(), where);
                    //}
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return where;
    }

    /**
     * 构建过滤条件
     *
     * @param user 当前登录用户
     * @param where 当前查询条件
     * @return 构建后查询条件
     */
    //public static Expression dataScopeFilter(SysUser user, String tableAlias, Expression where) {
    //    Expression expression = null;
    //    for (SysRole role : user.getRoles()) {
    //        String dataScope = role.getDataScope();
    //        if (DataScopeAspect.DATA_SCOPE_ALL.equals(dataScope)) {
    //            return where;
    //        }
    //        if (DataScopeAspect.DATA_SCOPE_CUSTOM.equals(dataScope)) {
    //            InExpression inExpression = new InExpression();
    //            inExpression.setLeftExpression(buildColumn(tableAlias, "dept_id"));
    //            SubSelect subSelect = new SubSelect();
    //            PlainSelect select = new PlainSelect();
    //            select.setSelectItems(Collections.singletonList(new SelectExpressionItem(new Column("dept_id"))));
    //            select.setFromItem(new Table("sys_role_dept"));
    //            EqualsTo equalsTo = new EqualsTo();
    //            equalsTo.setLeftExpression(new Column("role_id"));
    //            equalsTo.setRightExpression(new LongValue(role.getRoleId()));
    //            select.setWhere(equalsTo);
    //            subSelect.setSelectBody(select);
    //            inExpression.setRightExpression(subSelect);
    //            expression = ObjectUtils.isNotEmpty(expression) ? new OrExpression(expression, inExpression) : inExpression;
    //        }
    //        if (DataScopeAspect.DATA_SCOPE_DEPT.equals(dataScope)) {
    //            EqualsTo equalsTo = new EqualsTo();
    //            equalsTo.setLeftExpression(buildColumn(tableAlias, "dept_id"));
    //            equalsTo.setRightExpression(new LongValue(user.getDeptId()));
    //            expression = ObjectUtils.isNotEmpty(expression) ? new OrExpression(expression, equalsTo) : equalsTo;
    //        }
    //        if (DataScopeAspect.DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope)) {
    //            InExpression inExpression = new InExpression();
    //            inExpression.setLeftExpression(buildColumn(tableAlias, "dept_id"));
    //            SubSelect subSelect = new SubSelect();
    //            PlainSelect select = new PlainSelect();
    //            select.setSelectItems(Collections.singletonList(new SelectExpressionItem(new Column("dept_id"))));
    //            select.setFromItem(new Table("sys_dept"));
    //            EqualsTo equalsTo = new EqualsTo();
    //            equalsTo.setLeftExpression(new Column("dept_id"));
    //            equalsTo.setRightExpression(new LongValue(user.getDeptId()));
    //            Function function = new Function();
    //            function.setName("find_in_set");
    //            function.setParameters(new ExpressionList(new LongValue(user.getDeptId()) , new Column("ancestors")));
    //            select.setWhere(new OrExpression(equalsTo, function));
    //            subSelect.setSelectBody(select);
    //            inExpression.setRightExpression(subSelect);
    //            expression = ObjectUtils.isNotEmpty(expression) ? new OrExpression(expression, inExpression) : inExpression;
    //        }
    //        if (DataScopeAspect.DATA_SCOPE_SELF.equals(dataScope)) {
    //            EqualsTo equalsTo = new EqualsTo();
    //            equalsTo.setLeftExpression(buildColumn(tableAlias, "create_by"));
    //            equalsTo.setRightExpression(new StringValue(user.getUserName()));
    //            expression = ObjectUtils.isNotEmpty(expression) ? new OrExpression(expression, equalsTo) : equalsTo;
    //        }
    //    }
    //    return ObjectUtils.isNotEmpty(where) ? new AndExpression(where, new Parenthesis(expression)) : expression;
    //}

    /**
     * 构建Column
     *
     * @param tableAlias 表别名
     * @param columnName 字段名称
     * @return 带表别名字段
     */
    //public static Column buildColumn(String tableAlias, String columnName) {
    //    if (StringUtils.isNotEmpty(tableAlias)) {
    //        columnName = tableAlias + "." + columnName;
    //    }
    //    return new Column(columnName);
    //}
}

