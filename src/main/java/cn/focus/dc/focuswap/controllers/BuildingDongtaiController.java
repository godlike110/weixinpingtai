package cn.focus.dc.focuswap.controllers;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;

import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.model.BuildingInfo;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.RecentNews;
import cn.focus.dc.focuswap.service.BuildingService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.NewsService;
import cn.focus.dc.focuswap.service.RencentNewsService;
import cn.focus.dc.focuswap.utils.DateUtils;
import cn.focus.dc.focuswap.utils.DeviceUtils;
import cn.focus.dc.focuswap.utils.DeviceUtils.WapVersion;
import cn.focus.dc.focuswap.utils.JsonResponseUtil;
import cn.focus.dc.focuswap.utils.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.sohu.sce.repackaged.net.rubyeye.xmemcached.exception.MemcachedException;

@Path("{cityName:[a-zA-Z]{2,}}/loupan")
@AccessLogRequired
@LoginRequired
public class BuildingDongtaiController {

	
    @Autowired
    private CityService cityService;


    @Autowired
    private BuildingService buildingService;

    @Autowired
    private RencentNewsService rencentNewsService;

    @Autowired
    private NewsService newsService;
    
	
    /**
     * @author kanezheng
     * @desc 动态详情页
     * @url m.focus.cn/bj/loupan/7675/dongtai/2578/
     * @param inv
     * @param itemId
     * @param device
     * @return
     * @throws net.rubyeye.xmemcached.exception.MemcachedException
     * @throws MalformedURLException 
     */
    @Get("{groupId:[0-9]+}/dongtai/{itemId:[0-9]+}/")
    public String getDongTai(Invocation inv, @Param("groupId") Integer groupId,
            @Param("itemId") Integer itemId, @Param("cityName") @DefValue("bj") String cityName,
            @Param("con") String con) throws InterruptedException, MemcachedException, TimeoutException,
            net.rubyeye.xmemcached.exception.MemcachedException, MalformedURLException {
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        RecentNews recentNews = rencentNewsService.getRecentNews(itemId, city.getCityId(), groupId);
        int phone400 = -1;
        String groupName = "";
        if (recentNews == null) {
            return "e:404";
        }
        List<BuildingInfo> pros = new ArrayList<BuildingInfo>();
        // 判断动态是否有关联楼盘
        if (recentNews.getGroup_ids() != null) {
            for (Integer gId : recentNews.getGroup_ids()) {
                BuildingInfo proj = buildingService.getBuilding(gId);
                if(proj != null){
                    // 修改实体里面图片url
                    buildingService.genRealPhotoPath(proj, true);
                    // 打折信息
                    
                    if ("待定".equals(proj.getDiscount().trim()) || "暂无".equals(proj.getDiscount().trim())
                            || "无".equals(proj.getDiscount().trim()) || "0".equals(proj.getDiscount().trim())) {
                        proj.setDiscount("");
                    }
                    pros.add(proj);
                    // 获取第一个楼盘的客服电话
                    // if (phone400 == -1) {
                    // phone400 = proj.getPhone400() == null ? 0 : proj.getPhone400();
                    // }
                    //groupName = proj.getProjName();
                    buildingService.setBuildingPrice(proj);
                }
            }

        }
        // else防止php接口有问题

        BuildingInfo proj = buildingService.getBuilding(groupId);
        if(proj != null){
            //buildingService.genRealPhotoPath(proj, true);
            phone400 = proj.getPhone400();
            groupName = proj.getProjName();
        }
        recentNews.setPros(pros);
        String content = Utils.encodeContent(recentNews.getContent());
        content = newsService.filterTagDongtai(content);
        recentNews.setContent(content);
        try {
            recentNews.setInfotime(DateUtils.stringPatternThrow(recentNews.getInfotime(), "yyyy年MM月dd",
                    "yyyy年M月d日"));
        } catch (ParseException e) {
            recentNews.setInfotime(DateUtils.stringPattern(recentNews.getInfotime(), "yyyy-MM-dd",
                    "yyyy年M月d日"));
        }

        // 为了拦截不再获取第二次
        inv.addModel("_city", city);
        JSONObject cityJson = (JSONObject)JSONObject.toJSON(city);
        inv.addModel("city", cityJson);
        inv.addModel("phone400", phone400);
        inv.addModel("groupName", groupName);
        inv.addModel("group_id", groupId);
        inv.addModel("recentNews", recentNews);
        inv.addModel("con", con);
        int returnFlag = Utils.getBackStatus(inv.getRequest());
        inv.addModel("returnFlag", returnFlag);
        return "phone/dynamic";
    }
	
    /**
     * 楼盘动态ajax
     * @param inv
     * @param dongtaiId
     * @return
     * @throws net.rubyeye.xmemcached.exception.MemcachedException 
     * @throws InterruptedException 
     * @throws TimeoutException 
     */
    @Get("dongtaiajax")
    @Post("dongtaiajax")
    public String getdongtaiAjax(Invocation inv,@Param("dongtaiId") int dongtaiId,@Param("cityName") String cityName, 
            @Param("groupId") Integer groupId,@Param("callback") String callback) throws TimeoutException, InterruptedException, net.rubyeye.xmemcached.exception.MemcachedException {
    	int codestatus=-1;
    	DictCity city = cityService.getCityByNameOrPinYin(cityName);
    	RecentNews recentNews = rencentNewsService.getRecentNews(dongtaiId, city.getCityId(), groupId);
        return JsonResponseUtil.jsonp(recentNews, callback);
    }
    
    @Get("{groupId:[0-9]+}/dongtaiapi/{itemId:[0-9]+}/")
    public String getDongTaiFromInterface(Invocation inv,@Param("groupId") Integer groupId,
            @Param("itemId") Integer itemId, @Param("cityName") @DefValue("bj") String cityName) throws InterruptedException, MemcachedException, TimeoutException,
            net.rubyeye.xmemcached.exception.MemcachedException, MalformedURLException, UnsupportedEncodingException {
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        RecentNews recentNews = rencentNewsService.getRecentNews(itemId, city.getCityId(), groupId);
        JSONObject result = new JSONObject();
        if (recentNews == null) {
            result.put("errorcode", 1);
            result.put("data", "no data");
            return "@"+result;
        }
       
        String content = Utils.encodeContent(recentNews.getContent());
        content = newsService.filterTagDongtai(content);
        content = Base64.encodeBase64String(content.getBytes("utf-8"));
        recentNews.setContent(content);
        try {
            recentNews.setInfotime(DateUtils.stringPatternThrow(recentNews.getInfotime(), "yyyy年MM月dd",
                    "yyyy年M月d日"));
        } catch (ParseException e) {
            recentNews.setInfotime(DateUtils.stringPattern(recentNews.getInfotime(), "yyyy-MM-dd",
                    "yyyy年M月d日"));
        }
        result.put("errorcode", 0);
        result.put("data", recentNews);
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(RecentNews.class, "infoauthor", "infoname",
                "infocontent", "infotime");
        String value = JSON.toJSONString(result, filter);       
        return "@"+value;
    }
}
