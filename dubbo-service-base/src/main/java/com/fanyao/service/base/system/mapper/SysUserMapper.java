package com.fanyao.service.base.system.mapper;

import com.fanyao.common.core.mapper.SuperMapper;
import com.fanyao.api.base.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表
 */
@Mapper
public interface SysUserMapper extends SuperMapper<SysUser> {

    SysUser selectByLoginName(@Param("loginName") String loginName);
}
