package com.myspringboot.community.advice;

import com.alibaba.fastjson.JSON;
import com.myspringboot.community.dto.ResultDTO;
import com.myspringboot.community.exception.MyErrorCode;
import com.myspringboot.community.exception.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 异常处理类
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    //处理所有Exception类
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable ex, HttpServletResponse response) {

        ModelAndView modelAndView=new ModelAndView();
        String contentType=request.getContentType();
        if("application/json".equals(contentType)){
            ResultDTO resultDTO;
            //返回json
            if(ex instanceof MyException){
                resultDTO= ResultDTO.errorOf((MyException) ex);
            }else{
                resultDTO= ResultDTO.errorOf(MyErrorCode.SYS_ERROR);
            }
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.setStatus(200);
                PrintWriter writer=response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else{
            //错误页面跳转
            //获取异常状态码 ，404，500之类的
            HttpStatus status = getStatus(request);

            modelAndView.setViewName("error");
            //判断捕获的异常是否是自定义异常类
            if(ex instanceof MyException){
                modelAndView.addObject("message", ex.getMessage());
            }else{
                modelAndView.addObject("message",MyErrorCode.SYS_ERROR.getMessage());
            }
            return modelAndView;
        }

    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
