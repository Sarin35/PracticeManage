package com.practice.practicemanage.exception;

import com.practice.practicemanage.response.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice   // 全局异常处理
public class GlobalExceptionHandlerAdvice {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandlerAdvice.class);

    @ExceptionHandler({Exception.class}) // 捕获所有异常
    public ResponseMessage handlerException(Exception e, HttpServletRequest request, HttpServletResponse response){
//        记录日志
        logger.error("统一异常：", e);
        return new ResponseMessage(500, "error", null, false);
    }
}
