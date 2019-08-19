package com.myspringboot.community.advice;

import com.myspringboot.community.exception.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理类
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    //处理所有Exception类
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable ex) {
        //获取异常状态码 ，404，500之类的
        HttpStatus status = getStatus(request);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("error");
        //判断捕获的异常是否是自定义异常类
        if(ex instanceof MyException){
            modelAndView.addObject("message", ex.getMessage());
        }else{
            modelAndView.addObject("message","页面消失了，再试试吧~");
        }
        return modelAndView;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
