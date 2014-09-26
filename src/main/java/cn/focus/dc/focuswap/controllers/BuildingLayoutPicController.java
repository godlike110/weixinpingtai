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
public class BuildingLayoutPicController {
	
	private static Log logger = LogFactory.getLog(BuildingLayoutPicController.class);

    @Autowired
    private CityService cityService;

    @Autowired
    private BuildingService buildingService;


    /**
     * 户型图
     * 
     * @param inv
     * @param cityName
     * @param groupId
     * @param type
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws MalformedURLException 
     */
    @Get({ "/{groupId:[0-9]+}/huxingtu/{typeId:[-|0-9a-zA-Z]*}", "/{groupId:[0-9]+}/huxingtu/" })
    public String buildingTypePic(Invocation inv,
            @Param("cityName") @DefValue("bj") String cityName, @Param("groupId") @DefValue("0") int groupId,
            @Param("typeId") @DefValue("11111") String typeId) throws IllegalAccessException,
            InvocationTargetException, MalformedURLException {

        List<BuildingLayoutPhotoes> buildingLayoutPhotoes = buildingService.getBuildingPicInfo(groupId);

        if (null == buildingLayoutPhotoes || buildingLayoutPhotoes.size() == 0) {
            return "e:404";
        }

        boolean thumbed = false;
        boolean needcutTitle = true;
        for (BuildingLayoutPhotoes layout : buildingLayoutPhotoes) {
            buildingService.decorateBuildingPics(layout.getPhotoes(), thumbed, 120, needcutTitle);
        }
        buildingLayoutPhotoes = decraPhotos(buildingLayoutPhotoes);

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
        inv.addModel("_city", city);
        // int i = typeId.charAt(0);
        // //是字母
        // if((i>=65&&i<=90)||(i>=97&&i<=122)) {
        // typeId = buildingLayoutPhotoes.get(0).getType().toString();
        // }
        inv.addModel("projName", projName);
        inv.addModel("phone400", phone400);
        inv.addModel("dataStr", getJsonString(buildingLayoutPhotoes));
        inv.addModel("data", buildingLayoutPhotoes);
        inv.addModel("defaultTypeId", typeId);
        String typeName = AppConstants.Layout.getLayoutName(typeId);
        inv.addModel("typeName", typeName);
        inv.addModel("info", info);
        inv.addModel("pageinfo", "hxt");

        String buildingName = info.getProjName();
        if (buildingName.length() >= 5) {
            buildingName = buildingName.substring(0, 4) + "...";
        }
        //返回标志 0表示返回历史记录 1 表示返回首页
        int returnFlag = Utils.getBackStatus(inv.getRequest());
        inv.addModel("returnFlag", returnFlag);
        inv.addModel("buildName", buildingName);

        return "phone/layoutpic";
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
    
    /**
     * 户型图添加修改数据
     * 
     * @param photosList
     * @return
     */
    private List<BuildingLayoutPhotoes> decraPhotos(List<BuildingLayoutPhotoes> photosList) {
        for (BuildingLayoutPhotoes photos : photosList) {

            List<ProjPhotoesExt> list = photos.getPhotoes();
            float min = list.get(0).getBuildArea() == null ? 0 : list.get(0).getBuildArea();
            float max = list.get(0).getBuildArea() == null ? 0 : list.get(0).getBuildArea();
            for (ProjPhotoesExt photo : list) {
                if (photo.getBuildArea() == null) {
                    photo.setBuildArea((float) 0);
                }
                if (photo.getBuildArea() > max) {
                    max = photo.getBuildArea();
                }
                if (photo.getBuildArea() < min && photo.getBuildArea() > 0) {
                    min = photo.getBuildArea();
                }

                if (min <= 1.0 && photo.getBuildArea() < max) {
                    min = photo.getBuildArea();
                }
                photo.setTypeClassId(decraClassTypeId(photo.getTypeClassId()));
            }

            int a = (int) Math.ceil(min);
            int b = (int) Math.ceil(max);
            if (b == 0) {
                photos.setMinMaxArea(Layout.getLayoutName(photos.getType().toString()));
            } else if (a == b || a == 0) {
                photos.setMinMaxArea(Layout.getLayoutName(photos.getType().toString()) + "  " + b + "平米");
            } else {
                photos.setMinMaxArea(Layout.getLayoutName(photos.getType().toString()) + "  " + a + "--" + b
                        + "平米");
            }
        }
        return photosList;
    }

    private String decraClassTypeId(String typeId) {
        if (typeId == null) {
            return "";
        }
        char[] a = typeId.toCharArray();
        String result = "";
        if (a.length == 3) {
            result = a[0] + "室" + a[1] + "厅" + a[2] + "卫";
        } else if (a.length == 2) {
            result = a[0] + "室" + a[1] + "厅";
        } else if (a.length == 1) {
            if (a[0] == '0') {
                result = "0";
            } else {
                result = a[0] + "室";
            }
        } else {
            result = "0";
        }
        return result;
    }
	
	
}
