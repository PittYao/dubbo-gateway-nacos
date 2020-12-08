package com.fanyao.service.base;

import com.fanyao.api.base.system.entity.SysUser;
import com.fanyao.service.base.system.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ServerBaseApplicationTests {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetUser() {
        List<SysUser> sysUser = sysUserMapper.listSysUserAndRolesById(1);
        System.out.println(sysUser);
    }

}
