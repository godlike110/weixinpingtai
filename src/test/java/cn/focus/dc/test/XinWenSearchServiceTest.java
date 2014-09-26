package cn.focus.dc.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.focus.dc.focuswap.service.XinWenSearchService;
import cn.focus.dc.focuswap.service.XinWenSearchService.XinWenQueryData;
import cn.focus.dc.focuswap.service.XinWenSearchService.XinWenSearchCondition;
import cn.focus.dc.news.model.es.EsNews;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("product")
@ContextConfiguration(locations = { "file:src/test/resources/applicationContext-es.xml"})
public class XinWenSearchServiceTest {

    @Autowired
    private XinWenSearchService xinWenSearchService;
    
    /**
     * 测试方式基本功能，没有任何参数
     */
    @SuppressWarnings("unchecked")
    @Test
    @Ignore
    public void testXinWenSearchBasic() {
        XinWenSearchCondition searchCondition = new XinWenSearchCondition();
        
        Map<String, Object> ret = xinWenSearchService.xinWenSearch(searchCondition);
        
        assertNotNull(ret);
        
        int total = 0;
        
        Object totalObj = ret.get("total");
        if (null != totalObj) {
            total = (Integer)totalObj;
        }
        
        assertTrue(total > 0);
        
        List<EsNews> news = null;
        
        Object dataObj = ret.get("data");
        if (null != dataObj) {
             news = (List<EsNews>) ret.get("data");
        }
        
        assertNotNull(news);
        
    }
    
    
    /**
     * 测试方式基本功能，只有cityId、pageNo、pageSize
     */
    @SuppressWarnings("unchecked")
    @Test
    @Ignore
    public void testXinWenSearch() {
        XinWenSearchCondition searchCondition = new XinWenSearchCondition();
        searchCondition.setCityId(1);
        searchCondition.setPageNo(1);
        searchCondition.setPageSize(10);
        
        Map<String, Object> ret = xinWenSearchService.xinWenSearch(searchCondition);
        
        assertNotNull(ret);
        
        int total = 0;
        
        Object totalObj = ret.get("total");
        if (null != totalObj) {
            total = (Integer)totalObj;
        }
        
        assertTrue(total > 0);
        
        List<EsNews> news = null;
        
        Object dataObj = ret.get("data");
        if (null != dataObj) {
             news = (List<EsNews>) ret.get("data");
        }
        
        assertNotNull(news);
        
    }
    
    /**
     * 测试“市场”类别的新闻，城市为北京;
     */
    @SuppressWarnings("unchecked")
    @Test
    @Ignore
    public void testXinWenSearchMarket() {
        XinWenSearchCondition searchCondition = new XinWenSearchCondition();
        searchCondition.setCityId(1);
        
        List<XinWenQueryData> queryDatas = new ArrayList<XinWenQueryData>();
        queryDatas.add(new XinWenQueryData(XinWenSearchService.SearchType.MARKET));
        
        searchCondition.setQueryDatas(queryDatas);
        
        Map<String, Object> ret = xinWenSearchService.xinWenSearch(searchCondition);
        
        assertNotNull(ret);
        
        int total = 0;
        
        Object totalObj = ret.get("total");
        if (null != totalObj) {
            total = (Integer)totalObj;
        }
        
        assertTrue(total > 0);
        
        System.out.println("total:" + total);
        
        List<EsNews> news = null;
        
        Object dataObj = ret.get("data");
        if (null != dataObj) {
             news = (List<EsNews>) ret.get("data");
        }
        
        assertNotNull(news);
        
        printEsNewsForTest(news);
    }
    
    /**
     * 测试“政策”类别的新闻,城市为昆明，pageSize=20
     */
    @SuppressWarnings("unchecked")
    @Test
    @Ignore
    public void testXinWenSearchPolicy() {
        XinWenSearchCondition searchCondition = new XinWenSearchCondition();
        searchCondition.setCityId(22);
        searchCondition.setPageSize(20);
        
        List<XinWenQueryData> queryDatas = new ArrayList<XinWenQueryData>();
        queryDatas.add(new XinWenQueryData(XinWenSearchService.SearchType.POLICY));
        
        searchCondition.setQueryDatas(queryDatas);
        
        Map<String, Object> ret = xinWenSearchService.xinWenSearch(searchCondition);
        
        assertNotNull(ret);
        
        int total = 0;
        
        Object totalObj = ret.get("total");
        if (null != totalObj) {
            total = (Integer)totalObj;
        }
        
        assertTrue(total > 0);
        
        System.out.println("total:" + total);
        
        List<EsNews> news = null;
        
        Object dataObj = ret.get("data");
        if (null != dataObj) {
             news = (List<EsNews>) ret.get("data");
        }
        
        assertNotNull(news);
        
        printEsNewsForTest(news);
    }
    
    /**
     * 测试“观点”类别的新闻,城市为成都，pageNo=2
     */
    @SuppressWarnings("unchecked")
    @Test
    @Ignore
    public void testXinWenSearchPoint() {
        XinWenSearchCondition searchCondition = new XinWenSearchCondition();
        searchCondition.setCityId(11);
        searchCondition.setPageNo(2);
        
        List<XinWenQueryData> queryDatas = new ArrayList<XinWenQueryData>();
        queryDatas.add(new XinWenQueryData(XinWenSearchService.SearchType.POINT));
        
        searchCondition.setQueryDatas(queryDatas);
        
        Map<String, Object> ret = xinWenSearchService.xinWenSearch(searchCondition);
        
        assertNotNull(ret);
        
        int total = 0;
        
        Object totalObj = ret.get("total");
        if (null != totalObj) {
            total = (Integer)totalObj;
        }
        
        assertTrue(total > 0);
        
        System.out.println("total:" + total);
        
        List<EsNews> news = null;
        
        Object dataObj = ret.get("data");
        if (null != dataObj) {
             news = (List<EsNews>) ret.get("data");
        }
        
        assertNotNull(news);
        
        printEsNewsForTest(news);
    }
    
    /**
     * 测试“本地”类别（“本地新闻”）的新闻,城市为哈尔滨，pageNo=0, pageSize=5;
     */
    @SuppressWarnings("unchecked")
    @Test
    @Ignore
    public void testXinWenSearchLocal() {
        XinWenSearchCondition searchCondition = new XinWenSearchCondition();
        searchCondition.setCityId(19);
        searchCondition.setPageNo(0);
        searchCondition.setPageSize(5);
        
        List<XinWenQueryData> queryDatas = new ArrayList<XinWenQueryData>();
        queryDatas.add(new XinWenQueryData(XinWenSearchService.SearchType.LOCAL,451));
        
        searchCondition.setQueryDatas(queryDatas);
        
        Map<String, Object> ret = xinWenSearchService.xinWenSearch(searchCondition);
        
        assertNotNull(ret);
        
        int total = 0;
        
        Object totalObj = ret.get("total");
        if (null != totalObj) {
            total = (Integer)totalObj;
        }
        
        assertTrue(total > 0);
        
        System.out.println("total:" + total);
        
        List<EsNews> news = null;
        
        Object dataObj = ret.get("data");
        if (null != dataObj) {
             news = (List<EsNews>) ret.get("data");
        }
        
        assertNotNull(news);
        
        printEsNewsForTest(news);
    }
    
    /**
     * 测试“更多”类别,城市为威海，pageNo=1, pageSize=30;
     */
    @SuppressWarnings("unchecked")
    @Test
    @Ignore
    public void testXinWenSearchMore() {
        XinWenSearchCondition searchCondition = new XinWenSearchCondition();
        searchCondition.setCityId(40);
        searchCondition.setPageNo(1);
        searchCondition.setPageSize(30);
        
        List<XinWenQueryData> queryDatas = new ArrayList<XinWenQueryData>();
        queryDatas.add(new XinWenQueryData(XinWenSearchService.SearchType.MORE,451));
        
        searchCondition.setQueryDatas(queryDatas);
        
        Map<String, Object> ret = xinWenSearchService.xinWenSearch(searchCondition);
        
        assertNotNull(ret);
        
        int total = 0;
        
        Object totalObj = ret.get("total");
        if (null != totalObj) {
            total = (Integer)totalObj;
        }
        
        assertTrue(total > 0);
        
        System.out.println("total:" + total);
        
        List<EsNews> news = null;
        
        Object dataObj = ret.get("data");
        if (null != dataObj) {
             news = (List<EsNews>) ret.get("data");
        }
        
        assertNotNull(news);
        
        printEsNewsForTest(news);
    }
    
    private void printEsNewsForTest(List<EsNews> news) {
        if (null == news) {
            return;
        }
        for (EsNews esNew : news) {
            StringBuilder sb = new StringBuilder();
            sb.append("id:").append(esNew.getNewsId());
            sb.append(" title:").append(esNew.getTitle());
            sb.append(" imgLog:").append(esNew.getImgLogo());
            sb.append(" showTime:").append(new Date(esNew.getShowTime()));
            sb.append(" newsClassId:").append(esNew.getNewsClassId());
            sb.append(" newsDesc:").append(esNew.getNewsDesc());
            
            System.out.println(sb.toString());
        }
    }
    
    
}
