<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="cn.focus.dc.focuswap.service.CityService" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="cn.focus.dc.focuswap.model.DictCity" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <meta http-equiv="refresh" content="5; url=${ctx }/" />
    <title>页面未找到</title>

    <link rel="stylesheet" href="http://a1.itc.cn/sceapp/focus_static/wap/css/404style.min.css" />
    <link rel="stylesheet" href="http://a1.itc.cn/sceapp/focus_static/wap/css/style.min.css" />
    <script type="text/javascript" src="http://a1.itc.cn/sceapp/focus_static/wap/js/jquery2.0.3.min.js"></script>
    <script type="text/javascript" src="http://a1.itc.cn/sceapp/focus_static/wap/js/common.min.js"></script>
</head>
<body>
<div class="tittle">
    <img class="logo fl" src="http://a1.itc.cn/sceapp/focus_static/wap/images/logo.png" />
</div>
<ul class="uuu">
    <li id="firstli">
        <p><span class="sorry">抱歉！</span></p>
        <p><span class="sorry2">服务器开小差了！</span></p>
    </li>
    <li>
        <div class="back fl" id="b">返回上一页</div>
        <div class="back2home fl" url="${ctx }/">返回首页</div>
    </li>
</ul>


<%
    String domain = "http://house.focus.cn/?cfrom=mobile";
    WebApplicationContext ctx = (WebApplicationContext)application.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
    CityService cityService = (CityService)ctx.getBean("cityService");
    if (null != cityService) {
        DictCity city = cityService.getCityLocatedInfo((HttpServletRequest)request);
        if (null != city) {
            domain = "http://" + city.getDomainName();
        }
    }
%>
<div class="foot bgColor">
    <p><span><&nbsp;&nbsp;触屏版&nbsp;&nbsp;</span>|<a href="<%=domain%>"><span class="ac">&nbsp;&nbsp;电脑版&nbsp;&nbsp;></span></a></p>
    <p class="br">®2014 搜狐焦点</p>
</div>
<script>
    $(function(){
        $(".back").goBack();
        $(".back2home").gotoUrl({className:'touched'});
    });
</script>

</body>