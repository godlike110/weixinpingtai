package cn.focus.dc.focuswap.controllers;

import static cn.focus.dc.focuswap.config.AppConstants.BUILD_ID;
import static cn.focus.dc.focuswap.config.AppConstants.CITY_ID;
import static cn.focus.dc.focuswap.config.AppConstants.GROUP_ID;
import static cn.focus.dc.focuswap.config.AppConstants.PAGE_NO;
import static cn.focus.dc.focuswap.config.AppConstants.PAGE_SIZE;
import cn.focus.dc.building.model.ProjInfo;
import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.service.BuildingService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.utils.JsonResponseUtil;

import com.sohu.sce.repackaged.net.rubyeye.xmemcached.exception.MemcachedException;

import java.util.concurrent.TimeoutException;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.springframework.beans.factory.annotation.Autowired;

@Path("news")
public class RecentNewsController {

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private CityService cityService;
    /**
     * 最新动态列表页
     * 从0页开始计算
     * @param inv
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Get("recentnewslist")
    public String getRecentNewsList(Invocation inv, @Param(PAGE_NO) @DefValue("0") Integer pageNo,
            @Param(PAGE_SIZE) @DefValue("10") Integer pageSize, @Param(BUILD_ID) int buildId,
            @Param(CITY_ID) int cityId, @Param(GROUP_ID) int groupId) throws InterruptedException,
            MemcachedException, TimeoutException {
       
            if (buildId <= 0 || cityId <= 0) {
                return JsonResponseUtil.badResult("输入参数有误");
            }
            if (groupId == 0) {
                ProjInfo proj = buildingService.getBuilding(cityId, buildId);
                groupId = proj.getGroupId();
            }
        DictCity city = cityService.getCity(cityId);
        return "r:/"+city.getCityPinyinAbbr()+"/loupan/"+groupId+"/dongtai/";
    }

    @Get("recentnews/{itemId:[0-9]+}")
    public String getRecentNews(Invocation inv, @Param("itemId") Integer itemId, @Param(BUILD_ID) int buildId,
            @Param(CITY_ID) int cityId, @Param(PAGE_NO) @DefValue("0") Integer pageNo,
            @Param(PAGE_SIZE) @DefValue("10") Integer pageSize)
            throws InterruptedException, MemcachedException, TimeoutException {
        ProjInfo proj = buildingService.getBuilding(cityId, buildId);
        DictCity city = cityService.getCity(cityId);
        return "r:/"+city.getCityPinyinAbbr()+"/loupan/"+proj.getGroupId()+"/dongtai/"+itemId+"/";
    }

}
