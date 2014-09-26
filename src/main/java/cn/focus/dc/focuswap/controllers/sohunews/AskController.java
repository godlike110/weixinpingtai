package cn.focus.dc.focuswap.controllers.sohunews;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;

import static cn.focus.dc.focuswap.config.AppConstants.*;
import cn.focus.dc.commons.annotation.XSSFilter;
import cn.focus.dc.commons.model.UserBasic;
import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.json.filter.QuestionInfoExtJsonFilter;
import cn.focus.dc.focuswap.model.BuildingInfo;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.QuestionInfoExt;
import cn.focus.dc.focuswap.service.BuildingService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.QuestionService;
import cn.focus.dc.focuswap.service.XinFangCommonApiService;
import cn.focus.dc.focuswap.utils.Utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.HttpFeatures;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("/")
public class AskController {

    protected static Log logger = LogFactory.getLog(AskController.class);
    
    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private CityService cityService;
    
    @Autowired
    private BuildingService buildingService;
    
    @Autowired
    private XinFangCommonApiService xinFangCommonApiService;
    
    /**
     * 问答列表页面
     * @param inv
     * @param cityName 该参数可能没有
     * @return
     */
    @SuppressWarnings("unchecked")
    @Get("quiz")
    @AccessLogRequired
    @LoginRequired
    public String QuizList(Invocation inv, @Param("cityName") String cityName) {
        
        
        Object o = inv.getModel(AppConstants.FOCUS_USER);
        inv.addModel("logined", (null != o));
        
        DictCity city = null;
        
        if (StringUtils.isNotBlank(cityName)) {
            city = cityService.getCityByNameOrPinYin(cityName);
            if (null != city) {
                cityService.setCityLocated(inv, city);
            }
        }
        
        if (null == city) {
            city = cityService.getCityLocatedFromCookie(inv);
        }
        
        if (null == city) {
            city = cityService.getDefaultCity();
        }
        
        inv.addModel("_city", city);
        
        Map<String, Object> questionsMap = questionService.getQuestionListByOption(city.getCityId(), 1, 5, QuestionService.QA_SHOW_STATUS.ANSWERED, null, false);
        List<QuestionInfoExt> list = (List<QuestionInfoExt>) questionsMap.get(QuestionService.QUESTION_LIST);
        if (null == list || list.size() == 0) {
            return "quizListEmpty";
        }
        
        inv.addModel("quizList", list);
        inv.addModel(HASNEXT, questionsMap.get(QuestionService.HAS_NEXT));
        
        return "quizList";
    }
    
    /**
     * 问答列表异步获取接口
     * @param inv
     * @param cityName
     * @param pageNo
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    @Get("quizajax")
    @HttpFeatures(contentType="application/json")
    public String getQuizListByAnswerForAjax(Invocation inv, @Param("ct") String cityName, @DefValue("2")@Param(PAGE_NO) int pageNo, 
            @DefValue("5")@Param(PAGE_SIZE) int pageSize) {
        if (pageNo < 1) {
            pageNo = 2;
        }
        
        if (pageSize < 1) {
            pageSize = 5;
        }
        JSONObject ret = new JSONObject();
        ret.put(ERRORCODE, 0);
        try {
            
            DictCity city = null;
            
            if (StringUtils.isNotBlank(cityName)) {
                city = cityService.getCityByNameOrPinYin(cityName);
            }
            
            if (null == city) {
                city = cityService.getCityLocatedFromCookie(inv);
            }
            
            if (null == city) {
                city = cityService.getDefaultCity();
            }
            
            Map<String, Object> questionsMap = questionService.getQuestionListByOption(city.getCityId(), pageNo, pageSize, QuestionService.QA_SHOW_STATUS.ANSWERED, null, false);
            List<QuestionInfoExt> list = (List<QuestionInfoExt>) questionsMap.get(QuestionService.QUESTION_LIST);
            JSONObject data = new JSONObject();
            data.put(HASNEXT, questionsMap.get(QuestionService.HAS_NEXT));
            data.put(PAGE_NO, pageNo);
            data.put(PAGE_SIZE, list.size());
            //data.put("questionList", questionsMap.get(QuestionService.QUESTION_LIST));
            data.put("questionList", list);
            ret.put(DATA, data);
        } catch (Exception e) {
            logger.error("", e);
            ret.put(ERRORCODE, -1);
        }
        //return ret.toJSONString();
        return "@" + JSON.toJSONString(ret, QuestionInfoExtJsonFilter.getInstance(), SerializerFeature.WriteMapNullValue);
    }
    
    /**
     * 提问页面
     * @param inv
     * @param groupId
     * @return
     */
    @Get({"quiz/ask","quiz/ask/{groupId:[0-9]+}"})
    @AccessLogRequired
    @LoginRequired
    public String showAskPage(Invocation inv, @Param(GROUP_ID)int groupId) {
        
        if (groupId <= 0) {
            return "e:404";
        }
        
        Object o = inv.getModel(AppConstants.FOCUS_USER);
        if (null == o) {
            return "r:/login?ru=http://" + inv.getRequest().getServerName() + "/sohunews/zixun/ask/" + groupId;
        }
        
        BuildingInfo buildingInfo = buildingService.getBuildingShowInfo(groupId);
        
        if (null == buildingInfo) {
            return "e:404";
        }
        
        DictCity city = cityService.getCity(buildingInfo.getCityId());
        inv.addModel("_city", city);

        buildingInfo.setAreaDetail(buildingInfo.collateAreaDetail());
        buildingInfo.setSaleDateDetail(Utils.subSaleDateStr(buildingInfo.getSaleDateDetail()));
        buildingInfo.setMoveInDateDetail(Utils.subSaleDateStr(buildingInfo.getMoveInDateDetail()));
        
        int priceType = -2;
        int priceShow = 0;
        if(buildingInfo.getAvgPrice()!=null && buildingInfo.getAvgPrice()>0) {
            priceType = BuldingPrice.AVGPRICE;
            priceShow = buildingInfo.getAvgPrice();
        } else {
            if(buildingInfo.getLowPrice()!=null && buildingInfo.getLowPrice()>0) {
                priceType = BuldingPrice.MINPRICE;
                priceShow = buildingInfo.getLowPrice();
            } else {
                if(buildingInfo.getMaxPrice()!=null && buildingInfo.getMaxPrice()>0) {
                    priceType = BuldingPrice.MAXPRICE;
                    priceShow = buildingInfo.getMaxPrice();
                } else {
                    if(buildingInfo.getAllAvgPrice()!=null && buildingInfo.getAllAvgPrice()>0) {
                        priceType = BuldingPrice.ALLPRICE;
                        priceShow = buildingInfo.getAllAvgPrice();
                    }
                }
            }
        }
        inv.addModel("priceType", priceType);
        inv.addModel("priceShow", priceShow);
        
        
        buildingService.setBuildingPrice(buildingInfo);
        
        inv.addModel("building", buildingInfo);
        return "quizAsk";
    }
    
    /**
     * 用户提交问题（异步）
     * @param inv
     * @param groupId
     * @param question
     * @return
     */
    @AccessLogRequired
    @Post("quiz/ask/submit")
    @XSSFilter({"question"})
    @HttpFeatures(contentType="application/json")
    @LoginRequired
    public Object askSubmit(Invocation inv, @Param(GROUP_ID) int groupId) {
        String question = (String) inv.getAttribute("question");
        JSONObject ret = new JSONObject();
        ret.put(ERRORCODE, -1);
        
        UserBasic user = null;
        Object o = inv.getModel(AppConstants.FOCUS_USER);
        if (null == o) {
            return ret;
        } else {
           user = (UserBasic)o; 
        }
        if (groupId <= 0 || StringUtils.isBlank(question)) {
            return ret;
        }
        
        try {
            BuildingInfo buildingInfo = buildingService.getBuilding(groupId);
            if (null == buildingInfo) {
                return ret;
            }
            
            int questionId = xinFangCommonApiService.addQuestion(user.getUid(), groupId, question, false, buildingInfo.getCityId(), buildingInfo.getProjId());
            if (questionId < 0) {
                return ret;
            }
            ret.put(ERRORCODE, 0);
        } catch (Exception e) {
            logger.error("", e);
            ret.put(ERRORCODE, -1);
        }
        return ret;
    }
}
