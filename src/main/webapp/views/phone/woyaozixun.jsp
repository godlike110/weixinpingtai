<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<meta name="apple-mobile-web-app-title" content="搜狐焦点房产" />
<%@ include file="/views/icon.jsp"%>
<title>在线咨询</title>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_zixun.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
</head>
<body>
<!-- 新版样式导航 --> 
	<jsp:include page="/views/phoneHeader.jsp">
		<jsp:param value="我要咨询" name="page_title" />
	</jsp:include>

<!-- 咨询页—主体 -->
<section class="zixun_info_main">
	<textarea class="zixun_info_content" placeholder="不超过35个汉字" maxlength="35"></textarea>
    <div class="zixun_info_submit">提交</div>
</section>

<!-- 面包屑 -->
<div class="link_boxs">
	<a href="${ctx }/${_city.cityPinyinAbbr }/">新房</a><span class="h_space1">&gt;</span><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/">${_city.cityName}楼盘</a><span class="h_space1">&gt;</span>
	<a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${groupId}/">
	<c:choose>
	   <c:when test="${fn:length(building.projName) > 4}">
			  ${fn:substring(building.projName, 0, 4)}...
	   </c:when>
	   <c:otherwise>
	   		  ${building.projName }
	   </c:otherwise>
	</c:choose>
	</a><span class="h_space1">&gt;我要咨询</span>
</div>

<!-- 底部导航 // -->
<footer class="foot_nav">
    <div class="foot_nav_copyright">&copy;2014 搜狐焦点</div>
</footer>

<div class="back2top" data-title="返回顶部"></div>
<script>var city = {"cityAbbr":"${_city.cityPinyinAbbr}"};var groupId = '${groupId}';</script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_zixun.js"></script>
<%@ include file="/views/pv.jsp"%>
</body>
</html>