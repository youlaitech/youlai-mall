package com.youlai.mall.pms.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.youlai.mall.pms.pojo.dto.app.ProductFormDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
* @author DaniR
* @version 1.0
* @description 本地缓存设置
* @createDate 2021/6/16 10:08
*/
@Slf4j
@Component
public class ProductLocalCache {
   private Cache<String, ProductFormDTO> localCache = null;

   @PostConstruct
   private void init(){
       localCache = CacheBuilder.newBuilder()
               //设置本地缓存容器的初始容量
               .initialCapacity(10)
               //设置本地缓存的最大容量
               .maximumSize(500)
               //设置写缓存后多少秒过期
               .expireAfterWrite(60, TimeUnit.SECONDS).build();
   }


   public void setLocalCache(String key, ProductFormDTO object){
       localCache.put(key,object);
   }

   public ProductFormDTO get(String key){
       return localCache.getIfPresent(key);
   }
}
