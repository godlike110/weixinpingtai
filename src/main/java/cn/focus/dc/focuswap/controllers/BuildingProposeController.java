package cn.focus.dc.focuswap.controllers;

import static cn.focus.dc.focuswap.config.AppConstants.DAOGOULIST_FOCUS_IMG_WIDTH;
import static cn.focus.dc.focuswap.config.AppConstants.PAGE_NO;
import static cn.focus.dc.focuswap.config.AppConstants.PAGE_SIZE;
import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.BuildingInfo;
import cn.focus.dc.focuswap.model.BuildingPropose;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.service.BuildingProposeService;
import cn.focus.dc.focuswap.service.BuildingService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.ShareService;
import cn.focus.dc.focuswap.utils.DeviceUtils;
import cn.focus.dc.focuswap.utils.Utils;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Ignored;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;

@Path("{cityName:[a-zA-Z]{2,}}/daogou")
@Ignored
public class BuildingProposeController {
    @Autowired
    private BuildingProposeService buildingProposeService;
    
    @Autowired
    private BuildingService buildingService;
    
    @Autowired
    private CityService cityService;
    
    @Autowired
    private ShareService shareService;
    

    /**
     * @author kanezheng
     * @desc 导购正文页
     * @url m.focus.cn/bj/daogou/34567/
     * @time 2014.2.18
     * @param inv
     * @param daogouId 
     * @param device
     * @return
     * @throws ParseException
     * @throws UnsupportedEncodingException 
     * @throws MalformedURLException 
     */
    @Get("{daogouId:[0-9]+}/")
    @AccessLogRequired
    public String getDaoGou(Invocation inv, Device device, @Param("cityName") @DefValue("bj") String cityName,
            @Param("daogouId") Integer daogouId)
            throws ParseException, UnsupportedEncodingException, MalformedURLException {
            DictCity city = cityService.getCityByNameOrPinYin(cityName);
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
                
                inv.addModel("_city", city);
                inv.addModel("bp", bp);
                
                //share                
                shareService.share(inv, bp.getPromotion(), bp.getPic(), 1, bp.getId(), cityName);
                
                inv.addModel("mobile", device.isMobile());
                
                int returnFlag = Utils.getBackStatus(inv.getRequest());
                inv.addModel("returnFlag", returnFlag);
                return DeviceUtils.device(inv, device) + "/buildingPropose";
            }else{
                return "e:404";
            }
    }

    /**
     * @author kanezheng
     * @desc 导购列表
     * @url m.focus.cn/bj/daogou/
     * @time 2014.2.18
     * @param inv
     * @param device
     * @return
     * @throws ParseException
     */
    @Get("/")
    @Ignored
    @AccessLogRequired
    public String getDaogoulist(Invocation inv, Device device,
            @Param("cityName") @DefValue("bj") String cityName) throws ParseException {
        String d = DeviceUtils.device(inv, device);
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        inv.addModel("_city", city);
        JSONObject bpJson = buildingProposeService.getProposeList(city.getCityId(), 1, 10);
        //修改图片尺寸
        String imgWidth = AppConstants.Focus_img_width.widthMap.get(DAOGOULIST_FOCUS_IMG_WIDTH+d);
        List<BuildingPropose> bpList = buildingProposeService.decorateProposeList(bpJson,imgWidth);
        if(bpList != null){      
            inv.addModel("bpList", bpList);
        }else{
            return "e:404";
        }
        inv.addModel("mobile", device.isMobile());
        StringBuilder sb = new StringBuilder("/daogou/listajax/");
        StringBuilder single = new StringBuilder("/daogou/");
        inv.addModel("city",Utils.putAjaxUrl(city, sb,single));
        inv.addModel("hasNext", bpJson.get("hasNext"));
        inv.addModel("mobile", device.isMobile());
//        int returnFlag = Utils.getBackStatus(inv.getRequest());
//        inv.addModel("returnFlag", returnFlag);
        return d + "/buildingProposeList";
    }

    /**
     * 导购列表，ajax请求分页
     * 
     * @param inv
     * @param pageNo
     * @param pageSize
     * @param cityId
     * @return
     * @throws ParseException
     */
    @Ignored
    @Get("listajax/")
    @AccessLogRequired
    public String getDaoGouListAjax(Invocation inv, Device device,@Param(PAGE_NO) @DefValue("2") int pageNo,
            @Param(PAGE_SIZE) @DefValue("10") int pageSize,
            @Param("cityName") @DefValue("bj") String cityName, @Param("callback") String fun)
            throws ParseException {
            DictCity city = cityService.getCityByNameOrPinYin(cityName);
            JSONObject bpJson = buildingProposeService.getProposeList(city.getCityId(), pageNo, pageSize);
            if(bpJson != null){
                String imgWidth = AppConstants.Focus_img_width.widthMap.get(DAOGOULIST_FOCUS_IMG_WIDTH
                    + DeviceUtils.device(inv, device));
                List<BuildingPropose> bpList = buildingProposeService.decorateProposeList(bpJson,imgWidth);
                if(bpList != null){      
                    bpJson.put("data", bpList);
                    bpJson.put("errorCode", "0");
                }
                if(StringUtils.isBlank(fun)){
                    return "@" + bpJson.toJSONString();
                }
                return "@" + fun + "(" + bpJson.toJSONString() + ")";
            } 
            return null;
    }
    
    /**
     * PC版导购跳转移动版导购
     * @param inv
     * @param pcId
     * @param cityName
     * @return
     */
    @Get("redirect/{pcId:[0-9]+}/")
    public String transFromPC(Invocation inv, @Param("pcId") Integer pcId, @DefValue("bj") String cityName,
            Device device){
        int daogouId = buildingProposeService.transProposeId(pcId);
        if (daogouId > 0) {
            StringBuilder sb = new StringBuilder(25);
            sb.append("r:/").append(cityName).append("/daogou/").append(daogouId).append('/');
            return sb.toString();
        }
        //查询异常，或者没有映射关系
        //TODO: 暂时处理成404页面，后续根据需求再改
        String d = DeviceUtils.device(inv, device);
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        inv.addModel("_city", city);
        String cityJson = JSONObject.toJSONString(city);
        inv.addModel("city", cityJson);
        return d + "/proposeTransDefault";
        
    }
    
    
   
}
