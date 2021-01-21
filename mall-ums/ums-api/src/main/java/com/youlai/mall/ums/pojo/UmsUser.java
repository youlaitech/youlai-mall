package com.youlai.mall.ums.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class UmsUser {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private Integer gender;

    private String nickname;

    private String mobile;

    private LocalDate birthday;

    private String avatar;

    private String openid;

    private String sessionKey;

    private Integer status;

    private Integer point;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer deleted;

    @TableField(exist = false)
    private List<UmsAddress> addressList;

    private Integer balance;

}
