package com.bubbling.frame.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@TableName("t_ac_user")
public class TAcUser {
  @TableId(type = IdType.ASSIGN_UUID)
  private String id;
  private String loginName;
  private String password;
  private String userName;
  private short sex;
  private String email;
  private String phone;
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
