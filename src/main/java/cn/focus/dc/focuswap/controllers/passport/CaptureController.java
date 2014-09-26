package cn.focus.dc.focuswap.controllers.passport;


import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;

import cn.focus.dc.commons.service.CommonsService;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.service.CaptureService;
import cn.focus.dc.focuswap.service.IpService;
import cn.focus.dc.focuswap.service.LoginService;
import cn.focus.dc.focuswap.service.PassportProxyService;
import cn.focus.dc.focuswap.service.SignService;
import cn.focus.dc.focuswap.utils.JsonResponseUtil;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("user/capture")
public class CaptureController {
	
	@Autowired
	private CaptureService captureService;
	
	@Autowired
	private SignService signService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private PassportProxyService passportProxyService;
	
	@Autowired
	private IpService ipService;
	
	@Autowired
	private CommonsService commonService;

	/**
	 * 获取验证码
	 * @param inv
	 */
	@Get("get")
	public void getCapture(Invocation inv,@Param("uuid") String uuid) {
      
		captureService.getCapture(inv,uuid);
		
	}
	
	/**
	 * 校验图片验证码
	 * @param inv
	 * @param answer
	 * @return
	 */
	@Get("verify")
	@Post("verify")
	public String checkCapture(Invocation inv,@Param("uuid") String uuid,@Param("answer") String answer) {
		
		boolean result = captureService.checkCapture(uuid, answer);
		return JsonResponseUtil.ok(result);
	}
	
	
	/**
	 * 手机登入验证码发送
	 * @param inv
	 * @param phone
	 * @return
	 */
	@Get("phonecode")
	public String sendPhoneLogCode(Invocation inv,@Param("phone") String phone) {
		
		int result;
		String ip = ipService.getIpAddr(inv.getRequest());
		if(signService.checkPhoneFormat(phone)) {
			//result = passportProxyService.getPhoneLoginCode(phone, ip);
			result = commonService.sendPhoneCode(phone);
		} else {
			result = -2;
		}
		return JsonResponseUtil.okWithStatusAndMesage(result, AppConstants.PhoneLogin.getsendStatus(result));
		
	}
	
}
