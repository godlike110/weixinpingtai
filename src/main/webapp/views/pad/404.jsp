<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="telephone=no" name="format-detection" />
<meta name="apple-mobile-web-app-title" content="搜狐焦点房产">
<title>【${_city.cityName}楼盘|${_city.cityName}房价|${_city.cityName}新房】-搜狐焦点${_city.cityName}房产</title>
<meta name="Keywords"
	content="${_city.cityName}楼盘, ${_city.cityName}房价, ${_city.cityName}新房">
<meta name="Description"
	content="搜狐焦点${_city.cityName}房产网为广大网友提供了最新的${_city.cityName}楼盘信息，最准确的${_city.cityName}房价情况和最及时的${_city.cityName}新房资讯等，是买房、购房首选网站。 ">
<link rel="stylesheet"
	href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
<link rel="stylesheet"
	href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
<script
	src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script>
	var city = ${city};
</script>
</head>

<body class="bg_all">
	<section class="container">

		<%--新版导航 --%>
		<header class="top-nav-new page-list-header clearfix">
			<a href="${backUrl }" class="back fl">首页</a>
			<div class="logo clearfix">
				<a href="${ctx }/${_city.cityPinyinAbbr }/" class="fl"> <img
					src="http://a1.itc.cn/sceapp/focus_static/wap/images/logo.png"
					alt="首页" /></a>
			</div>
			
			<a href="javascript:;" class="nav-btn" role="main_menu"
				role-addclass="its_open" data-title="展开菜单">导航<i></i></a>
			<%@include file="/views/padNav.jsp" %>
		</header>

		<section class="content bg_all clearfix">
			<div class="no_result_blo">
				<span>抱歉，您要找的页面出门爬房去了~</span>
			</div>
		</section>

		<footer class="position_fixed">
			<span class="copyright">&copy;2014搜狐焦点</span>
			<div class="switch_version">
				<a href="javascript:;" class="version">Pad版</a>
				<div class="version_list">
					<a role="touch_bg" href="#" class="phone">手机版</a> <a
						role="touch_bg" href="#" class="ipad current">Pad版</a> <a
						role="touch_bg" href="#" class="pc">电脑版</a>
				</div>
			</div>
		</footer>
	</section>

	<!-- js -->
	<script
		src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
	<script
		src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
	<%@ include file="/views/pv.jsp"%>
</body>
</html>