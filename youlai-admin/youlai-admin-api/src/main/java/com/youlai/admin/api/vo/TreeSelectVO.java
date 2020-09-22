package com.youlai.admin.api.vo;


import lombok.Data;

import java.util.List;

@Data
public class TreeSelectVO {

    private Integer id;

    private String label;

    private List<TreeSelectVO> children;

}
