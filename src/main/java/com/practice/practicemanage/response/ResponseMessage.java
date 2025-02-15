package com.practice.practicemanage.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage <T> {
    // 响应状态码
    private int statusCode;

    // 响应信息
    private String message;

    // 响应数据
    private T data;

    // 是否成功
    private boolean success;

    /**
     * 构建成功响应
     * @param message 消息
     * @param data 数据
     * @param <T> 泛型数据类型
     * @return ResponseMessage
     */
    public static <T> ResponseMessage<T> success(String message, T data){
        return new ResponseMessage<>(
                HttpStatus.OK.value(),
                message != null ? message : "操作成功",
                data,
                true
        );
    }

    /**
     * 构建成功响应 2
     * @param message 消息
     * @param <T> 泛型数据类型
     * @return ResponseMessage
     */
    public static <T> ResponseMessage<T> success(String message){
        return new ResponseMessage<>(
                HttpStatus.OK.value(),
                message != null ? message : "操作成功",
                null,
                true
        );
    }

    /**
     * 构建成功响应 3
     * @param data 数据
     * @param <T> 泛型数据类型
     * @return ResponseMessage
     */
    public static <T> ResponseMessage<T> success(T data){
        return new ResponseMessage<>(
                HttpStatus.OK.value(),
                "操作成功",
                data,
                true
        );
    }

    /**
     * 构建失败响应
     * @param message 错误消息
     * @param <T> 泛型数据类型
     * @return ResponseMessage
     */
    public static <T> ResponseMessage<T> error(String message){
        return new ResponseMessage<>(
                HttpStatus.BAD_REQUEST.value(),
                message != null ? message : "操作失败",
                null,
                false
        );
    }

    /**
     * 构建自定义响应
     * @param statusCode 状态码
     * @param message 消息
     * @param data 数据
     * @param success 是否成功
     * @param <T> 泛型数据类型
     * @return ResponseMessage
     */
    public static <T> ResponseMessage<T> custom(int statusCode, String message, T data, boolean success) {
        return new ResponseMessage<>(statusCode, message, data, success);
    }

}
