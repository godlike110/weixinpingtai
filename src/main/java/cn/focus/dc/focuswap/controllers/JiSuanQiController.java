package cn.focus.dc.focuswap.controllers;

import java.net.MalformedURLException;

import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.utils.Utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("tools")
@AccessLogRequired
@LoginRequired
public class JiSuanQiController {
    
    private static Log logger = LogFactory.getLog(JiSuanQiController.class);
    
    @Autowired
    private CityService cityService;

    /**
     * 商业贷款
     * @param inv
     * @param device
     * @return
     */
    @Get("/sydk/")
    public String bussinessLoan(Invocation inv, @Param("from") String from) {
        DictCity city = cityService.getCityLocatedFromCookie(inv);
        if (null == city) {
            // 页面使用默认城市
            city = cityService.getDefaultCity();
        }
        inv.addModel("_city", city);
        //flag用于在搜狐新闻客户端显示的页面
        if("sn".equals(from)) {
            inv.addModel("sohunews", true);
        }
        try {
            inv.addModel("returnFlag", Utils.getBackStatus(inv.getRequest()));
        } catch (MalformedURLException e) {
            inv.addModel("returnFlag", 0);
        }
        return "jsq/phone/business";
    }
    
    /**
     * 公积金贷款
     * @param inv
     * @param device
     * @return
     */
    @Get("/gjjdk/")
    public String fundLoan(Invocation inv, @Param("from") String from) {
        DictCity city = cityService.getCityLocatedFromCookie(inv);
        if (null == city) {
            // 页面使用默认城市
            city = cityService.getDefaultCity();
        }
        inv.addModel("_city", city);
        //flag用于在搜狐新闻客户端显示的页面
        if("sn".equals(from)) {
            inv.addModel("sohunews", true);
        }

        try {
            inv.addModel("returnFlag", Utils.getBackStatus(inv.getRequest()));
        } catch (MalformedURLException e) {
            inv.addModel("returnFlag", 0);
        }
        return "jsq/phone/fund";
    }
    
    /**
     * 组合贷款
     * @param inv
     * @param device
     * @return
     */
    @Get("/zhdk/")
    public String combinationLoan(Invocation inv, @Param("from") String from) {
        DictCity city = cityService.getCityLocatedFromCookie(inv);
        if (null == city) {
            // 页面使用默认城市
            city = cityService.getDefaultCity();
        }
        inv.addModel("_city", city);
        //flag用于在搜狐新闻客户端显示的页面
        if("sn".equals(from)) {
            inv.addModel("sohunews", true);
        }
        try {
            inv.addModel("returnFlag", Utils.getBackStatus(inv.getRequest()));
        } catch (MalformedURLException e) {
            inv.addModel("returnFlag", 0);
        }
        return "jsq/phone/combination";
    }
    
    /**
     * 税费计算器
     * @param inv
     * @param device
     * @return
     */
    @Get("/sf/")
    public String taxation(Invocation inv, @Param("from") String from) {
        DictCity city = cityService.getCityLocatedFromCookie(inv);
        if (null == city) {
            // 页面使用默认城市
            city = cityService.getDefaultCity();
        }
        inv.addModel("_city", city);
        //flag用于在搜狐新闻客户端显示的页面
        if("sn".equals(from)) {
            inv.addModel("sohunews", true);
        }
        try {
            inv.addModel("returnFlag", Utils.getBackStatus(inv.getRequest()));
        } catch (MalformedURLException e) {
            inv.addModel("returnFlag", 0);
        }
        return "jsq/phone/taxation";
    }
    
    /**
     * 提前还贷计算器
     * @param inv
     * @param device
     * @return
     */
    @Get("/tqhd/")
    public String loanTerms(Invocation inv, @Param("from") String from) {
        DictCity city = cityService.getCityLocatedFromCookie(inv);
        if (null == city) {
            // 页面使用默认城市
            city = cityService.getDefaultCity();
        }
        inv.addModel("_city", city);
        //flag用于在搜狐新闻客户端显示的页面
        if("sn".equals(from)) {
            inv.addModel("sohunews", true);
            inv.addModel("snurl", "sohunews/");
        }
        try {
            inv.addModel("returnFlag", Utils.getBackStatus(inv.getRequest()));
        } catch (MalformedURLException e) {
            inv.addModel("returnFlag", 0);
        }
        return "jsq/phone/loanTerms";
    }
    
    /**
     * 购房能力评估
     * @param inv
     * @param device
     * @return
     */
    @Get("/gfnlpg/")
    public String buyHouseAbility(Invocation inv, @Param("from") String from) {
        DictCity city = cityService.getCityLocatedFromCookie(inv);
        if (null == city) {
            // 页面使用默认城市
            city = cityService.getDefaultCity();
        }
        inv.addModel("_city", city);
        //flag用于在搜狐新闻客户端显示的页面
        if("sn".equals(from)) {
            inv.addModel("sohunews", true);
        }
        try {
            inv.addModel("returnFlag", Utils.getBackStatus(inv.getRequest()));
        } catch (MalformedURLException e) {
            inv.addModel("returnFlag", 0);
        }
        return "jsq/phone/buyHouse";
    }
    
    /**
     * 购房能力评估之推荐楼盘
     * @param inv
     * @param device
     * @return
     */
    @Get("/gfnlpg/tjlp/")
    public String buyHouseAbilityBuiling(Invocation inv, @Param("price")Integer price, 
            @Param("area")Integer area, @Param("cityId")Integer cityId, @Param("from") String from) {
        DictCity city = cityService.getCityLocatedFromCookie(inv);
        if (null == city) {
            // 页面使用默认城市
            city = cityService.getDefaultCity();
        }
        inv.addModel("_city", city);
        //flag用于在搜狐新闻客户端显示的页面
        if("sn".equals(from)) {
            inv.addModel("sohunews", true);
        }
        //返回标志 0表示返回历史记录 1 表示返回首页
        int returnFlag = 0;
        try {
            returnFlag = Utils.getBackStatus(inv.getRequest());
        } catch (MalformedURLException e) {
            logger.error("", e);
        }
        inv.addModel("returnFlag", returnFlag);
        return "jsq/phone/buyHouse_list";
    }
    
    /**
     * 商业贷款还款详情\公积金贷款还款详情\公积金贷款还款详情
     * @param inv
     * @param device
     * @return
     */
    @Get({"/sydk/hkxq/",  "/gjjdk/hkxq/", "/zhdk/hkxq/"})
    public String loanDetail(Invocation inv, @Param("from") String from) {
        DictCity city = cityService.getCityLocatedFromCookie(inv);
        if (null == city) {
            // 页面使用默认城市
            city = cityService.getDefaultCity();
        }
        inv.addModel("_city", city);
        //flag用于在搜狐新闻客户端显示的页面
        if("sn".equals(from)) {
            inv.addModel("sohunews", true);
        }
        //返回标志 0表示返回历史记录 1 表示返回首页
        int returnFlag = 0;
        try {
            returnFlag = Utils.getBackStatus(inv.getRequest());
        } catch (MalformedURLException e) {
            logger.error("", e);
        }
        
        int source = 0;
        String url = inv.getRequestPath().getUri();
        if (url.contains("gjjdk")) {
            source = 1;
        } else if(url.contains("zhdk")) {
            source = 2;
        }
        inv.addModel("source", source);
        
        inv.addModel("returnFlag", returnFlag);
        return "jsq/phone/repay_detail";
    }
}
