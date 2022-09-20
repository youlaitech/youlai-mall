package com.youlai.common.mybatis.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.mybatis.annotation.DataPermission;
import com.youlai.common.web.util.JwtUtils;
import com.youlai.common.web.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.pool.TypePool;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SubSelect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门数据权限
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2021-12-10 13:28
 */
@Slf4j
public class MyDataPermissionHandler implements DataPermissionHandler {

    /**
     * 全部数据权限
     */
    public static final Integer DATA_SCOPE_ALL = 1;


    /**
     * 部门数据权限
     */
    public static final Integer DATA_SCOPE_DEPT = 2;

    /**
     * 部门及以下数据权限
     */
    public static final Integer DATA_SCOPE_DEPT_AND_CHILD =3;

    /**
     * 仅本人数据权限
     */
    public static final Integer DATA_SCOPE_SELF = 4;


    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        try {
            Class<?> clazz = Class.forName(mappedStatementId.substring(0, mappedStatementId.lastIndexOf(".")));
            String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(".") + 1);
            clazz.getAnnotatedSuperclass();
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                DataPermission annotation = method.getAnnotation(DataPermission.class);
                if (ObjectUtils.isNotEmpty(annotation) && (method.getName().equals(methodName) || (method.getName() + "_COUNT").equals(methodName))) {
                    // 获取当前的用户角色
                    List<String> roles = UserUtils.getRoles();
                    if (!roles.isEmpty() && roles.contains(GlobalConstants.ROOT_ROLE_CODE)) {
                        // 如果是超级管理员则放行
                        return where;
                    } else {
                        return dataScopeFilter(annotation.deptAlias(),annotation.userAlias(), where);
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
    public static Expression dataScopeFilter(String deptAlias,String userAlias, Expression where) {
        // 获取当前的用户数据权限
        List<Integer> dataScopes = UserUtils.getDataScopes();
        List<String> roles = UserUtils.getRoles();
        Long deptId = JwtUtils.getJwtPayload().getLong("deptId");
        Long userId = JwtUtils.getJwtPayload().getLong("userId");
        String deptIdColumn =StrUtil.isEmptyIfStr(deptAlias)?"id":deptAlias+".id";
                Expression newWhere = null;
        for (int i=0;i<dataScopes.size();i++) {
            Integer dataScope = dataScopes.get(i);
            String role = roles.get(i);
            if(dataScope == DATA_SCOPE_ALL){
                break;
            }else if(dataScope == DATA_SCOPE_DEPT){
                newWhere = addWhereExpression(newWhere,deptIdColumn+"="+deptId);
            }else if(dataScope == DATA_SCOPE_DEPT_AND_CHILD){
                newWhere = addWhereExpression(newWhere,deptIdColumn+" IN ( SELECT id FROM sys_dept WHERE id = "+deptId+" or find_in_set( "+deptId+" , tree_path ) )");
            }else if(dataScope == DATA_SCOPE_SELF){
                if (StrUtil.isNotBlank(userAlias))
                {
                    newWhere = addWhereExpression(newWhere,userAlias+".id="+userId);
                }
                else
                {
                    // 数据权限为仅本人且没有userAlias别名不查询任何数据
                    newWhere = addWhereExpression(newWhere,deptIdColumn+"=0");
                }
            }
        }
        Expression expression = ObjectUtil.isEmpty(where)? newWhere : new AndExpression(where,newWhere);
        return expression ;
    }

    private static Expression addWhereExpression(Expression whereExpression,String expStr){
       Expression addExpression;
       Expression newExpression;
        try {
            addExpression = CCJSqlParserUtil.parseCondExpression(expStr);
        } catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }
        if(whereExpression == null){
            newExpression = addExpression;
       }else{
                newExpression = new OrExpression(whereExpression,addExpression);
        }
        return newExpression;
    }


    /**
     * 当前用户的部门id
     *
     * @return
     */
    private static Expression getDeptId() {
        LongValue deptId = new LongValue(JwtUtils.getJwtPayload().getLong("deptId"));
        return deptId;
    }

    private static Expression getUserId() {
        LongValue userId = new LongValue(JwtUtils.getJwtPayload().getLong("userId"));
        return userId;
    }


}

