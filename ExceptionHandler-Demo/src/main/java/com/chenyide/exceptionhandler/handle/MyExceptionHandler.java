package com.chenyide.exceptionhandler.handle;

import com.chenyide.exceptionhandler.exception.JsonException;
import com.chenyide.exceptionhandler.exception.PageException;
import com.chenyide.exceptionhandler.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author chenyide
 * @version v1.0
 * @className ExceptionHandler
 * @description 统一异常处理
 * @date 2024/6/11 15:32
 **/
@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

    private static final String DEFAULT_ERROR_VIEW = "error";

    /**
     * 统一 json 异常处理
     *
     * /@ExceptionHandler注解中可以添加参数，参数是某个异常类的class，代表这个方法专门处理该类异常
     *
     * @param exception JsonException
     * @return 统一返回 json 格式
     */
    @ExceptionHandler(value = JsonException.class)
    @ResponseBody
    public ApiResponse jsonErrorHandler(JsonException exception) {
        log.error("【JsonException】:{}", exception.getMessage());
        return ApiResponse.ofException(exception);
    }

    /**
     * 统一 页面 异常处理
     *
     * @param exception PageException
     * @return 统一跳转到异常页面
     */
    @ExceptionHandler(value = PageException.class)
    public ModelAndView pageErrorHandler(PageException exception) {
        log.error("【DemoPageException】:{}", exception.getMessage());
        ModelAndView view = new ModelAndView();
        // 添加模型数据用addObject;
        view.addObject("message", exception.getMessage());
        // 设置视图setViewName;
        view.setViewName(DEFAULT_ERROR_VIEW);
        return view;
    }
}
