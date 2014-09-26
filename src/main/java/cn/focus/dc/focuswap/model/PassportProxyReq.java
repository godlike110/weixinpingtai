package cn.focus.dc.focuswap.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 代理SOHU-PASSPORT接口使用的Req类
 * @author rogantian
 * @date 2014-3-27
 * @email rogantianwz@gmail.com
 */
@XStreamAlias("info")
public class PassportProxyReq {

    /**
     * 产品在passport申请的appid,四位数字
     */
    @XStreamAlias("appid")
    private Integer appId;

    /**
     * long型系统时间
     */
    @XStreamAlias("ct")
    private Long ct;

    /**
     * 帐号
     */
    @XStreamAlias("userid")
    private String userId;
    
    /**
     * 校验code
     */
    @XStreamAlias("code")
    private String code;

    /**
     * 手机号
     */
    @XStreamAlias("mobile")
    private String mobile;
    
    /**
     * email
     */
    @XStreamAlias("email")
    private String email;
    
    /**
     * type 获取短信验证码
     * type 1、注册；3、绑定手机号；4解绑手机号
     */
    @XStreamAlias("type")
    private Integer type;
    
    /**
     * password
     */
    @XStreamAlias("password")
    private String password;
    
    /**
     * pwdtype 密码0是明文，1是暗文
     */
    @XStreamAlias("pwdtype")
    private Integer pwdtype;
    
    /**
     * captcha
     */
    @XStreamAlias("captcha")
    private String captcha;
    
    /**
     * email_captcha 邮箱验证码
     */
    @XStreamAlias("email_captcha")
    private String emailCaptcha;
    
    /**
     * createip 用户真实ip
     */
    @XStreamAlias("createip")
    private String createIp;
    
    /**
     * createip 用户真实ip
     */
    @XStreamAlias("modifyip")
    private String modifyip;
    
    /**
     * 是否强制生成昵称
        1：且uniqname为空时，强制生成昵称 
        0或不填：不强制生成昵称
     */
    @XStreamAlias("uniqname_force")
    private Integer uniqnameForce;
    
    /**
     * 昵称
     */
    @XStreamAlias("uniqname")
    private String uniqname;
    
    /**
     * 对于外域注册的账号，在未激活的情况下，引导用户激活的地址
     */ 
    @XStreamAlias("ru")
    private String ru;
    
    /**
     * 注册时间
     */
    @XStreamAlias("createtime")
    private String createTime;
    
    /**
     * 手机状态1：绑定 2：验证
     */
    @XStreamAlias("mobileflag")
    private Integer mobileFlag;
    
    /**
     * 注册产品id
     */
    @XStreamAlias("regappid")
    private Integer regappId;
       
    /**
     * 如果是外域账号，是否发生激活邮件，1为发激活邮件，否则不发激活邮件。
     */
    @XStreamAlias("send_email")
    private String send_email;       

    public String getSend_email() {
        return send_email;
    }

    public void setSend_email(String send_email) {
        this.send_email = send_email;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getMobileFlag() {
        return mobileFlag;
    }

    public void setMobileFlag(Integer mobileFlag) {
        this.mobileFlag = mobileFlag;
    }

    public Integer getRegappId() {
        return regappId;
    }

    public void setRegappId(Integer regappId) {
        this.regappId = regappId;
    }

    public String getRu() {
        return ru;
    }

    public void setRu(String ru) {
        this.ru = ru;
    }

    public String getModifyip() {
        return modifyip;
    }

    public void setModifyip(String modifyip) {
        this.modifyip = modifyip;
    }

    public String getUniqname() {
        return uniqname;
    }

    public void setUniqname(String uniqname) {
        this.uniqname = uniqname;
    }

    public Integer getUniqnameForce() {
        return uniqnameForce;
    }

    public void setUniqnameForce(Integer uniqnameForce) {
        this.uniqnameForce = uniqnameForce;
    }

    public String getEmailCaptcha() {
        return emailCaptcha;
    }

    public void setEmailCaptcha(String emailCaptcha) {
        this.emailCaptcha = emailCaptcha;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPwdtype() {
        return pwdtype;
    }

    public void setPwdtype(Integer pwdtype) {
        this.pwdtype = pwdtype;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
    
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Long getCt() {
        return ct;
    }

    public void setCt(Long ct) {
        this.ct = ct;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
