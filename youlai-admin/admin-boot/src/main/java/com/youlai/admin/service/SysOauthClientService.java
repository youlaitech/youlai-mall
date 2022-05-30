package com.youlai.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.admin.pojo.entity.SysOauthClient;

public interface SysOauthClientService extends IService<SysOauthClient> {
    void cleanCache();
}
