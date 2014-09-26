package cn.focus.dc.focuswap.controllers;

import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.QuestionInfo;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.QuestionService;


import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.springframework.beans.factory.annotation.Autowired;

@Path("ask")
public class AskController {
	
	private static final String PAGE_NO = "pageNo";

    private static final String PAGE_SIZE = "pageSize";

    //CITY_ID  GROUP_ID BUILD_ID 跟AppConstants里面对应的变量值不一样
    private static final String CITY_ID = "cityId";

    private static final String GROUP_ID = "groupId";

    private static final String BUILD_ID = "buildId";

    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private CityService cityService;
  
    @Get("{questionId:[0-9]+}")
    public String getQuizInfo(Invocation inv, @Param("questionId") int questionId,
            @Param("from") @DefValue("share") String from) {
         QuestionInfo questionInfo = questionService.findById(questionId);
        DictCity city = cityService.getCity(questionInfo.getCityId());
        if(city==null){
            city = cityService.getCityByIdIgnoringStatus(questionInfo.getCityId());
        }
        String cityName = city.getCityPinyinAbbr();
        if (!"share".equals(from)) {
            return "r:/"+cityName+"/loupan/"+questionInfo.getGroupId()+"/zixun/"+questionId+"/";
        }else{
            return "r:/"+cityName+"/loupan/"+questionInfo.getGroupId()+"/zixun/"+questionId+"/?from=share";
        }
    }

    @Get("questionlist")
    public String getQuizlist(Invocation inv, @Param(PAGE_NO) @DefValue("1") int pageNo,
            @Param(PAGE_SIZE) @DefValue("10") int pageSize, @Param(BUILD_ID) int buildId,
            @Param(CITY_ID) int cityId, @Param(GROUP_ID) int groupId) {       
        DictCity city = cityService.getCity(cityId);
        if(city == null){
            return "r:/";
        }
        return "r:/"+city.getCityPinyinAbbr()+"/loupan/"+groupId+"/zixun/";
    }

}
