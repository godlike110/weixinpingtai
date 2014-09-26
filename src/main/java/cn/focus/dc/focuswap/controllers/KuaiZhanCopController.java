package cn.focus.dc.focuswap.controllers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.KuaiZhanCopService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

/**
 * 与快站合作的接口
 * @author rogantian
 * @date 2014-9-19
 * @email rogantianwz@gmail.com
 */
@Path("internal")
public class KuaiZhanCopController {

    @Autowired
    private CityService cityService;
    
    @Autowired 
    private KuaiZhanCopService kzCopService;

    private static Log logger = LogFactory.getLog(KuaiZhanCopController.class);
    
    /**
     * 提供生成直达号页面的基础数据
     * @return
     */
    @Get("basicLouPanData")
    public String getBasicLoupanData(Invocation inv, @Param("cityId") @DefValue("1") int cityId,
            @Param("pageNo") @DefValue("1") int pageNo, 
            @Param("pageSize") @DefValue("10") int pageSize) {
        
        JSONObject ret = new JSONObject();
        ret.put(AppConstants.ERRORCODE, -1);
        
        long begin = System.currentTimeMillis();
        try {
            
            Map<String, Object> objectMap = kzCopService.getBasicLouPanDatasByPage(cityId, pageNo, pageSize);
            if (null != objectMap && objectMap.size() > 0) {
                ret.put(AppConstants.HASNEXT, objectMap.get(AppConstants.HASNEXT));
                ret.put("total", objectMap.get("total"));
                ret.put(AppConstants.DATA, objectMap.get(AppConstants.DATA));
            }
            ret.put(AppConstants.ERRORCODE, 0);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            StringBuilder sb = new StringBuilder("basicLouPanData @ ");
            sb.append(cityId).append(" spend ").append(System.currentTimeMillis() - begin).append(" mils with pageNo=").append(pageNo).append(" pageSize=").append(pageSize);
        
            System.out.println(sb.toString());
        }
        
        return "@" + ret.toJSONString();
    }
    
    /**
     * 获取城市列表的接口
     * @param inv
     * @return
     */
    @Get("cityList")
    public String getCityList(Invocation inv) {
        
        JSONObject ret = new JSONObject();
        ret.put(AppConstants.ERRORCODE, -1);
        
        try {
            List<DictCity> cityList = cityService.getCityList();
            if (null != cityList && cityList.size() > 0) {
                JSONArray ary = new JSONArray();
                for (DictCity city : cityList) {
                    JSONObject jo = new JSONObject();
                    jo.put("cityId", city.getCityId());
                    jo.put("cityAbbr", city.getCityPinyinAbbr());
                    jo.put("cityName", city.getCityName());
                    
                    ary.add(jo);
                }
                ret.put(AppConstants.DATA, ary);
                ret.put(AppConstants.ERRORCODE, 0);
            }
            
        } catch (Exception e) {
            logger.error("", e);
        }
        return "@" + ret.toJSONString();
    }
}
