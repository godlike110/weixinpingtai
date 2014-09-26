<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${_city.cityName }看房团-${_city.cityName }搜狐焦点网</title>
<meta name="viewport"
	content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0" />
<meta name="Keywords" content="${_city.cityName }看房团" />
<meta name="Description"
	content="${_city.cityName }搜狐焦点看房团为大家设计最佳看房路线，带大家去看心仪的楼盘。同时，${_city.cityName }看房团还将不定期的邀请房地产专业人士陪同，为大家解读户型，指点装修问题。" />
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pft/css/plubic.css" />
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pft/css/wap_nodate_phone_portrait.css" />
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pft/css/component_phone.css" />
<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<c:choose>
	<c:when test="${sohunews}"><link href="http://192.168.242.44/sceapp/focus_static/wap/sohunews/css/pft_header.css" rel="stylesheet" /></c:when>
	<c:otherwise>
		<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
		<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
	</c:otherwise>
</c:choose>
<script type="text/javascript">var city = ${city};</script>

</head>

<body>
	<!-- 新版样式导航 -->
<c:choose>
	<c:when test="${sohunews}">
		<div class="header clearfix">
	        <h1 class="title">看房团</h1>
	        <a href="${ctx}/sohunews/citySelect/?cityName=${_city.cityPinyinAbbr}&from=kanfangtuan" data-touched="true" class="fr" id="selectCity">
	            <span class="city">${_city.cityName}</span>
	            <img src="http://192.168.242.44/sceapp/focus_static/wap/phone/img/location.png" class="location_icon" alt="loaction">
	        </a>
	        <span class="arrow"></span>
		</div>
	</c:when>
	<c:otherwise>
		<jsp:include page="/views/phoneHeader.jsp">
			<jsp:param value="看房团" name="page_title" />
		</jsp:include>
	</c:otherwise>
</c:choose> 
	<div class="box">
		<div class="box1">
			<img
				src="http://192.168.242.44/sceapp/focus_static/wap/pft/img/loading2.png"
				width="100%" />
		</div>
		<div class="box2">
			<h1>全程免费看房团流程</h1>
			<ul>
				<li><img
					src="http://192.168.242.44/sceapp/focus_static/wap/pft/img/loading_icon1.png"
					width="56" height="56" />
				<p>1.我要报名</p></li>
				<li class="box2_j"><img
					src="http://192.168.242.44/sceapp/focus_static/wap/pft/img/loading_icon_jt.png"
					width="8" height="17" /></li>
				<li><img
					src="http://192.168.242.44/sceapp/focus_static/wap/pft/img/loading_icon_tel.png"
					width="56" height="56" />
				<p>2.电话沟通</p></li>
				<li class="box2_j"><img
					src="http://192.168.242.44/sceapp/focus_static/wap/pft/img/loading_icon_jt.png"
					width="8" height="17" /></li>
				<li><img
					src="http://192.168.242.44/sceapp/focus_static/wap/pft/img/loading_icon_message.png"
					width="56" height="56" />
				<p>3.短信通知</p></li>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
<c:if test="${!sohunews}">
	<div class="position">
		<p>
			<a color="#aaa" href="${ctx }/${_city.cityPinyinAbbr}"><font
				color="#aaa">新房</font></a> > <a color="#aaa" href="javascript:;"><font
				color="#aaa">看房团</font></a>
		</p>
	</div>
	<footer class="clearfix"> <span class="copyright">©2014搜狐焦点</span>
	<div class="switch_version">
		<a href="javascript:;" class="version">手机版</a>
		<div class="version_list">
			<a role="touch_bg" href="javascript:;" class="phone current">手机版</a>
			<c:choose>
			<c:when test="${_city.cityPinyinAbbr ==  'suzhou' }">
			<a role="touch_bg" data-go="false" href="http://kanfangtuan.focus.cn/${_city.cityPinyinAbbr }?cfrom=mobile" class="pc">电脑版</a>
			</c:when>
			<c:otherwise>
			<a role="touch_bg" data-go="false" href="http://kanfangtuan.focus.cn/${_city.cityPinyin }?cfrom=mobile" class="pc">电脑版</a>
			</c:otherwise>
			</c:choose>
		</div>
	</div>
	<a href="javascript:;" id="to_top" style="display: block;">返回顶部</a> 
	</footer>
</c:if> 
	<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/pft/js/common.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
	<%@ include file="/views/pv.jsp"%>
	<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>
