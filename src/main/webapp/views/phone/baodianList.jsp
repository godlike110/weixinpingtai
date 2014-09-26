<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>【购房宝典|购房须知|购房指南】-搜狐焦点${_city.cityName }房产</title>
<meta name="Keywords" content="${_city.cityName }房产，购房宝典,购房须知 ,购房指南 ">
<meta name="Description"
	content="搜狐焦点${_city.cityName }房产购房宝典频道为您提供购房须知，买房技巧，买房注意事项，购房指南，购房攻略等信息。">
<%@ include file="/views/icon.jsp"%>
<link rel="stylesheet" type="text/css"
	href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css"
	href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
	<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
<script>
	var city = ${city};
</script>
</head>
<body class="wap_hotquestion_all wap_baodian_all">
	<div class="wap_container scroller" id="wrapper">
		<div id="scroller">

			<!-- 新版样式导航 -->
			<jsp:include page="/views/phoneHeader.jsp">
				<jsp:param value="购房宝典" name="page_title" />
			</jsp:include>


			<!-- 宝典列表页主要内容板块 // -->
			<section class="hotquestion_list_main">
				<div class="hotquestion_list_container">
					<section class="dynamic_info_container" data-title="宝典列表">
						<div class="dynamic_info_ask">
							<ul class="baodian_ul1">
								<c:forEach var="housingGuide" items="${guideList}"
									varStatus="status">
									<li class="dynamic_info_nr"><a
										href="${ctx }/${_city.cityPinyinAbbr }/baodian/${housingGuide.id}/"
										role="text"
										url="${ctx }/${_city.cityPinyinAbbr }/baodian/${housingGuide.id}/">
											<h2 class="its_biaoti">${housingGuide.title }</h2>
											<p class="its_writer_time">
												${housingGuide.catagoryName }<span class="h_space2">
													<fmt:formatDate value="${housingGuide.createTime }"
														pattern="M月d日" />
												</span>
											</p>
											<div class="img_box1">
												<img src="${housingGuide.picUrl}"
													alt="${housingGuide.title }" width="300" height="154" />
											</div> <c:if test="${not empty housingGuide.summary  }">
												<p class="its_summary">${housingGuide.summary }</p>
											</c:if>
									</a></li>
								</c:forEach>
							</ul>
						</div>
						<!-- pull up -->
						<c:if test="${hasNext}">
							<div class="pull font_gray">
								<div class="icon fl"></div>
								<p>滑动加载更多</p>
							</div>
						</c:if>
					</section>
				</div>
			</section>

			<!-- 面包屑 -->
			<div class="link_boxs">
				<a href="/${_city.cityPinyinAbbr }/">新房</a><span class="h_space1">></span>购房宝典
			</div>

			<!-- 底部导航 // -->
			<footer class="foot_nav">
				<div class="foot_nav_copyright">&copy;2014 搜狐焦点</div>
				<div class="foot_nav_box2">
					<span class="wap_version" role="wap_version_menu">手机版</span>
					<ul class="nav_box_ul1 wap_version_menu_ul1">
						<li><a href="#" role="wap_version_text" url=""
							data-nr="PHONE" class="current">手机版</a></li>
						<li><a href="#" role="wap_version_text" url="" data-nr="PC">电脑版</a></li>
					</ul>
				</div>
			</footer>
			<!-- // 底部导航 -->
		</div>
	</div>

	<div class="back2top" data-title="返回顶部"></div>
	<script type="text/javascript"
		src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_baodian_list_v1.0.js"></script>
		<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
	<%@ include file="/views/pv.jsp"%>
	<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>