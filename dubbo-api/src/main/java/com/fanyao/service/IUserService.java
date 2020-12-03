package com.fanyao.service;

import com.fanyao.dto.UserDTO;

/**
 * @author: bugProvider
 * @date: 2020/12/3 14:07
 * @description:
 */
public interface IUserService {
    UserDTO login(UserDTO userDTO);
}
