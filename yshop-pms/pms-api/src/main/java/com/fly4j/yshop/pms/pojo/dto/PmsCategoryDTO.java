package com.fly4j.yshop.pms.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class PmsCategoryDTO implements Serializable {
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;
  private String name;
  private String description;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long parent_id;
  private Integer level;
  private String icon_url;
  private String pic_url;
  private Long sort;
  private Long is_show;

  private List<PmsCategoryDTO> children;
}
