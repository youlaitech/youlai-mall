package com.youlai.mall.pms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PmsSpec {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long categoryId;
    private String name;

    @TableField(exist = false)
    private List<PmsSpuSpecValue> values = new ArrayList<>();

}
