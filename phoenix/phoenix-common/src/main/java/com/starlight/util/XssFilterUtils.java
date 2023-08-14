package com.starlight.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Component;

/**
 * @Author: shilian
 * @CreateTime: 2023-03-02  09:37
 * @Description: 防止XSS注入工具类
 */
@Component
public class XssFilterUtils {
    /**
     * 防止XSS注入的过滤器
     */
    private static final Pattern XSS_PATTERN = Pattern.compile("<script>|</script>|<iframe>|</iframe>|javascript:");

    /**
     * 过滤掉字符串中的HTML标签和JavaScript代码
     *
     * @param value 要过滤的字符串
     * @return 过滤后的字符串
     */
    public static String stripXSS(String value) {
        if (value != null) {
            // 去掉HTML标签和JavaScript代码
            value = XSS_PATTERN.matcher(value).replaceAll("");
            value = StringEscapeUtils.escapeHtml4(value);
        }
        return value;
    }
}
