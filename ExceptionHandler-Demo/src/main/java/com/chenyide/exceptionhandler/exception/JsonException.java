package com.chenyide.exceptionhandler.exception;

import com.chenyide.exceptionhandler.constant.Status;
import lombok.Getter;

/**
 * @author chenyide
 * @version v1.0
 * @className JsonException
 * @description
 * @date 2024/6/11 15:26
 **/
@Getter
public class JsonException extends BaseException {

    public JsonException(Status status) {
        super(status);
    }

    public JsonException(Integer code, String message) {
        super(code, message);
    }
}
