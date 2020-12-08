package com.fanyao.web.auth.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: bugProvider
 * @date: 2020/12/8 09:57
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCheckDTO {
    @NotBlank(message = "验证url地址不能为空")
    private String authUrl;
    @NotBlank(message = "请求方法不能为空")
    private String method;
    @NotNull(message = "用户id能为空")
    private Integer userId;
    @NotBlank(message = "用户角色不能为空")
    private String roles;


}
