package com.fanyao.web.auth.controller;

import com.fanyao.api.base.system.dto.UserDTO;
import com.fanyao.api.base.system.entity.SysUser;
import com.fanyao.api.base.system.service.ISysUserService;
import com.fanyao.common.core.enums.UserEnum;
import com.fanyao.common.core.exception.BusinessException;
import com.fanyao.common.core.vo.LoginUserVo;
import com.fanyao.web.auth.util.dozer.DozerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Reference
    private ISysUserService sysUserService;

    @PostMapping("/password")
    public LoginUserVo login(@RequestBody @Validated UserDTO userDTO) {
        // 请求 用户服务 查询用户信息
        SysUser sysUser = sysUserService.getSysUserByLoginName(userDTO.getLoginName());
        if (Objects.isNull(sysUser)) {
            throw new BusinessException(UserEnum.USER_NOT_EXIST);
        }

        if (!userDTO.getPassword().equals(sysUser.getPassword())) {
            throw new BusinessException(UserEnum.PWD_ERROR);
        }
        // 账号密码正确,颁发token
        return DozerUtil.map(sysUser, LoginUserVo.class);
    }

    @GetMapping("/error")
    public String error() {
        log.info("请求模拟错误测试");
        throw new RuntimeException("test");
    }
}
