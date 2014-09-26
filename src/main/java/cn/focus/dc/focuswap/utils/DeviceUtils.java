package cn.focus.dc.focuswap.utils;

import cn.focus.dc.focuswap.service.CityService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.paoding.rose.web.Invocation;

import org.apache.commons.lang.StringUtils;
import org.springframework.mobile.device.Device;

public final class DeviceUtils {
    
    //页面上手动选择wap版本(pad,phone)时指定的参数名称
    public static String WAP_VERSION_PARAM = "d";
    
    public static String WAP_VERSION_COOKIE_KEY = "focus_wap_app_version";
    
    public enum WapVersion{
        PHONE,//modified by rogantianwz @2014-07-22 将大写改成了小写，为的是方便使用WapVersion.phone.name获取“phone”字符串
        PAD
    }

    private DeviceUtils(){
        
    }
    
    /**
     * 判断应该返回Pad版还是Phone版页面,规则如下：</br>
     * 1. 判断查询字符串中是否指明了返回具体某个版本的页面，如果没有则看第二步</br>
     * 2. 判断cookie中是否有记录用户指定过的页面版本，如果没有则看第三步</br>
     * 3. 根据UA判断</br>
     * @param device 从UA中解析出来的表示用户的设备类型
     * @param request
     * @param response
     * @return
     */
    public static WapVersion confirmWapVersion(Device device, HttpServletRequest request, 
            HttpServletResponse response) {
        String versionValue = null;
        WapVersion wapVersion = null;
        //1.
        if (null != request) {
            versionValue = request.getParameter(WAP_VERSION_PARAM);
            if (StringUtils.isNotBlank(versionValue)
                    && (versionValue.equals(WapVersion.PAD.toString()) || versionValue.equals(WapVersion.PHONE
                            .toString()))) {
                wapVersion = WapVersion.valueOf(versionValue);
                if (null != wapVersion) {
                    CookieUtil.addCookie(request, response, WAP_VERSION_COOKIE_KEY, wapVersion.name(),
                            CityService.CITY_SELECTED_COOKIE_EXPIRE);
                    return wapVersion;
                }
            }
        }
        //2.
        versionValue = CookieUtil.getCookieValueByName(request, WAP_VERSION_COOKIE_KEY);
        if (null != versionValue) {
            wapVersion = WapVersion.valueOf(versionValue);
            return wapVersion;
        }
        
        //3.
        if (null == device || device.isMobile()) {
            return WapVersion.PHONE;
        } else {
            return WapVersion.PAD; 
        }
        
    }
    
    /**
     * 判断应该返回Pad版还是Phone版页面,</br>
     * @param device 从UA中解析出来的表示用户的设备类型
     * @param inv
     * @return
     */
    public static String device(Invocation inv,Device device){
        WapVersion deviceWap = DeviceUtils.confirmWapVersion(device, inv.getRequest(), inv.getResponse());
        String returnDevice = "";
        switch (deviceWap) {
        case PHONE:
            returnDevice = "phone";
            break;
        case PAD:
            returnDevice = "pad";
            break;
        default:
            break;
        }
        return returnDevice;
    }
    
    /**
     * 判断应该返回Pad版还是Phone版页面,jsp使用</br>
     * @param device 从UA中解析出来的表示用户的设备类型
     * @param inv
     * @return
     */
    public static String device(HttpServletRequest request,HttpServletResponse response,Device device){
        WapVersion deviceWap = DeviceUtils.confirmWapVersion(device, request, response);
        String returnDevice = "";
        switch (deviceWap) {
        case PHONE:
            returnDevice = "phone";
            break;
        case PAD:
            returnDevice = "pad";
            break;
        default:
            break;
        }
        return returnDevice;
    }
}
