package cn.focus.dc.test;

import junit.framework.Assert;
import cn.focus.dc.focuswap.service.RedisHandlerService;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试redisPool
 * @author rogantian
 * @date 2014-5-26
 * @email rogantianwz@gmail.com
 */
@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/applicationContext-redis.xml"})
@ActiveProfiles("product")
public class RedisServiceTest {
    
    private String testKey = "wap-test";

    @Autowired
    private RedisHandlerService redisHandler;
    
    @Test
    @Ignore
    public void test() {
        redisHandler.set(testKey, "ttwwzz", 0);
        String rsp = redisHandler.get(testKey);
        Assert.assertEquals("ttwwzz", rsp);
    }
    
    @Test
    @Ignore
    public void test2() {
        redisHandler.set(testKey.getBytes(), "aaaaabbb".getBytes(), 0);
        String rsp = new String(redisHandler.get(testKey.getBytes()));
        Assert.assertEquals("aaaaabbb", rsp);
    }
    
    @Test
    public void test3() {
        redisHandler.set(testKey, "5", 0);
        Assert.assertEquals(Long.valueOf(6L), redisHandler.incr(testKey));
    }
    
}
