package cn.focus.dc.focuswap.controllers;

import org.apache.commons.lang.StringUtils;

import cn.focus.dc.focuswap.utils.CookieUtil;
import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

/**
 * wo联通推广头的拦截器
 * @author rogantian
 * @date 2014-9-1
 * @email rogantianwz@gmail.com
 */
public class WoChinaUnicomHeaderInterceptor extends ControllerInterceptorAdapter {

    public static String WAP_CHINA_UNICOM_HEADER_COOKIE_NAME = "wap_cuhcn";
    
    @Override
    protected Object before(Invocation inv) throws Exception {
        String v = CookieUtil.getCookieValueByName(inv.getRequest(), WAP_CHINA_UNICOM_HEADER_COOKIE_NAME);
        if (StringUtils.isNotBlank(v)) {
            inv.addModel(WAP_CHINA_UNICOM_HEADER_COOKIE_NAME, true);
        }
        return super.before(inv);
    }

    
}
