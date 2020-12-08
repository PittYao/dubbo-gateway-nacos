package com.fanyao.web.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


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
}
