package cn.focus.dc.focuswap.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.search.client.SearchClient;
import com.common.search.client.base.Building;

import cn.focus.dc.building.model.es.EsProjInfo;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.dao.RecommendHouseDAO;
import cn.focus.dc.focuswap.model.BuildingDaoGou;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.EsProjInfoChild;
import cn.focus.dc.focuswap.model.HomeFocusPic;
import cn.focus.dc.focuswap.model.HousingGuide;
import cn.focus.dc.focuswap.model.RecommendHouse;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.Pafangtuan;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.PftBuildModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.common.joda.time.DateTime;
import org.elasticsearch.common.joda.time.Period;
import org.elasticsearch.common.joda.time.PeriodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static cn.focus.dc.focuswap.config.AppConstants.*;

@Component
public class HomeService {

	private static Log logger = LogFactory.getLog(HomeService.class);

	@Autowired
	private RecommendHouseDAO recommendDao;

	@Autowired
	private SearchService searchService;

	@Resource(name="memcachedClient")
	private MemcachedClient cacheClient;

	@Autowired
	private SearchClient suggestSearchClient;

	@Autowired
	private InterfaceService interfaceService;

	@Autowired
	private BuyHouseService buyHouseService;

	@Autowired
	private HousingGuideService housingGuideService;

	@Autowired
	private BuildingProposeService buildingProposeService;

	@Autowired
	private XinFangCommonApiService xinfangCommonApiService;

	@Autowired
	private BuildingSearchService buildingSearchService;
	@Autowired
	private PftService pftService;
	
	@Autowired
	private BuildingDaoGouService daoGouService;
	
	@Autowired
	private PaFangTuanService paFangTuanService;

    @Autowired
    private HomeJiaoDianTuService homeJiaoDianTuService;

    @Autowired
    private CacheHandlerService cacheHandler;
    
	private static final Integer maxFocusTitleLength = 18;

	private static final String threeDots = "...";
	
    // 最多可能在某城市的首页上显示的焦点图数目
    private static final int MAX_HOME_JIAODIANTU_SIZE = 5;

	/**
	 * 获取首页主编推荐楼盘列表
	 */
	public Map<String, Object> getRecommendHouseList(DictCity city) {
		List<EsProjInfoChild> ret = Collections.emptyList();
		Map<String, Object> result = new HashMap<String, Object>();

		String cacheKey = new StringBuilder(CK_HOME_RECOMMENDLIST).append(
				city.getCityId()).toString();

		// 首先从缓存获取
		try {
			ret = cacheClient.get(cacheKey);
			if (null != ret && ret.size() > 0) {
				result.put("list", ret);
				return result;
			}
		} catch (Exception e) {
			logger.error("", e);
		}

		// 缓存获取不到，从接口和数据库获取
		Date beginDate = recommendDao.getRecommendStartDate(city.getCityId());
		DateTime now = new DateTime();
		DateTime beginDateJoda = new DateTime(beginDate.getTime());

		// 获取当前时间到开始时间的天数
		Period p = new Period(beginDateJoda, now, PeriodType.days());
		int pDays = p.getDays();

		// 获取总共的期数
		int pCounts = (pDays / 7) + 1;

		// 获取本期开始的第一天
		DateTime startDate = beginDateJoda.plusDays((pCounts - 1) * 7);
		DateTime endDate = startDate.plusDays(7);

		// 获取主编推荐楼盘ID
		List<RecommendHouse> rhList = recommendDao.getRecommendIdList(
				city.getCityId(), 0, 5, startDate.getMillis() / 1000L,
				endDate.getMillis() / 1000L);
		Map<Integer, String> urlMap = new HashMap<Integer, String>();
		List<Integer> groupIds = new ArrayList<Integer>();
		for (RecommendHouse rh : rhList) {
			urlMap.put(rh.getGroupId(), rh.getPicUrl());
			groupIds.add(rh.getGroupId());
		}
		ret = searchService.projGroupIdSearch(groupIds);
		ret = buyHouseService.switchBuildingParameter(ret, city, 0);
		/**
		 * @author linfangwang 
		 * 截字
		 */
		for(EsProjInfoChild espj:ret) {
			//楼盘名
        	if(espj.getProjName().length()>7) {
        		espj.setProjName(espj.getProjName().substring(0, 7) + "...");
        	}
        	//地址
        	if (espj.getProjAddress().length() > 9) {
        		espj.setProjAddress(espj.getProjAddress().substring(0, 9) + "...");
            }
        	
        	//户型
        	if(espj.getRoomInfo().length() > 14) {
        		espj.setRoomInfo(espj.getRoomInfo().substring(0, 14)+"...");
        	}
		}
		
		
		Date recommendTime = new Date(rhList.get(0).getRecommendDate() * 1000L);
		for (EsProjInfoChild ec : ret) {
			ec.setUrl(urlMap.get(ec.getGroupId()));
			ec.setRecommendDate(recommendTime);
			ec.setRecommendPeriods(pCounts);
		}
		// 将结果放入缓存
		if (ret != null && ret.size() > 0) {
			try {
				cacheClient.set(cacheKey, CE_HOME_RECOMMENDLIST_TIME, ret);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		result.put("list", ret);
		return result;
	}

	/**
	 * 楼盘搜索input框 auto-complete功能
	 */
	public List<EsProjInfo> getSearchSuggestList(String keywords, int cityId) {
		List<EsProjInfo> ret = Collections.emptyList();
		ret = searchService.projNameSearch(keywords, cityId, 10);
		return ret;
	}

	/**
	 * 楼盘搜索inupt框auto-complete功能支持，支持拼音搜索
	 * 
	 * @param keywords
	 * @param cityId
	 * @param type
	 *            1 新房suggest 2 二手房suggest
	 * @return
	 */
	public JSONArray getSearchSuggestListSupportPY(String keywords,
			DictCity city, int type) {
		JSONArray ret = new JSONArray();
		try {
			if (type == AppConstants.SEARCH_TYPE_XINFANG) {
				Map<Integer, Building> searchResult = suggestSearchClient
						.search(keywords, city.getCityId(), 10);
				if (null != searchResult) {
					for (Entry<Integer, Building> entry : searchResult
							.entrySet()) {
						JSONObject jo = new JSONObject();
						jo.put("projName", entry.getValue().getName());
						jo.put("groupId", entry.getKey());
						jo.put("linkUrl", "/" + city.getCityPinyinAbbr()
								+ "/loupan/" + entry.getKey() + "/");
						ret.add(jo);
					}
				}
			} else {
				// 获取二手房suggest数据
				ret = interfaceService.getSuggestJsonFromEsf(keywords, city);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return ret;
	}

	/**
	 * 楼盘搜索调用lucene，支持拼音搜索,目前先不上线 bykane
	 * 
	 * @param keywords
	 * @param city
	 * @param type
	 *            1 新房suggest 2 二手房suggest
	 * @return
	 */
	public JSONArray getSearchSuggestListSupportLucene(String keywords,
			DictCity city, int type) {
		JSONArray ret = new JSONArray();
		try {
			if (type == AppConstants.SEARCH_TYPE_XINFANG) {
				Map<String, Object> urlVariables = new HashMap<String, Object>();
				urlVariables.put("q", keywords);
				urlVariables.put("c", city.getCityId());
				urlVariables.put("n", 10);
				JSONObject jo = interfaceService.getJSONFromInterface(
						"http://10.10.44.133/search?q={q}&c={c}&n={n}",
						urlVariables);

				if (jo != null) {
					ret = (JSONArray) jo.get("list");
					for (Object ob : ret) {
						JSONObject j = (JSONObject) ob;
						j.put("linkUrl", "/" + city.getCityPinyinAbbr()
								+ "/loupan/" + j.getString("groupId") + "/");
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return ret;
	}

	/**
	 * 原思路：导购不够，宝典来凑
     * 改为：碎片化资源不够，剩余按照原思路走
	 * @author zihaoli
	 * @date 2014-07-16
	 */
	@SuppressWarnings("unchecked")
    public List<HomeFocusPic> getFocusPic(int cityId) {

	    // 返回图片列表
        List<HomeFocusPic> focusList = null;

	    // 最终成功加载的碎片化管理的图片数量
	    int sphSize = 0;
		    
	    // 首先从缓存读取
        // key 值构建
        StringBuilder sb = new StringBuilder(CK_HOME_JIAODIANTU_LIST);
        sb.append(cityId);

        //从缓存中获取
        focusList = cacheHandler.getCacheValue(sb.toString(), List.class);
        if (focusList != null) 
            return focusList;
        
        focusList = new ArrayList<HomeFocusPic>();

        try {
            // 开始填充焦点图列表
            // 顺序为 碎片化资源 导购 爬房团 宝典
		    JSONObject json = homeJiaoDianTuService.getHomeJiaoDianTuList(cityId, MAX_HOME_JIAODIANTU_SIZE);
		    if (json != null && json.getString("errorCode").equals("0")) {
		        // TODO 接口目前提供的data结果，在为空的时候会出现解析错误从而抛exception
		        // 可以考虑拆解try block，降低可能出现问题的可能性
	            JSONArray jsonArray = json.getJSONArray("data");

	            // 可能的取值为[0, 5]
	            sphSize = jsonArray.size();
	            List<HomeFocusPic> sphs = JSON.parseArray(jsonArray.toJSONString(), HomeFocusPic.class);

                // 参照现有的方式，对标题进行截取
	            
                /*
                 *modified by rogantian @ 2014.08.26 截字操作由前端完成 
                */
	            for (HomeFocusPic sph : sphs) {
                    /*String title = sph.getTitle();
                    if (title.length() > maxFocusTitleLength) {
                        title = title.substring(0, maxFocusTitleLength) + threeDots;
                    }
                    sph.setTitle(title);*/
                    sph.setAbsoluteUrl(true);
                }
                focusList.addAll(sphs);
		    }
        } catch (Exception e) {
            logger.error("", e);
            // 出错则直接使用后面的方法填充焦点图
        }
        int leftSize = 5 - sphSize;
        // 接下来的填充工作，依次从导购、爬房团、宝典各取一条
        leftSize = leftSize > 3 ? 3 : leftSize;

		 // 导购
            /*
             * modified by rogantainwz @2014/06/30 使用新版导购 
            JSONObject json = buildingProposeService.getProposeList(cityId, 1,
                    1);
            List<BuildingPropose> list = buildingProposeService
                    .decorateProposeList(
                            json,
                            AppConstants.Focus_img_width.widthMap
                                    .get(AppConstants.DAOGOULIST_FOCUS_IMG_WIDTH
                                            + device));
            if (null != list && list.size() > 0) {
                BuildingPropose propose = list.get(0);
                HomeFocusPic focus = new HomeFocusPic();
                focus.setPicUrl(propose.getPic());
                String title = propose.getTitle();
                if (title.length() > maxFocusTitleLength) {
                    title = title.substring(0, maxFocusTitleLength) + threeDots;
                }
                focus.setTitle(title);
                focus.setUrl("daogou/" + propose.getId() + "/");
                focusList.add(focus);
                baodianSize--;
            }*/
            
            
//            JSONObject json = daoGouService.getDaoGouList(cityId, 1, 1);
//            if (null != json) {
//                JSONArray jsonArray = json.getJSONArray("data");
//                List<BuildingDaoGou> daoGous = JSON.parseArray(jsonArray.toJSONString(),
//                    BuildingDaoGou.class);
//                if (null != daoGous && daoGous.size() > 0) {
//                    BuildingDaoGou dg = daoGous.get(0);
//                    HomeFocusPic focus = new HomeFocusPic();
//                    focus.setPicUrl(dg.getPic());
//                    String title = dg.getTitle();
//                    if (title.length() > maxFocusTitleLength) {
//                        title = title.substring(0, maxFocusTitleLength) + threeDots;
//                    }
//                    focus.setTitle(title);
//                    focus.setUrl("daogou/" + dg.getId() + "/");
//                    focusList.add(focus);
//                    baodianSize--;
//                }
//            }
        try {
            // 使用新逻辑，可能不再需要从导购获取图片作为焦点图。
			if (leftSize > 0) {
    			// 导购缓存
    			List<BuildingDaoGou> daoGous=daoGouService.getHDaoGouList(cityId, 1);
    			if (null != daoGous && daoGous.size() > 0) {
                    BuildingDaoGou dg = daoGous.get(0);
                    HomeFocusPic focus = new HomeFocusPic();
                    focus.setPicUrl(dg.getPic());
                    /*String title = dg.getTitle();
                    if (title.length() > maxFocusTitleLength) {
                        title = title.substring(0, maxFocusTitleLength) + threeDots;
                    }*/
                    focus.setTitle(dg.getTitle());
                    focus.setUrl("daogou/" + dg.getId() + "/");
                    focusList.add(focus);
                    leftSize--;
    			}
			}
			
			// pafangtuan
			// 使用新逻辑，可能不再需要从爬房团获取图片作为焦点图。
            if (leftSize > 0) {
    			/*
    			 * modified by rogantianwz @ 2014/07/02 使用新的pafangtuanservice接口
    			 */
                Map<String,Object> pftRet = paFangTuanService.getPafangtuanList(cityId, 1, 1);
                if (null != pftRet) {
                    List<Pafangtuan> pftList = (List<Pafangtuan>) pftRet.get(PaFangTuanService.DATA_LIST);
                    if (null != pftList && pftList.size() > 0) {
                        Pafangtuan pft = pftList.get(0);
                        HomeFocusPic focus = new HomeFocusPic();
                        focus.setUrl("pafangtuan/" + pft.getLineId() + "/");
                        focus.setTitle(pft.getTitle());
                        List<PftBuildModel> builds = pft.getBuilds();
                        if(null != builds && builds.size() > 0) {
                            focus.setPicUrl(builds.get(0).getMainPic());
                            focusList.add(focus);
                            leftSize--;
                        }
                    }
                }
            }
            
			// 获取宝典
            // 使用新逻辑，可能不再需要从宝典获取图片作为焦点图。
            if (leftSize > 0) {
    			List<HousingGuide> guideList = housingGuideService
    					.getHousingGuideList(0, leftSize);
    			if (guideList != null && guideList.size() > 0) {
    				for (HousingGuide guide : guideList) {
    					HomeFocusPic focus = new HomeFocusPic();
    					focus.setPicUrl(guide.getPicUrl());
    					/*String title = guide.getTitle();
    					if (title.length() > maxFocusTitleLength) {
    						title = title.substring(0, maxFocusTitleLength)
    								+ threeDots;
    					}*/
    					focus.setTitle(guide.getTitle());
    					focus.setUrl("baodian/" + guide.getId() + "/");
    					focusList.add(focus);
    				}
    
    			}
            }
            
            /*
             * added by zihaoli
             * 焦点图列表存入缓存
             */
            logger.info("now adding home_jiaodiantu list to memcached server, key: " + sb.toString());
            // 设置缓存，focusList不会为null
            cacheHandler.setCache(sb.toString(), CE_HOME_JIAODIANTU_LIST, focusList);
		} catch (Exception e) {
		    logger.error("", e);
			return null;
		}

		return focusList;
	}

}