package com.youlai.admin.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.entity.SysRole;
import com.youlai.admin.pojo.form.RoleForm;
import com.youlai.admin.pojo.vo.role.RolePageVO;
import com.youlai.common.web.domain.Option;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 角色实体转换器
 *
 * @author haoxr
 * @date 2022/5/29
 */
@Mapper(componentModel = "spring")
public interface SysRoleConvert {

    Page<RolePageVO> role2Page(Page<SysRole> page);


    @Mappings({
            @Mapping(target = "value", source = "id"),
            @Mapping(target = "label", source = "name")
    })
    Option role2Option(SysRole role);


    List<Option> roles2Options(List<SysRole> roleList);


    SysRole form2Entity(RoleForm roleForm);
}