<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<%@page import="cn.focus.dc.focuswap.service.SearchService.SearchType"%>
<c:set var="DISTRICT" value="<%= SearchType.DISTRICT%>"></c:set>
<c:set var="HOT" value="<%= SearchType.HOT%>"></c:set>
<c:set var="PRICE" value="<%= SearchType.PRICE%>"></c:set>
<c:set var="SUBWAY" value="<%= SearchType.SUBWAY%>"></c:set>
<c:set var="SPECIAL" value="<%= SearchType.SPECIAL%>"></c:set>
<c:set var="TYPE" value="<%= SearchType.TYPE%>"></c:set>
<!doctype html>
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	<meta content="telephone=no" name="format-detection" />
    <!-- <title>北京新楼盘房价-北京搜狐焦点网</title>
    <meta name="Keywords" content="北京楼盘，新楼盘，房价" />
    <meta name="Description" content="北京搜狐焦点网楼盘搜索频道为您提供北京新楼盘房价，让您快速方便的找房，更多北京楼盘信息，尽在北京搜狐焦点网。"/> -->
    
    <c:choose>
    <c:when test="${fn:length(showChosenConMap) == 0 }">
    <title>${_city.cityName }楼盘地图_${_city.cityName }地图找房_${_city.cityName }房产地图-${_city.cityName }搜狐焦点网</title>
	<meta name="Keywords" content="${_city.cityName }楼盘地图，${_city.cityName }地图找房，${_city.cityName }房产地图" />
	<meta name="Description" content="${_city.cityName }搜狐焦点网地图找房为您提供按照价格区间、区县交通，查看${_city.cityName }楼盘地图分布、${_city.cityName }地图找房、${_city.cityName }房产地图等信息.${_city.cityName }新楼盘地图，尽在${_city.cityName }搜狐焦点网。"/>
    </c:when>
	<c:when test="${fn:length(showChosenConMap) ==1 && ( not empty showChosenConMap[DISTRICT] || not empty showChosenConMap[HOT] )}">
	<title>${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }楼盘地图_${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }地图找房_${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }房产地图-${_city.cityName }搜狐焦点网</title>
	<meta name="Keywords" content="${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }楼盘地图，${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }地图找房，${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }房产地图" />
	<meta name="Description" content="${_city.cityName }搜狐焦点网地图找房为您提供按照价格区间、区县交通，查看${_city.cityName }${showChosenConMap[DISTRICT].condName}${showChosenConMap[HOT].condName }楼盘地图分布、${showChosenConMap[DISTRICT].condName}${showChosenConMap[HOT].condName }地图找房、${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }房产地图等信息.${_city.cityName }${showChosenConMap[DISTRICT].condName}${showChosenConMap[HOT].condName }新楼盘地图，尽在${_city.cityName }搜狐焦点网。"/>
	</c:when>
	<c:otherwise>
	<title>${_city.cityName }${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }${showChosenConMap[SUBWAY].condName }${showChosenConMap[PRICE].condName }${showChosenConMap[SPECIAL].condName }${showChosenConMap[TYPE].condName }地图找房-${_city.cityName }搜狐焦点网</title>
	<meta name="Keywords" content="${_city.cityName }${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }${showChosenConMap[SUBWAY].condName }${showChosenConMap[PRICE].condName }${showChosenConMap[SPECIAL].condName }${showChosenConMap[TYPE].condName }地图找房" />
	<meta name="Description" content="${_city.cityName }搜狐焦点网地图找房为您提供${_city.cityName }${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }${showChosenConMap[SUBWAY].condName }${showChosenConMap[PRICE].condName }${showChosenConMap[SPECIAL].condName }${showChosenConMap[TYPE].condName }地图找房信息.让您快速方便的找房，更多${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }楼盘信息，尽在${_city.cityName }搜狐焦点网。"/>
	</c:otherwise>
	</c:choose>
    <%@ include file="/views/icon.jsp"%>
	<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
    <link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
    <link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/map_loupan_pad.css" rel="stylesheet" />
	<script>
	    var count = ${count};
	    var city = ${city};
	    var hasNext = ${hasNext};
	    var epjs = ${epjs};
	    var con = "${con}";
	</script>
</head>	
<body>
    
	<jsp:include page="/views/padHeader.jsp">
			<jsp:param value="楼盘筛选" name="page_title" />
	</jsp:include>
	<c:if test="${empty fn:trim(con) }">
	<div class="map_top_bar">共${count }个楼盘<a class="fr list_mode" href="${ctx }/${_city.cityPinyinAbbr }/loupan/?pageNo=${pageNo}">列表模式</a></div>
	</c:if>
    <c:if test="${ not empty fn:trim(con) }">
    <div class="map_top_bar">共${count }个楼盘<a class="fr list_mode" href="${ctx }/${_city.cityPinyinAbbr }/loupan/${con }/?pageNo=${pageNo}">列表模式</a></div>
    </c:if>
    <div id="iCenter"></div>

    <div class="bottom_bar">
    <button class="reset_zoom_btn"><img src="http://192.168.242.44/sceapp/focus_static/wap/loupan/images/default_zoom.png" alt="" /></button>
        <c:if test="${not empty fn:trim(epjs) }">
        <div class="map_pager">
       	 	<button class="pager_btn pager_left_btn" role="touch_bg"><img class="pager_left_arrow" src="http://192.168.242.44/sceapp/focus_static/wap/loupan/images/left_arrow_pad.png" alt="" /></button>
       	 	<c:set var="start" value="${(pageNo - 1 )*pageSize +1 }"></c:set>
       	 	<c:set var="end" value="${(pageNo -1 )*pageSize + _epjs }"></c:set>
       	 	<c:if test="${start == end }">
       	 	<span>第${start }个楼盘</span>
       	 	</c:if>
       	 	<c:if test="${start != end }">
       	 	<span>第${start }~${end }个楼盘</span>
       	 	</c:if>
       	 	<button class="pager_btn pager_right_btn" role="touch_bg" ><img class="pager_right_arrow" src="http://192.168.242.44/sceapp/focus_static/wap/loupan/images/arrow_pad.png" alt="" /></button>
        </div>
        </c:if>
    </div>
	<!-- js -->
	<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=9e4b883b2a6d8482638c56b6f60078b7"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/js/map_loupan.js"></script>
    <%@ include file="/views/pv.jsp"%>
</body>
</html>