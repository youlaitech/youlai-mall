package com.youlai.admin.vo;


import lombok.Data;

import java.util.List;

@Data
public class TreeSelectVO {

    private String id;

    private String label;

    private List<TreeSelectVO> children;

}
