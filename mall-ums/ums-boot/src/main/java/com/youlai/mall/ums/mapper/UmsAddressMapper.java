package com.youlai.mall.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlai.mall.ums.pojo.entity.UmsAddress;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UmsAddressMapper extends BaseMapper<UmsAddress> {

    @Select("<script>" +
            " SELECT * from ums_address where member_id =#{userId} " +
            "</script>")
    List<UmsAddress> listByUserId(Long userId);

}
