package com.fanyao.service.base.system.mapper;

import com.fanyao.api.base.system.entity.SysMenu;
import com.fanyao.api.base.system.entity.SysRole;
import com.fanyao.common.core.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单表
 */
@Mapper
public interface SysMenuMapper extends SuperMapper<SysMenu> {
    List<SysMenu> listAllAndRole();
}
