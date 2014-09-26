<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>${housingGuide.title }_购房宝典-搜狐焦点${_city.cityName }房产</title>
<meta name="keywords" content="购房宝典，购房指南"/>
<meta name="description" content="${housingGuide.summary}"/>
<%@ include file="/views/icon.jsp" %>
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
<body class="wap_hotquestion_all wap_baodian_info_all wap_baodian_info_self">
	<div class="wap_container">
			<!-- 新版样式导航 -->
		<jsp:include page="/views/phoneHeader.jsp">
			<jsp:param value="详情" name="page_title" />
		</jsp:include>
		
		
		<!-- 宝典正文页主要内容板块 // -->
		<section class="baodian_info_main">
			<div class="baodian_info_container">	
				<section class="dynamic_info_container" data-title="宝典正文">
					<div class="dynamic_info_ask">
						<h1 class="its_biaoti">${housingGuide.title }</h1>
						<p class="its_writer_time">${housingGuide.catagoryName }<span class="h_space2">
						<fmt:formatDate value="${housingGuide.createTime }" pattern="yyyy年M月d日"/>
						</span></p>
						<div class="dynamic_info_nr">
							<div class="img_box1"><img src="${housingGuide.picUrl}" alt="${housingGuide.title }" /></div>
							<p>${housingGuide.content }</p>
						</div>
					</div>
				</section>
				
			</div>
		</section>
		
		<!-- 面包屑 -->
		<div class="link_boxs"><a href="/${_city.cityPinyinAbbr }/">新房</a><span class="h_space1">></span><a href="${ctx }/${_city.cityPinyinAbbr }/baodian/">购房宝典</a><span class="h_space1">></span>购房宝典正文</div>
		
		<!-- 底部导航 // -->
		<footer class="foot_nav">
			<div class="foot_nav_copyright">&copy;2014 搜狐焦点</div>
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