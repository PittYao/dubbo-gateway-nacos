package com.fanyao.service.base.system.service.impl;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fanyao.api.base.system.dto.SysUserDTO;
import com.fanyao.api.base.system.entity.SysUser;
import com.fanyao.api.base.system.service.ISysUserService;
import com.fanyao.service.base.system.mapper.SysUserMapper;
import com.fanyao.common.core.service.impl.BaseServiceImpl;
import com.fanyao.service.base.util.dozer.DozerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Slf4j
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getSysUserByLoginName(String loginName) {
        List<SysUser> sysUsers = sysUserMapper.listSysUserAndRolesByLoginName(loginName);
        if (CollectionUtils.isEmpty(sysUsers)) {
            return null;
        }

        return sysUsers.get(0);
    }

    @Override
    public SysUser getSysUserAndRolesById(Integer userId) {
        List<SysUser> sysUsers = sysUserMapper.listSysUserAndRolesById(userId);
        if (CollectionUtils.isEmpty(sysUsers)) {
            return null;
        }

        return sysUsers.get(0);
    }

    @Override
    public SysUser addSysUser(SysUserDTO sysUserDTO) {
        SysUser sysUser = DozerUtil.map(sysUserDTO, SysUser.class);
        // 密码加密
        String encodePwd = SecureUtil.md5(sysUser.getPassword());
        sysUser.setPassword(encodePwd);
        this.save(sysUser);
        return sysUser;
    }


}
