package cn.focus.dc.focuswap.utils;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.paoding.rose.web.Invocation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.focus.dc.focuswap.model.LoginType;
import cn.focus.dc.focuswap.model.User;
import cn.focus.dc.focuswap.service.LoginService;
import cn.focus.dc.focuswap.service.PassportProxyService;

public class UserUtil {
	
	protected static Log logger = LogFactory.getLog(UserUtil.class);
	
	//第三方登入用
	private static String UID_IN_HEAD = "x-sohu-mobile-userid";
	
	//手机登入userid
	private static String USERID = "X-SohuPassport-UserId";
	
	//登入产品Id
	private static String APPID = "1007";
	
	@Autowired
	private PassportProxyService passportProxyService;

	
	@Autowired
	private LoginService loginService;

	/**
	 * 获取用户账号
	 * @param inv
	 * @return
	 */
	public static String getUserAccount(Invocation inv) {
		return inv.getRequest().getHeader(UID_IN_HEAD);
	}
	
	/**
	 * 获取用户
	 * @param inv
	 * @return
	 */
	public static User getUser(Invocation inv) {
		User user = null;
		String userid = inv.getRequest().getHeader(USERID);
		if(StringUtils.isNotBlank(userid)) {
			user = new User();
			user.setAppId(APPID);
			user.setUserId(userid);
		}
		return user;
	}
	
	/**
	 * 获取全局唯一标识
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	public static int getLoginType(Invocation inv) {
		HttpServletRequest request = inv.getRequest();
		String type = request.getParameter("loginType");
		if(null==type) {
			return 7;
		} else {
			return LoginType.typeMap.get(type);
		}
	}

}
