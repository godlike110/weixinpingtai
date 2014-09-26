package cn.focus.dc.focuswap.interceptors;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


import cn.focus.dc.commons.model.UserBasic;
import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.User;
import cn.focus.dc.focuswap.service.LoginService;
import cn.focus.dc.focuswap.utils.UserUtil;
import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

public class LoginRequiredInterceptor extends ControllerInterceptorAdapter {

	@Autowired
	private LoginService loginService;

	protected Class<? extends Annotation> getRequiredAnnotationClass() {
		return LoginRequired.class;
	}

	@SuppressWarnings("rawtypes")
	public Object before(Invocation inv) throws NumberFormatException,
			Exception {
		// 获取sohu用户信息
		User user = UserUtil.getUser(inv);
		try {
			if (user != null) {
				int type = UserUtil.getLoginType(inv);
				// 通过sohuuserid获取焦点uid
				Integer focusUid = loginService.getFocusUid(user.getUserId());
				UserBasic ub = null;
				if (null == focusUid) {
					// 没有焦点uid的话注册新用户
					Map uMap = loginService.registFocusUser(type,
							user.getUserId(), inv);
					ub = (UserBasic) uMap.get("userBasic");
				} else {
					// 获取sohu用户在焦点的信息
					ub = loginService.getUserBasicByUid(focusUid);
				}
				if (null != ub) {
				    inv.addModel(AppConstants.FOCUS_USER_NAME, ub.getNickName());
                    inv.addModel(AppConstants.FOCUS_USER, ub);
				} else {
				    throw new Exception("不能查找到用户(sohuUserId=" + user.getUserId() + ")");
				}

			}
		} catch (Exception e) {
			inv.addModel("username", "＠搜狐网友＠");
			inv.addModel("uid", "-1");
		}
		return true;
	}

}
