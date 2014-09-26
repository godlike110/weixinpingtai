/**
 *
 */
package cn.focus.dc.focuswap.controllers;

import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.HousingGuide;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.HousingGuideService;
import cn.focus.dc.focuswap.utils.JsonResponseUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.springframework.beans.factory.annotation.Autowired;

import static cn.focus.dc.focuswap.config.AppConstants.*;

/**
 * @author yunguangwang
 * @2013-8-27 @下午5:02:26
 */
@Path("news")
public class HousingGuideController {

	private static final String CATAGORY_ID = "catagoryId";
	
    @Autowired
    private HousingGuideService housingGuideService;

    @Autowired
    private CityService cityService;


    /**
     * 宝典列表，ajax请求分页 暂时获取所有类别的宝典数据，catagoryId参数暂时不用
     * 
     * @param inv
     * @param pageNo
     * @param pageSize
     * @param catagoryId
     * @return
     */
    @Get("baodian/listajax")
    public String getHouseGuideListAjax(Invocation inv,
            @Param(PAGE_NO) @DefValue("1") int pageNo,
            @Param(PAGE_SIZE) @DefValue("10") int pageSize,
            @Param(CATAGORY_ID) Integer catagoryId,@Param("fun") String fun) {

        List<HousingGuide> guideList = housingGuideService.getHousingGuideList(
                (pageNo - 1) * pageSize, pageSize+1);

        Map<String, Object> conditions = new HashMap<String, Object>();
        boolean hasNext = false;
        if (guideList.size() > pageSize) {
            guideList.remove(pageSize);
            hasNext = true;
        }

        conditions.put(HASNEXT, hasNext);
        conditions.put(CATAGORY_ID, catagoryId);
        conditions.put(PAGE_NO, pageNo);
        conditions.put(PAGE_SIZE, pageSize);

        return JsonResponseUtil.okSupportJSONPWithPaginate(guideList,
                fun, conditions);

    }

    /**
     * 分享的宝典详细页
     * 
     * @param inv
     * @param itemId
     * @return
     */
    @Get("baodian/{itemId:[0-9]+}")
    public String getHouseGuide(Invocation inv,
            @Param("itemId") Integer itemId,
            @Param("from") @DefValue("share") String from) {
            DictCity city = cityService.getCityLocatedInfo(inv);
            String cityName = city.getCityPinyinAbbr();
            if (!"share".equals(from)) {
                return "r:/"+cityName+"/baodian/"+itemId+"/";
            }else{
                return "r:/"+cityName+"/baodian/"+itemId+"/?from=share";
            }
    }

    
    /**
     * 首屏宝典列表 暂时获取所有类别的宝典数据
     * 2013.11.22
     * @param inv
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Get("baodian/list")
    public String getHouseGuideList(Invocation inv,
            @Param(PAGE_NO) @DefValue("1") int pageNo,
            @Param(PAGE_SIZE) @DefValue("10") int pageSize,@Param("callback") String callback) {
        DictCity city = cityService.getCityLocatedInfo(inv);
        return "r:/"+city.getCityPinyinAbbr()+"/baodian/";
    }
}
