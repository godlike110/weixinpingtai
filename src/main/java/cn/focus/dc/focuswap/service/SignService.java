package cn.focus.dc.focuswap.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.paoding.rose.web.Invocation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.config.AppConstants.PassportStatus;
import cn.focus.dc.focuswap.model.PassportProxyRsp;

/**
 * 注册服务
 * 
 * @author zhiweiwen
 * 
 */
@Service
public class SignService {

	private static Log logger = LogFactory.getLog(SignService.class);

	// 搜狐内域邮箱匹配表达式
	private static String innerRegex = "(@sohu.com)|(@sogou.com)|(@vip.sohu.com)|(@chinaren.com)|(@focus.cn)|(@game.sohu.com)|(@bo.sohu.com)|(@changyou.com)|(@sms.sohu.com)|(@sol.sohu.com)|(@wap.sohu.com)|(@17173.com)";

	// 内域不支持注册邮箱
	private static String unsportInnerRegex = "(@game.sohu.com)|(@bo.sohu.com)|(@changyou.com)|(@sms.sohu.com)|(@sol.sohu.com)|(@wap.sohu.com)|(@17173.com)|(@37wanwan.com)";
	
	// 邮箱格式匹配
	private static String emailFormatRegex = "^([a-zA-Z0-9]+[-_]?[.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[-_]?)*(.[a-zA-Z]+)*.[a-zA-Z]{2,4}$";
	
	// 手机格式匹配
	private static String phoneFormatRegex = "^[1][358][0-9]{9}$";
	
    // 明文
    private static final int NOSECRET = 0;
	
	private static final int SUCCESS = 0;
	private static final int FAILED = 1;
	private static final int SEND_PHONECODE_OVERRUN = 3;
	private static final int SEND_EMAILCODE_OVERRUN = 4;
	//验证码校验 超限 不发送邮件
	private static final int EMAIL_CAPTURE_CHECK_OVERRUN = 5;
	//邮箱激活码错误次数过多 
	private static final int CAPTURECHECK_ERR_OVERRUN = 12;
	private static final int VERIFY_ERR = 3;

	// 注册
	private static final int SIGN_FLAG = 1;
	// 绑定手机
	private static final int BIND_PHONE = 3;
	// 解绑手机
	private static final int UNBIND_PHONE = 4;

	// ip单日注册次数上限
	private static final int PHONE_DAY_REGIST_UP = 20;
	
	//日志注册类别
	private String SING_CATAGOERY = "user.add";
	//request agent 头
	private String HEAD_AGENT = "user-agent";
	
	//用户已经存在
	private final int USER_EXIST = 4;

	@Autowired
	private PassportProxyService passportProxyService;

	@Autowired
	private CacheHandlerService cacheHandlerService;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private LoginService loginService;

	/**
	 * 检查用户账户
	 * 
	 * @param userId
	 * @return true 已经被注册 false 未被使用
	 */
	public boolean checkUser(String userId) {
		boolean flag = true;
		try {
			PassportProxyRsp rsp = passportProxyService.checkUser(userId);
			if (rsp == null) {
				return flag;
			}
			int status = rsp.getStatus();
			if (status == USER_EXIST) {
				flag = true;
			} else {
				flag = false;
			}
			return flag;
		} catch (Exception e) {
			logger.error("", e);
			return flag;
		}

	}

	/**
	 * 检查手机账号绑定情况
	 * 
	 * @param phone
	 * @return true 手机被绑定 false 手机未绑定
	 */
	public boolean checkbind(String phone) {
		boolean flag = true;
		try {
			PassportProxyRsp rsp = passportProxyService.checkBind(phone);
			if (rsp != null
					&& rsp.getStatus() == PassportStatus.PHONE_AVAILABLE) {
				flag = false;
			}
			return flag;
		} catch (Exception e) {
			logger.error(" ", e);
			return flag;
		}
	}

	/**
	 * 向邮箱或者手机发送验证码
	 * 
	 * @param account
	 *            账号
	 * @param type
	 *            0 手机 1 邮箱
	 * @return 0 发送成功 1 发送失败
	 */
	public int sendCode(String account, int type) {
		int flag = 1;
		try {
			PassportProxyRsp rsp = null;
			if (type == PassportStatus.TYPE_EMAIL) {
				rsp = passportProxyService.getEmailCaptcha(account);
				if (rsp != null) {
					switch (rsp.getStatus()) {
					case SUCCESS:
						flag = SUCCESS;
						break;
					case SEND_EMAILCODE_OVERRUN:
						flag = PassportStatus.SEND_OVERRUN;
						break;
					case EMAIL_CAPTURE_CHECK_OVERRUN:
						flag = PassportStatus.EMAIL_CAPTURE_CHECK_OVERRUN;
						break;
					default:
						flag = FAILED;
					}
				}
			} else {
				rsp = passportProxyService.getMobileCaptcha(account, SIGN_FLAG);
				if (rsp != null) {
					switch (rsp.getStatus()) {
					case SUCCESS:
						flag = SUCCESS;
						break;
					case SEND_PHONECODE_OVERRUN:
						flag = PassportStatus.SEND_OVERRUN;
						break;
					default:
						flag = FAILED;
					}
				}
			}

		} catch (Exception e) {
			logger.error("", e);
			return FAILED;
		}
		return flag;

	}

	/**
	 * 注册账号
	 * 
	 * @param userId
	 * @param password
	 * @param code
	 *            手机码 或 邮箱验证码
	 * @param ip
	 *            ip地址
	 * @param type
	 *            0 手机注册 1 邮箱注册
	 * @return 0 注册成功 1 注册失败 3 phone验证码错误 4 mail验证码错误
	 */
	public int sign(Invocation inv,String userId, String password, String code, String ip,
			int type) {

		int flag = 1;
		PassportProxyRsp rsp = null;
		try {
			// 手机注册
			if (type == PassportStatus.TYPE_PHONE) {
				int ipSignCount = getIpPhoneRegistCount(ip);
				if(ipSignCount>=PHONE_DAY_REGIST_UP) {
					flag = PassportStatus.SIGN_OVERRUN;
				} else {
				rsp = passportProxyService.registerFromMobile(userId, password,
						code,ip);
				if(rsp!=null) {
				switch (rsp.getStatus()) {
				case PassportStatus.PHONE_REGIST_SUCCESS:
					flag = SUCCESS;
					//登入注册日志
					logService.sendLrLog(SING_CATAGOERY, userId	, rsp.getuId(), ip, inv.getRequest().getHeader(HEAD_AGENT));
					loginService.login(inv, userId, password, NOSECRET, ip,PassportStatus.NONEED_LOGIN_LIMIT);
					//设置注册次数
					getIpPhoneRegistCount(ip);
					break;
				case PassportStatus.PHONE_REGIST_FAILED:
					flag = FAILED;
					break;
				case PassportStatus.PHONE_VERIFY_WRONG:
					flag = PassportStatus.PHONE_CODE_ERR;
					break;
				default:
					flag = FAILED;
					break;
				}
				}
			}
			} else {
				// 邮箱注册
				rsp = passportProxyService.registerFromEmail(userId, password,
						code, ip,true);
				if(rsp!=null) {
				switch (rsp.getStatus()) {
				case PassportStatus.REGIST_SUCCESS:
					flag = SUCCESS;
					//登入注册日志
					logService.sendLrLog(SING_CATAGOERY, userId	, rsp.getuId(), ip, inv.getRequest().getHeader(HEAD_AGENT));
					loginService.login(inv, userId, password, NOSECRET, ip,PassportStatus.NONEED_LOGIN_LIMIT);
					break;
				case PassportStatus.EMAIL_VERIFY_WRONG:
					flag = PassportStatus.MAIL_CODE_ERR;
					break;
				case PassportStatus.EMAIL_SIGN_OVERRUN:
					flag = PassportStatus.SIGN_OVERRUN;
					break;	
				case CAPTURECHECK_ERR_OVERRUN:
					flag = PassportStatus.EMAIL_CAPTURECHECK_ERR_OVERRUN;
					break;
				default:
					flag = FAILED;
					break;
				}
				}
			}
			return flag;
		} catch (Exception e) {
			logger.error("", e);
			return flag;
		}
	}

	/**
	 * 邮箱内外域的判断
	 * 
	 * @param email
	 * @return 0 内域支持 1 内域不支持 2 外域
	 */
	public int checkEmailDomain(String email) {
		
		email = email.toLowerCase();

		int flag = 2;
		try {
			//内域判断
			Pattern p = Pattern.compile(innerRegex);
			Matcher m = p.matcher(email);
			while (m.find()) {
				flag = 0;
				//内域不支持注册判断
				Pattern sp = Pattern.compile(unsportInnerRegex);
				Matcher sm = sp.matcher(email);
				while(sm.find()) {
					flag = 1;
					break;
				}
			    break;
			}
		} catch (Exception e) {
			logger.error("", e);
			return 0;
		}
		return flag;
	}

	/**
	 * 设置ip注册次数
	 * 
	 * @param ip
	 */
	public void setIpPhoneRegistCount(String ip) {
		String key = AppConstants.WAP_PHONE_REGIST_COUNT_KEY + ip;
		int count = getIpPhoneRegistCount(key);
		try {
			cacheHandlerService.addCache(key,
					AppConstants.WAP_PHONE_REGIST_COUNT_TIME, ++count);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 获取IP 注册次数
	 * 
	 * @param ip
	 * @return
	 */
	public int getIpPhoneRegistCount(String ip) {
		String key = AppConstants.WAP_PHONE_REGIST_COUNT_KEY + ip;
		int value = 0;
		try {
			Integer cValue = cacheHandlerService.getCacheValue(key,
					Integer.class);
			if (cValue == null) {
				value = 0;
			} else {
				value = cValue.intValue();
			}
		} catch (Exception e) {
			logger.error("", e);
			return 0;
		}
		return value;
	}
	
	/**
	 * 检查邮箱格式
	 * @param email
	 * @return true 格式正确 false 格式错误
	 */
	public boolean checkEmailFormat(String email) {
		
		boolean flag = false;
		
		try {
			//格式检查
			Pattern p = Pattern.compile(emailFormatRegex);
			Matcher m = p.matcher(email);
			while (m.find()) {
				flag = true;
			    break;
			}
		} catch (Exception e) {
			logger.error("", e);
			return false;
		}
		return flag;
		
	}
	/**
	 * 检查手机格式
	 * @param email
	 * @return true 格式正确 false 格式错误
	 */
	public boolean checkPhoneFormat(String phone) {
		
		boolean flag = false;
		
		try {
			//格式检查
			Pattern p = Pattern.compile(phoneFormatRegex);
			Matcher m = p.matcher(phone);
			while (m.find()) {
				flag = true;
			    break;
			}
		} catch (Exception e) {
			logger.error("", e);
			return false;
		}
		return flag;
		
	}
	
	/**
	 * 账号种类查询
	 * @param uid
	 * @return o 手机 1 邮箱 2 其他
	 */
	public int getUidCatagory(String uid) {
		int flag = 2;
		try {
			//邮箱格式检查
			Pattern ep = Pattern.compile(emailFormatRegex);
			Matcher em = ep.matcher(uid);
			while (em.find()) {
				flag = 1;
                return flag;
			}
			
			//手机格式检查
			Pattern pp = Pattern.compile(phoneFormatRegex);
			Matcher pm = pp.matcher(uid);
			while (pm.find()) {
				flag = 0;
                return flag;
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return flag;
	}
	

	public static void main(String argv[]) {
		String email = "wzw.153@sohu.com";
		SignService service = new SignService();
		service.checkEmailFormat(email);
//		PassportStatus.getLoginStatus(1);

	}

}
