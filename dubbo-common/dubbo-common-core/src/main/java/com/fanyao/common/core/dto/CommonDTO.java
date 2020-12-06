package com.fanyao.common.core.dto;

import com.fanyao.common.core.constant.PageConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description 基础参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CommonDTO implements Serializable {

    @NotNull(message = "页码不能为空")
    private long page = PageConstant.DEFAULT_PAGE;

    @NotNull(message = "行数不能为空")
    private long limit = PageConstant.DEFAULT_LIMIT;

    private String key;

    public void setPage(long page) {
        if (page <= 0) {
            this.page = PageConstant.DEFAULT_PAGE;
        } else {
            this.page = page;
        }
    }

    public void setLimit(long limit) {
        if (limit <= 0) {
            this.limit = PageConstant.DEFAULT_LIMIT;
        } else if (this.limit >= PageConstant.MAX_LIMIT) {
            this.limit = PageConstant.MAX_LIMIT;
        } else {
            this.limit = limit;
        }
    }
}
