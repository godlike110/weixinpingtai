package cn.focus.dc.focuswap.controllers;

import static cn.focus.dc.focuswap.config.AppConstants.PAGE_NO;
import static cn.focus.dc.focuswap.config.AppConstants.PAGE_SIZE;

import cn.focus.dc.commons.annotation.XSSFilter;
import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.City;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.EsProjInfoChild;
import cn.focus.dc.focuswap.model.SearchCondition;
import cn.focus.dc.focuswap.service.BuildingProposeService;
import cn.focus.dc.focuswap.service.BuildingSearchService;
import cn.focus.dc.focuswap.service.BuyHouseService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.HomeService;
import cn.focus.dc.focuswap.service.SearchService;
import cn.focus.dc.focuswap.utils.JsonResponseUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.feread.antispam.wordfilter.WordFilterUtil;
import com.feread.antispam.wordfilter.result.FilteredResult;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Path("{cityName:[a-zA-Z]{2,}}")
@AccessLogRequired
@LoginRequired
public class BuildingSearchController {
    
    private static final int PAGE_SIZE_NUMBER = 10;
    
    @Autowired
    private CityService cityService;
    
    @Autowired
    private BuyHouseService buyHouseService;
    
    @Autowired
    private BuildingSearchService buildingSearchService;
    
    @Autowired
    private HomeService homeService;
    
    @Autowired
    private SearchService searchService;
    
    
    @Autowired
    private WordFilterUtil wordFilterUtil;
    
    @Autowired
    private BuildingProposeService buildingProposeService;
    /**
     * @author kanezheng
     * @desc 楼盘搜索页(synchronization)
     * @url m.focus.cn/bj/search/
     * @time 2013.11.22
     * @param inv
     * @param search
     * @param device
     * @return
     * @throws UnsupportedEncodingException 
     */
    @SuppressWarnings("unchecked")
    @Post("search/list/")
    @Get({"search/","search/list/","search/list/{group_name:.+}"})
    @XSSFilter({ "group_name"})
    public String searchList(@Param("cityName") @DefValue("bj") String cityName, Invocation inv,
            @Param("group_name") String search) throws UnsupportedEncodingException {
        String desearch = "";
        search = (String)inv.getAttribute("group_name"); 
        String badWords = null;
        if (StringUtils.isNotBlank(search)) {
            desearch = java.net.URLDecoder.decode(search, "utf-8");
            FilteredResult filterResult = wordFilterUtil.filterText(desearch, ' ');
            badWords = filterResult.getBadWords();
        }
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        // 为了拦截不再获取第二次
        inv.addModel("_city", city);
        List<EsProjInfoChild> epj = new ArrayList<EsProjInfoChild>();
        if (null != badWords && badWords.length() > 0) {
            inv.addModel("buildingList", epj);
            inv.addModel("total", 0);
            //xssfilter会把group_name放进去,违禁词做晴空处理
            inv.addModel("group_name","");
        } else {                    
            //需要同步给出数据
            SearchCondition searchConditions = setPara(desearch, 1, PAGE_SIZE_NUMBER+1, city.getCityId(), null);        
            Map<String, Object> buildingList = buildingSearchService.filterBuilding(searchConditions);
            epj = (List<EsProjInfoChild>) buildingList.get("buildingList");
            Integer total = (Integer)buildingList.get("total");
            if(epj == null || epj.isEmpty()){
                //从suggest获取groupId
                JSONArray ret = homeService.getSearchSuggestListSupportPY(searchConditions.getQueryStr(), city,
                        AppConstants.SEARCH_TYPE_XINFANG);
                List<Integer> groupIds = new ArrayList<Integer>();
                for(Object ob : ret){
                    JSONObject jo = (JSONObject) ob;
                    Integer groupId = (Integer)jo.get("groupId");
                    groupIds.add(groupId);
                }
                //epj = searchService.projGroupIdSearch(groupIds);
                searchConditions = setPara(null, 1, PAGE_SIZE_NUMBER+1, city.getCityId(), groupIds);
                buildingList = buildingSearchService.filterBuilding(searchConditions);
                epj = (List<EsProjInfoChild>) buildingList.get("buildingList");
                total = (Integer)buildingList.get("total");
            }
            epj = buyHouseService.switchBuildingParameter(epj, city,1);        
            
            /**
             * @author linfangwang
             * 截字
             */
            for(EsProjInfoChild espj:epj) {
            	/*//楼盘名
            	if(espj.getProjName().length()>7) {
            		espj.setProjName(espj.getProjName().substring(0, 7) + "...");
            	}
            	//地址
            	if (espj.getProjAddress().length() > 14) {
            		espj.setProjAddress(espj.getProjAddress().substring(0, 14) + "...");
                }*/
            	//户型
            	if(espj.getRoomInfo().length() > 14) {
            		espj.setRoomInfo(espj.getRoomInfo().substring(0, 14)+"...");
            	}
            }
            
            
            inv.addModel("total", total);
            inv.addModel("buildingList", epj);            
            inv.addModel("group_name", desearch);
        }
        //传给前端各个url
        City cityInfo = new City();
        cityInfo.setSuggestUrl("/" + city.getCityPinyinAbbr() + "/search/suggest/");
        cityInfo.setXinfangUrl("/" + city.getCityPinyinAbbr() + "/search/list/");
        cityInfo.setBuildingListUrl("/" + city.getCityPinyinAbbr() + "/searchajax/");
        cityInfo.setDomainName("house.focus.cn");
        inv.addModel("cityStr", JSONObject.toJSONString(cityInfo));
        if(epj != null && epj.size() > PAGE_SIZE_NUMBER){
            inv.addModel("hasNext", true);
            epj.remove(PAGE_SIZE_NUMBER);
        }
        //判断导购标签是否有值
        JSONObject bpJson = buildingProposeService.getProposeList(city.getCityId(), 1, 1);
        int totalDaogou = bpJson == null ? 0 : bpJson.getIntValue("total");
        inv.addModel("totalDaogou", totalDaogou);
//        int returnFlag = Utils.getBackStatus(inv.getRequest());
//        inv.addModel("returnFlag", returnFlag);
        if(epj.size() > 0){
            return "phone/search";
        }else{
            return "phone/search_noresult";
        }
    }


    /**
     * @author kanezheng
     * @desc 根据搜索结果获取楼盘列表(楼盘列表页,nonsynchronous)
     * @time 2013.11.22
     * @param inv
     * @param chosen
     * @return
     * @throws UnsupportedEncodingException 
     */
    @SuppressWarnings("unchecked")
    @Post("searchajax/")
    @Get("searchajax/")
    public String searchList(Invocation inv, @Param("cityName") @DefValue("bj") String cityName,
            @Param("group_name") String search, @Param(PAGE_NO) @DefValue("1") Integer pageNo,
            @Param(PAGE_SIZE) @DefValue("10") Integer pageSize,
            @Param("callback") String callback) throws UnsupportedEncodingException {
        String desearch = "";
        if (StringUtils.isNotBlank(search)) {
            desearch = java.net.URLDecoder.decode(search, "utf-8");
            //inv.addModel("ensearch", ensearch);
        }
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        SearchCondition searchConditions = setPara(desearch, pageNo, pageSize, city.getCityId(), null);        
        Map<String, Object> buildingList = buildingSearchService.filterBuilding(searchConditions);
        List<EsProjInfoChild> epj = (List<EsProjInfoChild>) buildingList.get("buildingList");
        epj = buyHouseService.switchBuildingParameter(epj, city,1);
        
        
        /**
         * @author linfangwang
         * 截字
         */
        for(EsProjInfoChild espj:epj) {
        	/*//楼盘名
        	if(espj.getProjName().length()>7) {
        		espj.setProjName(espj.getProjName().substring(0, 7) + "...");
        	}
        	//地址
        	if (espj.getProjAddress().length() > 14) {
        		espj.setProjAddress(espj.getProjAddress().substring(0, 14) + "...");
            }*/
        	//户型
        	if(espj.getRoomInfo().length() > 14) {
        		espj.setRoomInfo(espj.getRoomInfo().substring(0, 14)+"...");
        	}
        }
        
        
        buildingList.put("buildingList", epj);
        return JsonResponseUtil.jsonp(buildingList, callback);
    }
    
    private SearchCondition setPara(String search,int pageNo,int pageSize,int cityId, List<Integer> groupIds){
        /**
         * modified by rogantian @2014.08.07 searchService中优先判断search，如果search为空才判断groupIds
         */
        SearchCondition searchConditions = new SearchCondition();
        searchConditions.setQueryStr(search);
        searchConditions.setPageNo(pageNo);
        searchConditions.setPageSize(pageSize);
        searchConditions.setCityId(cityId);
        searchConditions.setGroupIds(groupIds);
        return searchConditions;
    }
    
    /**
     * 搜索suggest接口
     * @param inv
     * @param queryWords
     * @param type
     * @param fun
     * @return
     */
    @Get("search/suggest/")
    public Object projSearchSuggest(Invocation inv, @Param("cityName") @DefValue("bj") String cityName,
            @Param("queryWords") String queryWords,@Param("type") @DefValue("1") int type, @Param("callback") String fun) {
        //DictCity city = cityService.getCityLocatedInfo(inv);
        /**
         * 解决bug，用户定位在北京，但是手动输入m.focus.cn/gz/的时候调用该接口，cityId就不能取定位的bj了
         */
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        JSONArray ret = homeService.getSearchSuggestListSupportPY(queryWords, city, type);
        //JSONArray ret = homeService.getSearchSuggestListSupportLucene(queryWords, city, type);
        
        return JsonResponseUtil.okSupportJSONP(ret, fun);
    }
}
