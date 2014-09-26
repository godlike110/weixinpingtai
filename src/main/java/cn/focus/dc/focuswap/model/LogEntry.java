package cn.focus.dc.focuswap.model;

import java.util.Map;

/**
 * @author qiaowang
 * @date 2013-12-30 下午5:30:04
 */
public class LogEntry {
    
    private long logTime = 0;
    
    private int appId;
    
    private String clientIP;
    
    private String category;
    
    private Map<String, Object> message;
    
    private String uid;
    
    private String channelId;
    
    private String originURL;
    
    private String visitURL;
    
    private String userAgent;
    
    public long getLogTime() {
        return logTime;
    }

    public void setLogTime(long logTime) {
        this.logTime = logTime;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Map<String, Object> getMessage() {
        return message;
    }

    public void setMessage(Map<String, Object> message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getOriginURL() {
        return originURL;
    }

    public void setOriginURL(String originURL) {
        this.originURL = originURL;
    }

    public String getVisitURL() {
        return visitURL;
    }

    public void setVisitURL(String visitURL) {
        this.visitURL = visitURL;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

}
