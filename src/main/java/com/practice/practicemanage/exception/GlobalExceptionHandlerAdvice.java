package com.practice.practicemanage.exception;

import com.practice.practicemanage.response.ResponseMessage;
import com.practice.practicemanage.utils.LogUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        return new ResponseMessage(500, e.getMessage(), null, false);
    }

    // 处理 @Valid @RequestBody 参数校验失败的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseMessage<Object> handleValidationException(MethodArgumentNotValidException ex) {
        // 获取第一个校验失败的错误信息
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        return new ResponseMessage<>(400, errorMessage, null, false);
    }

    // 处理 @RequestParam、@PathVariable 的参数校验失败
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseMessage<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseMessage<>(400, ex.getMessage(), null, false);
    }
}
