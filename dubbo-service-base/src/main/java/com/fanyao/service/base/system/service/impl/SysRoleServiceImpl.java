package com.fanyao.service.base.system.service.impl;


import com.fanyao.api.base.system.entity.SysRole;
import com.fanyao.api.base.system.service.ISysRoleService;
import com.fanyao.common.core.service.impl.BaseServiceImpl;
import com.fanyao.service.base.system.mapper.SysRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Slf4j
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> listSysRoleByMenuUrl(String url) {
        return sysRoleMapper.listSysRoleByMenuUrl(url);
    }
}
