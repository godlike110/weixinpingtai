package cn.focus.dc.focuswap.controllers;

import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.utils.CookieUtil;

import org.apache.commons.lang.StringUtils;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

/**
 * 将设置到.m.focus.cn域的用户"focus_wap_city"cookie复制一份出来，然后将其设置到.focus.cn域
 * 
 * 为了使品格能够获取到新房wap站的城市cookie而有的该Interceptor，由于wap站新房的城市cookie有效期为90天，所以此Intercepter的有效期只有90天</br>
 * 在这个Interceptor中将.m.focus.cn域下的城市选择cookie拿出来设置到.focus.cn域（品格可以访问到该域的cookie）,使得以前设置了新房城市cookie的用户</br>
 * 在品格的页面中也能获取到正确的城市。在90天后，该Interceptor仅对新用户起作用（即调用/city/locate接口）。</br>
 * 
 * 此举可以不用将品格相关代码侵入到现有的新房wap代码中去
 * 
 * @author rogantian
 * @date 2014-7-31
 * @email rogantianwz@gmail.com
 */
public class CookieTempProcessInterceptor extends ControllerInterceptorAdapter {

    @Override
    protected Object after(Invocation inv, Object instruction) throws Exception {
        //获取城市的cookie
        String cityIdInCookie = CookieUtil.getCookieValueByName(inv.getRequest(), CityService.CITY_SELECTED_COOKIE_KEY);
        
        if (StringUtils.isNotBlank(cityIdInCookie)) {
            String oldPGCookie = CookieUtil.getCookieValueByName(inv.getRequest(), CityService.CITY_SELECT_COOKIE_KEY_AT_PINGE);
            if (!StringUtils.equals(cityIdInCookie, oldPGCookie)) {
                //将城市选择的cookie种到.focus.cn域上
                CookieUtil.addCommonCookie(inv.getRequest(), inv.getResponse(), CityService.CITY_SELECT_COOKIE_KEY_AT_PINGE, 
                        cityIdInCookie, ".focus.cn", CityService.CITY_SELECTED_COOKIE_EXPIRE, "/");
            }
        }
        return super.after(inv, instruction);
    }

    
}
