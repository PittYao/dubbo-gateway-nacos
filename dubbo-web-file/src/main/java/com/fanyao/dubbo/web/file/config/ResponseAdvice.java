package com.fanyao.dubbo.web.file.config;

import com.fanyao.common.core.api.CommonResult;
import com.fanyao.common.core.enums.ResultEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * @author: bugProvider
 * @date: 2019/10/9 09:45
 * @description: json统一格式响应
 */
@RestControllerAdvice()
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Autowired
    private UnConversion unConversion;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (body instanceof CommonResult) {
            return body;
        }

        //  不包装为json格式的直接返回结果
        String path = serverHttpRequest.getURI().getPath();
        List<String> urls = unConversion.getUrls();
        if (CollectionUtils.isNotEmpty(urls)) {
            boolean anyMatch = urls.stream().anyMatch(url -> url.equals(path));
            if (anyMatch) {
                return body;
            }
        }

        // 确定请求方法的要返回的默认消息
        HttpMethod method = serverHttpRequest.getMethod();
//        URI uri = serverHttpRequest.getURI();
        ResultEnum resultEnum = ResultEnum.SUCCESS;
        if (method != null) {
            switch (method) {
                case GET:
                    resultEnum = ResultEnum.GET_SUCCESS;
                    break;
                case POST:
                    resultEnum = ResultEnum.POST_SUCCESS;
                    break;
                case PUT:
                    resultEnum = ResultEnum.PUT_SUCCESS;
                    break;
                case DELETE:
                    resultEnum = ResultEnum.DELETE_SUCCESS;
                    break;
                default:
                    break;
            }
        }

        return new CommonResult<>(resultEnum, body);
    }
}
