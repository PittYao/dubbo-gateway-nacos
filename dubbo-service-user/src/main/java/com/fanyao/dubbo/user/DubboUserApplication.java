package com.fanyao.dubbo.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) // 排除启动报错
@EnableDiscoveryClient //能够让注册中心能够发现，扫描到该服务。
public class DubboUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboUserApplication.class, args);
        System.out.println("用户服务启动");
    }

}
