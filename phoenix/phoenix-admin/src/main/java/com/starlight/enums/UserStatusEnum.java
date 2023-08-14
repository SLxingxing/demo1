package com.starlight.enums;

/**
 * @Author: shilian
 * @CreateTime: 2023-02-20  19:47
 * @Description: 用户账号状态枚举类
 */
public enum UserStatusEnum {
    /**
     * 账号密码错误
     */
    ERRORS(2),
    /**
     * 账号已注册
     */
    REGISTERED(1),

    /**
     * 注册成功
     */
    SUCCESS(0);

    private int status;


    private UserStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus(){
        return status;
    }
}
