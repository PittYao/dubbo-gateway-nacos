package com.fanyao.service.base.system.mapper;

import com.fanyao.api.base.system.entity.SysRole;
import com.fanyao.api.base.system.entity.SysUser;
import com.fanyao.common.core.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表
 */
@Mapper
public interface SysRoleMapper extends SuperMapper<SysRole> {
    List<SysRole> listSysRoleByMenuUrl(@Param("url") String url);
}
