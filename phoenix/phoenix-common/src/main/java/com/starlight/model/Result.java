package com.starlight.model;

import lombok.Data;

/**
 * 结果模型
 *
 * @author wenyunfei
 */
@Data
public class Result {
    private int code;

    private String message;

    private Object data;

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
