package com.fanyao.web.auth.controller;

import cn.hutool.crypto.SecureUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fanyao.api.base.system.dto.SysUserDTO;
import com.fanyao.api.base.system.dto.UserDTO;
import com.fanyao.api.base.system.entity.SysMenu;
import com.fanyao.api.base.system.entity.SysRole;
import com.fanyao.api.base.system.entity.SysUser;
import com.fanyao.api.base.system.service.ISysMenuService;
import com.fanyao.api.base.system.service.ISysUserService;
import com.fanyao.common.core.dto.JwtDTO;
import com.fanyao.common.core.enums.ResultEnum;
import com.fanyao.common.core.enums.UserEnum;
import com.fanyao.common.core.exception.BusinessException;
import com.fanyao.common.core.exception.JwtException;
import com.fanyao.common.core.util.JWTUtil;
import com.fanyao.web.auth.config.JwtConfig;
import com.fanyao.web.auth.pojo.dto.UserCheckDTO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.AntPathMatcher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Reference
    private ISysUserService sysUserService;
    @Reference
    private ISysMenuService sysMenuService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private JwtConfig jwtConfig;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 密码登录
     *
     * @param sysUserDTO 登录信息
     * @return token
     */
    @PostMapping("/login/password")
    public JwtDTO loginPassword(@RequestBody @Validated SysUserDTO sysUserDTO) {
        // 请求 用户服务 查询用户信息
        SysUser sysUser = sysUserService.getSysUserByLoginName(sysUserDTO.getLoginName());
        if (Objects.isNull(sysUser)) {
            throw new BusinessException(UserEnum.USER_NOT_EXIST);
        }

        // 比对加密后的密码是否相等
        if (!sysUserDTO.getPassword().equals(SecureUtil.md5(sysUserDTO.getPassword()))) {
            throw new BusinessException(UserEnum.PWD_ERROR);
        }

        // 账号密码正确,颁发token,包含用户基本信息和用户角色列表,注意：密码不能写入token
        Map<String, String> map = new HashMap<>();
        map.put(jwtConfig.getUserId(), sysUser.getId().toString());
        map.put(jwtConfig.getUserName(), sysUser.getLoginName());

        // 收集该用户所有角色名,用,号拼接角色名
        StringBuilder roleNames = new StringBuilder();
        if (CollectionUtils.isNotEmpty(sysUser.getRoles())) {
            for (SysRole role : sysUser.getRoles()) {
                roleNames.append(role.getRoleAlias())
                        .append(",");
            }
        }
        map.put(jwtConfig.getRoles(), roleNames.toString());

        // 颁发token，token会加密
        String token = JWTUtil.getToken(map);
        // throw new JwtException(500, "测试token颁发失败");
        // token存入redis  redis可控制token强制下线
        stringRedisTemplate.opsForValue().set(sysUser.getId().toString(), token);
        log.info("颁发token,已存入redis:{}", token);

        return JwtDTO.builder().token(token).build();
    }

    /**
     * 解析token
     */
    @GetMapping("/decodeToken")
    public List<String> decodeToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (Strings.isBlank(token)) {
            return null;
        }
        DecodedJWT verify = JWTUtil.verify(token);
        String id = verify.getClaim(jwtConfig.getUserId()).asString();
        String userName = verify.getClaim(jwtConfig.getUserName()).asString();
        String roles = verify.getClaim(jwtConfig.getRoles()).asString();

        return Lists.newArrayList(id, userName, roles);
    }

    @PostMapping("/users/check")
    public SysUser checkUserAuth(@Validated @RequestBody UserCheckDTO userCheckDTO, HttpServletRequest request) {
        // 1. 解析token
        String token = request.getHeader(jwtConfig.getHeader());
        if (Strings.isBlank(token)) {
            throw new JwtException(ResultEnum.JWT_HEADER_NO_TOKEN);
        }
        // ,号分隔为token中该用户持有的角色列表
        List<String> userHasRoleNames = Arrays.asList(userCheckDTO.getRoles().split(","));

        // 2. 校验用户是否存在
        SysUser sysUser = sysUserService.getSysUserAndRolesById(userCheckDTO.getUserId());
        if (Objects.isNull(sysUser)) {
            throw new JwtException(ResultEnum.USER_NOT_EXIST);
        }

        // 3. 校验用户是否有访问url的权限

        // 3.1 查询访问改url所需要的角色列表
        List<SysMenu> sysMenus = sysMenuService.listAllAndRole();
        if (CollectionUtils.isEmpty(sysMenus)) {
            throw new JwtException(ResultEnum.JWT_DB_NO_MENU);
        }

        List<String> urlNeedRoleNames = Lists.newArrayList();
        for (SysMenu sysMenu : sysMenus) {
            if (antPathMatcher.match(sysMenu.getUrl(), userCheckDTO.getAuthUrl())
                    && !CollectionUtils.isEmpty(sysMenu.getRoles()) &&
                    userCheckDTO.getMethod().equalsIgnoreCase(sysMenu.getMethod())) {
                // 构建 角色名 集合
                List<String> urlNeedRoleName = sysMenu.getRoles().stream().map(SysRole::getRoleAlias).collect(Collectors.toList());
                urlNeedRoleNames.addAll(urlNeedRoleName);
            }
        }
        if (CollectionUtils.isEmpty(urlNeedRoleNames)) {
            log.warn("该用户没有匹配该url的角色");
            throw new JwtException(ResultEnum.JWT_USER_NO_AUTHORITY);
        }
        // 3.2 比对token的角色列表是否包含url所需列表中的一个，即满足访问权限
        for (String role : urlNeedRoleNames) {
            if (userHasRoleNames.contains(role)) {
                // 满足
                return sysUser;
            }
        }
        // 用户没有权限
        throw new JwtException(ResultEnum.JWT_USER_NO_AUTHORITY);
    }

    @GetMapping("/error")
    public String error() {
        log.info("请求模拟错误测试");
        throw new RuntimeException("test");
    }
}
