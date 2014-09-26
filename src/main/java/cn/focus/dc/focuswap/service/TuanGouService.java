package cn.focus.dc.focuswap.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.TuanGou;
import cn.focus.dc.focuswap.model.TuanGouDetail;
import cn.focus.dc.focuswap.utils.HttpClientUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
public class TuanGouService {
    protected Log logger = LogFactory.getLog(TuanGouService.class);

    @Autowired
    private InterfaceService interfaceService;

    @Autowired
    private RedisHandlerService redis;

    @Autowired
    private CacheHandlerService cacheHandler;
    
    @Autowired
    private CityService cityService;

    private static final long DAY_IN_MS = 1000 * 24 * 60 * 60;

    /**
     * 团购缓存更新wrapper 更新频率为两个小时（整点更新）
     * 
     * @author zihaoli
     */
    @Scheduled(cron = "0 0 0/2 * * ?")
    public void timeTask() {
        updateRedis();
    }

    public void releaseLock() {
    String lock = "tuangou:update:lock";
    redis.delete(lock);
    }
    
    // TODO 暂时只更新北京的信息
    public void updateRedis() {
        String lock = "tuangou:update:lock";
       // boolean getLock = redis.acquireLock(lock);
        try {
            // 利用分布式锁 避免多个实例同时更新缓存
            if (true) {
                // 从接口分页拿取数据
                // 1、获取总数
                // TODO 其他城市ID的遍历
            	List<DictCity> cityList = cityService.getCityList();
            	for(DictCity city:cityList) {
                int cityId = city.getCityId();
                JSONObject tmp = getTuanGouFromApi(cityId, 0, 1);
                int total = 0;
                if(null!=tmp) {
                	System.out.println(tmp.toJSONString());
                	total = org.apache.commons.lang.StringUtils.isNotBlank(tmp.getString("total"))?tmp.getIntValue("total"):0;
                }
                // TODO 一次性取多少性能较好？
                int pageNum = 0;
                int pageSize = total;
                List<TuanGou> list = new ArrayList<TuanGou>();
                if(total>0) {
                while (true) {
                    JSONObject jo = getTuanGouFromApi(cityId, pageNum, pageSize);
                    if (jo != null) {
                        String jstr = jo.getString("data");
                        List<TuanGou> l = JSON.parseArray(jstr, TuanGou.class);
                        list.addAll(l);
                        
                        if (total <= list.size())
                            break;
                        else
                            pageNum++;
                    } else {
                        logger.error("从接口获取团购数据失败:" + cityId);
                        break;
                    }
                }
                
                redis.updateTuanGouObject(list,cityId);
                redis.updateTuanGouList(list, cityId);
                redis.updateApplyNumSortedList(list, cityId);
                }
                //redis.updateStartTimeSortedList(list, cityId);
                // 按照团购人数从高到低排序后 的团购编号列表
                //redis.updateApplyNumSortedList(list, cityId);
                // TODO 释放分布式锁（目前的超时时间较长，团购条目较少，足够完成缓存的生成）
            	}
                //redis.delete(lock);
            }
        } catch (Exception e) {
            logger.error("", e);
            System.out.println("更新redis团购列表失败: " + e.getMessage());
            //redis.delete(lock);
        }

    }

    /**
     * 分页获取团购详细数据列表，从API拿数据
     * 
     * @param cityId
     * @param pageNo
     * @param pageSize
     * @return 其返回值如果为null，表示没有收到数据或者是收到了错误数据
     */
    public JSONObject getTuanGouFromApi(Integer cityId, Integer pageNo, Integer pageSize) {
        Map<String, String> urlVariables = new HashMap<String, String>();
         urlVariables.put("mod","wap");
         urlVariables.put("func","dianshang_map");
        urlVariables.put("city_id", String.valueOf(cityId));
        urlVariables.put("pageindex", String.valueOf(pageNo));
        urlVariables.put("pagesize", String.valueOf(pageSize));
        JSONObject jo = null;
        try {
            // 访问接口URL获取接送元数据
            //jo = interfaceService.getJSONFromInterface(AppConstants.TUANGOULIST_BY_CITYID_URL, urlVariables);
        	jo = HttpClientUtil.getNormalResoponseFromUrl(AppConstants.TUANGOULIST_BY_CITYID_URL, urlVariables);
            if (jo == null) {
                return null;
            }
            // System.out.println(jo);
            // 判断是否成功
            Integer errorCode = jo.getInteger("stat");
            if (null == errorCode || errorCode == 0) {
                jo = null;
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return jo;
    }

    /**
     * 团购列表json
     * 
     * @author zihaoli
     * @throws ParseException 
     */

    public JSONObject getTuanGouList(Integer cityId, Integer pageNo, int pageSize) throws ParseException {

        // 先获取团购列表
        String tuanGouListKey = "tuangou:" + cityId + ":list";
        int startIdx = pageNo * pageSize;
        int endIdx = pageNo * pageSize + pageSize - 1;
        // 多获取一个，用以判断hasNext
        List<String> tuanGouList = redis.lrange(tuanGouListKey, startIdx, endIdx + 1);

        // 看打算多获取的那个是否获取到了，如果是则确实hasNext
        boolean hasNext = false;
        if (tuanGouList.size() > pageSize) {
            tuanGouList.remove(pageSize);
            hasNext = true;
        }

       // System.out.println("读取团购信息：第" + pageNo + "页，每页" + pageSize + "条（" + startIdx + "~" + endIdx + "）");

        List<TuanGouDetail> tuanGouDetails = new ArrayList<TuanGouDetail>();
        for (String tuanGou : tuanGouList) {
            String key = "tuangou:" + cityId + ":" + tuanGou;
            TuanGouDetail tuangou = getOneTuangou(key);
            tuangou.setPrice_unit(tuangou.getPrice_unit().replace("㎡", "平方米"));
            tuanGouDetails.add(tuangou);
        }
       // tuanGouDetails = sortTuangou(tuanGouDetails);
        JSONObject rt = null;

        if (tuanGouDetails.size() != 0) {
        	rt = new JSONObject();
            rt.put("data", tuanGouDetails);
            rt.put("errorCode", "0");
            rt.put("hasNext", hasNext);
        } else {
        	rt = new JSONObject();
        	rt.put("data", "");
        	rt.put("hasNext", "false");
        	rt.put("errorCode", "1");
        }
        return rt;
    }

    /**
     * 取activeId对应的团购，生成团购详情
     * @throws ParseException 
     */
    public TuanGouDetail getTuanGouDetail(Integer cityId, Integer activeId) throws ParseException {
        String key = "tuangou:" + cityId + ":" + activeId;
        TuanGouDetail detail = getOneTuangou(key);
        return detail;
    }

    /**
     * 团购推荐
     * 
     * @return 报名人数最多的前num条团购组成列表
     * @throws ParseException 
     */
    public List<TuanGouDetail> getTuanGouRecommend(Integer cityId, Integer num) throws ParseException {
        // TODO Auto-generated method stub
        List<String> list = redis.lrange("tuangou:" + cityId + ":apply_list", 0, num - 1);
        List<TuanGouDetail> tgList = new ArrayList<TuanGouDetail>();
        // 从缓存中获取
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String tmp = it.next();
            String key = "tuangou:" + cityId + ":" + tmp;
            TuanGouDetail detail = getOneTuangou(key);
            detail.setPrice_unit(detail.getPrice_unit().replace("㎡", "平方米")); 
            tgList.add(detail);
        }
        JSONObject tgJson = new JSONObject();

        tgJson.put("data", list);

        return tgList;
    }
    
    /**
     * get groupids that has tuangou
     * @return
     */
    public String getTuanGouGroupids(int cityid) {
    	
    	@SuppressWarnings("deprecation")
		String groupIds = redis.get("tuangou:"+ cityid + ":groupid");
    	
    	return groupIds;
    	
    }
    

    /**
     * 按照key从redis取出一个团购项，组装为团购。
     * @author zihaoli
     * @throws ParseException 
     */
    private TuanGouDetail getOneTuangou(String key) throws ParseException {
    	SimpleDateFormat sf = new SimpleDateFormat("EEE MMM d H:m:s z y",Locale.ENGLISH);
        TuanGouDetail rt = new TuanGouDetail();
        Map<String, String> map = redis.hgetAll(key);

        rt.setActive_id(Integer.parseInt(map.get("actvie_id")));
        rt.setPhoto(map.get("photo"));
        rt.setActive_name(map.get("actvie_name"));
        rt.setActive_desc(map.get("actvie_desc"));
        // e.g. 均价 10000 元/套，价格待定
        String price = map.get("price").replace("㎡", "平米");
        rt.setPrice(price.replaceAll("[^\\d]", ""));   
        rt.setPrice_unit(price.replaceAll("[\\d]", ""));
        if(rt.getPrice_unit().equals("价格待定")){
        	rt.setPrice_unit("");
        	rt.setPrice("价格待定");
        }
        rt.setAddress(map.get("address"));
        //get a hight fixie photo
        rt.setPhone(map.get("phone"));
        rt.setProj_name(map.get("proj_name"));
        rt.setTimeLeft((int) (calculateTimeLeft(sf.parse(map.get("active_end")))));
        rt.setActive_start(sf.parse(map.get("active_start")));
        rt.setActive_end(sf.parse(map.get("active_end")));
        rt.setLng(Double.parseDouble(map.get("lat")));
        rt.setLat(Double.parseDouble(map.get("lng")));
        rt.setActive_order(Integer.parseInt(map.get("active_order")));
        // 是否有可能报名人数为零时会是非数字
        try {
            rt.setApply_num(Integer.parseInt(map.get("apply_num")));   
        } catch (Exception e) {
            rt.setApply_num(0);
        }
        rt.setGroup_id(Integer.parseInt(map.get("group_id")));
        rt.setLocation(map.get("location"));
        return rt;
    }
    
    private long calculateTimeLeft(Date endTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Date date;
        try {
            date = endTime;
            long rt = (date.getTime() - now.getTime()) / DAY_IN_MS;
            return rt > 0 ? rt : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * first sort by active_order then sort by enddate
     * @param list
     */
    public List<TuanGouDetail> sortTuangou(List<TuanGouDetail> list) {
    	//Collections.sort(list);
    	List<TuanGouDetail> orderList = new ArrayList<TuanGouDetail>();
    	List<TuanGouDetail> timeList = new ArrayList<TuanGouDetail>();
    	Iterator it = list.iterator();
    	long timeNow = System.currentTimeMillis();
    	while(it.hasNext()) {
    		TuanGouDetail td = (TuanGouDetail) it.next();
    		if(td.getActive_end().getTime()<timeNow) {
    			it.remove();
    		} else {
    			if(td.getActive_order()>0 && td.getActive_order()<999) {
    				if(orderList.size()>10) {
    					break;
    				}
    				orderList.add(td);
    			} else {
    				if(timeList.size()>10)  {
    					break;
    				}
    				timeList.add(td);
    			}
    		}
    		
    	}
    	Collections.sort(orderList, new Comparator<TuanGouDetail>(){

			@Override
			public int compare(TuanGouDetail o1, TuanGouDetail o2) {
				// TODO Auto-generated method stub
				if(o1.getActive_order()>o2.getActive_order()) {
					return 1;
				} else if(o1.getActive_order()<o2.getActive_order()) {
					return -1;
				} else {
					return 0;
				}
			}
    		
    	});
    	orderList.addAll(timeList);
    	return orderList;
    }
}
