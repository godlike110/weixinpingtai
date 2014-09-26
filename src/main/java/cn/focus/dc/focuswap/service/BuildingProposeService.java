package cn.focus.dc.focuswap.service;

import static cn.focus.dc.focuswap.config.AppConstants.FOCUS_IMG_SET;
import static cn.focus.dc.focuswap.config.AppConstants.XINFANG_TYPE_DAOGOU_CONTENT_URL;
import static cn.focus.dc.focuswap.config.AppConstants.XINFANG_TYPE_DAOGOU_LIST_URL;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.BuildingPropose;
import cn.focus.dc.focuswap.utils.DateUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 楼盘导购 by kane
 */
@Service
public class BuildingProposeService {

	private static Log logger = LogFactory.getLog(BuildingProposeService.class);

	private static final String JSON_ERRORCODE_NAME = "errorCode";

	private static final String JSON_DATA_NAME = "data";

	private static final String HTTP = "http://";
	
	//暂时需要被删除的div
	private static final String DVI_NEEDTO_DELETE = "daogouloupaninfo";

	@Autowired
	private InterfaceService interfaceService;

	@Autowired
	private CacheHandlerService cacheHandler;

	public BuildingPropose getPropose(Integer pId, Integer cityId) {
		Map<String, Object> urlVariables = new HashMap<String, Object>();
		urlVariables.put("proposeId", pId);
		urlVariables.put("cityId", cityId);
		BuildingPropose bp = null;
		try {
			JSONObject jo = interfaceService.getJSONFromInterface(
					XINFANG_TYPE_DAOGOU_CONTENT_URL, urlVariables);
			jo = extractJSONData(jo);
			bp = JSON.toJavaObject(jo, BuildingPropose.class);
		} catch (Exception e) {
			logger.error("", e);
		}
		return bp;
	}

	public JSONObject getProposeList(Integer cityId, Integer pageNo,
			Integer pageSize) {
		Map<String, Object> urlVariables = new HashMap<String, Object>();
		urlVariables.put("cityId", cityId);
		urlVariables.put("pageNo", pageNo);
		urlVariables.put("pageSize", pageSize);
		JSONObject jo = null;
		try {
			jo = interfaceService.getJSONFromInterface(
					XINFANG_TYPE_DAOGOU_LIST_URL, urlVariables);
			jo = extractJSONData(jo);
		} catch (Exception e) {
			logger.error("", e);
		}
		return jo;
	}
	
	/**
	 * 把PC端的一条导购Id转换成移动端的Id
	 * @param pcId
	 * @return <=0 表示失败或者不存在，>0 为正常
	 */
	public int transProposeId(Integer pcId) {
	    if (null != pcId) {
	        ImmutableMap<String,Object> urlVariables = ImmutableMap.of("daogouId", (Object)pcId);
            JSONObject jo = interfaceService.getJSONFromInterface(AppConstants.XINFANG_DAOGOU_PC2MB_URL, urlVariables);
            if (null != jo) {
                Integer errorCode = jo.getInteger(JSON_ERRORCODE_NAME);
                Integer ret = jo.getInteger(JSON_DATA_NAME);
                if (null != errorCode && errorCode == 0 && null != ret) {
                    return ret;
                }
            }
	    }
	    return -1;
	}

	/*
	 * 从标准的接口返回值中提取所需数据
	 * 
	 * @param jsonObject
	 * 
	 * @return
	 */
	public JSONObject extractJSONData(JSONObject jsonObject) {
		if (null != jsonObject) {
			Integer errorCode = jsonObject.getInteger(JSON_ERRORCODE_NAME);
			if (null != errorCode && errorCode == 0) {
				return jsonObject.getJSONObject(JSON_DATA_NAME);
			}
		}
		return null;
	}

	public void decorateDate(BuildingPropose bp) {
		bp.setPubDate(DateUtils.stringPattern(bp.getPubDate(), "yyyy-MM-dd",
				"yyyy年M月d日"));
		bp.setContent(convertContent(bp.getContent()));
	}
	
	/**
	 * 去除内容内部的楼盘展示div
	 * @param content
	 * @return
	 */
	public static String convertContent(String content) {
		
		Document doc = Jsoup.parseBodyFragment(content);
		Elements divEles = doc.getElementsByClass(DVI_NEEDTO_DELETE);
		StringBuffer pStr = new StringBuffer();
		int index = 0;
		for(Element ele:divEles) {
			String imgtag = ele.getElementsByTag("img").toString();
			imgtag="<p>" + imgtag + "</p>";
			//doc.getElementsByClass(DVI_NEEDTO_DELETE).get(index++).html("").html(imgtag);
			divEles.get(index++).html("").html(imgtag);
		}
		
		return doc.getElementsByTag("body").html();
		
	}

	/**
	 * 
	 * @param bpJson
	 * @param width
	 *            图片尺寸
	 * @return
	 */
	public List<BuildingPropose> decorateProposeList(JSONObject bpJson,
			String width) {
		if (bpJson != null) {
			JSONArray bpArray = bpJson.getJSONArray("data");
			List<BuildingPropose> bpList = null;
			if (bpArray != null) {
				bpList = JSON.parseArray(bpArray.toJSONString(),
						BuildingPropose.class);
				for (BuildingPropose bp : bpList) {
					// 修改日期
					bp.setPubDate(DateUtils.stringPattern(bp.getPubDate(),
							"yyyy-MM-dd", "M月d日"));
					// 修改相关楼盘
					String groupName = StringUtils
							.join(bp.getBuildNames(), "，");
					if (StringUtils.isBlank(groupName)) {
						groupName = "暂无";
					} else if (groupName.length() > 50) {
						groupName = groupName.substring(0, 50) + "...";
					}
					bp.setBuildingNameShow(groupName);
					// 修改title
					if (bp.getTitle().length() > 23) {
						bp.setTitle(bp.getTitle().substring(0, 23) + "...");
					}
					// 修改图片尺寸,域名后加入参数
					String picUrl = bp.getPic();
					if (StringUtils.isNotBlank(picUrl)) {
						try {
							picUrl = picUrl.replaceAll(HTTP, "");
							String front = picUrl.substring(0,
									picUrl.indexOf("/") + "/".length());
							String back = picUrl.substring(picUrl.indexOf("/"),
									picUrl.length());
							picUrl = HTTP + front + FOCUS_IMG_SET + ",w_"
									+ width + back;
						} catch (StringIndexOutOfBoundsException e) {
							logger.info("pic is not regular url");
						}
						bp.setPic(picUrl);
					}
				}
			}
			return bpList;
		} else {
			return null;
		}
	}
	

	/**
	 * 判断各城市站是否有导购
	 * 
	 * @param cityId
	 * @return 0 没有 1 有
	 */
	public int getProposeStatus(int cityId) {
		Integer status = AppConstants.NO_PROPOSE;
		try {
			String cacheKey = AppConstants.CK_WAP_PROPOSE_STATUS + cityId;
			status = cacheHandler.getCacheValue(cacheKey, Integer.class);
			if (null == status) {
				JSONObject bpJson = getProposeList(cityId, 1, 1);
				Integer cacheStatus = bpJson == null ? 0 : bpJson.getIntValue("total");
				cacheHandler.setCache(cacheKey,
						AppConstants.CE_WAP_PROPOSE_TIME, cacheStatus);
				logger.info("set propose status to cache: " + cityId);
				status = cacheStatus;
			}
		} catch (Exception e) {
			logger.error(" ",e);
			// cache 出错的话 直接调用接口
			JSONObject bpJson = getProposeList(cityId, 1, 1);
			int cacheStatus = bpJson == null ? 0 : 1;
			status = cacheStatus;
		}

		return status;
	}
	
	public static void main(String argv[]) {
		
		String content = "<div class=\"afdae\"><p>[搜狐焦点 初步海选]天宫院没房了？随着住总万科橙新推房源的售罄不少人感叹。据统计，天宫院区域共有14新盘，有房在售的普宅仅三个：熙悦春天、众美城、金融街融汇。</p><div class=\"daogouloupaninfo\" data-title=\"返回顶部\">这个东西必须取出</div></div>";
		String a  = BuildingProposeService.convertContent(content);
		System.out.println(a);
	}

}
