package com.practice.practicemanage.exception;

import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.util.LogUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice   // 全局异常处理
public class GlobalExceptionHandlerAdvice {

    @Autowired
    private LogUtil logUtil;

    @ExceptionHandler({Exception.class}) // 捕获所有异常
    public ResponseMessage handlerException(Exception e, HttpServletRequest request, HttpServletResponse response){
//        记录日志
        logUtil.error(GlobalExceptionHandlerAdvice.class,"统一异常： ", request, e);
        return new ResponseMessage(500, "error", null, false);
    }
}
