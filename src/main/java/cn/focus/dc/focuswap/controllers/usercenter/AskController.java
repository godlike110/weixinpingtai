package cn.focus.dc.focuswap.controllers.usercenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import static cn.focus.dc.focuswap.config.AppConstants.*;
import cn.focus.dc.commons.model.UserBasic;
import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.annotation.LoginRequired;
import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.json.filter.QuestionInfoExtJsonFilter;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.QuestionInfoExt;
import cn.focus.dc.focuswap.service.CityService;
import cn.focus.dc.focuswap.service.QuestionService;
import cn.focus.dc.focuswap.utils.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.DefValue;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

/**
 * 个人中心--咨询列表
 * @author rogantian
 * @date 2014-7-21
 * @email rogantianwz@gmail.com
 */
@Path("/my/zixun")
@LoginRequired
public class AskController {
    
    protected static Log logger = LogFactory.getLog(AskController.class);
    
    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private CityService cityService;
    
    private static int PHONE_QUIZ_LIST_SIZE = 5;
    
    /**
     * 我的咨询列表--全部
     * @param inv
     * @param device
     * @param empty 测试用，当该参数为空时使用一个不存在的uid作为当前的uid
     * @param uid   测试用，模拟该用户，在正式上线的时候要拿掉该参数
     * @return
     */
    @SuppressWarnings("unchecked")
    @Get("all")
    @AccessLogRequired
    public String all(Invocation inv, @Param("empty") boolean empty) {
        /**
         * 先判断用户是否登录，如果未登录则返回登录页面
         */
        
        UserBasic user = null;
        
        Object o = inv.getModel(AppConstants.FOCUS_USER);
        if (null == o) {
            return "r:/login";
        } else {
           user = (UserBasic)o; 
        }
        
        /**
         * 测试代码
         */
        if (empty) {
            user.setUid(1427870122);
        }
        
        inv.addModel("model", QuestionService.QA_SHOW_STATUS.ALL.ordinal());
        
        int returnFlag = 1;
        try {
            returnFlag = Utils.getBackStatus(inv.getRequest());
        } catch (MalformedURLException e) {
            logger.error("", e);
        }
        inv.addModel("returnFlag", returnFlag);
        
        DictCity city = null;
        city = cityService.getCityLocatedFromCookie(inv);
        if (null == city) {
            city = cityService.getDefaultCity();
        }
        inv.addModel("_city", city);

        int pageSize = PHONE_QUIZ_LIST_SIZE;
        
        boolean hasNext = false;
        Map<String, Object> questionsMap = questionService.getQuestionListByOption(null, 1, pageSize, QuestionService.QA_SHOW_STATUS.ALL, user, true);
        List<QuestionInfoExt> list = (List<QuestionInfoExt>) questionsMap.get(QuestionService.QUESTION_LIST);
        if (null == list || list.size() == 0) {
            return "phone/myzixunEmpty";
        }
        hasNext = (Boolean) questionsMap.get(QuestionService.HAS_NEXT);
        inv.addModel("quizList", list);
        inv.addModel("hasNext", hasNext);
        return "phone/myzixunList";
    }
    
    /**
     * 我的咨询列表--已回答
     * @param inv
     * @param device
     * @param empty 测试用，当该参数为空时使用一个不存在的uid作为当前的uid
     * @param uid   测试用，模拟该用户，在正式上线的时候要拿掉该参数
     * @return
     */
    @SuppressWarnings("unchecked")
    @Get("yihuida")
    @AccessLogRequired
    public String yiHuiDa(Invocation inv, @Param("empty") boolean empty) {
        UserBasic user = null;
        
        Object o = inv.getModel(AppConstants.FOCUS_USER);
        if (null == o) {
            return "r:/login";
        } else {
           user = (UserBasic)o; 
        }
        
        /**
         * 测试代码
         */
        if (empty) {
            user.setUid(1427870122);
        }
        
        inv.addModel("model", QuestionService.QA_SHOW_STATUS.YIHUIDA.ordinal());
        int returnFlag = 1;
        try {
            returnFlag = Utils.getBackStatus(inv.getRequest());
        } catch (MalformedURLException e) {
            logger.error("", e);
        }
        inv.addModel("returnFlag", returnFlag);
        
        DictCity city = null;
        city = cityService.getCityLocatedFromCookie(inv);
        if (null == city) {
            city = cityService.getDefaultCity();
        }
        inv.addModel("_city", city);
        
        int pageSize = PHONE_QUIZ_LIST_SIZE;
        Map<String, Object> questionsMap = questionService.getQuestionListByOption(null, 1, pageSize, QuestionService.QA_SHOW_STATUS.YIHUIDA, user, true);
        List<QuestionInfoExt> list = (List<QuestionInfoExt>) questionsMap.get(QuestionService.QUESTION_LIST);
        if (null == list || list.size() == 0) {
            return "phone/myzixunList";
        }
        
        inv.addModel("quizList", list);
        inv.addModel("hasNext", questionsMap.get(QuestionService.HAS_NEXT));
        return "phone/myzixunList";
    }
    
    /**
     * 我的咨询列表--未回答
     * @param inv
     * @param devcie
     * @param empty 测试用，当该参数为空时使用一个不存在的uid作为当前的uid
     * @param uid   测试用，模拟该用户，在正式上线的时候要拿掉该参数
     * @return
     */
    @SuppressWarnings("unchecked")
    @Get("weihuida")
    @AccessLogRequired
    public String weiHuiDa(Invocation inv, @Param("empty") boolean empty) {
        UserBasic user = null;
        
        Object o = inv.getModel(AppConstants.FOCUS_USER);
        if (null == o) {
            return "r:/login";
        } else {
           user = (UserBasic)o; 
        }
        
        /**
         * 测试代码
         */
        if (empty) {
            user.setUid(1427870122);
        }
        
        inv.addModel("model", QuestionService.QA_SHOW_STATUS.WEIHUIDA.ordinal());
        int returnFlag = 1;
        try {
            returnFlag = Utils.getBackStatus(inv.getRequest());
        } catch (MalformedURLException e) {
            logger.error("", e);
        }
        inv.addModel("returnFlag", returnFlag);
        
        DictCity city = null;
        city = cityService.getCityLocatedFromCookie(inv);
        if (null == city) {
            city = cityService.getDefaultCity();
        }
        inv.addModel("_city", city);
        
        int pageSize = PHONE_QUIZ_LIST_SIZE;
        Map<String, Object> questionsMap = questionService.getQuestionListByOption(null, 1, pageSize, QuestionService.QA_SHOW_STATUS.WEIHUIDA, user, true);
        List<QuestionInfoExt> list = (List<QuestionInfoExt>) questionsMap.get(QuestionService.QUESTION_LIST);
        if (null == list || list.size() == 0) {
            return "phone/myzixunList";
        }
        
        inv.addModel("quizList", list);
        inv.addModel("hasNext", questionsMap.get(QuestionService.HAS_NEXT));
        return "phone/myzixunList";
    }
    
    /**
     * 我的咨询列表--异步分页加载接口
     * @param inv
     * @param device
     * @param type 列表类型 参考QuestionService.QA_SHOW_STATUS， 默认为ALL
     * @param pageNo 默认2
     * @param pageSize phone版默认为PHONE_QUIZ_LIST_SIZE， pad版默认为PAD_QUIZ_LIST_SIZE
     * @return
     */
    @SuppressWarnings("unchecked")
    @Get("listajax")
    public String listAjax(Invocation inv, @DefValue("0")@Param("type") int type, 
            @DefValue("2")@Param(PAGE_NO) int pageNo, @Param(PAGE_SIZE) int pageSize) {
        
        JSONObject ret = new JSONObject();
        ret.put(ERRORCODE, 0);
        
        UserBasic user = null;
        
        Object o = inv.getModel(AppConstants.FOCUS_USER);
        if(null == o) {
            ret.put(ERRORCODE, -1);
            return "@" + ret.toJSONString();
        } else {
            user = (UserBasic)o;
        }
        
        QuestionService.QA_SHOW_STATUS showStatus = null;
        
        if (type == QuestionService.QA_SHOW_STATUS.ALL.ordinal()) {
            showStatus = QuestionService.QA_SHOW_STATUS.ALL;
        } else if (type == QuestionService.QA_SHOW_STATUS.YIHUIDA.ordinal()) {
            showStatus = QuestionService.QA_SHOW_STATUS.YIHUIDA;
        } else if (type == QuestionService.QA_SHOW_STATUS.WEIHUIDA.ordinal()) {
            showStatus = QuestionService.QA_SHOW_STATUS.WEIHUIDA;
        } else {
            ret.put(ERRORCODE, -1);
            return "@" + ret.toJSONString();
        }
        
        if (pageNo < 1) {
            pageNo = 2;
        }
        
        if (pageSize < 1) {
            pageSize = PHONE_QUIZ_LIST_SIZE;
        }
        
        /**
         * 测试使用的数据
         *
        if (type == QuestionService.QA_SHOW_STATUS.ALL.ordinal() || type == QuestionService.QA_SHOW_STATUS.YIHUIDA.ordinal()) {
            uid = 10857592;
        } else {
            uid = 13135635;
        }
        */
        
        try {
            Map<String, Object> questionsMap = questionService.getQuestionListByOption(null, pageNo, pageSize, showStatus, user, true);
            List<QuestionInfoExt> list = (List<QuestionInfoExt>) questionsMap.get(QuestionService.QUESTION_LIST);
            JSONObject data = new JSONObject();
            data.put(HASNEXT, questionsMap.get(QuestionService.HAS_NEXT));
            data.put(PAGE_NO, pageNo);
            data.put(PAGE_SIZE, list.size());
            data.put("questionList", list);
            ret.put(DATA, data);
        } catch (Exception e) {
            logger.error("", e);
            ret.put(ERRORCODE, -1);
        }
        
        return "@" + JSON.toJSONString(ret, QuestionInfoExtJsonFilter.getInstance(), SerializerFeature.WriteMapNullValue);
    }
    
}
