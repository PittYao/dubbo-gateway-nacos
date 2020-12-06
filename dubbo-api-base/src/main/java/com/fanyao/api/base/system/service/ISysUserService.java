package com.fanyao.api.base.system.service;

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
}
