package com.fly4j.yshop.pms.pojo.dto.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class PmsCategoryDTO implements Serializable {
  private Long id;
  private String name;
  private String description;
  private String icon_url;
  private Long parent_id;
  private Integer level;
  private String pic_url;
  private Long sort;
  private Long is_show;

  private List<PmsCategoryDTO> children;
}
