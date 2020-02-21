package com.fly.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("T_USER")
public class TUser {

    @TableId
    private Integer id;

    private String userName;

}
