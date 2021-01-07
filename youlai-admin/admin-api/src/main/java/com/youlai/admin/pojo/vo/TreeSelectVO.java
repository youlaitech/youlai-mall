package com.youlai.admin.pojo.vo;


import lombok.Data;

import java.util.List;

@Data
public class TreeSelectVO {

    private Long id;

    private String label;

    private List<TreeSelectVO> children;

}
