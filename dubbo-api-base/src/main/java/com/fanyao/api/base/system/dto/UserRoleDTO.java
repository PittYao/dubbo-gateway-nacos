package com.fanyao.api.base.system.dto;

import com.fanyao.api.base.system.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: bugProvider
 * @date: 2019/8/22 13:41
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleDTO implements Serializable {

    private Integer code ;

    private String message ;

    private SysUser data;


}
