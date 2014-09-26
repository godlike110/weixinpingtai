package cn.focus.dc.focuswap.controllers.sohunews;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import cn.focus.dc.focuswap.annotation.AccessLogRequired;
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
import cn.focus.dc.focuswap.service.BuildingSearchService;
import cn.focus.dc.focuswap.service.BuildingService;
import cn.focus.dc.focuswap.service.ChooseSubJsonFilterService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.SearchService.SearchType;
import cn.focus.dc.focuswap.utils.CookieUtil;
import cn.focus.dc.focuswap.utils.JsonResponseUtil;
import cn.focus.dc.focuswap.utils.Utils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

/**
 * 
 * @author linfangwang
 *
 */

@Path("/")
public class SohunewsLouPanController {

	@Autowired
	private CityService cityService;
	@Autowired
	private BuildingSearchService buildingSearchService;
	@Autowired
	private BuildingService buildingService;
	
	
	/**
	 * 楼盘筛选页
	 * 入口：城市选择页（关闭按钮+点击某个城市）、搜狐新闻客户端
	 * @param inv
	 * @param loupanSelect：楼盘筛选条件,格式如下：kdi_ty_pr_ho_su_sp_se:
	 * 增加条件不限
	 * 每次加载10套房源
	 * @param cityName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Get("loupan/")
	@AccessLogRequired
	public String loupanChoose(Invocation inv,@Param("lpChoose") @DefValue("") String lpChoose,@Param("cityName") String cityName) {
		
		DictCity city = getCity(inv, cityName);
		
		if(city == null ) {
			//城市信息有误
			return "e:404";
		}
		
		inv.addModel("_city", city);
		
		//搜索条件
		if(StringUtils.isNotBlank(lpChoose)) {
			lpChoose = lpChoose.replaceAll("/","");
		}
		lpChoose = Condition.verify(lpChoose);
		
		//查询条件
		List<QueryData> list = null;
		if(lpChoose != null) {
			Condition condition = new Condition(lpChoose);
			list = condition.combined();
		}
		SearchCondition searchConditions = new SearchCondition(list, 1, 20, city.getCityId());
		//查询条件+已发布+未删除+排序
		/**
		 * buildingList：
		 * total
		 */
		Map<String, Object> ret = buildingSearchService.filterBuilding(searchConditions);
		
		//筛选楼盘总数
		Integer total = (Integer) ret.get("total");
		boolean hasNext = (Boolean) ret.get("hasNext");
		//筛选结果
		List<EsProjInfoChild> espjs = (List<EsProjInfoChild>) ret.get("buildingList");
		
		//楼盘页面显示价格
		decorateLoupan(espjs, city);
		inv.addModel("espjs", espjs);
		inv.addModel("total", total);
		inv.addModel("hasNext", hasNext);

		/**
		 * 楼盘Ajax url 加载
		 * sohunews/loupan/filterAjax?lpChoose=?&cityName=?&pageNo=?&pageSize=?
		 */
		StringBuilder sb = new StringBuilder("/sohunews/loupan/filterajax/");
		
        inv.addModel("city", Utils.putAjaxUrl(city, sb, null));
        inv.addModel("lpChoose",lpChoose);
		
		//筛选条件获取
		Map<SearchType, Object> conditions = buildingSearchService.getConditions(city.getCityId(), true);
		
		//conditions筛选条件url生成，返回已选择的条件
		Map<String, Object> resultMap = getChosenCondition(conditions, city, lpChoose);
		

		Map<SearchType, Object> showChosenConMap = (Map<SearchType, Object>) resultMap.get("chosen");
		
		
		//筛选条件
		inv.addModel("conditions", conditions);
		//当前已选条件
		inv.addModel("showChosenConMap", showChosenConMap);
		
		
		//楼盘筛选页面
		return "loupanChoose";
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Get("loupan/filterAjax/")
	public String loupanChooseAjax(Invocation inv,
			@Param("lpChoose") String lpChoose,
			@Param("cityName") String cityName,
			@Param("pageNo") @DefValue("2") Integer pageNo,
			@Param("pageSize") @DefValue("20") Integer pageSize,@Param("callback") String fun) {

		DictCity city = getCity(inv, cityName);

		if (city == null) {
			return null;
		}

		// 搜索条件
		lpChoose = Condition.verify(lpChoose);

		// 查询条件
		List<QueryData> list = null;
		if (lpChoose != null) {
			Condition condition = new Condition(lpChoose);
			list = condition.combined();
		}
		SearchCondition searchConditions = new SearchCondition(list, pageNo, pageSize,
				city.getCityId());
		// 查询条件+已发布+未删除+排序
		/**
		 * buildingList： total
		 */
		Map<String, Object> ret = buildingSearchService.filterBuilding(searchConditions);

		
		List<EsProjInfoChild> espjs = (List<EsProjInfoChild>) ret.get("buildingList");

		decorateLoupan(espjs, city);
		
		//减少输出项
		String sub = JSON.toJSONString(espjs, ChooseSubJsonFilterService.getInstance());
		JSONArray json = JSONArray.parseArray(sub);
		ret.put("buildingList", json);
		
		//输出项
		return JsonResponseUtil.jsonp(ret, fun);
	}
	
	/**
	 * 筛选条件 url 生成
	 * linkUrl:筛选条件url；
	 * 条件已选：selected
	 * 条件不限
	 * @param con:为前端传来的条件
	 * @return 已选择的条件 map
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getChosenCondition(Map<SearchType, Object> allConditions,DictCity city,String con) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		//已选的条件
		Map<SearchType, Object> showCondition = new LinkedHashMap<SearchType, Object>();
		
		
		
		if(StringUtils.isNotBlank(con)){
            con +="/";            
        }else{
            con="k______/";
        }
		
		int index = -1; //条件索引值
        String[] sp = con.split("_");
//        List<Object> showCondition = new ArrayList<Object>();
        
        //客户端url为定值
        StringBuilder linkUrl = new StringBuilder("/sohunews/loupan/?cityName="+city.getCityPinyinAbbr()+"&lpChoose=");
		
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

        result.put("chosen", showCondition);
        
        return result;

	}
	
	
	
	/**
	 * 城市列表页：如果用户没有传入城市；则使用cookie；如果cookie中没有；则使用默认城市bj
	 * 入口：楼盘筛选页
	 * 点击城市后，则跳转到楼盘筛选页
	 * 如果城市选择前后不变，则不清空条件；关闭按钮，则返回历史记录
	 * @param inv
	 * @param cityName
	 * @return
	 */
	@Get("citySelect/")
	@AccessLogRequired
	public String citySelect(Invocation inv,@Param("cityName") String cityName,@Param("from") @DefValue("loupan") String from){
		
		DictCity city = getCity(inv, cityName);
		
		if(city == null ) {
			//如果城市是不存在的一个错误城市名
			return "phone/404";
		}
		//城市列表数据
		List<DictCity> citys = cityService.getCityList();
		
		//城市列表排序
		TreeMap<String,List<DictCity>> cityListMap=cityService.getCityListOrderByPinYin(citys);
		inv.addModel("_city", city);
		inv.addModel("citys", citys);
		inv.addModel("cityListMap", cityListMap);
		inv.addModel("from", from);
		//跳转页面城市列表页
		return "citySelect";
	}

	
	//获取当前城市
	public DictCity getCity(Invocation inv,String cityName) {
		
		DictCity city = null;
		
		if(StringUtils.isBlank(cityName)) {
			city = cityService.getCityLocatedFromCookie(inv);
			
			if(city == null ) {
				city = cityService.getDefaultCity();
			} 
			
		} else {
			city = cityService.getCityByNameOrPinYin(cityName);
			
			//cookie中设置当前城市信息
			if(city != null ) {
				cityService.setCityLocated(inv, city);
			}
		}
		
		/*//cookie中设置当前城市信息
		if(city != null ) {
			cityService.setCityLocated(inv, city);
		}*/
		
		return city;
	}
	
	/**
	 * 页面显示数据
	 * 楼盘url：buildingUrl
	 * 楼盘名：projName
	 * 楼盘状态：saleStatus
	 * 楼盘价格:avgPriceShow
	 * 楼盘地址：ProjAddress
	 * 楼盘折扣：Discount
	 * 楼盘图片：url
	 */
	public void decorateLoupan(List<EsProjInfoChild> espjs,DictCity city){
		
		if(espjs != null && !espjs.isEmpty()){
			
			for(EsProjInfoChild espj : espjs){
				
				//楼盘url:
				StringBuilder sb = new StringBuilder();
                String url = "";
                url = sb.append("/").append(city.getCityPinyinAbbr()).append("/loupan/")
                            .append(espj.getGroupId()).append("/").toString();
                espj.setBuildingUrl(url);
                
                //楼盘状态
                
                //楼盘图片url
                buildingService.genRealPhotoPath(espj, true);
                
                
                // 打折信息
                if ("待定".equals(espj.getDiscount().trim()) || "暂无".equals(espj.getDiscount().trim())
                        || "无".equals(espj.getDiscount().trim()) || "0".equals(espj.getDiscount().trim())) {
                	espj.setDiscount("");
                }
			}
			
			//楼盘价格
			buildingService.setBuildingListPrice(espjs);
		}
	}

}
