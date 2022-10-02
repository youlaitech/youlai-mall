package com.youlai.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.system.pojo.entity.SysOauthClient;
import com.youlai.system.pojo.vo.client.ClientPageVO;
import org.mapstruct.Mapper;

/**
 * 客户端对象转换器
 *
 * @author haoxr
 * @date 2022/6/11
 */
@Mapper(componentModel = "spring")
public interface ClientConverter {

    ClientPageVO entity2PageVO(SysOauthClient entity);

    Page<ClientPageVO> entity2PageVO(Page<SysOauthClient> entityPage);
}
