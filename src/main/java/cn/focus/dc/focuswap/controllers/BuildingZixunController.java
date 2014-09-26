package cn.focus.dc.focuswap.controllers;

import static cn.focus.dc.focuswap.config.AppConstants.*;
import cn.focus.dc.commons.annotation.XSSFilter;
import cn.focus.dc.commons.model.UserBasic;
import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.model.BuildingInfo;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.QuestionInfo;
import cn.focus.dc.focuswap.model.QuestionInfoExt;
import cn.focus.dc.focuswap.service.BuildingService;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.QuestionService;
import cn.focus.dc.focuswap.service.XinFangCommonApiService;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.OperateType;
import cn.focus.dc.focuswap.utils.HtmlDigestUtil;
import cn.focus.dc.focuswap.utils.JsonResponseUtil;
import cn.focus.dc.focuswap.utils.Utils;

import com.alibaba.fastjson.JSONObject;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.HttpFeatures;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;



@Path("{cityName:[a-zA-Z]{2,}}/loupan")
@AccessLogRequired
@LoginRequired
public class BuildingZixunController {

    @Autowired
    private CityService cityService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private XinFangCommonApiService xinFangCommonApiService;

    private static final int PAGE_SIZE_NUMBER = 10;
    
    private static Log logger = LogFactory.getLog(BuildingZixunController.class);
    
    /**
     * @author kanezheng
     * @desc 咨询正文页
     * @url m.focus.cn/bj/loupan/7675/zixun/3901/
     * @time 2013.12.4
     * @param inv
     * @param questionId
     * @param groupId * @param device
     * @return
     * @throws ParseException
     * @throws MalformedURLException 
     */
    @Get("{groupId:[0-9]+}/zixun/{questionId:[0-9]+}/")
    public String getQuizInfo(Invocation inv, @Param("groupId") Integer groupId,
            @Param("questionId") int questionId, @Param("con") String con, @Param("from") String from)
            throws ParseException, MalformedURLException {
        QuestionInfo questionInfo = questionService.findById(questionId);
        if (null != questionInfo) {
            // ProjInfo projInfo = buildingService.getBuilding(questionInfo.getCityId(), questionInfo.getBuildId());
            BuildingInfo build = buildingService.getBuilding(groupId);
            if(build != null){
                buildingService.genRealPhotoPath(build, true);
                if (StringUtils.isNotBlank(build.getProjAddress()) && build.getProjAddress().length() > 12) {
                    build.setProjAddress(build.getProjAddress().substring(0, 10) + "...");
                }
                // 打折信息
                if ("待定".equals(build.getDiscount().trim()) || "暂无".equals(build.getDiscount().trim())
                        || "无".equals(build.getDiscount().trim()) || "0".equals(build.getDiscount().trim())) {
                    build.setDiscount("");
                }
                buildingService.setBuildingPrice(build);
                
                questionService.decorate(questionInfo, build);
            }
            inv.addModel(questionInfo);
            String answerDigest = StringUtils.EMPTY;
            if (StringUtils.isNotBlank(questionInfo.getAnswer())) {
                answerDigest = HtmlDigestUtil.getDigest(questionInfo.getAnswer(), false);
            }
            DictCity city = cityService.getCity(questionInfo.getCityId());
            JSONObject cityJson = (JSONObject)JSONObject.toJSON(city);
            inv.addModel("city", cityJson);
            inv.addModel("_city", city);
            inv.addModel("bl", build);
            inv.addModel("summary", answerDigest);
            inv.addModel("con", con);
            int returnFlag = Utils.getBackStatus(inv.getRequest());
            inv.addModel("returnFlag", returnFlag);
            if ("share".equals(from)) {
                return "phone/askShare";
            } else {
                return "phone/ask";
            }
        } else {
            return "e:404";
        }
    }
    
    /**
     * @author kanezheng
     * @desc 咨询列表
     * @url m.focus.cn/bj/loupan/7675/zixun/
     * @time 2013.12.4
     * @param inv
     * @param device
     * @param groupId
     * @return
     * @throws ParseException
     * @throws MalformedURLException 
     */
    @SuppressWarnings("unchecked")
    @Get("{groupId:[0-9]+}/zixun/")
    public String getQuizlist(Invocation inv,
            @Param("cityName") @DefValue("bj") String cityName, @Param("groupId") int groupId,
            @Param("con") String con) throws ParseException, MalformedURLException {
        DictCity city = cityService.getCityByNameOrPinYin(cityName);
        inv.addModel("_city", city);
        inv.addModel("con", con);
        BuildingInfo build = buildingService.getBuilding(groupId);
        inv.addModel("bl", build);
        
        Integer uid = null;
        Object o = inv.getModel(AppConstants.FOCUS_USER);
        if (null != o) {
            uid = ((UserBasic)o).getUid();
        }
        
        Map<String, Object> rsp = questionService.getQuestionInfoListByCreateTime(build, city, uid, 1, PAGE_SIZE_NUMBER);
        inv.addModel("hasNext", rsp.get(QuestionService.HAS_NEXT));
        List<QuestionInfo> questionList = (List<QuestionInfo>) rsp.get(QuestionService.QUESTION_LIST);
        
        
       /* List<QuestionInfo> questionList = questionService.getQuestionInfoList(groupId, 0,
                PAGE_SIZE_NUMBER + 1);
        for (QuestionInfo questionInfo : questionList) {
            questionService.decorate(questionInfo, build);
        }*/
        inv.addModel("questionList", questionList);
        StringBuilder sb = new StringBuilder("/loupan/").append(groupId + "").append("/zixunajax/");
        StringBuilder single = new StringBuilder("/loupan/").append(groupId + "").append("/zixun/");
        inv.addModel("city", Utils.putAjaxUrl(city, sb, single));
        /*if (questionList != null && questionList.size() > PAGE_SIZE_NUMBER) {
            inv.addModel("hasNext", true);
            questionList.remove(PAGE_SIZE_NUMBER);
        }*/
        int returnFlag = Utils.getBackStatus(inv.getRequest());
        inv.addModel("returnFlag", returnFlag);
        return "phone/askList";
    }
    

    /**
     * 咨询列表，ajax请求分页
     * 
     * @param inv
     * @param pageNo
     * @param pageSize
     * @param buildId
     * @param cityId
     * @return
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
    @Get("{groupId:[0-9]+}/zixunajax/")
    public String getQuizListAjax(Invocation inv, @Param(PAGE_NO) @DefValue("2") int pageNo,
            @Param(PAGE_SIZE) @DefValue("10") int pageSize, @Param("groupId") int groupId,
            @Param("cityName") @DefValue("bj") String cityName, @Param("callback") String fun)
            throws ParseException {

        // if (pageNo <= 0 || pageSize <= 0 || buildId <= 0 || cityId <= 0) {
        // return JsonResponseUtil.badResult("输入参数有误");
        // }
        BuildingInfo build = buildingService.getBuilding(groupId);
        // DictCity city = cityService.getCityByNameOrPinYin(cityName);
        /*List<QuestionInfo> questionList = questionService.getQuestionInfoList(groupId, (pageNo - 1)
                * pageSize, pageSize + 1);
        for (QuestionInfo questionInfo : questionList) {
            questionService.decorate(questionInfo, build);
        }*/
        
        Integer uid = null;
        Object o = inv.getModel(AppConstants.FOCUS_USER);
        if (null != o) {
            uid = ((UserBasic)o).getUid();
        }
        
        Map<String, Object> rsp = questionService.getQuestionInfoListByCreateTime(build, null, uid, pageNo, pageSize);
        boolean hasNext = (Boolean) rsp.get(QuestionService.HAS_NEXT);
        List<QuestionInfo> questionList = (List<QuestionInfo>) rsp.get(QuestionService.QUESTION_LIST);
        
        Map<String, Object> conditions = new HashMap<String, Object>();
        /*if (questionList.size() > pageSize) {
            questionList.remove(pageSize);
            hasNext = true;
        }*/
        conditions.put(PAGE_NO, pageNo);
        conditions.put(PAGE_SIZE, pageSize);
        conditions.put(HASNEXT, hasNext);

        return JsonResponseUtil.okSupportJSONPWithPaginate(questionList, fun, conditions);

    }
    
    @Autowired
    XinFangCommonApiService cs;
    @Get("{groupId:[0-9]+}/zixunajax/test/d/")
    public String test() {
        boolean ret = cs.deleteFav(OperateType.QUESTION, 85, 12345);
        return "@" + ret;
    }
    
    /**
     * 我要咨询页面
     * @param inv
     * @param device
     * @param groupId
     * @param cityName
     * @return
     */
    @Get("{groupId:[0-9]+}/woyaozixun")
    public String woYaoZiXun(Invocation inv,
            @Param("groupId") int groupId, @Param("cityName") String cityName) {

        BuildingInfo build = buildingService.getBuilding(groupId);
        if (null == build) {
            return "e:404";
        }
        
        DictCity city = cityService.getCity(build.getCityId());
        if (null == city) {
            return "e:404";
        }
        
        Object o = inv.getModel(AppConstants.FOCUS_USER);
        if (null == o) {
            return "r:/login?ru=http://" + inv.getRequest().getServerName() + "/" + cityName + "/loupan/" + groupId + "/woyaozixun";
        }
        
        inv.addModel("building", build);
        inv.addModel("_city", city);
        int returnFlag = 1;
        try {
            returnFlag = Utils.getBackStatus(inv.getRequest());
        } catch (MalformedURLException e) {
            logger.error("", e);
        }
        inv.addModel("returnFlag", returnFlag);
        return "phone/woyaozixun";
    }
    
    @Post("{groupId:[0-9]+}/woyaozixun/submit")
    @XSSFilter({"question"})
    @HttpFeatures(contentType="application/json")
    public Object ziXunSubmit(Invocation inv,@Param("groupId") int groupId, @Param("cityName") String cityName) {
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
      
            JSONObject data = new JSONObject();
            data.put("redirectUrl", "/" + cityName + "/loupan/" + groupId + "/woyaozixun/" + questionId);
            ret.put(DATA, data);
        } catch (Exception e) {
            logger.error("", e);
            ret.put(ERRORCODE, -1);
        }
        return ret;
    }
    
    /**
     * 咨询成功页面
     * @param inv
     * @param device
     * @param groupId
     * @param cityName
     * @param questionId
     * @return
     */
    @Get("{groupId:[0-9]+}/woyaozixun/{questionId:[0-9]+}")
    public String zixunSucess(Invocation inv,
            @Param("groupId") int groupId, @Param("cityName") String cityName,
            @Param("questionId") int questionId) {
        if (groupId < 1 || questionId < 1) {
            return "e:404";
        }
        
        BuildingInfo buildingInfo = buildingService.getBuilding(groupId);
        if (null == buildingInfo) {
            return "e:404";
        }
        //生成楼盘的logur
        buildingService.genRealPhotoPath(buildingInfo, true);
        
        int priceType = -2;
        int priceShow = 0;
        if(buildingInfo.getAvgPrice()!=null && buildingInfo.getAvgPrice()>0) {
            priceType = AppConstants.BuldingPrice.AVGPRICE;
            priceShow = buildingInfo.getAvgPrice();
        } else {
            if(buildingInfo.getLowPrice()!=null && buildingInfo.getLowPrice()>0) {
                priceType = AppConstants.BuldingPrice.MINPRICE;
                priceShow = buildingInfo.getLowPrice();
            } else {
                if(buildingInfo.getMaxPrice()!=null && buildingInfo.getMaxPrice()>0) {
                    priceType = AppConstants.BuldingPrice.MAXPRICE;
                    priceShow = buildingInfo.getMaxPrice();
                } else {
                    if(buildingInfo.getAllAvgPrice()!=null && buildingInfo.getAllAvgPrice()>0) {
                        priceType = AppConstants.BuldingPrice.ALLPRICE;
                        priceShow = buildingInfo.getAllAvgPrice();
                    }
                }
            }
        }
        inv.addModel("priceType", priceType);
        inv.addModel("priceShow", priceShow);
        
        DictCity city = cityService.getCity(buildingInfo.getCityId());
        if (null == city) {
            return "e:404";
        }
        
        QuestionInfoExt question = questionService.getQuestionExt(questionId);
        if (null == question) {
            return "e:404";
        }
        
        inv.addModel("building", buildingInfo);
        inv.addModel("_city", city);
        inv.addModel("question", question);
        
        int returnFlag = 1;
        try {
            returnFlag = Utils.getBackStatus(inv.getRequest());
        } catch (MalformedURLException e) {
            logger.error("", e);
        }
        inv.addModel("returnFlag", returnFlag);
        return "phone/zixunSucess";
        
    }
}
