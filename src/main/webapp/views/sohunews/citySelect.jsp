<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta content="telephone=no" name="format-detection" />
    <%@ include file="/views/icon.jsp"%>
    <title>选择城市</title>
    <meta name="Keywords" content="${_city.cityName }楼盘,+楼盘信息" />
    <meta name="Description" content="搜狐焦点${_city.cityName }房产网为广大网友提供了最新的${_city.cityName }楼盘信息，最准确的${_city.cityName }房价情况和最及时的${_city.cityName }新房资讯等，是买房、购房首选网站。 "/>
    <%@ include file="/views/icon.jsp"%>
    <link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
    <link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css" />
    <link href="http://192.168.242.44/sceapp/focus_static/wap/sohunews/css/selectCity.css" rel="stylesheet" />
</head>
<body>
    <div class="header clearfix">
        <h1 class="title">${_city.cityName }</h1>
        <a href="javascript:history.back();" data-touched="true" class="fr close_link">
            <img src="http://192.168.242.44/sceapp/focus_static/wap/phone/img/close.png" class="close_icon" alt="close" />
        </a>
        <span class="arrow"></span>
    </div>
    <section class="section_content">
        <ul class="all_city_lists">
        
        	<c:forEach items="${cityListMap }" var="map" varStatus="status">
        	<li>
                <h3 class="city_letter_tit" id="letter_${map.key }"><span>${map.key }</span></h3>
                <ul class="city_list selected_city_list">
                	<c:forEach items="${map.value }" var="cityList">
                	
                	<!-- 同一个城市 -->
                	<c:if test="${_city.cityId == cityList.cityId}">
                	<li class="city_item selected_city_item"><a href="javascript:history.back();" data-touched="true" data-value="${cityList.cityPinyinAbbr }">${cityList.cityName }</a></li>
                	</c:if>
                	<!-- 不同一个城市 -->
                	<c:if test="${_city.cityId != cityList.cityId}">
                	<li class="city_item" data-touched="true"><a href="${ctx }/sohunews/${from }/?cityName=${cityList.cityPinyinAbbr}"  data-value="${cityList.cityPinyinAbbr }">${cityList.cityName }</a></li>
                	</c:if>
                	</c:forEach>
                </ul>
            </li>
        	</c:forEach>
        </ul>
        <!--Navigation-->
        <ul class="city_nav">
        	<c:forEach items="${cityListMap }" var="map" varStatus="status">	
        	<li><a class="nav_link" href="javascript:;" data-href="#letter_${map.key }">${map.key }</a></li>
        	</c:forEach>				
		</ul>
    </section>
    <script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/sohunews/snippets/common.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/sohunews/snippets/selectCity.js"></script>
    <%@ include file="/views/pv.jsp"%>
</body>
</html>
