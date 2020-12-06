package com.fanyao.web.base.controller;

import com.fanyao.api.base.system.dto.UserDTO;
import com.fanyao.api.base.system.entity.SysUser;
import com.fanyao.api.base.system.service.ISysUserService;
import com.fanyao.common.core.enums.UserEnum;
import com.fanyao.common.core.exception.BusinessException;
import com.fanyao.common.core.vo.LoginUserVo;
import com.fanyao.web.base.util.dozer.DozerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Reference
    private ISysUserService sysUserService;

    @Value("${server.port}")
    private Integer port;


    @PostMapping("/login")
    public LoginUserVo login(@RequestBody @Validated UserDTO userDTO) {
        log.info("请求进入服务端口:{}", port);
        SysUser sysUser = sysUserService.getSysUserByLoginName(userDTO.getLoginName());
        if (Objects.isNull(sysUser)) {
            throw new BusinessException(UserEnum.USER_NOT_EXIST);
        }
        return DozerUtil.map(sysUser, LoginUserVo.class);
    }

    @GetMapping("/error")
    public String error() {
        log.info("请求模拟错误测试");
        throw new RuntimeException("test");
    }
}
