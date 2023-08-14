package com.starlight.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starlight.dao.SysLogMapper;
import com.starlight.model.LogInfo;
import com.starlight.model.PageInfo;
import com.starlight.constant.ResultTips;
import com.starlight.model.Result;
import com.starlight.util.IpAddrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 日志服务类
 *
 * @author wenyunfei
 */
@Service
@Slf4j
public class SysLogService {
    @Autowired
    SysLogMapper sysLogMapper;

    /**
     * 日志查询
     *
     * @param pageInfo pageInfo
     * @return 查询结果
     */
    public Result queryLogInfo(PageInfo<LogInfo> pageInfo) {
        Page<LogInfo> page = new Page<>(pageInfo.getPageIndex(), pageInfo.getPageSize());
        QueryWrapper<LogInfo> wrapper = createWrapper(pageInfo);
        sysLogMapper.selectPage(page, wrapper);
        return new Result(ResultTips.SUCCESS_CODE, ResultTips.QUERY_SUCCESS_MESSAGE, page);
    }

    private static QueryWrapper<LogInfo> createWrapper(PageInfo<LogInfo> pageInfo) {
        QueryWrapper<LogInfo> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("ip", "server");
        if (pageInfo == null || pageInfo.getData() == null) {
            return wrapper;
        }
        LogInfo logInfo = pageInfo.getData();
        String ip = logInfo.getIp();
        if (StringUtils.isNotBlank(ip)) {
            wrapper.like("ip", ip);
        }
        return wrapper;
    }

    /**
     * 日志详情
     *
     * @param logInfo logInfo
     * @return 查询结果
     * @throws UnknownHostException 异常
     */
    public Result queryLogDetail(LogInfo logInfo) throws UnknownHostException {
        String ip = logInfo.getIp();
        if (!IpAddrUtils.getIpAddress().equals(ip)) {
            return ResultTips.getErrorResult("无法查询非服务本地的日志");
        }
        String path = logInfo.getLogPath();
        File baseFile = new File(path);
        if (!baseFile.exists()) {
            return ResultTips.getErrorResult("文件不存在");
        }
        if (!baseFile.isDirectory()) {
            return ResultTips.getErrorResult("路径是文件无法展开");
        }
        File[] files = baseFile.listFiles();
        if (files == null || files.length == 0) {
            return ResultTips.getErrorResult("文件夹是空的");
        }
        List<LogInfo> result = new ArrayList<>();
        for (File file : files) {
            result.add(getLogInfo(logInfo, file));
        }
        return new Result(ResultTips.SUCCESS_CODE, ResultTips.QUERY_SUCCESS_MESSAGE, result);
    }

    private LogInfo getLogInfo(LogInfo logInfo, File file) {
        LogInfo result = new LogInfo();
        result.setIp(logInfo.getIp());
        result.setLogPath(logInfo.getLogPath() + File.separator + file.getName());
        result.setPort(logInfo.getPort());
        if (!file.isDirectory()) {
            result.setFileType("FILE");
        }
        result.setFileName(file.getName());
        result.setServer(logInfo.getServer());
        return result;
    }

    public void download(LogInfo logInfo, HttpServletResponse response) {
        String logPath = logInfo.getLogPath();
        File file = new File(logPath);
        if (!file.exists()) {
            log.error("文件不存在,logPath:{}", logPath);
        }
        if (file.isDirectory()) {
            log.error("文件是文件夹，无法下载,logPath:{}", logPath);
        }
        downloadFile(response, logPath, file);
    }

    private void downloadFile(HttpServletResponse response, String logPath, File file) {
        OutputStream toClient = null;
        try (FileInputStream is = new FileInputStream(file);
             BufferedInputStream buff = new BufferedInputStream(is);) {
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition",
                "attachment;filename=" + new String(file.getName().getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            byte[] b = new byte[1024];
            int len;
            while ((len = buff.read(b)) > 0) {
                toClient.write(b, 0, len);
            }
            toClient.flush();
        } catch (FileNotFoundException e) {
            log.error("文件不存在,logPath:{}", logPath);
        } catch (IOException e) {
            log.error("IO异常,logPath:{},error:{}", logPath, e);
        } finally {
            if (toClient != null) {
                try {
                    toClient.close();
                } catch (IOException e) {
                    log.error("IO异常,无法正确关闭流,logPath:{},error:{}", logPath, e);
                }
            }
        }
    }
}
