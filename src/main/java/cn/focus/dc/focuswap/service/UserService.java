package cn.focus.dc.focuswap.service;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import scala.collection.Map;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.PassportProxyRsp;
import cn.focus.dc.focuswap.model.User;

@Service
public class UserService {

	private static Log logger = LogFactory.getLog(UserService.class);
	
	// focus.cn域名账号
	private static String FDOMAIN = "@focus.cn";
	
	@Autowired
	private PassportProxyService passportProxyService;
	
	@Autowired
	private SignService signService;
	
	@Autowired
	private CacheHandlerService cacheHandler;
	
	/**
	 * 通过uid获取user对象
	 * @param uid
	 * @return
	 */
	public PassportProxyRsp getUser(String uid) {
		PassportProxyRsp rsp = passportProxyService.getUserByUserId(uid);
		return rsp;
	}
	
	/**
	 * 用户是不是vip (focus.cn域名账号，在焦点应用注册的账号都为vip)
	 * @param uid
	 * @return
	 */
	public boolean isUserVip(PassportProxyRsp user) {
		boolean flag = false;
		if(signService.getUidCatagory(user.getUserId())==AppConstants.PassportStatus.TYPE_EMAIL && user.getUserId().contains(FDOMAIN)) {
			flag = true;
		} else {
			if(user!=null && StringUtils.isNotBlank(user.getRegappId()) && Integer.parseInt(user.getRegappId()) == AppConstants.PASSPORT_APPID) {
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 为什么是vip 0：不是vip  1：焦点注册用户，2：focus.cn域名账号
	 * @param uid
	 * @return
	 */
	public int getVipCause(PassportProxyRsp user) {
		int flag = 0;
		if(signService.getUidCatagory(user.getUserId())==AppConstants.PassportStatus.TYPE_EMAIL && user.getUserId().contains(FDOMAIN)) {
			flag = 2;
		} else {
			if(user!=null &&  StringUtils.isNotBlank(user.getRegappId()) && Integer.parseInt(user.getRegappId()) == AppConstants.PASSPORT_APPID) {
				flag = 1;
			}
		}
		return flag;
	}
	
	//是否是焦点注册
	public boolean isFocusSign(PassportProxyRsp user) {
		if(user!=null &&  StringUtils.isNotBlank(user.getRegappId()) && Integer.parseInt(user.getRegappId())== AppConstants.PASSPORT_APPID) {
			return true;
		}
		return false;
	}
	
	public void setUserToCache(String userName,int uid) {
		try {
			cacheHandler.setCache(AppConstants.FOCUS_USER_KEY + userName , AppConstants.FOCUS_USER_SAVE_TIME, uid);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * 获取焦点用户id 没有获取到的话返回-1
	 * @param userName
	 * @return
	 */
	public Integer getFocusUid(String userName) {
		Integer uid = null ;
		try {
			uid = cacheHandler.getCacheValue(AppConstants.FOCUS_USER_KEY + userName, Integer.class);

		} catch (Exception e) {
			logger.error(e);
		}
		return uid;
	}
}
