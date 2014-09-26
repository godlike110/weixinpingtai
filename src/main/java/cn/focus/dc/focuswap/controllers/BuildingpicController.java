package cn.focus.dc.focuswap.controllers;

import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.config.AppConstants.CityPageStatus;
import cn.focus.dc.focuswap.config.AppConstants.Layout;
import cn.focus.dc.focuswap.model.BuildingInfo;
import cn.focus.dc.focuswap.model.BuildingLayoutPhotoes;
import cn.focus.dc.focuswap.model.City;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.ProjPhotoesExt;
import cn.focus.dc.focuswap.service.BuildingService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.utils.Utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;


@Path("{cityName:[a-zA-Z]{2,}}/loupan")
@AccessLogRequired
public class BuildingpicController {
	
	private static Log logger = LogFactory.getLog(BuildingpicController.class);


    @Autowired
    private BuildingService buildingService;
    
    @Autowired
    private CityService cityService;


    /**
     * 楼盘图片
     * 
     * @param inv
     * @param cityName
     * @param groupId
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws MalformedURLException 
     */
    @Get("/{groupId:[0-9]+}/tupian/{typeId:-[0-9a-zA-Z]+}/")
    public String buildingPic(Invocation inv,
            @Param("cityName") @DefValue("bj") String cityName, @Param("groupId") @DefValue("0") int groupId,
            @Param("typeId") int typeId) throws IllegalAccessException,
            InvocationTargetException, MalformedURLException {

        boolean needThumb = false;
        // if("phone".endsWith(whichDevice)) {
        // needThumb = true;
        // }
        List<BuildingLayoutPhotoes> photos = buildingService
                .getBuildingPicWithFormat(groupId, needThumb, 120);
        if (null == photos || photos.size() == 0) {
            return "e:404";
        }
        photos = decraHousePhotos(photos);
        String projName = StringUtils.EMPTY;
        int phone400 = 0;
        DictCity city = null;
        BuildingInfo info = buildingService.getBuildingShowInfo(groupId);
        if (null != info) {
            projName = info.getProjName();
            phone400 = info.getPhone400();
            try {
                city = cityService.getCity(info.getCityId());
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        City cityInfo = new City();
        BeanUtils.copyProperties(cityInfo, city);
        cityInfo.setCityStatus(AppConstants.CityPageStatus.getCityStatus(cityInfo.getCityName()));
        cityInfo.setEsfUrl("http://" + city.getCityPinyinAbbr() + "." + CityPageStatus.getEsfUrl());
        cityInfo.setXinfangUrl("/" + city.getCityPinyinAbbr() + "/search/");
        cityInfo.setSuggestUrl("/" + city.getCityPinyinAbbr() + "/search/suggest/");
        inv.addModel("cityStr", JSONObject.toJSONString(cityInfo));
        inv.addModel("groupId",groupId);
        inv.addModel("_city", cityInfo);
        inv.addModel("data", photos);
        inv.addModel("dataStr", getJsonString(photos));
        inv.addModel("deftype", photos.get(0).getType());
        inv.addModel("projName", projName);
        inv.addModel("phone400", phone400);
        inv.addModel("pageinfo", "lptp");
        inv.addModel("info", info);
        String buildingName = info.getProjName();
        if (buildingName.length() >= 5) {
            buildingName = buildingName.substring(0, 4) + "...";
        }
                //返回标志 0表示返回历史记录 1 表示返回首页
        int returnFlag = Utils.getBackStatus(inv.getRequest());
        inv.addModel("returnFlag", returnFlag);
        inv.addModel("buildName", buildingName);
        // return "buildingpic";
        return "phone/housepic";
    }
    
    /**
     * 修改楼盘图片数据
     * 
     * @param photosList
     * @return
     */
    private List<BuildingLayoutPhotoes> decraHousePhotos(List<BuildingLayoutPhotoes> photosList) {
        for (BuildingLayoutPhotoes photos : photosList) {
            photos.setMinMaxArea(Layout.getLayoutName(photos.getType().toString()));
        }
        return photosList;
    }
	
    
    /**
     * 简化图片json
     * 
     * @param photoList
     * @return
     */
    private String getJsonString(List<BuildingLayoutPhotoes> photoList) {

        JSONArray array = new JSONArray();
        for (BuildingLayoutPhotoes photos : photoList) {
            JSONObject ob = new JSONObject();
            ob.put("count", photos.getCount());
            ob.put("minMaxArea", photos.getMinMaxArea());
            ob.put("type", photos.getType());
            JSONArray photoArray = new JSONArray();
            for (ProjPhotoesExt photo : photos.getPhotoes()) {
                JSONObject photoOb = new JSONObject();
                photoOb.put("realPath", photo.getRealPath());
                photoOb.put("title", photo.getTitle());
                photoOb.put("typeClassId", photo.getTypeClassId());
                photoOb.put("buildArea", photo.getBuildArea());
                photoOb.put("photoWidth", photo.getPhotoWidth());
                photoOb.put("photoHeight", photo.getPhotoHeight());
                photoArray.add(photoOb);
                ob.put("photoes", photoArray);
            }
            array.add(ob);
        }
        return array.toJSONString();
    }
}
