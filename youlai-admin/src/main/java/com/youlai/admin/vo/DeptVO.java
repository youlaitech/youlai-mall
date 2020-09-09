package com.youlai.admin.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class DeptVO {

    private Integer id;

    private String name;

    private Integer parentId;

    private String treePath;

    private Integer sort;

    private Integer status;

    private String leader;

    private String mobile;

    private String email;


    private List<DeptVO> children;

}
