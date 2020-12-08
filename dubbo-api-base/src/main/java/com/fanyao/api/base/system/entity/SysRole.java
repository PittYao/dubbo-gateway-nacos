package com.fanyao.api.base.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: bugProvider
 * @date: 2020/12/4 16:32
 * @description: 角色表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_sys_role")
public class SysRole implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("create_time")
    private Date createTime;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("role_alias")
    private String roleAlias;

    @TableField("role_name")
    private String roleName;

    @TableField("seq")
    private Integer seq;

    @TableField("parent_id")
    private Integer parentId;
}
