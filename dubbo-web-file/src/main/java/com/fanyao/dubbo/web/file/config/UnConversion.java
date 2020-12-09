package com.fanyao.dubbo.web.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: bugProvider
 * @date: 2020/12/9 14:40
 * @description:
 */
@Data
@Component
@ConfigurationProperties("unconversion")
public class UnConversion {
    private List<String> urls;

}
