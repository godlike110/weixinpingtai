package cn.focus.dc.focuswap.controllers;

import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.model.BuildingInfo;
import cn.focus.dc.focuswap.model.BuildingLayoutPhotoes;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.service.BuildingService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.InterfaceService;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import net.paoding.rose.web.portal.Portal;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author rogantian
 */
@Path("building")
public class BuildingController {

    private static Log logger = LogFactory.getLog(BuildingController.class);

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private CityService cityService;

    @Autowired
    private InterfaceService interfaceService;

    /**
     * 楼盘详情页
     * 
     * @param inv
     * @param portal
     * @param id
     * @param search
     * @return
     */
    @Get("show/{groupId:[0-9]+}")
    public String getBuildingShow(Invocation inv, Portal portal, @Param("groupId") @DefValue("0") Integer id,
            @Param("search") String search, @Param("backParam") String param) {
        // 楼盘信息
        BuildingInfo buildingInfo = buildingService.getBuildingShowInfo(id);
        if (null == buildingInfo) {
            return "e:404";
        }
        int cityId = buildingInfo.getCityId();
        DictCity city = cityService.getCity(cityId);
        return "r:/" + city.getCityPinyinAbbr() + "/loupan/" + id + "/";       
    }

    /**
     * 地图
     * 
     * @param groupId
     * @param type
     * @param inv
     * @return
     */
    @Get("map/{groupId:[0-9]+}/{type:[0-9]}/")
    public String getBuildingMap(@Param("groupId") @DefValue("0") Integer groupId,
            @Param("type") @DefValue("0") int type, Invocation inv) {

        BuildingInfo buildingDetailInfo = buildingService.getBuildingDetailInfo(groupId);
        if (buildingDetailInfo == null) {
            return "e:404";
        }
        DictCity city = null;
        try {
            city = cityService.getCity(buildingDetailInfo.getCityId());
        } catch (Exception e) {
            logger.error("", e);
        }
        if (0 == type) {
            return "r:/" + city.getCityPinyinAbbr() + "/loupan/" + groupId + "/ditu/";           
        } else {
            return "r:/" + city.getCityPinyinAbbr() + "/loupan/" + groupId + "/zhoubian/";
        }
    }

    /**
     * 楼盘详细信息
     * 
     * @param inv
     * @param id
     * @return
     */
    @Get("detail/{groupId:[0-9]+}")
    public String getBuildingDetail(Invocation inv, @Param("groupId") @DefValue("0") Integer id) {
        BuildingInfo buildingInfo = buildingService.getBuildingDetailInfo(id);
        if (buildingInfo == null) {
            return "e:404";
        }
        DictCity city = null;
        try {
            city = cityService.getCity(buildingInfo.getCityId());
        } catch (Exception e) {
            logger.error("", e);
        }
        return "r:/" + city.getCityPinyinAbbr() + "/loupan/" + id + "/xiangxi/";
    }

    @Get("getfavour")
    @Post("getfavour")
    public String activeRegist(Invocation inv, @Param("cityid") @DefValue("0") int cityId,
            @Param("groupid") int groupId, @Param("username") String name, @Param("phone") String phone,
            @Param("booknews") @DefValue("0") int booknews) {
        return "@" + interfaceService.activeRegist(booknews, name, phone, cityId, groupId);
    }

    /**
     * 户型图
     * 
     * @param inv
     * @param groupId
     * @param typeId
     * @return
     */
    @Get("pic/list/{groupId:[0-9]+}/{typeId:[0-9a-zA-Z]+}")
    public String getBuildingPicList(Invocation inv, @Param("groupId") @DefValue("0") Integer groupId,
            @Param("typeId") @DefValue("0") String typeId) {
        BuildingInfo buildingDetailInfo = buildingService.getBuildingDetailInfo(groupId);
        if (buildingDetailInfo == null) {
            return "e:404";
        }
        DictCity city = null;
        try {
            city = cityService.getCity(buildingDetailInfo.getCityId());
        } catch (Exception e) {
            logger.error("", e);
        }
        return "r:/" + city.getCityPinyinAbbr() + "/loupan/" + groupId + "/huxingtu/" + typeId + "/";
    }

    @Get("piclist/{groupId:[0-9]+}")
    public String getLoupanPicList(Invocation inv, @Param("groupId") int groupId) {
        List<BuildingLayoutPhotoes> photos = buildingService.getBuildingPicWithFormat(groupId,false,120);
        if (null == photos || photos.size() == 0) {
            return "e:404";
        }
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
        inv.addModel("_city", city);
        inv.addModel("data", JSONObject.toJSONString(photos));
        inv.addModel("projName", projName);
        inv.addModel("phone400", phone400);

        // return "buildingpic";
        return "@" + JSONObject.toJSONString(photos);

    }
}
