<%@page import="cn.focus.dc.focuswap.model.TuanGou"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>楼盘团购</title>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/tuangou.css">
<script data-main="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/modulejs/app_tuangou" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/modulejs/lib/require.js" data-module="default"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
</head>
<body>
<!--顶部导航 -->
<!-- 新版样式导航 --> 
	<jsp:include page="/views/phoneHeader.jsp">
		<jsp:param value="楼盘团购" name="page_title" />
	</jsp:include>

<section class="tuangou_module tuangou_module_no">
</section>

<!-- 面包屑 -->
<div class="link_boxs"><a href="${ctx }/${_city.cityPinyinAbbr }/">新房</a><span class="h_space1">></span><a href="${ctx }/${_city.cityPinyinAbbr }/tuangou/">楼盘团购</a></div>

<!-- 底部导航 -->
<footer class="foot_nav">
    <div class="foot_nav_copyright">©2014 搜狐焦点</div>
    <!--div class="foot_nav_box2">
        <span class="wap_version" role="wap_version_menu">手机版</span>
        <ul class="nav_box_ul1 wap_version_menu_ul1">
            <li><a href="#" role="wap_version_text" data-nr="PC">电脑版</a></li>
            <li><a href="#" role="wap_version_text" data-nr="PHONE" class="current">手机版</a></li>
            <li><a href="#" role="wap_version_text" data-nr="PAD">Pad版</a></li>
        </ul>
    </div-->
</footer>

<!-- 团购详情弹层 -->
<div class="tuangou_mak">
    <div class="tuangou_mak_tips"></div>
</div>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>