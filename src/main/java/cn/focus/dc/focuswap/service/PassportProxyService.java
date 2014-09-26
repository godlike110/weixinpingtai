package cn.focus.dc.focuswap.service;

import static cn.focus.dc.focuswap.config.AppConstants.NEWS_CENTER_NORMAL_NEWS_DIGEST_URL;
import static cn.focus.dc.focuswap.config.AppConstants.PASSPORT_APPID;
import static cn.focus.dc.focuswap.config.AppConstants.PASSPORT_CHECK_BIND_URL;
import static cn.focus.dc.focuswap.config.AppConstants.PASSPORT_CHECK_USER_URL;
import static cn.focus.dc.focuswap.config.AppConstants.PASSPORT_EMAIL_CODE_URL;
import static cn.focus.dc.focuswap.config.AppConstants.PASSPORT_KEY;
import static cn.focus.dc.focuswap.config.AppConstants.PASSPORT_LOGIN_URL;
import static cn.focus.dc.focuswap.config.AppConstants.PASSPORT_MOBILE_CODE_URL;
import static cn.focus.dc.focuswap.config.AppConstants.PASSPORT_REGISTER_FROM_EMAIL_URL;
import static cn.focus.dc.focuswap.config.AppConstants.PASSPORT_REGISTER_FROM_MOBILE_URL;
import static cn.focus.dc.focuswap.config.AppConstants.PASSPORT_THIRD_LOGIN_URL;
import static cn.focus.dc.focuswap.config.AppConstants.NICKNAME_PREFIX;
import static cn.focus.dc.focuswap.config.AppConstants.CHECK_NICKNAME_NEVER_REGISTER;
import static cn.focus.dc.focuswap.config.AppConstants.CHECK_NICKNAME_ILLEGAL_NICKNAME;
import static cn.focus.dc.focuswap.config.AppConstants.CHECK_NICKNAME_USED;
import static cn.focus.dc.focuswap.config.AppConstants.NICKNAME_CHECK_URL;
import static cn.focus.dc.focuswap.config.AppConstants.UPDATE_NICKNAME_URL;
import static cn.focus.dc.focuswap.config.AppConstants.PASSPORT_FOCUS_APPID;
import static cn.focus.dc.focuswap.config.AppConstants.PASSPORT_FOCUS_KEY;
import static cn.focus.dc.focuswap.config.AppConstants.PASSPORT_FOCUS_SIGN_KEY;
import static cn.focus.dc.focuswap.config.AppConstants.FOCUS_PQSSWORD_GET_USER_URL;
import static cn.focus.dc.focuswap.config.AppConstants.FOCUS_PQSSWORD_LOG_RECORD_URL;
import static cn.focus.dc.focuswap.config.AppConstants.GET_USER_INFO;
import cn.focus.dc.commons.service.CommonsService;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.FocusPasswordUser;
import cn.focus.dc.focuswap.model.PassportProxyReq;
import cn.focus.dc.focuswap.model.PassportProxyRsp;
import cn.focus.dc.focuswap.utils.CookieUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.paoding.rose.web.Invocation;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.params.HttpParams;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * SOHU-PASSPORT的接口代理
 * @author rogantian
 * @date 2014-3-27
 * @email rogantianwz@gmail.com
 */
@Component
public class PassportProxyService {
    
    private static Log logger = LogFactory.getLog(PassportProxyService.class);

    public static final int THOUND = 1000;
    
    public static final int RANDOM_STR_LENGTH = 8;
    
    String ppinf = "2|1405320454|0|2|1405329889|0|bG9naW5pZDowOnx1c2VyaWQ6MjA6MTMzMDExNjM3MzBAc29odS5jb218c2VydmljZXVzZTozMDowMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDB8Y3J0OjEwOjIwMTQtMDctMDh8ZW10OjE6MHxhcHBpZDo0Ojk5OTh8dHJ1c3Q6MToxfHBhcnRuZXJpZDoxOjB8cmVsYXRpb246MDp8dXVpZDoxNjowZjRiZjU2NzI0MmM0NWNzfHVpZDoxNjowZjRiZjU2NzI0MmM0NWNzfHVuaXFuYW1lOjA6fA";
    String pprdig = "STj4cUApUIwJKKFmyHVSebeNrqiLG2dnj9IHDLi9b8oqNd4PRpNdVjZV6qqW5D60MR0rCNOXASgPEBLx2u0nANFk6bG97qJRY8rRpYkCi_p6A91fIHpYt9fJ1OtKMHP-CN_TU97TBHgNmWzuUXyoNfoSM5DFNXpMgYtugki4ExA";
    String route = "1c6a1c8087d0d3ede7c0c2fe509e0abd";
    //验证昵称是否正确
    private static int code = 0 ;
    
    //代理SOHU-PASSPORT接口使用的header
    private HttpHeaders headers;
    
    //xml转换对象
    private XStream passportProxyReqXStream;
    
    @Autowired
    private InterfaceService interfaceService;
    
    
	@Autowired
	private CommonsService commonService;
    
    public PassportProxyService() {
        super();
        headers = new HttpHeaders();

        // NoNameCoder解决xml标签中含有下划线（_）时会被替换成双下划线(__)的问题
        StaxDriver stax = new StaxDriver(new NoNameCoder());
        passportProxyReqXStream = new XStream(stax);
    }
    
    @PostConstruct
    public void init() {
        headers.set("Content-Type", "text/xml;charset=UTF-8");
        
        passportProxyReqXStream.processAnnotations(new Class[] {PassportProxyReq.class, PassportProxyRsp.class});
    }
    
    /**
     * 校验账号是否存在，代理SOHU-PASSPORT的接口 <a href="http://wiki.sohu-inc.com/pages/viewpage.action?pageId=4131094">1.1.5.2<a></br>
     * </br>
     * <b>注意：如果用户在前端用手机号作为账号时，需要在手机号后加上@sohu.com来作为userId</b>
     * @param userId
     */
    public PassportProxyRsp checkUser(String userId) {
        PassportProxyRsp rsp = null;       
        PassportProxyReq req = new PassportProxyReq();       
        setGeneral(req,userId);
        req.setUserId(userId);
        
        HttpEntity<String> entity;
        try {
            entity = new HttpEntity<String>(passportProxyReqXStream.toXML(req), headers);
        } catch (Exception e) {
            logger.error("", e);
            return rsp;
        }
        
        String retString = interfaceService.postObjectForString(PASSPORT_CHECK_USER_URL, entity);
        
        if (StringUtils.isNotBlank(retString)) {
            try {
                rsp = (PassportProxyRsp) passportProxyReqXStream.fromXML(retString.trim());
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        
        return rsp;
    }
    
    /**
     * passport提供了先给外域邮箱发送验证码，验证邮箱的真实有效后，直接注册的流程，改流程省去了用户注册后再激活的步骤；各产品如果想使用此功能，可以调用该接口给用户邮箱下发验证码，验证邮箱的真实性
     * <a href="http://confluence.sohuno.com/pages/viewpage.action?pageId=4131048">1.1.1.3 获取邮箱验证码<a>
     * @param email
     * @return
     */
    public PassportProxyRsp getEmailCaptcha(String email){
        PassportProxyRsp rsp = null;       
        PassportProxyReq req = new PassportProxyReq();
        req.setEmail(email);
        setGeneral(req,email);
        HttpEntity<String> entity;
        try {
            entity = new HttpEntity<String>(passportProxyReqXStream.toXML(req), headers);
        } catch (Exception e) {
            logger.error("", e);
            return rsp;
        }
        String retString = interfaceService.postObjectForString(PASSPORT_EMAIL_CODE_URL, entity);
        if (StringUtils.isNotBlank(retString)) {
            try {
                rsp = (PassportProxyRsp) passportProxyReqXStream.fromXML(retString.trim());
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return rsp;
    }
    
    /**
     * 提供各产品的线后台注册用户
     * <a href="http://confluence.sohuno.com/pages/viewpage.action?pageId=4130823">1.1.1.1用户注册接口</a>
     * @param userid
     * @param password   明文
     * @param email_captcha 只有外域邮箱才会有验证码 
     * @param createip 用户真实ip
     * @return
     */
    public PassportProxyRsp registerFromEmail(String userId,String password,String email_captcha,String createIp,boolean mustNickName){
        PassportProxyRsp rsp = null;       
        PassportProxyReq req = new PassportProxyReq();
        setGeneral(req,userId);
        req.setUserId(userId);
        req.setPassword(password);
        req.setSend_email("0");
        String nickName = null;
        if(mustNickName){
            nickName = getUniqNickName();
        }
        if(StringUtils.isNotBlank(nickName)){
            req.setUniqname(nickName);
        }else{
            req.setUniqnameForce(1);
        }
        if(email_captcha != null){
            req.setEmailCaptcha(email_captcha);
        }
        req.setCreateIp(createIp);
        HttpEntity<String> entity;
        try {
            entity = new HttpEntity<String>(passportProxyReqXStream.toXML(req), headers);
        } catch (Exception e) {
            logger.error("", e);
            return rsp;
        }
        String retString = interfaceService.postObjectForString(PASSPORT_REGISTER_FROM_EMAIL_URL,entity);
        if (StringUtils.isNotBlank(retString)) {
            try {
                rsp = (PassportProxyRsp) passportProxyReqXStream.fromXML(retString.trim());
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return rsp;
    }
    
    /**
     * 可以通过此接口查询手机号绑定的账号；
     * <a href="http://confluence.sohuno.com/pages/viewpage.action?pageId=4131189">1.1.3.6 查询手机号绑定的账号</a>
     * @param mobile
     * @return
     */
    public PassportProxyRsp checkBind(String mobile){
        PassportProxyRsp rsp = null;        
        PassportProxyReq req = new PassportProxyReq();        
        setGeneral(req,mobile);
        req.setMobile(mobile);        
        HttpEntity<String> entity;
        try {
            entity = new HttpEntity<String>(passportProxyReqXStream.toXML(req), headers);
        } catch (Exception e) {
            logger.error("", e);
            return rsp;
        }        
        String retString = interfaceService.postObjectForString(PASSPORT_CHECK_BIND_URL, entity);       
        if (StringUtils.isNotBlank(retString)) {
            try {
                rsp = (PassportProxyRsp) passportProxyReqXStream.fromXML(retString.trim());
            } catch (Exception e) {
                logger.error("", e);
            }
        }      
        return rsp;
    }
    
    /**
     * passport相关业务中需要发送短信验证码时调用。根据type区分不同的业务。
     * <a href="http://confluence.sohuno.com/pages/viewpage.action?pageId=4786479">1.1.7.2 获取短信验证码</a>
     * @param mobile
     * @param type 1、注册；3、绑定手机号；4解绑手机号
     * @return
     */
    public PassportProxyRsp getMobileCaptcha(String mobile,Integer type){
        PassportProxyRsp rsp = null;       
        PassportProxyReq req = new PassportProxyReq();
        req.setMobile(mobile);
        req.setType(type);
        setGeneral(req,mobile);
        HttpEntity<String> entity;
        try {
            entity = new HttpEntity<String>(passportProxyReqXStream.toXML(req), headers);
        } catch (Exception e) {
            logger.error("", e);
            return rsp;
        }
        String retString = interfaceService.postObjectForString(PASSPORT_MOBILE_CODE_URL, entity);
        if (StringUtils.isNotBlank(retString)) {
            try {
                rsp = (PassportProxyRsp) passportProxyReqXStream.fromXML(retString.trim());
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return rsp;
    } 
    
    /**
     * 可以通过此接口注册手机号@sohu.com的账号，前提是手机号既没有注册过帐号，也没有绑定过任何账号。
     * <a href="http://confluence.sohuno.com/pages/viewpage.action?pageId=4785542">1.1.1.5 手机号验证码注册接口</a>
     * @param mobile
     * @param password 明文
     * @param captcha 
     * @return
     */
    public PassportProxyRsp registerFromMobile(String mobile,String password,String captcha,String ip){
        PassportProxyRsp rsp = null;       
        PassportProxyReq req = new PassportProxyReq();
        setGeneral(req,mobile);
        req.setMobile(mobile);
        req.setPassword(password);
        req.setCaptcha(captcha);
        HttpEntity<String> entity;
        try {
            entity = new HttpEntity<String>(passportProxyReqXStream.toXML(req), headers);
        } catch (Exception e) {
            logger.error("", e);
            return rsp;
        }
        String retString = interfaceService.postObjectForString(PASSPORT_REGISTER_FROM_MOBILE_URL, entity);
        if (StringUtils.isNotBlank(retString)) {
            try {
                rsp = (PassportProxyRsp) passportProxyReqXStream.fromXML(retString.trim());
                //用户注册成功,则修改昵称
                if(rsp.getStatus() == 0){
                    PassportProxyRsp rspUpdate = updateNickName(mobile+"@sohu.com",ip);
                    if(rspUpdate != null && rspUpdate.getStatus() != 0){
                        logger.info("用户"+mobile +"修改昵称失败");
                    }
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return rsp;
    }
    
    /**
     * 端校验用户名和密码是否正确
     * <a href="http://confluence.sohuno.com/pages/viewpage.action?pageId=4131387">1.1.2.2 wap应用检查用户名和密码是否正确（wap_auth）</a>
     * @param userId 要查询的账号或昵称或手机号
     * @param password
     * @param pwdtype 1为md5后的口令 0为明文密码
     * @return
     */
    public PassportProxyRsp login(String userId,String password,Integer pwdtype){
        PassportProxyRsp rsp = null;       
        PassportProxyReq req = new PassportProxyReq();
        req.setUserId(userId);
        req.setPassword(password);
        if(pwdtype != null){
            req.setPwdtype(pwdtype);
        }
        setGeneral(req,userId);
        HttpEntity<String> entity;
        try {
            entity = new HttpEntity<String>(passportProxyReqXStream.toXML(req), headers);
        } catch (Exception e) {
            logger.error("", e);
            return rsp;
        }
        String retString = interfaceService.postObjectForString(PASSPORT_LOGIN_URL, entity);
        if (StringUtils.isNotBlank(retString)) {
            try {
                rsp = (PassportProxyRsp) passportProxyReqXStream.fromXML(retString.trim());
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return rsp;
    }
    
    /**
     * 
     * <a href="http://confluence.sohuno.com/pages/viewpage.action?pageId=4131343">2.1.1 第三方登陆借口</a>
     * 
     * @param provider 
     * 名称   provider值   支持的登录类型 备注  OAuth协议信息
       QQ空间      qq      web、wap     OAuth2.0不支持refresh token
                     腾讯微博    t.qq    web、wap     OAuth2.0支持refresh token
                     新浪微博    sina    web、wap     OAuth2.0不支持refresh token
                     人人            renren  web、wap     OAuth2.0支持refresh token
       @param ru 授权成功后的回调接口，只支持搜狐旗下产品域名
     */
    @Deprecated
    public String thirdLogin(String provider,String ru){
        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("provider", "qq");
        urlVariables.put("ru", ru);
        return interfaceService.getStringFromInterface(PASSPORT_THIRD_LOGIN_URL,urlVariables);    
        
    }
     
    
    private void setGeneral(PassportProxyReq req,String codePara){
        long ct = System.currentTimeMillis() / THOUND;
        req.setAppId(PASSPORT_APPID);
        req.setCt(ct);
        String code;
        try {
            code = DigestUtils.md5Hex(new StringBuilder(codePara).append(PASSPORT_APPID).append(PASSPORT_KEY).append(ct).toString().getBytes("GBK"));
            req.setCode(code);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * 生成一个唯一的nickname
     * 
     * @return String 生成的昵称
     */
    private String getUniqNickName() {
        PassportProxyRsp rsp = null;
        // 自动生成一个形如：焦点网友XXXX的昵称，并到SOHU passport检验是否重复
        StringBuilder sb = new StringBuilder();
        String suffix = RandomStringUtils.randomNumeric(RANDOM_STR_LENGTH);
        String nickname = (sb.append(NICKNAME_PREFIX).append(suffix)).toString();
        rsp = checkNickName(nickname);
        if(rsp != null){
            code = rsp.getStatus();
        }
        // 检测自动生成的nickname是否可用,3表示昵称已经被使用，6表示系统错误
        while (code != CHECK_NICKNAME_NEVER_REGISTER) {
            if (code != CHECK_NICKNAME_ILLEGAL_NICKNAME || code != CHECK_NICKNAME_USED) {
                nickname = null;
                break;
            }
            suffix = RandomStringUtils.randomNumeric(RANDOM_STR_LENGTH);
            // 清空StringBuilder，比直接new实例化更有效率
            sb.delete(0, sb.length());
            nickname = (sb.append(NICKNAME_PREFIX).append(suffix)).toString();
            rsp = checkNickName(nickname);
            if(rsp != null){
                code = rsp.getStatus();
            }
        }
        return nickname;
    }
    
    /**
     * 对nickname进行校验
     * <a href="http://confluence.sohuno.com/pages/viewpage.action?pageId=4131030">1.1.5.9 检查昵称是否可用接口</a>
     * @param nickName 昵称
     * @return ErrorResult 返回校验结果
     */
    private PassportProxyRsp checkNickName(String nickName) {
        PassportProxyRsp rsp = null;       
        PassportProxyReq req = new PassportProxyReq();       
        setGeneral(req,nickName);
        req.setUniqname(nickName);
        HttpEntity<String> entity;
        try {
            entity = new HttpEntity<String>(passportProxyReqXStream.toXML(req), headers);
        } catch (Exception e) {
            logger.error("", e);
            return rsp;
        }
        String retString = interfaceService.postObjectForString(NICKNAME_CHECK_URL, entity);
        if (StringUtils.isNotBlank(retString)) {
            try {
                rsp = (PassportProxyRsp) passportProxyReqXStream.fromXML(retString.trim());
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return rsp;
    }
    
    /**
     * 根据userid，将输入的用户信息更新，需要修改那些信息，就在输入哪些节点
     * <a href="http://confluence.sohuno.com/pages/viewpage.action?pageId=4131059">1.1.4.1 修改用户信息接口</a>
     * @param nickName 昵称
     * @return ErrorResult 返回校验结果
     */
    private PassportProxyRsp updateNickName(String userId,String ip) {
        PassportProxyRsp rsp = null;       
        PassportProxyReq req = new PassportProxyReq();  
        String nickName = getUniqNickName();
        setGeneral(req,userId);
        req.setUniqname(nickName);
        req.setModifyip(ip);
        req.setUserId(userId);
        HttpEntity<String> entity = null;
        try {
            entity = new HttpEntity<String>(passportProxyReqXStream.toXML(req), headers);
        } catch (Exception e) {
            logger.error("", e);
        }
        String retString = interfaceService.postObjectForString(UPDATE_NICKNAME_URL, entity);
        if (StringUtils.isNotBlank(retString)) {
            try {
                rsp = (PassportProxyRsp) passportProxyReqXStream.fromXML(retString.trim());
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return rsp;
    }
    
    /**
     * 根据userid，查询用户信息
     * <a href="http://confluence.sohuno.com/pages/viewpage.action?pageId=4131019">1.1.5.1 查询用户信息</a>
     * @return ErrorResult 返回校验结果
     */
    public PassportProxyRsp getUserByUserId(String userId) {
        PassportProxyRsp rsp = null;       
        PassportProxyReq req = new PassportProxyReq();  
        setGeneral(req,userId);
        req.setUserId(userId);
        req.setCreateIp("");
        req.setCreateTime("");
        req.setMobileFlag(0);
        req.setRegappId(0);
        req.setMobile("");
        HttpEntity<String> entity = null;
        try {
            entity = new HttpEntity<String>(passportProxyReqXStream.toXML(req), headers);
        } catch (Exception e) {
            logger.error("", e);
        }
        String retString = interfaceService.postObjectForString(GET_USER_INFO, entity);
        if (StringUtils.isNotBlank(retString)) {
            try {
                rsp = (PassportProxyRsp) passportProxyReqXStream.fromXML(retString.trim());
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("", e);
            }
        }
        return rsp;
    }
    
    
    
    /*******************************************************
     * 焦点passpordInterface
     */

    /**
     * focus-user/userinfo 获取用户信息接口
     * <a href = "http://wiki.sohu-inc.com/pages/viewpage.action?pageId=10953874">查询相关用户信息接口</a>
     * @param userId
     * @return
     */
    public String getFocusUserByUserId(String userId){
        //Map<String, Object> urlVariables = new HashMap<String, Object>();
        MultiValueMap<String, Object> urlVariables = new LinkedMultiValueMap<String, Object>();
        urlVariables.add("userId", userId);
        setGeneralFocus(urlVariables);
        String result = interfaceService.postObjectForString(FOCUS_PQSSWORD_GET_USER_URL, urlVariables);
        return result;
    }
    
    /**
     * focus-user/useroperatelog 
     * <a href = "http://wiki.sohu-inc.com/pages/viewpage.action?pageId=10955768">用户日志接口</a>
     * @param userId
     * @return
     */
    public String logRecord(FocusPasswordUser user) {
        // Map<String, Object> urlVariables = new HashMap<String, Object>();
        MultiValueMap<String, Object> urlVariables = new LinkedMultiValueMap<String, Object>();
        if (user == null) {
            return null;
        }
        urlVariables.add("logCategory", user.getLogCategory());
        urlVariables.add("userId", user.getUserId());
        try {
            String nick = user.getNickname() == null?null:java.net.URLEncoder.encode(user.getNickname(), "utf-8");
            urlVariables.add("nickname",nick);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        urlVariables.add("sohuUid", user.getSohuUid());
        urlVariables.add("isVip", user.getIsVip());
        urlVariables.add("isActive", user.getIsActive());
        urlVariables.add("userType", user.getUserType());
        urlVariables.add("vipCause", user.getVipCause());
        urlVariables.add("thirdType", user.getThirdType());
        urlVariables.add("from", user.getFrom());
        urlVariables.add("ip", user.getIp());
        urlVariables.add("regTime", user.getRegTime());
        urlVariables.add("regAppid", user.getRegAppid());
        urlVariables.add("bindMobile", user.getBindMobile());
        urlVariables.add("source", user.getSource());
        urlVariables.add("userAgent", user.getUserAgent());
        setGeneralFocus(urlVariables);

        String result = interfaceService.postObjectForString(FOCUS_PQSSWORD_LOG_RECORD_URL, urlVariables);
        return result;
    }
    
    /**
     * focus-user/getPhoneLoginCode 
     * <a href = "http://wiki.sohu-inc.com/pages/viewpage.action?pageId=4131391">手机登陆验证码发送</a>
     * @param phone
     * @return -1服务错误　其他　服务正常返回．
     */
    public int getPhoneLoginCode(String phone,String ip) {
        PassportProxyRsp rsp = null;       
        PassportProxyReq req = new PassportProxyReq();  
        setGeneral(req,phone);
        req.setMobile(phone);
        HttpEntity<String> entity = null;
		try {
            entity  = new HttpEntity<String>(passportProxyReqXStream.toXML(req), headers);
            String retString = interfaceService.postObjectForString(AppConstants.PHONE_LOGINCODE_SENDURL, entity);
            if(StringUtils.isNotBlank(retString)) {
        	    rsp = (PassportProxyRsp) passportProxyReqXStream.fromXML(retString.trim());
        	    if(null!=rsp) {
        	    	return rsp.getStatus();
        	    }
            }
        } catch (Exception e) {
            logger.error("", e);
            return -1;
        }
		return -1;
        
    }
    
    /**
     * 手机号直接登入
     * <a href = "http://wiki.sohu-inc.com/pages/viewpage.action?pageId=4131393">手机登陆验证</a>
     * @param phone
     * @return -2 服务错误　其他　正常
     * @throws IOException 
     */
    public int phoneCodeLogin(Invocation inv,String phone,String code) throws IOException {
//    	Map<String,Object> variables = new HashMap<String,Object>();
//    	variables.put("mobile", phone);
//    	variables.put("appid", PASSPORT_APPID);
//    	variables.put("captcha", code);
//        String retString = interfaceService.getStringFromInterface(AppConstants.PHONE_LOGIN_WITH_CODE_URL, variables);
//        int result = -2;
//        if(StringUtils.isNotBlank(retString)) {
//        	retString = retString.substring(retString.indexOf("=")+1,retString.length());
//        	JSONObject data = JSON.parseObject(retString);
//        	result = data.getIntValue("errorcode");
//        }
    	int result = -2;
    	HttpClient cli = new HttpClient();
    	GetMethod method = new GetMethod(AppConstants.PHONE_LOGIN_WITH_CODE_URL+"?appid="+ PASSPORT_APPID + "&mobile=" + phone + "&captcha=" + code);
    	int status = cli.executeMethod(method);
    	if(status == HttpStatus.SC_OK) {
    	  String resp = method.getResponseBodyAsString();
          if(StringUtils.isNotBlank(resp)) {
        	resp = resp.substring(resp.indexOf("=")+1,resp.length());
        	JSONObject data = JSON.parseObject(resp);
        	result = data.getIntValue("errorcode");
          }
          Header[] headers = method.getResponseHeaders();
          for(int i=0;i<headers.length;i++) {
        	  if(!headers[i].getName().contains("Set-Cookie")) {
        		  continue;
        	  }
        	  HeaderElement[] hes = headers[i].getElements();
        	  for(HeaderElement he:hes) {
        		  if(he!=null && he.getName().equals("ppinf")) {
        			  System.out.println("");
        			  CookieUtil.addUserCookie(inv.getRequest(), inv.getResponse(), "ppinf", he.getValue(), 10000);
        		  }
        		  if(he!=null && he.getName().equals("pprdig")) {
        			  CookieUtil.addUserCookie(inv.getRequest(), inv.getResponse(), "pprdig", he.getValue(), 10000);
        		  }
        		  System.out.println("value: " + he.getName() + "value: " + he.getValue());
        	  }
          }
    	}
        return result;
    }
    
    public int phoneLoginFromDD(Invocation inv,String phone,String code) {
    	int status = -2;
    	String ppinfo;
    	String pprdig;
    	try {
    		JSONObject res = commonService.phoneCodeLogin(phone, code);
    		status = res.getIntValue("status");
    		if(status == AppConstants.PhoneLogin.LOGIN_SUCC) {
    			String domain = inv.getRequest().getServerName();
    			ppinfo = res.getString("ppinf");
    			pprdig = res.getString("pprdig");
    			CookieUtil.addCommonCookie(inv.getRequest(), inv.getResponse(), "ppinf", ppinfo, ".focus.cn", 15*24*3600, "/");
    			CookieUtil.addCommonCookie(inv.getRequest(), inv.getResponse(), "pprdig", pprdig, ".focus.cn", 15*24*3600, "/");
    		}
    		
    	} catch (Exception e) {
    		logger.error("",e);
    	}
    	return status;
    	
    }
    
    
    private void setGeneralFocus(MultiValueMap<String, Object> urlVariables){
        long ct = System.currentTimeMillis() / THOUND;
        String code = DigestUtils.md5Hex(new StringBuilder().append(PASSPORT_FOCUS_APPID).append(PASSPORT_FOCUS_KEY).append(PASSPORT_FOCUS_SIGN_KEY).append(ct).toString());
        urlVariables.add("appId", PASSPORT_FOCUS_APPID);
        urlVariables.add("secretKey", PASSPORT_FOCUS_KEY);
        urlVariables.add("ct", ct);
        urlVariables.add("code", code);
    }
    
}
