package cn.focus.dc.focuswap.service;

import java.util.Map;

import net.paoding.rose.web.Invocation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.focus.dc.commons.AppInvocation;
import cn.focus.dc.commons.impl.AppInvocationImpl;
import cn.focus.dc.commons.model.UserBasic;
import cn.focus.dc.commons.service.CommonsService;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.PassportProxyRsp;
import cn.focus.dc.focuswap.utils.CookieUtil;

@Service
public class LoginService {

	private static Log logger = LogFactory.getLog(LoginService.class);

	@Autowired
	private PassportProxyService passportProxyService;

	@Autowired
	private CacheHandlerService cacheHandlerService;
	
	@Autowired
	private CommonsService commonService;

	@Autowired
	private LogService logService;

	// ip单日登入次数限制
	private static final int DAY_LOGIN_LIMIT = 20;

	// 账号单日登入次数限制
	private static final int ACCOUNT_DAY_LOGIN_LIMIT = 6;

	// 账号单日登入密码错误次数限制
	private static final int ACCOUNT_DAY_LOGIN_PASSWORDERR_LIMIT = 6;

	// 初始化登入次数
	private static final int DEFAULT_COUNT = 0;

	// 日志登入类别
	private String LOGIN_CATAGOERY = "user.login";
	// request agent 头
	private String HEAD_AGENT = "user-agent";

	/**
	 ** 用户登入
	 * 
	 * @param userId
	 *            要查询的账号或昵称或手机号
	 * @param password
	 * @param type
	 *            0 需要进行登入限制 1 不需要
	 * @param pwdtype
	 *            1为md5后的口令 缺省为 明文密码
	 * @return
	 */
	public PassportProxyRsp login(Invocation inv, String userId,
			String password, Integer pwdtype, String ip, int type) {
		PassportProxyRsp passport = null;
		try {
			if (type == 0) {
				int passwordErrCount = getLoginPasswordErrCount(userId);
				if (passwordErrCount >= ACCOUNT_DAY_LOGIN_PASSWORDERR_LIMIT) {
					passport = new PassportProxyRsp();
					passport.setStatus(AppConstants.PassportStatus.ACC_LOGIN_PASSWORDWRONG_OVERRUN);
					return passport;
				}
			}
			passport = passportProxyService.login(userId, password, pwdtype);
			// 登入成功 ppsmu写入cookie
			if (passport.getStatus() == AppConstants.PassportStatus.LOGIN_SUCCESS) {
				if (type == 0) {
					int ipLoginCount = getLoginCount(ip);
					int accLoginCount = getLoginCount(userId);
					// ip登入限制
					if (ipLoginCount >= DAY_LOGIN_LIMIT) {
						passport = new PassportProxyRsp();
						passport.setStatus(AppConstants.PassportStatus.LOGIN_OVERRRUN);
						return passport;
					} else if (accLoginCount >= ACCOUNT_DAY_LOGIN_LIMIT) {
						// 账号登入限制
						passport = new PassportProxyRsp();
						passport.setStatus(AppConstants.PassportStatus.ACC_LOGIN_OVERRRUN);
						return passport;
					}
				}
				String ppsmu = passport.getPpsmu();

				// 设置ip登入
				setLoginCount(ip);
				// 设置用户登入
				setLoginCount(userId);
				// 登入日志
				logService.sendLrLog(LOGIN_CATAGOERY, userId,
						passport.getuId(), ip,
						inv.getRequest().getHeader(HEAD_AGENT));
				CookieUtil.addCookie(inv.getRequest(), inv.getResponse(),
						"ppsmu", ppsmu, AppConstants.WAP_PPSMU_TIME);
			} else if (passport.getStatus() == AppConstants.PassportStatus.PASSWORD_WRONG) {
				// 设置账号密码错误次数
				setLoginPasswordErrCount(userId);
			}
			return passport;
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	/**
	 * 设置ip或账号当天登入次数
	 * 
	 * @param ipOrAcc
	 *            ip或者账号
	 */
	public void setLoginCount(String ipOrAcc) {
		String key = AppConstants.WAP_DAY_LOGIN_COUNT_KEY + ipOrAcc;
		int count = getLoginCount(ipOrAcc);
		try {
			++count;
			cacheHandlerService.setCache(key,
					AppConstants.WAP_DAY_LOGIN_COUNT_TIME, count);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 重置ip或账号当天登入次数
	 * 
	 * @param ipOrAcc
	 *            ip或者账号
	 */
	public void resetLoginCount(String ipOrAcc) {
		String key = AppConstants.WAP_DAY_LOGIN_COUNT_KEY + ipOrAcc;
		try {
			cacheHandlerService.setCache(key,
					AppConstants.WAP_DAY_LOGIN_COUNT_TIME, 0);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 获取IP或账号当天登入次数
	 * 
	 * @param ipOrAcc
	 *            ip或账号
	 * @return
	 */
	public int getLoginCount(String ipOrAcc) {
		String key = AppConstants.WAP_DAY_LOGIN_COUNT_KEY + ipOrAcc;
		int value = DEFAULT_COUNT;
		try {
			Integer cValue = cacheHandlerService.getCacheValue(key,
					Integer.class);
			if (cValue == null) {
				value = DEFAULT_COUNT;
			} else {
				value = cValue.intValue();
			}
		} catch (Exception e) {
			logger.error("", e);
			return DEFAULT_COUNT;
		}
		return value;
	}

	/**
	 * 获取账号登入密码错误次数
	 * 
	 * @param ipOrAcc
	 *            ip或账号
	 * @return
	 */
	public int getLoginPasswordErrCount(String acc) {
		String key = AppConstants.WAP_DAY_LOGIN_PASSWORD_ERR_COUNT_KEY + acc;
		int value = DEFAULT_COUNT;
		try {
			Integer cValue = cacheHandlerService.getCacheValue(key,Integer.class);
			if (cValue == null) {
				value = DEFAULT_COUNT;
			} else {
				value = cValue.intValue();
			}
		} catch (Exception e) {
			logger.error("", e);
			return DEFAULT_COUNT;
		}
		return value;
	}

	/**
	 * 设置账号登入密码错误次数
	 * 
	 * @param ipOrAcc
	 *            ip或者账号
	 */
	public void setLoginPasswordErrCount(String acc) {
		String key = AppConstants.WAP_DAY_LOGIN_PASSWORD_ERR_COUNT_KEY + acc;
		int count = getLoginPasswordErrCount(acc);
		try {
			++count;
			cacheHandlerService.setCache(key,
					AppConstants.WAP_DAY_LOGIN_PASSWORD_ERR_COUNT_TIME, count);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 获取焦点用户
	 * @param type
	 * @param sohuuId
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public Map registFocusUser(int type,String userId,Invocation inv) throws NumberFormatException, Exception {
		AppInvocation iv = new AppInvocationImpl(inv);
		
		return commonService.newRegister(userId, type, "", AppConstants.PASSPORT_FOCUS_APPID, iv);
	}
	
	/**
	 * sohu　userId获取焦点uid
	 * @param inv
	 * @param userId
	 * @return
	 */
	public Integer getFocusUid(String userId) {
		
		Integer uid =  commonService.getUidBySohuUid(userId);
		return uid;
		
	}
	
	/**
	 * 通过焦点uid获取userBasic
	 * @param focusUid
	 * @return
	 */
	public UserBasic getUserBasicByUid(int focusUid) {
		
		UserBasic ub = commonService.getUserBasicByUid(focusUid);
		return ub;
		
	}
	
	/**
	 * 退出登入
	 * @param inv
	 */
	public void logOut(Invocation inv) {
		
		CookieUtil.delFocusCookie(inv.getRequest(), inv.getResponse(), "ppinfo", null, 0);
		CookieUtil.delFocusCookie(inv.getRequest(), inv.getResponse(), "pprdig", null, 0);
		
	}

}
