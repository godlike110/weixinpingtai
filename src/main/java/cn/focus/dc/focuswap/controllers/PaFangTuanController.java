package cn.focus.dc.focuswap.controllers;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.BuildingInfo;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.LineInfo;
import cn.focus.dc.focuswap.model.LineRelatedProject;
import cn.focus.dc.focuswap.service.BuildingService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.PaFangTuanService;
import cn.focus.dc.focuswap.service.XinFangCommonApiService;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.Pafangtuan;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.PafangtuanDeclaration;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.PafangtuanTips;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.PftBuildModel;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.PftCityInfo;
import cn.focus.dc.focuswap.utils.Utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


@Path("{cityName:[a-zA-Z]{2,}}/pafangtuan")
@AccessLogRequired
@LoginRequired
public class PaFangTuanController {
    
    @Autowired
    private CityService cityService;
    
    @Autowired
    private PaFangTuanService paFangTuanService;
    
    @Autowired
    private BuildingService buildingService;
    
    @Autowired
    private XinFangCommonApiService xinFangCommonApiService;
    
    private Log logger = LogFactory.getLog(PaFangTuanController.class);
    
    
    //加载大小
    private static final int Page_Size_Phone=5;
    
    
    private static final String THREE_DOTS = "...";
    
    
    /**
     *新版爬房团列表页      
     * @param inv
     * @param device
     * @param cityName
     * @return
     */
    @SuppressWarnings("unchecked")
    @Get("/")
    @AccessLogRequired
    public String getPftListNew(Invocation inv,@Param("cityName")@DefValue("bj")String cityName,
            @Param("from") String from){
        //flag用于在搜狐新闻客户端显示的页面
        if("sn".equals(from)) {
            inv.addModel("sohunews", true);
        }
        
        try {
                //城市信息
                DictCity city = cityService.getCityByNameOrPinYin(cityName);
                if(null == city) {
                    return "e:404";
                }
                inv.addModel("_city", city);
    
//                //返回
//                int returnFlag = Utils.getBackStatus(inv.getRequest());
//                inv.addModel("returnFlag", returnFlag);
                
                //此为无意义的一个值，上一个版本使用到的，为了防止js异步加载的时候找不到该参数，所以暂时随意填了一个值
                inv.addModel("endDate", "ab");
                
                int pageSize = Page_Size_Phone;
                Map<String, Object> pftRet = paFangTuanService.getPafangtuanList(city.getCityId(), 1, pageSize);
                if (null == pftRet) {
                    return "phone/nopftList";
                }
                
                inv.addModel("hasNext", pftRet.get(PaFangTuanService.HAS_NEXT));
                List<Pafangtuan> pftList = (List<Pafangtuan>) pftRet.get(PaFangTuanService.DATA_LIST);
                
                if (null == pftList || pftList.size() < 1) {
                    return "phone/nopftList";
                }
                
                //根据页面要求修改字段
                decoratePftList(pftList);
                
                inv.addModel("pfts", pftList);
                
                //ajax url
                StringBuilder sb = new StringBuilder("/pafangtuan/listAjax/");
                StringBuilder single = new StringBuilder("/pafangtuan/");
                inv.addModel("city",Utils.putAjaxUrl(city, sb,single));
                
                return "phone/pftList";
        } catch (Exception e) {
            logger.error("", e);
        }
        return "phone/nopftList";
    }
    
    
    /**
     * 新版异步获取爬房团列表
     * @param inv
     * @param device
     * @param cityName
     * @param pageNo
     * @param pageSize
     * @param endDate
     * @param func
     * @return
     */
    @SuppressWarnings("unchecked")
    @Get("listAjax/")
    public String getPftListAjaxNew(Invocation inv,@Param("cityName")@DefValue("bj")String cityName,
            @Param("pageNo")@DefValue("2")Integer pageNo,@Param("pageSize")@DefValue("6")Integer pageSize,@Param("callback")String func) {
        JSONObject ret = new JSONObject();
        ret.put("hasNext", false);
        ret.put("errorCode", "-1");
        
        try {
            DictCity city = cityService.getCityByNameOrPinYin(cityName);
            pageSize = Page_Size_Phone;
            Map<String, Object> pftRet = paFangTuanService.getPafangtuanList(city.getCityId(), pageNo, pageSize);
            if (null == pftRet) {
                pftRet = new HashMap<String, Object>();
                pftRet.put("hasNex", false);
            }
            
            ret.put("hasNext", pftRet.get(PaFangTuanService.HAS_NEXT));
            List<Pafangtuan> pftList = (List<Pafangtuan>) pftRet.get(PaFangTuanService.DATA_LIST);
            
            if (null == pftList) {
                pftList = new LinkedList<Pafangtuan>();
            }
            
            decoratePftList(pftList);
            
            ret.put("data", pftList);
            
        } catch (Exception e) {
            logger.error("", e);
        }
        
        if (StringUtils.isBlank(func)) {
            return "@" + ret.toJSONString();
        } else {
            return "@" + func + "(" + ret.toJSONString() + ")";
        }
    }
    
    private void decoratePftList(List<Pafangtuan> list) {
        SimpleDateFormat sdf = new SimpleDateFormat("M月d日");
        for (Pafangtuan pft : list) {
//            //剩余天数 = 截止日期-当前日期+1
//            pft.setApplyRemainedDays(pft.getApplyRemainedDays()+1);
            
            pft.setActiveDate(sdf.format(pft.getActiveDateRel()));

            List<PftBuildModel> builds = pft.getBuilds();
            StringBuilder longlatParam = new StringBuilder();
            if (null != builds && builds.size() > 0) {
                for (PftBuildModel build : builds) {
                    String name = build.getName();
                    if (name.length() > 10) {
                        build.setNameAbbr(name.substring(0, 9) + THREE_DOTS);
                    } else {
                        build.setNameAbbr(name);
                    }
                    
                    String phone = build.getPhone();
                    if(null == phone) {
                        build.setPhone2("");
                    } else {
                        build.setPhone2(decPhone(build.getPhone()));
                        
                    }
                    
                    
                    String price = build.getRefPrice();
                    if (StringUtils.isNotBlank(price)) {
                        build.setRefPrice(price.replace("元/㎡", "元/平米"));
                    }
                    
                    String longtitudeStr = build.getLongitude();
                    String latitudeStr = build.getLatitude();
                    if (null != longtitudeStr && null != latitudeStr) {
                        float longtitude = Float.parseFloat(longtitudeStr.isEmpty() ? "0" : longtitudeStr);
                        float lattitude = Float.parseFloat(latitudeStr.isEmpty() ? "0" : latitudeStr);
                        if (!(longtitude < AppConstants.longtitudeMin) && !(longtitude > AppConstants.longtitudeMax)
                                && !(lattitude < AppConstants.latitudeMin) && !(lattitude > AppConstants.latitudeMax)) {
                            longlatParam.append(longtitude).append(",").append(lattitude).append(";");
                            }
                    }
                    
                }
                
                if (longlatParam.length() > 0) {
                    //删除最后一个分号
                    longlatParam.deleteCharAt(longlatParam.length() -1);
                    StringBuilder mapUrl = new StringBuilder("http://restapi.amap.com/v3/staticmap?markers=-1,http://webapi.amap.com/images/marker_sprite.png,0:");
                    mapUrl.append(longlatParam).append("&key=9e4b883b2a6d8482638c56b6f60078b7&size=470*240&zoom=9");
                    pft.setLnglatsUrl(mapUrl.toString());
                }
            }
        }
    }
    
    private String decPhone(String phone) {
        if (phone.indexOf("转") != -1) {
            return phone.replaceAll("转", ",").replaceAll("[-\\s]", "");
        }
        
        return phone;
    }
    
    /**
     * 获取爬房团声明
     * @param inv
     * @param device
     * @param cityName
     * @return
     * @throws MalformedURLException 
     */
    @Get("statement/{lineId:[0-9]+}/")
    public String getPftStatement(Invocation inv,@Param("cityName")@DefValue("bj")String cityName,@Param("lineId") String lineId) throws MalformedURLException
    {
    	try {
        	//2、城市信息
        	DictCity city = cityService.getCityByNameOrPinYin(cityName);
        	if(city == null)
        	{
        		return "e:404";
        	}
        	inv.addModel("lineId", lineId);
        	inv.addModel("_city", city);
        	
        	inv.addModel("city", JSONObject.toJSON(city));
        	//返回
        	int returnFlag = Utils.getBackStatus(inv.getRequest());
            inv.addModel("returnFlag", returnFlag);
            
        	//参加流程和免责声明
        	List<PafangtuanDeclaration> declarations = xinFangCommonApiService.getPftDeclaration();
        	if(declarations == null)
        	{
        		inv.addModel("declarations", "");
        	}
        	else {
        		inv.addModel("declarations", declarations);
    		}
        	
        	return "phone/pftStatement";
		} catch (Exception e) {
			logger.error(e);
		}
    	return "e:404";
    	
    }
    
    /**
     * 新版爬房团正文
     * @param inv
     * @param device
     * @param lineId
     * @param cityName
     * @return
     */
    @SuppressWarnings("unchecked")
    @Get("{lineId:[0-9]+}/")
    public String getPftNew(Invocation inv,@Param("lineId")Integer lineId,
            @Param("cityName")@DefValue("bj")String cityName) {
        
        try {
            DictCity city = cityService.getCityByNameOrPinYin(cityName);
            inv.addModel("_city", city);
            if(null == city) {
                return "e:404";
            }
            
            int returnFlag = Utils.getBackStatus(inv.getRequest());
            inv.addModel("returnFlag", returnFlag);
            
            Pafangtuan pft = xinFangCommonApiService.getPftInfo(city.getCityId(), lineId);
            if(null == pft) {
                return "e:404";
            }
            
            //其它线路模块
            List<Pafangtuan> otherList = null;
            Map<String, Object> pftRet = paFangTuanService.getPafangtuanList(city.getCityId(), 1, 6);
            if (null != pftRet) {
                otherList = (List<Pafangtuan>) pftRet.get(PaFangTuanService.DATA_LIST);
                if (null != otherList && otherList.size() > 0) {
                    SimpleDateFormat sdf = new SimpleDateFormat("M月d日");
                    Iterator<Pafangtuan> it = otherList.iterator();
                    while(it.hasNext()) {
                        Pafangtuan otherPft = it.next();
                        if (lineId.equals(otherPft.getLineId())) {
                            it.remove();
                            continue;
                        }
                        otherPft.setActiveDate(sdf.format(otherPft.getActiveDateRel()));
                        String lightSpot = otherPft.getLightspot();
                        if (StringUtils.isNotBlank(lightSpot) && lightSpot.length() > 25) {
                            otherPft.setLightspot(lightSpot.substring(0, 25) + THREE_DOTS);
                        }
                    }
                    int oherListSize = otherList.size();
                    if (oherListSize > 0) {
                        inv.addModel("otherPfts", otherList.subList(0, Math.min(5, oherListSize)));
                    }
                }
            }
            //相关楼盘模块
            List<PftBuildModel> builds = pft.getBuilds();
            StringBuilder longlatParam = new StringBuilder();
            if (null != builds && builds.size() > 0) {
                for (PftBuildModel build : builds) {
                    
                    String longtitudeStr = build.getLongitude();
                    String latitudeStr = build.getLatitude();
                    if (null != longtitudeStr && null != latitudeStr) {
                        float longtitude = Float.parseFloat(longtitudeStr.isEmpty() ? "0" : longtitudeStr);
                        float lattitude = Float.parseFloat(latitudeStr.isEmpty() ? "0" : latitudeStr);
                        if (!(longtitude < AppConstants.longtitudeMin) && !(longtitude > AppConstants.longtitudeMax)
                                && !(lattitude < AppConstants.latitudeMin) && !(lattitude > AppConstants.latitudeMax)) {
                            longlatParam.append(longtitude).append(",").append(lattitude).append(";");
                            }
                    }
                    
                    /**
                     * 
                     * 本线路楼盘phone截断
                     * pad版不截断
                     */
                    if(builds.indexOf(build) == 0 ){
                        String address = build.getAddress();
                        if (StringUtils.isNotBlank(address) && address.length() > 18) {
                            build.setAddress(address.substring(0,18) + THREE_DOTS);
                        }
                    }
                    
                    String phone = build.getPhone();
                    if(null == phone) {
                        build.setPhone2("");
                    } else {
                        build.setPhone2(decPhone(build.getPhone()));
                        build.setPhoneFenji(build.getPhone2().substring(build.getPhone2().indexOf(",")+1,build.getPhone2().length()));
                        
                    }
                    
                    String price = build.getRefPrice();
                    if (StringUtils.isNotBlank(price)) {
                        build.setRefPrice(price.replace("元/㎡", "元/平米"));
                    }
                }
                
                //此处不使用build.remove(0)来操作，是为了减少shift操作。在jsp的forEach循环中设置begin=1（经过验证，begin的值大于列表的大小不会异常）、循环外层的if语句中的length > 1
                inv.addModel("recommondPftBuild", builds.get(0));
            }
            
            //线路详情模块
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
            pft.setActiveDate(sdf3.format(sdf2.parse(pft.getActiveDate())));
            Date lastSignUpDate = sdf2.parse(pft.getLastSignUpDate());
            pft.setLastSignUpDate(sdf3.format(lastSignUpDate));
            

//            if(null == pft.getSignUpStatus()) {
//                if(sdf3.format(new Date()).compareTo(pft.getLastSignUpDate()) <= 0 ) {
//                    pft.setSignUpStatus(1);
//                } else {
//                    //已截止报名
//                    pft.setSignUpStatus(3);
//                }
//            }
            if (longlatParam.length() > 0) {
                //删除最后一个分号
                longlatParam.deleteCharAt(longlatParam.length() -1);
                StringBuilder mapUrl = new StringBuilder("http://restapi.amap.com/v3/staticmap?markers=-1,http://webapi.amap.com/images/marker_sprite.png,0:");
                mapUrl.append(longlatParam).append("&key=9e4b883b2a6d8482638c56b6f60078b7&size=470*240&zoom=9");
                pft.setLnglatsUrl(mapUrl.toString());
            }
            
            PftCityInfo pftCityInfo = paFangTuanService.getPftCityInfo(city.getCityId());
            if(Boolean.valueOf(pftCityInfo.getPhoneVisiable())) {
                inv.addModel("phone", pftCityInfo.getPhone());
                inv.addModel("phone2", decPhone(pftCityInfo.getPhone()));
            } else {
                inv.addModel("phone", "");
            }
            
            inv.addModel("pft", pft);
            
            
            //小贴士模块
            List<PafangtuanTips> tips = paFangTuanService.getPftTips(city.getCityId());
            inv.addModel("tips", tips);
            
            //我要报名url       
            StringBuilder single = new StringBuilder("/pafangtuan/");
            inv.addModel("city",Utils.putAjaxUrl(city, null,single));
            
            //购房大学
            if(pft.getHouseCollege() == null) {
                inv.addModel("houseCollege", false);
            } else {
                inv.addModel("houseCollege", true);
            }
            
            return "phone/pftInfo";
        } catch (Exception e) {
            logger.error("", e);
        }
        
        return "e:404";
    }
    
    
    
    
    /**
     * 我要报名按钮，post 请求
     * @param inv
     * @return
     */
    @Post("{lineId:[0-9]+}/signup/")
    public String pftSign(Invocation inv,@Param("name")String name,@Param("phone")String phone,
    		@Param("cityName")@DefValue("bj")String cityName,@Param("lineId")Integer lineId,@Param("uid")Integer uid)
    {
    	try {
			
    		DictCity city = cityService.getCityByNameOrPinYin(cityName);
        	if(city == null)
        	{
        		return null;
        	}
        	JSONObject jo;
        	if(name == null)
        	{
        		//用户名+手机号为空
        		jo= new JSONObject();
        		jo.put("errorCode",6);
        		jo.put("errorMessage", "用户名为空");
        		return "@"+jo.toJSONString();
        	}
        	else if(phone == null)
        	{
        		jo= new JSONObject();
        		jo.put("errorCode",6);
        		jo.put("errorMessage", "手机号为空");
        		return "@"+jo.toJSONString();
        	}
        	//返回爬房团报名响应数据
        	return "@"+xinFangCommonApiService.pftSign(uid, city.getCityId(), lineId, phone,name);
    		
		} catch (Exception e) {
			logger.error(e);
		}
    	return null;
    	
    }
    
    
    /**
     * 地图
     * @param groupId
     * @param type
     * @param inv
     * @return
     */
    @Get("{lineId:[0-9]+}/map")
    public String map(@Param("cityName") @DefValue("bj") String cityName,@Param("lineId") Integer lineId,Invocation inv) {

        //获取城市信息
        DictCity city = cityService.getCityByPinYinIgnoringStatus(cityName);
        
        //获取楼盘信息
        LineInfo lineInfo = paFangTuanService.getLineInfo(city.getCityId(), lineId);
        
        //获取相关楼盘信息用于地图显示
        List<LineRelatedProject> projects = lineInfo.getProject();
        StringBuilder groupIds = new StringBuilder();
        StringBuilder groupNames = new StringBuilder();
        int projLoopCount = projects.size() - 1;
        for (int i = 0; i < projLoopCount; i ++){
            LineRelatedProject project = projects.get(i);
            groupIds.append(project.getGroup_id()).append(",");
            groupNames.append(project.getName()).append(";");
        }
        LineRelatedProject lastProject = projects.get(projLoopCount);
        groupIds.append(lastProject.getGroup_id());
        groupNames.append(lastProject.getName());
        inv.addModel("projNames", groupNames.toString());
        
        List<BuildingInfo> relatedBuildingInfoList = null;
        relatedBuildingInfoList = buildingService.getBuildingInfoList(groupIds.toString());
        if (null != relatedBuildingInfoList && relatedBuildingInfoList.size() > 0) {
            StringBuilder lnglatStr = new StringBuilder();
            int loopSize = relatedBuildingInfoList.size() - 1;
            for (int i = 0; i < loopSize; i ++){
                BuildingInfo info = relatedBuildingInfoList.get(i);
                lnglatStr.append(info.getLongitude()).append(",").append(info.getLatitude()).append(";");
            }
            BuildingInfo info = relatedBuildingInfoList.get(loopSize);
            lnglatStr.append(info.getLongitude()).append(",").append(info.getLatitude());
            inv.addModel("lnglats", lnglatStr.toString());
        }
        String sub = JSON.toJSONString(projects);
        JSONArray ja =  JSONArray.parseArray(sub);
        inv.addModel("ja", ja);
        return "climbMap";
    }
    
    
}
