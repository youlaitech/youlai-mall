package com.youlai.gateway.component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
* @author DaniR
* @version 1.0
* @description 本地缓存设置
* @createDate 2021/6/16 10:08
*/
@Slf4j
@Component
public class AdminRoleLocalCache<T> {
    private Cache<String,T> localCache = null;

   @PostConstruct
   private void init(){
       localCache = CacheBuilder.newBuilder()
               //设置本地缓存容器的初始容量
               .initialCapacity(1)
               //设置本地缓存的最大容量
               .maximumSize(10)
               //设置写缓存后多少秒过期
               .expireAfterWrite(120, TimeUnit.SECONDS).build();
   }


    public void setLocalCache(String key,T object){
        localCache.put(key,object);
    }

    public <T> T getCache(String key){
        return (T) localCache.getIfPresent(key);
    }
    public void remove(String key){
        localCache.invalidate(key);
    }
}
