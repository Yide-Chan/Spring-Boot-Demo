package com.chenyide.exceptionhandler.exception;

import com.chenyide.exceptionhandler.constant.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chenyide
 * @version v1.0
 * @className BaseException
 * @description 异常基类
 *
 * 为什么不继承Exception
 * 1:因为Exception异常太过广泛，我们直接抛出所有异常信息，对用户而言是非常不友好的。
 * 2:在事务管理里，如果我们自定义的异常继承的是Exception，则事务无效。如果我们是继承RuntimeException，则不会
 *
 * @date 2024/6/11 15:27
 **/

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {
    private Integer code;
    private String message;

    public BaseException(Status status) {
        super(status.getMessage());
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
