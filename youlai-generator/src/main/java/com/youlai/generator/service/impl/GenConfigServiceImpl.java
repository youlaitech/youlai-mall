package com.youlai.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.generator.mapper.GenConfigMapper;
import com.youlai.generator.model.entity.GenConfig;
import com.youlai.generator.service.GenConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 数据库服务实现类
 *
 * @author Ray
 * @since 2.10.0
 */
@Service
@RequiredArgsConstructor
public class GenConfigServiceImpl extends ServiceImpl<GenConfigMapper, GenConfig> implements GenConfigService {

}
