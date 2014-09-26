package cn.focus.dc.focuswap.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 从html内容中获取摘要的工具类
 * 
 * @author rogantian
 */
public class HtmlDigestUtil {
    
    protected static Log logger = LogFactory.getLog(HtmlDigestUtil.class);

    // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
    private static String REGEX_SCRIPT = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>";

    // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>
    private static String REGEX_STYLE = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>";

    private static String REGEX_HTML = "<[^>]+>"; // 定义HTML标签的正则表达式

    private static String REGEX_HTML1 = "<[^>]+";

    private static Integer DEFAULT_DIGEST_SUB_LENGTH = 50;
 
    @SuppressWarnings("unused")
    private static Pattern PSCRIPT = Pattern.compile(REGEX_SCRIPT);

    @SuppressWarnings("unused")
    private static Pattern PSTYLE = Pattern.compile(REGEX_STYLE);

    private static Pattern PHTML = Pattern.compile(REGEX_HTML);

    private static Pattern PHTML1 = Pattern.compile(REGEX_HTML1);

    private HtmlDigestUtil() {

    } 

    /**
     * 获取html内容的摘要
     * 
     * @param srcHtml html内容
     * @param preserveTag 是否保留html标签
     * @return
     */
    public static String getDigest(String srcHtml, boolean preserveTag) {
        if (!preserveTag) {
            return getDigestWithoutTag(srcHtml);
        } else {
            return getDigestWithTag(srcHtml);
        }
    }

    // TODO
    private static String getDigestWithTag(String srcHtml) {
        return null;
    }

    /**
     * 以 第一个"。"或者"</p>"作为截取点
     */
    private static String getDigestWithoutTag(String srcHtml) {
        int subIndex = 0;
        int firstFullStopIndex = srcHtml.indexOf("。");
        if (firstFullStopIndex == -1) {
            firstFullStopIndex = Integer.MAX_VALUE;
        }

        int firstFeedLineIndex = srcHtml.indexOf("</p>");
        if (firstFeedLineIndex == -1) {
            firstFeedLineIndex = Integer.MAX_VALUE;
        }

        subIndex = Math.min(firstFullStopIndex, firstFeedLineIndex);
        if (subIndex == Integer.MAX_VALUE || subIndex <= 0) {
            subIndex = DEFAULT_DIGEST_SUB_LENGTH;
        }

        if (subIndex >= srcHtml.length()) {
            subIndex = srcHtml.length();
        }

        return html2Text(srcHtml.substring(0, subIndex));
    }

    // 暂时只考虑html常规标签，script标签和style标签先不考虑
    public static String html2Text(String srcHtml) {
        String outPut = StringUtils.EMPTY;
        try {
            Matcher mHtml = PHTML.matcher(srcHtml);
            srcHtml = mHtml.replaceAll("");

            Matcher mHtml1 = PHTML1.matcher(srcHtml);
            srcHtml = mHtml1.replaceAll("");
            outPut = srcHtml;
        } catch (Exception e) {
            logger.error("", e);
        }

        return outPut;
    }
}
