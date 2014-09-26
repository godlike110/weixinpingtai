package cn.focus.dc.focuswap.utils;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public final class RequestIpUtil {
    
    private RequestIpUtil(){
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown")) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown")) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase(ip, "unknown")) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.isNotBlank(ip) && StringUtils.indexOf(ip, ",") > 0) {
            String[] ipArray = StringUtils.split(ip, ",");
            ip = ipArray[0];
        }
        return ip;
    }
}