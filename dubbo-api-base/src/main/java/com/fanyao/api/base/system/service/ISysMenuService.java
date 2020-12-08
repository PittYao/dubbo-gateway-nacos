package com.fanyao.api.base.system.service;

import com.fanyao.api.base.system.entity.SysMenu;
import com.fanyao.api.base.system.entity.SysRole;
import com.fanyao.common.core.service.IBaseService;

import java.util.List;

/**
 * @author: bugProvider
 * @date: 2020/12/3 14:07
 * @description: 系统菜单
 */
public interface ISysMenuService extends IBaseService<SysMenu> {

    List<SysMenu> listAllAndRole();

}
