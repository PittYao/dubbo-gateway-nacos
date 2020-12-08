package com.fanyao.api.base.system.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: bugProvider
 * @date: 2020/12/7 13:54
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysUserDTO implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 用户名
     */
    private String userName;

    private List<String> roles;

}
