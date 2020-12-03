package com.fanyao.dubbo.web.user.exception;

import com.fanyao.enums.ResultEnum;
import com.fanyao.exception.BusinessException;
import com.fanyao.response.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: bugProvider
 * @date: 2019/10/8 09:45
 * @description: 异常处理
 */
@Slf4j
@ControllerAdvice
@RestController
public class ControllerExceptionHandler {

    /**
     * 处理所有业务异常
     *
     * @param e 业务异常
     * @return 异常信息
     */
    @ExceptionHandler(BusinessException.class)
    CommonResult handleBusinessException(BusinessException e) {
        log.error("业务异常-> {}", e.getMessage(), e);
        return CommonResult.errorResult(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RpcException.class)
    CommonResult handleRpcException(Exception e) {
        log.error("下游服务调用异常-> {}", e.getMessage(), e);
        return CommonResult.errorResult(ResultEnum.FAILURE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    CommonResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("请求方法不支持-> {}", e.getMessage(), e);
        return CommonResult.errorResult(ResultEnum.METHOD_UN_SUPPORT);
    }



    /**
     * 处理所有不可知的异常
     *
     * @param e 运行时异常
     * @return 异常信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    CommonResult handleException(Exception e) {
        log.error("系统运行时异常-> {}", e.getMessage(), e);
        return CommonResult.errorResult(ResultEnum.FAILURE);
    }

    /**
     * Assert 异常
     *
     * @param e 主动预言异常
     * @return 异常信息
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    CommonResult handleIllegalArgumentException(Exception e) {
        log.error("系统运行时内部参数异常-> {}", e.getMessage(), e);
        return CommonResult.errorResult(ResultEnum.FAILURE);
    }


    /**
     * 处理所有接口数据验证异常
     *
     * @param e 参数异常
     * @return 异常信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    CommonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数校验异常->请求参数: {}  错误: {}  异常:", Objects.requireNonNull(e.getBindingResult().getTarget()).toString(), Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(), e);

//        多种格式 Map<String, String> errorMap = new HashMap<>(2);
//        fieldErrors.forEach(it-> errorMap.put(it.getField(), it.getDefaultMessage()));

        return getErrorList(e.getBindingResult());
    }

    /**
     * 违反约束异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    CommonResult handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        List<String> errorList = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());

        return CommonResult.errorResult(ResultEnum.PARAM_VERIFICATION_FAILED, errorList);
    }

    /**
     * 处理验证参数封装错误时异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    CommonResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return CommonResult.errorResult(ResultEnum.PARAM_VERIFICATION_FAILED);
    }

    /**
     * 处理参数绑定时异常（反400错误码）
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    CommonResult handleBindException(BindException e) {
        return getErrorList(e.getBindingResult());
    }

    /**
     * 获取参数校验具体异常信息
     *
     * @param bindingResult
     */
    private CommonResult getErrorList(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> errorList = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

        return CommonResult.errorResult(ResultEnum.PARAM_VERIFICATION_FAILED, errorList);
    }

}
