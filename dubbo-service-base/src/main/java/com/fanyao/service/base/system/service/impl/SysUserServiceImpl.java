package com.fanyao.service.base.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fanyao.api.base.system.entity.SysUser;
import com.fanyao.api.base.system.service.ISysUserService;
import com.fanyao.service.base.system.mapper.SysUserMapper;
import com.fanyao.common.core.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

import java.util.Optional;


@Slf4j
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Override
    public SysUser getSysUserByLoginName(String loginName) {
        SysUser sysUser = this.getOne(
                new LambdaQueryWrapper<SysUser>()
                        .eq(SysUser::getLoginName, loginName)
        );

        return Optional.ofNullable(sysUser).orElse(null);

    }


}
