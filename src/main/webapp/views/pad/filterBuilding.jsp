<%@page import="cn.focus.dc.focuswap.service.SearchService.SearchType"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<c:set var="DISTRICT" value="<%= SearchType.DISTRICT%>"></c:set>
<c:set var="HOT" value="<%= SearchType.HOT%>"></c:set>
<c:set var="PRICE" value="<%= SearchType.PRICE%>"></c:set>
<c:set var="SUBWAY" value="<%= SearchType.SUBWAY%>"></c:set>
<c:set var="SPECIAL" value="<%= SearchType.SPECIAL%>"></c:set>
<c:set var="TYPE" value="<%= SearchType.TYPE%>"></c:set>
<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	<meta content="telephone=no" name="format-detection" />
<c:choose>
<c:when test="${fn:length(showChosenConMap) ==1 && ( not empty showChosenConMap[DISTRICT] || not empty showChosenConMap[HOT] )}">
<title>${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }楼盘_${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }新楼盘_${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }房价-${_city.cityName }搜狐焦点网</title>
<meta name="Keywords" content="${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }楼盘，${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }新楼盘，${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }房价" />
<meta name="Description" content="${_city.cityName }搜狐焦点网楼盘搜索频道为您提供${_city.cityName }${showChosenConMap[DISTRICT].condName}${showChosenConMap[HOT].condName }楼盘查询、${showChosenConMap[DISTRICT].condName}${showChosenConMap[HOT].condName }新楼盘查询、${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }房价，让您快速方便的找房，更多${_city.cityName }${showChosenConMap[DISTRICT].condName}${showChosenConMap[HOT].condName }楼盘信息，尽在${_city.cityName }搜狐焦点网。"/>
</c:when>
<c:otherwise>
<title>${_city.cityName }${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }${showChosenConMap[SUBWAY].condName }${showChosenConMap[PRICE].condName }${showChosenConMap[SPECIAL].condName }${showChosenConMap[TYPE].condName }新楼盘房价-${_city.cityName }搜狐焦点网</title>
<meta name="Keywords" content="${_city.cityName }${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }${showChosenConMap[SUBWAY].condName }${showChosenConMap[PRICE].condName }${showChosenConMap[SPECIAL].condName }${showChosenConMap[TYPE].condName }楼盘，${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }新楼盘，${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }房价" />
<meta name="Description" content="${_city.cityName }搜狐焦点网楼盘搜索频道为您提供${_city.cityName }${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }${showChosenConMap[SUBWAY].condName }${showChosenConMap[PRICE].condName }${showChosenConMap[SPECIAL].condName }${showChosenConMap[TYPE].condName }新楼盘房价，让您快速方便的找房，更多${_city.cityName }${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }楼盘信息，尽在${_city.cityName }搜狐焦点网。"/>
</c:otherwise>
</c:choose>
	
	<%@ include file="/views/icon.jsp" %>
	<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
	<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
	<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
	<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
	<script>
		var city = ${city};
		var hasNext = ${hasNext};
		var con = "${con}";
		var pageNo = ${pageNo}+1;
	</script>
</head>
	
<body class="bg_all">
	<section class="container">
		<jsp:include page="/views/padHeader.jsp">
			<jsp:param value="楼盘筛选" name="page_title" />
		</jsp:include>
		
			
		<section class="content bg_all">
			<aside class="scroll_mod filter_list" id="filter_list" style="position:absolute;left:0;top:0">
				<div class="scroller">
					<ul id="filter_items">
						
						<c:if test="${not empty conditions[DISTRICT] }">
						<li ftype="di" <c:if test="${fold == 'di' }">class="unfold"</c:if>>
							<div class="">
								<div class="dt">区域</div>
								<c:forEach var="co" items="${conditions[DISTRICT]}" varStatus="status">
									<a href="${co.linkUrl}" class="dd ${co.select }" <c:if test="${fold != 'di' }">style="display:none;"</c:if>>${co.condName}</a>
								</c:forEach>
							</div>
						</li>
						</c:if>
						
						<c:if test="${not empty conditions[TYPE] }">
						<li ftype="ty" <c:if test="${fold == 'ty' }">class="unfold"</c:if>>
							<div class="">
								<div class="dt">类型</div>
								<c:forEach var="co" items="${conditions[TYPE]}" varStatus="status">
									<a href="${co.linkUrl}" class="dd ${co.select }" <c:if test="${fold != 'ty' }">style="display:none;"</c:if>>${co.condName}</a>
								</c:forEach>
							</div>
						</li>
						</c:if>
						
						<c:if test="${not empty conditions[PRICE] }">
						<li ftype="pr " <c:if test="${fold == 'pr' }">class="unfold"</c:if>>
							<div class="">
								<div class="dt">价格</div>
								<c:forEach var="co" items="${conditions[PRICE]}" varStatus="status">
									<a href="${co.linkUrl}" class="dd ${co.select }" <c:if test="${fold != 'pr' }">style="display:none;"</c:if>>${co.condName}</a>
								</c:forEach>									
							</div>
						</li>
						</c:if>
						
						<c:if test="${not empty conditions[HOT] }">
						<li ftype="ho" <c:if test="${fold == 'ho' }">class="unfold"</c:if>>
							<div class="">
								<div class="dt">热点板块</div>
								<c:forEach var="co" items="${conditions[HOT]}" varStatus="status">
									<a href="${co.linkUrl}" class="dd ${co.select }" <c:if test="${fold != 'ho' }">style="display:none;"</c:if>>${co.condName}</a>
								</c:forEach>
							</div>
						</li>
						</c:if>
						
						<c:if test="${not empty conditions[SUBWAY] }">
						<li ftype="su" <c:if test="${fold == 'su' }">class="unfold"</c:if>>
							<div class="">
								<div class="dt">轨道交通</div>
								<c:forEach var="co" items="${conditions[SUBWAY]}" varStatus="status">
									<a href="${co.linkUrl}" class="dd ${co.select }" <c:if test="${fold != 'su' }">style="display:none;"</c:if>>${co.condName}</a>
								</c:forEach>	
							</div>
						</li>
						</c:if>
						
						<c:if test="${not empty conditions[SPECIAL] }">
						<li ftype="sp" <c:if test="${fold == 'sp' }">class="unfold"</c:if>>
							<div class="">
								<div class="dt">特色楼盘</div>
								<c:forEach var="co" items="${conditions[SPECIAL]}" varStatus="status">
									<a href="${co.linkUrl}" class="dd ${co.select }" <c:if test="${fold != 'sp' }">style="display:none;"</c:if>>${co.condName}</a>
								</c:forEach>	
							</div>
						</li>
						</c:if>
					</ul>
				</div>
			</aside>

			<article class="scroll_mod filter_result article_result" id="filter_result">
				<div class="scroller">
					<div class="filter_top">
							<div class="item clearfix">
								<span>筛选条件：</span>
								<c:if test="${empty chosen}">
								<a href="javascript:;" class="all">全部楼盘</a>	
								</c:if>
								<c:if test="${not empty chosen}">	
										<a href="/${_city.cityPinyinAbbr }/loupan/" class="empty">清空</a>
										<c:forEach var="ch" items="${chosen}" varStatus="status">
										<a href="${ch.linkUrl }">${ch.condName}</a>
										</c:forEach>
								</c:if>
							</div>
					</div>
					<article>
					<c:if test="${total > 0 }">
						<div class="filter_search_results">在<span class="red">${_city.cityName }</span>共找到${total}个符合条件的楼盘
						
						<c:if test="${not empty fn:trim(con) }">
						<a class="map_mode" href="${ctx }/${_city.cityPinyinAbbr}/loupan/lpmap/?con=${con}&pageNo=${pageNo}">地图模式</a>
						</c:if>
						<c:if test="${ empty fn:trim(con) }">
						<a class="map_mode" href="${ctx }/${_city.cityPinyinAbbr}/loupan/lpmap/?pageNo=${pageNo}">地图模式</a>
						</c:if>
						
						</div>
						
							<ul class="article_list article_img article_line article_result">
								<c:forEach var="bl" items="${buildingList}" varStatus="status">
								<li>
								<a href="${bl.buildingUrl }" class="clearfix" role="touch_bg">
								<img alt="${bl.projName }" src="${bl.url }" class="img fl">
								<dl><dt>
								${bl.projName }
								<c:choose>
										   <c:when test="${bl.saleStatus == 0}">
												  <span class="status sale">在售</span>
										   </c:when>
										   <c:when test="${bl.saleStatus == 1}">
												  <span class="status wait">待售</span>
										   </c:when>
										   <c:when test="${bl.saleStatus == 2}">
												  <span class="status remain">尾盘</span>
										   </c:when>
										   <c:otherwise>
												 <span class="status over">售罄</span>
										   </c:otherwise>
								</c:choose>
								</dt>
								<dd class="clearfix">${bl.projAddress}
								<span class="price fr">${bl.avgPriceShow}</span></dd>
								<c:if test="${not empty fn:trim(bl.discount) }">
									<dd><span class="mark discount">${bl.discount}</span></dd>
								</c:if>
								</dl></a>
								</li>
								</c:forEach>
							</ul>
							<div id="more">
								<div class="line line_nop"></div>
								<a href="javascript:;" class="load_more">滑动加载更多</a>
								<a href="javascript:;" class="loading"><b class="rotating"></b>加载中...</a>
							</div>
						</c:if>
						<c:if test="${empty buildingList}">
							<div class="noresult">
								<div class="inner">
									<p>暂无符合条件的楼盘，请修改筛选条件</p>
								</div>
							</div>
						</c:if>
					</article>
				</div>
			</article>
		</section>

		<section class="crumb">
			<div class="inner">
				<a href="/${_city.cityPinyinAbbr }/">新房</a>&gt;
				<c:if test="${not empty showChosenConMap[DISTRICT] }"><span>${showChosenConMap[DISTRICT].condName }楼盘</span></c:if>
				<c:if test="${empty showChosenConMap[DISTRICT]}"><span>${_city.cityName }楼盘</span></c:if>
			</div>
		</section>
			
		<footer>
			<span class="copyright">&copy;2014搜狐焦点</span>
			<div class="switch_version">
				<a href="javascript:;" class="version">Pad版</a>
				<div class="version_list">
					<a href="javascript:;" role="touch_bg" class="phone">手机版</a>
					<a href="javascript:;" role="touch_bg" class="ipad current">Pad版</a>
					<a href="http://${_city.sourceDomainName }/search/index.html?cfrom=mobile" data-go="false" role="touch_bg" class="pc">电脑版</a>
				</div>
			</div>
			<a href="javascript:;" id="to_top">返回顶部</a>
		</footer>
	</section>
		
	<!-- js -->
	<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.filter.gh_1.0.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
	<%@ include file="/views/pv.jsp"%>
</body>
</html>