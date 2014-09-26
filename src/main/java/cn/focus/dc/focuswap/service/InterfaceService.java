package cn.focus.dc.focuswap.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.config.AppConstants.CityPageStatus;
import cn.focus.dc.focuswap.model.DictCity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 调用外部接口，服务类
 */
@Component
public class InterfaceService {

    private static Log logger = LogFactory.getLog(InterfaceService.class);

    @Resource
    private RestTemplate focusWapTemplate;

    // @Resource
    // private RestTemplate newsCenterTemplate;

    public JSONObject getJSONFromInterface(String url, Map<String, Object> params) {
        JSONObject ret = null;
        try {
            String jsonStr = focusWapTemplate.getForObject(url, String.class, params);
            if (StringUtils.isNotBlank(jsonStr)) {
                /**
                 * fix bug com.alibaba.fastjson.JSONException: unclosed string : \v at
                 * com.alibaba.fastjson.parser.JSONScanner.scanString(JSONScanner.java:738) 田为中 2013-10-22
                 */
                /**
                 * fix back 在楼盘中心修改 by rogantian 2013-10-22 jsonStr = jsonStr.replaceAll("\\\\v", "");
                 */
                ret = JSONObject.parseObject(jsonStr);
            }
        } catch (Exception e) {
            String p = "";
            if (null != params) {
                for (String key : params.keySet()) {
                    p += key + " " + params.get(key) + ",";
                }
            }
            logger.error("error and url = " + url + "and para = " + p, e);
        }
        return ret;
    }

    /*
     * 从接口获取字符串类型结果
     */
    public String getStringFromInterface(String url, Map<String, Object> params) {
        String ret = null;

        try {
            if (null != params) {
                ret = focusWapTemplate.getForObject(url, String.class, params);
            } else {
                ret = focusWapTemplate.getForObject(url, String.class);
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("getStringFromInterface: ").append(url).append(" error, and params is  ");
            if (null != params) {
                for (String key : params.keySet()) {
                    sb.append(key).append(" = ").append(params.get(key));
                }
            }
            logger.error(sb.toString(), e);
        }

        return ret;
    }

    /**
     * post数据到接口，获取字符串结果
     * 
     * @param url
     * @param obj
     * @return
     */
    public String postObjectForString(String url, Object obj) {
        String ret = null;

        try {
            ret = focusWapTemplate.postForObject(url, obj, String.class);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("postObjectForString ");
            sb.append(url).append(" error, and the Object is ").append(obj);
            logger.error(sb.toString(), e);
        }

        return ret;
    }

    /**
     * get数据到接口，获取字符串结果
     * 
     * @param url
     * @param obj
     * @return
     */
    public String getObjectForString(String url, Object obj) {
        String ret = null;

        try {
            ret = focusWapTemplate.getForObject(url, String.class, obj);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("getObjectForString ");
            sb.append(url).append(" error, and the Object is ").append(obj);
            logger.error(sb.toString(), e);
        }

        return ret;
    }

    /**
     * post数据到接口，获取字符串结果
     * 
     * @param url
     * @param obj
     * @param variables
     * @return
     */
    public String postObjectForString(String url, Object obj, Map<String, Object> variables) {
        String ret = null;

        try {
            ret = focusWapTemplate.postForObject(url, obj, String.class, variables);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("postObjectForString ");
            sb.append(url).append(" error, and the Object is ").append(obj);
            logger.error(sb.toString(), e);
        }

        return ret;
    }

    @Deprecated
    public Map<String, Object> getSignedParams(Map<String, Object> params, String secretKey) {
        return params;
    }

    /**
     * 获取二手房suggest数据
     * 
     * @param queryWord
     * @param city
     * @return
     */
    public JSONArray getSuggestJsonFromEsf(String queryWord, DictCity city) {
        String url = "http://" + city.getCityPinyinAbbr() + "." + CityPageStatus.getEsfSuggestUrl();
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", queryWord);
        String result = focusWapTemplate.postForObject(url, null, String.class, params);
        result = result.replaceAll("house_name", "projName").replaceAll("house_id", "groupId");
        JSONArray array = JSONArray.parseArray(result);
        JSONArray newArray = new JSONArray();
        JSONObject object = new JSONObject();
        for (int i = 0; i < array.size(); i++) {
            if (i == 10) {
                break;
            }
            object = array.getJSONObject(i);
            object.put("linkUrl", "http://" + city.getCityPinyinAbbr() + "." + CityPageStatus.getEsfUrl()
                    + "?group_name=" + object.get("projName"));
            newArray.add(object);
        }
        return newArray;
    }

    /**
     * 获取电商活动信息
     * 
     * @param cityId
     * @param groupId
     * @return
     */
    public String getActiveInfo(int cityId, int groupId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("city_id", cityId);
        param.put("group_id", groupId);
        String result = focusWapTemplate.getForObject(AppConstants.ACTIVE_INFO_URL, String.class, param);
        return result;
    }

    /**
     * 电商活动报名
     * 
     * @return
     */
    public String activeRegist(int bookFlag, String name, String phone, int cityId, int groupId) {

        try {
            String activeInfo = getActiveInfo(cityId, groupId);
            int activeId = getActiveIdFromActiveInfo(activeInfo);
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("name", name);
            param.put("city_id", cityId);
            param.put("cellphone", phone);
            param.put("addmember", bookFlag);
            param.put("active_id", activeId);
            String result = focusWapTemplate.getForObject(AppConstants.ACTIVE_REGIST_URL, String.class, param);
            return getRegistResult(result);
        } catch (Exception e) {
            logger.info(e.getMessage());
            JSONObject ob = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("status", 0);
            ob.put("data", data);
            ob.put("errorCode", 0);
            return ob.toString();
        }

    }

    /**
     * 从活动信息中获取活动Id
     * 
     * @param activeInfo
     * @return
     */
    private Integer getActiveIdFromActiveInfo(String activeInfo) {

        JSONObject object = JSONObject.parseObject(activeInfo);
        JSONObject data = object.getJSONArray("data").getJSONObject(0);
        return data.getInteger("actvie_id");
    }

    /**
     * 从报名接口返回数据中获取报名结果
     * 
     * @param result
     * @return
     */
    private String getRegistResult(String interfaceResult) {
        JSONObject result = new JSONObject();
        JSONObject status = new JSONObject();
        try {
            if ("".equals(interfaceResult) || null == interfaceResult) {
                status.put("status", AppConstants.TuanGouStatus.ERR);
                status.put("info", AppConstants.TuanGouStatus.getStatusStr(AppConstants.TuanGouStatus.ERR));
                result.put("data", status);
                result.put("errorCode", 0);
            } else {
                JSONObject object = JSONObject.parseObject(interfaceResult);
                int rstatus = object.getIntValue("errcode");
                status.put("status", rstatus);
                status.put("info", AppConstants.TuanGouStatus.getStatusStr(rstatus));
                result.put("data", status);
                result.put("errorCode", 0);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return result.toString();
    }

    public JSONObject getJSONFromInterfaceByPost(String url, Map<String, Object> params) {
        JSONObject ret = null;
        try {
            String jsonStr = focusWapTemplate.postForObject(url, null, String.class, params);
            if (StringUtils.isNotBlank(jsonStr)) {
                ret = JSONObject.parseObject(jsonStr);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return ret;
    }

    /**
     * 按经纬度获取城市信息
     * 
     * @param lat
     * @param lgn
     * @return
     */
    public JSONObject getCityJsonInterface(String lat, String lgn) {
        JSONObject ret = null;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("location", lat + "," + lgn);
            String jsonStr = focusWapTemplate.getForObject(AppConstants.CITY_LOCATION_URL, String.class, params);
            if (StringUtils.isNotBlank(jsonStr)) {
                ret = JSONObject.parseObject(jsonStr);
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return ret;
    }

    /**
     * 团购报名
     * 
     * @author zihaoli
     */
    public String tuanGouRegister(String name, String cellphone, Integer cityId, Integer activeId) {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("city_id", cityId);
            param.put("cellphone", cellphone);
            param.put("name", name);
            param.put("active_id", activeId);
           // String pstr = "mod=wap&func=dianshang_apply&city_id="+cityId+"&cellphone="+cellphone+"&name="+name+"&active_id="+activeId +"&source=wap";
            
            // sign字段自动生成并附在URL末尾
            String result = focusWapTemplate.getForObject(AppConstants.TUANGOU_REGISTER_URL, String.class, param);
            return getRegistResult(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.out.println("团购报名出错");
            
            JSONObject rt = new JSONObject();
            rt.put("errorCode", 1);
            return rt.toJSONString();
        }
    }
    
    public static void main(String args[]) throws UnsupportedEncodingException {
    	String a = URLDecoder.decode("%E6%88%91%E7%9A%84", "utf8");
    	System.out.println(a);
    }
}
