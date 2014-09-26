package cn.focus.dc.focuswap.controllers;

import static cn.focus.dc.focuswap.config.AppConstants.HASNEXT;
import static cn.focus.dc.focuswap.config.AppConstants.PAGE_NO;
import static cn.focus.dc.focuswap.config.AppConstants.PAGE_SIZE;

import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.model.BuildingInfo;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.RecentNews;
import cn.focus.dc.focuswap.service.BuildingService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.RencentNewsService;
import cn.focus.dc.focuswap.utils.DateUtils;
import cn.focus.dc.focuswap.utils.JsonResponseUtil;
import cn.focus.dc.focuswap.utils.Utils;

import com.sohu.sce.repackaged.net.rubyeye.xmemcached.exception.MemcachedException;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.springframework.beans.factory.annotation.Autowired;




@Path("{cityName:[a-zA-Z]{2,}}/loupan")
@AccessLogRequired
@LoginRequired
public class BuildingDongtaiListController {
	
    private static final String RECENT_NEWS_LIST = "recentNewsList";

    private static final int PAGE_SIZE_NUMBER = 10;
    
    // 动态新闻截断字数phone
    private static final int TRUNCATION_NUM = 75;

    @Autowired
    private CityService cityService;


    @Autowired
    private BuildingService buildingService;

    @Autowired
    private RencentNewsService rencentNewsService;

    /**
     * @author kanezheng
     * @desc 动态楼盘列表
     * @url m.focus.cn/bj/loupan/7675/dongtai/
     * @param inv
     * @param device
     * @param groupId
     * @return
     * @throws ParseException
     * @throws MalformedURLException 
     * @throws net.rubyeye.xmemcached.exception.MemcachedException
     */
    @SuppressWarnings("unchecked")
    @Get("{groupId:[0-9]+}/dongtai/")
    public String getDongtaiList(@Param("cityName") @DefValue("bj") String cityName,
            Invocation inv, @Param("groupId") Integer groupId, @Param("con") String con)
            throws ParseException, MalformedURLException {
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        BuildingInfo build = buildingService.getBuilding(groupId);
        inv.addModel("_city", city);
        inv.addModel("building", build);
        inv.addModel("con", con);

        List<RecentNews> recentNewsList = null;
        if (build != null) {
            Map<String, Object> mapResult = rencentNewsService.getRecentNewsList(build.getProjId(),
                    build.getCityId(), 0, PAGE_SIZE_NUMBER + 1);
            recentNewsList = (List<RecentNews>) mapResult.get(RECENT_NEWS_LIST);

            recentNewsList = decorate(recentNewsList);
            inv.addModel(RECENT_NEWS_LIST, recentNewsList);
        }
        StringBuilder sb = new StringBuilder("/loupan/").append(groupId.toString()).append("/dongtaiajax/");
        StringBuilder single = new StringBuilder("/loupan/").append(groupId.toString()).append("/dongtai/");
        ;
        inv.addModel("city", Utils.putAjaxUrl(city, sb, single));
        if (recentNewsList != null && recentNewsList.size() > PAGE_SIZE_NUMBER) {
            inv.addModel("hasNext", true);
            recentNewsList.remove(PAGE_SIZE_NUMBER);
        }
        int returnFlag = Utils.getBackStatus(inv.getRequest());
        inv.addModel("returnFlag", returnFlag);
        return "phone/dynamicList";
    }
    
    
    /**
     * @author kanezheng
     * @desc 最新动态列表，ajax请求分页
     * @param inv
     * @param pageNo
     * @param pageSize
     * @param groupId
     * @return
     * @throws net.rubyeye.xmemcached.exception.MemcachedException
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
    @Get("{groupId:[0-9]+}/dongtaiajax/")
    public String getDongTaiListAjax(Invocation inv,
            @Param(PAGE_NO) @DefValue("1") Integer pageNo,
            @Param(PAGE_SIZE) @DefValue("10") Integer pageSize, @Param("groupId") Integer groupId,
            @Param("callback") String fun) throws InterruptedException, MemcachedException, TimeoutException,
            ParseException {
        BuildingInfo build = buildingService.getBuilding(groupId);
        Map<String, Object> conditions = new HashMap<String, Object>();
        List<RecentNews> recentNewsList = null;
        if (build != null) {
            Map<String, Object> mapResult = rencentNewsService.getRecentNewsList(build.getProjId(),
                    build.getCityId(), pageNo - 1, pageSize);
            recentNewsList = (List<RecentNews>) mapResult.get(RECENT_NEWS_LIST);
            recentNewsList = decorate(recentNewsList);
            int total = (Integer) mapResult.get("total");
            // 是否还有下一页的属性 hasNext
            boolean hasNext = (pageNo * pageSize) <= total;
            inv.addModel(RECENT_NEWS_LIST, recentNewsList);
            conditions.put(PAGE_NO, pageNo);
            conditions.put(PAGE_SIZE, pageSize);
            conditions.put(HASNEXT, hasNext);
        }
        return JsonResponseUtil.okSupportJSONPWithPaginate(recentNewsList, fun, conditions);
    }
    
    
    @SuppressWarnings("rawtypes")
    private List decorate(List<RecentNews> recentNewsList) throws ParseException {
        List<RecentNews> list = new ArrayList<RecentNews>();
        if (recentNewsList != null && recentNewsList.size() > 0) {
            for (RecentNews recentNews : recentNewsList) {
                String info = recentNews.getInfocontent();
                if (info != null) {
                    if (info.indexOf("-break-") != -1) {
                        recentNews.setInfocontent(info.replaceAll("-break-", ""));
                    }
                    if (recentNews.getInfocontent().length() > TRUNCATION_NUM) {
                        recentNews.setInfocontent(recentNews.getInfocontent().substring(0, TRUNCATION_NUM)
                                + "...");
                    }
                }
                // 日期
                try {
                    recentNews.setInfotime(DateUtils.stringPatternThrow(recentNews.getInfotime(),
                            "yyyy-MM-dd", "M月d日"));
                } catch (ParseException e) {
                    recentNews.setInfotime(DateUtils.stringPattern(recentNews.getInfotime(), "yyyy年MM月dd日",
                            "M月d日"));
                }

                list.add(recentNews);
            }
        }
        return list;
    }
    
    
    
	
}
