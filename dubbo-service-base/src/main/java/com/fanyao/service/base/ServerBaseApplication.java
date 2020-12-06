package com.fanyao.service.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
public class ServerBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerBaseApplication.class, args);
        System.out.println("基础服务启动");
    }

}
