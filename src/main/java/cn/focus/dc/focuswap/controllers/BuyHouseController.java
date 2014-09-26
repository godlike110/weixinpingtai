package cn.focus.dc.focuswap.controllers;


import cn.focus.dc.commons.annotation.XSSFilter;
import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.model.Condition;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.service.BuyHouseService;
import cn.focus.dc.focuswap.service.CityService;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.HttpFeatures;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author kanezheng
 * @2013-8-28 @上午10:21:33
 */
@Path("buy")
public class BuyHouseController {

    private static final String CITY_ID = "cityId";

    private static final String DISTRICT_ID = "districtId";

    private static final String PAGE_NO = "pageNo";

    private static final String PAGE_SIZE = "pageSize";


    @Autowired
    private BuyHouseService buyHouseService;

    @Autowired
    private CityService cityService;


    /**
     * @http://m.focus.cn/buy/home/showIndex?cityId=1
     * @param inv
     * @param cityId
     * @return 301 m.focus.cn/bj/loupan/
     */
    @Get("home/showIndex")
    public String getShowCondition(Invocation inv, @Param(CITY_ID) Integer cityId) {
        DictCity city = cityService.getCityLocatedInfo(inv);
        return "r:/"+city.getCityPinyinAbbr()+"/loupan/";
    }

    @Get("home/show")
    @HttpFeatures(contentType = "application/json")
    public Object getShowConditionAjax(Invocation inv, @Param(CITY_ID) Integer cityId) {
        if (cityId == null) {
            cityId = cityService.getCityLocatedInfo(inv).getCityId();
        }
        return buyHouseService.getAllCondition(cityId);
    }

    @Get("home/showResident")
    @HttpFeatures(contentType = "application/json")
    public Object getAreaAjax(Invocation inv, @Param(CITY_ID) Integer cityId,
            @Param(DISTRICT_ID) @DefValue("1") Integer districtId) {
        if (cityId == null) {
            cityId = cityService.getCityLocatedInfo(inv).getCityId();
        }
        return buyHouseService.getArea(districtId, cityId);
    }

    @Get("home/showPrice")
    @HttpFeatures(contentType = "application/json")
    public Object getPriceAjax(Invocation inv, @Param(CITY_ID) Integer cityId,
            @Param(DISTRICT_ID) @DefValue("1") Integer districtId) {
        if (cityId == null) {
            cityId = cityService.getCityLocatedInfo(inv).getCityId();
        }
        return buyHouseService.getPrice(districtId, cityId);
    }

    /*
     * 楼盘列表页 get是搜索框页进入 post是条件首页进入
     */
    @Get("building/showList")
    @Post("building/showList")
    @XSSFilter({ "condition1", "condition2", "condition3", "condition4", "condition5", "condition6", "search" })
    public String getBuildingList(Invocation inv, @Param(CITY_ID) Integer cityId,
            @Param(PAGE_SIZE) @DefValue("10") Integer pageSize,
            @Param(PAGE_NO) @DefValue("1") Integer pageNo, Condition c) {
        // if (cityId == null) {
        // cityId = cityService.getCityLocatedInfo(inv).getCityId();
        // }
         String districtId = (String) inv.getAttribute("condition1");
         String typeId = (String) inv.getAttribute("condition2");
         String priceId = (String) inv.getAttribute("condition3");
         String areaId = (String) inv.getAttribute("condition4");
         String lineId = (String) inv.getAttribute("condition5");
         String recomendId = (String) inv.getAttribute("condition6");
         String search = (String) inv.getAttribute("search");
//         if (null == search) {
//         search = "";
//         }
        // JSONObject conditions = new JSONObject();
        // JSONArray queryData = buyHouseService.transformerCondition(districtId, typeId, priceId, areaId,
        // lineId, recomendId);
        // conditions.put("queryData", queryData);
        // conditions.put(PAGE_SIZE, pageSize);
        // conditions.put(PAGE_NO, pageNo);
        // conditions.put(CITY_ID, cityId);
        // conditions.put("queryStr", search);
        // // 页面显示项
        // JSONObject cName = buyHouseService.transformerCondition(districtId, typeId, priceId, areaId, lineId,
        // recomendId, 1);
        // StringBuilder sb = new StringBuilder();
        // sb.append(cName.get("districtName")).append(" ").append(cName.get("typeName")).append(" ")
        // .append(cName.get("priceName")).append(" ").append(cName.get("areaName")).append(" ")
        // .append(cName.get("lineName")).append(" ").append(cName.get("recomendName")).append(" ")
        // .append(search);
        // conditions.put("conditionsName", sb.toString());
        // // 页面应用项
        // conditions.put("conditionsId", buyHouseService.transformerCondition(districtId, typeId, priceId,
        // areaId, lineId, recomendId, 0));
        // Map<String, Object> result = searchService.projSearch(conditions);
        // @SuppressWarnings("unchecked")
        // List<EsProjInfo> list = (List<EsProjInfo>) result.get(DATA);
        // List<EsProjInfoChild> listPics = buyHouseService.switchBuildingParameter(list, cityId,
        // buildingService);
        // result.put(DATA, listPics);
        // conditions.put(DISTRICT_ID, districtId);
        // conditions.put("typeId", typeId);
        // conditions.put("priceId", priceId);
        // conditions.put("areaId", areaId);
        // conditions.put("lineId", lineId);
        // conditions.put("recomendId", recomendId);
        // conditions.put("search", search);
        // inv.addModel("conditions", conditions);
        // inv.addModel("result", result);
        //
        // // search history save
        // JSONObject id = (JSONObject) conditions.get("conditionsId");
        // String keyCookie = buyHouseService.transformCookieValue(id, cName);
        // if (StringUtils.isBlank(keyCookie) && StringUtils.isNotBlank(search)) {
        // keyCookie = "search=" + search + "&";
        // }
        // CookieUtil.saveCookieBySearch(inv, COOKIE_SEARCH_HISTORY,
        // keyCookie + (String) conditions.get("conditionsName"));
        // return "buildingList";
        DictCity city = cityService.getCityLocatedInfo(inv);
        StringBuilder sb = new StringBuilder("k");
        sb.append(districtId==null?"":districtId.split("@@")[0]).append("_")
        .append(typeId==null?"":typeId.split("@@")[0]).append("_")
        .append(priceId==null?"":priceId.split("@@")[0]).append("_")
        .append(areaId==null?"":areaId.split("@@")[0]).append("_")
        .append(lineId==null?"":lineId.split("@@")[0]).append("_")
        .append(recomendId==null?"":recomendId.split("@@")[0]).append("_")
        .append(search==null?"":search).append("_");
        return "r:/"+city.getCityPinyinAbbr()+"/loupan/"+sb.toString()+"/";
    }

    /*
     * 楼盘列表页 分页ajax获取 get是搜索框页进入
     */
//    @Post("building/showListAjax")
//    public String getBuildingListAjax(Invocation inv, @Param(CITY_ID) Integer cityId,
//            @Param(DISTRICT_ID) String districtId, @Param("typeId") String typeId,
//            @Param("priceId") String priceId, @Param("areaId") String areaId, @Param("lineId") String lineId,
//            @Param("recomendId") String recomendId, @Param("search") String search,
//            @Param(PAGE_SIZE) @DefValue("10") Integer pageSize, @Param(PAGE_NO) Integer pageNo,
//            @Param("fun") String fun) {
//        if (cityId == null) {
//            cityId = cityService.getCityLocatedInfo(inv).getCityId();
//        }
//        JSONObject conditions = new JSONObject();
//        JSONArray queryData = buyHouseService.transformerCondition(districtId, typeId, priceId, areaId,
//                lineId, recomendId);
//        conditions.put("queryData", queryData);
//        conditions.put(PAGE_SIZE, pageSize);
//        conditions.put(PAGE_NO, pageNo);
//        conditions.put(CITY_ID, cityId);
//        conditions.put("queryStr", search);
//        // 调用查询接口
//        Map<String, Object> result = searchService.projSearch(conditions);
//        @SuppressWarnings("unchecked")
//        List<EsProjInfoChild> list = (List<EsProjInfoChild>) result.get(DATA);
//        List<EsProjInfoChild> listPics = buyHouseService.switchBuildingParameter(list, cityId);
//        result.put(DATA, listPics);
//        return JsonResponseUtil.okSupportJSONPWithPaginate(listPics, fun, result);
//    }
}
