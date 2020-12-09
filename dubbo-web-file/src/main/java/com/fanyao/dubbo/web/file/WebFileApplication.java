package com.fanyao.dubbo.web.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class WebFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebFileApplication.class, args);
        System.out.println("文件服务启动");
    }

}
