package cn.focus.dc.focuswap.service;

import cn.focus.dc.es.EsHandleService;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.config.AppConstants.Xinwen;
import cn.focus.dc.news.model.es.EsNews;
import io.searchbox.core.search.sort.Sort;
import io.searchbox.core.search.sort.Sort.Sorting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 房产新闻搜索
 * @author rogantian
 * @date 2014-4-24
 * @email rogantianwz@gmail.com
 */
@Service
public class XinWenSearchService {

    private static Log logger = LogFactory.getLog(XinWenSearchService.class);
    
    public static final String INDEX_TYPE = "esNews";
    
    @Autowired
    private EsHandleService esHanldService;
    
    public static enum SearchType {
        
        
        //要闻
        TOP {
            int getClassId() {
                return 0;
            }
            
            public String getTypeName() {
                return "";
            }
            
            public String getChineseName(){
                return "要闻";
            }
        },
        //市场
        MARKET {
            int getClassId() {
                return 449;
            }
            
            public String getTypeName() {
                return "shichang";
            }
            
            public String getChineseName(){
                return "市场";
            }
        },
        //本地,由各地编辑确认各地使用的小类ID，这里先默认使用“本地”
        LOCAL {
            private String chineseName = "本地";
            
            private int classId = 451;
            
            int getClassId() {
                return classId;
            }

            public String getTypeName() {
                return "bendi";
            }
            
            public String getChineseName(){
                return chineseName;
            }
            
            public void setChineseName(int cityId){
                String map = Xinwen.localNameMap.get(cityId);
                if(StringUtils.isNotBlank(map)){
                    chineseName = map;
                }
            }
            
            public void setClassId(int cityId){
                Integer map = Xinwen.localIdMap.get(cityId);
                if(null != map){
                    classId = map;
                }else{
                    classId = 451;
                }
            }
            
        },
        // 政策
        POLICY {
            int getClassId() {
                return 507;
            }
            
            public String getTypeName() {
                return "zhengce";
            }
            
            public String getChineseName(){
                return "政策";
            }
        },
        // 观点
        POINT {
            int getClassId() {
                return 498;
            }
            
            public String getTypeName() {
                return "guandian";
            }
            
            public String getChineseName(){
                return "观点";
            }
        },
        // 更多
        MORE {
            int getClassId() {
                return 451;
            }
            
            public String getTypeName() {
                return "more";
            }
            
            public String getChineseName(){
                return "更多";
            }
        };
        
        //分类ID
        abstract int getClassId();
        
        public abstract String getTypeName();
        
        public abstract String getChineseName();
       
        public void setChineseName(int cityId) {
        }
        
        public void setClassId(int cityId) {
            
        }
    }
    
    /**
     * 新闻大的频道
     * @author rogantian
     * @date 2014-4-25
     * @email rogantianwz@gmail.com
     */
    public static enum XinWenCategoryEnum {
        //新闻频道
        NEWS {int getValue() {return 32;}},
        //资讯频道
        INFO {int getValue() {return 34;}},
        //企业频道
        COMP {int getValue() {return 50;}};
        
        abstract int getValue();
    }
    
    public Map<String, Object> xinWenSearch(XinWenSearchCondition condition) {
        Map<String, Object> ret = new HashMap<String, Object>();
        
        if (null == condition) {
            return ret;
        }
        
        int pageNo = condition.getPageNo();
        if (pageNo <= 0) {
            pageNo = 1;
        }
        
        int pageSize = condition.getPageSize();
        if (pageSize <= 0) {
            pageSize = 10;
        }
        
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        
        // status = 1 表示发布状态
        queryBuilder.must(QueryBuilders.termQuery("status", 1));
        
        // newsType = 2 表示视频新闻
        queryBuilder.mustNot(QueryBuilders.termQuery("newsType", 2));
        
        //queryBuilder.must(QueryBuilders.termQuery("newsId", 5048663));
        
        //是否移动要闻
        if(condition.getIsMobileNews() == 1){
            queryBuilder.must(QueryBuilders.termQuery("isMobileImportant", condition.getIsMobileNews()));
        }
        
        int cityId = condition.getCityId();
        if (cityId > 0) {
            queryBuilder.must(QueryBuilders.termQuery("cityId", cityId));
        }
        
        List<XinWenQueryData> queryDatas = condition.getQueryDatas();
        if (null != queryDatas) {
            
            for (XinWenQueryData queryData : queryDatas) {
                QueryBuilder qb = getQuery(queryData,cityId);
                if (null != qb) {
                    queryBuilder.must(qb);
                }
            }
        }
        
        List<Sort> sortList = new ArrayList<Sort>();
        Sort sortTime = new Sort("showTime", Sorting.DESC);
        sortList.add(sortTime);
        
        try {
            ret = esHanldService.searchWithCount(EsNews.class, INDEX_TYPE, queryBuilder, pageNo, pageSize, sortList);
        } catch (Exception e) {
            logger.error("", e);
        }
        
        return ret;
    }
    
    private QueryBuilder getQuery(XinWenQueryData queryData,int cityId) {
        QueryBuilder ret = null;
        if (null != queryData && null != queryData.getSearchType()) {
            switch (queryData.getSearchType()) {
            case TOP:
                break;
            case MARKET:
                BoolQueryBuilder qbMarket = new BoolQueryBuilder();
                qbMarket.must(new TermQueryBuilder("newsClassId", SearchType.MARKET.getClassId()));
                ret = qbMarket;
                break;
            case LOCAL:
                SearchType.LOCAL.setChineseName(cityId);
                SearchType.LOCAL.setClassId(cityId);
                int localId = SearchType.LOCAL.getClassId();//设置一个默认值
                Object searchVal = queryData.getSearchValue();
                if (null != searchVal) {
                    try {
                        localId = (Integer)(queryData.getSearchValue());
                    } catch (NumberFormatException e) {
                        logger.error("", e);
                    }
                }
                BoolQueryBuilder qbLocal = new BoolQueryBuilder();
                qbLocal.must(new TermQueryBuilder("newsClassId", localId));
                ret = qbLocal;
                break;
            case POLICY:
                BoolQueryBuilder qbPolicy = new BoolQueryBuilder();
                qbPolicy.must(new TermQueryBuilder("newsClassId", SearchType.POLICY.getClassId()));
                ret = qbPolicy;
                break;
            case POINT:
                BoolQueryBuilder qbPoint = new BoolQueryBuilder();
                qbPoint.must(new TermQueryBuilder("newsClassId", SearchType.POINT.getClassId()));
                ret = qbPoint;
                break;
            case MORE:
                int localIdMore = SearchType.LOCAL.getClassId();//设置一个默认值
                Object searchValMore = queryData.getSearchValue();
                if (null != searchValMore) {
                    try {
                        localIdMore = (Integer)(queryData.getSearchValue());
                    } catch (NumberFormatException e) {
                        logger.error("", e);
                    }
                }
                BoolQueryBuilder qbMore = new BoolQueryBuilder();
                qbMore.mustNot(QueryBuilders.termQuery("newsClassId", localIdMore))
                    .mustNot(QueryBuilders.termQuery("newsClassId", SearchType.MARKET.getClassId()))
                    .mustNot(QueryBuilders.termQuery("newsClassId", SearchType.POLICY.getClassId()))
                    .mustNot(QueryBuilders.termQuery("newsClassId", SearchType.POINT.getClassId()));
                ret = qbMore;
                break;
            default:
                break;
            }
        }
        return ret;
    }
    
    public static class XinWenSearchCondition {
        
        private int pageNo;
        
        private int pageSize;
        
        private int cityId;
        
        /*
         * 是否移动要闻
         */
        private int isMobileNews;
        
        private List<XinWenQueryData> queryDatas;
        
        

        public int getIsMobileNews() {
            return isMobileNews;
        }

        public void setIsMobileNews(int isMobileNews) {
            this.isMobileNews = isMobileNews;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public List<XinWenQueryData> getQueryDatas() {
            return queryDatas;
        }

        public void setQueryDatas(List<XinWenQueryData> queryDatas) {
            this.queryDatas = queryDatas;
        } 
        
    }
    
    public static class XinWenQueryData {
        
        private SearchType searchType;
        
        private Object searchValue;

        public XinWenQueryData(SearchType searchType, Object searchValue) {
            super();
            this.searchType = searchType;
            this.searchValue = searchValue;
        }

        public XinWenQueryData(SearchType searchType) {
            super();
            this.searchType = searchType;
        }

        public SearchType getSearchType() {
            return searchType;
        }

        public void setSearchType(SearchType searchType) {
            this.searchType = searchType;
        }

        public Object getSearchValue() {
            return searchValue;
        }

        public void setSearchValue(Object searchValue) {
            this.searchValue = searchValue;
        }
    }
}
