package com.youlai.system.converter;

import com.youlai.system.pojo.entity.SysMenu;
import com.youlai.system.pojo.vo.menu.MenuVO;
import com.youlai.system.pojo.vo.menu.ResourceVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 菜单对象转换器
 *
 * @author haoxr
 * @date 2022/7/29
 */
@Mapper(componentModel = "spring")
public interface MenuConverter {

    MenuVO entity2VO(SysMenu entity);

    @Mappings({
            @Mapping(target = "value", source = "id"),
            @Mapping(target = "label", source = "name")
    })
    ResourceVO entity2ResourceVO(SysMenu entity);

}