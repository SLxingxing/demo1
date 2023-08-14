package com.starlight.controller;

import java.util.List;

import javax.validation.Valid;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starlight.constant.ResultTips;
import com.starlight.model.DemoVo;
import com.starlight.model.Result;
import com.starlight.model.UserModel;
import com.starlight.model.UserRoleModel;
import com.starlight.service.Impl.DemoService;
import com.starlight.service.UserService;
import com.starlight.util.XssFilterUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: shilian
 * @CreateTime: 2023-02-20  20:49
 * @Description: 用户登录控制类
 */
@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/UserController")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    DemoService demoService;

    /**
     * 注册
     *
     * @param userModel 用户信息
     * @return 结果
     */
    @ApiOperation("注册接口")
    @PostMapping("/register")
    public Result register(@Valid @RequestBody UserModel userModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 校验失败，返回注册信息
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            return new Result(ResultTips.ERROR_CODE, ResultTips.ERROR_MESSAGE, fieldErrors);
        }
        try {
            userModel.setUsername(XssFilterUtils.stripXSS(userModel.getUsername()));
            userModel.setPassword(XssFilterUtils.stripXSS(userModel.getPassword()));
            userModel.setEmail(XssFilterUtils.stripXSS(userModel.getEmail()));
            if (StringUtils.isNotEmpty(userModel.getHeadUrl())) {
                userModel.setHeadUrl(XssFilterUtils.stripXSS(userModel.getHeadUrl()));
            }
            userModel.setRealName(XssFilterUtils.stripXSS(userModel.getRealName()));
            return new Result(ResultTips.SUCCESS_CODE, ResultTips.QUERY_SUCCESS_MESSAGE,
                userService.register(userModel));
        } catch (Exception e) {
            return ResultTips.getErrorResult(e);
        }
    }

    /**
     * 登录
     *
     * @param userModel 用户对象
     * @return 返回用户信息提供前端使用
     */
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public Result login(@Valid @RequestBody UserModel userModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 校验失败，返回注册信息
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            return new Result(ResultTips.ERROR_CODE, ResultTips.ERROR_MESSAGE, fieldErrors);
        }
        try {
            userModel.setUsername(XssFilterUtils.stripXSS(userModel.getUsername()));
            userModel.setPassword(XssFilterUtils.stripXSS(userModel.getPassword()));
            return new Result(ResultTips.SUCCESS_CODE, ResultTips.QUERY_SUCCESS_MESSAGE,
                userService.login(userModel));
        } catch (Exception e) {
            return ResultTips.getErrorResult(e);
        }
    }

    /**
     * 创建角色
     *
     * @param userRoleModel 角色模型
     * @return 创建信息
     */
    @ApiOperation("创建角色接口")
    @PostMapping("/createRole")
    public Result createRole(@Validated @RequestBody UserRoleModel userRoleModel) {
        try {
            userRoleModel.setName(XssFilterUtils.stripXSS(userRoleModel.getName()));
            if (StringUtils.isNotEmpty(userRoleModel.getRemark())) {
                userRoleModel.setRemark(XssFilterUtils.stripXSS(userRoleModel.getRemark()));
            }
            return userService.createRole(userRoleModel);
        } catch (Exception e) {
            return ResultTips.getErrorResult(e);
        }
    }

    /**
     * 更新角色
     *
     * @param userRoleModel 角色模型
     * @return 创建信息
     */
    @ApiOperation("更新角色接口")
    @PostMapping("/updateRole")
    public Result updateRole(@RequestBody UserRoleModel userRoleModel) {
        try {
            userRoleModel.setName(XssFilterUtils.stripXSS(userRoleModel.getName()));
            if (StringUtils.isNotEmpty(userRoleModel.getRemark())) {
                userRoleModel.setRemark(XssFilterUtils.stripXSS(userRoleModel.getRemark()));
            }
            return userService.updateRole(userRoleModel);
        } catch (Exception e) {
            return ResultTips.getErrorResult(e);
        }
    }

    /**
     * 创建角色
     *
     * @param roleId 角色Id
     * @return 创建信息
     */
    @ApiOperation("删除角色接口")
    @PostMapping("/deleteRole")
    public Result deleteRole(@RequestParam("roleId") Long roleId) {
        try {
            return userService.deleteRole(roleId);
        } catch (Exception e) {
            return ResultTips.getErrorResult(e);
        }
    }

    /**
     * 分页查询所有角色接口
     *
     * @param pageNum 当前页数
     * @param pageSize 每页条数
     * @return 角色列表
     */
    @ApiOperation("分页查询所有角色接口")
    @GetMapping("/roles")
    public Result queryRoleByPage(@RequestParam(defaultValue = "1") Integer pageNum,
        @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            Page<UserRoleModel> page = new Page<>(pageNum, pageSize);
            IPage<UserRoleModel> roleList = userService.queryRoleByPage(page);
            return new Result(ResultTips.SUCCESS_CODE, ResultTips.QUERY_SUCCESS_MESSAGE, roleList);
        } catch (Exception e) {
            return ResultTips.getErrorResult(e);
        }
    }

    /**
     * 设置角色
     *
     * @param roleId 角色Id
     * @param userId 用户Id
     * @return 创建信息
     */
    @ApiOperation("设置用户角色")
    @PostMapping("/setUserRole")
    public Result deleteRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        try {
            return userService.setUserRole(userId, roleId);
        } catch (Exception e) {
            return ResultTips.getErrorResult(e);
        }
    }

    /**
     * 获取用户角色列表
     *
     * @param userId 角色Id
     * @return 创建信息
     */
    @ApiOperation("获取用户角色列表")
    @PostMapping("/getUserRoles")
    public Result getUserRoles(@RequestParam("userId") Long userId) {
        try {
            return new Result(ResultTips.ERROR_CODE, ResultTips.ERROR_MESSAGE, userService.getUserRoles(userId));
        } catch (Exception e) {
            return ResultTips.getErrorResult(e);
        }
    }

    @PostMapping("/controllerDemo")
    public Result demoController(@RequestBody DemoVo demoVo){
        int resultNum = demoService.method(demoVo.getNum1(),demoVo.getNum2());
        return new Result(resultNum,"success!",0);
    }

}
