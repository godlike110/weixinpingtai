package cn.focus.dc.test;

import cn.focus.webjunit.MockRose;
import cn.focus.webjunit.MockRoseResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ContextUtils {

    private static MockRose mockRose;

    private static WebApplicationContext ctx = null;

    static {
        System.setProperty("spring.profiles.active", "test");
    }
    public static MockRose getMockRose() {
        if (mockRose == null) {
            mockRose = MockRose.getMockRose();
        }
        return mockRose;
    }
    
    public static WebApplicationContext getContext() {
        if (ctx == null) {
            MockRoseResponse r = getMockRose().get("/home");
            
            ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(
                    r.getInvocation().getServletContext());
            /*ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(r.getRequest().getSession().getServletContext());*/
        }
        return ctx;
    }
    
    public static <T> T getBean(Class<T> clz) {
        return getContext().getBean(clz);
    }
}
