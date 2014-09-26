package cn.focus.dc.test;

import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextLoader;

public class RoseTestContextLoader implements ContextLoader {

    @Override
    public String[] processLocations(Class<?> clazz, String... locations) {
        return new String[]{""};
    }
    
    @Override
    public ApplicationContext loadContext(String... locations) throws Exception {
        return ContextUtils.getContext();
    }

}
