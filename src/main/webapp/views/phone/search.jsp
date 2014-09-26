<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>【${_city.cityName }${group_name }楼盘】-搜狐焦点${_city.cityName }房产</title>
<meta name="Keywords" content="${group_name }楼盘">
<meta name="Description" content="搜狐焦点${_city.cityName }房产网为广大网友提供了最新的${group_name }楼盘信息，最准确的${group_name }房价情况和最及时的${group_name }新房资讯等，是买房、购房首选网站。 ">
<%@ include file="/views/icon.jsp" %>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_search_v1.0.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
<script>
var city = ${cityStr};
</script>
</head>
<body class="wap_search_all">
	<div class="wap_container">
		<!-- 新版样式导航 -->
		<jsp:include page="/views/phoneHeader.jsp">
			<jsp:param value="搜索" name="page_title" />
		</jsp:include>
		<!-- 搜索页主要内容板块 // -->
		<section class="search_main">
			<!-- 搜索框-模块 // -->
			<section class="wap_module search_module" data-title="搜索框-模块">
				<div class="search_bar_module" data-title="搜索框">
					<form name="searchForm" id="searchForm1" method="post" action="${ctx }/${_city.cityPinyinAbbr }/search/list/">
						<div class="search_bar_father">
							<div class="search_button fr" id="search_button1"></div>	
							<div class="search_bar">
								<input type="text" class="search_result1" autocomplete="off" name="group_name" id="search_result1" placeholder="输入楼盘名称/地点" value="${group_name }"/>
								<span class="clear_record1"></span>
							</div>
						</div>
					</form>
					<div class="search_results">在${_city.cityName }共找到${total}个<h1><span class="red1">${group_name }</span>楼盘</h1></div>
					<div class="search_result_boxs"><!-- 搜索结果—包含历史记录 -->
						<div class="clear_history">历史记录<span class="clear_record2">清空</span></div><!-- 清空历史记录 -->
						<ul class="search_result_ul1">
						</ul>
						<div class="close_searchbox" role="text" url="">关闭</div>
					</div>
				</div>
				
				
				<div class="borderTopBottom_e9e9e9"></div>
				<ul class="search_list_container" data-title="搜索结果-列表">
					<c:forEach var="bl" items="${buildingList}" varStatus="status">
						<li>
						<a href="${bl.buildingUrl }" role="text" url="${bl.buildingUrl }" class="clearfix">
							<div class="fl its_img"><img alt="${bl.projName }" src="${bl.url }"></div>
							<div class="its_content">
								<p class="it_name">${bl.projName }
								<c:choose>
									   <c:when test="${bl.saleStatus == 0}">
											  <em class='in_sale' data-title="在售"></em>
									   </c:when>
									   <c:when test="${bl.saleStatus == 1}">
											  <em class='wait_sale' data-title="待售"></em>
									   </c:when>
									   <c:when test="${bl.saleStatus == 2}">
											  <em class='last_sale' data-title="尾盘"></em>
									   </c:when>
									   <c:otherwise>
											  <em class='out_sale' data-title="售罄"></em>
									   </c:otherwise>
								</c:choose>
								
								</p>
								<p class="it_address">${bl.projAddress}</p>
								<p class="it_price">${bl.avgPriceShow}</p>
								<c:if test="${not empty fn:trim(bl.discount) }">
								<p class="it_zhekou"><em class="zhekou1"></em>${bl.discount}</p>
								</c:if>
							</div>
						</a>
					</li>
					</c:forEach>
				</ul>
				
			</section>
			<!-- // 搜索框-模块 -->
			
		</section>
		<!-- // 搜索页主要内容板块 -->
		
		<!-- pull up -->
		<c:if test="${hasNext}">
		<div class="pull font_gray">
			<div class="icon fl"></div>
			<p>滑动加载更多</p>
		</div>
		</c:if>
		<!-- 面包屑 -->
		<div class="link_boxs"><a href="/${_city.cityPinyinAbbr }/">新房</a><span class="h_space1">></span>${group_name }楼盘</div>
		
		<!-- 底部导航 // -->
		<footer class="foot_nav">
			<div class="foot_nav_copyright">©2014 搜狐焦点</div>
			<div class="foot_nav_box2">
				<span class="wap_version" role="wap_version_menu">手机版</span>
				<ul class="nav_box_ul1 wap_version_menu_ul1">
					<li><a href="#" role="wap_search_version_text" url="" data-nr="PHONE" class="current">手机版</a></li>
					<li><a href="#" role="wap_search_version_text" url="" data-nr="PC">电脑版</a></li>
				</ul>
			</div>
		</footer>
		<!-- // 底部导航 -->
	</div>
		
	<div class="back2top" data-title="返回顶部"></div>
	
	<div class="keywords_alert" id="keywords_alert1" style="">请输入关键词后再搜索</div>
	<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_search_v1.0.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
	<%@ include file="/views/pv.jsp"%>
	<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>