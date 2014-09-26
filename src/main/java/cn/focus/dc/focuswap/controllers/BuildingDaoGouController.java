package cn.focus.dc.focuswap.controllers;

import static cn.focus.dc.focuswap.config.AppConstants.PAGE_NO;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSONObject;


import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.model.BuildingDaoGou;
import cn.focus.dc.focuswap.model.BuildingDaoGouContent;
import cn.focus.dc.focuswap.model.BuildingInfo;
import cn.focus.dc.focuswap.model.BuildingPropose;

import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.service.BuildingDaoGouService;
import cn.focus.dc.focuswap.service.BuildingProposeService;
import cn.focus.dc.focuswap.service.BuildingService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.ShareService;
import cn.focus.dc.focuswap.utils.Utils;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

/**
 * 导购正文+导购列表（改版）
 * @author linfangwang
 */
@AccessLogRequired
@Path("{cityName:[a-zA-Z]{2,}}/daogou/")
@LoginRequired
public class BuildingDaoGouController {

	private static final Log logger=LogFactory.getLog(BuildingDaoGouController.class);
	@Autowired
	private CityService cityService;
	@Autowired
	private BuildingDaoGouService buildingDaoGouService;
	
	@Autowired
    private ShareService shareService;
	
	@Autowired
	private BuildingService buildingService;
	@Autowired
	private BuildingProposeService buildingProposeService;
	
	//导购列表页面显示大小
	private static final int PageSize_DaogouList_Phone = 10;
	
	
	
	/**
	 * @param inv：请求响应
	 * @param device:设备
	 * @param cityName：城市名,默认北京
	 * @throws MalformedURLException 
	 */
	@Get("/")
	public String getDaoGouList(Invocation inv,@Param("cityName") @DefValue("bj") String cityName) throws MalformedURLException
	{
		try {
			//1、设备判断，返回值为 phone||pad

			//2、根据城市名得到该城市的信息
			DictCity city = cityService.getCityByNameOrPinYin(cityName);
			if(city==null)
			{
				logger.error("获取导购列表请求，获取城市"+cityName+"信息失败");
				//加载失败
	            return "e:404";
			}
			inv.addModel("_city", city);
			
			//页面返回
//	        int returnFlag = Utils.getBackStatus(inv.getRequest());
//	        inv.addModel("returnFlag",returnFlag);
			
			
			//3、访问wiki,获取导购列表信息，json格式
			//3.1 如果pad版，则获取6个楼盘列表信息，如果phone版，则获取10个楼盘列表信息
			
			int pageSize = PageSize_DaogouList_Phone;
			
			JSONObject bpJson = buildingDaoGouService.getDaoGouList(city.getCityId(), 1, pageSize);
			if(bpJson == null)
			{
				logger.error("导购列表请求，从后台获取导购数据失败");
				return "e:404";
			}
	        //解析json数据
	        List<BuildingDaoGou> dgList = buildingDaoGouService.decorateDaoGouList(bpJson);
	        if(dgList != null){      
	            inv.addModel("dgList", dgList);
	        }else{
	        	//加载失败
	            return "e:404";
	        }
			
	        
	        StringBuilder sb = new StringBuilder("/daogou/listajax/");
	        StringBuilder single = new StringBuilder("/daogou/");
	        
	        inv.addModel("city",Utils.putAjaxUrl(city, sb,single));
	        inv.addModel("hasNext", bpJson.get("hasNext"));
	        
	        return "phone/buildingDaoGouList";
		} catch (Exception e) {
			logger.error(e);
		}
		return "e:404";
	}
	
	

	
	/**
	 * 
	 * @param inv
	 * @param dev
	 * @param pageNo
	 * @param pageSize
	 * @param cityName
	 * @param fun：前端回调参数
	 * @return
	 */
	@Get("listajax/")
	public String getDaoGouListAjax(Invocation inv, @Param(PAGE_NO) @DefValue("2") int pageNo,
            @Param("cityName") @DefValue("bj") String cityName,@Param("callback") String fun)
	{
		try {
			//1、设备
			//2、获取城市信息
			DictCity city = cityService.getCityByNameOrPinYin(cityName);

			if(city==null)
			{
				logger.error("导购列表Ajax请求，获取城市"+cityName+"信息失败");
				return null;
			}
			//3、页面显示条数设置
			int pageSize = PageSize_DaogouList_Phone;
			//导购信息
			JSONObject bpJson = buildingDaoGouService.getDaoGouList(city.getCityId(), pageNo, pageSize);
			if(bpJson == null )
			{
				return null;
			}
			//解析json格式的数据
			List<BuildingDaoGou> dgList = buildingDaoGouService.decorateDaoGouList(bpJson);
			
			
			if(dgList != null){   
				
				//删除不需要显示的数据
				for(BuildingDaoGou dg:dgList)
				{
					dg.setKeyWord(null);
				}
				
				bpJson.remove("pageNo");
				bpJson.remove("pageSize");
				bpJson.remove("total");
				bpJson.remove("totalPage");
				
				
	            bpJson.put("data", dgList);
	            bpJson.put("errorCode", "0");
	        }
			else {
				bpJson.put("errorCode", "1");//失败
			}
			
			//@为字符串
			if(StringUtils.isBlank(fun))
			{
				return "@"+bpJson.toJSONString();
			}
			return "@"+fun+"("+bpJson.toJSONString()+")";
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	

	//导购正文
	@Get("{daogouId:[0-9]+}/")
	public String getDaoGouDescription(Invocation inv,@Param("cityName") @DefValue("bj")String cityName,@Param("daogouId")Integer daogouId) throws UnsupportedEncodingException, MalformedURLException
	{
		
		try {
			//1、设备判断，返回值为 phone||pad
			
			//2、根据城市名得到该城市的信息
			DictCity city = cityService.getCityByNameOrPinYin(cityName);
			if(city == null)
			{
				logger.error("导购正文，获取城市"+cityName+"的信息失败");
				return "e:404";
			}
			
			//页面返回
			int returnFlag = Utils.getBackStatus(inv.getRequest());
			inv.addModel("returnFlag",returnFlag);
			inv.addModel("_city", city);
			
			BuildingDaoGou dg = buildingDaoGouService.getDaoGou(city.getCityId(),daogouId);
			
			
			
			if(dg !=null)
			{
				dg = buildingDaoGouService.decorateDaoGou(dg);
				if(dg == null )
				{
					logger.error("导购正文，从后台获取导购正文失败"+daogouId);
					return "e:404";
				}
				List<BuildingDaoGouContent> contents = dg.getContentList();
				if(contents != null )
				{
					for(BuildingDaoGouContent dgc:contents)
					{
						
						if(dgc.getBuildPrice()==null || dgc.getBuildPrice().trim().equals("0")) {
							dgc.setBuildPrice("");
						}
						if(dgc.getPhone400()==null || dgc.getPhone400().trim().equals("0")) {
							dgc.setPhone400("");
						} else {
							dgc.setPhone400(buildingDaoGouService.decoratePhone(dgc.getPhone400()));
							int index = dgc.getPhone400().indexOf(",");
							dgc.setPhoneFenji(dgc.getPhone400().substring(index+1, dgc.getPhone400().length()));
						}
						
						//正文内容格式化
						dgc.setContent(buildingDaoGouService.decorateContent(dgc.getContent()));
					}
				}
				
				//js
				String cityJson = JSONObject.toJSONString(city);
		        inv.addModel("city", cityJson);
		        
				inv.addModel("dg", dg);
				//share，新浪微博+q空间
				//导语，使用title代替，取前80个字
				if(StringUtils.isBlank(dg.getPromotion()))
				{
					if(dg.getTitle().length()>80)
					{
						dg.setPromotion(dg.getTitle().substring(0,80)+"...");
					}
					else {
						dg.setPromotion(dg.getTitle());
					}
				}
				else {
					if(dg.getPromotion().length()>80)
					{
						dg.setPromotion(dg.getPromotion().substring(0,80)+"...");
					}
				}
				
				shareService.share(inv, dg.getPromotion(), dg.getPic(), 1, dg.getId(), cityName);
				return "phone/buildingDaoGou";
			}
			//使用旧版导购
			BuildingPropose bp = buildingProposeService.getPropose(daogouId,city.getCityId()); 
			if(bp != null){
	             List<BuildingInfo> pros = new ArrayList<BuildingInfo>();
	             String gids = StringUtils.join(bp.getGroupIds(), ",");
	             pros = buildingService.getBuildingInfoList(gids);
	             buildingProposeService.decorateDate(bp);
	             // 判断动态是否有关联楼盘
	             if (bp.getGroupIds() != null) {
	                 for (BuildingInfo proj : pros) {
	                     if(proj != null){
	                         // 修改实体里面图片url
	                         buildingService.genRealPhotoPath(proj, true);
	                         if (StringUtils.isNotBlank(proj.getProjAddress()) && proj.getProjAddress().length() > 12) {
	                             proj.setProjAddress(proj.getProjAddress().substring(0, 10) + "...");
	                         }
	                         // 打折信息
	                         if ("待定".equals(proj.getDiscount().trim()) || "暂无".equals(proj.getDiscount().trim())
	                                 || "无".equals(proj.getDiscount().trim()) || "0".equals(proj.getDiscount().trim())) {
	                             proj.setDiscount("");
	                         }
	                         buildingService.setBuildingPrice(proj);
	                     }
	                 }

	             }               
	             bp.setPros(pros);
	             
	             inv.addModel("bp", bp);
	             
	             //share                
	             shareService.share(inv, bp.getPromotion(), bp.getPic(), 1, bp.getId(), cityName);
	             
	             return "phone/buildingPropose";
	         }
			 else{
	             return "e:404";
	         }
		} catch (Exception e) {
			logger.error(e);
		}
		 return "e:404";
	}
	
	
	/**
     * PC版导购跳转移动版导购
     * @param inv
     * @param pcId:pc端的导购ID号
     * @param cityName
     * @return
     */
    @Get("redirect/{pcId:[0-9]+}/")
    public String transFromPC(Invocation inv, @Param("pcId") Integer pcId, @DefValue("bj") String cityName){
 
        if (pcId > 0) {
            StringBuilder sb = new StringBuilder(25);
            sb.append("r:/").append(cityName).append("/daogou/").append(pcId).append('/');
            return sb.toString();
        }
        else//404
        	return "e:404";
    }

}
