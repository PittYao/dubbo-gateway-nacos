package com.fanyao.api.base.system.service;

import com.fanyao.api.base.system.dto.SysUserDTO;
import com.fanyao.common.core.service.IBaseService;
import com.fanyao.api.base.system.entity.SysUser;

/**
 * @author: bugProvider
 * @date: 2020/12/3 14:07
 * @description: 系统用户
 */
public interface ISysUserService extends IBaseService<SysUser> {
    /**
     * 根据用户登录名查询登录用户信息
     *
     * @param loginName 登录名
     * @return 用户信息
     */
    SysUser getSysUserByLoginName(String loginName);

    /**
     * 根据用户id查询用户信息，其中包含用户的角色信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    SysUser getSysUserAndRolesById(Integer userId);

    /**
     * 新增用户
     *
     * @param sysUser 用户信息
     * @return 用户信息
     */
    SysUser addSysUser(SysUserDTO sysUserDTO);

}
