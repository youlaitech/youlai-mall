package com.youlai.admin.pojo.vo;


import lombok.Data;

import java.util.List;

@Data
public class TreeVO {

    private Long id;

    private String label;

    private List<TreeVO> children;

}
