package cn.focus.dc.focuswap.controllers;

import static cn.focus.dc.focuswap.config.AppConstants.HASNEXT;
import static cn.focus.dc.focuswap.config.AppConstants.PAGE_NO;
import static cn.focus.dc.focuswap.config.AppConstants.PAGE_SIZE;
import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.HousingGuide;
import cn.focus.dc.focuswap.service.BuildingProposeService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.HousingGuideService;
import cn.focus.dc.focuswap.utils.DeviceUtils;
import cn.focus.dc.focuswap.utils.JsonResponseUtil;
import cn.focus.dc.focuswap.utils.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.JSONSerializerMap;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;



import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;

@SuppressWarnings("deprecation")
@Path("{cityName:[a-zA-Z]{2,}}/baodian")
@AccessLogRequired
@LoginRequired
public class BuildingGuideController {
    private  static final int PAGE_SIZE_NUMBER = 10;
    
    @Autowired
    private HousingGuideService housingGuideService;
    
    @Autowired
    private CityService cityService;   
    
    @Autowired
    private BuildingProposeService buildingProposeService;
    /**
     * @desc  首屏宝典列表 暂时获取所有类别的宝典数据
     * @url    m.focus.cn/bj/baodian/
     * @param inv
     * @param device
     * @param cityName
     * @return
     */
    @Get("/")
    public String getHouseGuideList(Invocation inv,@Param("cityName") @DefValue("bj") String cityName) {
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        inv.addModel("_city", city);
        List<HousingGuide> guideList = housingGuideService.getHousingGuideList(
                0, PAGE_SIZE_NUMBER+1);
        inv.addModel("guideList", guideList);
        StringBuilder sb = new StringBuilder("/baodian/listajax/");
        StringBuilder single = new StringBuilder("/baodian/");
        inv.addModel("city",Utils.putAjaxUrl(city, sb,single));
        if(guideList != null && guideList.size() > PAGE_SIZE_NUMBER){
            inv.addModel("hasNext", true);
            guideList.remove(PAGE_SIZE_NUMBER);
        }
        //判断导购标签是否有值
        int status = buildingProposeService.getProposeStatus(city.getCityId());
        inv.addModel("total", status);
//        int returnFlag = Utils.getBackStatus(inv.getRequest());
//        inv.addModel("returnFlag", returnFlag);
        return "phone/baodianList";
    }

    /**
     * @desc  宝典列表，ajax请求分页 暂时获取所有类别的宝典数据
     * @url   m.focus.cn/bj/baodian/listajax/
     * @param inv
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Get("listajax/")
    public String getHouseGuideListAjax(Invocation inv,
            @Param(PAGE_NO) @DefValue("1") int pageNo,
            @Param(PAGE_SIZE) @DefValue("10") int pageSize,@Param("callback") String fun) {

        List<HousingGuide> guideList = housingGuideService.getHousingGuideList(
                (pageNo - 1) * pageSize, pageSize+1);
        Map<String, Object> conditions = new HashMap<String, Object>();
        boolean hasNext = false;
        if (guideList.size() > pageSize) {
            guideList.remove(pageSize);
            hasNext = true;
        }
        
        JSONSerializerMap mapping = new JSONSerializerMap();
        Map<String, String> tmap = new HashMap<String, String>();  
        tmap.put("summary", "summary");  
        tmap.put("catagoryName", "catagoryName");  
        tmap.put("title", "title");  
        tmap.put("id", "id");  
        tmap.put("createTime", "createTime");  
        tmap.put("picUrl", "picUrl");  
        
        mapping.put(HousingGuide.class, new JavaBeanSerializer(HousingGuide.class, tmap));
        JSONSerializer serializer = new JSONSerializer(mapping);
        serializer.write(guideList);
        String jsonString = serializer.toString();
        JSONArray ja = (JSONArray)JSON.parse(jsonString);       
        
        conditions.put(HASNEXT, hasNext);
        conditions.put(PAGE_NO, pageNo);
        conditions.put(PAGE_SIZE, pageSize);
        conditions.put("baodianList", ja);

        return JsonResponseUtil.jsonp(conditions,
                fun);
    }

    /**
     * @desc  分享的宝典详细页
     * @url   m.focus.cn/bj/baodian/506/
     * @param inv
     * @param device
     * @param itemId
     * @param from
     * @return
     * @throws MalformedURLException 
     */
    @Get("{itemId:[0-9]+}/")
    public String getHouseGuide(Invocation inv,
            @Param("itemId") Integer itemId,@Param("cityName") @DefValue("bj") String cityName,
            @Param("from") String from) throws MalformedURLException {
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        JSONObject cityJson = (JSONObject)JSONObject.toJSON(city);
        inv.addModel("city", cityJson);
        HousingGuide housingGuide = housingGuideService.getHousingGuide(itemId);
        //decorate(housingGuide);
        String returnTo = "";
        if (null != housingGuide) {
            inv.addModel("housingGuide", housingGuide);
            //inv.addModel("from", from);
            if("share".equals(from)){
                if(city == null){
                    city = cityService.getCityByPinYinIgnoringStatus(cityName);
                }
                returnTo =  "phone/baodianShare";
            }else{                
                returnTo =  "phone/baodian";            
            }
            inv.addModel("_city", city);
        } else {
            return "e:404";
        }
        int returnFlag = Utils.getBackStatus(inv.getRequest());
        inv.addModel("returnFlag", returnFlag);
        return returnTo;
    }
    
}