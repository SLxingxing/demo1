package com.starlight.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.stereotype.Component;

/**
 * ip地址工具类
 *
 * @author wenyunfei
 */
@Component
public class IpAddrUtils {
    private static String ipAddress;

    /**
     * 获取ip地址
     *
     * @return ip地址
     * @throws UnknownHostException 异常
     */
    public static String getIpAddress() throws UnknownHostException {
        if (StringUtils.isNotBlank(ipAddress)) {
            return ipAddress;
        }
        InetAddress ip=InetAddress.getLocalHost();
        ipAddress = ip.getHostAddress();
        return ipAddress;
    }

    private static boolean checkAddress(InetAddress address) {
        return address.isLoopbackAddress() && (!address.getHostAddress().contains(":"));
    }
}
