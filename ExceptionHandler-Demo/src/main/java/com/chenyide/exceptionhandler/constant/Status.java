package com.chenyide.exceptionhandler.constant;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chenyide
 * @version v1.0
 * @className Status
 * @description 封装状态码
 * @date 2024/6/11 12:50
 **/
@Getter
public enum Status {
    /**
     * 操作成功
     */
    OK(200, "操作成功"),

    /**
     * 未知异常
     */
    UNKNOWN_ERROR(500, "服务器出错啦");
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 内容
     */
    private String message;

    Status(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
