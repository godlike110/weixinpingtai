package cn.focus.dc.focuswap.controllers;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.Condition;
import cn.focus.dc.focuswap.model.DictAreaExt;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.DictCityPriceExt;
import cn.focus.dc.focuswap.model.DictDistrictExt;
import cn.focus.dc.focuswap.model.DictProjTypeExtNew;
import cn.focus.dc.focuswap.model.DictSpecialBuilding;
import cn.focus.dc.focuswap.model.DictTrafficLineExt;
import cn.focus.dc.focuswap.model.EsProjInfoChild;
import cn.focus.dc.focuswap.model.QueryData;
import cn.focus.dc.focuswap.model.SearchCondition;
import cn.focus.dc.focuswap.service.BuildingProposeService;
import cn.focus.dc.focuswap.service.BuildingSearchService;
import cn.focus.dc.focuswap.service.BuildingService;
import cn.focus.dc.focuswap.service.BuyHouseService;
import cn.focus.dc.focuswap.service.ChooseMapSubJsonFilterService;
import cn.focus.dc.focuswap.service.ChooseSubJsonFilterService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.LoginService;
import cn.focus.dc.focuswap.service.PassportProxyService;
import cn.focus.dc.focuswap.service.SearchService.SearchType;
import cn.focus.dc.focuswap.utils.JsonResponseUtil;
import cn.focus.dc.focuswap.utils.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


@Path("{cityName:[a-zA-Z]{2,}}/loupan")
@AccessLogRequired
@LoginRequired
public class BuildingChooseController {
    private static final int PAGE_SIZE_NUMBER = 10;

    //最大一次性加载100页的数据
    private static final int PAGE_NO_MAX = 100;
	@Autowired
	private CityService cityService;
	
	@Autowired
	private BuildingSearchService buildingSearchService;
	
	@Autowired
	private BuyHouseService buyHouseService;
	
	@Autowired
	private BuildingProposeService buildingProposeService;
	
	@Autowired
	private PassportProxyService passportProxyService;
	
	@Autowired
	private BuildingService buildingService;
	
	@Autowired
    private LoginService loginService;
    /**
     * @author kanezheng
     * @desc 楼盘筛选页(synchronization)
     * @url m.focus.cn/bj/loupan/
     * @time 2013.11.22
     * @param inv
     * @param device
     * @param con 格式k开头 kdi_ty_pr_ho_su_sp_se
     * @return
     */
    @SuppressWarnings("unchecked")
    @Get({ "/", "{con:^k.+}/" })
    public String loupan(@Param("cityName") @DefValue("bj") String cityName,
            @DefValue("") @Param("con") String con, Invocation inv,@Param("pageNo") @DefValue("1") int pageNo,
            @Param("pageSize") @DefValue(""+PAGE_SIZE_NUMBER) int pageSize) {
    	
    	
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        con = Condition.verify(con);
        List<QueryData> list = null;
        if(con != null){
            Condition chosen = new Condition(con);
            list = chosen.combined();
        }
        //地图模式
        
        /**
         * 地图页面上列表模式
         * 如果页码》100，则默认从1页开始；否则一次性加载 pageNo*pageSize大小的数据
         */
        if(pageNo > PAGE_NO_MAX) {
        	pageNo = 1;
        } else {
        	pageSize = pageNo *pageSize;
        }
        
        inv.addModel("con",con);
        inv.addModel("pageNo", pageNo);
        
        SearchCondition searchConditions = setPara(list, 1, pageSize, city.getCityId());
        Map<String, Object> buildingList = buildingSearchService.filterBuilding(searchConditions);
        int total = (Integer)buildingList.get("total");
        List<EsProjInfoChild> epj = (List<EsProjInfoChild>) buildingList.get("buildingList");
        
        inv.addModel("total", total);
        // 为了拦截不再获取第二次
        inv.addModel("_city", city);

        StringBuilder sb = new StringBuilder("/loupan/filterajax/"+(con==null?"":con));
        //StringBuilder single = new StringBuilder("/loupan/");
        inv.addModel("city", Utils.putAjaxUrl(city, sb, null));
        Map<SearchType, Object> conditions = buildingSearchService.getConditions(city.getCityId(), true);

        //获取面包屑,以及搜索条件,并且赋予每个条件url
        Map<String, Object> chosenTkd_bread = buildingSearchService.getChosenCondition(conditions,city,con);
        List<Object> chosenList = (List<Object>)chosenTkd_bread.get("chosen");
        
        /**
         * @edit linfangwang
         * 对于新版phone版的条件url修饰
         * 不截字
         */
        //WapVersion wapVersion = DeviceUtils.confirmWapVersion(device, inv.getRequest(), inv.getResponse());
        Map<SearchType, Object> showChosenConMap;
        showChosenConMap = getChosenCondition(conditions, city, con);
        decorateLoupan(epj, city,true,false);

        
        inv.addModel("showChosenConMap", showChosenConMap);
        inv.addModel("buildingList", epj);
        
        /*String tdk  = (String)chosenTkd_bread.get("tdk");
        if(StringUtils.isBlank(tdk)) {
        	tdk = city.getCityName();
        }*/
        
        inv.addModel("chosen", chosenList);
//        inv.addModel("districtName", chosenTkd_bread.get("bread"));
//        inv.addModel("chosenTkd", tdk);
        //判断导购标签是否有值
        int status = buildingProposeService.getProposeStatus(city.getCityId());
        inv.addModel("totalDaogou", status);
       
        inv.addModel("conditions", conditions);
        /*if (epj != null && epj.size() > PAGE_SIZE_NUMBER) {
            inv.addModel("hasNext", true);
            epj.remove(PAGE_SIZE_NUMBER);
        }else{
            inv.addModel("hasNext", false);
        }*/
        inv.addModel("hasNext", buildingList.get("hasNext"));
        //old
        String fold = Utils.getFold(inv.getRequest(), con);
        inv.addModel("fold", fold);
        return "phone/filterBuilding";
    }
    
    /**
     * @author kanezheng
     * @desc 根据不同条件获取楼盘列表(楼盘筛选页,nonsynchronous)
     * @time 2013.11.22
     * @param inv
     * @param PAGE_NO
     * @param PAGE_SIZE
     * @param con 格式k开头 kdi_ty_pr_ho_su_sp_se
     * @return
     */

    @SuppressWarnings("unchecked")
    @Get({ "filterajax/", "filterajax/{con:^k.+}/" })
    public String filterList(Invocation inv, @Param("cityName") @DefValue("bj") String cityName,
        @Param("con") String con, @Param("callback") String callback,
        @Param(AppConstants.PAGE_NO) @DefValue("1") Integer pageNo,
        @Param(AppConstants.PAGE_SIZE) @DefValue("10") Integer pageSize) {
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        con = Condition.verify(con);
        List<QueryData> list = null;
        if(con != null){
            Condition chosen = new Condition(con);
            list = chosen.combined();
        }
        SearchCondition searchConditions = setPara(list, pageNo, pageSize, city.getCityId());
        Map<String, Object> buildingList = buildingSearchService.filterBuilding(searchConditions);
        List<EsProjInfoChild> epj = (List<EsProjInfoChild>) buildingList.get("buildingList");
        
        /***
         * @author linfangwang
         * 新版phone版不截字
         */
        decorateLoupan(epj, city,true,false);
        
        //减少输出项
        String sub = JSON.toJSONString(epj, ChooseSubJsonFilterService.getInstance());
        JSONArray json = JSONArray.parseArray(sub);
        buildingList.put("buildingList", json);
        return JsonResponseUtil.jsonp(buildingList, callback);
    }
    
    private SearchCondition setPara(List<QueryData> list, int pageNo, int pageSize, int cityId) {
        return new SearchCondition(list, pageNo, pageSize, cityId);
    }
    
    /**
     * @author linfangwang
     * 地图模式
     * @throws MalformedURLException 
     */
    @Get("lpmap/")
    public String loupanMap(Invocation inv,@Param("con") @DefValue("") String con,
    		@Param("pageNo") @DefValue("1") int pageNo,@Param("pageSize") @DefValue("10") int pageSize,@Param("cityName") String cityName) throws MalformedURLException {

    	DictCity city = cityService.getCityByNameOrPinYin(cityName);
    	if(city == null ) {
    		return "e:404";
    	}
    	inv.addModel("_city", city);
    	
    	
    	int returnFlag = Utils.getBackStatus(inv.getRequest());
    	inv.addModel("returnFlag", returnFlag);
    	
    	
    	//搜索
    	con = Condition.verify(con);
        List<QueryData> list = null;
        if(con != null){
            Condition chosen = new Condition(con);
            list = chosen.combined();
        }
        SearchCondition searchConditions = setPara(list, pageNo, pageSize, city.getCityId());
        Map<String, Object> ret = buildingSearchService.filterBuilding(searchConditions);
        List<EsProjInfoChild> epj = (List<EsProjInfoChild>) ret.get("buildingList");
    	
        inv.addModel("_epjs", epj.size());
        
        decorateLoupan(epj, city,true,true);
        //下一页
        StringBuilder sb = null;
        if(StringUtils.isBlank(con)) {
        	sb = new StringBuilder("/"+city.getCityPinyinAbbr()+"/loupan/lpmapajax/?"+"pageNo="+(pageNo+1)+"&pageSize="+pageSize);
        } else {
        	sb = new StringBuilder("/"+city.getCityPinyinAbbr()+"/loupan/lpmapajax/?"+(con==null ? "":con)+"&pageNo="+(pageNo+1)+"&pageSize="+pageSize);
        }
        
        Map<SearchType, Object> conditions = buildingSearchService.getConditions(city.getCityId(), true);
        Map<SearchType, Object> showChosenConMap = getChosenCondition(conditions, city, con);
        inv.addModel("showChosenConMap", showChosenConMap);
        
        
        inv.addModel("city", Utils.putAjaxUrl(city, sb, null));
        inv.addModel("hasNext", ret.get("hasNext"));
        inv.addModel("count", ret.get("total"));
    	inv.addModel("pageNo", ret.get("pageNo"));
    	inv.addModel("pageSize", ret.get("pageSize"));
    	
        inv.addModel("con",con);
        
        //减少输出项
        String sub = JSON.toJSONString(epj, ChooseMapSubJsonFilterService.getInstance());
        JSONArray json = JSONArray.parseArray(sub);
        ret.put("buildingList", json);
    	
        
        JSONObject object = new JSONObject(ret);
        
        inv.addModel("epjs", object.toJSONString());
        
    	return "phone/filterBuildingMap";
    }
    
    @Get("lpmapajax/")
    @Post("lpmapajax/")
    public String lpmapAjax(Invocation inv,@Param("con") @DefValue("") String con,
    		@Param("pageNo") @DefValue("1") int pageNo,@Param("pageSize") @DefValue("10") int pageSize,@Param("cityName") String cityName,@Param("callback") String fun) {
    	DictCity city = cityService.getCityByNameOrPinYin(cityName);
    	if(city == null ) {
    		return null;
    	}
    	//搜索
    	con = Condition.verify(con);
        List<QueryData> list = null;
        if(con != null){
            Condition chosen = new Condition(con);
            list = chosen.combined();
        }
        SearchCondition searchConditions = setPara(list, pageNo, pageSize, city.getCityId());
        Map<String, Object> ret = buildingSearchService.filterBuilding(searchConditions);
        List<EsProjInfoChild> epj = (List<EsProjInfoChild>) ret.get("buildingList");
    	
        decorateLoupan(epj, city,true,true);
        
        //减少输出项
        String sub = JSON.toJSONString(epj, ChooseMapSubJsonFilterService.getInstance());
        JSONArray json = JSONArray.parseArray(sub);
        ret.put("buildingList", json);
        JSONObject object = new JSONObject(ret);

        if(StringUtils.isBlank(fun)) {
        	return "@"+object.toJSONString();
        } else {
        	return "@"+fun+"("+object.toJSONString()+")";
        }
    }
    
    
    
    /**
     * @author linfangwang
     * 针对seo
     */
    public Map<SearchType, Object> showChosenConMap(List<Object> chosenList){
    	
    	Map<SearchType, Object> map = new HashMap<SearchType, Object>();
    	
    	for(Object obj:chosenList) {
    		
    		if(obj instanceof DictDistrictExt) {
    			DictDistrictExt district = (DictDistrictExt) obj;
    			map.put(SearchType.DISTRICT, district);
    		} else if (obj instanceof DictProjTypeExtNew) {
    			DictProjTypeExtNew type = (DictProjTypeExtNew) obj;
    			map.put(SearchType.TYPE, type);
    		} else if (obj instanceof DictCityPriceExt) {
    			DictCityPriceExt price = (DictCityPriceExt) obj;
    			map.put(SearchType.PRICE, price);
    		} else if(obj instanceof DictAreaExt) {
    			DictAreaExt hot = (DictAreaExt) obj;
    			map.put(SearchType.HOT, hot);
    		} else if(obj instanceof DictTrafficLineExt) {
    			DictTrafficLineExt subway = (DictTrafficLineExt) obj;
    			map.put(SearchType.SUBWAY, subway);
    		} else if(obj instanceof DictSpecialBuilding) {
    			DictSpecialBuilding special = (DictSpecialBuilding) obj;
    			map.put(SearchType.SPECIAL, special);
    		}
    	}
    	
    	return map;
    }
    
    
    /**
     * @author linfangwang
     * 针对phone版新版
	 * 筛选条件 url 生成
	 * linkUrl:筛选条件url；
	 * 条件已选：selected
	 * 条件不限
	 * @param con:为前端传来的条件
	 * @return 已选择的条件 map
	 */
	@SuppressWarnings("unchecked")
	public Map<SearchType, Object> getChosenCondition(Map<SearchType, Object> allConditions,DictCity city,String con) {
		
		//已选的条件
		Map<SearchType, Object> showCondition = new LinkedHashMap<SearchType, Object>();
		
		if(StringUtils.isNotBlank(con)){
            con +="/";            
        }else{
            con="k______/";
        }
		
		int index = -1; //条件索引值
        String[] sp = con.split("_");
        
        //客户端url为定值
        StringBuilder linkUrl = new StringBuilder("/"+city.getCityPinyinAbbr()+"/loupan/");
		
        for(SearchType q : allConditions.keySet()){
            switch (q) {
                case DISTRICT:
                    List<DictDistrictExt> dde = (List<DictDistrictExt>) allConditions.get(SearchType.DISTRICT); 
                    
                    //增加一个区域不限的条件
                    DictDistrictExt ddExt = new DictDistrictExt();
                    ddExt.setDistrictName("不限");
                    dde.add(0,ddExt);
                    
                    index = 0; 
                    String[] arr0 = sp.clone();
                    for (DictDistrictExt dd : dde) {
                    	
                    	if(dd.getDistrictId() == null) {
                    		arr0[index] = "";
                    		dd.setLinkUrl(linkUrl.toString()+"k"+StringUtils.join(arr0,"_"));
                    	} else {
                    		arr0[index] = dd.getCondValue();
                    		dd.setLinkUrl(linkUrl.toString()+"k"+StringUtils.join(arr0,"_"));
                    		//获取选中项
                            if(StringUtils.isNotBlank(sp[index]) && sp[index].equals("k"+dd.getCondValue())){
                                //以选中
                                dd.setSelect("selected");
                                showCondition.put(SearchType.DISTRICT, dd.clone(index));
                            }
                    	}
                    }
                    break;
                case TYPE:
                    List<DictProjTypeExtNew> dpt = (List<DictProjTypeExtNew>) allConditions.get(SearchType.TYPE); 
                    
                    DictProjTypeExtNew dExtNew = new DictProjTypeExtNew();
                    dExtNew.setTypeName("不限");
                    dpt.add(0, dExtNew);
                    
                    index = 1; 
                    String[] arr1 = sp.clone();
                    for (DictProjTypeExtNew dd : dpt) {
                    	
                    	if(dd.getTypeId() == null ) {
                    		arr1[1] = "";
                    		dd.setLinkUrl(linkUrl.toString()+StringUtils.join(arr1,"_"));
                    	} else {
                    		arr1[1] = dd.getCondValue();
                    		dd.setLinkUrl(linkUrl.toString()+StringUtils.join(arr1,"_"));
                    		//获取选中项
                            if(StringUtils.isNotBlank(sp[index]) && sp[index].equals(dd.getCondValue())){
                                dd.setSelect("selected");
                                showCondition.put(SearchType.TYPE, dd.clone(index));
                            }
                    	}
                    	
                    }
                    break;
                case PRICE:
                    List<DictCityPriceExt> dcp = (List<DictCityPriceExt>) allConditions.get(SearchType.PRICE);
                    
                    
                    DictCityPriceExt priceExt = new DictCityPriceExt();
                    priceExt.setCondName("不限");
                    dcp.add(0, priceExt);
                    
                    
                    index = 2; 
                    String[] arr2 = sp.clone();
                    for (DictCityPriceExt dd : dcp) {   
                    	
                    	if(dd.getId() == null ) {
                    		 arr2[2] = "";
                    		 dd.setLinkUrl(linkUrl.toString()+StringUtils.join(arr2,"_"));
                    	} else {
                    		arr2[2] = dd.getCondValue();
                    		 dd.setLinkUrl(linkUrl.toString()+StringUtils.join(arr2,"_"));
                    		//获取选中项
                            if(StringUtils.isNotBlank(sp[index]) && sp[index].equals(dd.getCondValue())){
                                dd.setSelect("selected");
                                showCondition.put(SearchType.PRICE, dd.clone(index));
                            }
                    	}
                    }
                    break;
                case HOT:
                    List<DictAreaExt> dae = (List<DictAreaExt>) allConditions.get(SearchType.HOT); 
                    
                    DictAreaExt areaExt = new DictAreaExt();
                    areaExt.setAreaName("不限");
                    dae.add(0, areaExt);
                    
                    
                    index = 3; 
                    String[] arr3 = sp.clone();
                    for (DictAreaExt dd : dae) {     
                    	
                    	if(dd.getAreaId() == null ) {
                    		arr3[3] = "";
                    		dd.setLinkUrl(linkUrl.toString()+StringUtils.join(arr3,"_"));
                    	} else {
                    		arr3[3] = dd.getCondValue();
                    		dd.setLinkUrl(linkUrl.toString()+StringUtils.join(arr3,"_"));
                    		//获取选中项
                            if(StringUtils.isNotBlank(sp[index]) && sp[index].equals(dd.getCondValue())){
                                dd.setSelect("selected");
                                showCondition.put(SearchType.HOT, dd.clone(index));
                            }
						}
                        
                        
                    }
                    break;
                case SUBWAY:
                    List<DictTrafficLineExt> dtl = (List<DictTrafficLineExt>) allConditions.get(SearchType.SUBWAY); 
                    
                    DictTrafficLineExt trafficLineExt = new DictTrafficLineExt();
                    trafficLineExt.setLineName("不限");
                    dtl.add(0, trafficLineExt);
                    
                    index = 4; 
                    String[] arr4 = sp.clone();
                    for (DictTrafficLineExt dd : dtl) {
                    	
                    	if(dd.getLineId() == null ) {
                    		arr4[4] = "";
                    		dd.setLinkUrl(linkUrl.toString()+StringUtils.join(arr4,"_"));
                    	} else {
                    		arr4[4] = dd.getCondValue();
                    		dd.setLinkUrl(linkUrl.toString()+StringUtils.join(arr4,"_"));
                    		//获取选中项
                            if(StringUtils.isNotBlank(sp[index]) && sp[index].equals(dd.getCondValue())){
                                dd.setSelect("selected");
                                showCondition.put(SearchType.SUBWAY, dd.clone(index));
                            }
                    	}
  
                    }
                    break;
                case SPECIAL:
                    List<DictSpecialBuilding> dsb = (List<DictSpecialBuilding>) allConditions.get(SearchType.SPECIAL); 
                    
                    DictSpecialBuilding specialBuilding = new DictSpecialBuilding();
                    specialBuilding.setCondName("不限");
                    specialBuilding.setCondValue("");
                    dsb.add(0, specialBuilding);
                    
                    index = 5; 
                    String[] arr5 = sp.clone();
                    for (DictSpecialBuilding dd : dsb) {
                        arr5[5] = dd.getCondValue();
                        dd.setLinkUrl(linkUrl.toString()+StringUtils.join(arr5,"_"));
                        //获取选中项
                        if (StringUtils.isNotBlank(sp[index]) && sp[index].equals(dd.getCondValue())) {                 
                            dd.setSelect("selected");
                            showCondition.put(SearchType.SPECIAL, dd.clone(index));
                        }
                    }
                    break;
                default:
                    break;
                }
            } 
       
        return showCondition;
	}
	
	/**
	 * @author linfangwang
	 * 新版楼盘数据修饰
	 * 页面显示数据
	 * 楼盘url：buildingUrl
	 * 楼盘名：projName
	 * 楼盘状态：saleStatus
	 * 楼盘价格:avgPriceShow
	 * 楼盘地址：ProjAddress
	 * 楼盘折扣：Discount
	 * 楼盘图片：url
	 */
	public void decorateLoupan(List<EsProjInfoChild> espjs,DictCity city,boolean isMobile,boolean isMap){
		
		List<EsProjInfoChild> del = null;
		
		
		if(espjs != null && !espjs.isEmpty()){
			
			for(EsProjInfoChild espj : espjs){
				
				//楼盘url:
				StringBuilder sb = new StringBuilder();
                String url = "";
                url = sb.append("/").append(city.getCityPinyinAbbr()).append("/loupan/")
                            .append(espj.getGroupId()).append("/").toString();
                espj.setBuildingUrl(url);
                
                //楼盘状态
                
                if(!isMap) {
                	
                	//楼盘图片url
                    buildingService.genRealPhotoPath(espj, true);
                    
                    
                    // 打折信息
                    if ("待定".equals(espj.getDiscount().trim()) || "暂无".equals(espj.getDiscount().trim())
                            || "无".equals(espj.getDiscount().trim()) || "0".equals(espj.getDiscount().trim())) {
                    	espj.setDiscount("");
                    }
                    
                    //楼盘名
                    if(!isMobile) {
                    	//pad 版
                    	//楼盘名
                    	if(espj.getProjName().length()>7) {
                    		espj.setProjName(espj.getProjName().substring(0, 7) + "...");
                    	}
                    	//地址
                    	if (espj.getProjAddress().length() > 9) {
                    		espj.setProjAddress(espj.getProjAddress().substring(0, 9) + "...");
                        }
                    }
                    
                  //楼盘价格
        			buildingService.setBuildingListPrice(espjs);
        			
                } else {
                	
                	
                	if(del == null ) {
                		del = new ArrayList<EsProjInfoChild>();
                	}
                	
                	//经纬度
                	float longitude = Float.parseFloat(StringUtils.isBlank(espj.getLongitude()) ? "0" : espj
                            .getLongitude());
                    float latitude = Float.parseFloat(StringUtils.isBlank(espj.getLatitude()) ? "0" : espj
                            .getLatitude());
                    if (longitude < AppConstants.longtitudeMin || longitude > AppConstants.longtitudeMax
                            || latitude < AppConstants.latitudeMin || latitude > AppConstants.latitudeMax) {
                       //不合法的经纬度
                    	del.add(espj);
                    }
                    
                  //楼盘价格
                  buildingService.setBuildingMapListPrice(espjs);
                }
                
			}
			
		}
		
		if(del != null && !del.isEmpty()){
			espjs.removeAll(del);
		}
	}
}
