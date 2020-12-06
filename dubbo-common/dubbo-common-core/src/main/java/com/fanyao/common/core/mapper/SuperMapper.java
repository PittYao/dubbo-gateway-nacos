package com.fanyao.common.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fanyao.common.core.dto.CommonDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description
 */
public interface SuperMapper<T> extends BaseMapper<T> {

    /**
     * 自定义分页
     *
     * @param page
     * @param commonDto
     * @return
     */
    IPage<T> pageList(Page<T> page, @Param("ew") CommonDTO commonDto);

    /**
     * 自定义查询
     *
     * @param commonDto
     * @return
     */
    List<Map<String, Object>> selectMapList(@Param("ew") CommonDTO commonDto);
}
