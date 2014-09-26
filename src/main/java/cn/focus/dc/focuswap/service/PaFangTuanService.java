package cn.focus.dc.focuswap.service;

import static cn.focus.dc.focuswap.config.AppConstants.*;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.LineInfo;
import cn.focus.dc.focuswap.model.TipsInfo;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.Pafangtuan;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.PafangtuanTips;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.PftBuildModel;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.PftCityInfo;
import cn.focus.dc.focuswap.utils.DateUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.Resource;

import net.paoding.rose.web.Invocation;
import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 爬房团数据相关service
 * @author rogantian
 * @date 2013-12-17
 * @email rogantianwz@gmail.com
 */
@Service
public class PaFangTuanService {

    protected Log logger = LogFactory.getLog(PaFangTuanService.class);
    
    @Resource(name="memcachedClient")
    private MemcachedClient cacheClient;
    
    @Autowired
    private InterfaceService interfaceService;
    
    //报名截止状态
    private static final int SignUpStatusNormal = 1;//报名正常
    //地图缩放级别
    private static final int ZOOM=9;
    
    //从api加载爬房团数据的条数
    private static final int PAFANGTUAN_LOAD_SIZE = 60;
    
    //加载当前往后推多少个月的爬房团数据
    private static final int PAFANGTUAN_LOAD_AHEAD_MONTH = 2;
    
    @Autowired
    private XinFangCommonApiService xinFangCommonApiService; 
    
    @Autowired
    private CacheHandlerService cacheHandler;
    
    public static final String HAS_NEXT = "hasNext";
    
    public static final String DATA_LIST = "list";
    
        //为了减少从缓存获取数据的次数，爬房团的总数使用这个特殊的key来存储在同一个map中,2114-07-03。
    public static final Date SPECIAL_DATE_FOR_COUNT = new Date(4560049214199L);
    
    
    /**
     * 获取离当前最近的2天的爬房团列表，首页爬房团模块和焦点图模块使用
     * @param cityId
     * @return map的key为日期，value为该日期的列表。</br>
     * 注意：该接口可能返回当前日期前一天的数据，比如23:30缓存的今天和明天的列表，缓存时间为2小时，在第二天的0-1:30这段时间取的数据中就会包含头一天的过期数据，业务使用方自己来处理这个问题。</br>
     * 建议使用该接口返回的爬房团列表中的activeDateRel字段，而不是activeDate字段。</br>
     * 返回的值可能是null，也可能是一个空集合，业务方需要判断。</br>
     * 返回的值中有一个key特别大(参考 SPECIAL_DATE_FOR_COUNT)的数据，该数据为爬房团总数。
     */
    public Map<Date, Object> getLatest2DaysList(int cityId) {
        if (cityId <= 0) {
            return null;
        }
        StringBuilder keyBuilder = new StringBuilder(CK_PANGTUAN_2_DAYS_MAP);
        keyBuilder.append(cityId);
        
        Map<Date, Object> ret = null;
        ret = cacheHandler.getCacheValue(keyBuilder.toString(), Map.class);
        
        
        if (null == ret) {
            List<Pafangtuan> originList = loadPafangtuanData(cityId);
            if (null == originList || originList.size() < 1) {
            	
            	/**
            	 * @edit linfangwang
            	 * 如果从后台获取不到爬房团数据，则设置缓存为空
            	 */
            	cacheHandler.setCache(keyBuilder.toString(), CE_PANGTUAN_EMPTY_COLLECTION, MapUtils.EMPTY_MAP);
            	
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Map<Date, List<Pafangtuan>> bloatedMap = new HashMap<Date, List<Pafangtuan>>();
            int pftCount = 0;
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, -1);
            Date lastDay = c.getTime();
            for(Pafangtuan pft : originList) {
            	
            	
            	int applyRemainDays = pft.getApplyRemainedDays();
            	if(applyRemainDays <= 0) {
            		continue;
            	}
            	
          
                
                String activeDateStr = pft.getActiveDate();
                if (StringUtils.isBlank(activeDateStr)) {
                    continue;
                }
                
                Date activeDate = null;
                try {
                    activeDate = sdf.parse(activeDateStr);
                } catch (ParseException e) {
                    logger.error("", e);
                }
                
                if (null == activeDate) {
                    continue;
                }
                
                pft.setActiveDateRel(activeDate);
                
                if (!activeDate.after(lastDay)){
                    continue;
                }
                
                
                pftCount ++;
                
                //使用第一个相关楼盘的图片作为爬房团的图片
                List<PftBuildModel> builds = pft.getBuilds();
                if (null != builds && builds.size() > 0) {
                    pft.setPic(builds.get(0).getMainPic());
                    pft.setBuildNum(builds.size());
                }
                
                List<Pafangtuan> dayList = bloatedMap.get(activeDate);
                if (null == dayList) {
                    dayList = new LinkedList<Pafangtuan>();
                }
                dayList.add(pft);
                bloatedMap.put(activeDate, dayList);
            }
            
            Set<Date> keySet = bloatedMap.keySet();
            if (null != keySet && keySet.size() > 0) {
                TreeSet<Date> sortedKeySet = new TreeSet<Date>();
                sortedKeySet.addAll(keySet);
                
                ret = new TreeMap<Date, Object>();
                Date firstDate = sortedKeySet.pollFirst();
                Date secondDate = sortedKeySet.pollFirst();
                if (null != firstDate) {
                    ret.put(firstDate, bloatedMap.get(firstDate));
                }
                
                if (null != secondDate) {
                    ret.put(secondDate, bloatedMap.get(secondDate));
                }
                
                ret.put(SPECIAL_DATE_FOR_COUNT, pftCount);
                
                cacheHandler.setCache(keyBuilder.toString(), CE_PANGTUAN_2_DAYS_MAP, ret);
            } else {
            	/**
            	 * @edit linfangwang
            	 * 没有符合要求的爬房团
            	 */
            	cacheHandler.setCache(keyBuilder.toString(), CE_PANGTUAN_EMPTY_COLLECTION, MapUtils.EMPTY_MAP);
            	return null;
            }
            
        }
        return ret;
    }
    
    /**
     * 获取与某个groupId关联的爬房团列表。</br>
     * 说明：先从缓存中获取该城市的爬房团包含的grouId集合，查看该集合中是否包含此groupId，如果没有则直接返回（这么做的原因是有爬房团活动的groupId占该城市的比例很小）</br>
     * 如果包含则从缓存中再次获取爬房团列表遍历该列表查找包含该groupId的爬房团集合。
     * @param groupId
     * @param cityId
     * @return 返回的值可能是null，也可能是一个空集合，业务方需要判断。
     */
    @SuppressWarnings("unchecked")
    public List<Pafangtuan> getPafangtuanListByGroupId(int cityId, int groupId) {
        if (cityId <= 0 || groupId <= 0) {
            return null;
        }
        
        StringBuilder keyBuilder = new StringBuilder(CK_PAFANGTUAN_GROUPIDS);
        keyBuilder.append(cityId);
        
        Set<Integer> cachedSet = null;
        cachedSet = cacheHandler.getCacheValue(keyBuilder.toString(), Set.class);
        
        //如果取到了缓存，而且缓存中不包含该groupId则直接返回null（这种情况所占的比例为大多数，所以把这部分判断放在最前边）
        if (null != cachedSet) {
            if (!cachedSet.contains(groupId)) {
                return null;
            }
        }
        
        //TODO：可以在这个函数中传一个limit参数，根据limit参数来控制在满足limit了之后跳出循环。
        
        //如果没取到缓存或者取到了缓存但是缓存中包含该groupId则获取该城市的所有爬房团列表，然后遍历该列表，查找包含给groupId的爬房团。
        Set<Integer> groupIds = new HashSet<Integer>();
        List<Pafangtuan> ret = new ArrayList<Pafangtuan>();
        //假设每个城市的爬房团线路不会超过100条
        Map<String,Object> pftListRet = getPafangtuanList(cityId, 1, 100);
        List<Pafangtuan> pftList = (List<Pafangtuan>) pftListRet.get(DATA_LIST);
        if (null != pftList) {
            for (Pafangtuan pft : pftList) {
                //这里先判断该爬房团的状态是否正确，如果不正确就可以直接跳过
//                int singUpStatus = pft.getSignUpStatus();
//                if (SignUpStatusNormal != singUpStatus) {
//                    continue;
//                }
            	/**
				 * @author linfangwang
				 * signUpStatus:状态有误，使用 applyRemainDays 判断，
				 */
            	int applyRemainDays = pft.getApplyRemainedDays();
            	if(applyRemainDays <= 0) {
            		continue;
            	}
                
                List<PftBuildModel> builds = pft.getBuilds();
                if (null != builds && builds.size() > 0) {
                    for (PftBuildModel build : builds) {
                        Integer groupIdOfBuild = build.getGroupId();
                        //buildGeted表示在循环内是否已经验证了该groupId已经在楼盘列表内了。
                        boolean buildGeted = false;

                        //如果缓存集合为空，则需要在遍历是生成该集合，然后最后将其缓存起来。
                        if (null == cachedSet) {
                            if (null != groupIdOfBuild) {
                                groupIds.add(groupIdOfBuild);
                                if (!buildGeted && groupIdOfBuild.intValue() == groupId) {
                                    ret.add(pft);
                                    buildGeted = true;
                                }
                            }
                        } else {
                            if (null != groupIdOfBuild && groupIdOfBuild.intValue() == groupId) {
                                ret.add(pft);
                                break;
                            }
                        }
                    }
                }
            }
        }
        
        //将集合缓存到memcached，有可能该集合的大小为0，这是没有问题的。大小为0的集合可以用来快速判断某个groupId是否在爬房团列表内。
        if (null == cachedSet) {
            cacheHandler.setCache(keyBuilder.toString(), CE_PAFANGTUAN_GROUPIDS, groupIds);
        }
        
        return ret;
    }
    
    /**
     * 分页获取爬房团列表，按时间顺序排列
     * @param cityId
     * @param pageNo
     * @param pageSize
     * @return 返回的map中有两个key，一个是HAS_NEXT表示是否还有下一条数据，默认为false</br>
     *另一个是DATA_LIST表示分页数据，该列表可能是null，也可能是一个空集合，业务方自己判断
     */
    public Map<String, Object> getPafangtuanList(int cityId, int pageNo, int pageSize) {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put(HAS_NEXT, false);
        if (cityId <= 0) {
            return ret;
        }
        
        if (pageNo <= 0) {
            pageNo = 1;
        }
        
        if (pageSize <= 0) {
            pageSize = 10;
        }
        
        StringBuilder keyBuilder = new StringBuilder(CK_PAFANGTUAN_LIST);
        keyBuilder.append(cityId);
        
        List<Pafangtuan> cachedList = null;
        //缓存的是数组型的List
        cachedList = cacheHandler.getCacheValue(keyBuilder.toString(), List.class);
        
        if (null == cachedList) {
        	
            List<Pafangtuan> originList = loadPafangtuanData(cityId);
            if (null == originList || originList.size() < 1) {
                cacheHandler.setCache(keyBuilder.toString(), CE_PANGTUAN_EMPTY_COLLECTION, ListUtils.EMPTY_LIST);
                return ret;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            //在预估不了大小的情况下以linkedList方式存储
            List<Pafangtuan> processedList = new LinkedList<Pafangtuan>();
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, -1);
            Date lastDay = c.getTime();
            for (Pafangtuan pft : originList) {
//                int singUpStatus = pft.getSignUpStatus();
//                if (SignUpStatusNormal != singUpStatus) {
//                    continue;
//                }
            	/**
				 * @author linfangwang
				 * signUpStatus:状态有误，使用 applyRemainDays 判断，
				 */
                int applyRemainDays = pft.getApplyRemainedDays();
                if(applyRemainDays <= 0) {
                	continue;
                }
                String activeDateStr = pft.getActiveDate();
                if (StringUtils.isBlank(activeDateStr)) {
                    continue;
                }
                
                Date activeDate = null;
                try {
                    activeDate = sdf.parse(activeDateStr);
                } catch (ParseException e) {
                    logger.error("", e);
                }
                
                if (null == activeDate) {
                    continue;
                }
                
                pft.setActiveDateRel(activeDate);
                
                if (!activeDate.after(lastDay)){
                    continue;
                }
                
                //使用第一个相关楼盘的图片作为爬房团的图片
                List<PftBuildModel> builds = pft.getBuilds();
                if (null != builds && builds.size() > 0) {
                    pft.setPic(builds.get(0).getMainPic());
                    pft.setBuildNum(builds.size());
                }
                processedList.add(pft);
            }
            
            if (processedList.size() < 1) {
                cacheHandler.setCache(keyBuilder.toString(), CE_PANGTUAN_EMPTY_COLLECTION, ListUtils.EMPTY_LIST);
                return ret;
            }
            
            //按照时间顺序排列
            Collections.sort(processedList, new Comparator<Pafangtuan>(){
                @Override
                public int compare(Pafangtuan arg0, Pafangtuan arg1) {
                    return arg0.getActiveDateRel().compareTo(arg1.getActiveDateRel());
                }});
            
            //以ArrayList的方式存储，方便分页获取
            cachedList = new ArrayList<Pafangtuan>(processedList.size());
            cachedList.addAll(processedList);
            
            cacheHandler.setCache(keyBuilder.toString(), CE_PAFANGTUAN_LIST, cachedList);
            
        }
        
        int listLength = cachedList.size();
        int start = (pageNo - 1) * pageSize;
        int end = Math.min(start + pageSize, listLength);
        if (listLength > 0 && start <= end) {
            if (end < listLength) {
                ret.put(HAS_NEXT, true);
            }
            ret.put(DATA_LIST, cachedList.subList(start, end));
        }
        return ret;
    }
    
    /**
     * 获取爬房团小贴士，先从缓存获取，如果获取不到再从接口获取
     * @param cityId
     * @return
     */
    public List<PafangtuanTips> getPftTips(int cityId) {
        List<PafangtuanTips> ret = null;
        if (cityId <= 0) {
            return ret;
        }
        StringBuilder keyBuilder = new StringBuilder(CK_PAFANGTUAN_TIPS_LIST_NEW);
        keyBuilder.append(cityId);
        
        ret = cacheHandler.getCacheValue(keyBuilder.toString(), List.class);
        
        if (null == ret) {
            ret = xinFangCommonApiService.getPftTips(cityId);
            if (null != ret) {
                cacheHandler.setCache(keyBuilder.toString(), CE_PAFANGTUAN_TIPS_LIST_NEW, ret);
            }
        }
        return ret;
    }
    
    /**
     * 获取爬房团城市信息，先从缓存获取
     * @param cityId
     * @return
     */
    public PftCityInfo getPftCityInfo(int cityId) {
        PftCityInfo ret = null;
        if (cityId <= 0) {
            return ret;
        }
        StringBuilder keyBuilder = new StringBuilder(CK_PAFANGTUAN_CITY_INFO);
        keyBuilder.append(cityId);
        
        ret = cacheHandler.getCacheValue(keyBuilder.toString(), PftCityInfo.class);
        
        if (null == ret) {
            ret = xinFangCommonApiService.getPafangtuanCitiInfo(cityId);
            if (null != ret) {
                cacheHandler.setCache(keyBuilder.toString(), CE_PAFANGTUAN_CITY_INFO, ret);
            }
        }
        return ret;
    }
    
    /**
     * 加载某个城市的爬房团数据，数据条数和加载时间参考 PAFANGTUAN_LOAD_SIZE 和 PAFANGTUAN_LOAD_AHEAD_MONTH 字段
     * @param cityId
     * @return
     */
    private List<Pafangtuan> loadPafangtuanData(int cityId) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, PAFANGTUAN_LOAD_AHEAD_MONTH);
        return xinFangCommonApiService.getPftList(cityId, PAFANGTUAN_LOAD_SIZE, 1, c.getTimeInMillis());
    }
    
    
    
    
    /**
     * 获取爬房团线路信息
     * @param cityId
     * @param lineId
     * @return
     */
    public LineInfo getLineInfo(Integer cityId, Integer lineId) {
        if (null == cityId || null == lineId) {
            return null;
        }
        
        try {            
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("_t", System.currentTimeMillis()/1000);
            variables.put("_v", 1);
            variables.put("appid", 2);
            variables.put("city", cityId);
            variables.put("line_id", lineId);
            variables.put("token", generateToken(variables));
            JSONObject ret = interfaceService.getJSONFromInterface(PAFANGTUAN_LINE_INFO_URL, variables);
            
            logger.info(new StringBuilder("cityId:").append(cityId).append(" lineId:").append(lineId)
                    .append("获取爬房团信息为：").append(ret).toString());
            
            if (null != ret) {
                Integer errorCode = ret.getInteger("e");
                if (null != errorCode && 9999 == errorCode) {
                    String lineData = ret.getString("data");
                    if (null != lineData) {
                        return JSON.parseObject(lineData, LineInfo.class);
                    } else {
                        logger.error(new StringBuilder("cityId:").append(cityId).append(" lineId:").append(lineId)
                                .append("获取爬房团信息data出错").append(ret).toString());
                    }
                } else {
                    logger.error(new StringBuilder("cityId:").append(cityId).append(" lineId:").append(lineId)
                            .append("获取爬房团信息errorCode出错：").append(ret).toString());
                }
            } else {
                logger.error(new StringBuilder("cityId:").append(cityId).append(" lineId:").append(lineId)
                        .append("获取不到爬房团信息").toString());
            }
        } catch (Exception e) {
            logger.error(
                    new StringBuilder("cityId:").append(cityId).append(" lineId:").append(lineId).append("获取爬房团信息接口异常")
                            .toString(), e);
        }
        return null;
    }
    
    /**
     * 获取爬房团小贴士，该部分先从缓存取，如果缓存没有则从接口调用。
     * @param cityId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TipsInfo> getTips(Integer cityId) {
        if (null == cityId) {
            return null;
        }
        Object tips = null;
        StringBuilder cacheKeyBuilder = new StringBuilder(CK_PAFANGTUAN_TIPS_LIST).append(":").append(cityId);
        String cacheKey = cacheKeyBuilder.toString();
        try {
            tips = cacheClient.get(cacheKey);
        } catch (Exception e) {
            logger.error("", e);
        }
        
        if (null != tips) {
            return (List<TipsInfo>) tips;
        }
        
        try {
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("_t", System.currentTimeMillis()/1000);
            variables.put("city", cityId);
            variables.put("_v", 1);
            variables.put("appid", 2);
            variables.put("token", generateToken(variables));
            JSONObject ret = interfaceService.getJSONFromInterface(PAFANGTUAN_TIPS_URL, variables);
            logger.info(new StringBuilder("cityId:").append(cityId).append("获取tipsInfo信息为：").append(ret).toString());
            if (null != ret) {
                Integer errorCode = ret.getInteger("e");
                if (null != errorCode && 9999 == errorCode) {
                    JSONObject dataWrap = ret.getJSONObject("data");
                    if (null != dataWrap) {
                        String tipsData = dataWrap.getString("data");
                        List<TipsInfo> tipsRet = JSONObject.parseArray(tipsData, TipsInfo.class);
                        cacheClient.setWithNoReply(cacheKey, CE_PAFANGTUAN_TIPS_LIST, tipsRet);
                        return tipsRet;
                    } else {
                        logger.error(new StringBuilder("cityId:").append(cityId).append("获取tipsInfo的data出错：")
                                .append(ret).toString());
                    }
                } else {
                    logger.error(new StringBuilder("cityId:").append(cityId).append("获取tipsInfo的errorCode出错：")
                            .append(ret).toString());
                }
            } else {
               logger.error(new StringBuilder("cityId:").append(cityId).append("获取不到tipsInfo").toString()); 
            }
        } catch (Exception e) {
            logger.error(new StringBuilder("cityId:").append(cityId).append("获取tipsInfo接口异常：").toString());
        }
        return null;
    }
    
    /**
     * 调用php接口报名爬房团
     * @param cityId
     * @param lineId
     * @param name
     * @param mobile
     * @return
     */
    public JSONObject signup(Integer cityId, Integer lineId, String name, String mobile,Integer userId) {
        JSONObject ret = null;
        if (null == cityId || null == lineId || StringUtils.isBlank(name) 
                || StringUtils.isBlank(mobile)) {
            ret = new JSONObject();
            ret.put("errorCode", -1);
            ret.put("errorMsg", "报名失败");
        }
        
        Map<String, Object> variables = new HashMap<String, Object>();
       
        try {
            variables.put("_t", System.currentTimeMillis()/1000);
            variables.put("line_id", lineId);
            //variables.put("real_name", URLEncoder.encode(name, "UTF-8"));
            variables.put("mobile", mobile);
            variables.put("city", cityId);
            variables.put("_v", 1);
            variables.put("appid", 2);
            variables.put("real_name", name);
            variables.put("token", generateToken(variables));
            if(userId == null){
                ret = interfaceService.getJSONFromInterfaceByPost(PAFANGTUAN_SIGNUP_URL, variables);
            }else{
                variables.put("uid", userId);
                ret = interfaceService.getJSONFromInterfaceByPost(PAFANGTUAN_SIGNUP_URL_LOGIN, variables);
            }
            logger.info(new StringBuilder("爬房团用户报名[lineId:").append(lineId).append(" cityId:").append(cityId)
                    .append(" name:").append(name)
                    .append(" mobile:").append(mobile).append("]返回值为").append(ret));
            if (null == ret) {
                throw new Exception("接口返回值为空");
            }
            Integer errorCode = ret.getInteger("e");
            ret = new JSONObject();
            if (9999 == errorCode) {
                ret.put("errorCode", 0);
                ret.put("errorMsg", "报名成功");
            } else if (3008 == errorCode || 3009 == errorCode) {
                ret.put("errorCode", -2);
                ret.put("errorMsg", "这个爬房团已下线，请换一个");
            } else if (2117 == errorCode) {
                ret.put("errorCode", -3);
                ret.put("errorMsg", "您的手机号已经在电脑上登录，请在电脑上报名");
            } else if (2104 == errorCode) {
                ret.put("errorCode", -4);
                ret.put("errorMsg", "请输入正确的姓名");
            } else if (2105 == errorCode) {
                ret.put("errorCode", -5);
                ret.put("errorMsg", "请输入正确的手机号");
            } else {
                ret.put("errorCode", -6);
                ret.put("errorMsg", "报名失败，请稍后重试");
            }
        } catch (Exception e) {
            logger.error("", e);
            ret = new JSONObject();
            ret.put("errorCode", -1);
            ret.put("errorMsg", "报名失败，请稍后重试");
        }
        
        return ret;
    }
    
//    /**
//     * 调用php接口爬房团list
//     * @param cityId
//     * @param lineId
//     * @param name
//     * @param mobile
//     * @return
//     */
//    public JSONObject signup(Integer cityId, Integer pageNo, Integer pageSize, Long endDate) {
//        JSONObject ret = null;        
//        Map<String, Object> variables = new HashMap<String, Object>();       
//        try {
//            variables.put("pageNo", pageNo);
//            variables.put("pageSize", pageSize);
//            variables.put("cityId", cityId);
//            if(endDate != null){
//                variables.put("endDate", endDate);
//            }      
//        } catch (Exception e) {
//            logger.error("", e);
//            ret = new JSONObject();
//            ret.put("errorCode", -1);
//            ret.put("errorMsg", "报名失败，请稍后重试");
//        }
//        
//        return ret;
//    }
    
    private String generateToken(Map<String, Object> variables) {
        TreeMap<String,Object> tmpMap = new TreeMap<String, Object>(variables);
        StringBuilder sb = new StringBuilder("_k=");
        sb.append(PAFANGTUAN_SECRETKEY);
        
        if (null == variables) {
            return DigestUtils.md5Hex(sb.toString());
        } else {            
            for (String key : tmpMap.keySet()) {
                sb.append(key).append("=").append(tmpMap.get(key)).append("&");
            }
            return DigestUtils.md5Hex(StringUtils.chomp(sb.toString(), "&"));
        }
    }
    
    /*******************************************xinfang api v4 wap**********************************************************/
   
    /**
     * 从json中获取数据
     * @param jo
     * @return
     */
    public List<Pafangtuan> getPftList(JSONObject jo)
    {
    	try {
			if(jo == null)
			{
				return null;
			}
			JSONArray jArray = jo.getJSONArray("data");
			if(jArray == null)
        	{
        		return null;
        	}
			return JSON.parseArray(jArray.toJSONString(),Pafangtuan.class);
		} catch (Exception e) {
			logger.error(e);
		}
    	return null;
    }
    
    
    /**
     * 两个月内的可报名已排序的爬房团
     * @param pageSize
     * @param pageNo
     * @param pfts
     * @return
     */
    public List<Pafangtuan> getPftList(Invocation inv,List<Pafangtuan> pfts,int pageNo,int pageSize)
    {
    	if(pfts == null)
    	{
    		return null;
    	}
    	
    	List<Pafangtuan> list = new ArrayList<Pafangtuan>(pfts.size());
    	
    	for(Pafangtuan pft:pfts)
    	{
    		/**
			 * @author linfangwang
			 * signUpStatus:状态有误，使用 applyRemainDays 判断，
			 * 调用该接口的方法已停用，故未做改变
			 */
    		if(pft.getSignUpStatus().intValue()!=SignUpStatusNormal)
    		{
    			break;
    		}
    		list.add(pft);
    	}

    	return getPfts(inv,list,pageNo,pageSize);
    }
    
    
    //对爬房团排序
    public List<Pafangtuan> getPfts(Invocation inv,List<Pafangtuan> pfts,int pageNo,int pageSize)
    {
 
    	List<Pafangtuan> tmpList = new ArrayList<Pafangtuan>(pfts.size());
    	
    	for(int i=pfts.size()-1;i>=0;i--)
    	{
    		tmpList.add(pfts.get(i));
    	}
    	
        int start = (pageNo - 1)*pageSize;
        int end = pageNo*pageSize;
        if(pfts.size() <start)
        {
        	inv.addModel("hasNext", false);
        	return null;
        }
        if(pfts.size() <= end)
        {
        	end =  pfts.size();
        	inv.addModel("hasNext", false);
        }
    	
        List<Pafangtuan> tmpList2 = new ArrayList<Pafangtuan>(pageSize);
        for(int i = start;i<end;i++)
        {
        	tmpList2.add(tmpList.get(i));
        }
        if(!inv.getModel("hasNext").equals(false))
        {
        	if(tmpList2.size() < pageSize)
        	{
        		inv.addModel("hasNext", false);
        	}
        }
    	
    	return tmpList2;
    }

    //除去本线路楼盘后，其他的最新的可报名的楼盘；
    public  void decorateOtherPftList(List<Pafangtuan> pfts,Pafangtuan delPft)
    {
    	try {
    		if(pfts != null)
        	{
    			//可报名的爬房团
    			int length = pfts.size();
    			List<Pafangtuan> dels = new ArrayList<Pafangtuan>();
    			for(int i=0;i<length;i++)
    			{
    				if(pfts.get(i).getLineId().intValue() == delPft.getLineId().intValue())
    				{
    					dels.add(pfts.get(i));
    					continue;
    				}
    				/**
    				 * @author linfangwang
    				 * signUpStatus:状态有误，使用 applyRemainDays 判断，
    				 * 调用该接口的方法已停用，故未做改变
    				 */
    				if(pfts.get(i).getSignUpStatus().intValue()!=SignUpStatusNormal)
    				{
    					dels.add(pfts.get(i));
    					continue;
    				}
    			}
    			pfts.removeAll(dels);
    			
    			//最多显示5条线路
    			if(pfts.size()==6)
    			{
    				pfts.remove(5);//删除最后一条数据
    			}
    			
    			
    			//其他线路数据显示格式设置
    			for(Pafangtuan tmp:pfts)
            	{
    				
    				tmp.setActiveDate(DateUtils.stringPattern(tmp.getActiveDate(),"yyyyMMdd", "M月d日"));
    				
            		//时间+标题超过11个
            		if(tmp.getTitle()!=null)
            		{
//            			if(tmp.getTitle().length()>11)
//            			{
//            				tmp.setTitle(tmp.getTitle().substring(0, 11)+"...");
//            			}
//            			else {
//    						tmp.setTitle(tmp.getTitle());
//    					}
            		}
            		else {
    					tmp.setTitle("");
    				}
            		//线路描述
            		if(tmp.getLightspot()!=null)
            		{
            			if(tmp.getLightspot().length()>25)
            			{
            				tmp.setLightspot(tmp.getLightspot().substring(0, 25)+"...");
            			}
            			
            		}
            		else {
    					tmp.setLightspot("");
    				}
            		
            	}
    			
    			
        		
        	}
		} catch (Exception e) {
			logger.error(e);
		}
    }
    
    
    
    public void decorateMapUrl(Pafangtuan pft)
    {
    	StringBuilder sb_buildLnglats=new StringBuilder();
    	StringBuilder sb_lnglatsUrl = new StringBuilder();
    	
    	if(pft!=null)
    	{
    		List<PftBuildModel> builds = pft.getBuilds();
    		if(builds != null)
    		{
    			int length=builds.size()-1;
            	//1、构造地图的楼盘名称
            	for(int i=0;i<length;i++)
            	{
            		PftBuildModel tmp = builds.get(i);
            		if (null != tmp.getLongitude() && null != tmp.getLatitude()) {
                        float longitude = Float.parseFloat(tmp.getLongitude().isEmpty() ? "0" : tmp
                                .getLongitude());
                        float latitude = Float.parseFloat(tmp.getLatitude().isEmpty() ? "0" : tmp
                                .getLatitude());
                        if (longitude < AppConstants.longtitudeMin || longitude > AppConstants.longtitudeMax
                                || latitude < AppConstants.latitudeMin || latitude > AppConstants.latitudeMax) {
                           //不合法的经纬度
                        }
                        else {
                        	sb_buildLnglats.append(tmp.getLongitude()).append(",").append(tmp.getLatitude()).append(";");
    					}
                    }
            		
            	}
            	
            	if(StringUtils.isNotBlank(builds.get(length).getLatitude()) && StringUtils.isNotBlank(builds.get(length).getLongitude()))
            	{
            		float longitude = Float.parseFloat(builds.get(length).getLongitude().isEmpty() ? "0" : builds.get(length)
                            .getLongitude());
                    float latitude = Float.parseFloat(builds.get(length).getLatitude().isEmpty() ? "0" : builds.get(length)
                            .getLatitude());
            		
                    if (longitude < AppConstants.longtitudeMin || longitude > AppConstants.longtitudeMax
                            || latitude < AppConstants.latitudeMin || latitude > AppConstants.latitudeMax) {
                       //不合法的经纬度,删除最后一个分号 ；
                    	if(sb_buildLnglats.toString().length()>0)
                    	{
                    		sb_buildLnglats.delete(sb_buildLnglats.toString().length()-1,sb_buildLnglats.toString().length());
                    	}
                    }
                    else {
                    	sb_buildLnglats.append(builds.get(length).getLongitude()).append(",").append(builds.get(length).getLatitude());
    				}

                    sb_lnglatsUrl.append("http://restapi.amap.com/v3/staticmap?markers=-1,http://webapi.amap.com/images/marker_sprite.png,0:");
                	sb_lnglatsUrl.append(sb_buildLnglats.toString());
                	sb_lnglatsUrl.append("&key=9e4b883b2a6d8482638c56b6f60078b7&size=470*240&zoom=");
                	sb_lnglatsUrl.append(ZOOM);
                	
                	//地图地址
                	pft.setLnglatsUrl(sb_lnglatsUrl.toString());
                	sb_buildLnglats.delete(0, sb_buildLnglats.toString().length());
                	sb_lnglatsUrl.delete(0, sb_lnglatsUrl.toString().length());
            	}
    		}
    	}
    }
    
    
    
    /**
     * 获取爬房团列表信息+对数据进行修饰
     * @param jo
     * @param endDate：当前时间
     * @return
     */
    public void decoratePtfList(Long endDate,String device,List<Pafangtuan> pfts)
    {
    	try {

    		if(pfts != null)
    		{
    			for(Pafangtuan pft: pfts)
    			{
    				
    				//剩余天数 = 截止日期-当前日期+1
//            		pft.setApplyRemainedDays(pft.getApplyRemainedDays()+1);
            		
            		//已报名人数+标题+
            		//开团时间,m月d日格式
            		pft.setActiveDate(DateUtils.stringPattern(pft.getActiveDate(), "yyyyMMdd","M月d日"));
            		
            		//线路描述
            		if(pft.getLightspot()!=null)
            		{
            			if(device.equals("pad") && pft.getLightspot().length()>30)
            			{
            				pft.setLightspot(pft.getLightspot().substring(0,30)+"...");
            			}
            			else if(device.equals("phone") && pft.getLightspot().length()>35)
            			{
            				pft.setLightspot(pft.getLightspot().substring(0, 35)+"...");
            			}
            		}
            		else {
    					pft.setLightspot("");
    				}

            		//线路楼盘,phone 全显示
            		List<PftBuildModel> lineLouPans = pft.getBuilds();
            		if(lineLouPans!=null && !lineLouPans.isEmpty())
            		{
            			
            			for(int i=0;i<lineLouPans.size();i++)
            			{
            				//楼盘名称最多10个汉字
            				PftBuildModel lp = lineLouPans.get(i);
            				if(lp.getName().length()>10)
            				{
            					//楼盘名称简写
            					lp.setNameAbbr(lp.getName().substring(0,10)+"...");
            				}
            				else {
    							lp.setNameAbbr(lp.getName());
    						}
            				
            				//电话tel，打电话数据转为123456,780
            				lp.setPhone2(decoratePhone(lp.getPhone()));
            				/**
            				 * 将xxx元m2改成xxx,"元平方米"直接在页面上加
            				 * modified by rogantianwz @2014/6/20
            				 */
            				String price = lp.getRefPrice();
            				if (StringUtils.isNotBlank(price)) {
            				    lp.setRefPrice(price.replace("元/m２", "元/平米"));
            				}
            			}
            			
            		}

    			}
    		}   	
		} catch (Exception e) {
			logger.error(e);
		}
    }
    
    
    
    public void decoratePtf(Pafangtuan pft)
    {
    	try {
    		if(pft!=null)
        	{
    			//开团时间
    			pft.setActiveDate(DateUtils.stringPattern(pft.getActiveDate(),"yyyyMMdd", "yyyy-MM-dd"));
    			//报名截止日期
    			pft.setLastSignUpDate(DateUtils.stringPattern(pft.getLastSignUpDate(),"yyyyMMdd", "yyyy-MM-dd"));
    			
    			
    			/**
    			 * @author linfangwang
    			 * signUpStatus:状态有误，使用 applyRemainDays 判断，
    			 * 调用该接口的方法已停用，故未做改变
    			 */
    			//判断报名状态
    			if(pft.getSignUpStatus() == null)
    			{
    				if(pft.getLastSignUpDate().compareTo(getCurDate("yyyy-MM-dd"))>=0)
    				{
    					pft.setSignUpStatus(1);
    				}
    				else {
    					//已截止报名
						pft.setSignUpStatus(3);
					}
    			}
    			

        		//位置18+“...”
    			List<PftBuildModel> builds = pft.getBuilds();
    			if(builds != null)
    			{
    				for(PftBuildModel build:builds)
    				{
    					if(build.getAddress()!=null)
    					{
    						if(build.getAddress().length()>18)
    						{
    							build.setAddress(build.getAddress().substring(0, 18)+"...");
    						}
    					}
    					else {
							build.setAddress("");
						}
    					
    					//电话
    					
    					build.setPhone2(decoratePhone(build.getPhone()));
    					

                        /**
                         * 将xxx元m2改成xxx,"元平方米"直接在页面上加
                         * modified by rogantianwz @2014/6/20
                         */
                        String price = build.getRefPrice();
                        if (StringUtils.isNotBlank(price)) {
                            build.setRefPrice(price.replace("元/㎡", "元/平米"));
                        }
    				}
    			}

        	}
		} catch (Exception e) {
			
		}
    }
    
    /**
     * 电话格式处理
     * @param phone
     * @return
     */
    public String decoratePhone(String phone)
    {
    	if(phone == null)
    	{
    		return "";
    	}
    	
    	if(phone.indexOf("转")!=-1)
    	{
    		phone = phone.replaceAll("转", ",");
    	}
    	
    	phone = phone.replaceAll("-","");
    	phone = phone.replaceAll(" ", "");
    	
    	return phone.trim();
    }

    /**
     * 获取n月后的时间
     * @param format
     * @param after:几月后
     * @param cur:当前时间
     * @return
     * @throws ParseException 
     */
    public Long getTMonthAfterDay(String format,int after) throws ParseException
    {
    	SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.MONTH,after);
        String afterString = dateFormat.format(calendar.getTime());
    	
        
        return dateFormat.parse(afterString).getTime()/1000;
    } 
    
    public String getCurDate(String format)
    {
    	SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    	return  dateFormat.format(new Date());
    	
    }

    public void setXinFangCommonApiService(XinFangCommonApiService xinFangCommonApiService) {
        this.xinFangCommonApiService = xinFangCommonApiService;
    }
    
}
