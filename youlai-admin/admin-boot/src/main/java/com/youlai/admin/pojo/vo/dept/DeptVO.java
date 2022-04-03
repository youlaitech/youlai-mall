package com.youlai.admin.pojo.vo.dept;

import lombok.Data;

import java.util.List;

@Data
public class DeptVO {

    private Long id;

    private Long parentId;

    private String name;

    private String treePath;

    private Integer sort;

    private Integer status;

    private String leader;

    private String mobile;

    private String email;

    private List<DeptVO> children;

}
