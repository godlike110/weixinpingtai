package cn.focus.dc.focuswap.controllers.passport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.alibaba.fastjson.JSONObject;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.config.AppConstants.PassportStatus;
import cn.focus.dc.focuswap.model.Test;
import cn.focus.dc.focuswap.service.CaptureService;
import cn.focus.dc.focuswap.service.InterfaceService;
import cn.focus.dc.focuswap.service.IpService;
import cn.focus.dc.focuswap.service.SignService;
import cn.focus.dc.focuswap.utils.JsonResponseUtil;
import cn.focus.dc.focuswap.utils.UserUtil;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

/**
 * 注册
 * 
 * @author zhiweiwen
 * 
 */
@Path("user/sign/")
public class SignController {

	private static Log logger = LogFactory.getLog(SignController.class);

	@Autowired
	private CaptureService captureService;

	@Autowired
	private SignService signService;

	@Autowired
	private IpService ipService;
	
	@Autowired
	private InterfaceService interfaceService;

	// 账号检查 和 图片验证码检查通过 、发送成功等积极状态
	private static final int CHECK_SUCCESS = 0;

	/**
	 * 添加账户
	 * 
	 * @param inv
	 * @param account
	 * @param password
	 * @param vericode
	 *            图片验证码
	 * @param uuid
	 *            唯一标识
	 * @param type
	 * @param code
	 *            手机验证码 或 邮箱验证码
	 * @return
	 */
	@Get("add")
	@Post("add")
	public String addAccount(Invocation inv, @Param("account") String account,
			@Param("password") String password,
			@Param("vericode") String vericode, @Param("uuid") String uuid,
			@Param("type") int type, @Param("code") String code) {

		String ip = ipService.getIpAddr(inv.getRequest());
		String userId = account;
		int mailStatus = 2;
		
		if(null == account || (type <0 || type > 2)) {
			return "@err request!";
		}

		// 没有code 可以判断是检查账号和发送验证码
		if (null == code) {
			if (type == PassportStatus.TYPE_PHONE) {
				if(!signService.checkPhoneFormat(account)) {
					return JsonResponseUtil.okWithStatusAndMesage(
							PassportStatus.PHONE_FORMAT_ERR,
							PassportStatus.PHONE_FORMAT_ERR_STR);
				}
				userId = account + "@sohu.com";
			} else {
				//是不是合法的邮箱域名
				mailStatus = signService.checkEmailDomain(userId);
				if(mailStatus == PassportStatus.INNER_UNSPPORT) {
					return JsonResponseUtil
							.okWithStatusAndMesage(
									PassportStatus.INNER_EMAIL_UPSPPORT,
									PassportStatus
											.getRegistStatus(PassportStatus.INNER_EMAIL_UPSPPORT));
				}
			}
			// 检查账号是否已经被注册
			if (signService.checkUser(userId)) {
				return JsonResponseUtil.okWithStatusAndMesage(
						PassportStatus.USERID_EXIST,
						PassportStatus.USERID_EXIST_STR);
			}

			if (type == PassportStatus.TYPE_PHONE) {
				// 检查手机是否已经被绑定
				if (signService.checkbind(account)) {
					return JsonResponseUtil.okWithStatusAndMesage(
							PassportStatus.PHONE_DISABLE,
							PassportStatus.PHONE_DISABLE_STR);
				}
			} else {
				// 检查邮件格式
				if(!signService.checkEmailFormat(account)) {
					return JsonResponseUtil.okWithStatusAndMesage(
							PassportStatus.EMAIL_FORMAT_ERR,
							PassportStatus.EMAIL_FORMAT_ERR_STR);
				}
			}
			
			//密码最短长度是6
			if(StringUtils.isNotBlank(password) && ( password.length()<6 || password.length()>16 || password.getBytes().length !=password.length())) {
				return JsonResponseUtil.okWithStatusAndMesage(
						PassportStatus.PASSWORD_FORMAT_ERR,
						PassportStatus.PASSWORD_FORMAT_ERR_STR);
			}

			if(vericode!=null && StringUtils.isNotBlank(vericode)) {
			boolean checkVerify = captureService.checkCapture(uuid, vericode);
			if (!checkVerify) {
				return JsonResponseUtil.okWithStatusAndMesage(
						PassportStatus.CAPTURE_CHECK_FAILED,
						PassportStatus.CAPTURE_CHECK_FAILED_STR);
			}
			}

			if (type == PassportStatus.TYPE_EMAIL) {
				// 邮箱域检查
				switch (mailStatus) {
				case PassportStatus.INNER_SUPPORT_EMAIL:
					int signFlag = signService.sign(inv,account, password, code,
							ip, type);
					if (signFlag == 0) {
						return JsonResponseUtil
								.okWithStatusAndMesage(
										PassportStatus.INNER_REGIST_SUCC,
										PassportStatus
												.getRegistStatus(PassportStatus.INNER_REGIST_SUCC));
					} else {
						return JsonResponseUtil
								.okWithStatusAndMesage(
										PassportStatus.INNER_REGIST_FAILED,
										PassportStatus
												.getRegistStatus(PassportStatus.INNER_REGIST_FAILED));
					} 
				case PassportStatus.INNER_UNSPPORT:
					return JsonResponseUtil
							.okWithStatusAndMesage(
									PassportStatus.INNER_EMAIL_UPSPPORT,
									PassportStatus
											.getRegistStatus(PassportStatus.INNER_EMAIL_UPSPPORT));
				case PassportStatus.OUTER_EMAIL:
					int status = signService.sendCode(account, type);
					return JsonResponseUtil
							.okWithStatusAndMesage(
									PassportStatus.HAS_SENDCODE,
									PassportStatus
											.getRegistStatus(PassportStatus.HAS_SENDCODE) + ":" + status);
				}
			} else {
				int status = signService.sendCode(account, type);
				return JsonResponseUtil.okWithStatusAndMesage(
						PassportStatus.HAS_SENDCODE, PassportStatus
								.getRegistStatus(PassportStatus.HAS_SENDCODE)+ ":" + status);
			}
		} else {
			//code存在 表示已经发送过验证码 直接注册即可
			int status = signService.sign(inv,account, password, code, ip, type);
			return JsonResponseUtil.okWithStatusAndMesage(status,
					PassportStatus.getRegistStatus(status));
		}
		return null;

	}

	/**
	 * 注册账号检查
	 * 
	 * @param inv
	 * @param account
	 * @param password
	 * @param vericode
	 * @param uuid
	 * @param type
	 * @return
	 */
	@Get("checkacc")
	public String checkAcc(Invocation inv, @Param("account") String account,
			@Param("vericode") String vericode, @Param("uuid") String uuid,
			@Param("type") int type) {
		String userId = account;
		if (type == PassportStatus.TYPE_PHONE) {
			if(!signService.checkPhoneFormat(account)) {
				
			}
			userId = account + "@sohu.com";
		}
		// 检查账号是否已经被注册
		if (signService.checkUser(userId)) {
			return JsonResponseUtil.okWithStatusAndMesage(
					PassportStatus.USERID_EXIST,
					PassportStatus.USERID_EXIST_STR);
		}

		if (type == PassportStatus.TYPE_PHONE) {
			// 检查手机是否已经被绑定
			if (signService.checkbind(account)) {
				return JsonResponseUtil.okWithStatusAndMesage(
						PassportStatus.PHONE_DISABLE,
						PassportStatus.PHONE_DISABLE_STR);
			}
		}

		return JsonResponseUtil.okWithStatusAndMesage(CHECK_SUCCESS, "账户检查通过");
	}

	/**
	 * 注册页面
	 * 
	 * @param inv
	 * @param url
	 * @return
	 */
	@Get("")
	public String index(Invocation inv, @Param("url") String url) {

		String uuid = UserUtil.getUUID();
		inv.addModel("uuid", uuid);

		return "sign/index";

	}

	/**
	 * 发送邮箱或手机验证码
	 * 
	 * @param inv
	 * @param account
	 * @param type
	 *            0 手机 1 邮箱
	 * @return
	 */
	@Post("sendcode")
	@Get("sendcode")
	public String sendCode(Invocation inv, @Param("account") String account,
			@Param("type") int type) {
		int result = signService.sendCode(account, type);
		String method = inv.getRequest().getMethod();
		return JsonResponseUtil.okWithStatusAndMesage(result,
					PassportStatus.getSendStatus((result)));
	}
	
	@Get("paramTest")
	@Post("paramTest")
	public String getBean(Test test) {
		return "@ " + test.getId() + " " +  test.getName();
	}
	
	@Get("postString")
	public String get() {
		//multivalue解决问题
    	MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
    	params.add("id", 123);
    	params.add("name", "zhiweiwen");   
    	params.add("name", "zhiweiwen1");   
		
		Test test = new Test();
		test.setId(123);
		test.setName("zhiweiwen");
    	//原始的map 有问题
//    	Map<String, Object> param = new HashMap<String, Object>();
//    	param.put("id", test.getId());
//    	param.put("name", test.getName());
    	//System.out.println(JSONObject.toJSONString(param));
    	System.out.println(JSONObject.toJSONString(params));
    	String result = interfaceService.postObjectForString("http://test.m.focus.cn/user/sign/paramTest/", params);
    	return "@"  + result;
	}

}
