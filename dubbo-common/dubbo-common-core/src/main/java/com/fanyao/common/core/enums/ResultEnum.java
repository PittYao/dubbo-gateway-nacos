package com.fanyao.common.core.enums;

import lombok.Getter;

@Getter
public enum ResultEnum implements ExtensibleEnum {
    /* 成功状态码 */
    SUCCESS(200, "成功"),
    FAILURE(500, "操作失败,系统内部异常!"),

    GET_SUCCESS(200, "查询成功"),
    POST_SUCCESS(200, "添加成功"),
    PUT_SUCCESS(200, "修改成功"),
    DELETE_SUCCESS(200, "删除成功"),

    /* 认证服务 */
    JWT_HEADER_NO_TOKEN(500, "请求头没有token"),
    JWT_FAILURE(500, "认证服务异常"),


    /* 参数错误：10001-19999 */
    PARAM_VERIFICATION_FAILED(10001, "参数校验异常"),
    PARAM_VERIFICATION_FAILED_RUNTIME(10001, "运行时参数校验异常"),
    METHOD_UN_SUPPORT(10001, "请求方法不支持"),

    /* 用户错误：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    USER_LOGIN_ERROR(20002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
    USER_NOT_EXIST(20004, "用户不存在"),
    USER_HAS_EXISTED(20005, "用户已存在"),

    /* 业务错误：30001-39999 */
    SPECIFIED_QUESTIONED_USER_NOT_EXIST(30001, "某业务出现问题"),

    /* 系统错误：40001-49999 */
    SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),

    /* 数据错误：50001-599999 */
    RESULT_DATA_NONE(50001, "数据未找到"),
    DATA_IS_WRONG(50002, "数据有误"),
    DATA_ALREADY_EXISTED(50003, "数据已存在"),

    /* 接口错误：60001-69999 */
    INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),
    INTERFACE_OUTTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(60006, "接口负载过高"),

    /* 权限错误：70001-79999 */
    PERMISSION_NO_ACCESS(70001, "无访问权限");


    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
