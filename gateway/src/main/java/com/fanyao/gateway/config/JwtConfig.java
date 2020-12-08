package com.fanyao.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author: bugProvider
 * @date: 2020/12/7 13:24
 * @description:
 */
@Data
@Component
@ConfigurationProperties("jwt")
public class JwtConfig {
    private String userId = "userId";
    private String userName = "userName";
    private String roles = "roles";

    private String header;
    private String authServerUrl;
    private List<String> skipAuthUrls;
}
