package cn.focus.dc.focuswap.interceptors;

import static cn.focus.dc.focuswap.config.AppConstants.LOG_OPEN;

import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.service.LogStatService;

import java.lang.annotation.Annotation;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author qiaowang
 * @date 2013-12-25 下午12:07:45
 */
public class AccessLogInterceptor extends ControllerInterceptorAdapter {

    @Autowired
    private LogStatService logStatService;
    
    public AccessLogInterceptor() {
        this.setPriority(100);
    }
    
    protected Class<? extends Annotation> getRequiredAnnotationClass() {
        return AccessLogRequired.class;
    }
    
    public Object before(Invocation inv){
        //保存日志
        if(LOG_OPEN){
            logStatService.statAccess(inv);
        }
        return true;
    }
}
