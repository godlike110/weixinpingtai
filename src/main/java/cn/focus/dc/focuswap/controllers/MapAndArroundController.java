package cn.focus.dc.focuswap.controllers;

import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.config.AppConstants.CityPageStatus;
import cn.focus.dc.focuswap.model.BuildingInfo;
import cn.focus.dc.focuswap.model.City;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.service.BuildingService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.utils.Utils;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;


@Path("{cityName:[a-zA-Z]{2,}}/loupan")
@AccessLogRequired
public class MapAndArroundController {
	
	private static Log logger = LogFactory.getLog(MapAndArroundController.class);

    @Autowired
    private CityService cityService;

    @Autowired
    private BuildingService buildingService;

    private static String lngLatPattern = "^[0-9|\\.|E|e|-]+$";

    /**
     * 位置和周边
     * 
     * @param inv
     * @param cityName
     * @param groupId
     * @param whatfor 需要地图还是周边信息
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws MalformedURLException 
     */
    @Get({ "/{groupId:[0-9]+}/zhoubian/", "/{groupId:[0-9]+}/ditu/" })
    public String mapAndArround(Invocation inv,
            @Param("cityName") @DefValue("bj") String cityName, @Param("groupId") @DefValue("0") int groupId,
            @Param("whatfor") @DefValue("whatfor") String whatfor) throws IllegalAccessException,
            InvocationTargetException, MalformedURLException {
        BuildingInfo buildingDetailInfo = buildingService.getBuildingDetailInfo(groupId);
        if (null != buildingDetailInfo) {
            String longtitude = buildingDetailInfo.getLongitude();
            String latitude = buildingDetailInfo.getLatitude();
            if (null == longtitude || !longtitude.matches(lngLatPattern) || null == latitude
                    || !latitude.matches(lngLatPattern)) {
                return "e:404";
            }
        } else {
            return "e:404";
        }

        int phone400 = 0;
        if (null != buildingDetailInfo) {
            phone400 = buildingDetailInfo.getPhone400();
        }
        inv.addModel("phone400", phone400);

        DictCity city = null;
        try {
            city = cityService.getCity(buildingDetailInfo.getCityId());
        } catch (Exception e) {
            logger.error("", e);
        }
        City cityInfo = new City();
        BeanUtils.copyProperties(cityInfo, city);
        cityInfo.setCityStatus(AppConstants.CityPageStatus.getCityStatus(cityInfo.getCityName()));
        cityInfo.setEsfUrl("http://" + city.getCityPinyinAbbr() + "." + CityPageStatus.getEsfUrl());
        cityInfo.setXinfangUrl("/" + city.getCityPinyinAbbr() + "/search/list/");
        cityInfo.setSuggestUrl("/" + city.getCityPinyinAbbr() + "/search/suggest/");
        inv.addModel("cityStr", JSONObject.toJSONString(cityInfo));
        inv.addModel("_city", cityInfo);
        inv.addModel("groupId",groupId);
        inv.addModel("info", buildingDetailInfo);

        String buildingName = buildingDetailInfo.getProjName();
        if (buildingName.length() >= 5) {
            buildingName = buildingName.substring(0, 4) + "...";
        }
        inv.addModel("buildName", buildingName);
        //返回标志 0表示返回历史记录 1 表示返回首页
        int returnFlag = Utils.getBackStatus(inv.getRequest());
        inv.addModel("returnFlag", returnFlag);
        // phone展示页
        if (inv.getRequestPath().getUri().contains("ditu")) {
            return "phone/map";
        } else {
            return "phone/arround";
        }
    }
	
}
