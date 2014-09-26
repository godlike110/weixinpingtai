package cn.focus.dc.focuswap.service;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.utils.MD5Util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import net.paoding.rose.web.Invocation;

import org.springframework.stereotype.Service;

/**
 * 分享服务
 * 
 * @author zhiweiwen 2014-2-17 上午10:47:52
 */
@Service
public class ShareService {
    /**
     * 获取分享url
     * 
     * @param cType 分享类型
     * @param content 分享内容
     * @param auth 权限验证参数
     * @return
     * @throws UnsupportedEncodingException 
     */
    public String getShareUrl(int cType, String shareTo, String auth, String pic, String title, String url)
            throws UnsupportedEncodingException {
        if (auth == null) {
            return null;
        }
        
        StringBuffer cResult = new StringBuffer();
        
        cResult.append("&pic=").append(pic).append("&title=").append(title).append("&url=").append(url);
        //如果容器没有decode自己来
        //String result = URLDecoder.decode(cResult.toString().replaceAll("%", "%25"), "UTF-8");
        String result = cResult.toString();
        // 只有qq空间分享参数里面使用的是pics 其他的都是pic
        
        if ("true".equals(AppConstants.SHARE_AUTH_OPEN)) {
            if (checkAuth(auth, result)) {               
                result = pinzhuangUrl(pic,title,url,shareTo);
                return AppConstants.ContentShare.getShareUrl(shareTo) + result;
            }
        } else {
            result = pinzhuangUrl(pic,title,url,shareTo);
            return AppConstants.ContentShare.getShareUrl(shareTo) + result;
        }
        return null;
    }

    /**
     * 验证权限
     * 
     * @param auth 权限参数
     * @param content 分享内容
     * @return
     */
    private boolean checkAuth(String auth, String content) {

        if (auth == null || content == null) {
            return false;
        }
        String result = MD5Util.md5(content);
        if (auth.equals(result)) {
            return true;
        }
        return false;
    }
    
    /**
     *
     * 生成分享内容
     */
    public String spliceShareContent(String pic,String title,String url) {
    
        if(pic==null || title==null || url==null) {
        return null;
        }
        
        String content = "@@" + pic + "@@" + title + "@@" + url;
    
        return content;
    
    }

    private String pinzhuangUrl(String pic,String title,String url,String shareTo) throws UnsupportedEncodingException{
        url += "?fromId=" + shareTo;                 
        String result = "&pic=" + URLEncoder.encode(pic, "UTF-8") + "&title=" + URLEncoder.encode(title, "UTF-8")
                + "&url=" + URLEncoder.encode(url, "UTF-8");
        if ("qZone".equals(shareTo)) {
            result = result.replaceFirst("pic", "pics");
            result = result.replaceFirst("title", "desc");
        } 
        return result;
    }
    
    /**
     * http://confluence.sohuno.com/pages/viewpage.action?pageId=10949029
     * @param inv
     * @param title
     * @param picUrl
     * @param type   分享类型 1:导购 2 新闻
     * @param id
     * @param cityName
     * @throws UnsupportedEncodingException
     */
    public void share(Invocation inv,String title,String picUrl,Integer type,Integer id,String cityName) throws UnsupportedEncodingException{
        String enTitle = URLEncoder.encode(title,"UTF-8");
        String enPic = URLEncoder.encode(picUrl==null?"":picUrl,"UTF-8");
        String enUrl = URLEncoder.encode(inv.getRequest().getRequestURL().toString(),"UTF-8");
        //md5加密
        StringBuffer md5 = new StringBuffer();
        md5.append("&pic=").append(picUrl==null?"":picUrl).append("&title=").append(title).append("&url=")
            .append(inv.getRequest().getRequestURL().toString());
        String auth = MD5Util.md5(md5.toString());
        //url拼接
        StringBuffer url = new StringBuffer("/");
        url.append(cityName).append("/share?cId=")
        .append(id).append("&cType=2")
        .append("&auth=").append(auth)
        .append("&pic=").append(enPic)
        .append("&title=").append(enTitle)
        .append("&url=").append(enUrl);
        
        inv.addModel("qZone", url.toString()+"&shareTo=qZone");
        inv.addModel("tWeibo", url.toString()+"&shareTo=tWeibo");
        inv.addModel("sWeibo", url.toString()+"&shareTo=sWeibo");
    }
}
