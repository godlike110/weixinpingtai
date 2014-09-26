package cn.focus.dc.focuswap.utils;

import cn.focus.dc.focuswap.model.DictCity;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Utils {
    
    private static String[] servicesPic = {"imgs.focus.cn", "i1.f.itc.cn", "i2.f.itc.cn"};
    
    private static Log logger = LogFactory.getLog(Utils.class);
    
    @SuppressWarnings("unused")
    private static Pattern pattern = Pattern.compile("^([\\s\\S]*?)(<br>)[\\s\\S]*$");

    
    /**
     * 修改价格的显示
     * 0  :  0
     * 10000以下   :  1万以下
     * 50000以上  :  5万以上
     * 10000-15000 : 1-1.5万
     */
    public static String stringPattern(String showName){
        String name = "";
        if(showName.indexOf("首付") != -1){
            return name;
        }
        if(StringUtils.isNotBlank(showName)){
            try{
            if(showName.contains("-")){
                String[] arr = showName.split("-");
                String front = arr[0];
                String back = arr[1];
                int priceFront = Integer.parseInt(front);
                int priceBack = Integer.parseInt(back);
                BigDecimal bd = null;
                BigDecimal bdBack = null;
                if(priceFront % 10000 == 0){
                    bd = new BigDecimal(priceFront / 10000);
                }else{
                    bd = new BigDecimal(priceFront);                
                    bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);  
                    bd = bd.divide(new BigDecimal(10000)); 
                }
                if(priceBack % 10000 == 0){
                    bdBack = new BigDecimal(priceBack / 10000);
                }else{
                    bdBack = new BigDecimal(back);    
                    bdBack = bdBack.setScale(1, BigDecimal.ROUND_HALF_UP);  
                    bdBack = bdBack.divide(new BigDecimal(10000));                 
                }
                //小于一万
                if(bdBack.compareTo(new BigDecimal(1)) == -1){
                    String f = String.format("%.0f",bdBack.multiply(new BigDecimal(10)));
                    String b = String.format("%.0f",bd.multiply(new BigDecimal(10)));
                    name = b + "-" + f + "千";
                }else{
                    name = bd + "-" + bdBack + "万";
                }
            }else{
                String num = "";
                String words = "";
                for(int i = 0;i<showName.length();i++){
                    char c = showName.charAt(i);
                    String s = String.valueOf(c);
                    boolean isNum =  isNumeric(s);
                    if(isNum){
                        num += s;
                    }else{
                        words += s;
                    }
                }
                int price = Integer.parseInt(num);
                BigDecimal p = null;
                if(price % 10000 == 0){
                    p = new BigDecimal(price / 10000);
                }else{
                    p = new BigDecimal(price);    
                    p = p.setScale(1, BigDecimal.ROUND_HALF_UP);  
                    p = p.divide(new BigDecimal(10000));                 
                }
                //小于一万
                if(p.compareTo(new BigDecimal(1)) == -1){
                    String n = String.format("%.0f",p.multiply(new BigDecimal(10)));
                    name = n + "千" + words;
                }else{
                    name = p + "万" + words;
                }
            }              
            }catch(Exception e){
                logger.error("格式不符合",e);
            }
        }
        return name;
    } 
    
    /**
     * 修改价格的Id
     * 0  :  0
     * 10000以下   :  0-10000
     * 50000以上  :  50000-100000
     */
    public static String stringPatternId(String qStr){
        String result = "";
        if(qStr.contains("-") || qStr.equals("0")){
            result = qStr;
        }else{
            String num = "";
            String words = "";
            for(int i = 0;i<qStr.length();i++){
                char c = qStr.charAt(i);
                String s = String.valueOf(c);
                boolean isNum =  isNumeric(s);
                if(isNum){
                    num += s;
                }else{
                    words += s;
                }
            }
            if(words.equals("以上")){
                result = num+"-100000";
            }else{
                result = "0-"+num;
            }
        }
        return result;
    }
    
    public static boolean isNumeric(String str) {
        Pattern patternIn = Pattern.compile("[0-9]*");
        Matcher isNum = patternIn.matcher(str);
        return isNum.matches();
    }
    
    public static String rondomService(){
        /**
         * bug fix by rogantian @ 2013-11-12
         *int index = (int)(Math.random()*2);
         *at least use : Math.random()*3 
         */
        int index = (int)(RandomUtils.nextInt(3));
        return servicesPic[index];
    }
    
    public static String showRoom(Integer roomLowArea,Integer roomMaxArea){
        String roomMax = "";
        String roomLow = "";
        if(roomLowArea != null && roomLowArea != 0) {
            roomLow = roomLowArea.toString();
        }
        if(roomMaxArea != null && roomMaxArea != 0) {
            roomMax = roomMaxArea.toString();
        }
        if(StringUtils.isNotBlank(roomMax) && StringUtils.isNotBlank(roomLow)) {
            if(roomLow.equals(roomMax)){
                return roomMax+"平米";
            }else if(!roomLow.equals(roomMax)){
                return roomLow+"-"+roomMax+"平米";
            }
        }
        return "";
    }
    
    //TOFIX: using Pattern...
    public static String subSaleDateStr(String str) {
        
        if (StringUtils.isBlank(str)) {
            return "";
        }
        int index = StringUtils.indexOf(str, "<br");
        if (index == -1) {
            return str;
        } else {
            return StringUtils.substring(str, 0, index);
        }
        
        /*Matcher m = pattern.matcher(str);
        while (m.find()) {
            return m.group(1);
        }
        return "";*/
    }
    
    /**
     * 
     * @param city
     * @param ajaxurl   
     * @param singleUrl
     * @return
     */
    public static JSONObject putAjaxUrl(DictCity city,StringBuilder ajaxurl,StringBuilder singleUrl){
        JSONObject cityJson = (JSONObject)JSONObject.toJSON(city);
        StringBuilder sb = new StringBuilder("/"+city.getCityPinyinAbbr());
        if(ajaxurl != null){
            ajaxurl = sb.append(ajaxurl);       
            cityJson.put("ajaxUrl", ajaxurl.toString());
        }
        sb = new StringBuilder("/"+city.getCityPinyinAbbr());
        if(singleUrl != null){
        singleUrl = sb.append(singleUrl);
            cityJson.put("singleUrl", singleUrl.toString());
        }
        return cityJson;
    }
    
    /**
     * 动态内容转义
     * @param value
     * @return
     */
    public static String encodeContent(String value) {
        if(StringUtils.isNotBlank(value)){
            value = value.replaceAll("&amp;", "&").replaceAll("&#39;", "'")
            .replaceAll("&quot;", "\"").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        }
        return value;
    }
    
    /**
    * 获取返回url
    * @param request
    * @param cityPinYin
    * @return
    */
    public static String getRefer(HttpServletRequest request,String cityPinYin){
        // 详情页点击返回url
        String backUrl = "";
        String referUrl = request.getHeader("Referer");
        String domain = request.getServerName();
        if (referUrl != null && referUrl.indexOf(domain) != -1) {
            backUrl = referUrl;
        } else {
            backUrl = "/" + cityPinYin + "/";
        }
        return backUrl;
    }
    
    /**
     * 返回标志 0表示返回历史记录 1 表示返回首页
     * @param request
     * @param cityPinYin
     * @return
     * @throws MalformedURLException 
     */
    public static int getBackStatus(HttpServletRequest request) throws MalformedURLException{
        // 详情页点击返回url
    	//返回标志 0表示返回历史记录 1 表示返回首页
    	int returnFlag = 1;
        String referUrl = request.getHeader("Referer");
        if(referUrl==null) {
        	return returnFlag;
        }
        URL url = new URL(referUrl);
        String refServer = url.getHost();
        String domain = request.getServerName();
        if (refServer != null && refServer.equals(domain) && !referUrl.contains("/login/yanzhengma")) {
            returnFlag = 0;
        }
        return returnFlag;
    }
    
    /**
     * 根据url获取servername
     * @param url
     * @return
     */
    public static String getServerName(String url) {
    	if(url==null) {
    		return null;
    	}
    	if(url.indexOf("http://")==0) {
    		url = url.substring(7);
    	} 
    	int a = url.indexOf("/");
    	int b = url.indexOf("?");
	    a = a==-1?1000:a;
		b = b==-1?1000:b;
		int end = a>=b?b:a;
		String server = url.substring(0, end);
		return server;
    }
    
    /**
     * 通过refer判断用户点击哪个条件,展开所选条件
     * @param request
     * @param nowPara
     * @return
     */
    public static String getFold(HttpServletRequest request,String nowPara){
        String returnFlag = "";
        if(StringUtils.isNotBlank(nowPara)){
            String referUrl = request.getHeader("Referer");
            String domain = request.getServerName();
            if (referUrl != null && referUrl.indexOf(domain) != -1) {
                String oldPara = StringUtils.substringAfter(referUrl, "loupan/k");
                if(StringUtils.countMatches(oldPara, "_") == 6){
                    if(StringUtils.isNotBlank(oldPara)){
                        //进入这个判断,说明是从筛选页跳转
                        String []olds = oldPara.split("_");
                        String []nows = nowPara.replaceFirst("k", "").split("_");
                        for(int i=0;i<nows.length;i++){
                            if (nows[i].length() > 0 && !nows[i].equals(olds[i])) {
                                switch (i) {
                                case 0:
                                    returnFlag = "di";
                                    break;
                                case 1:
                                    returnFlag = "ty";
                                    break;
                                case 2:
                                    returnFlag = "pr";
                                    break;
                                case 3:
                                    returnFlag = "ho";
                                    break;
                                case 4:
                                    returnFlag = "su";
                                    break;
                                case 5:
                                    returnFlag = "sp";
                                    break;
                                default:
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return returnFlag;
    }
    
}
