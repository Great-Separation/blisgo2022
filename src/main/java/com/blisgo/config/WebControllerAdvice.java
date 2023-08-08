package com.blisgo.config;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class WebControllerAdvice {
    // 모든 Controller에 적용하는 설정. 기본 객체로 전달받은 @ModelAttribute DTO 객체들은
    // http url parameter 방식으로 전달받기 때문에 Setter로 1:1 매핑을 시킴
    // 모든 요청을 직접 body json 으로 전달
    // MessageConverter들 중 하나인 MappingJackson2HttpMessageConverter를 통해 Java 객체로 반환됨

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
    }
}