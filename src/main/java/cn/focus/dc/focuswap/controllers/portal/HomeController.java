package cn.focus.dc.focuswap.controllers.portal;

import static cn.focus.dc.focuswap.config.AppConstants.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.BuildingDaoGou;
import cn.focus.dc.focuswap.model.BuildingPropose;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.DictCityPriceExt;
import cn.focus.dc.focuswap.model.DictDistrictExt;
import cn.focus.dc.focuswap.model.EsNewsExt;
import cn.focus.dc.focuswap.model.EsProjInfoChild;
import cn.focus.dc.focuswap.model.HomeFocusPic;
import cn.focus.dc.focuswap.model.HousingGuide;
import cn.focus.dc.focuswap.model.Information;
import cn.focus.dc.focuswap.model.News;
import cn.focus.dc.focuswap.model.SearchCondition;
import cn.focus.dc.focuswap.service.BuildingDaoGouService;
import cn.focus.dc.focuswap.service.BuildingProposeService;
import cn.focus.dc.focuswap.service.BuildingSearchService;
import cn.focus.dc.focuswap.service.BuildingService;
import cn.focus.dc.focuswap.service.BuildingXinWenService;
import cn.focus.dc.focuswap.service.BuyHouseService;
import cn.focus.dc.focuswap.service.CacheHandlerService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.HomeService;
import cn.focus.dc.focuswap.service.HousingGuideService;
import cn.focus.dc.focuswap.service.NewsService;
import cn.focus.dc.focuswap.service.PaFangTuanService;
import cn.focus.dc.focuswap.service.SearchService.SearchType;
import cn.focus.dc.focuswap.service.XinFangCommonApiService;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.Pafangtuan;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.PftBuildModel;
import cn.focus.dc.news.model.es.EsNews;

import com.alibaba.fastjson.JSONObject;

/**
 * 将home页面拆封成多个子窗口模块
 * 
 * @author zhiweiwen 2013-12-2 上午10:44:13
 */
@Path("home")
public class HomeController {

	// 打折楼盘
	public static final String DZLP = "DZLP";

	// 小户型
	public static final String XHX = "XHX";

	// 最新开盘
	public static final String ZXKP = "ZXKP";

	// 前端截取字数
	public static final int DEFAULTCOUNTS = 10;
	private static Logger logger = LoggerFactory
			.getLogger(HomeController.class);
	private static final String PFT_FIRST_LIST = "one";
	private static final String PFT_SECOND_LIST = "two";

	@Autowired
	private HousingGuideService housingGuideService;

	@Autowired
	private BuildingService buildingService;

	@Autowired
	private CityService cityService;

	@Autowired
	private NewsService newsService;
	@Autowired
	private BuildingXinWenService buildingXinWenService;

	@Autowired
	private BuyHouseService buyHouseService;

	@Autowired
	private BuildingSearchService buildingSearchService;
	@Autowired
	private BuildingProposeService buildingProposeService;
	@Autowired
	private HomeService homeService;
	
	@Autowired 
	private BuildingDaoGouService daogouService;
	@Autowired
	private XinFangCommonApiService xinFangCommonApiService;
	
	@Autowired
    private PaFangTuanService paFangTuanService;
	
	@Autowired
	private CacheHandlerService cacheHandler;
	
	private static final Integer maxFirstBaodianSummaryLength = 29;
	private static final Integer maxFirstBaodianTitleLength = 10;
	private static final Integer maxOtherBaodianTitleLength = 15;
	private static final Integer maxFirstDaogouSummaryLength = 29;
	private static final Integer maxFirstDaogouTitleLength = 10;
	private static final Integer maxOtherDaogouTitleLength = 15;
	private static final String threeDots = "...";

	@Get("test")
	public String test() {
		return "@test";
	}

	/**
	 * 获取焦点图
	 * 
	 * @param inv
	 * @param cityId
	 * @param device
	 * @return
	 */
	@Get("focus")
	public String getFocusPic(Invocation inv, @Param("cityId") int cityId) {
		try {

			int listSize = 0;
			List<HomeFocusPic> focusList = homeService.getFocusPic(cityId);
			if (focusList != null && focusList.size() > 0) {
				listSize = focusList.size();
				inv.addModel("focuslist", focusList);
			}
			inv.addModel("listSize", listSize);
			return "home/phone/hfocus";

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取宝典
	 * 
	 * @param inv
	 * @param cityId
	 * @return
	 */
	@Get("baodian")
	public String getBaoDian(Invocation inv, @Param("cityId") int cityId) {
		try {
			// 获取购房宝典 默认显示5条
			List<HousingGuide> guideList = housingGuideService
					.getHousingGuideList(0, 5);
			
			/**
			 * @author linfangwang
			 * 去截字
			 */
			
			/*if (null != guideList && guideList.size() > 0) {
				int listSize = guideList.size();
				for (int i = 0; i < listSize; i++) {
					HousingGuide guide = guideList.get(i);
					if (i == 0) {
						String summary = guide.getSummary();
						if (StringUtils.isNotBlank(summary)
								&& summary.length() > maxFirstBaodianSummaryLength) {
							summary = summary.substring(0,
									maxFirstBaodianSummaryLength) + threeDots;
							guide.setSummary(summary);
						}
					} else {
						String title = guide.getTitle();
						if (StringUtils.isNotBlank(title)
								&& title.length() > maxOtherBaodianTitleLength) {
							title = title.substring(0,
									maxOtherBaodianTitleLength) + threeDots;
							guide.setTitle(title);
						}
					}
				}
			}*/
			// guideList = setTime(guideList);
			inv.addModel("guideList", guideList);
			inv.addModel("count", guideList.size());
			return "home/phone/hbaodiannew";
		} catch (Exception e) {
			logger.info("", e.getMessage());
			return null;
		}
	}

	/**
	 * 爬房团
	 * 
	 * @param inv
	 * @param cityId
	 * @param device
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
    //@Get("pft")
	public String getPFTList(Invocation inv, @Param("cityId") int cityId,
			@Param("device") String device) {
		try {
			Map<String, List> pftlistMap = getPFTList(cityId);
			List<Pafangtuan> firstList = pftlistMap.get(PFT_FIRST_LIST);
			List<Pafangtuan> secondList = pftlistMap.get(PFT_SECOND_LIST);
			decratePftList(firstList, device);
			decratePftList(secondList, device);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("M月d日");
			inv.addModel("secondList", firstList);
			if (firstList.size() > 0) {
				Date firstDate = sdf1.parse(String.valueOf(firstList.get(0)
						.getActiveDate()));
				inv.addModel("time2", sdf2.format(firstDate));
			}
			inv.addModel("firstList", secondList);
			if (secondList.size() > 0) {
				Date secondDate = sdf1.parse(String.valueOf(secondList.get(0)
						.getActiveDate()));
				inv.addModel("time1", sdf2.format(secondDate));
			} else {
				inv.addModel("show", "true");
			}
			inv.addModel("count", firstList.size() + secondList.size());
			if (secondList.size() == 0 && firstList.size() == 0) {
				inv.addModel("showable", 0);
			}
			return "home/" + device + "/hpafangtuan";
		} catch (Exception e) {
			logger.info("", e.getMessage());
			return null;
		}
	}
	
	/**
	 * 优化后的爬房团模块
	 * modified by rogantianwz @ 22014/07/02 对爬房团模块进行优化
	 * @param inv
	 * @param cityId
	 * @param device
	 * @return
	 */
	@SuppressWarnings("unchecked")
    @Get("pft")
	public String getPFTListNew(Invocation inv, @Param("cityId") int cityId) {
	    try {
	        Map<Date, Object> listMap = paFangTuanService.getLatest2DaysList(cityId);
	        if (null == listMap || listMap.size() < 1) {
	            return null;
	        }
	        List<Pafangtuan> firstList = null;
	        List<Pafangtuan> secondList = null;
	        SimpleDateFormat sdf = new SimpleDateFormat("M月d日");
	        int count = 0;
	        Set<Date> keySet = listMap.keySet();
	        for (Date d : keySet) {
	            if (d.equals(PaFangTuanService.SPECIAL_DATE_FOR_COUNT)) {
                    count = (Integer) listMap.get(d);
                    continue;
                }
	            if (null == firstList) {
	                firstList = (List<Pafangtuan>) listMap.get(d);
	                inv.addModel("first", firstList);
	                inv.addModel("firstTime", sdf.format(d));
	            } else if (null == secondList){
	                secondList = (List<Pafangtuan>) listMap.get(d);
	                inv.addModel("second", secondList);
	                inv.addModel("secondTime", sdf.format(d));
	            }
	        }
	        inv.addModel("count", count);
	        
	        return "home/phone/hpafangtuannew";
	    } catch (Exception e) {
	        logger.error("", e);
	        return null;
	    }
	}

	/**
	 * 获取导购
	 * 
	 * @param inv
	 * @param cityId
	 * @return
	 */
	//@Get("daogou")
	public String getDaogouList(Invocation inv, @Param("cityId") int cityId,
			@Param("device") String device) {
		try {
			JSONObject json = buildingProposeService.getProposeList(cityId, 1,
					5);
			List<BuildingPropose> list = buildingProposeService
					.decorateProposeList(
							json,
							AppConstants.Focus_img_width.widthMap
									.get(AppConstants.DAOGOULIST_FOCUS_IMG_WIDTH
											+ device));
			if (null == list || list.size() == 0) {
				return null;
			}
			int listSize = list.size();
			for (int i = 0; i < listSize; i++) {
				BuildingPropose propose = list.get(i);
				if (i == 0) {
					String promotion = propose.getPromotion();
					if (StringUtils.isNotBlank(promotion)
							&& promotion.length() > maxFirstDaogouSummaryLength) {
						promotion = promotion.substring(0,
								maxFirstDaogouSummaryLength) + threeDots;
						propose.setPromotion(promotion);
					}
				} else {
					String title = propose.getTitle();
					if (StringUtils.isNotBlank(title)
							&& title.length() > maxOtherDaogouTitleLength) {
						title = title.substring(0, maxOtherDaogouTitleLength)
								+ threeDots;
						propose.setTitle(title);
					}
				}
				// propose.setPubDate(DateUtils.stringPattern(propose.getPubDate(),"yyyy-MM-dd",
				// "M月d日"));
			}
			inv.addModel("list", list);
			inv.addModel("count", list.size());
			return "home/" + device + "/hdaogounew";
		} catch (Exception e) {
			logger.info("", e.getMessage());
			return null;
		}
	}
	
	/**
	 * 获取新版导购
	 * @param inv
	 * @param cityId
	 * @param device
	 * @return
	 */
	@Get("daogou")
	public String getDaogouListNew(Invocation inv,@Param("cityId") int cityId) {
	    try {
	    	
//	        JSONObject json = daogouService.getDaoGouList(cityId, 1, 5);
//	        if (null != json) {
//	            JSONArray jsonArray = json.getJSONArray("data");
//	            List<BuildingDaoGou> daoGous = JSON.parseArray(jsonArray.toJSONString(),
//                        BuildingDaoGou.class);
//	            if (null != daoGous && daoGous.size() > 0) {
//	                int listSize = daoGous.size();
//	                for (int i = 0; i< listSize; i++) {
//	                    BuildingDaoGou dg = daoGous.get(i);
//	                    dg.setOnlineTime(DateUtils.stringPattern(dg.getOnlineTime(), "yyyy-MM-dd", "M月d日"));
//	                    if (0 == i) {
//	                        String promotion = dg.getPromotion();
//	                        if (StringUtils.isNotBlank(promotion)
//	                                && promotion.length() > maxFirstDaogouSummaryLength) {
//	                            promotion = promotion.substring(0,
//	                                    maxFirstDaogouSummaryLength) + threeDots;
//	                            dg.setPromotion(promotion);
//	                        }
//	                    } else {
//	                        String title = dg.getTitle();
//	                        if (StringUtils.isNotBlank(title)
//	                                && title.length() > maxOtherDaogouTitleLength) {
//	                            title = title.substring(0, maxOtherDaogouTitleLength)
//	                                    + threeDots;
//	                            dg.setTitle(title);
//	                        }
//	                    }
//	                }
//	                inv.addModel("list", daoGous);
//	                inv.addModel("count", daoGous.size());
//	                return "home/" + device + "/hdaogounew";
//	            }
//	        }
	    	
	    	//使用缓存后的导购
			List<BuildingDaoGou> daoGous = daogouService.getHDaoGouList(cityId,5);
			if (null != daoGous && daoGous.size() > 0) {
				/*
				 * modified by rogantian @ 20140826 不显示日期
				 * for (int i = 0; i < listSize; i++) {
					BuildingDaoGou dg = daoGous.get(i);
					dg.setOnlineTime(DateUtils.stringPattern(
							dg.getOnlineTime(), "yyyy-MM-dd", "M月d日"));
					
					*//**
					 * @author linfangwang
					 * 去截字
					 *//*
					if (0 == i) {
						String promotion = dg.getPromotion();
						if (StringUtils.isNotBlank(promotion)
								&& promotion.length() > maxFirstDaogouSummaryLength) {
							promotion = promotion.substring(0,
									maxFirstDaogouSummaryLength) + threeDots;
							dg.setPromotion(promotion);
						}
					} else {
						String title = dg.getTitle();
						if (StringUtils.isNotBlank(title)
								&& title.length() > maxOtherDaogouTitleLength) {
							title = title.substring(0,
									maxOtherDaogouTitleLength) + threeDots;
							dg.setTitle(title);
						}
					}
				}*/
				inv.addModel("list", daoGous);
				inv.addModel("count", daoGous.size());
				return "home/phone/hdaogounew";
			}
	        
	        
	    } catch (Exception e) {
            logger.info("", e.getMessage());
        }
	  return null;  
	}

	/**
	 * 获取新闻
	 * 
	 * @param inv
	 * @param cityId
	 * @return
	 */
	@Get("news")
	public String getNews(Invocation inv, @Param("cityId") int cityId) {
		try {
			// 获取资讯新闻列表
			List<EsNewsExt> newsList = buildingXinWenService.getYaoWenByIndex(
					cityId, true);
			List<News> newsListReal = new ArrayList<News>();
			int min = 6;
			if (newsList != null && newsList.size() < 6) {
				min = newsList.size();
			}
			for (int i = 0; i < min; i++) {
				Information realNew = new Information();
				EsNewsExt news = null;
				EsNews enews = null;
				try {
				     news = newsList.get(i);
				} catch (Exception e) {
					//newList中获取的对象可能是两种类型　EsNews EsNewsExt,容错处理．
					 enews = newsList.get(i);
					 news = new EsNewsExt();
					 BeanUtils.copyProperties(news, enews);
				}

				news.setPartContent(buildingXinWenService.subPartContent(news
						.getPartContent()));
				if(null!=news.getNewsId()) {
				realNew.setNewsId(Integer.parseInt(news.getNewsId().toString()));
				}
				realNew.setTitle(news.getTitle());
				realNew.setDescription(news.getPartContent());
				realNew.setTime(news.getShowTime().toString());
				String newsUrl = news.getNewsUrl();
				
				if(StringUtils.isNotBlank(newsUrl)) {
					if(!newsUrl.startsWith("http")) {
						newsUrl = "http://" + newsUrl;
					}
					realNew.setNewsUrl(newsUrl);
				}
				
				String imgLogo = news.getImgLogo();
				if (StringUtils.isNotBlank(imgLogo)) {
					// 编辑未修改图片
					if (imgLogo.indexOf("/120/") != -1) {
						realNew.setImgLogo(news.getImgLogo()
								.replaceFirst("/120/", "/240/")
								.replaceFirst(" ", ""));
					}
					// 编辑修改图片
					else {
						imgLogo = imgLogo.replaceAll("http://", "");
						String front = imgLogo.substring(0,
								imgLogo.indexOf("/") + "/".length());
						String back = imgLogo.substring(
								imgLogo.indexOf("/"), imgLogo.length());
						imgLogo = "http://" + front + FOCUS_IMG_SET + ",w_"
								+ 200 + back;
						realNew.setImgLogo(imgLogo);
					}
				}
				/**
				 * @author linfangwang
				 * 新闻标题+摘要去截字
				 */
				/*if (StringUtils.isNotBlank(realNew.getDescription())
						&& realNew.getDescription().length() > 40) {
					realNew.setDescription(realNew.getDescription().substring(
							0, 40));
				}*/
				/*if (StringUtils.isNotBlank(realNew.getTitle())
						&& realNew.getTitle().length() > 17) {
					realNew.setTitle(realNew.getTitle().substring(0, 17)
							+ "...");
				}*/
				decorateNews(realNew);
				newsListReal.add(realNew);
			}
			if (newsListReal.get(0).getImgLogo() == null) {
				inv.addModel("logo", "");
			}
			inv.addModel("newsList", newsListReal);
			inv.addModel("count", newsListReal.size());
			return "home/phone/hnewsnew";
		} catch (Exception e) {
			logger.info("", e.getMessage());
			return null;
		}
	}

	private void decorateNews(News news) {
		try {
			SimpleDateFormat sdf3 = new SimpleDateFormat("M月d日");
			Date d = new Date(Long.parseLong(news.getTime()));
			news.setTime(sdf3.format(d));
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 获取在售楼盘和选择条件
	 * 
	 * @param inv
	 * @param cityId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Get("tiaojian")
	public String getTJ(Invocation inv, @Param("cityId") int cityId,
			@Param("device") String device) {
		try {
			DictCity city = cityService.getCity(cityId);
			// 获取打折楼盘列表 默认3条
			cn.focus.dc.focuswap.model.SearchCondition condition = buyHouseService
					.getHomeHouseCondition(city.getCityId(),
							HOME_BUILDING_LIST_PAGENO,
							HOME_BUILDING_LIST_PAGESIZE, "", "");
			Map<String, Object> disCountHouseMap = buildingSearchService
					.filterBuilding(condition);
			int total = (Integer) disCountHouseMap.get("total");
			inv.addModel("total", total);
			Map<SearchType, Object> conditionMap = buildingSearchService
					.getConditions(city.getCityId(), false);
			// 获取价格条件
			List<DictCityPriceExt> priceList = (List<DictCityPriceExt>) conditionMap
					.get(SearchType.PRICE);

			inv.addModel("priceList", priceList);
			return "home/" + device + "/htiaojian";
		} catch (Exception e) {
			logger.info("", e.getMessage());
			return null;
		}
	}

	/**
	 * 获取打折楼盘
	 * 
	 * @param inv
	 * @param cityId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Get("dazheloupan")
	public String getDZLP(Invocation inv, @Param("cityId") int cityId) {
		try {
		    StringBuilder keyBuilder = new StringBuilder(CK_HOME_DZLP_LIST);
            keyBuilder.append(cityId);
            Map<String, Object> cached = cacheHandler.getCacheValue(keyBuilder.toString(), Map.class);
            if (null == cached) {
                DictCity city = cityService.getCity(cityId);
                // 获取打折楼盘列表 默认3条
                cn.focus.dc.focuswap.model.SearchCondition condition = buyHouseService
                        .getHomeHouseCondition(city.getCityId(),
                                HOME_BUILDING_LIST_PAGENO,
                                HOME_BUILDING_LIST_PAGESIZE, DZLP, "");
                Map<String, Object> disCountHouseMap = buildingSearchService
                        .filterBuilding(condition);
                List<EsProjInfoChild> houseList = (List<EsProjInfoChild>) disCountHouseMap
                        .get("buildingList");
                houseList = buyHouseService.switchBuildingParameter(houseList,
                        city, 0);
                houseList = buildingService.setBuildingListPrice(houseList);
                
                if (null != houseList && houseList.size() > 0) {
                    disCountHouseMap.put("buildingList", houseList);
                    cached = disCountHouseMap;
                    cacheHandler.addCache(keyBuilder.toString(), CE_HOME_DZLP_LIST, cached);
                    logger.info("cached dazheloupan list" + cityId);
                }
            }
            if (null == cached) {
                return null;
            }
            List houseList = (List) cached.get("buildingList");
            if (null == houseList || houseList.size() == 0) {
                return null;
            }
			inv.addModel("disCouentHouse", houseList);
			inv.addModel("nums", houseList.size());
			inv.addModel("total", cached.get("total"));
			return "home/phone/hdazhenew";
		} catch (Exception e) {
			logger.info("", e.getMessage());
			return null;
		}
	}

	/**
	 * 获取最新开盘
	 * 
	 * @param inv
	 * @param cityId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Get("zuixinkaipan")
	public String getZXKP(Invocation inv, @Param("cityId") int cityId) {
		try {
		    StringBuilder keyBuilder = new StringBuilder(CK_HOME_ZXKP_LIST);
		    keyBuilder.append(cityId);
		    Map<String, Object> cached = cacheHandler.getCacheValue(keyBuilder.toString(), Map.class);
		    if (null == cached) {
		        DictCity city = cityService.getCity(cityId);
		        // 获取最新开盘列表 默认3条
		        cn.focus.dc.focuswap.model.SearchCondition condition = buyHouseService
		                .getHomeHouseCondition(city.getCityId(),
		                        HOME_BUILDING_LIST_PAGENO,
		                        HOME_BUILDING_LIST_PAGESIZE, ZXKP, "");
		        Map<String, Object> newHouseMap = buildingSearchService
		                .filterBuilding(condition);
		        List<EsProjInfoChild> houseList = (List<EsProjInfoChild>) newHouseMap.get("buildingList");
		        houseList = buyHouseService.switchBuildingParameter(houseList,
		                city, 0);
		        houseList = buildingService.setBuildingListPrice(houseList);
		        
		        if (null != houseList && houseList.size() > 0) {
    		        newHouseMap.put("buildingList", houseList);
    		        cached = newHouseMap;
    		        cacheHandler.addCache(keyBuilder.toString(), CE_HOME_ZXKP_LIST, cached);
    		        logger.info("cached zuixinkaipan list " + cityId);
		        }
		    }
		    if (null == cached) {
		        return null;
		    }
		    List houseList = (List) cached.get("buildingList");
		    if (null == houseList || houseList.size() == 0) {
		        return null;
		    }
			inv.addModel("newHouse", houseList);
			inv.addModel("nums", houseList.size());
			inv.addModel("total", cached.get("total"));
			return "home/phone/hzuixinnew";
		} catch (Exception e) {
			logger.info("", e.getMessage());
			return null;
		}
	}

	/**
	 * 获取小户型
	 * 
	 * @param inv
	 * @param cityId
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Get("xiaohuxing")
	public String getXHX(Invocation inv, @Param("cityId") int cityId) {
		try {
		    StringBuilder keyBuilder = new StringBuilder(CK_HOME_XHX_LIST);
            keyBuilder.append(cityId);
            Map<String, Object> cached = cacheHandler.getCacheValue(keyBuilder.toString(), Map.class);
            if (null == cached) {
                DictCity city = cityService.getCity(cityId);
                // 获取小户型列表 默认3条
                cn.focus.dc.focuswap.model.SearchCondition condition = buyHouseService
                        .getHomeHouseCondition(city.getCityId(),
                                HOME_BUILDING_LIST_PAGENO,
                                HOME_BUILDING_LIST_PAGESIZE, XHX, "");
                Map<String, Object> littleHouseMap = buildingSearchService
                        .filterBuilding(condition);
                List<EsProjInfoChild> houseList = (List<EsProjInfoChild>) littleHouseMap
                        .get("buildingList");
                houseList = buyHouseService.switchBuildingParameter(houseList,
                        city, 0);
                houseList = buildingService.setBuildingListPrice(houseList);
                
                if (null != houseList && houseList.size() > 0) {
                    littleHouseMap.put("buildingList", houseList);
                    cached = littleHouseMap;
                    cacheHandler.addCache(keyBuilder.toString(), CE_HOME_XHX_LIST, cached);
                    logger.info("cached xiao huxing list " + cityId);
                }
            }
            if (null == cached) {
                return null;
            }
            List houseList = (List) cached.get("buildingList");
			if (null == houseList || houseList.size() == 0) {
			    return null;
			}
			inv.addModel("littleHouse", houseList);
			inv.addModel("nums", houseList.size());
			inv.addModel("total", cached.get("total"));
			return "home/phone/hxiaohuxingnew";
		} catch (Exception e) {
			logger.info("", e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
    @Get("zhaofang")
	public String getZF(Invocation inv, @Param("cityId") int cityId) {
		try {
			Map<SearchType, Object> conditionMap = buildingSearchService
					.getConditions(cityId, false);
			// 获取价格条件
			List<DictCityPriceExt> priceList = (List<DictCityPriceExt>) conditionMap
					.get(SearchType.PRICE);

			inv.addModel("priceList", priceList);
			// 获取区域条件
			List<DictDistrictExt> distList = (List<DictDistrictExt>) conditionMap
					.get(SearchType.DISTRICT);
			inv.addModel("hotList", distList);
			
			StringBuilder keyBuilder = new StringBuilder(CK_HOME_ZHAOFANG_TOTAL);
			keyBuilder.append(cityId);
			Integer total = cacheHandler.getCacheValue(keyBuilder.toString(), Integer.class);
			if (null == total || total <= 0) {
			    SearchCondition condition = new SearchCondition(null, 1, 1, cityId);
			    Map<String, Object> buildingList = buildingSearchService
			            .filterBuilding(condition);
			    if (null != buildingList) {
			        total = (Integer) buildingList.get("total");
			        if (null != total && total > 0) {
			            cacheHandler.addCache(keyBuilder.toString(), CE_HOME_ZHAOFANG_TOTAL, total);
			            logger.info("cached zhaofang list " + cityId);
			        }
			    }
			}
			inv.addModel("total", total);
			return "home/phone/hzhaofangnew";
		} catch (Exception e) {
			logger.info("", e.getMessage());
			return null;
		}
	}

	/**
	 * pad 版截字
	 * 楼盘地址截字
	 * 楼盘户型截字
	 * 楼盘名称截字
	 * @param houseList
	 * @return
	 */
	private List<EsProjInfoChild> setAddressAndRoominfo(List<EsProjInfoChild> houseList) {
		for (EsProjInfoChild info : houseList) {
			String address = info.getProjAddress();
			if (address.length() > DEFAULTCOUNTS) {
				address = address.substring(0, DEFAULTCOUNTS) + "...";
				info.setProjAddress(address);
			}
			
			if(info.getRoomInfo().length()>14) {
				info.setRoomInfo(info.getRoomInfo().substring(0, 14)+"...");
			}
		}
		return houseList;
	}

	/**
	 * 楼盘地址截字
	 * 
	 * @param houseList
	 * @return
	 */
	private List<EsProjInfoChild> setProjName(List<EsProjInfoChild> houseList) {
		for (EsProjInfoChild info : houseList) {
			String name = info.getProjNameNoSplit();
			if (name.length() > 9) {
				name = name.substring(0, 8) + "...";
				info.setProjNameNoSplit(name);
			}
		}
		return houseList;
	}

	@SuppressWarnings("rawtypes")
    private Map<String, List> getPFTList(int cityId) {
		Map<String, Integer> pftMap = new HashMap<String, Integer>();
		Map<String, List> pftListMap = new HashMap<String, List>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			String now = sdf.format(date);
			long nowtime = Long.parseLong(now);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH, 2);
			long time = c.getTime().getTime() / 1000;
			List<Pafangtuan> list = xinFangCommonApiService.getPftList(cityId,
					60, 1, time);
			if (null == list) {
				return null;
			}
			Collections.sort(list);
			long tmptime = 0l;
			boolean flag = false;
			int count = 0;
			List<Pafangtuan> pftList = new ArrayList<Pafangtuan>();
			for (Pafangtuan pft : list) {
			    long ad = Long.valueOf(pft.getActiveDate());
				if (ad < nowtime) {
					continue;
				} else {
					if (!flag && count < 2) {
						// flag 标记新的时间点 false表示当前对象时间点失效
						flag = true;
						// 当前对象时间
						tmptime = ad;
						pftList.add(pft);
					} else {
						if (ad == tmptime) {
							pftList.add(pft);
						} else {
							if (count < 1) {
								pftMap.put("firtsize", pftList.size());
								pftList.add(pft);
								tmptime = ad;
							}
							if (count > 1) {
								break;
							}
							count++;
						}
					}
				}
			}
			if (pftList.size() > 0) {
				int begin = 0;
				if(pftMap.keySet().size()==0) {
					begin = pftList.size();
				} else {
					begin = pftMap.get("firtsize");
				}
				pftListMap.put("one",pftList.subList(0, begin));
				pftListMap.put("two",pftList.subList(begin,pftList.size()));
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return pftListMap;
	}

	public List<Pafangtuan> getPftList(int cityId, String device) {
		List<Pafangtuan> pftList = new ArrayList<Pafangtuan>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			String now = sdf.format(date);
			long nowtime = Long.parseLong(now);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH, 2);
			long time = c.getTime().getTime() / 1000;
			List<Pafangtuan> list = xinFangCommonApiService.getPftList(cityId,
					50, 1, time);
			if (null == list) {
				return null;
			}
		    Collections.sort(list);
			long tmptime = 0l;
			boolean flag = false;
			int count = 0;
			for (Pafangtuan pft : list) {
			    long ad = Long.valueOf(pft.getActiveDate());
				if (ad < nowtime) {
                   continue;
				} else {
					if (!flag && count < 2) {
						// flag 标记新的时间点 false表示当前对象时间点失效
						flag = true;
						// 当前对象时间
						tmptime = ad;
						pftList.add(pft);
					} else {
						if (ad == tmptime) {
							pftList.add(pft);
						} else {
							if (count > 1) {
								break;
							}
							flag = false;
							count++;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		decratePftList(pftList, device);
		return pftList;
	}

	public void decratePftList(List<Pafangtuan> list, String device) {
		if (null != list && list.size() > 0) {
			for (Pafangtuan pft : list) {
				String title = pft.getTitle();
				String cotent = pft.getLightspot();
				List<PftBuildModel> builds = pft.getBuilds();
				int length = "pad".equals(device) ? 16 : 1500;
				if (StringUtils.isNotBlank(title) && title.length() > length) {
					title = title.substring(0, length - 1) + "...";
					pft.setTitle(title);
				}
				if ("pad".equals(device) && StringUtils.isNotBlank(cotent) && cotent.length()>18) {
					cotent = cotent.substring(0, 18) + "...";
					pft.setLightspot(cotent);
				}
				if (builds.size() > 0) {
					pft.setPic(builds.get(0).getMainPic());
					pft.setBuildNum(builds.size());
				}
			}
		}
	}

}