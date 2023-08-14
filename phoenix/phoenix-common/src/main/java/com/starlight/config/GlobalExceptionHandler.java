package com.starlight.config;

import java.util.ArrayList;
import java.util.List;

import com.starlight.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 校验配置
 *
 * @author wenyunfei
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * /0异常
     *
     * @param e 异常
     * @return 结果
     */
    @ExceptionHandler(value = java.lang.ArithmeticException.class)
    @ResponseBody
    public Result exceptionHandler1(ArithmeticException e) {
        return new Result(500, e.getMessage(), null);
    }

    /**
     * 方法异常
     *
     * @param e 异常
     * @return 结果
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result methodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
        List<String> resultErrors = new ArrayList<>();
        objectErrors.forEach(objectError -> {
            resultErrors.add(objectError.getDefaultMessage());
        });
        return new Result(500, "服务异常", resultErrors);
    }

    /**
     * 其他异常
     *
     * @param e 异常
     * @return 结果
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e) {
        log.error("未知异常！原因是:", e);
        return new Result(500, "服务异常", e.getMessage());
    }
}
