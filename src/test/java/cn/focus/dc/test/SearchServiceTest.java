package cn.focus.dc.test;


import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.focus.dc.building.model.es.EsProjInfo;
import cn.focus.dc.focuswap.model.EsProjInfoChild;
import cn.focus.dc.focuswap.service.SearchService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/applicationContext-config.xml"
         ,"file:src/main/webapp/WEB-INF/applicationContext-es.xml"})
public class SearchServiceTest extends AbstractJUnit4SpringContextTests {
    
    @Autowired
    private SearchService search;
    
    @Test
    public void test2() { 
        List<EsProjInfo> list = search.projNameSearch("建业", 1, 10);
        System.out.println(list);
    }
    
    @Test
    public void test3() { 
        List<EsProjInfoChild> list = search.projGroupIdSearch(Arrays.asList(1,2,3));
        System.out.println(list);
    }
    
    @Test
    public void test1() {
        
        JSONObject obj = new JSONObject();
        obj.put("cityId", 1);
        obj.put("pageSize",10);
        obj.put("pageNo", 1);
        obj.put("queryStr", "");
        
        // 搜索组合条件
        JSONArray conds = new JSONArray();
        
        // 价格0-10000
        JSONObject price = new JSONObject();
        price.put("queryType", "PRICE");
        price.put("qSelect", "0-10000");
        
        // 热点板块
        JSONObject hot = new JSONObject();
        hot.put("queryType", "HOT");
        hot.put("qSelect", "25");

        // 特色楼盘
        JSONObject special = new JSONObject();
        special.put("queryType", "SPECIAL");
        special.put("qSelect", "XF");
        
        // 区域
        JSONObject district = new JSONObject();
        district.put("queryType", "DISTRICT");
        district.put("qSelect", "2");
        
        // 楼盘类型
        JSONObject loupanType = new JSONObject();
        loupanType.put("queryType", "TYPE");
        loupanType.put("qSelect", "2");
        
        // 轨道交通
        JSONObject subway = new JSONObject();
        subway.put("queryType", "SUBWAY");
        subway.put("qSelect", "6");
        
        
        // conds.add(price);
        // conds.add(hot);
        // conds.add(subway);
        
        conds.add(special);
        obj.put("queryData", conds);
        
        // Map<String,Object> ret = search.projSearch(obj);
    }

}
