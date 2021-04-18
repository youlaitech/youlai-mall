package com.youlai.admin.pojo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private Integer status;
    private List<Long> roleIds;

}
