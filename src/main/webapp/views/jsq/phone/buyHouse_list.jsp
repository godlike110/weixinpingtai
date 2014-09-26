<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, width=device-width" />
<meta name="format-detection" content="telephone=no,address=no,email=no" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<title>购房能力评估_推荐房源信息</title>
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css"
	href="http://192.168.242.44/sceapp/focus_static/wap/common/css/swiper_v1.0.css">
<link rel="stylesheet" type="text/css"
	href="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/css/phone_calculator_v1.0.css">
<link rel="stylesheet" type="text/css"
	href="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/css/moblie.css">
	<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
<script>
	var House = {
		price : "${price}",
		cityId : "${cityId}",
		area : "${area}"
	}
</script>
</head>
<body class="SP_taxation_black">

	<!-- 新版样式导航 -->
<c:if test="${sohunews}"><c:set var="ctx" value="/sohunews"/></c:if>
<jsp:include page="../phone/phoneHeader.jsp">
	<jsp:param value="推荐楼盘" name="page_title" />
</jsp:include>
<div class="calculator_main">
	
	<div class="swiper-container calculator_nav_swiper">
		<ul class="swiper-wrapper">
			<li class="swiper-slide" data-index="0"><a href="${ctx}/tools/sydk/" data-url="${ctx}/tools/sydk/">商业贷款</a><em>◆</em></li>
	        <li class="swiper-slide" data-index="1"><a href="${ctx}/tools/gjjdk/" data-url="${ctx}/tools/gjjdk/">公积金贷款</a><em>◆</em></li>
	        <li class="swiper-slide" data-index="2"><a href="${ctx}/tools/zhdk/" data-url="${ctx}/tools/zhdk/">组合贷款</a><em>◆</em></li>
			        <li class="swiper-slide" data-index="3"><a href="${ctx}/tools/tqhd/" data-url="${ctx}/tools/tqhd/">提前还贷</a><em>◆</em></li>
			        <li class="swiper-slide" data-index="4"><a href="${ctx}/tools/gfnlpg/" data-url="${ctx}/tools/gfnlpg/">购房能力</a><em>◆</em></li>
			        <li class="swiper-slide" data-index="5"><a href="${ctx}/tools/sf/" data-url="${ctx}/tools/sf/">税费计算</a><em>◆</em></li>
		</ul>
		<div class="pagination"></div>
	</div>
		</div>
		<div class="S_main">
			<div class="S_cal result">
				<h3 class="tit suggest_houses">推荐楼盘信息</h3>
				<div class="list_house">
					<ul id="houseList"></ul>

				</div>
			</div>
			<div id="has_more" class="has_more1">
				<a href="javascript:;" class="load_more" style="display: block;">滑动加载更多</a>
				<a href="javascript:;" class="loading" style="display: none;"><b
					class="rotating"></b>加载中...</a>
			</div>
		</div>

	</div>
	<!-- 面包屑 // -->
<c:if test="${!sohunews}">
	<div class="link_boxs" style="margin-top: 0px;">
		<a href="${ctx}/${_city.cityPinyinAbbr}/">新房</a><span class="h_space1">&gt;</span><a
			href="${ctx}/tools/gfnlpg/">购房能力评估</a><span class="h_space1">&gt;</span>推荐楼盘
	</div>
	<!-- // 面包屑 -->

	<!-- 底部导航 // -->
	<footer class="foot_nav" style="margin-top:;">
		<div class="foot_nav_box1">
			<a href="#" class="first current" role="text" url="">新房</a> <a
				href="#" class="last" role="text" url="">二手房</a>
		</div>
		<div class="foot_nav_box2">
			<span class="wap_version" role="wap_version_menu">手机版</span>
			<ul class="nav_box_ul1 wap_version_menu_ul1">
				<li><a href="#" role="text" url="" class="current">手机版</a></li>
				<!-- <li><a href="#" role="text" url="">电脑版</a></li> -->
			</ul>
		</div>
	</footer>
	<!-- // 底部导航 -->
</c:if>
	<div class="back2top" data-title="返回顶部"></div>

</body>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/snippets/phone_common_v1.0.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/swiper_v2.1.min.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/snippets/common.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/jsq/common/lib/lib-common.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/snippets/buyHouseList.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
<%@ include file="/views/pv.jsp"%>
</html>
