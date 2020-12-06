package com.fanyao.common.core.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanyao.common.core.mapper.SuperMapper;
import com.fanyao.common.core.vo.PageVo;
import com.fanyao.common.core.dto.CommonDTO;
import com.fanyao.common.core.service.IBaseService;

import java.util.List;
import java.util.Map;


public abstract class BaseServiceImpl<M extends SuperMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {

    @Override
    public PageVo pageList(CommonDTO commonDto) {
        Page page = new Page();
        // 设置当前页码
        page.setCurrent(commonDto.getPage());
        // 设置页大小
        page.setSize(commonDto.getLimit());
        IPage iPage = this.baseMapper.pageList(page, commonDto);
        return new PageVo(iPage);

    }

    @Override
    public List<Map<String, Object>> selectMapList(CommonDTO commonDto) {
        return this.baseMapper.selectMapList(commonDto);
    }
}
