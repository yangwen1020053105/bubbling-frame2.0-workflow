package com.bubbling.workflow.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_act_deployment")
public class TActDeployment {
  @TableId(type = IdType.ASSIGN_UUID)
  private String id;
  private String devName;
  private String category;
  private String devKey;
  private String devId;
  @TableField(fill = FieldFill.INSERT)
  private String createUser;
  @TableField(fill = FieldFill.INSERT)
  private Date createTime;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private String updateUser;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;
  @TableField(fill = FieldFill.INSERT)
  @TableLogic
  private Integer isValid;

}
