package cn.focus.dc.focuswap.service;

import static cn.focus.dc.focuswap.config.AppConstants.BUY_BUILD_CONDITION_KEY;
import static cn.focus.dc.focuswap.config.AppConstants.CONDITION_AREAS;
import static cn.focus.dc.focuswap.config.AppConstants.CONDITION_AREAS_NAME;
import static cn.focus.dc.focuswap.config.AppConstants.CONDITION_DISTRICTS;
import static cn.focus.dc.focuswap.config.AppConstants.CONDITION_DISTRICTS_NAME;
import static cn.focus.dc.focuswap.config.AppConstants.CONDITION_LINES;
import static cn.focus.dc.focuswap.config.AppConstants.CONDITION_LINES_NAME;
import static cn.focus.dc.focuswap.config.AppConstants.CONDITION_PRICES;
import static cn.focus.dc.focuswap.config.AppConstants.CONDITION_PRICES_NAME;
import static cn.focus.dc.focuswap.config.AppConstants.CONDITION_RECOMENDS;
import static cn.focus.dc.focuswap.config.AppConstants.CONDITION_RECOMENDS_NAME;
import static cn.focus.dc.focuswap.config.AppConstants.CONDITION_TYPES;
import static cn.focus.dc.focuswap.config.AppConstants.CONDITION_TYPES_NAME;
import static cn.focus.dc.focuswap.config.AppConstants.DATACENTER_BUILDING_NAME_BUY_CONDITION;
import static cn.focus.dc.focuswap.config.AppConstants.DATE_CENTER_INTERFACE_EXPIRE;

import cn.focus.dc.focuswap.model.BuildingLayoutPhotoes;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.building.model.es.EsProjInfo;
import cn.focus.dc.focuswap.model.EsProjInfoChild;
import cn.focus.dc.focuswap.model.ProjPhotoesExt;
import cn.focus.dc.focuswap.model.QueryData;
import cn.focus.dc.focuswap.model.SearchCondition;
import cn.focus.dc.focuswap.service.SearchService.SaleStatus;
import cn.focus.dc.focuswap.service.SearchService.SearchType;
import cn.focus.dc.focuswap.service.SearchService.SearchTypeSpecial;
import cn.focus.dc.focuswap.utils.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyHouseService {

    private static Log logger = LogFactory.getLog(BuyHouseService.class);

    private static Map<String, String> recommendMap = new HashMap<String, String>();

    private static Map<String, String> conditionMap = new HashMap<String, String>();

    private static final String JSON_ERRORCODE_NAME = "errorCode";

    /*
     * @Autowired private RestTemplate restTemplate;
     */

    @Autowired
    private InterfaceService interfaceService;

    @Resource(name="memcachedClient")
    private MemcachedClient cacheClient;

    @Autowired
    private BuildingService buildingService;

    static {
        recommendMap.put("dz", "打折楼盘");
        recommendMap.put("kp", "本月开盘");
        recommendMap.put("xhx", "小户型");
        recommendMap.put("xf", "现房");
        recommendMap.put("zxkp", "最新开盘");

        // 条件影射
        conditionMap.put("districtId", "condition1");
        conditionMap.put("typeId", "condition2");
        conditionMap.put("priceId", "condition3");
        conditionMap.put("areaId", "condition4");
        conditionMap.put("lineId", "condition5");
        conditionMap.put("recomendId", "condition6");
    }

    // 按wiki格式获取所有搜索条件
    public JSONObject getAllCondition(int cityId) {
        // check();
        JSONObject jsonCondition = null;
        JSONObject jsonResult = getJsonStr(cityId);
        if (jsonResult != null) {
            Integer errorCode = jsonResult.getInteger(JSON_ERRORCODE_NAME);
            if (null != errorCode && errorCode == 0) {
                jsonCondition = transformerCondition((JSONObject) jsonResult.get("data"));
            } else {
                jsonCondition = jsonResult;
            }
        }
        return jsonCondition;
    }

    // 获取区域下的热点
    public JSONObject getArea(int districtId, int cityId) {
        // check();
        JSONObject jsonArea = null;
        JSONObject jsonResult = getJsonStr(cityId);
        if (jsonResult != null) {
            Integer errorCode = jsonResult.getInteger(JSON_ERRORCODE_NAME);
            if (null != errorCode && errorCode == 0) {
                JSONObject jsonConditionData = (JSONObject) jsonResult.get("data");
                JSONObject areaObjectDefalut = (JSONObject) jsonConditionData.get("areas");
                JSONArray areaArray = (JSONArray) areaObjectDefalut.get(districtId + "");
                // 0为没绑定区域
                if (areaArray == null || areaArray.size() == 0) {
                    areaArray = (JSONArray) areaObjectDefalut.get("0");
                }
                jsonArea = transformerCondition(areaArray, CONDITION_AREAS, CONDITION_AREAS_NAME, "areaId",
                        "areaName");
            } else {
                jsonArea = jsonResult;
            }
        }
        return jsonArea;
    }

    // 获取区域下的价格(只有北京有区别)
    public JSONObject getPrice(int districtId, int cityId) {
        JSONObject jsonPrice = null;
        JSONObject jsonResult = getJsonStr(cityId);
        if (jsonResult != null) {
            Integer errorCode = jsonResult.getInteger(JSON_ERRORCODE_NAME);
            if (null != errorCode && errorCode == 0) {
                JSONObject jsonConditionData = (JSONObject) jsonResult.get("data");
                JSONObject areaObjectDefalut = (JSONObject) jsonConditionData.get("prices");
                JSONArray priceArray;
                if (cityId == 1) {
                    priceArray = (JSONArray) areaObjectDefalut.get(districtId + "");
                } else {
                    priceArray = (JSONArray) areaObjectDefalut.get("0");
                }
                jsonPrice = transformerCondition(priceArray, CONDITION_PRICES, CONDITION_PRICES_NAME, "id",
                        "price");
            } else {
                jsonPrice = jsonResult;
            }
        }
        return jsonPrice;
    }

    // 获取缓存或接口的数据
    public JSONObject getJsonStr(int cityId) {
        JSONObject jsonResult = null;
        Object cachedMsg = null;
        try {
            cachedMsg = cacheClient.get(BUY_BUILD_CONDITION_KEY + cityId);
            /**
             * fix bug java.lang.ClassCastException: java.lang.String cannot be cast to com.alibaba.fastjson.JSONObject
             * modify by shixingtao 2013-10-15
             */
            if (cachedMsg instanceof String) {
                jsonResult = JSON.parseObject((String) cachedMsg);
            } else if (cachedMsg instanceof JSONObject) {
                jsonResult = (JSONObject) cachedMsg;
            }
        } catch (Exception e) {
            logger.error("cityId=" + cityId, e);
            if (null != cachedMsg) {
                logger.debug(cachedMsg, e);
            }
        }
        if (jsonResult == null) {
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("cityId", cityId);
            jsonResult = interfaceService.getJSONFromInterface(DATACENTER_BUILDING_NAME_BUY_CONDITION,
                    variables);
            if (jsonResult != null) {
                try {
                    if (jsonResult.get("error") != null) {
                        cacheClient.set(BUY_BUILD_CONDITION_KEY + cityId, DATE_CENTER_INTERFACE_EXPIRE,
                                jsonResult);
                    }
                } catch (Exception e) {
                    logger.error("cityId=" + cityId, e);
                }
            }
        }
        return jsonResult;
    }

    /*
     * NO.1 拼装搜索条件 jsonObject 获得接口的原始json
     */
    public static JSONObject transformerCondition(JSONObject jsonObject) {
        assert jsonObject != null;
        JSONObject returnJson = new JSONObject();
        JSONArray returnValues = new JSONArray();
        // price
        JSONObject priceObjectDefalut = (JSONObject) jsonObject.get("prices");
        JSONArray priceArray = (JSONArray) priceObjectDefalut.get("0");
        JSONObject priceObject = transformerCondition(priceArray, CONDITION_PRICES, CONDITION_PRICES_NAME,
                "id", "price");
        returnValues.add(priceObject);
        // 热点版块
        JSONObject areaObjectDefalut = (JSONObject) jsonObject.get("areas");
        // -1为默认，0为没绑定区域
        JSONArray areaArray = (JSONArray) areaObjectDefalut.get("-1");
        if (areaArray == null || areaArray.size() == 0) {
            areaArray = (JSONArray) areaObjectDefalut.get("0");
        }
        JSONObject areaObject = transformerCondition(areaArray, CONDITION_AREAS, CONDITION_AREAS_NAME,
                "areaId", "areaName");
        returnValues.add(areaObject);
        // 特色楼盘
        // JSONArray recommendArray = (JSONArray)jsonObject.get("recommend");
        JSONObject recommendObject = transformerCondition(null, CONDITION_RECOMENDS,
                CONDITION_RECOMENDS_NAME, null, null);
        returnValues.add(recommendObject);
        // 区域
        JSONArray districtArray = (JSONArray) jsonObject.get("districts");
        JSONObject districtObject = transformerCondition(districtArray, CONDITION_DISTRICTS,
                CONDITION_DISTRICTS_NAME, "districtId", "districtName");
        returnValues.add(districtObject);
        // 类型
        JSONArray typeArray = (JSONArray) jsonObject.get("types");
        JSONObject typeObject = transformerCondition(typeArray, CONDITION_TYPES, CONDITION_TYPES_NAME,
                "typeId", "typeName");
        returnValues.add(typeObject);
        // 轨道交通
        JSONArray looplineArray = (JSONArray) jsonObject.get("lines");
        JSONObject looplineObject = transformerCondition(looplineArray, CONDITION_LINES,
                CONDITION_LINES_NAME, "lineId", "lineName");
        returnValues.add(looplineObject);
        returnJson.put("data", returnValues);
        return returnJson;
    }

    /*
     * NO.2 拼装搜索条件 jSONArray 搜索条键 type 参数类型 name 条件显示中文名称 itemId 条件显示Id itemName 条件显示名称
     */
    public static JSONObject transformerCondition(JSONArray jSONArray, Integer type, String name,
            String itemId, String itemName) {
        JSONObject returnChild = new JSONObject();
        returnChild.put("cTypeId", type);
        returnChild.put("cTypeName", name);
        JSONArray returnChildValues = new JSONArray();
        if (type == CONDITION_RECOMENDS) {
            for (String key : recommendMap.keySet()) {
                JSONObject returnGrandchild = new JSONObject();
                returnGrandchild.put("qName", recommendMap.get(key));
                returnGrandchild.put("qStr", key);
                returnChildValues.add(returnGrandchild);
            }
        } else {
            if (jSONArray != null) {
                for (Object ob : jSONArray) {
                    JSONObject jsonOb = (JSONObject) ob;
                    JSONObject returnGrandchild = new JSONObject();
                    // 价格直接用值,并且需要转换形式
                    if (type == CONDITION_PRICES) {
                        String showName = (String) jsonOb.get(itemName);
                        // 价格排出0
                        if (!showName.equals("0")) {
                            showName = Utils.stringPattern(showName);
                            if (StringUtils.isNotBlank(showName)) {
                                returnGrandchild.put("qName", showName);
                                String qStr = (String) jsonOb.get(itemName);
                                qStr = Utils.stringPatternId(qStr);
                                returnGrandchild.put("qStr", qStr);
                                returnChildValues.add(returnGrandchild);
                            }
                        }
                    } else {
                        returnGrandchild.put("qName", jsonOb.get(itemName));
                        returnGrandchild.put("qStr", jsonOb.get(itemId));
                        returnChildValues.add(returnGrandchild);
                    }
                }
            }
        }
        returnChild.put("cList", returnChildValues);
        return returnChild;
    }

    /*
     * NO.3 组装json 调用接口返回buidingList
     */
    public JSONArray transformerCondition(String districtId, String typeId, String priceId, String areaId,
            String lineId, String recomendId) {
        JSONArray queryData = new JSONArray();

        queryData.add(putCondition(SearchType.PRICE, priceId == null || "".equals(priceId) ? null
                : splitCondition(priceId)[0]));
        queryData.add(putCondition(SearchType.HOT, areaId == null || "".equals(areaId) ? null
                : splitCondition(areaId)[0]));
        if (StringUtils.isNotBlank(recomendId)) {
            /**
             * modify by shixingtao 2013-10-15 防止判断的时候出现空指针
             */
            if ("xhx".equals(splitCondition(recomendId)[0])) {
                queryData.add(putCondition(SearchType.SPECIAL, SearchTypeSpecial.XHX));
            } else if ("dz".equals(splitCondition(recomendId)[0])) {
                queryData.add(putCondition(SearchType.SPECIAL, SearchTypeSpecial.DZLP));
            } else if ("kp".equals(splitCondition(recomendId)[0])) {
                queryData.add(putCondition(SearchType.SPECIAL, SearchTypeSpecial.ZXKP));
            } else if ("xf".equals(splitCondition(recomendId)[0])) {
                queryData.add(putCondition(SearchType.SPECIAL, SearchTypeSpecial.XF));
            }
        }
        queryData.add(putCondition(SearchType.DISTRICT, districtId == null || "".equals(districtId) ? null
                : splitCondition(districtId)[0]));
        queryData.add(putCondition(SearchType.TYPE, typeId == null || "".equals(typeId) ? null
                : splitCondition(typeId)[0]));
        queryData.add(putCondition(SearchType.SUBWAY, lineId == null || "".equals(lineId) ? null
                : splitCondition(lineId)[0]));
        return queryData;
    }

    // first 给service参数查询数据
    public static String[] splitCondition(String condition) {
        String[] conditions = {};
        if (StringUtils.isNotBlank(condition)) {
            condition = condition.trim();
            conditions = condition.split("@@");
        }
        return conditions;
    }

    // second 给前端搜索条件用以判断显示
    private static JSONObject putCondition(SearchType queryType, Object qSelect) {
        JSONObject conditionsChilds = new JSONObject();
        conditionsChilds.put("queryType", queryType);
        conditionsChilds.put("qSelect", qSelect);
        return conditionsChilds;
    }

    /*
     * NO.4 组装json buidingList页面展示使用 int index 0,id 1,name
     */
    public JSONObject transformerCondition(String districtId, String typeId, String priceId, String areaId,
            String lineId, String recomendId, int index) {
        JSONObject conditions = new JSONObject();
        String[] district = splitCondition(districtId);
        conditions.put("district" + (index == 0 ? "Id" : "Name"), district.length > index ? district[index]
                : "");

        String[] type = splitCondition(typeId);
        conditions.put("type" + (index == 0 ? "Id" : "Name"), type.length > index ? type[index] : "");
        String[] price = splitCondition(priceId);
        conditions.put("price" + (index == 0 ? "Id" : "Name"), price.length > index ? price[index] : "");
        String[] area = splitCondition(areaId);
        conditions.put("area" + (index == 0 ? "Id" : "Name"), area.length > index ? area[index] : "");
        String[] line = splitCondition(lineId);
        conditions.put("line" + (index == 0 ? "Id" : "Name"), line.length > index ? line[index] : "");
        String[] recomend = splitCondition(recomendId);
        conditions.put("recomend" + (index == 0 ? "Id" : "Name"), recomend.length > index ? recomend[index]
                : "");
        return conditions;
    }

    /**
     * 拼装楼盘的信息
     * 
     * @param list
     * @param city
     * @param pos 0 首页 1 帅选页面
     * @param entrancePara
     * @return
     */
    public List<EsProjInfoChild> switchBuildingParameter(List<EsProjInfoChild> list, DictCity city,int pos) {
        List<EsProjInfoChild> listPics = new ArrayList<EsProjInfoChild>();
        if (list != null) {
            for (EsProjInfo es : list) {
                EsProjInfoChild p = (EsProjInfoChild) es;
                // picUrl
                buildingService.genRealPhotoPath(p, true);
                // room
                if ((p.getRoom1MaxArea() != null && p.getRoom1MaxArea() < 1)
                        && (p.getRoom2MaxArea() != null && p.getRoom2MaxArea() < 1)
                        && (p.getRoom1LowArea() != null && p.getRoom1LowArea() < 1)
                        && (p.getRoom2LowArea() != null && p.getRoom2LowArea() < 1)) {
                        List<BuildingLayoutPhotoes> buildingLayoutPhotoes = buildingService
                                .getBuildingPicInfo(p.getGroupId());
                        for (BuildingLayoutPhotoes photos : buildingLayoutPhotoes) {
                            if (photos.getType() == 1 || photos.getType() == 2) {
                                List<ProjPhotoesExt> listPhoto = photos.getPhotoes();
                                float min = listPhoto.get(0).getBuildArea() == null ? 0 : listPhoto.get(0)
                                        .getBuildArea();
                                float max = listPhoto.get(0).getBuildArea() == null ? 0 : listPhoto.get(0)
                                        .getBuildArea();
                                for (ProjPhotoesExt photo : listPhoto) {
                                    if (photo.getBuildArea() == null) {
                                        photo.setBuildArea((float) 0);
                                    }
                                    if (photo.getBuildArea() > max) {
                                        max = photo.getBuildArea();
                                    }
                                    if (photo.getBuildArea() < min && photo.getBuildArea() > 0) {
                                        min = photo.getBuildArea();
                                    }

                                    if (min <= 1.0 && photo.getBuildArea() < max) {
                                        min = photo.getBuildArea();
                                    }
                                }
                                int a = (int) Math.ceil(min);
                                int b = (int) Math.ceil(max);
                                if (photos.getType() == 1) {
                                    String roo1Show = Utils.showRoom(a, b);
                                    p.setRoomOne(roo1Show);
                                }
                                if (photos.getType() == 2) {
                                    String roo2Show = Utils.showRoom(a, b);
                                    p.setRoomTwo(roo2Show);
                                }
                            }
                        }
                    
                } else if ((p.getRoom1LowArea() != null && p.getRoom1MaxArea() != null && p.getRoom1LowArea() > 0 && p
                        .getRoom1LowArea() >= p.getRoom1MaxArea())
                        || (p.getRoom2LowArea() != null && p.getRoom2MaxArea() != null && p.getRoom2LowArea() > 0 && p
                                .getRoom2LowArea() >= p.getRoom2MaxArea())) {
                    if(p.getRoom1LowArea()>= p.getRoom1MaxArea() && p.getRoom1LowArea()>0) {
                        p.setRoomOne(p.getRoom1LowArea() + "平米");
                    }
                    if(p.getRoom2LowArea()>= p.getRoom2MaxArea() && p.getRoom2LowArea()>0) {
                        p.setRoomTwo(p.getRoom2LowArea()+ "平米");
                    }
                    String roominfo = p.getRoomOne() + " " + p.getRoomTwo();
                    /**
                     * @author linfangwang
                     * 去掉截字优化
                     */
                    /*if(roominfo.length()>14) {
                    	roominfo = roominfo.substring(0, 14) + "...";
                    }*/
                    p.setRoomInfo(roominfo);
                }else {
                    String roo1Show = Utils.showRoom(p.getRoom1LowArea(), p.getRoom1MaxArea());
                    p.setRoomOne(roo1Show);
                    String roo2Show = Utils.showRoom(p.getRoom2LowArea(), p.getRoom2MaxArea());
                    p.setRoomTwo(roo2Show);
                }
                // price
               /*      
                if (p.getAvgPrice() == null || p.getAvgPrice() == 0) {
                    p.setAvgPriceShow("价格待定");
                } else {
                    p.setAvgPriceShow("<em>" + p.getAvgPrice() + "</em>元/平米");
                }*/
                // 开盘时间
                String dateTime = Utils.subSaleDateStr(p.getSaleDateDetail());
                if (dateTime.length() > 30) {
                    dateTime = dateTime.substring(0, 30) + "...";
                }
                p.setSaleDateDetail(dateTime);
                StringBuilder sb = new StringBuilder();
                String url = "";
                url = sb.append("/").append(city.getCityPinyinAbbr()).append("/loupan/")
                            .append(p.getGroupId()).append("/").toString();
                p.setBuildingUrl(url);
                // 楼盘状态
                // p.setStatusShow();
                // 楼盘地址
                /*if(pos==1) {
                if (p.getProjAddress().length() > 9) {
                    p.setProjAddress(p.getProjAddress().substring(0, 9) + "...");
                }
                } else {
                    if (p.getProjAddress().length() >14) {
                        p.setProjAddress(p.getProjAddress().substring(0, 14) + "...");
                    }
                }*/
                // 楼盘名称
//                if (p.getProjName().length() > 7) {
//                    p.setProjName(p.getProjName().substring(0, 7) + "...");
//                }
                // 打折信息
                if ("待定".equals(p.getDiscount().trim()) || "暂无".equals(p.getDiscount().trim())
                        || "无".equals(p.getDiscount().trim()) || "0".equals(p.getDiscount().trim())) {
                    p.setDiscount("");
                }
                listPics.add(p);
            }
        }
        listPics = buildingService.setBuildingListPrice(listPics);
        return listPics;
    }

    // 拼装cookie Value
    public String transformCookieValue(JSONObject id, JSONObject name) {
        StringBuilder result = new StringBuilder();
        Set<String> set = id.keySet();
        Iterator<String> it = set.iterator();
        for (Iterator<String> i = it; it.hasNext();) {
            String key = i.next();
            String value = id.getString(key);
            if (StringUtils.isNotBlank(value)) {
                String idCookie = transformConditionForCookieId(key);
                result.append(idCookie).append("=").append(value).append("@@")
                        .append(name.getString(key.replaceAll("Id", "Name"))).append("&");
            }
        }
        return result.toString();
    }

    private static String transformConditionForCookieId(String sType) {
        return conditionMap.get(sType);
    }

    /**
     * 获取首页各个模块的搜索条件
     * 
     * @param cityId
     * @param pageNo
     * @param pageSize
     * @param sType
     * @param queryStr
     * @return
     */
    public SearchCondition getHomeHouseCondition(int cityId, int pageNo, int pageSize, String sType,
            String queryStr) {
        SearchCondition condition = new SearchCondition();
        List<QueryData> queryDataList = new ArrayList<QueryData>();
        QueryData data = new QueryData();
        data.setqSelect(sType);
        data.setQueryType(SearchType.SPECIAL);
        queryDataList.add(data);
        condition.setCityId(cityId);
        condition.setPageNo(pageNo);
        condition.setPageSize(pageSize);
        condition.setQueryData(queryDataList);
        condition.setQueryStr(queryStr);
        // condition.setConditionsName(conditionName);
        condition.setSaleStatus(SaleStatus.ONSALE);
        return condition;
    }

}
