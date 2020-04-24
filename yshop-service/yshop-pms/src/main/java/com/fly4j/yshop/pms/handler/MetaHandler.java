package com.fly4j.yshop.pms.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by XianRui on 2019-12-30 12:07
 **/
@Component
public class MetaHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("create_time", new Date(), metaObject);
        this.setFieldValByName("create_by", "fly4j", metaObject);
        this.setFieldValByName("update_time", new Date(), metaObject);
        this.setFieldValByName("update_by", "fly4j", metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("update_time", new Date(), metaObject);
        this.setFieldValByName("update_by", "fly4j", metaObject);
    }
}
