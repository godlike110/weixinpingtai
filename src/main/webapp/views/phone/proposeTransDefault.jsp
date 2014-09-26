<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>楼盘导购-搜狐焦点${_city.cityName }房产</title>
<meta name="Keywords" content="楼盘导购，${_city.cityName }房产，${_city.cityName }楼盘" />
<meta name="Description" content="搜狐焦点${_city.cityName }房产网为广大网友提供了最新的${_city.cityName }楼盘信息，最准确的${_city.cityName }房价情况和最及时的${_city.cityName }新房资讯等，是买房、购房首选网站。 ">
<%@ include file="/views/icon.jsp" %>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<script>
var city = ${city};
</script>
</head>
<body class="wap_hotquestion_all wap_notpage_all">
	<div class="wap_container">
		<!-- 404页—顶部导航 -->
		<header class="top_nav">
			<a href="${ctx }/${_city.cityPinyinAbbr }/" class="go_back" data-title="返回上一级">首页</a>
			<em class="logo"></em>
			<a class="go_home" href="${ctx }/${_city.cityPinyinAbbr }/"></a>
		</header>
		
		<!-- 404页主要内容板块 // -->
		<section class="no_page_main">
			<div class="search_no_result">
				<span class="search_no_result_icon">您想看的导购，暂时还没有手机版</span>
			</div>
		</section>
		
		<!-- 底部导航 // -->
		<footer class="foot_nav">
			<div class="foot_nav_copyright">©2014 搜狐焦点</div>
			<div class="foot_nav_box2">
				<span class="wap_version" role="wap_version_menu">手机版</span>
				<ul class="nav_box_ul1 wap_version_menu_ul1">
					<c:if test="${mobile == 'false'}">
					<li><a href="#" role="wap_version_text" url="" data-nr="PAD">Pad版${phone}</a></li>
					</c:if>
					<li><a href="#" role="wap_version_text" url="" data-nr="PHONE" class="current">手机版</a></li>
					<li><a href="#" role="wap_version_text" url="" data-nr="PC">电脑版</a></li>
				</ul>
			</div>
		</footer>
		<!-- // 底部导航 -->
		
		<div class="back2top" data-title="返回顶部"></div>
		
	</div>
	<%@ include file="/views/pv.jsp"%>
</body>
</html>