package cn.focus.dc.focuswap.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.dao.DaoGouDAO;
import cn.focus.dc.focuswap.model.BuildingDaoGou;
import cn.focus.dc.focuswap.utils.DateUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author linfangwang
 */
@Service
public class BuildingDaoGouService {

	private static final Log logger=LogFactory.getLog(BuildingDaoGouService.class);
	
	@Autowired
	private InterfaceService interfaceService;
	
	@Autowired
	private BuildingProposeService buildingProposeService;
	
	@Autowired
	private DaoGouDAO daoGouDao;
	//常量
	private static final String JSON_DATA_NAME="data";
	private static final String JSON_KEYWORD_NAME="keyword";
	private static final String JSON_EDITOR_TITLE = "编辑";
	
	@Autowired
    private CacheHandlerService cacheHandler;
	
	//导购列表缓存key值
	private static final String DAOGOU_CACHE_KEY="wap:home:daogou:list:";
	
	//导购缓存期限
	private static final int DAOGOU_CACHE_EXP=2*3600;
	
	/**
	 * 新版首页，导购缓存，
	 * @param cityId
	 * @param pageSize  导购（5），焦点图（1）
	 * @return
	 */
	public List<BuildingDaoGou> getHDaoGouList(Integer cityId,int pageSize)
	{
		//key 值构建
		StringBuilder sb = new StringBuilder(DAOGOU_CACHE_KEY);
		sb.append(cityId);

		List<BuildingDaoGou> dgList = null;
		//从缓存中获取
		dgList=cacheHandler.getCacheValue(sb.toString(), List.class);
		
		if(dgList == null) {
			//从后台获取
			JSONObject json = getDaoGouList(cityId, 1, 5);
			
			if(json!=null){
				JSONArray jsonArray = json.getJSONArray("data");
				dgList = JSON.parseArray(jsonArray.toJSONString(),
                    BuildingDaoGou.class);
                if(dgList!=null && !dgList.isEmpty()){
                	
                	//设置缓存
                	cacheHandler.setCache(sb.toString(), DAOGOU_CACHE_EXP, dgList);
                	
                }else {
                	//空缓存
                	cacheHandler.setCache(sb.toString(), DAOGOU_CACHE_EXP, ListUtils.EMPTY_LIST);
                	return null;
				}
                
			} else {
            	return null;
			}
		}
		
		//取值
		if(dgList.size()<pageSize){
			pageSize = dgList.size();
		}
		return dgList.subList(0, pageSize);

	}
	
	
	
	
	/**
	 * 导购列表获取
	 */
	public JSONObject getDaoGouList(Integer cityId, Integer pageNo,
			Integer pageSize) {
		Map<String, Object> urlVariables = new HashMap<String, Object>();
		urlVariables.put("cityId", cityId);
		urlVariables.put("pageNo", pageNo);
		urlVariables.put("pageSize", pageSize);
		JSONObject jo = null;
		try {
			//访问接口URL获取接送元数据
			jo = interfaceService.getJSONFromInterface(
					AppConstants.XIN_FANG_API_V4_DAOGOULIST_BY_CITYID_URL, urlVariables);
			if(jo==null)
			{
				return null;
			}
			//判断是否成功
			jo = buildingProposeService.extractJSONData(jo);
		} catch (Exception e) {
			logger.error("", e);
		}
		return jo;
	}
	
	/**
	 * 导购列表数据修改标签+导购标题
	 */
	public List<BuildingDaoGou> decorateDaoGouList(JSONObject jsonObject)
	{
		List<BuildingDaoGou> daoGous = null;

		try {
			if (jsonObject != null) {
				// 数组
				JSONArray jsonArray = jsonObject.getJSONArray(JSON_DATA_NAME);
				// 将数组转换成list
				if (jsonArray == null) {
					return daoGous;
				}
				daoGous = JSON.parseArray(jsonArray.toJSONString(),
						BuildingDaoGou.class);
				
				if(daoGous == null || daoGous.isEmpty())
				{
					return null;
				}

				// 遍历导购
				for (BuildingDaoGou dg : daoGous) {
					// 修改导购日期
					dg.setOnlineTime(DateUtils.stringPattern(
							dg.getOnlineTime(), "yyyy-MM-dd", "M月d日"));

//					// 导购标题
//					if (dg.getTitle().length() > 16) {
//						dg.setTitle(dg.getTitle().substring(0, 16) + "...");
//					}

					// 导购标签
					List<String> tabs = dg.getKeyWord();				
					StringBuilder sb = new StringBuilder();
					int i = 3;
					for (String str : tabs) {
						
						if(!StringUtils.isBlank(str))
						{
							// 获取关键字汉字
							JSONObject jo = JSON.parseObject(str);
							str = jo.getString(JSON_KEYWORD_NAME);

							if(str.equals("定制海选") || str.equals("编辑推荐"))
							{
								i++;
								continue;
							}
							
							// 最多3个标签
							if (tabs.indexOf(str) >= i) {
								break;
							}
							// 标签最多6个汉字
							if (str.length() > 6) {
								str = str.substring(0, 6);
							}
							// 如果超过13个汉字，则显示两个标签
							if (sb.toString().length() + str.length() > 13) {
								break;
							}
							sb.append(str + " ");
						}
					}
					// 去掉最后的空格
					if(StringUtils.isBlank(sb.toString()))
					{
						dg.setTab("");
					}
					else {
						dg.setTab(sb.toString().substring(0,
								sb.toString().length() - 1));
					}
					
					// 编辑姓名4个汉字
					if (dg.getEditorName().length() > 4) {
						dg.setEditorName(dg.getEditorName().substring(0, 4));
					}
					// 编辑头衔,最多4个汉字
					if(StringUtils.isBlank(dg.getEditorTitle()))
					{
						dg.setEditorTitle(JSON_EDITOR_TITLE);
					}
					else {
						if(dg.getEditorTitle().length()>4)
						{
							dg.setEditorTitle(dg.getEditorTitle().substring(0, 4));
						}
					}
					// 总发布数
					dg.setTotalPublish(integerDisplayRule(dg.getTotalPublish()));
					// 总阅读数
					dg.setTotalViews(integerDisplayRule(dg.getTotalViews()));
					
				}
			}
			return daoGous;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 正文，格式化+换行
	 * 手机：第一行不包；pad:全部包《p》
	 * @param content
	 * @param isMobile:true,手机；false：pad
	 * @return
	 */
	public String decorateContent(String content) {
		
		StringBuilder sb = null;
		if(!StringUtils.isBlank(content)) {
			String[] cont = content.split("(\r\n)+");
			
			
			sb = new StringBuilder(cont[0].trim());
			
			if(cont.length > 1 ) {
				for(int i=1; i < cont.length ; i++) {
					
					sb.append("<p class=\"its_pra\">").append(cont[i].trim()).append("</p>");
				}
			}
			return sb.toString();
		}
		return null;
	}
	
	//导购正文
	public BuildingDaoGou decorateDaoGou(BuildingDaoGou dg)
	{
		try {
			if(dg == null)
			{
				return null;
			}
			// 修改导购日期
			dg.setOnlineTime(DateUtils.stringPattern(
					dg.getOnlineTime(), "yyyy-MM-dd", "yyyy年M月d日"));
			
			
			dg.setContent(decorateContent(dg.getContent()));
			// 导购标签，全部显示
			List<String> tabs = dg.getKeyWord();
			StringBuilder sb = new StringBuilder();
			for (String str : tabs) {
				if(!StringUtils.isBlank(str))
				{
					// 获取关键字汉字
					JSONObject jo = JSON.parseObject(str);
					str = jo.getString(JSON_KEYWORD_NAME);
					if(str.equals("定制海选")||str.equals("编辑推荐"))
					{
						continue;
					}
					sb.append(str + " ");
				}
			}
			// 去掉最后的空格
			if(StringUtils.isBlank(sb.toString()))
			{
				dg.setTab("");
			}
			else {
				dg.setTab(sb.toString().substring(0,
						sb.toString().length() - 1));
			}
			// 编辑姓名《=4个汉字
			if (dg.getEditorName().length() > 4) {
				dg.setEditorName(dg.getEditorName().substring(0, 4));
			}
			// 编辑头衔,《=4个汉字
			if(StringUtils.isBlank(dg.getEditorTitle()))
			{
				dg.setEditorTitle(JSON_EDITOR_TITLE);
			}
			else {
				if(dg.getEditorTitle().length()>4)
				{
					dg.setEditorTitle(dg.getEditorTitle().substring(0, 4));
				}
			}
			// 总发布数
			dg.setTotalPublish(integerDisplayRule(dg.getTotalPublish()));
			// 总阅读数
			dg.setTotalViews(integerDisplayRule(dg.getTotalViews()));
			
			return dg;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		
	}
	
	
	/**
	 * 计数规则
	 * 0-999，显示数字；
	 * 1000-9999，保留到千位，显示文字“3千”
	 * 1万-9.9万，保留到千位，显示文字“9.9万”
	 * 10万-99万，不保留小数，显示文字“99万”
	 * 100万-999万，不保留小数，显示文字“99万+”
	 */
	public String integerDisplayRule(String data)
	{
		try {
			int iData = Integer.parseInt(data);
			
			if(iData<0)
			{
				return null;
			}
			else if(iData>=0 && iData<=999)
			{
				return data;
			}
			else if(iData>=1000 && iData <=9999)
			{
				return data.substring(0,1)+"千";
			}
			else 
			{
				float fData = iData/10000;
				
				if(fData >=1 && fData < 10)
				{
					
					String result = String.valueOf(fData).substring(0,3);
					if(checkRegex(result,"^[1-9]{1}[.]{1}[0]{1}$"))
					{
						
						return String.valueOf(fData).substring(0,1)+"万";
					}
					else {
						return result+"万";
					}
				}
				else if(fData >=10 && fData < 100)
				{
					return String.valueOf(fData).substring(0, 2)+"万";
				}
				else if(fData >= 100 && fData < 1000){
					return "99万+";
				}
				else {
					return "99万+";
				}
			}
		} catch (Exception e) {
			logger.error("计数规则"+e.getMessage());
			//默认显示原数据
			return data;
		}
	}
	
	/**
	 * 
	 * @param cityId:城市ID
	 * @param dgId：导购ID
	 * @return
	 */
	public BuildingDaoGou getDaoGou(Integer cityId,Integer dgId) {

		BuildingDaoGou dg = getDgFromPC(cityId, dgId);
		if(dg == null)
		{
			//根据移动端的导购id获取pc端的导购id
			return getDgFromPCAppMap(cityId, dgId);
		}
		return dg;
	}
	//获取pc端导购号与web app端导购号一一对应的导购信息
	public BuildingDaoGou getDgFromPCAppMap(Integer cityId,Integer dgId)
	{
		Integer pcId = daoGouDao.getDaogouIdOfPc(dgId);
		if(pcId == null)
		{
			return null;
		}
		return getDgFromPC(cityId, pcId);
	}
	//从PC端获取导购
	public BuildingDaoGou getDgFromPC(Integer cityId,Integer dgId)
	{
		Map<String, Object> urlVariables = new HashMap<String, Object>();
		urlVariables.put("cityId", cityId);
		urlVariables.put("proposeId", dgId);
		BuildingDaoGou dg = null;
		try {
			JSONObject jo = interfaceService.getJSONFromInterface(
					AppConstants.XIN_FANG_API_V4_DAOGOU_DETAIL_URL, urlVariables);
			
			jo = buildingProposeService.extractJSONData(jo);
			if(jo == null)
			{
				return null;
			}

			dg = JSON.parseObject(jo.toJSONString(), BuildingDaoGou.class);
		} catch (Exception e) {
			logger.error("", e);
		}
		return dg;
	}
	
	
	
	
	//电话
	public String decoratePhone(String tel)
	{
		if(StringUtils.isNotBlank(tel))
		{
			tel = tel.replaceAll("转", ",");
			tel = tel.replaceAll(" ", "");
			tel = tel.replaceAll("-", "");
			return tel;
		}
		return "";
	}
	
	public boolean checkRegex(String str,String regex)
	{
		Pattern pattern=Pattern.compile(regex);
	    Matcher matcher=pattern.matcher(str);
	    if(!matcher.matches())
	    {
	    	return false;
	    }
	    return true;
	}
	
}
