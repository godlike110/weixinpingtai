package cn.focus.dc.test;

import cn.focus.webjunit.MockRoseResponse;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 快站合作直达号的接口测试
 * @author rogantian
 * @date 2014-9-19
 * @email rogantianwz@gmail.com
 */
public class KuaiZhanCopTest extends BaseControllerTest {

    @Test
    @Ignore
    public void testCityList() {
        MockRoseResponse rsp = super.getRose().get("/internal/cityList");
        System.out.println(rsp.getReturnPath());
        Assert.assertNotNull(rsp);
    }
    
    @Test
    //@Ignore
    public void testbasicLouPanData() {
        MockRoseResponse rsp = super.getRose().get("/internal/basicLouPanData");
        System.out.println(rsp.getReturnPath());
        Assert.assertNotNull(rsp);
    }
}
