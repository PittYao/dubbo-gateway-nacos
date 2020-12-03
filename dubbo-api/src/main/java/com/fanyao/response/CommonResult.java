package com.fanyao.response;

import com.fanyao.enums.ExtensibleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: bugProvider
 * @date: 2019/8/22 13:41
 * @description: 统一返回对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResult<T> {

    private Integer code ;

    private String message ;

    private T data;

    public CommonResult(ExtensibleEnum extensibleEnum, T data) {
        this.code = extensibleEnum.getCode();
        this.message = extensibleEnum.getMessage();
        this.data = data;
    }

    public static <T> CommonResult<T> errorResult(Integer code, String message){
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.code = code;
        commonResult.message = message;
        return commonResult;
    }

    public static <T> CommonResult<T> errorResult(ExtensibleEnum extensibleEnum,T data){
        CommonResult<T> commonResult = errorResult(extensibleEnum);
        commonResult.data = data;
        return commonResult;
    }

    public static <T> CommonResult<T> errorResult(ExtensibleEnum extensibleEnum){
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.code = extensibleEnum.getCode();
        commonResult.message = extensibleEnum.getMessage();
        return commonResult;
    }

}
