package cn.focus.dc.focuswap.service;

import static cn.focus.dc.focuswap.config.AppConstants.GONGYI_ORDER_ID_REDIS;

import java.net.URLEncoder;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.focus.dc.focuswap.config.YiBaoConfig;
import cn.focus.dc.focuswap.controllers.GonyiController;
import cn.focus.dc.focuswap.utils.IpUtil;
import cn.focus.dc.focuswap.utils.encrypt.AES;
import cn.focus.dc.focuswap.utils.encrypt.EncryUtil;
import cn.focus.dc.focuswap.utils.encrypt.RSA;

import com.alibaba.fastjson.JSON;
import com.sohu.sce.repackaged.net.rubyeye.xmemcached.utils.ByteUtils;

/**
 * 公益合作项目相关 代码参考易宝提供的demo
 * 
 * @author zihaoli
 * @date 2014-07-22
 */
@Service
public class GongyiService {

    private static Log logger = LogFactory.getLog(GonyiController.class);

    @Autowired
    private YiBaoConfig yiBaoConfig;

    @Autowired
    private RedisHandlerService redisHandlerService;

    /**
     * 用于支付结果的验签、解密
     */
    public String checkPayResult(String data, String encryptkey) {

        // 验签
        try {
            boolean passSign = EncryUtil.checkDecryptAndSign(data, encryptkey, yiBaoConfig.getYibaoPublicKey(),
                    yiBaoConfig.getMerchantPrivateKey());
            if (passSign) {
                // 验签通过
                String aeskey = RSA.decrypt(encryptkey, yiBaoConfig.getMerchantPrivateKey());
                System.out.println("易宝支付返回结果中使用的aeskey为：" + aeskey);
                String payresult = AES.decryptFromBase64(data, aeskey);
                return payresult;
            } else {
                logger.error("验签不通过");
            }
        } catch (Exception e) {
            logger.error("验签解密过程失败");
        }
        return "failed";
    }

    /**
     * 完成生成订单并向易宝发出请求的过程
     * 
     * @author zihaoli
     * @throws Exception 加密失败会抛出异常
     */
    public String doMobilePay(HttpServletRequest request, Double pay_amount) throws Exception {
        String orderid, identityid;
        // 用户输入的是以“元”为单位的，易宝、我们数据库使用的是以“分”为单位的
        int amount = (int) (pay_amount * 100);
        long incredGonyiId = redisHandlerService.incr(ByteUtils.getBytes(GONGYI_ORDER_ID_REDIS));
        long transtime = System.currentTimeMillis();
        
        orderid = new StringBuilder(String.valueOf(transtime)).append("_").append(incredGonyiId).toString();
        identityid = new StringBuilder("user").append("_").append(orderid).toString();

        // 将此笔订单写入数据库
        //gongyiDAO.save(orderid, amount);

        /*
         * 按照易宝要求的参数顺序存放好，以便加解密操作 注释掉的部分是可选参数
         * @author zihaoli
         */
        TreeMap<String, Object> map = new TreeMap<String, Object>();
        map.put("merchantaccount", yiBaoConfig.getMerchantaccount());
        map.put("amount", amount);
        // map.put("currency", currency);
        map.put("identityid", identityid);
        map.put("identitytype", yiBaoConfig.getIdentitytype());
        map.put("orderid", orderid);
        map.put("productcatalog", yiBaoConfig.getProductcatalog());
        // map.put("productdesc", productdesc);
        // e.g. 搜狐焦点公益-捐款
        map.put("productname", yiBaoConfig.getProductnamePrefix());
        // 精确到秒
        map.put("transtime", transtime/1000L);
        // 用户网络终端IP
        String userIp = IpUtil.getIpAddr(request);
        if (StringUtils.isBlank(userIp)) {
            userIp = request.getRemoteAddr();
        }
        map.put("userip", userIp);
        // map.put("terminaltype", terminaltype);
        // map.put("terminalid", terminalid);
        map.put("callbackurl", yiBaoConfig.getCallbackurl());
        map.put("fcallbackurl", yiBaoConfig.getCallbackurl());
        map.put("userua", request.getHeader("User-Agent"));
        // map.put("paytypes", "1|2");

        // 生成RSA签名
        String sign = EncryUtil.handleRSA(map, yiBaoConfig.getMerchantPrivateKey());

        map.put("sign", sign);

        // 生成data
        String plainText = JSON.toJSONString(map);
        logger.debug("公益业务数据明文：" + plainText);
        // 随机生成AESkey
        String merchantAESKey = getRandom(16);

        // cipher text
        String data = AES.encryptToBase64(plainText, merchantAESKey);

        // 使用RSA算法将商户自己随机生成的AESkey加密
        String encryptkey = RSA.encrypt(merchantAESKey, yiBaoConfig.getYibaoPublicKey());

        return yiBaoConfig.getMobilePayUrl() + "?" + "merchantaccount="
                + URLEncoder.encode(yiBaoConfig.getMerchantaccount(), "UTF-8") + "&data="
                + URLEncoder.encode(data, "UTF-8") + "&encryptkey=" + URLEncoder.encode(encryptkey, "UTF-8");
    }

    /**
     * 易宝提供的一个生成随机大小写字母或数字组成的AESKey的方法
     * 
     * @param Key的长度
     */
    private String getRandom(int length) {
        Random random = new Random();
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < length; i++) {
            boolean isChar = (random.nextInt(2) % 2 == 0);// 输出字母还是数字
            if (isChar) { // 字符串
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
                ret.append((char) (choice + random.nextInt(26)));
            } else { // 数字
                ret.append(Integer.toString(random.nextInt(10)));
            }
        }
        return ret.toString();
    }

}