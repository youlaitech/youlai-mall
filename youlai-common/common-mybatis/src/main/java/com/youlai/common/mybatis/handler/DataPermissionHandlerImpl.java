package com.youlai.common.mybatis.handler;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;

import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.web.util.JwtUtils;
import jdk.nashorn.internal.ir.JoinPredecessor;
import jdk.nashorn.internal.ir.LexicalContext;
import jdk.nashorn.internal.ir.LocalVariableConversion;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.OldOracleJoinBinaryExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
                    // 获取当前的用户角色
                    List<String> roles = JwtUtils.getRoles();
                    if( !roles.isEmpty() && roles.contains(GlobalConstants.ROOT_ROLE_CODE)) {
                        // 如果是超级管理员则放行
                        return where;
                    }else{
                        return dataScopeFilter(annotation.dataPermission(), where);
                    }
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
     * @param where 当前查询条件
     * @return 构建后查询条件
     */
    public static Expression dataScopeFilter(String dataPermission, Expression where) {
        Expression expression = null;
        if(dataPermission.equals("1")){
            return where;
        }else{
            EqualsTo equalsTo = new EqualsTo(new Column( "id"),getDeptId());
            expression = ObjectUtils.isNotEmpty(expression) ? new AndExpression(expression, equalsTo) : equalsTo;
            LikeExpression likeExpression = new LikeExpression();
            Function left = new Function();
            left.setName("concat");
            left.setParameters(new ExpressionList().addExpressions(new StringValue(","),new Column("tree_path"),new StringValue(",")));
            likeExpression.setLeftExpression(left);
            Function right = new Function();
            right.setName("concat");
            right.setParameters(new ExpressionList().addExpressions(new StringValue("%,"),getDeptId(),new StringValue("%,")));
            likeExpression.setRightExpression(right);
             expression = ObjectUtils.isNotEmpty(expression) ? new OrExpression(expression, likeExpression) : expression;
        }
        return ObjectUtils.isNotEmpty(where) ? new AndExpression(where, new Parenthesis(expression)) : expression;
    }

    /**
     * 当前用户的部门id
     * @return
     */
    private static Expression getDeptId(){
            return new LongValue(JwtUtils.getJwtPayload().getLong("deptId"));
    }


}

