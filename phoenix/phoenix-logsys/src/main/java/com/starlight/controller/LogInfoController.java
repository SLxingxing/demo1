package com.starlight.controller;

import javax.servlet.http.HttpServletResponse;

import com.starlight.model.LogInfo;
import com.starlight.model.PageInfo;
import com.starlight.service.SysLogService;
import com.starlight.constant.ResultTips;
import com.starlight.model.LogValidate.LogQueryValidate;
import com.starlight.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志接口
 *
 * @author wenyunfei
 */
@Api(tags = "日志接口")
@RestController
@RequestMapping("/logInfo")
public class LogInfoController {
    @Autowired
    SysLogService sysLogService;

    /**
     * 日志查询
     *
     * @param pageInfo
     * @return 结果
     */
    @ApiOperation("日志查询")
    @PostMapping("/pageInfo")
    public Result queryPageInfo(@Validated(LogQueryValidate.class) @RequestBody PageInfo<LogInfo> pageInfo) {
        try {
            return sysLogService.queryLogInfo(pageInfo);
        } catch (Exception e) {
            return ResultTips.getErrorResult(e);
        }
    }

    /**
     * 日志查询-详情
     *
     * @param logInfo
     * @return 结果
     */
    @ApiOperation("日志详情")
    @PostMapping("/detail")
    public Result detail(@Validated(LogQueryValidate.class) @RequestBody LogInfo logInfo) {
        try {
            return sysLogService.queryLogDetail(logInfo);
        } catch (Exception e) {
            return ResultTips.getErrorResult(e);
        }
    }

    /**
     * 日志下载
     *
     * @param logInfo
     */
    @ApiOperation("日志下载")
    @PostMapping("/download")
    public void download(@Validated(LogQueryValidate.class) @RequestBody LogInfo logInfo,
        HttpServletResponse response) {
        sysLogService.download(logInfo, response);
    }
}
