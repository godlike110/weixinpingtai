<%@page import="java.text.MessageFormat"%>
<%@page import="cn.focus.dc.focuswap.service.SearchService.SearchType"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>【${_city.cityName }${chosenTkd }楼盘】-搜狐焦点${_city.cityName }房产</title>
<meta name="Keywords" content="${_city.cityName }楼盘,${chosenTkd }+楼盘信息">
<meta name="Description" content="搜狐焦点${_city.cityName }房产每天都有大量的更新房源，它汇聚了最新、最准确的${chosenTkd }的楼盘信息，是买房、购房的首选网站。 ">
<%@ include file="/views/icon.jsp" %>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_search_v1.0.css">
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<script>
var city = ${city};
var hasNext = ${hasNext};
</script>
</head>
<body class="wap_search_all wap_property_screening">
	<div class="wap_container" id="wrapper">
			<!-- 楼盘筛选页—顶部导航 -->
			<header class="top_nav">
				<a href="${ctx }/${_city.cityPinyinAbbr }/" class="go_back" data-title="返回上一级">首页</a>
				<span class="search_title">楼盘筛选</span>			
				<span class="menu_list" role="main_menu" role-addClass="its_open" data-title="展开列表"></span>
				<ul class="nav_box_ul1 main_menu_ul1">
					<li><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/" role="text" url="${ctx }/${_city.cityPinyinAbbr }/loupan/" role-current="current" class="current">楼盘</a></li>
					<c:if test="${totalDaogou > 0 }">
					<li><a href="${ctx}/${_city.cityPinyinAbbr}/daogou/" 	role="text" url="${ctx}/${_city.cityPinyinAbbr}/daogou/">导购</a></li>
					</c:if>
					<li><a href="${ctx }/${_city.cityPinyinAbbr }/zixun/" role="text" url="${ctx }/${_city.cityPinyinAbbr }/zixun/">新闻</a></li>
					<li><a href="${ctx }/${_city.cityPinyinAbbr }/baodian/" role="text" url="${ctx }/${_city.cityPinyinAbbr }/baodian/">宝典</a></li>
					<li><a href="${ctx }/${_city.cityPinyinAbbr }/pafangtuan/" role="text" url="${ctx }/${_city.cityPinyinAbbr }/pafangtuan/">看房团</a></li>
					
				</ul>	
			</header>
			
			<!-- 楼盘筛选页主要内容板块 // -->
			<section class="property_screening_main">
				<!-- 楼盘筛选-模块 // -->
				<section class="wap_module property_screening_module" data-title="楼盘筛选模块">
					<div class="property_screening_container">
						<c:if test="${empty chosen}">
							<div class="initial_state"><span class="fr more_btn1" role="filter" >更多筛选<em class="public_title_more1"></em></span>所有楼盘</div><!-- 初始显示 -->
						</c:if>
						<c:if test="${not empty chosen}">
							<div class="filter_list_box">
								<div class="filter_list_tit">已选择条件：</div>
								<div class="filter_list_nr">
									<c:forEach var="ch" items="${chosen}" varStatus="status">
										<a href="${ch.linkUrl }" class="its_tiaojian"><em class="public_close1 fr"></em><span>${ch.condName}</span></a>								
									</c:forEach>
								</div>
								<div class="filter_list_btns">
									<a href="/${_city.cityPinyinAbbr }/loupan/" class="more_btn1" role="clear" ><em class="public_close2 fr"></em>清空</a>
									<span class="more_btn1" role="filter" >更多筛选<em class="public_title_more1"></em></span>
								</div>
								<div class="search_results">在${_city.cityName }共找到${total}个符合条件的楼盘</div>
							</div>
						</c:if>
					</div>				
					<div class="borderTopBottom_e9e9e9"></div>
					<c:if test="${not empty buildingList}">
					<ul class="search_list_container" data-title="搜索结果列表">
						<c:forEach var="bl" items="${buildingList}" varStatus="status">
							<li>
							<a href="${bl.buildingUrl }" role="text" class="clearfix">
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
					</c:if>
					
					<!-- 筛选无结果 -->
					<c:if test="${empty buildingList}">
						<div class="filter_no_result show">
							<em class="filter_no_result_icon"></em><span>暂无符合条件的楼盘，请修改筛选条件</span>
						</div>
					</c:if>
	
				</section>
				<!-- // 楼盘筛选-模块 -->
				
				<!-- pull up -->
				<div class="pull font_gray">
					<div class="icon fl"></div>
					<p>滑动加载更多</p>
				</div>
			</section>
			<!-- // 楼盘筛选页主要内容板块 -->
			
			<!-- 面包屑 -->
			<div class="link_boxs"><a href="/${_city.cityPinyinAbbr }/">新房</a>
			<c:if test="${not empty districtName}"><span class="h_space1">></span>${districtName }楼盘</c:if>
			<c:if test="${empty districtName}"><span class="h_space1">></span>${_city.cityName }楼盘</c:if>
			</div>
			
			<!-- 底部导航 // -->
			<footer class="foot_nav">
				<div class="foot_nav_copyright">©2014 搜狐焦点</div>
				<div class="foot_nav_box2">
					<span class="wap_version" role="wap_version_menu">手机版</span>
					<ul class="nav_box_ul1 wap_version_menu_ul1">					
						<li><a href="#" role="wap_version_text" data-nr="PC">电脑版</a></li>
						<li><a href="#" role="wap_version_text" data-nr="PHONE" class="current">手机版</a></li>
						<c:if test="${mobile=='false'}">
							<li><a href="#" role="wap_version_text" data-nr="PAD">Pad版</a></li>
						</c:if>
					</ul>
				</div>
			</footer>
			<!-- // 底部导航 -->
						
			<!-- 筛选条件弹层 // -->
			<div class="property_screening_filter">
				<div class="its_power_switch" role="close"></div>
				<div class="its_filter_nr">
					<ul class="its_ul">
						<c:set var="district" value="<%=SearchType.DISTRICT %>"/>
						<c:if test="${not empty conditions[district] }">
						<li class="its_ul_li">
							<h3 role="tiaojian" data-filter="区域">区域<em class="its_arrow1 <c:if test="${fold == 'di' }">its_shouqi1</c:if>"></em></h3>
							<ul class="its_ul_li_ul filter_district_ul <c:if test="${fold == 'di' }">show</c:if>" data-filter="区域">
								<c:forEach var="co" items="${conditions[district]}" varStatus="status">
									<li><a href="${co.linkUrl}" role="tt" >${co.condName}</a>
									<em class="its_current <c:if test="${co.select == 'selected'}">show</c:if>"></em></li>
								</c:forEach>								
							</ul>
						</li>
						</c:if>
						<c:set var="type" value="<%=SearchType.TYPE %>"/>
						<c:if test="${not empty conditions[type] }">
						<li class="its_ul_li">
							<h3 role="tiaojian" data-filter="类型">类型<em class="its_arrow1" <c:if test="${fold == 'ty' }">its_shouqi1</c:if>></em></h3>
							<ul class="its_ul_li_ul filter_type_ul <c:if test="${fold == 'ty' }">show</c:if>" data-filter="类型">
								<c:forEach var="co" items="${conditions[type]}" varStatus="status">
									<li><a href="${co.linkUrl}" role="tt" >${co.condName}</a>
									<em class="its_current <c:if test="${co.select == 'selected'}">show</c:if>"></em></li>
								</c:forEach>								
							</ul>
						</li>
						</c:if>
						<c:set var="price" value="<%=SearchType.PRICE %>"/>
						<c:if test="${not empty conditions[price] }">
						<li class="its_ul_li">
							<h3 role="tiaojian" data-filter="价格">价格<em class="its_arrow1 <c:if test="${fold == 'pr' }">its_shouqi1</c:if>"></em></h3>
							<ul class="its_ul_li_ul filter_price_ul <c:if test="${fold == 'pr' }">show</c:if>" data-filter="价格">
								<c:forEach var="co" items="${conditions[price]}" varStatus="status">
									<li><a href="${co.linkUrl}" role="tt" >${co.condName}</a>
									<em class="its_current <c:if test="${co.select == 'selected'}">show</c:if>"></em></li>
								</c:forEach>
							</ul>
						</li>
						</c:if>
						<c:set var="hot" value="<%=SearchType.HOT %>"/>
						<c:if test="${not empty conditions[hot] }">
						<li class="its_ul_li">
							<h3 role="tiaojian" data-filter="热点区域">热点区域<em class="its_arrow1 <c:if test="${fold == 'ho' }">its_shouqi1</c:if>"></em></h3>
							<ul class="its_ul_li_ul filter_hotdistrict_ul <c:if test="${fold == 'ho' }">show</c:if>" data-filter="热点区域">
								<c:forEach var="co" items="${conditions[hot]}" varStatus="status">
									<li><a href="${co.linkUrl}" role="tt" >${co.condName}</a>
									<em class="its_current <c:if test="${co.select == 'selected'}">show</c:if>"></em></li>
								</c:forEach>	
							</ul>
						</li>
						</c:if>
						<c:set var="subway" value="<%=SearchType.SUBWAY %>"/>
						<c:if test="${not empty conditions[subway] }">
						<li class="its_ul_li">
							<h3 role="tiaojian" data-filter="轨道交通">轨道交通<em class="its_arrow1 <c:if test="${fold == 'su' }">its_shouqi1</c:if>"></em></h3>
							<ul class="its_ul_li_ul  filter_subway_ul <c:if test="${fold == 'su' }">show</c:if>" data-filter="轨道交通">
								<c:forEach var="co" items="${conditions[subway]}" varStatus="status">
									<li><a href="${co.linkUrl}" role="tt" >${co.condName}</a>
									<em class="its_current <c:if test="${co.select == 'selected'}">show</c:if>"></em></li>
								</c:forEach>	
							</ul>
						</li>
						</c:if>
						<c:set var="special" value="<%=SearchType.SPECIAL %>"/>
						<c:if test="${not empty conditions[special] }">
						<li class="its_ul_li">
							<h3 role="tiaojian" data-filter="特色楼盘">特色楼盘<em class="its_arrow1 <c:if test="${fold == 'sp' }">its_shouqi1</c:if>"></em></h3>
							<ul class="its_ul_li_ul filter_special_ul <c:if test="${fold == 'sp' }">show</c:if>" data-filter="特色楼盘">
								<c:forEach var="co" items="${conditions[special]}" varStatus="status">
									<li><a href="${co.linkUrl}" role="tt" >${co.condName}</a>
									<em class="its_current <c:if test="${co.select == 'selected'}">show</c:if>"></em></li>
								</c:forEach>	
							</ul>
						</li>
						</c:if>
					</ul>
				</div>
			</div>
			<!-- // 筛选条件弹层 -->
	</div>	
	<div class="back2top" data-title="返回顶部"></div>
	<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_filter_v1.0.js"></script>
	<%@ include file="/views/pv.jsp"%>
</body>
</html>