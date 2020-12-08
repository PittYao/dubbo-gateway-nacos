package com.fanyao.service.base.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fanyao.api.base.system.entity.SysUser;
import com.fanyao.api.base.system.service.ISysUserService;
import com.fanyao.common.core.enums.ResultEnum;
import com.fanyao.common.core.exception.BusinessException;
import com.fanyao.service.base.system.mapper.SysUserMapper;
import com.fanyao.common.core.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.BagUtils;
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


}
