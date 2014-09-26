package cn.focus.dc.focuswap.controllers;

import cn.focus.dc.focuswap.annotation.AccessLogRequired;

import org.apache.commons.lang.StringUtils;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("huodong")
@AccessLogRequired
public class HuoDongController {
    private static final String  ActivityAndroid618 = "601";
    
    private static final String ActivityIOS618 = "602";

    @Get("/")
    public String huodong(Invocation inv, @Param("channelId")String ch) {
        if (StringUtils.equals(ActivityAndroid618, ch) || StringUtils.equals(ActivityIOS618, ch)) {
            return "r:http://6.m.focus.cn/wap/";
        } else {
            return "r:/";
        }
    }
}
