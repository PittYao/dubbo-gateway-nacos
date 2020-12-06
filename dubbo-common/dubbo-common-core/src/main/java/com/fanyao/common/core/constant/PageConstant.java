package com.fanyao.common.core.constant;

/**
 * @Description 分页常量
 **/
public interface PageConstant {
    /**
     * 默认最小页码
     */
    long MIN_PAGE = 0;
    /**
     * 最大显示条数
     */
    long MAX_LIMIT = 1000;
    /**
     * 默认页码
     */
    long DEFAULT_PAGE = 1;
    /**
     * 默认显示条数
     */
    long DEFAULT_LIMIT = 10;
    /**
     * 页码 KEY
     */
    String PAGE_KEY = "page";
    /**
     * 显示条数 KEY
     */
    String PAGE_LIMIT_KEY = "limit";

}
