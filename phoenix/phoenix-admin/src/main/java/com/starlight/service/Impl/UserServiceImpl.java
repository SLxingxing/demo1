package com.starlight.service.Impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starlight.enums.UserStatusEnum;
import com.starlight.mapper.UserMapper;
import com.starlight.mapper.UserRoleMapper;
import com.starlight.mapper.UserRoleRelationMapper;
import com.starlight.model.Result;
import com.starlight.model.UserModel;
import com.starlight.model.UserRoleModel;
import com.starlight.model.UserRoleRelationModel;
import com.starlight.service.UserService;
import com.starlight.util.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: shilian
 * @CreateTime: 2023-02-19  21:48
 * @Description: 登录注册功能模块
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;

    private static int SUCCESS_CODE = 0;

    /**
     * 用户注册
     *
     * @param userModel 接收的用户模型
     * @return 返回是否可以正确注册的状态
     */
    public int register(UserModel userModel) {
        // 检查用户名是否已存在
        UserModel existedUser = userMapper.selectOne(
            new QueryWrapper<UserModel>().eq("username", userModel.getUsername()));
        if (existedUser != null) {
            return UserStatusEnum.REGISTERED.getStatus();
        }
        // 设置创建时间和更新时间
        Date now = new Date();
        userModel.setId(new SnowflakeIdGenerator().nextId());
        userModel.setCreateDate(now);
        userModel.setUpdateDate(now);
        // 插入用户数据
        userMapper.insert(userModel);
        return UserStatusEnum.SUCCESS.getStatus();
    }

    /**
     * 用户登录功能
     *
     * @param userModel 用户对象
     * @return 返回用户信息供前端使用
     */
    public UserModel login(UserModel userModel) {
        // 根据用户名和密码查询用户
        QueryWrapper<UserModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userModel.getUsername());
        queryWrapper.eq("password", userModel.getPassword());
        UserModel user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        return user;
    }

    /**
     * 创建角色
     *
     * @param userModel 角色模型
     * @return 创建信息
     */
    @Override
    public Result createRole(UserRoleModel userModel) {
        userModel.setCreateDate(LocalDateTime.now());
        userModel.setUpdater(userModel.getCreator());
        userModel.setUpdateDate(LocalDateTime.now());
        userRoleMapper.insert(userModel);
        return new Result(SUCCESS_CODE, "角色创建成功");
    }

    /**
     * 更新角色
     *
     * @param userModel 角色模型
     * @return 返回更新信息
     */
    @Override
    public Result updateRole(UserRoleModel userModel) {
        userModel.setUpdater(userModel.getCreator());
        userModel.setUpdateDate(LocalDateTime.now());
        userRoleMapper.updateById(userModel);
        return new Result(SUCCESS_CODE, "更新成功");
    }

    /**
     * 删除角色
     *
     * @param roleId 角色Id
     * @return 返回删除信息
     */
    @Override
    public Result deleteRole(Long roleId) {
        userRoleMapper.deleteById(roleId);
        QueryWrapper<UserRoleRelationModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        userRoleRelationMapper.delete(queryWrapper);
        return new Result(SUCCESS_CODE, "删除成功");
    }

    /**
     * 分页查询所有角色
     *
     * @param page  角色列表
     * @return 角色列表
     */
    @Override
    public IPage<UserRoleModel> queryRoleByPage(Page<UserRoleModel> page) {
        return userRoleMapper.selectPage(page, null);
    }

    /**
     * 设置用户角色
     *
     * @param userId 用户Id
     * @param roleId 角色Id
     * @return 设置成功与否
     */
    @Override
    public Result setUserRole(Long userId, Long roleId) {
        UserRoleRelationModel userRoleRelation = new UserRoleRelationModel();
        userRoleRelation.setUserId(userId);
        userRoleRelation.setRoleId(roleId);
        userRoleRelation.setUpdater(userId);
        userRoleRelation.setUpdateDate(new Date());
        userRoleRelationMapper.insert(userRoleRelation);
        return new Result(SUCCESS_CODE, "设置成功");
    }

    /**
     * 获取用户角色列表
     *
     * @param userId 用户Id
     * @return 返回用户角色列表
     */
    @Override
    public List<UserRoleModel> getUserRoles(Long userId) {
        QueryWrapper<UserRoleRelationModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserRoleRelationModel> userRoleRelations = userRoleRelationMapper.selectList(queryWrapper);
        List<Long> roleIds = userRoleRelations.stream().map(UserRoleRelationModel::getRoleId).collect(Collectors.toList());
        if (roleIds.isEmpty()) {
            return new ArrayList<>();
        }
        QueryWrapper<UserRoleModel> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.in("id", roleIds);
        return userRoleMapper.selectList(roleQueryWrapper);
    }

}
