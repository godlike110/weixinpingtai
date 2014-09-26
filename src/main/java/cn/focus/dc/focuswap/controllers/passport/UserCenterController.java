package cn.focus.dc.focuswap.controllers.passport;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mobile.device.Device;

import cn.focus.dc.focuswap.model.User;
import cn.focus.dc.focuswap.model.User1;
import cn.focus.dc.focuswap.utils.DeviceUtils;
import cn.focus.dc.focuswap.utils.JsonResponseUtil;
import cn.focus.dc.focuswap.utils.UserUtil;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;


@Path("/user/")
public class UserCenterController {

	private static Log logger = LogFactory.getLog(UserCenterController.class);
	
	/**
	 * 登入注册中心
	 */
	@Get("lrcenter")
	public String lrIndex(Device device,Invocation inv,@Param("s_m_u") String smu) {
		String uuid = UUID.randomUUID().toString();
		inv.addModel("uuid",uuid);
		String whichDevice = DeviceUtils.device(inv, device);
		String uid = inv.getRequest().getHeader("x-sohu-mobile-userid");
		if(null!=uid && StringUtils.isNotBlank(uid)) {
			inv.addModel("uid", uid);
		}
		return whichDevice+"/login";
	}
	
	@Get("phonelogin") 
	public String getPhoneLoginPage(Device device,Invocation inv) {
		String whichDevice = DeviceUtils.device(inv, device);
		if(whichDevice.equals("pad")) {
			return "@err";
		}
		return whichDevice + "/phonelogin";
	}
	
	@Get("phonecode") 
	public String getCodePage(Device device,Invocation inv) {
		String whichDevice = DeviceUtils.device(inv, device);
		if(whichDevice.equals("pad")) {
			return "@err";
		}
		return whichDevice + "/phonecode";
	}

	/**
	 * 获取用户信息
	 * @param inv
	 * @return
	 */
	@Get("userinfo")
	public String getUserInfo(Invocation inv) {
		
		//String uid = inv.getRequest().getHeader("x-sohu-mobile-userid");
//		String uid = inv.getRequest().getHeader("X-SohuPassport-UId");
//	    String userId = inv.getRequest().getHeader("X-SohuPassport-UserId");
//	    String uuid = inv.getRequest().getHeader("X-SohuPassport-UUId");
//		inv.addModel("uid", uid);
//		logger.info("get info uid: "   + uid + " userId: " + userId + "uuid: " + uuid);
		User user = UserUtil.getUser(inv);
		return JsonResponseUtil.ok(user);
		
	}
	
	/**
	 * 获取ppsmu
	 * @param inv
	 * @return
	 */
	@Get("ppsmu")
	public String getppsmu(Invocation inv) {
		
		String uid = inv.getRequest().getHeader("ppsmu");
		inv.addModel("ppsmu", uid);
		logger.info("get info : "   + uid);
		return "@" + uid;
	}
	
}
