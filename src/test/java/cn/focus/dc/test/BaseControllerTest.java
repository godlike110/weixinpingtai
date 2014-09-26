package cn.focus.dc.test;

import java.util.HashMap;
import java.util.Map;

import cn.focus.webjunit.MockRose;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = RoseTestContextLoader.class)
public class BaseControllerTest {

    private static MockRose mockRose = ContextUtils.getMockRose();
    
    @BeforeClass
    public static void ini() {
        Logger.getRootLogger().setLevel(Level.ERROR);
    }
    
    protected static MockRose getRose() {
        return mockRose;
    }
    
    protected static Map<String, Object> convertParamsToMap(Object... params) {
        if (params == null || params.length == 0) {
            return new HashMap<String, Object>();
        }

        if (params.length % 2 != 0) {
            throw new RuntimeException("params length must be multiple of 2");
        }
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        for (int i = 0; i < params.length; i += 2) {
            paramsMap.put(params[i].toString(), params[i + 1]);
        }
        return paramsMap;
    }
}
