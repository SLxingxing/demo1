package com.starlight.constant;

import com.starlight.model.Result;

/**
 * 结果常量
 *
 * @author wenyunfei
 */
public class ResultTips {
    public static int SUCCESS_CODE = 200;

    public static int ERROR_CODE = 500;

    public static String QUERY_SUCCESS_MESSAGE = "查询成功";

    public static String ERROR_MESSAGE = "服务异常";

    /**
     * 获取错误结果
     *
     * @param error 异常
     * @return 结果
     */
    public static Result getErrorResult(Exception error) {
        return getErrorResult(error.getMessage());
    }

    /**
     * 获取错误结果
     *
     * @param message 错误信息
     * @return 结果
     */
    public static Result getErrorResult(String message) {
        return new Result(ResultTips.ERROR_CODE, ResultTips.ERROR_MESSAGE, message);
    }
}
