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
import java.util.List;

/**
 * @author: bugProvider
 * @date: 2020/12/4 16:32
 * @description: 菜单表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_sys_menu")
public class SysMenu implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("create_time")
    private Date createTime;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("grades")
    private Integer grades;

    @TableField("icon")
    private String icon;

    @TableField("is_visible")
    private Integer isVisible;

    @TableField("method")
    private String method;

    @TableField("name")
    private String name;

    @TableField("seq")
    private Integer seq;

    @TableField("url")
    private String url;

    @TableField("parent_id")
    private Integer parentId;

    @TableField(exist = false)
    private List<SysRole> roles;

    @TableField(exist = false)
    private List<SysMenu> children;

}
