package com.chenyide.exceptionhandler.controller;

import com.chenyide.exceptionhandler.constant.Status;
import com.chenyide.exceptionhandler.exception.JsonException;
import com.chenyide.exceptionhandler.exception.PageException;
import com.chenyide.exceptionhandler.model.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author chenyide
 * @version v1.0
 * @className ExceptionHandlerController
 * @description
 * @date 2024/6/11 12:48
 **/
@Controller
public class ExceptionHandlerController {
    @GetMapping("/json")
    @ResponseBody
    public ApiResponse jsonException() {
        throw new JsonException(Status.UNKNOWN_ERROR);
    }

    @GetMapping("/page")
    public ModelAndView pageException() {
        throw new PageException(Status.UNKNOWN_ERROR);
    }
}
