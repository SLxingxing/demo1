package com.starlight.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @Author: shilian
 * @CreateTime: 2023-02-18  15:37
 * @Description: 角色实体类
 */
@Data
@TableName("sys_role")
public class UserRoleModel {
    /**
     * 唯一的自增ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    @NotNull(message = "角色名不能为空")
    @Length(min = 1, max = 50, message = "角色名长度应为1到50个字符")
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 部门ID
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 权限级别
     */
    @TableField(value = "authority_level")
    private Integer authorityLevel;

    /**
     * 创建者
     */
    private Long creator;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    /**
     * 更新人
     */
    private Long updater;

    /**
     * 更新时间
     */
    @TableField("update_date")
    private LocalDateTime updateDate;
}
