package cn.focus.dc.focuswap.controllers;

import static cn.focus.dc.focuswap.config.AppConstants.PAGE_NO;
import static cn.focus.dc.focuswap.config.AppConstants.PAGE_SIZE;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.TuanGouDetail;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.InterfaceService;
import cn.focus.dc.focuswap.service.PaFangTuanService;
import cn.focus.dc.focuswap.service.TuanGouService;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.Pafangtuan;
import cn.focus.dc.focuswap.utils.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Path("{cityName:[a-zA-Z]{2,}}/tuangou")
@AccessLogRequired
@LoginRequired
public class TuanGouController {
    @Autowired
    private CityService cityService;

    @Autowired
    private TuanGouService tuanGouService;

    @Autowired
    private InterfaceService interfaceService;

    @Autowired
    private PaFangTuanService paFangTuanService;

    private Log logger = LogFactory.getLog(TuanGouController.class);

    @Get("update")
    public void update() {
        tuanGouService.updateRedis();
    }

    @Get("releaseLock")
    public String releaseLock() {
    	
    	tuanGouService.releaseLock();
    	
    	return "@SUCCESS";
    	
    }

    /**
     * 团购报名页
     * 
     * @return
     * @throws MalformedURLException 
     */
    @Get("{activeId:[0-9]+}/apply")
    public String apply(Invocation inv, @Param("cityName") @DefValue("bj") String cityName,
            @Param("activeId") Integer activeId) throws MalformedURLException {
        
        //返回标志 0表示返回历史记录 1 表示返回首页
        int d = Utils.getBackStatus(inv.getRequest());
        inv.addModel("returnFlag", d);
        try {
            // 2、根据城市名得到该城市的信息
            DictCity city = cityService.getCityByNameOrPinYin(cityName);
            if (city == null) {
                logger.error("获取团购列表请求，获取城市" + cityName + "信息失败");
                // 加载失败
                return "e:404";
            }
            inv.addModel("_city", city);

            TuanGouDetail tuanGou = tuanGouService.getTuanGouDetail(city.getCityId(), activeId);
            inv.addModel("tuangou", tuanGou);
            inv.addModel("activeid", activeId);
            return "phone/tuangou_zhuce";
        } catch (Exception e) {
            logger.error("");
            return "e:404";
        }
        
    }

    @Post("apply")
    public String tuangouSign(Invocation inv, @Param("name") String name, @Param("cellphone") String cellphone,
            @Param("cityId") @DefValue("1") Integer cityId, @Param("activeId") Integer activeId) {
        return "@" + interfaceService.tuanGouRegister(name, cellphone, cityId, activeId);
    }

    /**
     * 团购列表页，初次加载10条。
     * 
     * @author zihaoli
     */
    @Get("/")
    public String getTuanGouList(Invocation inv, @Param("cityName") @DefValue("bj") String cityName)
            throws MalformedURLException {
        try {
            //返回标志 0表示返回历史记录 1 表示返回首页
            //int d = Utils.getBackStatus(inv.getRequest());
            inv.addModel("returnFlag", 1);

            // 2、根据城市名得到该城市的信息
            DictCity city = cityService.getCityByNameOrPinYin(cityName);
            if (city == null) {
                logger.error("获取团购列表请求，获取城市" + cityName + "信息失败");
                // 加载失败
                return "e:404";
            }
            inv.addModel("_city", city);

            // 页面返回
            // int returnFlag = Utils.getBackStatus(inv.getRequest());
            // inv.addModel("returnFlag",returnFlag);
            // inv.addModel("mobile", device.isMobile());
            int pageSize = 10;
            JSONObject tgList = tuanGouService.getTuanGouList(city.getCityId(), 0, pageSize);

            List<TuanGouDetail> list = null;
			if(null!=tgList) { 
				if(StringUtils.isNotBlank(tgList.getString("data"))) {
				list = JSON.toJavaObject(tgList.getJSONArray("data"), List.class);
				}
			}
//            if(null!=list) {
//            	list = tuanGouService.sortTuangou(list);
//            }
//            List<TuanGouDetail> rlist = new ArrayList<TuanGouDetail>();
//            
//            for(TuanGouDetail tgd:list) {
//            	if(tgd.getTimeLeft()>0) {
//            		rlist.add(tgd);
//            	}
//            }
            
//            Iterator<TuanGouDetail> it = list.iterator();
            
//            while(it.hasNext()) {
//            	TuanGouDetail tgd = (TuanGouDetail)it.next();
//            	if(tgd.getTimeLeft()<=0) {
//            		it.remove();
//            	}
//            }
            
            
            if (list != null) {
                inv.addModel("tgList", list);
            } else {
                // 加载失败
                return "phone/tuangou_no";
            }

            StringBuilder sb = new StringBuilder("/tuangou/listajax/");
            StringBuilder single = new StringBuilder("/tuangou/");

            inv.addModel("city", Utils.putAjaxUrl(city, sb, single));
            inv.addModel("hasNext", tgList.get("hasNext"));
            
            return "phone/tuangou";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "e:404";
        }
    }

    /**
     * @author gufeng
     */

    @Get("listajax/")
    public String getTuanGouListAjax(Invocation inv, @Param(PAGE_NO) @DefValue("2") int pageNo,
            @Param(PAGE_SIZE) @DefValue("10") int pageSize, @Param("cityName") @DefValue("bj") String cityName,
            @Param("callback") String callback) {
        try {
            DictCity city = cityService.getCityByNameOrPinYin(cityName);
            if (city == null) {
                logger.error("导购列表Ajax请求，获取城市" + cityName + "信息失败");
                return null;
            }

            // 获取团购信息列表
            JSONObject tgList = tuanGouService.getTuanGouList(city.getCityId(), pageNo, pageSize);

            if (StringUtils.isBlank(callback) && null!=tgList) {
                return "@" + tgList.toJSONString();
            }
            return "@" + callback + "(" + tgList.toJSONString() + ")";
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * 团购详情页
     * 
     * @author zihaoli
     * @throws ParseException 
     */
    @Get("{activeId:[0-9]+}/")
    public String getTuanGouDetail(Invocation inv, @Param("cityName") @DefValue("bj") String cityName,
            @Param("activeId") Integer activeId) throws UnsupportedEncodingException, MalformedURLException, ParseException {

        //返回标志 0表示返回历史记录 1 表示返回首页
        int d = Utils.getBackStatus(inv.getRequest());
        inv.addModel("returnFlag", d);
    	
        // 根据城市名得到该城市的信息
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        if (city == null) {
            logger.error("团购详情获取城市" + cityName + "的信息失败");
            return "e:404";
        }

        inv.addModel("_city", city);
        TuanGouDetail tuanGouDetail = tuanGouService.getTuanGouDetail(city.getCityId(), activeId);

        // 获取三个报名人数最多的
        if (tuanGouDetail != null) {
            inv.addModel("tuangou", tuanGouDetail);
            List<TuanGouDetail> recommend = tuanGouService.getTuanGouRecommend(city.getCityId(), 4);
            List<TuanGouDetail> reList = new ArrayList<TuanGouDetail>();
            for(TuanGouDetail td:recommend) {
            	if(td.getGroup_id()!=6100) {
            		reList.add(td);
            	}
            }
            if(reList.size()>3) {
            	reList = reList.subList(0, 3);
            }
            inv.addModel("recommend", reList);
        } else {
            logger.error("导购正文，从后台获取导购正文失败" + activeId);
            return "e:404";
        }

        // 400电话处理
        String phone = tuanGouDetail.getPhone();
        String phoneDial = phone.replaceAll("转", ",");
        phoneDial = phoneDial.replaceAll(" ", "");
        inv.addModel("phone_dial", phoneDial);
        
        // 获取最相关爬房团信息作为“免费看房条目”
        List<Pafangtuan> pftList = paFangTuanService.getPafangtuanListByGroupId(city.getCityId(),
                tuanGouDetail.getGroup_id());
        if (pftList != null && pftList.size()>0 && pftList.get(0)!=null)
            inv.addModel("pft", pftList.get(0));

        return "phone/tuangou_detail";
    }

    @Get("shuoming")
    public String shuoming(Invocation inv, @Param("cityName") @DefValue("bj") String cityName) throws MalformedURLException {
        DictCity city = cityService.getCityByNameOrPinYin(cityName);

        inv.addModel("_city", city);
        //返回标志 0表示返回历史记录 1 表示返回首页
        int d = Utils.getBackStatus(inv.getRequest());
        inv.addModel("returnFlag", d);
        return "phone/tuangou_tips";
    }
    
    @Get("timetask")
    public String timetask() {
    	tuanGouService.timeTask();
    	return "@success";
    }
}
