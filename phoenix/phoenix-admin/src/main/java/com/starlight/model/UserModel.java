package com.starlight.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: shilian
 * @CreateTime: 2023-02-18  15:37
 * @Description: 系统用户实体类
 */
@Data
@TableName("sys_user")
public class UserModel {
    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字和下划线")
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "用户密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度应为6-20位")
    @Pattern(regexp = "^[a-zA-Z0-9_!@#$%^&*()\\-+=<>?,./;:'\"\\[\\]\\{\\}\\\\|]+$",
        message = "密码只能包含大小写字母、数字、特殊符号以及下划线")
    @TableField(value = "password")
    private String password;

    /**
     * 姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 头像
     */
    @TableField(value = "head_url")
    private String headUrl;

    /**
     * 性别   0：男   1：女    2：其他
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 手机
     */
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 部门ID
     */
    @TableField(value = "dept_id")
    private Long deptId;

    /**
     * 状态  0：停用   1：正常
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建者
     */
    @TableField(value = "creator")
    private Long creator;

    /**
     * 创建时间
     */
    @TableField(value = "create_date")
    private Date createDate;

    /**
     * 更新者
     */
    @TableField(value = "updater")
    private Long updater;

    /**
     * 更新时间
     */
    @TableField(value = "update_date")
    private Date updateDate;

    public String getUsername() {
        return username;
    }

    //public void setUsername(String username) {
    //    this.username = StringUtils.strip(Encode.forHtml(StringUtils.trimToEmpty(username)));
    //}
    //
    //public String getPassword() {
    //    return password;
    //}
    //
    //public void setPassword(String password) {
    //    this.password = StringUtils.strip(Encode.forHtml(StringUtils.trimToEmpty(password)));
    //}
    //
    //public String getEmail() {
    //    return email;
    //}
    //
    //public void setEmail(String email) {
    //    this.email = StringUtils.strip(Encode.forHtml(StringUtils.trimToEmpty(email)));
    //}
}
