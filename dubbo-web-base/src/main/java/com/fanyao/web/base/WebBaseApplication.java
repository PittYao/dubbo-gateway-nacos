package com.fanyao.web.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class}) // 排除启动自动加载数据配置，不配置会启动失败
public class WebBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebBaseApplication.class, args);
        System.out.println("web-base-controller启动");
    }

}
