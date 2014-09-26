package cn.focus.dc.test;

import cn.focus.webjunit.MockRoseResponse;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 搜狐新闻客户端合作项目测试类
 * @author rogantian
 * @date 2014-7-11
 * @email rogantianwz@gmail.com
 */
public class SohuNewsCopTest extends BaseControllerTest {


    /**
     * 异步加载问答列表接口测试
     */
    @Test
    @Ignore
    public void testZixunAjax() {
        MockRoseResponse rsp = super.getRose().get("/sohunews/zixunajax", convertParamsToMap("ct","bj", "pageNo", 5, "pageSize", 10));
        System.out.println(rsp.getReturnPath());
        Assert.assertNotNull(rsp);
    }
    
    @Test
    public void testZixun() {
        MockRoseResponse rsp = super.getRose().get("/sohunews/zixun/", convertParamsToMap("ct", "bj"));
        System.out.println(rsp.getReturnPath());
        Assert.assertNotNull(rsp);
    }
}
