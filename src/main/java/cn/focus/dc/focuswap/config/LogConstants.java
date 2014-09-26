package cn.focus.dc.focuswap.config;

import org.springframework.stereotype.Component;

/**
 * @author qiaowang
 * @date 2013-12-25 上午11:37:41
 */
@Component
public class LogConstants {

    //工程编号
    public static final int DEFAULT_PROJECT_ID = 1000007;

    //appid
    public static final int DEFAULT_APP_ID = 1007;
    
    //cookie过期时间
    public static final int COOKIE_MAX_AGE = 3600 * 24 * 3650;
    
    //secret_key
    public static final String DEFAULT_SECRECT_KEY = "09bf0f33153bbe16411095339126863b";

    //#号
    public static final String REPLACEMENT_SHARP = "\\#";

    //换行符
    public static final String NEWLINE = "[\r\n]";

    //不存在user-agent
    public static final String NOAGENT = "noagent";

    //user-agent
    public static final String HTTP_USER_AGENT = "user-agent";

    //活跃用户
    public static final String USER_ACCESS = "access";

    //request header 中的referer
    public static final String REQUEST_HEADER_REFERER = "Referer";
    
    //cookie 用户标识
    public static final String COOKIE_ACCESS_TOKEN = "focus_wap_access_token";
    
    //cookie 用户当天第一个referer
    public static final String COOKIE_FIRST_REFERER = "focus_wap_first_referer";
    
    //cookie 用户当天第一个channelId
    public static final String COOKIE_FIRST_CHANNELID = "focus_wap_first_channelId";
    
    //cookie 默认没有referer
    public static final String COOKIE_NO_REFERER = "noreferer";
}
