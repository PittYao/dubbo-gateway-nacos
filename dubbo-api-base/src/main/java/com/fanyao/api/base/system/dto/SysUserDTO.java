package com.fanyao.api.base.system.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fanyao.common.core.validator.group.CreateGroup;
import com.fanyao.common.core.validator.group.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull(groups = {UpdateGroup.class})
    private Integer id;

    /**
     * 登录账号
     */
    @NotBlank(message = "登录名不能为空")
    private String loginName;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String password;

}
