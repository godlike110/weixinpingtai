package cn.focus.dc.focuswap.service;

import cn.focus.dc.building.model.es.EsProjInfo;
import cn.focus.dc.es.EsHandleService;
import cn.focus.dc.focuswap.model.DictProjTypeExtNew;
import cn.focus.dc.focuswap.model.EsProjInfoChild;
import cn.focus.dc.focuswap.model.QueryData;
import cn.focus.dc.focuswap.model.SearchCondition;

import io.searchbox.core.search.sort.Sort;
import io.searchbox.core.search.sort.Sort.Sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.common.joda.time.DateTime;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SearchService {
    
    private static Log log = LogFactory.getLog(SearchService.class);

    protected Log logger = LogFactory.getLog(SearchService.class);

    // ES服务索引名
    public static final String INDEX_TYPE = "esProj";

    public static enum SearchType {
        PRICE, HOT, SPECIAL, DISTRICT, TYPE, SUBWAY
    }

    public static enum SearchTypeSpecial {
        // 打折楼盘
        DZLP,
        // 本月开盘
        //BYKP,
        // 小户型
        XHX,
        // 现房
        XF,
        // 最新开盘
        ZXKP,
        //城市豪宅
        CSHZ,
        //三口之家
        SKZJ,
        //婚房
        HF,
        //公园旁边
        GYPB,
        //教育社区
        JYSQ,
        //低总价
        DZJ
    }
    
    //楼盘销售状态
    public static enum SaleStatus {
        //在售
        ONSALE,
        //待售
        ONWAIT,
        //尾盘
        ONREMAIN,
        //售罄
        ONOVER
    }
    
    //住宅类型
    public static enum HouseType {
        //住宅
        住宅,
        //别墅
        别墅,
        //保障房
        保障房,
        //综合体
        综合体
    }


    @Autowired
    private EsHandleService client;
    
    @Autowired
    private BuildingSearchService buildingSearchService;
    /**
     * 楼盘搜索auto-complete功能
     * 
     * @param keywords 搜索关键字
     * @param cityId 城市ID
     * @param limits 返回结果数量限制
     * @return NameSuggestVO 列表
     */
    public List<EsProjInfo> projNameSearch(String keywords, int cityId, int limits) {
        List<EsProjInfo> ret = Collections.emptyList();

        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        queryBuilder.must(QueryBuilders.termQuery("cityId", cityId));

        // isTemp == 2 代表发布状态
        queryBuilder.must(QueryBuilders.termQuery("isTemp", 2));
        // isDel == 0 没有删除
        queryBuilder.must(QueryBuilders.termQuery("isDel", 0));
        // saleStatus != 3 代表还有未售完的楼盘
        queryBuilder.mustNot(QueryBuilders.termQuery("saleStatus", 3));

        if (StringUtils.isNotBlank(keywords)) {
            // 楼盘名关键字查询
            QueryBuilder keywordsQb = QueryBuilders.multiMatchQuery(keywords, "projName^2", "projNameOther");
            queryBuilder.must(keywordsQb);
            ret = searchBase(EsProjInfo.class, INDEX_TYPE, queryBuilder, null, 1, limits);
        }
        return ret;
    }

    /**
     * 根据楼盘groupIds的列表，通过搜索获取楼盘信息列表
     */
    public List<EsProjInfoChild> projGroupIdSearch(List<Integer> groupIds) {
        List<EsProjInfoChild> ret = Collections.emptyList();
        if (null != groupIds && groupIds.size() > 0) {
            QueryBuilder qb = QueryBuilders.inQuery("groupId", groupIds);
            // TODO , 返回结果暂时限定在100，因为需求不会大于100
            ret = searchBase(EsProjInfoChild.class, INDEX_TYPE, qb, null, 1, 100);
        }
        return ret;
    }

    
    /**
     * 根据搜索条件，返回楼盘搜索结果 详细见wiki文档 : http://10.10.90.156/wiki/index.php/Home/search/search
     * 
     * @param searchConditions 表示的搜索条件
     * @return 搜索结果对象
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> projSearch(SearchCondition searchConditions) {

        Map<String, Object> ret = new HashMap<String, Object>();

        int pageSize = searchConditions.getPageSize();
        if (pageSize == 0) {
            pageSize = 10;
        }
        int pageNo = searchConditions.getPageNo();
        if (pageNo == 0) {
            pageNo = 1;
        }
        int cityId = searchConditions.getCityId();
        String queryStr = searchConditions.getQueryStr();
        List<QueryData> conditionList = searchConditions.getQueryData();
        List<Integer> groupIds = searchConditions.getGroupIds();

        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        queryBuilder.must(QueryBuilders.termQuery("cityId", cityId));

        // isTemp == 2 代表发布状态
        queryBuilder.must(QueryBuilders.termQuery("isTemp", 2));
        // isDel == 0 没有删除
        queryBuilder.must(QueryBuilders.termQuery("isDel", 0));
        SaleStatus saleStatusStr = searchConditions.getSaleStatus();
        if (null != saleStatusStr){            
            queryBuilder.must(QueryBuilders.termQuery("saleStatus", saleStatusStr.ordinal()));
        }

        if(searchConditions.isNotEnsureHouse()){
            List<Integer> args = new ArrayList<Integer>();
            args.add(4);
            args.add(30);
            args.add(31);
            args.add(32);
            queryBuilder.mustNot(QueryBuilders.inQuery("projTypeList", args));
        }
        
        if(searchConditions.isNotSelloutHouse()){
            queryBuilder.mustNot(QueryBuilders.termQuery("saleStatus", 3));
        }
        
        List<Sort> sortList = new ArrayList<Sort>();
        if (StringUtils.isNotBlank(queryStr)) {
            // 楼盘名关键字查询
            QueryBuilder keywordsQb = QueryBuilders.multiMatchQuery(queryStr, "projName^4",
                    "projNameOther^2", "projAddress");
            queryBuilder.must(keywordsQb);

        } else if (null != groupIds) {
          /**
           * added by rogantian @2014.08.07 修复楼盘搜索的时候会将已停用的楼盘搜出来的bug  
           */
            queryBuilder.must(QueryBuilders.inQuery("groupId", groupIds));
        } else {
            // 组合条件查询
            if (null != conditionList) {
                for (QueryData obj : conditionList) {
                    String qStr =  obj.getQSelect();
                    SearchType sType = obj.getQueryType();
                    QueryBuilder qb = getQueryByType(sType, qStr,cityId);
                    if (null != qb) {
                        queryBuilder.must(qb);
                    }
                    // 默认按开盘时间逆序
                }
            }
            /**
             * bug修复，在输入关键字查询楼盘的时候不需要这两个排序字段
             */
            Sort sortStatus = new Sort("saleStatus", Sorting.ASC);
            Sort sortTime = new Sort("updateTime", Sorting.DESC);
            sortList.add(sortStatus);
            sortList.add(sortTime);
        }

        int total = 0;
        List<EsProjInfoChild> resultList = Collections.emptyList();

        Map<String, Object> searchResult = searchBaseWithCount(EsProjInfoChild.class, INDEX_TYPE,
                queryBuilder, sortList, pageNo, pageSize);

        if (null != searchResult) {
            try {
                Object totalObject = searchResult.get("total");
                if (null != totalObject) {
                    total = (Integer) totalObject;
                }

                Object dataList = searchResult.get("data");
                if (null != dataList) {
                    resultList = (List<EsProjInfoChild>) dataList;
                }
            } catch (Exception e) {
                logger.error("", e);
                total = 0;
            }
            // 是否还有下一页的属性 hasNext
            boolean hasNext = !((pageNo * pageSize) >= total);

            ret.put("buildingList", resultList);
            ret.put("total", total);
            ret.put("pageSize", pageSize);
            ret.put("pageNo", pageNo);
            ret.put("hasNext", hasNext);
        }
        return ret;
    }
    

    @SuppressWarnings("unchecked")
    private QueryBuilder getQueryByType(SearchType sType, String qStr,Integer cityId) {
        QueryBuilder ret = null;
        if (sType != null && StringUtils.isNotBlank(qStr)) {
            switch (sType) {
            case PRICE:
                ret = getRangeQuery(qStr);
                break;
            case HOT:
                ret = QueryBuilders.inQuery("areaIdList", qStr);
                break;
            case SPECIAL:
                ret = getSpecialQuery(qStr);
                break;
            case DISTRICT:
                ret = new TermQueryBuilder("districtId", Long.valueOf(qStr));
                break;
            case TYPE:
                
                Map<SearchType, Object> retMap = buildingSearchService.getConditions(cityId, false);
                List<DictProjTypeExtNew> types = (List<DictProjTypeExtNew>)retMap.get(SearchType.TYPE);
                List<Integer> args = null;
                for(DictProjTypeExtNew dpt : types){
                    String id = dpt.getTypeId().toString();
                    if(id.equals(qStr)){
                        args = dpt.getOldIds();
                    }
                }
                ret = QueryBuilders.inQuery("projTypeList", args);
                //ret = QueryBuilders.inQuery("projTypeList", qStr);
                break;
            case SUBWAY:
                ret = QueryBuilders.inQuery("lineIdList", qStr);
                break;

            default:
                break;
            }
        }
        return ret;
    }

    private static QueryBuilder getRangeQuery(String qStr) {
        QueryBuilder ret = null;
        String[] limits = StringUtils.split(qStr, "-");
        try {
            if (null != limits && limits.length == 2) {
                int from = Integer.valueOf(limits[0]);
                int to = Integer.valueOf(limits[1]);
                ret = new RangeQueryBuilder("avgPrice").from(from).to(to);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return ret;
    }

    private static QueryBuilder getSpecialQuery(String qStr) {
        QueryBuilder ret = null;
        if (null != qStr) {
            SearchTypeSpecial sp = SearchTypeSpecial.valueOf(qStr);
            if (null != sp) {
                switch (sp) {
                case DZLP:
                    ret = new TermQueryBuilder("hasDiscount", 1);
                    break;
                // case BYKP:
                // // 获取本月第一天时间戳
                // DateTime dateTime = new DateTime();
                // long firstDayLong = dateTime.withDayOfMonth(1).getMillis();
                // // 获取下月第一天的时间戳
                // long firstDayNextMonthLong = dateTime.plusMonths(1).withDayOfMonth(1).getMillis();
                // ret = new RangeQueryBuilder("saleDateList").gte(firstDayLong).lt(firstDayNextMonthLong);
                // break;
                case XHX:
                    BoolQueryBuilder qb = new BoolQueryBuilder();
                    qb.must(new TermQueryBuilder("hasSmallHouse", 1)).must(
                            new TermQueryBuilder("hasSmallHousePic", 1));
                    ret = qb;
                    break;
                case XF:
                    ret = new TermQueryBuilder("ifPresent", 1);
                    break;
                case ZXKP:
                    // 获取本月第一天时间戳
                    DateTime dateTime = new DateTime();
                    long firstDayLong = dateTime.withDayOfMonth(1).getMillis();
                    // 获取下月第一天的时间戳
                    long firstDayNextMonthLong = dateTime.plusMonths(1).withDayOfMonth(1).getMillis();
                    ret = new RangeQueryBuilder("saleDateList").gte(firstDayLong).lt(firstDayNextMonthLong);
                    break;
                case CSHZ:
                    ret = new RangeQueryBuilder("unitPrice").gt(40000);
                    break;
                case SKZJ:
                    BoolQueryBuilder qb2 = new BoolQueryBuilder();
                    BoolQueryBuilder area = new BoolQueryBuilder();           
                    area.should(new RangeQueryBuilder("room3LowArea").gt(0));
                    area.should(new RangeQueryBuilder("room3MaxArea").gt(0));
                    area.should(new RangeQueryBuilder("room4LowArea").gt(0));
                    area.should(new RangeQueryBuilder("room4MaxArea").gt(0));
                    area.should(new RangeQueryBuilder("dudongLowArea").gt(0));
                    area.should(new RangeQueryBuilder("dudongMaxArea").gt(0));
                    area.should(new RangeQueryBuilder("lianpaiLowArea").gt(0));
                    area.should(new RangeQueryBuilder("lianpaiMaxArea").gt(0));
                    area.should(new RangeQueryBuilder("diepinLowArea").gt(0));
                    area.should(new RangeQueryBuilder("diepinMaxArea").gt(0));
                    List<Integer> args = new ArrayList<Integer>();
                    args.add(3);
                    area.should(QueryBuilders.inQuery("projTypeList",args));
                    qb2.must(area);
                    ret = qb2;
                    break;
                case HF:
                    BoolQueryBuilder qb3 = new BoolQueryBuilder();
                    BoolQueryBuilder two = new BoolQueryBuilder();            
                    two.should(new RangeQueryBuilder("room2LowArea").lt(90));
                    two.should(new RangeQueryBuilder("room2MaxArea").lt(90));
                    two.mustNot(QueryBuilders.termQuery("room2LowArea",0));
                    two.mustNot(QueryBuilders.termQuery("room2MaxArea",0));            
                    BoolQueryBuilder three = new BoolQueryBuilder();            
                    three.should(new RangeQueryBuilder("room3LowArea").lt(90));
                    three.should(new RangeQueryBuilder("room3MaxArea").lt(90));
                    three.mustNot(QueryBuilders.termQuery("room3LowArea",0));
                    three.mustNot(QueryBuilders.termQuery("room3MaxArea",0));          
                    BoolQueryBuilder areaHF = two.should(three);
                    qb3.must(areaHF.must(new RangeQueryBuilder("totalPrice").gt(160)));
                    ret = qb3;
                    break;
                case JYSQ:
                    ret = new TermQueryBuilder("isEdu", 1);
                    break;
                case GYPB:
                    ret = new TermQueryBuilder("sidePark", 1);
                    break;
                case DZJ:
                    ret = new RangeQueryBuilder("totalPrice").lt(1000000);
                default:
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * 获取搜索结果
     */
    private <T> List<T> searchBase(Class<T> classType, String indexType, QueryBuilder query, Sort sort,
            int pageNo, int pageSize) {
        List<T> ret = Collections.emptyList();
        try {
            ret = client.searchBase(classType, indexType, query, sort, pageNo, pageSize);
        } catch (Exception e) {
            log.error("", e);
        }
        return ret;
    }

    /**
     * 获取带有总数的搜索结果
     */
    private <T> Map<String, Object> searchBaseWithCount(Class<T> classType, String indexType,
            QueryBuilder query, List<Sort> sort, int pageNo, int pageSize) {
        Map<String, Object> ret = Collections.emptyMap();
        try {
            ret = client.searchWithCount(classType, indexType, query, pageNo, pageSize,sort);
        } catch (Exception e) {
            log.error("", e);
        }
        return ret;
    }

}
