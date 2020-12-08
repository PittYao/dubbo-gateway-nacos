package com.fanyao.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fanyao.common.core.api.CommonResult;
import com.fanyao.common.core.exception.JwtException;
import com.fanyao.common.core.util.JWTUtil;
import com.fanyao.gateway.config.JwtConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

/**
 * @author: bugProvider
 * @date: 2020/12/7 10:28
 * @description: 校验token
 */
@Slf4j
@Data
@Component
public class JwtTokenFilter implements GlobalFilter, Ordered {
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private RestTemplate restTemplate;

    private GatewayProperties gatewayProperties;

    private ObjectMapper objectMapper;

    public JwtTokenFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> skipAuthUrls = jwtConfig.getSkipAuthUrls();
        String header = jwtConfig.getHeader();
        // 当前请求的path，method
        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethodValue();

        // 跳过不需要验证的路径
        if (CollectionUtils.isNotEmpty(skipAuthUrls) && skipAuthUrls.contains(path)) {
            return chain.filter(exchange);
        }

        //获取请求头中的token
        String token = exchange.getRequest().getHeaders().getFirst(header);
        ServerHttpResponse resp = exchange.getResponse();
        if (StringUtils.isBlank(token)) {
            log.info("请求头:{} 没有携带token", header);
            return authError(resp, "请求头中" + header + "没有携带token");
        }

        try {
            // 解析token，过滤掉已过期的token，或者无效的token，返回token异常。避免又到下游服务去校验
            DecodedJWT decodedJWT = JWTUtil.verify(token);
            // 校验token中的用户信息是否正确
            String userId = decodedJWT.getClaim(jwtConfig.getUserId()).asString();
            String roles = decodedJWT.getClaim(jwtConfig.getRoles()).asString();
            if (Strings.isBlank(userId)) {
                log.error("token中不包含userId信息");
                return authError(resp, "认证失败");
            }

            try {
                // 校验该route是否有配置StripPrefix过滤器,存储截断后的新地址
                String newPath = path;
                newPath = getStripPrefixUrl(exchange, path, newPath);

                // 请求认证服务，查询用户是否存在和用户是否满足访问权限
                ResponseEntity<CommonResult> response = checkUserHasAuth(method, token, userId, roles, newPath);

                CommonResult result = response.getBody();
                if (Objects.isNull(result)) {
                    log.error("请求认证服务失败");
                    return authError(resp, "认证失败");
                }
                log.info("response={}", result.toString());

                if (result.getCode() != 200) {
                    // 异常
                    log.error("请求认证服务失败:{}", result.getMessage());
                    return authError(resp, result.getMessage());
                }

            } catch (Exception e) {
                log.error("请求认证服务失败:", e);
                return authError(resp, "认证失败");
            }
            return chain.filter(exchange);
        } catch (JWTDecodeException | JwtException e) {
            log.error(e.getMessage(), e);
            return authError(resp, e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return authError(resp, "认证失败");
        }
    }

    // 请求认证服务，查询用户是否存在和用户是否满足访问权限
    private ResponseEntity<CommonResult> checkUserHasAuth(String method, String token, String userId, String roles, String newPath) throws RestClientException {
        // 认证服务的接口
        String authServerUrl = jwtConfig.getAuthServerUrl();
        // 请求头携带token
        HttpHeaders headers = new HttpHeaders();
        headers.add(jwtConfig.getHeader(), token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        //放入body中的json参数
        JSONObject content = new JSONObject();
        content.put("authUrl", newPath);
        content.put("method", method);
        content.put("userId", userId);
        content.put("roles", roles);

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(content, headers);

        return restTemplate.exchange(authServerUrl, HttpMethod.POST, httpEntity, CommonResult.class);
    }

    //校验该route是否有配置StripPrefix过滤器
    private String getStripPrefixUrl(ServerWebExchange exchange, String path, String newPath) {
        // 校验该route是否有配置StripPrefix过滤器
        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        if (route != null) {
            List<GatewayFilter> filters = route.getFilters();
            if (CollectionUtils.isNotEmpty(filters)) {
                for (GatewayFilter filter : filters) {
                    // [StripPrefix parts = 1]
                    String filterParts = ((OrderedGatewayFilter) filter).getDelegate().toString();
                    if (filterParts.contains("StripPrefix")) {
                        // 包含url截断拦截器,获取截断的长度
                        int parts = Integer.parseInt(filterParts.substring(filterParts.lastIndexOf(" ") + 1, filterParts.length() - 1));
                        // 对url进行截断 参考StripPrefixGatewayFilterFactory.class
                        newPath = "/"
                                + Arrays.stream(org.springframework.util.StringUtils.tokenizeToStringArray(path, "/"))
                                .skip(parts).collect(Collectors.joining("/"));
                        newPath += (newPath.length() > 1 && path.endsWith("/") ? "/" : "");
                        break;
                    }
                }
            }
        }
        return newPath;
    }

    /**
     * 认证错误输出
     *
     * @param resp     响应对象
     * @param errorMsg 错误信息
     * @return
     */
    private Mono<Void> authError(ServerHttpResponse resp, String errorMsg) {
        resp.setStatusCode(HttpStatus.UNAUTHORIZED);
        resp.getHeaders().add(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE);
        CommonResult commonResult = CommonResult.errorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMsg);

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
