/**
 *
 */
package cn.focus.dc.focuswap.controllers;

import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.News;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.NewsService;
import cn.focus.dc.focuswap.utils.JsonResponseUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.springframework.beans.factory.annotation.Autowired;

import static cn.focus.dc.focuswap.config.AppConstants.*;


/**
 * @author yunguangwang
 * @2013-8-27 @下午5:02:26
 */
@Path("channel")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CityService cityService;
    /**
     * 首屏新闻列表
     * 
     * @param inv
     * @param channelId
     * @param pageSize
     * @param pageNo
     * @return
     */
    @Get("newslist")
    public String getNewsList(Invocation inv, @Param(CHANNEL_ID) Integer channelId,
            @Param(PAGE_SIZE) @DefValue("10") Integer pageSize, @Param(PAGE_NO) @DefValue("1") Integer pageNo) {

        // List<News> newslist = newsService.getNewsList(pageNo);
        //
        // if (newslist == null || newslist.size() == 0) {
        // return "r:/404";
        // }
        //
        // inv.addModel(CHANNEL_ID, channelId);
        // inv.addModel(PAGE_NO, pageNo);
        // inv.addModel(PAGE_SIZE, pageSize);
        // inv.addModel("newslist", newslist);
        // inv.addModel("target", "news");
        //
        // return "zixunnewslist";
        DictCity city = cityService.getCityLocatedInfo(inv);
        return "r:/"+city.getCityPinyinAbbr()+"/xinwen/";
    }

    /**
     * 新闻列表页，ajax请求分页
     */
    @Get("newslistajax")
    public String getNewsListAjax(Invocation inv, @Param(CHANNEL_ID) Integer channelId,
            @Param(PAGE_SIZE) @DefValue("10") Integer pageSize, 
            @Param(PAGE_NO) @DefValue("1") Integer pageNo,@Param("fun") String fun) {

        List<News> newslist = newsService.getNewsList(pageNo);

        Map<String, Object> conditions = new HashMap<String, Object>();
        conditions.put(CHANNEL_ID, channelId);
        conditions.put(PAGE_NO, pageNo);
        conditions.put(PAGE_SIZE, pageSize);

        return JsonResponseUtil.okSupportJSONPWithPaginate(newslist, fun, conditions);
    }

    @Get("news")
    public String getNews(Invocation inv, @Param("itemId") Integer itemId, @Param(CHANNEL_ID) Integer channelId,
            @Param(PAGE_SIZE) @DefValue("10") Integer pageSize, @Param(PAGE_NO) @DefValue("1") Integer pageNo) {

        // News news = newsService.getNews(itemId, channelId, pageNo, pageSize);
        // if (news == null) {
        // return "r:/404";
        // }
        // if(DEFAULT_PIC_URL.equals(news.getPicUrl())) {
        // news.setPicUrl(DEFAULT_DETAIL_PIC_URL);
        // }
        //
        //
        // inv.addModel("news", news);
        // return "zixunnews";
        DictCity city = cityService.getCityLocatedInfo(inv);
        return "r:/"+city.getCityPinyinAbbr()+"/xinwen/"+itemId+"/";
    }
    

}
