package cn.focus.dc.focuswap.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XStreamAlias("result")
public class PassportProxyRsp {

    /**
     * 状态
     * 1.1.2.2:1 :参数错误 2 :code错误 3 :密码错误 5 : 黑名单用户 6：处理异常 7 未激活的手机注册用户 8 未激活的外域账号 11 : 服务器网络故障
     * 1.1.1.3 0 可用，1参数错误，2code错误， 3 邮箱错误（可能是邮箱格式错误或是禁止注册的邮箱地址eg@wap.sohu.com），  4发送验证码次数超限（一个邮箱一天最多可发送6次，超过6次会返回4） 5验证码输入错误超限，不再发送邮件 6系统错误
     * 1.1.3.6 0 成功，1参数错误，2验证码错误，3手机号码没有绑定用户， 6查询失败
     * 1.1.7.2 0 成功，1参数错误，2code错误，3 发送短信验证码次数超限，4 手机号@sohu.com的账号已经存在，5 手机号已经绑定了其他账号，6 手机号没有绑定账号，7手机号@sohu.com的账号不能解除绑定，9系统错误
     * 1.1.1.5 0：成功   1：参数错误 2：接口签名校验不通过 3：系统错误 4：账号已经存在 5：手机号已经绑定了其他账号 6：发送验证码的次数超限 7：验证码校验次数超限 8：验证码错误或者已经过期
     */
    @XStreamAlias("status")
    private Integer status;

    /**
     * 如果用户存在，返回结果中会增加一个flag字段，根据flag判断用户是否激活 ： 0表示未激活，1 表示已经激活
     */
    @XStreamAlias("flag")
    private String flag;
    
    /**
     * 帐号
     */
    @XStreamAlias("userid")
    private String userId;

    /**
     * 系统为用户分配的唯一标识,除了17173和37wanwan，新注册用户的uid和uuid已经统一
     */
    @XStreamAlias("uid")
    private String uId;
    
    /**
     * 系统为用户分配的唯一标识,除了17173和37wanwan，新注册用户的uid和uuid已经统一
     */
    @XStreamAlias("uuid")
    private String uuId;
    
    /**
     * 登陆成功时，返回此值，用来获取登陆状态，wap的nginx会从ppsmu中解析出userid放到x-sohu-mobile-userid的header里面
     */
    @XStreamAlias("ppsmu")
    private String ppsmu;
    
    /**
     * 用户在搜狐的昵称
     */
    @XStreamAlias("uniqname")
    private String uniqName;
    
    /**
     * sohu_mobile_user
     */
    @XStreamAlias("sohu_mobile_user")
    private String sohu_mobile_user;
    
    @XStreamAlias("errmsg")
    private String errMsg;  
    
    /**
     * 注册时间
     */
    @XStreamAlias("createtime")
    private String createTime;
    
    /**
     * 手机状态1：绑定 2：验证
     */
    @XStreamAlias("mobileflag")
    private String mobileFlag;
    
    /**
     * 手机
     */
    @XStreamAlias("mobile")
    private String mobile;
    
    /**
     * 注册产品id
     */
    @XStreamAlias("regappid")
    private String regappId;
    
    /**
     * createip 用户真实ip
     */
    @XStreamAlias("createip")
    private String createIp;
    
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

  

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

   
    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getPpsmu() {
        return ppsmu;
    }

    public void setPpsmu(String ppsmu) {
        this.ppsmu = ppsmu;
    }

    public String getUniqName() {
        return uniqName;
    }

    public void setUniqName(String uniqName) {
        this.uniqName = uniqName;
    }

    public String getSohu_mobile_user() {
        return sohu_mobile_user;
    }

    public void setSohu_mobile_user(String sohu_mobile_user) {
        this.sohu_mobile_user = sohu_mobile_user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    
    
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMobileFlag() {
        return mobileFlag;
    }

    public void setMobileFlag(String mobileFlag) {
        this.mobileFlag = mobileFlag;
    }

    public String getRegappId() {
        return regappId;
    }

    public void setRegappId(String regappId) {
        this.regappId = regappId;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
