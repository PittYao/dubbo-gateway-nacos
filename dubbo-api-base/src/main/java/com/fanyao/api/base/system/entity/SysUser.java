package com.fanyao.api.base.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: bugProvider
 * @date: 2020/12/4 16:32
 * @description: 用户表
 */
@Data
@Builder
@TableName("t_sys_user")
public class SysUser implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 登录账号
     */
    @TableField("login_name")
    private String loginName;

    /**
     * 用户名
     */
    @TableField("name")
    private String name;

    /**
     * 密码
     */
    @TableField("password")
    private String password;
}
