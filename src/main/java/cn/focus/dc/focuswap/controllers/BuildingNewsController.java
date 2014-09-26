package cn.focus.dc.focuswap.controllers;

import static cn.focus.dc.focuswap.config.AppConstants.DEFAULT_DETAIL_PIC_URL;
import static cn.focus.dc.focuswap.config.AppConstants.DEFAULT_PIC_URL;
import static cn.focus.dc.focuswap.config.AppConstants.HASNEXT;
import static cn.focus.dc.focuswap.config.AppConstants.PAGE_NO;
import static cn.focus.dc.focuswap.config.AppConstants.PAGE_SIZE;
import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.News;
import cn.focus.dc.focuswap.service.BuildingProposeService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.NewsService;
import cn.focus.dc.focuswap.utils.DeviceUtils;
import cn.focus.dc.focuswap.utils.JsonResponseUtil;
import cn.focus.dc.focuswap.utils.Utils;

import com.alibaba.fastjson.JSONObject;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;


@Path("{cityName:[a-zA-Z]{2,}}/xinwen")
@AccessLogRequired
public class BuildingNewsController {
    
    private static Log logger = LogFactory.getLog(BuildingNewsController.class);
    
    @Autowired
    private NewsService newsService;

    @Autowired
    private CityService cityService;
    
    @Autowired
    private BuildingProposeService buildingProposeService;
    /**
     * @desc  首屏新闻列表
     * @time  2013.11.22
     * @url   m.focus.cn/bj/xinwen/
     * @param inv
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Get("/")
    public String getNewsList(Invocation inv,Device device,@Param("cityName") @DefValue("bj") String cityName) {
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        List<News> newslist = newsService.getNewsList(1);
        SimpleDateFormat sdf2 = new SimpleDateFormat("M月d日");
        for(News news : newslist){
            decorate(news,sdf2);
        }
        inv.addModel("_city", city);
        inv.addModel("mobile", device.isMobile());
        inv.addModel("newslist", newslist);
        StringBuilder sb = new StringBuilder("/xinwen/listajax/");
        StringBuilder single = new StringBuilder("/xinwen/");
        inv.addModel("city",Utils.putAjaxUrl(city, sb,single));        
        inv.addModel("hasNext", true);
        //判断导购标签是否有值
        int status = buildingProposeService.getProposeStatus(city.getCityId());
        inv.addModel("total", status);
//        int returnFlag = Utils.getBackStatus(inv.getRequest());
//        inv.addModel("returnFlag", returnFlag);
        return DeviceUtils.device(inv, device) + "/newsList";
    }
    

    /**
     * @url   m.focus.cn/bj/xinwen/listajax
     * @desc  新闻列表页，ajax请求分页
     * @param inv
     * @param pageSize
     * @param pageNo
     * @param fun
     * @return
     */
    @Get("listajax/")
    public String getNewsListAjax(Invocation inv,@Param(PAGE_SIZE) @DefValue("10") Integer pageSize, 
            @Param(PAGE_NO) @DefValue("1") Integer pageNo,@Param("callback") String fun) {
        List<News> newslist = newsService.getNewsList(pageNo);
        SimpleDateFormat sdf2 = new SimpleDateFormat("M月d日");
        for(News news : newslist){
            decorate(news, sdf2);
        }
        Map<String, Object> conditions = new HashMap<String, Object>();
        boolean hasNext = false;
        if(newslist != null && newslist.size() > 0){
            hasNext = true;
        }
        conditions.put(HASNEXT, hasNext);
        conditions.put(HASNEXT, hasNext);
        conditions.put(PAGE_NO, pageNo);
        conditions.put(PAGE_SIZE, pageSize);
        conditions.put("newsList", newslist);
        return JsonResponseUtil.jsonp(conditions, fun);
    }
    
    /**
     * @desc  获取新闻实体
     * @url   m.focus.cn/bj/xinwen/4002710
     * @param inv
     * @param device
     * @param itemId
     * @param pageSize
     * @param pageNo
     * @return
     * @throws MemcachedException 
     * @throws InterruptedException 
     * @throws TimeoutException 
     * @throws MalformedURLException 
     */
    @Get("{itemId:[0-9]+}/")
    public String getNews(Invocation inv,Device device,@Param("cityName") @DefValue("bj") String cityName,
            @Param("itemId") Integer itemId) throws TimeoutException, InterruptedException, MemcachedException,
            MalformedURLException {
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        inv.addModel("_city", city);
        JSONObject cityJson = (JSONObject)JSONObject.toJSON(city);
        inv.addModel("city", cityJson);
        News news = newsService.getNews(itemId);
        if (news == null) {
            return "e:404";
        }
        newsService.filterTagNews(news);
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy年M月d日");
        decorate(news, sdf3);
        if(DEFAULT_PIC_URL.equals(news.getPicUrl())) {
            news.setPicUrl(DEFAULT_DETAIL_PIC_URL);
        }

        inv.addModel("mobile", device.isMobile());
        inv.addModel("news", news);
        int returnFlag = Utils.getBackStatus(inv.getRequest());
        inv.addModel("returnFlag", returnFlag);
        return DeviceUtils.device(inv, device) + "/news";
    }
    
    private static void decorate(News news, SimpleDateFormat toSdf){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date d = sdf.parse(news.getTime());
            news.setTime(toSdf.format(d));
            String desc = news.getDescription();
            if(StringUtils.isNotBlank(desc) && desc.length()>23){
                news.setDescription(desc.substring(0,23)+"...");
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
