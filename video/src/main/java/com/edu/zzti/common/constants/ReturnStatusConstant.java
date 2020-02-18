package com.edu.zzti.common.constants;

import lombok.Data;

/**
 * 返回界面信息
 */
@Data
public class ReturnStatusConstant {

    public static final String SUCCESS = "0";
    public static final String FAIL = "-1";
    public static final String SUCCESS_MESSAGE = "操作成功";
    public static final String SUCCESS_FAIL = "操作失败";

    /**
     * 状态码，0：成功 -1：失败
     */
    private String code;

    /**
     * 状态信息
     */
    private String message;
}
