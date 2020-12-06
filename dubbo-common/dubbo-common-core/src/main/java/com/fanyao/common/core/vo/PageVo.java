package com.fanyao.common.core.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @Description 分页参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PageVo<T> implements Serializable {

    /**
     * 总行数
     */
    private int totalCount;

    /**
     * 列表数据
     */
    private List list = Collections.emptyList();

    public PageVo() {

    }

    /**
     * 分页
     */
    public PageVo(IPage page) {
        this.list = page.getRecords();
        this.totalCount = new Long(page.getTotal()).intValue();
    }
}
