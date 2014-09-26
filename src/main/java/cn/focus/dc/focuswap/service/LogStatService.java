package cn.focus.dc.focuswap.service;

import static cn.focus.dc.focuswap.config.LogConstants.COOKIE_ACCESS_TOKEN;
import static cn.focus.dc.focuswap.config.LogConstants.COOKIE_FIRST_CHANNELID;
import static cn.focus.dc.focuswap.config.LogConstants.COOKIE_FIRST_REFERER;
import static cn.focus.dc.focuswap.config.LogConstants.COOKIE_MAX_AGE;
import static cn.focus.dc.focuswap.config.LogConstants.DEFAULT_APP_ID;
import static cn.focus.dc.focuswap.config.LogConstants.DEFAULT_SECRECT_KEY;
import static cn.focus.dc.focuswap.config.LogConstants.HTTP_USER_AGENT;
import static cn.focus.dc.focuswap.config.LogConstants.NEWLINE;
import static cn.focus.dc.focuswap.config.LogConstants.NOAGENT;
import static cn.focus.dc.focuswap.config.LogConstants.REPLACEMENT_SHARP;
import static cn.focus.dc.focuswap.config.LogConstants.REQUEST_HEADER_REFERER;
import static cn.focus.dc.focuswap.config.LogConstants.USER_ACCESS;

import cn.focus.dc.focuswap.model.LogDimension;
import cn.focus.dc.focuswap.model.LogEntry;
import cn.focus.dc.focuswap.model.LogMessageVO;
import cn.focus.dc.focuswap.model.LogMessageVO.LOG_MSG_TYPE;
import cn.focus.dc.focuswap.utils.CookieUtil;
import cn.focus.dc.focuswap.utils.DateUtils;
import cn.focus.dc.focuswap.utils.MD5Util;
import cn.focus.dc.focuswap.utils.RequestIpUtil;
import cn.focus.dc.lc.client.builder.LcLogger;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;

import net.paoding.rose.web.Invocation;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestUtils;



/**
 * @author qiaowang
 */
@Service
public class LogStatService {

    private static Logger logger = LoggerFactory.getLogger(LogStatService.class);

    @Autowired
    private LinkedBlockingQueue<LogMessageVO> msgQueue;
    
    @Autowired
    private LcLogger<Integer> lcLogger;

    /**
     * Description: 通用日志统计接口
     * 
     * @author qiaowang
     * @param message void
     */
    public void statLog(String message) {
        LogMessageVO logMessage = new LogMessageVO();
        logMessage.setMsgType(LOG_MSG_TYPE.COMMONLOG);
        logMessage.setMessage(message);
        msgQueue.add(logMessage);
    }

    /**
     * Description:活跃用户日志统计接口
     * 
     * @author qiaowang
     * @param message void
     */
    public void statAccessLog(String message) {
        LogMessageVO logMessage = new LogMessageVO();
        logMessage.setMsgType(LOG_MSG_TYPE.ACCESSLOG);
        logMessage.setMessage(message);
        msgQueue.add(logMessage);
    }

    /**
     * 日志前缀
     * 
     * @author qiaowang
     * @date 2013-12-25 下午2:56:22
     * @return String
     */
    private void setLogDefault(Invocation inv, LogEntry log) {

        // 用户标识
        String accessToken = CookieUtil.getCookieValueByName(inv.getRequest(), COOKIE_ACCESS_TOKEN);
        if (StringUtils.isBlank(accessToken)) {
            // 没有渠道用户则生成用户ID
            StringBuilder builder = new StringBuilder();
            builder.append(DEFAULT_APP_ID).append(DEFAULT_SECRECT_KEY)
                    .append(RequestIpUtil.getIpAddress(inv.getRequest())).append(System.currentTimeMillis())
                    .append(new Random());
            // 生成用户唯一标识
            accessToken = MD5Util.md5(builder.toString());
            CookieUtil.addCookie(inv.getRequest(), inv.getResponse(), COOKIE_ACCESS_TOKEN, accessToken, COOKIE_MAX_AGE);
        }

        log.setLogTime(System.currentTimeMillis());
        log.setAppId(DEFAULT_APP_ID);
        log.setCategory(USER_ACCESS);
        log.setUid(accessToken);
        log.setClientIP(RequestIpUtil.getIpAddress(inv.getRequest()));

        log.setUserAgent(inv.getRequest().getHeader(HTTP_USER_AGENT) != null ? inv.getRequest()
                .getHeader(HTTP_USER_AGENT).replaceAll(NEWLINE, REPLACEMENT_SHARP) : NOAGENT);

    }

    /**
     * Access
     * 
     * @param inv
     * @param pageNo
     * @param typeId
     */
    @SuppressWarnings("unchecked")
    public void statAccess(Invocation inv) {
        try {
            LogEntry log = new LogEntry();
            setLogDefault(inv, log);

            String uri = inv.getRequest().getRequestURI();
            log.setVisitURL(StringUtils.isNotBlank(uri) ? uri : StringUtils.EMPTY);

            //获取当天第一个渠道
            String firstChannelId = CookieUtil.getCookieValueByName(inv.getRequest(), COOKIE_FIRST_CHANNELID);
            // 用户当天第一个refer
            String firstReferer = CookieUtil.getCookieValueByName(inv.getRequest(), COOKIE_FIRST_REFERER);
            if(StringUtils.isBlank(firstChannelId) || "0".equals(firstChannelId)){
                //未获取第一个渠道或渠道为"0",则获取渠道
                String channelId = ServletRequestUtils.getStringParameter(inv.getRequest(), "channelId", "0");
                if(StringUtils.isBlank(channelId) || "0".equals(channelId)){
                    //如未获取渠道，或者渠道为0，则获取第一个refer
                    if (StringUtils.isBlank(firstReferer)) {
                        //如当天第一个refer没有，则获取refer
                        firstReferer = inv.getRequest().getHeader(REQUEST_HEADER_REFERER);
                        if (StringUtils.isNotBlank(firstReferer)) {
                            //如果refer存在，则加入cookie
                            CookieUtil.addCookie(inv.getRequest(), inv.getResponse(), COOKIE_FIRST_REFERER,
                                    firstReferer,DateUtils.getTodayRestTimeStamp());
                        } 
                    }
                } else {
                    //获取渠道则加入cookie
                    CookieUtil.addCookie(inv.getRequest(), inv.getResponse(), COOKIE_FIRST_CHANNELID,
                            channelId,
                            DateUtils.getTodayRestTimeStamp());
                    firstChannelId = channelId;
                }
            }

            log.setChannelId(StringUtils.isNotBlank(firstChannelId) ? firstChannelId : "0");
            log.setOriginURL(StringUtils.isNotBlank(firstReferer) ? firstReferer : StringUtils.EMPTY);
            
            List<LogDimension> dimensions = new ArrayList<LogDimension>();
            String cityName = ServletRequestUtils.getStringParameter(inv.getRequest(), "cityName",
                    StringUtils.EMPTY);
            LogDimension city = new LogDimension();
            city.setName("cityName");
            city.setValue(cityName);
            city.setSequence(1);

            dimensions.add(city);

            int gId = ServletRequestUtils.getIntParameter(inv.getRequest(), "groupId", 0);
            LogDimension groupId = new LogDimension();
            groupId.setName("groupId");
            groupId.setValue(String.valueOf(gId));
            groupId.setSequence(2);
            dimensions.add(groupId);

            Map<String, Object> message = new HashMap<String, Object>();
            Map<String, Object> paramList = new HashMap<String, Object>();

            String channelUser = ServletRequestUtils.getStringParameter(inv.getRequest(), "channelUser",
                    StringUtils.EMPTY);
            if (StringUtils.isNotBlank(channelUser)) {
                paramList.put("channelUser", channelUser);
            }

            StringBuffer bufUrl = new StringBuffer();
            bufUrl.append(inv.getRequest().getRequestURL().toString());
            String method = inv.getRequest().getMethod();
            if ("GET".equals(method)) {
                // get参数获取
                paramList.put("getData", getParameterData(inv.getRequest().getParameterMap()));
            } else if ("POST".equals(method)) {
                // post参数获取
                paramList.put("postData", getParameterData(inv.getRequest().getParameterMap()));
            }
            paramList.put("url", StringUtils.isNotBlank(bufUrl.toString()) ? bufUrl.toString() : StringUtils.EMPTY);
            message.put("params", paramList);
            message.put("dimensions", dimensions);
            log.setMessage(message);
            //sring-xd 日志接收
//            statAccessLog(logResult(log));
            //focus-log-center 日志接收
            sendSringLog(logResult(log));
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * 返回日志结果json字串
     * 
     * @param object 数据对象
     * @param pageTotal 结果列表总共多少页
     * @param pageSize 每页多少条记录
     * @param pageNo 页号
     * @return json 字串
     */
    public static String logResult(LogEntry log) {
        return JSON.toJSONString(log);
    }
    
    /**
     * focus-log-center 单条日志接收
     * @author qiaowang 
     * @date 2014-2-7 下午12:05:22
     * @param message void
     */
    public void sendSringLog(String message){
        try {
            //单条
            lcLogger.create().sendStringLog(message);
        } catch (Exception e) {
            logger.error("focus log center error", e);
        }
    }
    
    /**
     * focus-log-center 多条日志接收
     * @author qiaowang 
     * @date 2014-2-11 下午5:51:44
     * @param message void
     */
    public void sendSringLogs(String message){
        try {
            //多条
            StringBuilder buf = new StringBuilder();
            buf.append("[").append(message).append("]");
            lcLogger.create().sendStringLogs(buf.toString());
            
        } catch (Exception e) {
            logger.error("focus log center error", e);
        }
    }

    private Map<String, String> getParameterData(Map<String, String[]> params) {
        Map<String, String> map = convertMap(params);
        Map<String, String> ret = new TreeMap<String, String>(map);
        return ret;
    }

    private Map<String, String> convertMap(Map<String, String[]> params) {
        Map<String, String> map = new HashMap<String, String>();
        if (params == null || params.size() == 0) {
            return map;
        }
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            if (entry.getValue() != null && entry.getValue().length > 0) {
                map.put(entry.getKey(), entry.getValue()[0]);
            }
        }
        return map;
    }
    
    
    public void wapStatLog(String message) {
        try {
            LogEntry log = new LogEntry();
            log.setLogTime(System.currentTimeMillis());
            log.setAppId(DEFAULT_APP_ID);
//          log.setCategory(USER_ACCESS);
            String[] strArray = StringUtils.split(message, "|");
            if (null != strArray && strArray.length > 0) {
                log.setCategory(StringUtils.defaultString(strArray[0]));
                log.setUid(StringUtils.defaultString(strArray[1]));
                log.setClientIP(StringUtils.defaultString(strArray[3]));
                log.setUserAgent(StringUtils.defaultString(strArray[4]));
                Map<String, Object> msg = new HashMap<String, Object>();
                Map<String, String> paramList = new HashMap<String, String>();
                paramList.put("thirdType", StringUtils.defaultString(strArray[2]));
                msg.put("params", paramList);
                log.setMessage(msg);
            }
            //spring-xd日志接收
//            statAccessLog(logResult(log));
            //focus-log-center日志接收
            sendSringLog(logResult(log));
        } catch (Exception e) {
            logger.error("", e);
        }
    }

}
