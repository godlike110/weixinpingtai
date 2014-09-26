package cn.focus.dc.focuswap.controllers;

import static cn.focus.dc.focuswap.config.AppConstants.HASNEXT;
import static cn.focus.dc.focuswap.config.AppConstants.PAGE_NO;
import static cn.focus.dc.focuswap.config.AppConstants.PAGE_SIZE;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import net.paoding.rose.web.portal.Portal;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.config.AppConstants.Xinwen;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.EsNewsDecorate;
import cn.focus.dc.focuswap.model.EsNewsExt;
import cn.focus.dc.focuswap.model.Information;
import cn.focus.dc.focuswap.service.BuildingProposeService;
import cn.focus.dc.focuswap.service.BuildingXinWenService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.NewsService;
import cn.focus.dc.focuswap.service.ShareService;
import cn.focus.dc.focuswap.service.XinWenSearchService.SearchType;
import cn.focus.dc.focuswap.utils.DateUtils;
import cn.focus.dc.focuswap.utils.JsonResponseUtil;
import cn.focus.dc.focuswap.utils.Utils;
import cn.focus.dc.news.model.es.EsNews;

import com.alibaba.fastjson.JSONObject;

/**
 * 新版房产新闻
 * @author rogantian
 * @date 2014-4-24
 * @email rogantianwz@gmail.com
 */

@Path("{cityName:[a-zA-Z]{2,}}/zixun")
@AccessLogRequired
@LoginRequired
public class BuildingXinWenController {
    
    private static Collection<String> urls = new HashSet<String>();
    
    static{
        urls.add("http://imgs.focus.cn/thumb/120/news/10384/a_103834583.jpg");
        urls.add("http://imgs.focus.cn/thumb/120/news/7795/a_77942931.jpg");
        urls.add("http://imgs.focus.cn/thumb/120/news/10159/a_101580479.jpg");
        urls.add("http://imgs.focus.cn/thumb/120/news/10439/b_104386860.jpg");       
    }
    
    
    private static final Integer pageSize = 12;
    
    private static Log logger = LogFactory.getLog(BuildingXinWenController.class);

    @Autowired
    private NewsService newsService;
    
    @Autowired
    private BuildingXinWenService buildingXinWenService; 
    
    @Autowired
    private CityService cityService; 
    
    @Autowired
    private ShareService shareService; 
    
    
    @Autowired
    private BuildingProposeService buildingProposeService;
    
    /**
     * 要闻列表
     * @param device
     * @param cityName
     * @param inv
     * @return
     * @throws UnsupportedEncodingException
     */
    @Get("/")
    public String getYaoWen(@Param("cityName") @DefValue("bj") String cityName, Invocation inv) throws UnsupportedEncodingException {
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        Map<String, Object> newsMap = buildingXinWenService.getYaoWen(city.getCityId(),null,0,pageSize);
        assemble(inv, newsMap,city,SearchType.TOP);
        
        return "phone/xinwenList";     
    }
    
    /**
     * 市场列表
     * @param device
     * @param cityName
     * @param inv
     * @return
     * @throws UnsupportedEncodingException
     */
    @Get("shichang/")
    public String getShiChang(@Param("cityName") @DefValue("bj") String cityName, Invocation inv) throws UnsupportedEncodingException {
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        Map<String, Object> newsMap = buildingXinWenService.getYaoWen(city.getCityId(),SearchType.MARKET,0,pageSize);
        assemble(inv, newsMap,city,SearchType.MARKET);
        return "phone/xinwenList";     
    }
    
    /**
     * 本地列表
     * @param device
     * @param cityName
     * @param inv
     * @return
     * @throws UnsupportedEncodingException
     */
    @Get("bendi/")
    public String getBenDi( @Param("cityName") @DefValue("bj") String cityName, Invocation inv) throws UnsupportedEncodingException {
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        Map<String, Object> newsMap = buildingXinWenService.getYaoWen(city.getCityId(),SearchType.LOCAL,0,pageSize);
        assemble(inv, newsMap,city,SearchType.LOCAL);
        return "phone/xinwenList";   
    }
    
    /**
     * 政策列表
     * @param device
     * @param cityName
     * @param inv
     * @return
     * @throws UnsupportedEncodingException
     */
    @Get("zhengce/")
    public String getZhengCe(@Param("cityName") @DefValue("bj") String cityName, Invocation inv) throws UnsupportedEncodingException {
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        Map<String, Object> newsMap = buildingXinWenService.getYaoWen(city.getCityId(),SearchType.POLICY,0,pageSize);
        assemble(inv, newsMap,city,SearchType.POLICY);
        return "phone/xinwenList";
    }
    
    /**
     * 观点列表
     * @param device
     * @param cityName
     * @param inv
     * @return
     * @throws UnsupportedEncodingException
     */
    @Get("guandian/")
    public String getGuanDian(@Param("cityName") @DefValue("bj") String cityName, Invocation inv) throws UnsupportedEncodingException {
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        Map<String, Object> newsMap = buildingXinWenService.getYaoWen(city.getCityId(),SearchType.POINT,0,pageSize);
        assemble(inv, newsMap,city,SearchType.POINT);
        return "phone/xinwenList";
    }
    
    /**
     * 更多列表
     * @param device
     * @param cityName
     * @param inv
     * @return
     * @throws UnsupportedEncodingException
     */
    @Get("more/")
    public String getMore(@Param("cityName") @DefValue("bj") String cityName, Invocation inv) throws UnsupportedEncodingException {
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        Map<String, Object> newsMap = buildingXinWenService.getYaoWen(city.getCityId(),SearchType.MORE,0,pageSize);
        assemble(inv, newsMap,city,SearchType.MORE);
        return "phone/xinwenList";
    }
        
    
    @Get("zixuntest/")
    public String getYaoWenTest() throws UnsupportedEncodingException {
        buildingXinWenService.getYaoWenInCache();
        return null;     
    }
    
    /**
     * 加载更多
     * @param device
     * @param cityName
     * @param type
     * @param pageNo
     * @param pageSize
     * @param fun
     * @return
     */
    @SuppressWarnings("unchecked")
    @Get("listajax/{searchType:[a-zA-Z]*}/")
    public String getMoreAjax(@Param("cityName") @DefValue("bj") String cityName,@Param("searchType") String type,
            @Param(PAGE_NO) @DefValue("1") Integer pageNo,
            @Param(PAGE_SIZE) @DefValue("10") Integer pageSize,
            @Param("callback") String fun){
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        SearchType searchType = SearchType.valueOf(type);
        Map<String, Object> newsMap = buildingXinWenService.getYaoWen(city.getCityId(), searchType, pageNo, pageSize);
        boolean hasNext = false;
        List<EsNews> newslist = new ArrayList<EsNews>();
        if(newsMap != null){
            newslist = (List<EsNews>) newsMap.get("data");             
            int total = (Integer)newsMap.get("total");
            if(pageNo * pageSize < total){
                hasNext = true;
            }
        }
        List<EsNewsDecorate> results = new ArrayList<EsNewsDecorate>();
        for(EsNews news : newslist){                
            results.add(decorateList(news));
        }
        Map<String, Object> conditions = new HashMap<String, Object>();
        conditions.put(HASNEXT, hasNext);
        conditions.put("newsList", results);                
        return JsonResponseUtil.jsonp(conditions, fun);
    }
    
    /**
     * 列表装饰bean
     * @param inv
     * @param newsMap
     * @param city
     * @param type
     * @param device
     */
    @SuppressWarnings("unchecked")
    private void assemble(Invocation inv,Map<String, Object> newsMap,DictCity city,SearchType type){
        if(newsMap != null){
            List<EsNews> newslist = (List<EsNews>) newsMap.get("data"); 
            List<EsNewsDecorate> results = new ArrayList<EsNewsDecorate>();
            int total = (Integer)newsMap.get("total");
            if(total > pageSize){
                inv.addModel("hasNext", true);
            }
            for(EsNews news : newslist){                
                results.add(decorateList(news));
            }
            inv.addModel("newslist", results);
        }
        inv.addModel("_city", city);
        StringBuilder sb = new StringBuilder("/zixun/listajax/");
        sb.append(type.name()).append("/");
        StringBuilder single = new StringBuilder("/zixun/");
        inv.addModel("city",Utils.putAjaxUrl(city, sb,single));
        //判断导购标签是否有值
        int status = buildingProposeService.getProposeStatus(city.getCityId());
        inv.addModel("total", status);
        //page class="current" need
        inv.addModel("type", type.name());
        //tdk
        inv.addModel("chineseName", type.getChineseName());
        //各城市本地显示不同
        String localName = "本地";
        String localNickName = Xinwen.localNameMap.get(city.getCityId());
        if(StringUtils.isNotBlank(localNickName)){
            localName = localNickName;
        }
        inv.addModel("localName", localName);
    }
    
    /**
     * @author kanezheng
     * @url  local.focus.cn:8080/bj/zixun/4002710/
     * @desc 资讯正文页
     * @url 2013-10-24 @下午14:10:33
     * @time 2013-10-24 @下午14:10:33
     * @param inv
     * @return
     * @throws UnsupportedEncodingException 
     * @throws MemcachedException 
     * @throws InterruptedException 
     * @throws TimeoutException 
     * @throws MalformedURLException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    @SuppressWarnings("rawtypes")
    @Get({ "{infoId:[0-9]+}/{pageNo:[0-9]+}/", "{infoId:[0-9]+}/" })
    public String getNews(Invocation inv, Portal portal,
            @Param("cityName") @DefValue("bj") String cityName, @Param("infoId") Integer infoId,
            @Param("pageNo") @DefValue("1") Integer pageNo) throws UnsupportedEncodingException, TimeoutException, InterruptedException, MemcachedException, MalformedURLException, IllegalAccessException, InvocationTargetException {
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        // 为了拦截不再获取第二次
        inv.addModel("_city", city);    
        JSONObject cityJson = (JSONObject)JSONObject.toJSON(city);
        inv.addModel("city", cityJson);
        Information news = newsService.getNews(infoId, city.getCityId(),pageNo,cityName);
        if (news == null) {
            return "e:404";
        }
        decorate(news,false);
        if(news.getPageContent() != null){
            String content = news.getPageContent().getContent();
            String splitChar = "</p>|</P>";
            String endChar = "</p>";
            if(news.getTemplateType() == 1){
                splitChar = "</center>|</CENTER>";
                endChar = "</center>";
            }
            String []pContent = content.split(splitChar);
            String frontContent = "";
            String backContent = "";
            for(int i=0;i<pContent.length;i++){
                if(i < 5){
                    frontContent += pContent[i]+endChar;
                }else{
                    backContent += pContent[i]+endChar;
                }
            }
            inv.addModel("news", news);
            inv.addModel("frontContent", frontContent);
            inv.addModel("backContent", backContent);
        }else{
            logger.error("error! there is not content,please check newsCenterInterface and newsId = " + infoId
                    + "and cityName=" + cityName);
        }
        //获取相关主题
        String topic = "";        
        List<Map> caList = news.getNewsCategoryInfoList();
        if(caList != null && caList.size()>0){        
            Collections.sort(caList,new Comparator<Map>(){  
                @Override  
                public int compare(Map b1, Map b2) {  
                    return ((Integer) b1.get("categoryType")).compareTo((Integer)b2.get("categoryType"));  
                }                        
            });  
            topic = (String)caList.get(0).get("className");
            //获取topic对应的分类
            String categoryClassName = (String)caList.get(0).get("categoryClassName");
            SearchType type = Xinwen.xinwenMap.get(categoryClassName);
            if(type != null){
                inv.addModel("type", type.getTypeName());
            }else{
                inv.addModel("type", SearchType.MORE);
            }
        }
        
        //今日热点
        List<EsNewsExt> yaowen = buildingXinWenService.getYaoWenByIndex(city.getCityId(),false);
        for (Iterator<EsNewsExt> it = yaowen.iterator(); it.hasNext();) {
            EsNews esnews = it.next();
            Long newsid = esnews.getNewsId();
            if(null!=newsid && newsid.intValue() == infoId){
                it.remove();
                break;
            }
          }        
        
        //relate news
        portal.addWindow("infos", "/portal/info/infos?infoId=" + infoId + "&pageNo=" + pageNo + "&cityName=" + cityName);
         
        //share
        shareService.share(inv, news.getTitle(), news.getImgLogo(), 2, news.getNewsId(), cityName);
                
        //获取7日内的楼盘
        portal.addWindow("buildingList", "/portal/info/buildingList?cityName=" + cityName + "&limit=" + 5 + "&days=" + 7);
        
        //摘要去链接
        String zhaiyaoTDK = "";
        if(news.getPageContent() != null){
            zhaiyaoTDK = buildingXinWenService.subPartContent(news.getPageContent().getNewsSummary());
        }
        inv.addModel("zhaiyaoTDK", zhaiyaoTDK);
        inv.addModel("topic", topic);
        inv.addModel("yaowen", yaowen);
        inv.addModel("city",Utils.putAjaxUrl(city, null,null));
        int returnFlag = Utils.getBackStatus(inv.getRequest());
        inv.addModel("returnFlag", returnFlag);
        return "phone/xinwen";
    }  

    /**
     * 详情页的包装
     * @param news
     */
    private void decorate(Information news,boolean needSummaryByCMS){
        if(news != null){
            //来源
            if((news.getIfCopyright() != null && news.getIfCopyright() == 1) || "搜狐焦点网".equals(news.getSourceName())){
                news.setSourceName("焦点原创  "+ (news.getAuthor()==null?"":news.getAuthor()));
            }
            //摘要
            if(needSummaryByCMS && news.getPageContent() !=null && StringUtils.isBlank(news.getPageContent().getNewsSummary())){
                try {
                    news.getPageContent().setNewsSummary(buildingXinWenService.subPartContent(news.getPageContent().getContent()));
                } catch (Exception e) {
                    logger.error("",e);
                }
            }
            try{
                news.setTime(DateUtils.stringPatternThrow(news.getTime(), "yyyy年MM月dd", "yyyy年M月d日"));
            }catch(ParseException e){
                news.setTime(DateUtils.stringPattern(news.getTime(), "yyyy-MM-dd", "yyyy年M月d日"));
            }
        }
    }
    
    /**
     * 列表页的包装
     * @param news
     * @param toSdf
     */
    private EsNewsDecorate decorateList(EsNews news){
        EsNewsDecorate model = new EsNewsDecorate();
        try {
            //来源
            
            if(news.getIfCopyright() == 1 || "搜狐焦点网".equals(news.getSourceName())){
                news.setSourceName("焦点原创  "+ (news.getAuthor()==null?"":news.getAuthor()));
            }
            //showtime
            SimpleDateFormat sdf2 = new SimpleDateFormat("M月d日");
            Date d = new Date(news.getShowTime());
            String showtime = sdf2.format(d);
            
            //picurl
            if(StringUtils.isNotBlank(news.getImgLogo())){               
                //单独删除特殊图片http://imgs.focus.cn/upload/news/10159/a_101580479.jpg
                if(urls.contains(news.getImgLogo())){
                    news.setImgLogo("");
                }else{
                    news.setImgLogo(news.getImgLogo().replace("/120/", "/240/"));
                }
            }
            
            /*if(device != null && !device.isMobile()){
                news.setPartContent(buildingXinWenService.subPartContent(news.getPartContent()));
            }*/
            
            model.setEsNews(news);
            model.setShowTime(showtime);
        } catch (Exception e) {
            logger.error("", e);
        }
        return model;
    }
    
    @Get("getyaowenlist")
    @Post("getyaowenlist")
    public String getYaoWenList(Invocation inv,@Param("cityId") int cityId) throws IllegalAccessException, InvocationTargetException {
        List<EsNews> yws = buildingXinWenService.getYaoWenByCache(cityId);
        
        List<EsNewsDecorate> results = new ArrayList<EsNewsDecorate>();
        for(EsNews news : yws){       
            results.add(decorateList(news));
        }
        return JsonResponseUtil.ok(results);
    }
    
    @Get("getyaoweninfo")
    @Post("getyaoweninfo")
    public String getYaoWenInfo(Invocation inv,@Param("newsId") int newsId,@Param("cityId") int cityId) {
        Information news = newsService.getNews(newsId, cityId, null, null);
        decorate(news,true);
        return JsonResponseUtil.ok(news);
    }
}
