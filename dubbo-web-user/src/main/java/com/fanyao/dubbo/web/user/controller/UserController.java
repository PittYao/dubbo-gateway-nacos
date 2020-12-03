package com.fanyao.dubbo.web.user.controller;

import com.fanyao.dto.UserDTO;
import com.fanyao.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Reference
    private IUserService userService;

    @Value("${server.port}")
    private Integer port;


    @PostMapping("/login")
    public UserDTO login(@RequestBody @Validated UserDTO userDTO) {
        log.info("请求进入服务端口:{}", port);
        return userService.login(userDTO);
    }

    @GetMapping("/error")
    public String error(){
        log.info("请求模拟错误测试");
        throw new RuntimeException("test");
    }
}
