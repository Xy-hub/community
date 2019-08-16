package com.myspringboot.community.advice;

import com.myspringboot.community.exception.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable ex) {
        HttpStatus status = getStatus(request);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("error");
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
