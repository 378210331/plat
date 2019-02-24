package com.hsy.platform.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLSyntaxErrorException;
import java.util.HashMap;
import java.util.Map;


/**
 * 处理层统一处理异常
 */
@ControllerAdvice
public class ExceptionConfigController  {

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Map<String,Object> nullPointerExceptionHandler(NullPointerException e){
                Map<String,Object> result = new HashMap<>();
                result.put("res", false);
                result.put("msg", e.getMessage());
                System.err.println("发生了空指针异常"+e.getMessage());
                e.printStackTrace();
                return result;
    }

    // 专门用来捕获和处理Controller层的运行时异常
    @ExceptionHandler(RuntimeException.class)
        @ResponseBody
        public Map<String,Object> runtimeExceptionHandler(NullPointerException e){
            Map<String,Object> result = new HashMap<>();
            result.put("res", false);
            result.put("msg", e.getMessage());
             System.err.println("发生了运行异常"+e.getMessage());
             e.printStackTrace();
            return result;
        }

    // 专门用来捕获和处理Controller层的异常
    @ExceptionHandler(Exception.class)
    public Map<String,Object> exceptionHandler(NullPointerException e){
        Map<String,Object> result = new HashMap<>();
        result.put("res", false);
        result.put("msg", e.getMessage());
        System.err.println("发生了异常"+e.getMessage());
        e.printStackTrace();
        return result;
    }

    @ExceptionHandler(java.sql.SQLException.class)
    public Map<String,Object> sqlExceptionHandler(NullPointerException e){
        Map<String,Object> result = new HashMap<>();
        result.put("res", false);
        result.put("msg", e.getMessage());
        System.err.println("发生了sql异常"+e.getMessage());
        e.printStackTrace();
        return result;
    }
    @ExceptionHandler(java.sql.SQLSyntaxErrorException.class)
    public Map<String,Object> sqlSyntaxErrorExceptionxceptionHandler(NullPointerException e){
        Map<String,Object> result = new HashMap<>();
        result.put("res", false);
        result.put("msg", e.getMessage());
        System.err.println("发生了sql异常"+e.getMessage());
        e.printStackTrace();
        return result;
    }

}
