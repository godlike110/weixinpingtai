package cn.focus.dc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import cn.focus.dc.focuswap.model.PassportProxyRsp;
import cn.focus.dc.focuswap.service.PassportProxyService;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/applicationContext-passportProxy.xml"})
public class PassportProxyServiceTest {

    @Autowired
    private PassportProxyService passportProxyService;
    
//    @Test
//    /**
//     * 1.1.5.2
//     * checkUser：用户已经存在
//     */
//    public void checkUserTest1() {
//        PassportProxyRsp rsp = passportProxyService.checkUser("rogantian@sohu.com");
//        assertNotNull(rsp);
//        assertEquals(new Integer(4), rsp.getStatus());
//    }
//    
//    @Test
//    /**
//     * 1.1.5.2
//     * checkUser：用户不存在
//     */
//    public void checkUserTest2() {
//        PassportProxyRsp rsp = passportProxyService.checkUser("xyjkjilsajiojijae@sohu.com");
//        assertNotNull(rsp);
//        assertEquals(new Integer(0), rsp.getStatus());
//    }
//    
//    @Test
//    /**
//     * 1.1.5.2
//     * checkUser：非法用户名
//     */
//    public void checkUserTest3() {
//        PassportProxyRsp rsp = passportProxyService.checkUser("13810879180");
//        assertNotNull(rsp);
//        assertEquals(new Integer(3), rsp.getStatus());
//    }
//    
//    @Test
//    /**
//     * 1.1.5.2
//     * checkUser：非法用户名
//     */
//    public void checkUserTest4() {
//        PassportProxyRsp rsp = passportProxyService.checkUser("13810879189@sohu.com");
//        assertNotNull(rsp);
//        assertEquals(new Integer(4), rsp.getStatus());
//    }
//
//    @Test
//    /**
//     * 1.1.3.6
//     * 查询手机号绑定的账号
//     */
//    public void checkBindTest() {
//        PassportProxyRsp rsp = passportProxyService.checkBind("13810879180");
//        assertNotNull(rsp);
//        assertEquals(new Integer(3), rsp.getStatus());
//    }
//    
//    @Test
//    /**
//     * 1.1.3.6
//     * 查询手机号绑定的账号
//     */
//    public void checkBindTest2() {
//        PassportProxyRsp rsp = passportProxyService.checkBind("13810879189");
//        assertNotNull(rsp);
//        assertEquals(new Integer(0), rsp.getStatus());
//        System.out.println(rsp.getUserId());
//    }
//    
//    @Test
//    /**
//     * 1.1.1.3
//     * emailCode：邮箱验证码
//     */
//    public void emailCode() {
//        PassportProxyRsp rsp =  passportProxyService.getEmailCaptcha("202820448@qq.com");
//        assertNotNull(rsp);
//        assertEquals(new Integer(0), rsp.getStatus());
//    }
//    
//    @Test
//    /**
//     * 1.1.1.1
//     * 内域邮箱注册，用户已注册
//     */
//    public void registerTest1() {
//        PassportProxyRsp rsp = passportProxyService.registerFromEmail("kanezheng123456@sohu.com", "112233", null, "123.125.122.38",true);
//        assertNotNull(rsp);
//        assertEquals(new Integer(4), rsp.getStatus());
//    }
//    
//    @Test
//    /**
//     * 1.1.1.1
//     * 内域邮箱注册，密码不符合要求
//     */
//    public void registerTest2() {
//        PassportProxyRsp rsp = passportProxyService.registerFromEmail("kanezheng123@sohu.com", "12345", null, "123.125.122.38",true);
//        assertNotNull(rsp);
//        assertEquals(new Integer(1), rsp.getStatus());
//    }
//   
//    
//    @Test
//    /**
//     * 1.1.1.1
//     * 外域邮箱注册,只可使用一次，配合emailCode使用
//     */
//    public void registerTest2Only() {
//        PassportProxyRsp rsp = passportProxyService.registerFromEmail("2119843492@qq.com", "112233", "DrNPK", "123.125.122.38",true);
//        assertNotNull(rsp);
//        assertEquals(new Integer(0), rsp.getStatus());
//    }
//    
//    @Test
//    /**
//     * 1.1.1.1
//     * 内域邮箱注册，不能用vip.sohu.com
//     */
//    public void registerTest5() {
//        PassportProxyRsp rsp = passportProxyService.registerFromEmail("kanezheng@vip.sohu.com", "112233",null, "123.125.122.38",true);
//        assertNotNull(rsp);
//        assertEquals(new Integer(11), rsp.getStatus());
//    }
//    
//    @Test
//    /**
//     * 1.1.1.1
//     * 外域邮箱注册验证码错误
//     */
//    public void registerTest3() {
//        PassportProxyRsp rsp = passportProxyService.registerFromEmail("2119843492@qq.com", "112233", "DrNPKd", "123.125.122.38",true);
//        assertNotNull(rsp);
//        assertEquals(new Integer(13), rsp.getStatus());
//    }
//    
//    @Test
//    /**
//     * 1.1.1.1
//     * 内域邮箱注册，用户名不符合
//     */
//    public void registerTest4() {
//        PassportProxyRsp rsp = passportProxyService.registerFromEmail("kanezheng#$^-!#$@sohu.com", "112233", null, "123.125.122.38",true);
//        assertNotNull(rsp);
//        assertEquals(new Integer(3), rsp.getStatus());
//    }
//    
//    
////    @Test
////    /**
////     * 1.1.7.2
////     * 给手机发验证码,只能使用一次
////     */
////    public void getMobileCaptchaOnly() {
////        PassportProxyRsp rsp = passportProxyService.getMobileCaptcha("13810168431", 1);
////        assertNotNull(rsp);
////        assertEquals(new Integer(0), rsp.getStatus());
////    }
//    
//    @Test
//    /**
//     * 1.1.7.2
//     * 给手机发验证码,手机号@sohu.com的账号已经存在
//     */
//    public void getMobileCaptcha1() {
//        PassportProxyRsp rsp = passportProxyService.getMobileCaptcha("13810879189", 1);
//        assertNotNull(rsp);
//        assertEquals(new Integer(4), rsp.getStatus());
//    }
//    
//    @Test
//    /**
//     * 1.1.1.5
//     * 手机号注册,只能使用一次
//     */
//    public void registerMobileTestOnly() {
//        PassportProxyRsp rsp = passportProxyService.registerFromMobile("13810168431", "112233", "4021","123.125.122.38");
//        assertNotNull(rsp);
//        assertEquals(new Integer(0), rsp.getStatus());
//    }
//    
//    @Test
//    /**
//     * 1.1.1.5
//     * 验证码错误或者已经过期
//     */
//    public void registerMobileTest2() {
//        PassportProxyRsp rsp = passportProxyService.registerFromMobile("13810879189", "112233", "8478","123.125.122.38");
//        assertNotNull(rsp);
//        assertEquals(new Integer(8), rsp.getStatus());
//    }
//    
//    
//    
    @Test
    /**
     * 1.1.2.2
     * login：登陆
     */
    @Ignore
    public void login() {
        PassportProxyRsp rsp =  passportProxyService.login("13021027911", "66715", 0);
    //	 PassportProxyRsp rsp =  passportProxyService.login("godlike113@sohu.com", "112233", 0);
        assertEquals(new Integer(0), rsp.getStatus());
        System.out.println("result:"+rsp.toString());
    }
//    
//    @Test
//    /**
//     * 1.1.2.2
//     * login：登陆   密码错误
//     */
//    public void login2() {
//        PassportProxyRsp rsp =  passportProxyService.login("jiafeimao1121@hotmail.com", "222222", 0);
//        assertEquals(new Integer(3), rsp.getStatus());
//    }
//    
//    @Test
//    /**
//     * 1.1.2.2
//     * login：登陆
//     */
//    public void login3() {
//        PassportProxyRsp rsp =  passportProxyService.login("13810879189", "112233", 0);
//        assertEquals(new Integer(0), rsp.getStatus());
//    }
    
    @Test
    /**
     * 1.1.2.2
     * getuser
     */
    @Ignore
    public void getUserByUserId() {
        PassportProxyRsp rsp =  passportProxyService.getUserByUserId("rogantian@sohu.com");
        assertEquals(new Integer(0), rsp.getStatus());
    }
    
    /**
     * 1.1.2.3
     */
    @Test
    @Ignore
    public void sendPhoneSendCode() {
    	int rsp =  passportProxyService.getPhoneLoginCode("15810140570", "");
        assertNotNull(rsp);
    }
    
    
}
