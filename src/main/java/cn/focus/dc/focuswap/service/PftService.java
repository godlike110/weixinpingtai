package cn.focus.dc.focuswap.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.focus.dc.focuswap.service.XinFangCommonApiService.Pafangtuan;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.PftBuildModel;

@Service
public class PftService {

	public static Log logger = LogFactory.getLog(PftService.class);
	@Autowired
	private XinFangCommonApiService xinFangCommonApiService;

	public Map<String, List<Pafangtuan>> getPFTList(int cityId) {
		Map<String, Integer> pftMap = new HashMap<String, Integer>();
		Map<String, List<Pafangtuan>> pftListMap = new HashMap<String, List<Pafangtuan>>();
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
			List<Pafangtuan> pftList = new ArrayList<Pafangtuan>();
			for (Pafangtuan pft : list) {
				long ad = Long.parseLong(pft.getActiveDate());
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
							}
							if (count > 1) {
								break;
							}
							flag = false;
							count++;
						}
					}
				}
			}
			Integer firstSize = pftMap.get("firtsize");
			if(firstSize==null) {
				firstSize = pftList.size();
			}
			if (pftList.size() > 0 ) {
				pftListMap.put("one",
						pftList.subList(0, firstSize));
				pftListMap
						.put("two",
								pftList.subList(firstSize,
										pftList.size()));
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return pftListMap;
	}

	/**
	 * 楼盘相关爬房团
	 * 
	 * @param groupId
	 * @param cityId
	 * @param device
	 * @return
	 */
	public List<Pafangtuan> getPftList(int groupId, int cityId, String device) {
		List<Pafangtuan> pftList = new ArrayList<Pafangtuan>();
		try {
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH, 2);
			long time = c.getTime().getTime() / 1000;
			List<Pafangtuan> list = xinFangCommonApiService.getPftList(cityId,
					50, 1, time);
			if (null == list) {
				return null;
			}
			// Collections.sort(list);
			
			for (Pafangtuan pft : list) {
				
				/**
				 * @author linfangwang
				 * signUpStatus:状态有误，使用 applyRemainDays 判断，
				 * 因调用该接口的方法已停用，故未做改变
				 */
				
				if(pft.getSignUpStatus()==4) {
					continue;
				}
				List<PftBuildModel> builds = pft.getBuilds();
				for (PftBuildModel pbm : builds) {
					if (pbm.getGroupId() == groupId) {
						pftList.add(pft);
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		decratePftList(pftList, device);
		return pftList;
	}

	/**
	 * 城市爬房团
	 * 
	 * @param cityId
	 * @param device
	 * @return
	 */
	public List<Pafangtuan> getCityPFTList(int cityId, String device) {
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
			// Collections.sort(list);
			long tmptime = 0l;
			boolean flag = false;
			int count = 0;
			pftList = new ArrayList<Pafangtuan>();
			for (Pafangtuan pft : list) {
				long ad = Long.parseLong(pft.getActiveDate());
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
		return pftList;
	}

	public void decratePftList(List<Pafangtuan> list, String device) {
		if (null != list && list.size() > 0) {
			for (Pafangtuan pft : list) {
				String title = pft.getTitle();
				List<PftBuildModel> builds = pft.getBuilds();
				int length = "phone".equals(device) ? 18 : 18;
				if (StringUtils.isNotBlank(title) && title.length() > length) {
					title = title.substring(0, length - 1) + "...";
					pft.setTitle(title);
				}
				if (builds.size() > 0) {
					pft.setPic(builds.get(0).getMainPic());
					pft.setBuildNum(builds.size());
				}
			}
		}
	}
}