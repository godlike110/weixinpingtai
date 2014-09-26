/**
 *
 */
package cn.focus.dc.focuswap.service;


import static cn.focus.dc.focuswap.config.AppConstants.CE_NEWS_TIME;
import static cn.focus.dc.focuswap.config.AppConstants.CHANNEL_NEWS_QUERY_URL;
import static cn.focus.dc.focuswap.config.AppConstants.CK_NEWS_LIST;
import static cn.focus.dc.focuswap.config.AppConstants.CK_NEW_ITEM;
import static cn.focus.dc.focuswap.config.AppConstants.NEWS_CENTER_NORMAL_NEWS_CONTENT_URL;
import static cn.focus.dc.focuswap.config.AppConstants.NEWS_CENTER_NORMAL_NEWS_DIGEST_URL;
import static cn.focus.dc.focuswap.config.AppConstants.NEWS_CENTER_RELATED_NEWS;
import cn.focus.dc.building.model.ProjInfo;
import cn.focus.dc.focuswap.model.BuildingDaoGou;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.Information;
import cn.focus.dc.focuswap.model.News;
import cn.focus.dc.focuswap.model.PageContent;
import cn.focus.dc.focuswap.model.RecentNews;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author yunguangwang
 * @2013-8-27 @下午5:00:51
 */
@Service
public class NewsService {   
    
    //背景颜色
    // private static Pattern BACKGROUND_COLOR = Pattern.compile("BACKGROUND-COLOR.*[;]|BACKGROUND-COLOR.*(?=\")",
    // Pattern.CASE_INSENSITIVE);
    private static Pattern BACKGROUND_COLOR = Pattern.compile("BACKGROUND-COLOR[\\s\\S]*?(;|(?=\"))",
            Pattern.CASE_INSENSITIVE);
    
    //缩进
    private static Pattern TEXT_INDENT = Pattern.compile("TEXT-INDENT[\\s\\S]*?(;|(?=\"))", Pattern.CASE_INSENSITIVE);
            
    //字体
    private static Pattern FONT_FAMILY = Pattern.compile("FONT-FAMILY[\\s\\S]*?(;|(?=\"))", Pattern.CASE_INSENSITIVE);
    
    //字号
    private static Pattern FONT_SIZE = Pattern.compile("FONT-SIZE(.*?)px", Pattern.CASE_INSENSITIVE);
        
    //iframe标签
    private static Pattern IFRAME = Pattern.compile("<iframe(.*?)>(.*?)</iframe>", Pattern.CASE_INSENSITIVE);
    
    //table标签
    private static Pattern TABLE = Pattern.compile("<table(.*?)>(.*?)</table>", Pattern.CASE_INSENSITIVE);
    
    //img标签
    private static Pattern IMG = Pattern.compile("<img(.*?)/>", Pattern.CASE_INSENSITIVE);
    
    //style标签
    private static Pattern STYLE = Pattern.compile("(?<=style(\\s)?=(\\s)?\")[\\s\\S]*?((?=\"))",
            Pattern.CASE_INSENSITIVE);

    //width标签
    private static Pattern WIDTH = Pattern.compile("(?<=width(\\s)?=(\\s)?\")[\\s\\S]*?((?=\"))",
            Pattern.CASE_INSENSITIVE);

    //height标签
    private static Pattern HEIGHT = Pattern.compile("(?<=height(\\s)?=(\\s)?\")[\\s\\S]*?((?=\"))",
            Pattern.CASE_INSENSITIVE);
    
    //<span class="ad">
    private static Pattern FLASH = Pattern.compile("<SPAN (.*?) class\\s=\\s\"?ad\"?>(.*?)</span>",
            Pattern.CASE_INSENSITIVE);
    
    //form标签
    private static Pattern FORM = Pattern.compile("<FORM(.*?)>(.*?)</FORM>", Pattern.CASE_INSENSITIVE);
    
    //a标签
    private static Pattern A = Pattern.compile("(<a (.*?)href([\\s\\S])*?=(.*?)>(.*?)</a>)", Pattern.CASE_INSENSITIVE);
    
    //a标签内容
    // private static Pattern A_CONTENT = Pattern.compile("<a(.*?)href([\\s\\S])*?=\"(.+?)\"(.*?)>(.+?)(</a>)",
    // Pattern.CASE_INSENSITIVE);
    
    private static Pattern A_CONTENT = Pattern.compile(
            "(<a\\s+([^>h]|h(?!ref\\s))*href[\\s+]?=[\\s+]?('|\"))([^(\\s+|'|\")]*)([^>]*>)(.*?)</a>",
            Pattern.CASE_INSENSITIVE);

    //楼盘url
    private static Pattern VOTEHOUSE = Pattern.compile(".*?/votehouse/.*?", Pattern.CASE_INSENSITIVE);
    
    //资讯url
    private static Pattern NEWS = Pattern.compile(".*?/news/.*?", Pattern.CASE_INSENSITIVE);  
    
    //m.focus.cn
    private static Pattern WAP = Pattern.compile(".*?m.focus.cn/.*?", Pattern.CASE_INSENSITIVE); 
    
    //楼盘动态 
    private static Pattern VOTEHOUSE_LPDT = Pattern.compile(".*?/votehouse/lpdt/.*?", Pattern.CASE_INSENSITIVE);
    
    // 导购信息 
    private static Pattern DAOGOU = Pattern.compile(".*?/daogou/.*?", Pattern.CASE_INSENSITIVE);

    // 400电话
    private static Pattern TEL = Pattern.compile("(400(?:-\\d{3,4}){2})\\s*转\\s*(\\d{5}|\\d{4})", Pattern.CASE_INSENSITIVE);
    
    //相关新闻
    //private static Pattern RELATIVENEWS = Pattern.compile("<strong>【相关新闻】</strong>", Pattern.CASE_INSENSITIVE);
    // private static Pattern PAGE = Pattern.compile("<center>[1](.*?)上一页|[123456]|下一页(.*?)</center>",
    // Pattern.CASE_INSENSITIVE);
    
    //空格(标签里面的空格不剪裁)
    private static Pattern SPACE = Pattern.compile("[\\u3000\\s]+(?=[^>]*?(<|$))");
    
    //swf
    private static Pattern EMBED = Pattern.compile("<EMBED(.*?)>(.*?)(</EMBED>|>)", Pattern.CASE_INSENSITIVE);
    
    //注视\bhi\b.*\bLucy\b
    private static Pattern ZHUSHI = Pattern.compile("<!--\\$image=\"(.*)\"-->", Pattern.CASE_INSENSITIVE);
    
    //400phone
    //private static Pattern PHONE400 = Pattern.compile("(400-[0-9]{3}-[0-9]{4}(?:(?:\\s)*[\u4e00-\u9fa5]{1}(?:\\s)*[0-9]{5})?)", Pattern.CASE_INSENSITIVE);

    private static final String INFO_URL = "/zixun/";
    
    //楼盘动态
    private static final String DYNAMIC_URL = "/dongtai/";     
    
    private static final String LOUPAN_URL = "/loupan/";    
    
    private static final String DAOGOU_URL = "/daogou/";
    
    private static final String JSON_ERRORCODE_NAME = "errorCode";
    
    private static final String JSON_DATA_NAME = "data";
    
	// 3代表普通图文新闻
    private static final int COMMON_GRAPHIC_NEWS = 3;

    //楼盘咨询频道的值为38
    private static final int CHANNEL_ID = 38;

    // 从PHP接口load 20页数据
    private static final int NEWS_PAGE_TOTAL = 20;

    //默认加载10条资讯新闻
    private static final int NEWS_PAGE_SIZE = 10;

    private static final String ARTICLES = "articles";

    private static final String CACHE_LOCK_NEWS_KEY = "wap:reloadnews:lock:key";

    private static Log logger = LogFactory.getLog(NewsService.class);
    
    @Autowired
    private InterfaceService interfaceService;

    @Autowired
    private RencentNewsService rencentNewsService;
    
    @Autowired
    private CacheHandlerService cacheHandler;
    
    @Autowired
    private BuildingService buildingService;
    
    @Autowired
    private BuildingDaoGouService buildingDaoGouService;
    
    @Autowired
    private CityService cityService;

 
    // 正则需要过滤得标签
    private Pattern[] tags = {SPACE,FONT_FAMILY,FONT_SIZE,BACKGROUND_COLOR,TEXT_INDENT,IFRAME,FLASH,FORM,TABLE,EMBED,A};
 
    
    //正则需要过滤得标签
    //modified by rogantain @ 20140827 所有的a链接都保留
    private Pattern[] tagsDongtai = {FORM,TABLE,BACKGROUND_COLOR,IFRAME,IMG};

    //图片需要过滤的标签
    private Pattern[] tagsImg = {STYLE,WIDTH,HEIGHT};
    
    /**
     * 新闻列表页
     * 直接从cache里面获取每页的数据
     *
     * @param pageNo
     * @return
     */
    public List<News> getNewsList(Integer pageNo) {
        List<News> newsList = null;
        // 从缓存获取新闻列表
        newsList = cacheHandler.getCacheValue(CK_NEWS_LIST + pageNo,List.class);
        return newsList;
    }

    /**
     * 新闻详细页
     * @param itemId
     * @param pageNo
     * @param pageSize
     * @return
     */
    public News getNews(Integer itemId) {
        StringBuffer sb = new StringBuffer();
        sb.append(CK_NEW_ITEM).append(itemId);
        News news = cacheHandler.getCacheValue(sb.toString(),News.class);
        return news;
    }


    /** 资讯详细页(图片资讯,幻灯片资讯)
    * @author kanezheng
    * @param newsId
    * @param cityId
    * @param pageSize
    * @return
    */
   public Information getNews(Integer newsId, Integer cityId,Integer pageNo,String cityName) {
       Map<String, Object> urlVariables = new HashMap<String, Object>();
       urlVariables.put("cityId", cityId);
       urlVariables.put("newsId", newsId);
       //urlVariables.put("param", "{\"newsId\":\""+newsId+"\"}");
       Information info = null;
       //news 如需
       //要可加缓存,缓存住news实体,分页无需查第一个接口
       try {
           //news = cacheClient.get("wap:news:detail:page:"+pageNo+":newsId:"+newsId);            
          // if(news == null){
               JSONObject jo = interfaceService.getJSONFromInterface(NEWS_CENTER_NORMAL_NEWS_DIGEST_URL, urlVariables);
               String json = extractJSONData(jo);
               info = JSON.parseObject(json,Information.class);
           
               //获取正文内容
               if(info != null && info.getStatus() != null && info.getStatus() != 2){
                   urlVariables.put("pageNo", pageNo);//从第一页开始
                   urlVariables.put("source", 1);//请求来源是手机     
                   urlVariables.put("picType", 0);//图片宽度,1 120 2 150 3 180 4 240
                   urlVariables.put("allFlag", 1);//所有页面的content
                   urlVariables.put("needGraph", 1);//是否需要配图
                   urlVariables.put("needSummary", 1);//是否需要摘要
                   JSONObject jContent = interfaceService.getJSONFromInterface(NEWS_CENTER_NORMAL_NEWS_CONTENT_URL,
                        urlVariables);
                   String jsonContent = extractJSONData(jContent);
                   PageContent newsContent = JSON.parseObject(jsonContent,PageContent.class);
                   //describeContentphone400(newsContent.getContent());
                   if(newsContent != null){
                       newsContent.setContent(filterTag(newsContent.getContent(),cityId,cityName));
                       if(StringUtils.isNotBlank(newsContent.getNewsSummary())){
                           newsContent.setNewsSummary(filterTag(newsContent.getNewsSummary(),cityId,cityName));
                       }
                        // if(StringUtils.isNotBlank(newsContent.getGraphContent())){
                        // String photo = filterTag(newsContent.getGraphContent(),cityId,cityName);
                        // if(photo == null || photo.indexOf("http://") == -1){
                        // photo="";
                        // }
                        // newsContent.setGraphContent(photo);
                        // }
                       info.setPageContent(newsContent);
                   }
                   //cacheClient.add("wap:news:detail:page:"+pageNo+":newsId:"+newsId, 60 * 60 * 24, news);
               }else{
                   info = null;
               }
           //}
       }catch (Exception e) {
           logger.error("", e);
       }   
       return info;
   }
   
   /**
    * 
    * @param content
    */
   public void describeContentphone400(String content) {
	   
	   String loupanReg = "/[a-z]{2,}/loupan/[0-9]+/";
	   String phone400 = "tel\\:[0-9|\\-|,]*[0-9]$";
	   Pattern p = Pattern.compile(loupanReg);
	   Matcher m = p.matcher(content);
	   if(m.find()) {
		   System.out.println(m.group(1));
	   }
	   Pattern p1 = Pattern.compile(phone400);
	   Matcher m1 = p1.matcher(content);
	   if(m1.find()) {
		   System.out.println(m1.group(1));
	   }
	   
	   
	   
   }
   
   /** 获取相关新闻
    * @param newsId
    * @param cityId
    * @param pageSize
    * @return
    */
    public List<Information> getRelateNews(Integer newsId, Integer pageNo) {
        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("newsId", newsId);
        urlVariables.put("pageNo", pageNo);// 从第一页开始
        urlVariables.put("pageSize", 5);//暂定5条
        List<Information> infos = null;
        try {
            JSONObject jo = interfaceService.getJSONFromInterface(NEWS_CENTER_RELATED_NEWS,
                    urlVariables);
            String json = extractJSONData(jo);
            infos = JSON.parseArray(json,Information.class);          
        } catch (Exception e) {
            logger.error("", e);
        }
        return infos;
    }
    
    public void starReloadNewsData() {
        reloadNewsData();
    }
    /**
     * 通过PHP接口加载咨询新闻信息到缓存<br/>
     * 定时运行，或者通过暴露的url主动加载<br/>
     * 每5分钟运行一次
     */
    @Scheduled(cron="0 0/5 * * * ?")
    public void reloadNewsData() {
        boolean getLock = cacheHandler.addCache(CACHE_LOCK_NEWS_KEY, 30, "ok");
        try{
            /**
             * 首先获取分布锁， 避免多个实例同时加载信息到缓存的问题
             */
            if(getLock) {
                logger.info("====开始从PHP接口加载数据====");
                for(int j = 1; j <= NEWS_PAGE_TOTAL; j++) {

                    JSONObject jsonResult = getNewsFromPHP(CHANNEL_ID, j, NEWS_PAGE_SIZE);
                    addNewsToCache(j, jsonResult);
                }
                logger.info("========从PHP接口加载数据完毕==========");
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            cacheHandler.removeCache(CACHE_LOCK_NEWS_KEY);
        }
    }

    private void addNewsToCache(int pageNo, JSONObject jsonResult) {
        List<News> newsList = new ArrayList<News>();

        if (jsonResult != null && jsonResult.get(ARTICLES) != null) {
            JSONArray jsonArray = (JSONArray) jsonResult.get(ARTICLES);

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                // 3代表普通图文新闻
                if (COMMON_GRAPHIC_NEWS == jsonObject.getIntValue("newsType")) {
                    News news = null;
                    try {
                        //接口数据格式可能变动
                        news = JSON.toJavaObject(jsonObject, News.class);
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                    if(news != null) {
                        newsList.add(news);
                        // 添加新闻item缓存
                        cacheHandler.setCache(CK_NEW_ITEM + news.getNewsId(), CE_NEWS_TIME, news);
                    }

                }
            }
            if (newsList.size() > 0) {
                cacheHandler.setCache(CK_NEWS_LIST + pageNo, CE_NEWS_TIME, newsList);
            }
        }

    }

    public JSONObject getNewsFromPHP(Integer channelId, Integer pageNo, Integer pageSize) {
        Map<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("channelId", channelId);
        urlVariables.put("pageNo", pageNo);
        urlVariables.put("pageSize", pageSize);

        return interfaceService.getJSONFromInterface(CHANNEL_NEWS_QUERY_URL, urlVariables);
    }


   /* 从标准的接口返回值中提取所需数据
    * @param jsonObject
    * @return
    */
   private String extractJSONData(JSONObject jsonObject) {
       if (null != jsonObject) {
           Integer errorCode = jsonObject.getInteger(JSON_ERRORCODE_NAME);
           if (null != errorCode && errorCode == 0) {
               return jsonObject.getString(JSON_DATA_NAME);
           }
       }
       return null;
   }
   
   /**
    * 利用正则匹配过滤正文以及副标题的内容
    * @param content
    * @param cityId
    * @param cityName
    * @return
    * @throws TimeoutException
    * @throws InterruptedException
    * @throws MemcachedException
    */
	public String filterTag(String content, Integer cityId, String cityName) throws TimeoutException,
            InterruptedException, MemcachedException {
       /**
        * step1 过滤各标签
        */
       if(StringUtils.isNotBlank(content)) {
       for (Pattern p : tags){
           Matcher matcher = p.matcher(content);
           if(!p.equals(A)){
               content = matcher.replaceAll("");
           }
           /**
            * step2 过滤A标签
            */ 
           else{               
               content = filterATag(matcher,content,cityId,cityName);
           }
       }     
       /**
        * step3 过滤分页内容
        */
       content = content.replace("本系统支持键盘方向键←和→翻页", "");

       /**
        * step4 对电话号码添加链接
        */
//       Matcher matcher = TEL.matcher(content);
//       while (matcher.find()) {
//           String result = matcher.group();
//           StringBuilder sb = new StringBuilder();
//           String main = matcher.group(1), extension = matcher.group(2);
//           String tel = "tel:" + main + "," + extension;
//           sb.append("<a class=\"tel-num\" href=\"");
//           sb.append(tel);
//           sb.append("\" ");
//           sb.append("data-href=\"");
//           sb.append(tel);
//           sb.append("\">");
//           sb.append(result).append("</a>");
//           content = content.replace(result, sb.toString());
//       } 
       
       }
       return content;
   }
   
   /**
    * 利用正则匹配过滤动态的内容
    * @param content
    * @return
    * @throws TimeoutException
    * @throws InterruptedException
    * @throws MemcachedException
    */
   public String filterTagDongtai(String content) throws TimeoutException, InterruptedException, MemcachedException{
       for (Pattern p : tagsDongtai){
           Matcher matcher = p.matcher(content);          
           if(p.equals(A)){               
               String a_content = "";
               while(matcher.find()){            
                   String result = matcher.group();  
                   //logger.info("cut dongtai's link which is "+ result );
                   Matcher matcherContent = A_CONTENT.matcher(result);
                   if(matcherContent.find()){  
                       a_content = matcherContent.group(6);       
                       content = content.replace(result, a_content);
                   }
               }
           }
           //图片去除style，width，height
           else if(p.equals(IMG)){
               while(matcher.find()){
                   String old = matcher.group();
                   String result = old;
                   for (Pattern pImg : tagsImg){
                       Matcher matcherImg = pImg.matcher(result);
                       while(matcherImg.find()){            
                           String afterResult = matcherImg.group();
                           //afterResult = matcherImg.replaceAll("");
                           result = result.replace(afterResult, "");   
                       }
                   }
                   content = content.replace(old, result);        
                   //logger.debug(content);
               }
           }
           //phone coming soon
            // else if(p.equals(PHONE400)){
            // if(matcher.find()){
            // String result = matcher.group();
            // logger.info("cut dongtai's phone which is "+ result );
            // }
            // }
           else{
               content = matcher.replaceAll("");
           }
       }     
       return content;
   }
   
   /**
    * 利用正则匹配放开新闻页的图片注视
    * @param content
    * @return
    * @throws TimeoutException
    * @throws InterruptedException
    * @throws MemcachedException
    */
   public void filterTagNews(News news) throws TimeoutException, InterruptedException, MemcachedException{
       String content = news.getContent();
       Matcher matcher = ZHUSHI.matcher(news.getContent());
       filterDigui(matcher,news,content);
   }
   
   /**
    * string的replaceAll参数必须经过转义
    * Matcher的replaceAll 会一下把所有的replace掉，故用递归
    */
   private void filterDigui(Matcher matcher,News news,String content){
       if(matcher.find()){
           //String result1234 = matcher.group();  
           String result = matcher.group(1);     
           result = "<img src='"+result+"' alt='"+news.getTitle()+"'/>";
           content = matcher.replaceFirst(result);
           matcher = ZHUSHI.matcher(content);
           news.setContent(content);
           filterDigui(matcher,news,content);
       }
   }
   
    private String filterATag(Matcher matcher, String content, Integer cityId, String cityName)
            throws TimeoutException, InterruptedException, MemcachedException {
       String a_content = "";
       String a_href = "";
       while(matcher.find()){            
           String result = matcher.group();  
           //logger.debug(result);
           Matcher matcherContent = A_CONTENT.matcher(result);
           if(matcherContent.find()){  
               a_content = matcherContent.group(6);
               a_href = matcherContent.group(4);
               if(StringUtils.isNotBlank(a_href)) {
               // city信息
               String domain = a_href.substring(a_href.indexOf("//")+"//".length(), a_href.indexOf(".focus.cn")+".focus.cn".length());
               DictCity city = cityService.getCityByDomainname(domain);
               if(city != null ) {
            	   cityId = city.getCityId();
                   cityName = city.getCityPinyinAbbr();
               }
              }

               
               //资讯地址转换wap站资讯
               if(NEWS.matcher(result).matches()){
                   try{
                       String id = a_href.substring(a_href.lastIndexOf("/")+"/".length(),a_href.indexOf(".html"));
                       if(!StringUtils.isNumeric(id)){
                           content = content.replace(result, a_content);
                       }else{
                           content = content.replace(a_href, "/"+cityName + INFO_URL+id+"/");
                       }
                   }catch(Exception e){
                       content = content.replace(result, a_content);
                       //logger.error("change infomation error.newsId =" + newsId + "cityId = " +cityId,e);
                   }
               }               
               //wap站的url不做转换
               else if(WAP.matcher(result).matches()){}
               //楼盘动态单独判断,因为需要转换url
               else if(VOTEHOUSE_LPDT.matcher(result).matches()){
                   //通过id获取楼盘动态,如果有groupId,正常链接,如果没有groupId,则去掉链接
                   try{
                        String id = a_href.substring(a_href.lastIndexOf("lpdt/") + "lpdt/".length(),
                                a_href.lastIndexOf("/"));
                       RecentNews recentNews = rencentNewsService.getRecentNews(Integer.parseInt(id),cityId);
                       if(recentNews != null && !recentNews.getGroup_ids().isEmpty()){
                            content = content.replace(a_href, "/" + cityName + LOUPAN_URL
                                    + recentNews.getGroup_ids().get(0) + DYNAMIC_URL + id + "/");
                       }else{
                           content = content.replace(result, a_content);
                       }
                   }catch(Exception e){
                       content = content.replace(result, a_content);
                   }
               }
               //楼盘详情地址转换wap站楼盘详情
               else if(VOTEHOUSE.matcher(result).matches()){
                   try{
                       String id = a_href.substring(a_href.lastIndexOf("/")+"/".length(),a_href.indexOf(".html"));
                       ProjInfo info = buildingService.getBuilding(cityId, Integer.parseInt(id));
                       content = content.replace(a_href, "/"+cityName + LOUPAN_URL + info.getGroupId() + "/");   
                       Matcher pm = TEL.matcher(content);
                       while (pm.find()) {
                           String phoneStr = pm.group();
                           StringBuilder sb = new StringBuilder();
                           String main = pm.group(1), extension = pm.group(2);
                           String tel = "tel:" + main + "," + extension;
                           sb.append("<a class=\"tel-num\" href=\"");
                           sb.append(tel);
                           sb.append("\" ");
                           sb.append("data-groupId=\"");
                           sb.append(info.getGroupId());
                           sb.append("\" ");
                           sb.append("data-phone400=\"");
                           sb.append(extension);
                           sb.append("\" ");
                           sb.append("data-href=\"");
                           sb.append(tel);
                           sb.append("\">");
                           sb.append(phoneStr).append("</a>");
                           content = content.replace(phoneStr, sb.toString());
                       }
                   }catch(Exception e){
                       content = content.replace(result, a_content);
                   }
               }
               else if (DAOGOU.matcher(result).matches()) {
                   try{
                       String id = a_href.substring(a_href.lastIndexOf("/")+"/".length(),a_href.indexOf(".html"));
                       BuildingDaoGou info = buildingDaoGouService.getDaoGou(cityId, Integer.parseInt(id));
                       content = content.replace(a_href, "/"+cityName + DAOGOU_URL + info.getId() + "/");   
                   }catch(Exception e){
                       content = content.replace(result, a_content);
                   }                   
               }
               else{
            	   //所有的链接都放开
//                   content = content.replace(result, a_content); 
               }
           }
       }
       return content;
   }
   
    public String filterHTML(String content) throws TimeoutException, InterruptedException, MemcachedException {
        Pattern TESDT = Pattern.compile("<().*?>", Pattern.CASE_INSENSITIVE);
        Matcher matcher = TESDT.matcher(content);
        if (matcher.find()) {
            content = matcher.replaceAll("");
        }
        Matcher telMatcher = TEL.matcher(content);
        while (telMatcher.find()) {
            System.out.println(content);
        }
        return content;
    }
   
    public static void main(String args[]) throws TimeoutException, InterruptedException, MemcachedException{     
        //String str = "<a href='ased'>123</a><b>123213fdsa<br />fdsafas121faa<img src=sdada/> ";
        // Pattern TESDT = Pattern.compile("^(<|</)[\\s\\S]*?>$", Pattern.CASE_INSENSITIVE);
    	String str=null;
        Pattern TESDT = Pattern.compile("<().*?>", Pattern.CASE_INSENSITIVE);
        Matcher matcher = TESDT.matcher(str);
        if(matcher.find()){
            str = matcher.replaceAll("");           
        }  
        System.out.println(str);
    }
}
