package cn.focus.dc.test;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.focus.dc.focuswap.service.PaFangTuanService;
import cn.focus.dc.focuswap.service.XinFangCommonApiService;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.Pafangtuan;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/applicationContext-api.xml"})
public class PaFangTuanServiceTest extends BasicServiceTest{

    @Autowired
    private XinFangCommonApiService xinFangCommonApiService; 
    /**
     * 测试PaFangTuanService的getLatest2DaysList方法
     */
    @SuppressWarnings("unchecked")
    @Test
    //@Ignore
    public void testGetLatest2DaysList() {
        PaFangTuanService paFangTuanService = new PaFangTuanService();
        paFangTuanService.setXinFangCommonApiService(xinFangCommonApiService);
        Map<Date, Object> ret = paFangTuanService.getLatest2DaysList(1);
        if (null != ret) {
            Set<Date> keySet = ret.keySet();
            Iterator<Date> it = keySet.iterator();
            while (it.hasNext()) {
                Date key = it.next();
                Object list = ret.get(key);
                if (null != list && !key.equals(PaFangTuanService.SPECIAL_DATE_FOR_COUNT)) {
                    List<Pafangtuan> pftList = (List<Pafangtuan>) list;
                    System.out.println(key);
                    Iterator<Pafangtuan> it2 = pftList.iterator();
                    while (it2.hasNext()) {
                        Pafangtuan pft = it2.next();
                        System.out.println(pft.getTitle());
                    }
                } else {
                    int count = (Integer) list;
                    System.out.println("总数为：" + count);
                }
            }
        }
        
    }
    
    /**
     * 测试PaFangTuanService的getPafangtuanList方法
     */
    @SuppressWarnings("unchecked")
    @Test
    @Ignore
    public void testGetPafangtuanList() {
        PaFangTuanService paFangTuanService = new PaFangTuanService();
        paFangTuanService.setXinFangCommonApiService(xinFangCommonApiService);
        Map<String, Object> pftRet = paFangTuanService.getPafangtuanList(1, 3, 2);
        List<Pafangtuan> list = (List<Pafangtuan>) pftRet.get("list");
        boolean hasNext = (Boolean) pftRet.get("hasNext");
        System.out.println("hasNext:" + hasNext);
        if (null != list) {
            Iterator<Pafangtuan> it = list.iterator();
            while(it.hasNext()) {
                Pafangtuan pft = it.next();
                System.out.println(pft.getActiveDate() + "   " + pft.getTitle());
            }
        } else {
            System.out.println("列表为空");
        }
    }
    
    /**
     * 测试测试PaFangTuanService的getPafangtuanListByGroupId方法
     */
    @Test
    @Ignore
    public void testGetPafangtuanListByGroupId() {
        PaFangTuanService paFangTuanService = new PaFangTuanService();
        paFangTuanService.setXinFangCommonApiService(xinFangCommonApiService);
        List<Pafangtuan> list = paFangTuanService.getPafangtuanListByGroupId(24, 271346);
        if (null != list && list.size() > 0) {
            Iterator<Pafangtuan> it = list.iterator();
            while(it.hasNext()) {
                Pafangtuan pft = it.next();
                System.out.println(pft.getActiveDate() + "   " + pft.getTitle());
            }
        } else {
            System.out.println("列表为空");
        }
    }
}
