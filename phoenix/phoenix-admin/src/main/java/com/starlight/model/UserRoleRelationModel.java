package com.starlight.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: shilian
 * @CreateTime: 2023-02-18  16:12
 * @Description: 角色关系实体类
 */

@Data
@TableName("sys_role_user")
public class UserRoleRelationModel {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 创建者
     */
    private Long updater;

    /**
     * 创建时间
     */
    private Date updateDate;
}
