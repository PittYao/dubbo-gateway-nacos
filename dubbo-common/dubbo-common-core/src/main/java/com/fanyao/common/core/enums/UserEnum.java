package com.fanyao.common.core.enums;

import lombok.Getter;

/**
 * @author: bugProvider
 * @date: 2020/2/2 16:49
 * @description:
 */
@Getter
public enum UserEnum implements ExtensibleEnum {
    USER_EXIST(500, "账号已存在"),
    USER_NOT_EXIST(500, "账号不存在"),
    PWD_ERROR(500, "密码不正确"),
    OLD_PWD_ERROR(500, "原密码不正确"),
    PWD_SAME_ERROR(500, "新密码和原密码相同"),
    USER_NOT_ROLE(500, "用户未绑定角色");

    private Integer code;

    private String message;

    UserEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
