package com.fanyao.api.base.system.service;

import com.fanyao.api.base.system.entity.SysRole;
import com.fanyao.api.base.system.entity.SysUser;
import com.fanyao.common.core.service.IBaseService;

import java.util.List;

/**
 * @author: bugProvider
 * @date: 2020/12/3 14:07
 * @description: 系统角色
 */
public interface ISysRoleService extends IBaseService<SysRole> {

    List<SysRole> listSysRoleByMenuUrl(String url);

}
