package com.chenyide.exceptionhandler.exception;

import com.chenyide.exceptionhandler.constant.Status;
import lombok.Getter;

/**
 * @author chenyide
 * @version v1.0
 * @className PageException
 * @description 页面异常
 * @date 2024/6/11 15:28
 **/
@Getter
public class PageException extends BaseException {
    public PageException(Status status) {
        super(status);
    }

    public PageException(Integer code, String message) {
        super(code, message);
    }
}
