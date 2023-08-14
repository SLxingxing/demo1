package com.starlight.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starlight.model.Result;
import com.starlight.model.UserModel;
import com.starlight.model.UserRoleModel;

/**
 * @Author: shilian
 * @CreateTime: 2023-02-19  21:47
 * @Description: 登录注册接口
 */

public interface UserService {
    /**
     * 注册新用户
     * @param user 注册用户信息
     * @return 是否注册成功
     */
    int register(UserModel user);

    /**
     * 用户登录
     * @param userModel 用户对象
     * @return 登录用户信息
     */
    UserModel login(UserModel userModel);

    /**
     * 创建角色
     *
     * @param role 角色模型
     * @return 创建成功与否信息
     */
    Result createRole(UserRoleModel role);

    /**
     * 更新角色
     *
     * @param userModel 角色模型
     * @return 返回更新信息
     */
    Result updateRole(UserRoleModel userModel);

    /**
     * 删除角色
     *
     * @param roleId 角色Id
     * @return 返回删除信息
     */
    Result deleteRole(Long roleId);

    /**
     * 分页查询所有角色
     *
     * @param page  角色列表
     * @return 角色列表
     */
    IPage<UserRoleModel> queryRoleByPage(Page<UserRoleModel> page);

    /**
     * 设置用户角色
     *
     * @param userId 用户Id
     * @param roleId 角色Id
     */
    Result setUserRole(Long userId, Long roleId);

    /**
     * 获取用户角色列表
     *
     * @param userId 用户Id
     * @return 返回用户角色列表
     */
    List<UserRoleModel> getUserRoles(Long userId);
}
