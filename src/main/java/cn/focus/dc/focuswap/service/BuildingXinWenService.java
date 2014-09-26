package cn.focus.dc.focuswap.service;

import static cn.focus.dc.focuswap.config.AppConstants.DATA_CENTER_GET_7DAYS_URL;
import static cn.focus.dc.focuswap.config.AppConstants.GROUD_ID_FROM_SEVEN_DAYS;
import static cn.focus.dc.focuswap.config.AppConstants.GROUD_ID_FROM_SEVEN_DAYS_TIME;
import static cn.focus.dc.focuswap.config.AppConstants.INDEX_YAOWEN;
import static cn.focus.dc.focuswap.config.AppConstants.INDEX_YAOWEN_TIME;
import static cn.focus.dc.focuswap.config.AppConstants.NEWS_CENTER_NORMAL_NEWS_DIGEST_URL;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.focus.dc.focuswap.dao.XinWenDAO;
import cn.focus.dc.focuswap.model.BuildingDaoGou;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.EsNewsExt;
import cn.focus.dc.focuswap.model.Information;
import cn.focus.dc.focuswap.service.XinWenSearchService.SearchType;
import cn.focus.dc.focuswap.service.XinWenSearchService.XinWenQueryData;
import cn.focus.dc.focuswap.service.XinWenSearchService.XinWenSearchCondition;
import cn.focus.dc.news.model.es.EsNews;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 新版房产新闻服务类
 * @author rogantian
 * @date 2014-4-24
 * @email rogantianwz@gmail.com
 */
@Service
public class BuildingXinWenService {
    
    @Autowired
    private CacheHandlerService cacheHandler;
    
    @Autowired
    private XinWenSearchService xinWenSearchService;
    
    @Autowired
    private CityService cityService;
    
    @Autowired
    private NewsService newsService;
    
    @Autowired
    private XinWenDAO xinWenDao;
    
    @Autowired
    private InterfaceService interfaceService;
    
    /**
     * 获取要闻,如果有移动要闻,显示移动要闻,如果没有,则按时间倒叙显示新闻
     * @param cityId
     * @return
     */
    public Map<String, Object> getYaoWen(int cityId,XinWenSearchService.SearchType searchType,int pageNo,int pageSize){
        //获取是否有移动要闻
        Map<String, Object> newsMap = null;
        XinWenSearchCondition xc = new XinWenSearchCondition();
        xc.setCityId(cityId);        
        xc.setPageNo(pageNo);
        xc.setPageSize(pageSize);
        //如果是要闻,需要先获取是否有移动要闻
        if(searchType == null || searchType.compareTo(SearchType.TOP) == 0){
            newsMap = getMobileYaoWen(cityId,xc);
        }
        //如果没有移动要闻,则获取最新新闻      
        //newsMap = null;
        if(newsMap == null || (Integer)newsMap.get("total") == 0){
            if(searchType !=null){
                List<XinWenQueryData> queryDatas = new ArrayList<XinWenQueryData>();
                queryDatas.add(new XinWenQueryData(searchType));
                xc.setQueryDatas(queryDatas);            
            }
            xc.setIsMobileNews(0);
            newsMap = xinWenSearchService.xinWenSearch(xc);      
        }        
        return newsMap;
    }
    
    /**
     * 获取移动要闻
     * @param cityId
     * @param xc
     * @return
     */
    private Map<String, Object> getMobileYaoWen(int cityId,XinWenSearchCondition xc){        
        xc.setIsMobileNews(1);
        Map<String, Object> newsMap = xinWenSearchService.xinWenSearch(xc);
        return newsMap;
    }
    
    /**
     * 每一小时组装首页的要闻缓存
     */
    @Scheduled(cron="0 0 0/1 * * ?")
    public void getYaoWenInCache(){
        List<DictCity> citys = cityService.getCityList();
//        List<String> cityIds = new ArrayList<String>();
//        for(DictCity dc : citys){
//            String id = "yaoWen:" + dc.getCityId();
//            cityIds.add(id);
//        }
//        try {
//            Map<String, Object> map = cacheHandler.getMultyValues(cityIds);
//            for (String key : map.keySet()) {
//             System.out.println("key= "+ key + " and value= " + map.get(key));
//            }
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (MemcachedException e) {
//            e.printStackTrace();
//        }
                
        for(DictCity dc : citys){
            Map<String, Object> newsMap = getYaoWen(dc.getCityId(),null,0,0);
            List<EsNews> news = getnewsFromMap(newsMap);
            if (news != null) {
                cacheHandler.setCache(INDEX_YAOWEN+dc.getCityId(),INDEX_YAOWEN_TIME,news);
            } 
        }
    }
    
    /**
     * 提供给首页获取缓存要闻数据
     * (现在规则是没有手动自动缓存,如果要加入缓存,则需要判断是手动自动,自动依然是如下方法,手动需要调用HY接口获取数据库数据)
     * @param cityId
     * @param needFindDatebase 是否需要查数据库
     * @return
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public List<EsNewsExt> getYaoWenByIndex(int cityId,boolean needFindDatebase) throws IllegalAccessException, InvocationTargetException {
        List<EsNewsExt> news = null;
        List<Information> newsCMS = null;
        if(needFindDatebase){
            //cms reback coming soon!~
        	try {
            newsCMS = xinWenDao.getHandNews(cityId);
        	} catch (Exception e) {
        		//读取数据库错误时　
        		newsCMS = null;
        	}
        }
        if (newsCMS != null && newsCMS.size() > 0) {
            news = Information.toEsNews(newsCMS);
        }else{
            news = cacheHandler.getCacheValue(INDEX_YAOWEN + cityId, List.class);
            // 防止mem出错
            if (news == null || news.size() == 0) {
            	
                Map<String, Object> newsMap = getYaoWen(cityId, null, 0, 0);
                List<EsNews> esNews = getnewsFromMap(newsMap);
                news = new ArrayList<EsNewsExt>(esNews.size());
                if (esNews != null) {
                	BeanUtils.copyProperties(esNews, news);
                    cacheHandler.setCache(INDEX_YAOWEN + cityId, INDEX_YAOWEN_TIME, esNews);
                }
            }
        }
        return news;
    }
    
    public List<EsNews> getYaoWenByCache(int cityId) {
    	List<EsNews> news = null;
        news = cacheHandler.getCacheValue(INDEX_YAOWEN + cityId, List.class);
        // 防止mem出错
        if (news == null || news.size() == 0) {
            Map<String, Object> newsMap = getYaoWen(cityId, null, 0, 0);
            List<EsNews> esNews = getnewsFromMap(newsMap);
            if (esNews != null) {
                cacheHandler.setCache(INDEX_YAOWEN + cityId, INDEX_YAOWEN_TIME, esNews);
            }
        }
        return news;
    }
    
    
    @SuppressWarnings({ "null", "unchecked" })
    public List<EsNews> getnewsFromMap(Map<String, Object> newsMap){
        List<EsNews> news = null;
        if (null != newsMap  && null != newsMap.get("data")) {
            news = (List<EsNews>) newsMap.get("data");           
        } 
        return news;
    }
    
    /**
     * 7日电话最多的楼盘id
     * 暂时性处理,等合版后用resttemplate
     * @param cityName
     * @param limit
     * @param days
     * @return
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    public List<Integer> getGroupIds(String cityName,int limit,int days){
        List<Integer> results = cacheHandler.getCacheValue(GROUD_ID_FROM_SEVEN_DAYS+cityName, List.class);
       
        if(results == null || results.size() == 0){
            Map<String, Object> urlVariables = new HashMap<String, Object>();
            urlVariables.put("days", days);
            urlVariables.put("limit", limit);
            urlVariables.put("cityName", cityName);
            JSONObject json = interfaceService.getJSONFromInterface(DATA_CENTER_GET_7DAYS_URL, urlVariables);
            if(json != null && (Integer)json.get("errorCode") == 0){
//                results = (List<Integer>)json.get("data");
                
                JSONArray jsonArray = json.getJSONArray("data");
                results = JSON.parseArray(jsonArray.toJSONString(),Integer.class);
                cacheHandler.setCache(GROUD_ID_FROM_SEVEN_DAYS+cityName,GROUD_ID_FROM_SEVEN_DAYS_TIME,results);
            }  
//            
//            //创建默认的 HttpClient 实例  
//            HttpClient httpClient = new DefaultHttpClient();  
//            try {  
//                
//                //创建 httpUriRequest 实例  
//                HttpGet httpGet = new HttpGet("http://focus-app-report.apps.sohuno.com/api/wap/getLoupanRankByCity?appId=1009&days="+days+"&cityName="+cityName+"&limit="+limit);  
//      
//                httpGet.setHeader("sign", "7d4c7fab946742aa2ba0cac76a725105");  
//                
//                //执行 get 请求  
//                HttpResponse httpResponse = httpClient.execute(httpGet);  
//      
//                //获取响应实体  
//                HttpEntity httpEntity = httpResponse.getEntity();  
//                if (httpEntity != null) {  
//                    //响应内容  
//                    String content = EntityUtils.toString(httpEntity);  
//                    JSONObject json = JSONObject.parseObject(content);
//                    if(json != null && (Integer)json.get("errorCode") == 0){
//                        results = (List<Integer>)json.get("data");
//                        cacheHandler.setCache(GROUD_ID_FROM_SEVEN_DAYS+cityName,GROUD_ID_FROM_SEVEN_DAYS_TIME,results);
//                    }                    
//                }  
//      
//                httpGet.abort();  
//            } catch (Exception e) {  
//                e.printStackTrace();  
//            } finally {  
//                //关闭连接，释放资源  
//                httpClient.getConnectionManager().shutdown();  
//            }
        }
        return results;  
    }  
    
    public String subPartContent(String content) throws TimeoutException, InterruptedException, MemcachedException{
        if(StringUtils.isNotBlank(content)){
            content = content.trim().replaceAll(" ", "");
            content = newsService.filterHTML(content);
            if(content.length()>40){
                content = content.substring(0,40);
            }else{
                content = content.substring(0,content.length());
            }
        }
        return content;
    }
}
