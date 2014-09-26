package cn.focus.dc.focuswap.controllers.passport;

import cn.focus.dc.commons.service.CommonsService;
import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.config.AppConstants.PassportStatus;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.User;
import cn.focus.dc.focuswap.service.CaptureService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.IpService;
import cn.focus.dc.focuswap.service.LoginService;
import cn.focus.dc.focuswap.service.PassportProxyService;
import cn.focus.dc.focuswap.service.SignService;
import cn.focus.dc.focuswap.utils.DeviceUtils;
import cn.focus.dc.focuswap.utils.JsonResponseUtil;
import cn.focus.dc.focuswap.utils.UserUtil;
import cn.focus.dc.focuswap.utils.Utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Enumeration;

import javax.servlet.http.Cookie;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;

@Path("/login/")
@LoginRequired
public class LoginController {

	private static Log logger = LogFactory.getLog(LoginController.class);

	@Autowired
	private LoginService loginService;

	@Autowired
	private CaptureService captureService;

	@Autowired
	private SignService signService;

	@Autowired
	private IpService ipService;

	@Autowired
	private PassportProxyService passportProxyService;

	@Autowired
	private CommonsService commonService;

	@Autowired
	private CityService cityService;

	// 明文
	private static final int NOSECRET = 0;

	/**
	 * 用户登入
	 * 
	 * @param inv
	 * @param account
	 * @param password
	 * @param verifycode
	 * @return
	 */
	/*
	 * @Post("")
	 * 
	 * @Get("") public String login(Invocation inv,@Param("account") String
	 * account,@Param("password") String password,@Param("verifycode") String
	 * verifycode,@Param("uuid") String uuid) {
	 * 
	 * String userId = account;
	 * 
	 * PassportProxyRsp pass = null;
	 * 
	 * String ip = ipService.getIpAddr(inv.getRequest()); if (null == account) {
	 * return "@err request"; }
	 * 
	 * int accountType = signService.getUidCatagory(account);
	 * 
	 * if(accountType == AppConstants.PassportStatus.OTHER) { return
	 * JsonResponseUtil
	 * .okWithStatusAndMesage(PassportStatus.LOGIN_EMAIL_ERR,PassportStatus
	 * .LOGIN_EMAIL_ERR_STR); }
	 * 
	 * if(accountType == AppConstants.PassportStatus.TYPE_PHONE){ userId =
	 * account + "@sohu.com"; }
	 * 
	 * if(!signService.checkUser(userId)) { if(accountType ==
	 * AppConstants.PassportStatus.TYPE_PHONE) { //如果是手机账号登入 先检查是否存在
	 * 不存在再检查是否绑定，绑定了也让他登入 if(signService.checkbind(account)) {
	 * 
	 * } else { return
	 * JsonResponseUtil.okWithStatusAndMesage(PassportStatus.UNKNOW_USERID
	 * ,PassportStatus.UNKNOW_USERID_STR); } } else { return
	 * JsonResponseUtil.okWithStatusAndMesage
	 * (PassportStatus.UNKNOW_USERID,PassportStatus.UNKNOW_USERID_STR); } }
	 * //图片验证码验证 if(null != verifycode && StringUtils.isNotBlank(verifycode)) {
	 * if(!captureService.checkCapture(uuid, verifycode)) { return
	 * JsonResponseUtil
	 * .okWithStatusAndMesage(PassportStatus.VERICODE_ERROR,PassportStatus
	 * .getLoginStatus(PassportStatus.VERICODE_ERROR)); } }
	 * 
	 * pass = loginService.login(inv,account, password,
	 * NOSECRET,ip,PassportStatus.NEED_LOGIN_LIMIT);
	 * 
	 * if(null == pass) { return
	 * JsonResponseUtil.okWithStatusAndMesage(PassportStatus
	 * .OUT_SERVICE,PassportStatus.getLoginStatus(PassportStatus.OUT_SERVICE));
	 * }
	 * 
	 * int status = pass.getStatus();
	 * 
	 * if(status != PassportStatus.OUT_SERVICE ) { return
	 * JsonResponseUtil.okWithStatusAndMesage
	 * (status,PassportStatus.getLoginStatus(status)); } else { return
	 * JsonResponseUtil
	 * .okWithStatusAndMesage(PassportStatus.OUT_SERVICE,PassportStatus
	 * .getLoginStatus(PassportStatus.OUT_SERVICE)); }
	 * 
	 * }
	 */

	/**
	 * 账号检查
	 * 
	 * @param inv
	 * @param account
	 * @param type
	 * @return
	 */
	@Get("check")
	public String check(Invocation inv, @Param("account") String account,
			@Param("type") int type) {
		if (type == PassportStatus.TYPE_PHONE) {
			account = account + "@sohu.com";
		}
		if (!signService.checkUser(account)) {
			return JsonResponseUtil.okWithStatusAndMesage(
					PassportStatus.USERID_AVAILABLE, "用户不存在");
		}
		return JsonResponseUtil.okWithStatusAndMesage(1, "用户可用");
	}
	
	@Get("cookie")
	public String getCookie(Invocation inv) {
		
		Cookie[] cs = inv.getRequest().getCookies();
		String sb = new String("");
		for(Cookie c:cs) {
			sb = sb + c.getName() +" :" +c.getValue() + "\n";
			System.out.println(c.getName() +" :" +c.getValue());
		}
		return "@" + sb;
	}
	
	@Get("header") 
	public String getHeader(Invocation inv) {
		Enumeration headerNames = inv.getRequest().getHeaderNames();
		String sb = new String("");
		while(headerNames.hasMoreElements()) {
			String name = (String) headerNames.nextElement();
			sb = sb + name + ":" + inv.getRequest().getHeader(name) + "   ";
		}
		return "@" + sb;
	}


	@LoginRequired
	@Get("getuserinfo")
	public String info(Invocation inv) {
		String uid = inv.getRequest().getHeader("x-sohu-mobile-userid");
		logger.info("get info : " + uid);
		return "@" + uid;
	}

	/**
	 * 登入限制value重置
	 * 
	 * @return
	 */
	@Get("reset")
	public String clearLimit(Invocation inv, @Param("account") String account) {
		loginService.resetLoginCount(account);
		return "@已经清除" + account + "今日的登入限制";
	}

	/**
	 * 手机验证码登入
	 * 
	 * @param inv
	 * @param phone
	 *            　手机
	 * @param code
	 *            　验证码
	 * @param ru
	 *            　跳转url
	 * @return
	 * @throws IOException
	 */
	@Get("phonelogin")
	public String phoneCodeLogin(Invocation inv, @Param("phone") String phone,
			@Param("code") String code, @Param("ru") String ru)
			throws IOException {
		int rsp = passportProxyService.phoneLoginFromDD(inv, phone, code);
		// int rsp = commonService.phoneCodeLogin(phone, code);
		if (null == ru) {
			// return JsonResponseUtil.okWithStatusAndMesage(rsp,
			// AppConstants.PhoneLogin.getLoginStatus(rsp));
			return "r:" + ru;
		} else {
			return JsonResponseUtil.okWithStatusAndMesage(rsp,
					AppConstants.PhoneLogin.getLoginStatus(rsp));
		}
	}

	/**
	 * phone code login choose
	 * 
	 * @throws MalformedURLException
	 */
	@Get("")
	public String lrIndex(Invocation inv,
			@Param("cityName") @DefValue("bj") String cityName,@Param("ru") String ru,@Param("data") String data)
			throws MalformedURLException {
		DictCity city = cityService.getCityLocatedFromCookie(inv);
		if(null==city) {
			city = cityService.getDefaultCity();
		}
		//User user  = new User();
		inv.addModel("_city", city);
		// 返回标志 0表示返回历史记录 1 表示返回首页
		int returnFlag = Utils.getBackStatus(inv.getRequest());
		inv.addModel("returnFlag", returnFlag);
		
		String redirUrl = ru;
		if (StringUtils.isBlank(ru)) {
		    redirUrl = inv.getRequest().getHeader("Referer");
		}
		
		if (null == redirUrl || redirUrl.contains("/login")) {
			redirUrl = "http://" + inv.getRequest().getServerName();
		}
		int index = redirUrl.indexOf("?");
		if(index!=-1) {
			redirUrl = redirUrl.substring(0, index);
		}		
		User user = UserUtil.getUser(inv);
		if (null != user) {
			return "r:" + redirUrl;
		}
		if (redirUrl.contains("?")) {
			inv.addModel("fromQQ", redirUrl + "&loginType=qq");
			inv.addModel("fromSina", redirUrl + "&loginType=sina");
			inv.addModel("from", redirUrl);
		} else {
			inv.addModel("fromQQ", redirUrl + "?loginType=qq");
			inv.addModel("fromSina", redirUrl + "?loginType=sina");
			inv.addModel("from", redirUrl);
		}

		return "phone/login";
	}

	@Get("shoujihao")
	public String getPhoneLoginPage(Invocation inv,
			@Param("cityName") @DefValue("bj") String cityName,
			@Param("ru") String ru) throws MalformedURLException {
		// 返回标志 0表示返回历史记录 1 表示返回首页
		int returnFlag = Utils.getBackStatus(inv.getRequest());
		inv.addModel("returnFlag", returnFlag);
		String redirUrl = "http://" + inv.getRequest().getServerName();
		User user = UserUtil.getUser(inv);
		if (null != user) {
			return "r:" + redirUrl;
		}
		if (null == ru) {
			ru = "http://" + inv.getRequest().getServerName();
		}
		if (ru.contains("?")) {
			inv.addModel("fromQQ", ru + "&loginType=qq");
			inv.addModel("fromSina", ru + "&loginType=sina");
		} else {
			inv.addModel("fromQQ", ru + "?loginType=qq");
			inv.addModel("fromSina", ru + "?loginType=sina");
		}

		DictCity city = cityService.getCityByNameOrPinYin(cityName);
		inv.addModel("_city", city);
		// String whichDevice = DeviceUtils.device(inv, device);
		return "phone" + "/phonelogin";
	}

	@Get("yanzhengma")
	public String getCodePage(Invocation inv,
			@Param("cityName") @DefValue("bj") String cityName,@Param("ru") String ru)
			throws MalformedURLException {
		// 返回标志 0表示返回历史记录 1 表示返回首页
		int returnFlag = Utils.getBackStatus(inv.getRequest());
		inv.addModel("returnFlag", returnFlag);
		String redirUrl = "http://" + inv.getRequest().getServerName();
		User user = UserUtil.getUser(inv);
		if (null != user) {
			return "r:" + redirUrl;
		}
		if (null == ru) {
			ru = "http://" + inv.getRequest().getServerName();
		}
		if (ru.contains("?")) {
			inv.addModel("fromQQ", ru + "&loginType=qq");
			inv.addModel("fromSina", ru + "&loginType=sina");
		} else {
			inv.addModel("fromQQ", ru + "?loginType=qq");
			inv.addModel("fromSina", ru + "?loginType=sina");
		}
		
		DictCity city = cityService.getCityByNameOrPinYin(cityName);
		inv.addModel("_city", city);
		// String whichDevice = DeviceUtils.device(inv, device);
		return "phone" + "/phonecode";
	}

	@Get("logout")
	public String logout(Invocation inv) {
		String redirUrl = inv.getRequest().getHeader("Referer");
		if (null == redirUrl) {
			redirUrl = "http://" + inv.getRequest().getServerName();
		}
		loginService.logOut(inv);
		return "r:" + redirUrl;
	}

}
