package com.fanyao.service.base.system.service.impl;


import com.fanyao.api.base.system.entity.SysMenu;
import com.fanyao.api.base.system.entity.SysRole;
import com.fanyao.api.base.system.service.ISysMenuService;
import com.fanyao.api.base.system.service.ISysRoleService;
import com.fanyao.common.core.service.impl.BaseServiceImpl;
import com.fanyao.service.base.system.mapper.SysMenuMapper;
import com.fanyao.service.base.system.mapper.SysRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Slf4j
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> listAllAndRole() {
        return sysMenuMapper.listAllAndRole();
    }
}
