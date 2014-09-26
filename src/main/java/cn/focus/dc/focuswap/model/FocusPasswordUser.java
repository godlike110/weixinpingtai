package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;

public class FocusPasswordUser extends BaseObject {

    /**
     * enum：'user.login'，'user.bindmobile'，'user.add'
     */
    private static final long serialVersionUID = -1L;

    private String logCategory;

    /**
     * 用户名
     */
    private String userId;

    /**
     *  昵称 'user.add' 时必填
     */
    private String nickname;

    /**
     * 搜狐通行证uid
     */
    private String sohuUid;


    /**
     * 是focus.cn域名账号，是在焦点应用注册的账号都为vip，否则不是 enum： ‘not_vip’，‘vip’ 'user.add' 时必填
     */
    private String isVip;

    /**
     * enum：'y' ，'n' 账号是否被激活 'user.add' 时必填
     */
    private String isActive;

    /**
     * 0:focus-passport注册账号（在应用注册功能页面注册的邮箱账号） 5 手机号码直接注册（在应用注册功能页面注册的手机账号）
     */
    private Integer userType;

    /**
     * 0：不是vip  1：焦点注册用户，2：focus.cn域名账号 'user.add' 时必填
     */
    private Integer vipCause;

    /**
     * 0：搜狐通行证账号（除1、2、3之外的都是0） 1：QQ （第三方账号QQ） 2：SINA 3：手机（手机号注册账号）
     */
    private Integer thirdType;

    /**
     * 是否是焦点应用注册账号，在焦点应用(搜狐通行证分配ID为1028的应用)注册的账号为 'y' ； 在搜狐其他产品线注册账号为 'n' 'user.add' 时必填
     */
    private String from;

    /**
     * 用户IP
     */
    private String ip;

    /**
     * userId在sohu-passport注册时的时间
     */
    private String regTime;

    /**
     * userId在sohu-passport注册时的产品线ID，比如：用户在焦点通行证注册，则regAppid=1028
     */
    private String regAppid;

    /**
     * userId绑定的手机号，没有则为空 'user.bindmobile'，'user.add' 时必填
     */
    private String bindMobile;

    /**
     * 0：pc 1：app 2：wap
     */
    private Integer source;

    /**
     * 用户 UA，source为2 时填浏览器信息；source为1 填手机信息，手机品牌+空格+型号+空格+操作系统+空格+操作系统版本号
     */
    private String userAgent;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSohuUid() {
        return sohuUid;
    }

    public void setSohuUid(String sohuUid) {
        this.sohuUid = sohuUid;
    }

    public String getLogCategory() {
        return logCategory;
    }

    public void setLogCategory(String logCategory) {
        this.logCategory = logCategory;
    }

    public String getIsVip() {
        return isVip;
    }

    public void setIsVip(String isVip) {
        this.isVip = isVip;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getVipCause() {
        return vipCause;
    }

    public void setVipCause(Integer vipCause) {
        this.vipCause = vipCause;
    }

    public Integer getThirdType() {
        return thirdType;
    }

    public void setThirdType(Integer thirdType) {
        this.thirdType = thirdType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getRegAppid() {
        return regAppid;
    }

    public void setRegAppid(String regAppid) {
        this.regAppid = regAppid;
    }

    public String getBindMobile() {
        return bindMobile;
    }

    public void setBindMobile(String bindMobile) {
        this.bindMobile = bindMobile;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

}
