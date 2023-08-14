package com.starlight.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 日志配置
 *
 * @author wenyunfei
 */
@Configuration
@PropertySource("classpath:log.properties")
@Data
public class LogConfig {
    @Value("${log.path}")
    private String logPath;

    @Value("${log.port}")
    private String logPort;

    @Value("${log.server}")
    private String logServer;
}
