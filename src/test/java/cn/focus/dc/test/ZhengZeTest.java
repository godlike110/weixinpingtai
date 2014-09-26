package cn.focus.dc.test;

import cn.focus.dc.focuswap.model.News;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZhengZeTest {

    @org.junit.Test
    public void Test() {
        News aaa = new News();
        // aaa.setContent("http://house.focus.cn/votehouse/8128/tu-12/");
        aaa.setContent("http://sh.focus.cn/votehouse/12345/xiangqing/");
        aaa.setTitle("aaa");
        // a.filterTagNews(aaa);
        // Pattern TESDT = Pattern.compile("http://house.focus.cn/votehouse/(\\d+)/tu-((?!11)[0-9]+)/",
        // Pattern.CASE_INSENSITIVE);
        Pattern TESDT = Pattern.compile("http://((?!(?:house|www))[a-z]{2,}).focus.cn/votehouse/(\\d+)/xiangqing/",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = TESDT.matcher(aaa.getContent());
        String old = "aaa";
        if (matcher.find()) {
            old = matcher.group(2);
        }
        System.out.println(old);
    }
}
