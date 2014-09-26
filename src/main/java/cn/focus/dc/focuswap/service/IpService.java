package cn.focus.dc.focuswap.service;


import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.focus.dc.focuswap.config.AppConstants;

import com.sohu.sce.api.model.AppInstance;
import com.sohu.sce.api.model.Credentials;
import com.sohu.sce.api.service.AppService;
import com.sohu.sce.api.service.ServiceFactory;

@Service
public class IpService {

	
	private static Log logger = LogFactory.getLog(IpService.class);
	
	private static final Pattern ipPattern =  Pattern.compile("([0-9]{1,3}\\.){3}[0-9]{1,3}");
	
	@Autowired
	private CacheHandlerService cacheService;
	
	/** 	 
	 * 获得客户端真实IP地址	 
	 * @[author]param[/author] request  
	 * @return 	 
	 */ 
    public String getIpAddr(HttpServletRequest request) {
       if (logger.isDebugEnabled()) {
           logger.debug(new 
                  StringBuffer().append("X-Forwarded-For:").append(
                   request.getHeader("X-Forwarded-For")).append("\tProxy-Client-IP:").append(
                   request.getHeader("Proxy-Client-IP")).append("\t:WL-Proxy-Client-IP:").append( 
                   request.getHeader("WL-Proxy-Client-IP")).append("\t:HTTP_X_FORWARDED_FOR:").append(
                   request.getHeader("HTTP_X_FORWARDED_FOR")).append("\tRemoteAddr:").append(
                   request.getRemoteAddr()).toString());
      }
     String ip = request.getHeader("X-Forwarded-For");
   
     ip = getTrueIp(ip);
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
          ip = request.getHeader("Proxy-Client-IP");
           ip = getTrueIp(ip);
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         ip = request.getHeader("WL-Proxy-Client-IP");
          ip = getTrueIp(ip);
     }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         ip = request.getHeader("HTTP_CLIENT_IP");
        ip = getTrueIp(ip);
      }
     if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
          ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        ip = getTrueIp(ip);
     }
     if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getRemoteAddr();
        ip = getTrueIp(ip);
     }
     if (logger.isDebugEnabled()) {
         logger.debug("parsed IP:" + ip);
     }
	       return ip; 
	}
     
     
    /**
    * 取真实客户端IP，过滤代理IP
    * @[author]param[/author] ip
    * @return
    */
   public static String getTrueIp(String ip){
     if(ip == null || "".equals(ip))return null;
       if(ip.indexOf(",") != -1){
          String[] ipAddr = StringUtils.split(ip, ",");
//         for(int i=0; i<ipAddr.length; i++){
//             if(isValidIP(ipAddr[i].trim()) && !ipAddr[i].trim().startsWith("10.")
//                && !ipAddr[i].trim().startsWith("172.16")){
//                return ipAddr[i].trim();
//              } 
//         }
         for(int i=0; i<ipAddr.length; i++){
             if(isValidIP(ipAddr[i].trim()) && !ipAddr[i].trim().startsWith("172.16")){
                return ipAddr[i].trim();
              } 
         }
      }else{
//          if(isValidIP(ip.trim()) && !ip.trim().startsWith("10.")
//                  && !ip.trim().startsWith("172.16"))
//                return ip.trim();
          if(isValidIP(ip.trim()) && !ip.trim().startsWith("172.16"))
                return ip.trim();
     }
     return null;
  }
   
   private static boolean isValidIP(String ip) {
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         return false;
       }
      return ipPattern.matcher(ip).matches();
  }
   
   /**
    * 上传ip白名单
    * @param ip
    * @return true 在白名单中
    */
   public boolean checkUploadIPWhiteList(String ip) {
	   String whiteList = getUpLoadWhiteIpList();
	   if(whiteList!=null && whiteList.indexOf(ip)!=-1) {
		   return true;
	   }
	   return false;
   }
   
   /**
    * 添加白名单
    * @param ip
    * @return
    */
   public boolean addWhiteListIp(String ip) {
	   String key = AppConstants.UPLOAD_WHITELIST_KEY;
	   String whiteList = null;
	   try {
		   whiteList = cacheService.getCacheValue(key, String.class);
		   if(null==whiteList) {
			   cacheService.addCache(key, 0, ";"+ip);
		   } else {
			   if(whiteList.indexOf(";" + ip)==-1) {
			   
			   whiteList = whiteList  + ";" + ip;
			   }
		   }
		   return true;
	   } catch(Exception e) {
		   logger.error("", e);
		   return false;
	   }
   }
   
   /**
    * 获取上传白名单
    * @return
    */
   public String getUpLoadWhiteIpList() {
	   String key = AppConstants.UPLOAD_WHITELIST_KEY;
	   String whiteList = null;
	   try {
		   whiteList = cacheService.getCacheValue(key, String.class);
		   return whiteList;
	   } catch(Exception e) {
		   logger.error("", e);
		   return whiteList;
	   }
   }
   
   /**
    * 从白名单删除
    * @param ip
    * @return
    */
   public boolean delWhiteListIp(String ip) {
	   String key = AppConstants.UPLOAD_WHITELIST_KEY;
	   String whiteList = null;
	   
	   try {
		   whiteList = cacheService.getCacheValue(key, String.class);
		   if(null==whiteList) {
			   
		   } else {
			   cacheService.setCache(key, 0, whiteList.replaceAll(";" + ip, ""));
		   }
		   return true;
	   } catch(Exception e) {
		   logger.error("", e);
		   return false;
	   }
   }
   
   
   /**
    * 获取实例的IP列表
    * @return
    */
   public List<AppInstance> getEntityList() {
       List<AppInstance> instances = null;
       try {
           Credentials c = new Credentials(AppConstants.SCE_APP_ID, AppConstants.SCE_APP_SECRET);
           AppService service = ServiceFactory.getInstance();
           service.setEndPoint(AppConstants.SCE_API_ENDPOINT);
           instances = service.findInstances(c, Integer.parseInt(AppConstants.SCE_APP_ID));
       } catch (Exception e) {
           logger.error("", e);
       }
       return instances;
   }
   
   
   public static void main(String argv[]) {
	   String ip ="10.1.76.67";
	   String a = getTrueIp(ip);
	   System.out.println(a);
   }
	
}
