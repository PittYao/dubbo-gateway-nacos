package com.fanyao.common.core.exception;

import com.fanyao.common.core.enums.ExtensibleEnum;
import lombok.Getter;

/**
 * @author: bugProvider
 * @date: 2019/10/3 21:19
 * @description: JWT异常
 */
@Getter
public class JwtException extends RuntimeException {
    private Integer code;

    public JwtException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public JwtException(ExtensibleEnum extensibleEnum) {
        super(extensibleEnum.getMessage());
        this.code = extensibleEnum.getCode();
    }

}
