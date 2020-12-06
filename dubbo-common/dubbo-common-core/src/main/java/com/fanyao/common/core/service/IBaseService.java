package com.fanyao.common.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fanyao.common.core.vo.PageVo;
import com.fanyao.common.core.dto.CommonDTO;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author JacksonTu
 * @Date 2019/11/7 14:34
 */
public interface IBaseService<T> extends IService<T> {

    /**
     * 自定义分页
     *
     * @param commonDto
     * @return
     */
    PageVo pageList(CommonDTO commonDto);

    /**
     * 自定义查询
     *
     * @param commonDto
     * @return
     */
    List<Map<String, Object>> selectMapList(CommonDTO commonDto);
}
