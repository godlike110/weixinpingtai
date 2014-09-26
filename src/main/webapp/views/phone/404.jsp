<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<meta name="apple-mobile-web-app-title" content="搜狐焦点房产">
<title>【${_city.cityName}楼盘|${_city.cityName}房价|${_city.cityName}新房】-搜狐焦点${_city.cityName}房产</title>
<meta name="Keywords" content="${_city.cityName}楼盘, ${_city.cityName}房价, ${_city.cityName}新房">
<meta name="Description" content="搜狐焦点${_city.cityName}房产网为广大网友提供了最新的${_city.cityName}楼盘信息，最准确的${_city.cityName}房价情况和最及时的${_city.cityName}新房资讯等，是买房、购房首选网站。 ">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
<script>
var city = ${city};
</script>
</head>
<body class="wap_hotquestion_all wap_notpage_all">
	<div class="wap_container">
		<!-- 404页—顶部导航 -->
		<header class="top-nav-new">
			<a href="${backUrl }" class="go_back" data-title="返回上一级">首页</a>
			<em class="logo"></em>
			<%-- <a class="go_home" href="${ctx }/${_city.cityPinyinAbbr }/"></a> --%>
		<div class="header_tool">
		<a href="javascript:;" class="nav-btn" role="main_menu"
			data-title="展开菜单">导航<i></i></a>
		</div>
		<%@include file="/views/phoneNav.jsp" %>
		</header>
		
		<!-- 404页主要内容板块 // -->
		<section class="no_page_main">
			<div class="search_no_result">
				<span class="search_no_result_icon">抱歉！您要找的页面出门爬房去了~</span>
			</div>
		</section>
		
		<!-- 底部导航 // -->
		<footer class="foot_nav">
			<div class="foot_nav_copyright">©2014 搜狐焦点</div>
			<div class="foot_nav_box2">
				<span class="wap_version" role="wap_version_menu">手机版</span>
				<ul class="nav_box_ul1 wap_version_menu_ul1">
					<li><a href="#" role="wap_version_text" url="" data-nr="PHONE" class="current">手机版</a></li>
					<li><a href="#" role="wap_version_text" url="" data-nr="PC">电脑版</a></li>
				</ul>
			</div>
		</footer>
		<!-- // 底部导航 -->
		
		<div class="back2top" data-title="返回顶部"></div>
		
	</div>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
	<%@ include file="/views/pv.jsp"%>
	<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>