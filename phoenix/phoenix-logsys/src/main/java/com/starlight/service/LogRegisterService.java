package com.starlight.service;

import java.net.UnknownHostException;

import com.starlight.config.LogConfig;
import com.starlight.dao.SysLogMapper;
import com.starlight.model.LogInfo;
import com.starlight.util.IpAddrUtils;
import com.starlight.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 日志注册
 *
 * @author wenyunfei
 */
@Component
@Slf4j
public class LogRegisterService {
    /**
     * 注册日志
     * 用于日志查询和下载
     *
     * @throws UnknownHostException 异常
     */
    @Bean
    private void logRegister() throws UnknownHostException {
        LogConfig logConfig = SpringUtils.getBean(LogConfig.class);
        SysLogMapper sysLogMapper = SpringUtils.getBean(SysLogMapper.class);
        LogInfo logInfo = new LogInfo();
        logInfo.setLogPath(logConfig.getLogPath());
        logInfo.setPort(logConfig.getLogPort());
        logInfo.setServer(logConfig.getLogServer());
        logInfo.setIp(IpAddrUtils.getIpAddress());
        logInfo.setFileName(logInfo.getFileName());
        try {
            sysLogMapper.insert(logInfo);
        }catch (Exception e){
            log.error("logInfo is exist,logInfo:{}",logInfo);
        }
    }
}
