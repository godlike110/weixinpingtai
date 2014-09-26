package cn.focus.dc.focuswap.controllers;

import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.utils.JsonResponseUtil;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.TreeMap;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Path("city")
@AccessLogRequired
public class CityController {

    @Autowired
    private CityService cityService;

    @Get("select")
    public String citySelect(Invocation inv, @Param("debug") @DefValue("false") boolean debug) {
        List<DictCity> cityList = cityService.getCityList();
        DictCity city = cityService.getCityLocatedFromCookie(inv);
        if(null==city) {
            city = cityService.getDefaultCity();
        }
        TreeMap<String,List<DictCity>> cityMap = cityService.getCityListOrderByPinYin();
        inv.addModel("_dict_city", city);
        inv.addModel("cityStr", JSONObject.toJSONString(city));
        inv.addModel("cityList", cityList);
        inv.addModel("order_city",cityMap);
        
        return "phone/citySelectnew";
    }

    @Post("locate")
    public String setCityLocated(Invocation inv,
            @Param("cityName") @DefValue(AppConstants.DEFAULT_SELECT_CITY) String cityName,
            @Param("wc") @DefValue("true") boolean writeCookie) {
        inv.getResponse().setHeader("Cache-Control", "no-cache");
        if (cityName.endsWith("市")) {
            cityName = StringUtils.removeEnd(cityName, "市");
        }
        DictCity dictCity = cityService.getCityByNameOrPinYin(cityName);
        if (null == dictCity) {
            return JsonResponseUtil.badResult("city not found");
        } else {
            if (writeCookie) {
                cityService.setCityLocated(inv, dictCity);
            }
            return JsonResponseUtil.ok(new ImmutableMap.Builder<String, Object>()
                    .put("cityLocated", dictCity).build());
        }
    }
    
    /**
     * 坐标定位
     * @param inv
     * @param lat
     * @param lgn
     * @param wc
     * @return
     */
    @Get("colocate")
    @Post("colocate")
    public String setCityPosition(Invocation inv, @Param("lat") String lat, @Param("lgn") String lgn,
            @Param("wc") @DefValue("true") boolean wc) {
        return "@" + cityService.getCityInfoObject(lat, lgn);
    }
     
    
    @Get("clear")
    public String clearCityLocated(Invocation inv) {
        cityService.clearCityLocated(inv);
        return JsonResponseUtil.ok();
    }
    
    @Get("getcity")
    public String getCityInfo(@Param("py") String cityPy) {
    	DictCity dictCity = cityService.getCityByNameOrPinYin(cityPy);
    	return "@" + JSONObject.toJSONString(dictCity);
    }

}
