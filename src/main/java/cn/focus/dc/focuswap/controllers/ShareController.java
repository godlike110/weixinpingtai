package cn.focus.dc.focuswap.controllers;

import cn.focus.dc.focuswap.annotation.AccessLogRequired;
import cn.focus.dc.focuswap.service.ShareService;

import java.io.UnsupportedEncodingException;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * 分享接口
 * @author zhiweiwen
 * 2014-2-17
 * 上午10:51:15
 */
@Path("{cityName:[a-zA-Z]{2,}}/share")
@AccessLogRequired
public class ShareController {

    @Autowired 
    private ShareService shareService;
    
    @Get("/")
    public String shareDaogou(Invocation inv, @Param("cId") int cId, @Param("cType") int cType,
            @Param("auth") String auth, @Param("shareTo") String shareTo, @Param("pic") String pic,
            @Param("title") String title, @Param("url") String url) throws UnsupportedEncodingException {
        String rurl = shareService.getShareUrl(cType, shareTo, auth,pic,title,url);
        if(null == rurl) {	
            return "e:404";
        } 
        return "r:" + rurl;
    }

}
