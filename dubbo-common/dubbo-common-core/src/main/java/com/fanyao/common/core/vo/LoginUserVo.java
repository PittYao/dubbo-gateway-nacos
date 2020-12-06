package com.fanyao.common.core.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginUserVo implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 用户名
     */
    private String name;

}