package com.fanyao.dubbo.user.service.impl;


import com.fanyao.dto.UserDTO;
import com.fanyao.service.IUserService;
import org.apache.dubbo.config.annotation.Service;


@Service
public class UserServiceImpl implements IUserService {
    @Override
    public UserDTO login(UserDTO userDTO) {
        return UserDTO.builder().name("UserServiceImpl").build();
    }
}
