package com.myspringboot.community.dto;

import com.myspringboot.community.exception.MyErrorCode;
import com.myspringboot.community.exception.MyException;
import lombok.Data;

/**
 * 返回结果
 * @param <T>
 */
@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    //任意类型
    private T data;

    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(MyErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO successOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static ResultDTO errorOf(MyException ex) {
        return errorOf(ex.getCode(),ex.getMessage());
    }

    public static <T> ResultDTO successOf(T t){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }
}
