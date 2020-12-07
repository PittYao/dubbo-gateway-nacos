package com.fanyao.gateway.filter;

import com.fanyao.common.core.api.CommonResult;
import com.fanyao.common.core.enums.ResultEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @author: bugProvider
 * @date: 2020/12/7 10:28
 * @description:
 */
@Slf4j
@Data
@Component
@ConfigurationProperties("jwt")
public class JwtTokenFilter implements GlobalFilter, Ordered {
    private List<String> skipAuthUrls;
    private String header;

    private ObjectMapper objectMapper;

    public JwtTokenFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();

        //跳过不需要验证的路径
        if (CollectionUtils.isNotEmpty(skipAuthUrls) && skipAuthUrls.contains(url)) {
            return chain.filter(exchange);
        }

        //获取请求头中的token
        String token = exchange.getRequest().getHeaders().getFirst(header);
        ServerHttpResponse resp = exchange.getResponse();
        if (StringUtils.isBlank(token)) {
            //没有token
            log.info("请求头:{} 没有携带token", header);
            return authError(resp, ResultEnum.JWT_HEADER_NO_TOKEN);
        }
        //有token
//            try {
//                JwtUtil.checkToken(token, objectMapper);
//                return chain.filter(exchange);
//            } catch (ExpiredJwtException e) {
//                log.error(e.getMessage(), e);
//                if (e.getMessage().contains("Allowed clock skew")) {
//                    return authError(resp, "认证过期");
//                } else {
//                    return authError(resp, "认证失败");
//                }
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//                return authError(resp, "认证失败");
//            }
        return null;
    }

    /**
     * 认证错误输出
     *
     * @param resp 响应对象
     * @param msg  错误信息
     * @return
     */
    private Mono<Void> authError(ServerHttpResponse resp, ResultEnum errorEnum) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE);
        CommonResult commonResult = CommonResult.errorResult(errorEnum);

        String returnStr = "";
        try {
            returnStr = objectMapper.writeValueAsString(commonResult);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        DataBuffer buffer = resp.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));
        return resp.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
