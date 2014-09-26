package cn.focus.dc.focuswap.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.FocusPasswordUser;
import cn.focus.dc.focuswap.model.PassportProxyRsp;

/**
 * 日志
 * @author zhiweiwen
 *
 */
@Service
public class LogService {

	private static final Log logger = LogFactory.getLog(LogService.class);
	
	@Autowired
	private PassportProxyService passportProxyService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SignService signService;
	
	public static enum LogType {
		LOGIN("user.login"),
		BINDMOBILE("user.bindmobile"),
		ADD("user.add");
		
		private String value = "";
		
		LogType(String value) {
			this.value = value;
		}
	}
	/**
	 * 用户登入注册日志
	 * @param catagory 日志类型 对应LogType各个状态值
	 * @param sohuUid  登入注册搜狐分配的sohuUid
	 * @param isVip    是focus.cn域名账号，是在焦点应用注册的账号都为vip，否则不是 enum： ‘not_vip’，‘vip’ 'user.add' 'user.login' 时必填
	 * @param isActive 'y' ，'n' 账号是否被激活
	 * @param userType 0:focus-passport注册账号（在应用注册功能页面注册的邮箱账号） 5 手机号码直接注册
	 * @param vipCause 0：不是vip  1：焦点注册用户，2：focus.cn域名账号 'user.add' 'user.login' 时必填
	 * @param thirdType 0：搜狐通行证账号（除1、2、3之外的都是0） 1：QQ （第三方账号QQ） 2：SINA 3：手机（手机号注册账号）
	 * @param from 是否是焦点应用注册账号，在焦点应用(搜狐通行证分配ID为1028的应用)注册的账号为 'y' 
	 * @param ip 用户IP
	 * @param source 0：pc 1：app 2：wap
	 * @param userAgent 用户 UA，source为2 时填浏览器信息；source为1 填手机信息，手机品牌+空格+型号+空格+操作系统+空格+操作系统版本号
	 */
	public void sendLog(String uid,String catagory,String sohuUid,String isVip,String isActive,int userType,int vipCause,int thirdType,String from,String ip,int source,String userAgent,String regTime,String appid,String bindMobile) {
		FocusPasswordUser info = new FocusPasswordUser();
		info.setUserId(uid);
		info.setLogCategory(catagory);
		info.setSohuUid(sohuUid);
		info.setIsVip(isVip);
		info.setIsActive(isActive);
		info.setUserType(userType);
		info.setVipCause(vipCause);
		info.setThirdType(thirdType);
		info.setFrom(from);
		info.setIp(ip);
		info.setSource(source);
		info.setUserAgent(userAgent);
		info.setRegTime(regTime);
		info.setBindMobile(bindMobile);
		info.setRegAppid(appid);
		passportProxyService.logRecord(info);
	}
	
	/**
	 * 用户登入注册日志
	 * @param catagory
	 * @param uid
	 * @param sohuUid
	 * @param ip
	 * @param userAgent
	 * @param userType
	 */
	public void sendLrLog(String catagory,String uid,String sohuUid,String ip,String userAgent) {
		PassportProxyRsp user = userService.getUser(uid);
		String isVip = null;
		if(userService.isUserVip(user)) {
			isVip = "vip";
		}
		String isActive = "y";
		int vipCause = userService.getVipCause(user);
		int thirdType = 0;
		String from = null;
		if(userService.isFocusSign(user)) {
			from  = "y";
		}
		int uType = signService.getUidCatagory(uid);
		if(uType == AppConstants.PassportStatus.TYPE_PHONE) {
			//手机的话 得查询到对应的邮箱账号作为uid
			PassportProxyRsp bindUser = passportProxyService.checkBind(uid);
			if(null!=bindUser) {
			uid = bindUser.getUserId();
			} else {
				uid = null;
			}
		}
		int userType = getUserType(uType);
		this.sendLog(uid,catagory, sohuUid, isVip, isActive, userType, vipCause, thirdType, from, ip, 2, userAgent,user.getCreateTime(),user.getRegappId(),user.getMobile());
		
	}
	
	
	private int getUserType(int type) {
	       if(type==0) {
	    	   return 1;
	       } else {
	    	   return 0;
	       }
	       
	}
 	
	
	
	
}
