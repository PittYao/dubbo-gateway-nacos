package com.fanyao.web.auth.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fanyao.api.base.system.dto.UserDTO;
import com.fanyao.api.base.system.entity.SysUser;
import com.fanyao.api.base.system.service.ISysUserService;
import com.fanyao.common.core.dto.JwtDTO;
import com.fanyao.common.core.enums.UserEnum;
import com.fanyao.common.core.exception.BusinessException;
import com.fanyao.common.core.util.JWTUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Reference
    private ISysUserService sysUserService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    /**
     * 密码登录
     *
     * @param userDTO 登录信息
     * @return token
     */
    @PostMapping("/login/password")
    public JwtDTO loginPassword(@RequestBody @Validated UserDTO userDTO) {
        // 请求 用户服务 查询用户信息
        SysUser sysUser = sysUserService.getSysUserByLoginName(userDTO.getLoginName());
        if (Objects.isNull(sysUser)) {
            throw new BusinessException(UserEnum.USER_NOT_EXIST);
        }

        if (!userDTO.getPassword().equals(sysUser.getPassword())) {
            throw new BusinessException(UserEnum.PWD_ERROR);
        }

        // 账号密码正确,颁发token,包含用户基本信息和用户角色列表,密码不能写入token TODO 查询用户角色列表
        Map<String, String> map = new HashMap<>();
        map.put("id", sysUser.getId().toString());
        map.put("userName", sysUser.getLoginName());
        map.put("roles", CollectionUtils.EMPTY_COLLECTION.toString());
        // 颁发token，token会加密
        String token = JWTUtil.getToken(map);
        // throw new JwtException(500, "测试token生成失败");
        log.info("颁发token:{}", token);
        // token存入redis  redis可控制token强制下线
        stringRedisTemplate.opsForValue().set(sysUser.getId().toString(),token);

        return JwtDTO.builder().token(token).build();
    }

    /**
     * 解析token
     */
    @GetMapping("/decodeToken")
    public List<String> decodeToken(String token) {
        DecodedJWT verify = JWTUtil.verify(token);
        String id = verify.getClaim("id").asString();
        String userName = verify.getClaim("userName").asString();
        String roles = verify.getClaim("roles").asString();

        return Lists.newArrayList(id, userName, roles);
}

    @GetMapping("/error")
    public String error() {
        log.info("请求模拟错误测试");
        throw new RuntimeException("test");
    }
}
