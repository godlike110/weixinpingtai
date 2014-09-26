package cn.focus.dc.focuswap.utils;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

public class IpUtil {

    private static Log logger = LogFactory.getLog(IpUtil.class);

    /**
     * 获得客户端真实IP地址
     * 
     * @[author]param[/author] request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (logger.isDebugEnabled()) {
            logger.debug(new StringBuffer().append("X-Forwarded-For:").append(request.getHeader("X-Forwarded-For"))
                    .append("\tProxy-Client-IP:").append(request.getHeader("Proxy-Client-IP"))
                    .append("\t:WL-Proxy-Client-IP:").append(

                    request.getHeader("WL-Proxy-Client-IP")).append("\t:HTTP_X_FORWARDED_FOR:")
                    .append(request.getHeader("HTTP_X_FORWARDED_FOR")).append("\tRemoteAddr:")
                    .append(request.getRemoteAddr()).toString());

        }
        String ip = request.getHeader("X-Forwarded-For");
        ip = getTrueIp(ip);

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            ip = getTrueIp(ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            ip = getTrueIp(ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            ip = getTrueIp(ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            ip = getTrueIp(ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            ip = getTrueIp(ip);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("parsed IP:" + ip);
        }
        return ip;
    }

    /**
     * 取真实客户端IP，过滤代理IP
     * 
     * @[author]param[/author] ip
     * @return
     */
    public static String getTrueIp(String ip) {
        if (ip == null || "".equals(ip))
            return null;
        if (ip.indexOf(",") != -1) {
            String[] ipAddr = StringUtils.split(ip, ",");
            for (int i = 0; i < ipAddr.length; i++) {
                if (isValidIP(ipAddr[i].trim()) && !ipAddr[i].trim().startsWith("10.")
                        && !ipAddr[i].trim().startsWith("172.16")) {
                    return ipAddr[i].trim();
                }
            }
        } else {
            if (isValidIP(ip.trim()) && !ip.trim().startsWith("10.") && !ip.trim().startsWith("172.16"))
                return ip.trim();
        }
        return null;
    }

    private static final Pattern ipPattern = Pattern.compile("([0-9]{1,3}\\.){3}[0-9]{1,3}");

    private static boolean isValidIP(String ip) {
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            return false;
        }
        return ipPattern.matcher(ip).matches();
    }

}