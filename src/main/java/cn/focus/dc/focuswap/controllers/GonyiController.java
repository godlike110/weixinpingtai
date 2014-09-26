package cn.focus.dc.focuswap.controllers;

import javax.servlet.http.HttpServletRequest;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.focus.dc.focuswap.service.GongyiService;

@Path("gongyi")
public class GonyiController {
    
    private static final Log logger = LogFactory.getLog(GonyiController.class); 

    @Autowired
    private GongyiService gongyiService;

    @Get("/")
    public String juan(Invocation inv) {
        return "phone/juan";
    }

    /**
     * 至此用户将要跳转到易宝的支付页面 TODO 此后应该定时发起交易记录查询（https://ok.yeepay.com/merchant/query_server/pay_single）以免掉单 订单状态：
     * 0：待付（创建的订单未支付成功） 1：已付（订单已经支付成功） 2：已撤销（待支付订单有效期为1天，过期后自动撤销） 3：阻断交易（订单因为高风险而被阻断）
     * 
     * @author zihaoli
     * @date 2014-07-24
     */
    @Post("juan")
    public String juan(HttpServletRequest request, @Param("pay_amount") double pay_amount) {
        if (pay_amount <= 0) {
            return "r:/gongyi/juan/";
        }
        
        int amountInPenny = (int) (pay_amount * 100);
        if (amountInPenny <= 0) {
            return "r:/gongyi/juan/";
        }
        try {
            String redirectUrl = gongyiService.doMobilePay(request, pay_amount);
            logger.debug("redirectUrl:" + redirectUrl);
            return "r:" + redirectUrl;
//            return "r:/gongyi/juan/";
        } catch (Exception e) {
            // 流程出错则返回捐款页面
            logger.error("捐款提交出错：" + e.getMessage());
            return "r:/gongyi/juan/";
        }
    }

    /**
     * 用户点击“返回商户”访问的URL
     * 
     * @author zihaoli
     * @date 2014-07-24
     */
    @Get("success")
    public String success(Invocation inv, @Param("data") String data, @Param("encryptkey") String encryptkey) {
        // 可以考虑更保险地更新数据库，以免后台回调失败
        // 但是方法需修改
        // String rt = gongyiService.checkPayResult(data, encryptkey);
        // System.out.println("用户点击返回商户，数据：" + rt);
        // 重定向到感谢捐助页面
        return "phone/gongyi_success";
    }

    /**
     * 由易宝调用的在用户支付成功后的回调 解密后的参数中，status为1时表示支付成功
     * 
     * @author zihaoli
     * @date 2014-07-24
     */
    @Post("success")
    public String successInform(Invocation inv, @Param("data") String data, @Param("encryptkey") String encryptkey) {
        //String rt = gongyiService.checkPayResult(data, encryptkey);
        //System.out.println("收到post支付回调：" + rt);
        return "phone/gongyi_success";
    }
    
    @Get("agreement")
    public String agreement(Invocation inv) {
        return "phone/gongyi_agreement";
    }
}
