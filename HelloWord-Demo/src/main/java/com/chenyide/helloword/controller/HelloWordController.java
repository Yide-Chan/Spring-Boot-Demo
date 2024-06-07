package com.chenyide.helloword.controller;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenyide
 * @version v1.0
 * @className HelloWordController
 * @description
 * @date 2024/6/7 16:58
 **/
@RestController("hello")
public class HelloWordController {

    /**
     * 注解 @RequestParam()
     * （1）value：请求参数名（必须配置）
     * （2）required：是否必需，默认为 true，即 请求中必须包含该参数，如果没有包含，将会抛出异常（可选配置）
     * （3）defaultValue：默认值，如果设置了该值，required 将自动设为 false，无论你是否配置了required，配置了什么值，都是 false（可选配置）
     *
     * @param name
     * @return
     */
    @GetMapping("/say")
    public String sayHello(@RequestParam(required = false) String name) {
        if (StrUtil.isBlank(name)) {
            name = "nobody";
        }
        return String.format("{} say 'Hello Word!'" + name);
    }
}
