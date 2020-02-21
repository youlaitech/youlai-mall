package com.fly.system.aspect;

import com.fly.common.datasource.DynamicDataSourceContextHolder;
import com.fly.common.enums.DataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class DataSourceAspect {
    @Pointcut("execution(* com.fly.system.mapper.mysql..*.*(..))")
    private void mysqlAspect(){

    }

    @Pointcut("execution(* com.fly.system.mapper.oracle..*.*(..))")
    private void oracleAspect(){

    }

    @Before("mysqlAspect()")
    public void mysql(){
        log.info("切换到mysql的数据源");
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.mysql.getValue());
    }

    @Before("oracleAspect()")
    public void oracle(){
        log.info("切换到oracle的数据源");
        DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.oracle.getValue());
    }

}
