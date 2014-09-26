package cn.focus.dc.focuswap.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.config.SecretkeyPair;

@SuppressWarnings("deprecation")
public class HttpClientUtil {

	private static HttpClient client;
	
	/**
	 * 普通httpClient调用
	 * @param url
	 * @param params
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static JSONObject getNormalResoponseFromUrl(String url,Map<String,String> params) throws HttpException, IOException {
		
		if(url.contains("?")) {
			int index = url.indexOf("?");
			if(params.size()>0) {
			url = url.substring(0,index+1);
			} else {
				url = url.substring(0,index);
			}
		}
		StringBuffer sb = new StringBuffer(url);
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		if(params!=null&& params.size()>0) {
		Iterator<String> it = params.keySet().iterator();
		while(it.hasNext()) {
			String name = it.next();
			String value = params.get(name);
			sb.append(name).append("=").append(value).append("&");
		}
		}
		String newUrl = sb.toString();
		client = new HttpClient();
		client.getParams().setSoTimeout(10000);
		GetMethod method = new GetMethod(newUrl.substring(0,newUrl.length()-1));
		int status = client.executeMethod(method);	
		if(status==HttpStatus.SC_OK) {
			String rsp = method.getResponseBodyAsString();
			if(StringUtils.isNotBlank(rsp)) {
				return JSONObject.parseObject(rsp);
			} else {
				return null;
			}
		} else {
			return null;
		}
		
		
	}
	
	
	
	
	
	
    private static String genSignParam(String queryStr,String path) {
        StringBuilder sb = new StringBuilder();
        SecretkeyPair s = AppConstants.secretKeyMap.get(path);
        if(s != null){
            if (StringUtils.isNotBlank(s.getSecretKey())) {
                if (StringUtils.isBlank(queryStr)) {
                    sb.append(s.getSecretKey());
                } else {
                    sb.append(StringUtils.chomp(queryStr, "&")).append(s.getSecretKey());
                }
            } else {
                return null;
            }
            if(s.isMd5()){
                return DigestUtils.md5Hex(sb.toString());
            }else{
                return s.getSecretKey();
            }
        }
        return null;
    }
	
	
	
}
