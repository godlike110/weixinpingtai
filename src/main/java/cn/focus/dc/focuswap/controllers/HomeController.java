package cn.focus.dc.focuswap.controllers;


import cn.focus.dc.focuswap.annotation.LoginRequired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import net.paoding.rose.web.annotation.Path;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static cn.focus.dc.focuswap.config.AppConstants.*;

@Path("home")
public class HomeController {

    // 打折楼盘
    public static final String DZLP = "DZLP";

    // 小户型
    public static final String XHX = "XHX";

    // 最新开盘
    public static final String ZXKP = "ZXKP";

    @SuppressWarnings("unused")
    private static Log logger = LogFactory.getLog(HomeController.class);

    @SuppressWarnings("unused")
    private static List<SearchCondition> getSearchConditionList(JSONObject json) {
        List<SearchCondition> ret = new ArrayList<SearchCondition>();
        JSONArray jsonArray = json.getJSONArray("cList");
        int cType = json.getIntValue("cTypeId");
        if (null != jsonArray && jsonArray.size() > 0) {
            for (Object obj : jsonArray) {
                JSONObject jsonObj = (JSONObject) obj;
                String qStr = jsonObj.getString("qStr");
                // 价格条件，“0”的价格条件不显示
                if (cType == CONDITION_PRICES && "0".equals(qStr)) {
                    continue;
                }
                SearchCondition sc = new SearchCondition(jsonObj.getString("qName"), qStr);
                ret.add(sc);
            }
        }
        return ret;
    }

    public static class SearchCondition {

        private String condName;

        private String condValue;

        public SearchCondition(String name, String value) {
            this.condName = name;
            this.condValue = value;
        }

        public String getCondName() {
            return condName;
        }

        public void setCondName(String condName) {
            this.condName = condName;
        }

        public String getCondValue() {
            return condValue;
        }

        public void setCondValue(String condValue) {
            this.condValue = condValue;
        }

        @Override
        public String toString() {
            return condValue + "@@" + condName;
        }

    }

}
