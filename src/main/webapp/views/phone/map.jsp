<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>${detail.projName }详细信息-${_city.cityName }${detail.projName }地图信息-搜狐焦点</title>
<meta name="Keywords" content="${detail.projName }详细信息-${_city.cityName }${detail.projName }地图信息-搜狐焦点">
<meta name="Description" content="搜狐焦点${_city.cityName }房产网为广大网友提供了最新的${_city.cityName }楼盘信息，最准确的${_city.cityName }房价情况和最及时的${_city.cityName }新房资讯等，是买房、购房首选网站。 ">
<%@ include file="/views/icon.jsp" %>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_map_v1.0.css">
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<script type="text/javascript"  src="http://webapi.amap.com/maps?v=1.2&key=9e4b883b2a6d8482638c56b6f60078b7"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
<script type="text/javascript">
var city = ${cityStr};
var groupId = ${groupId}; 
var phone400 = ${phone400};
</script>
</head>
<body class="wap_map_all">
	<div class="wap_container">
		<!-- 地图页—顶部导航 -->
		<header class="top_nav map_top_nav">
		<c:if test="${returnFlag=='0' }">
		<a href="javascript:history.back();" class="go_back" data-title="返回上一级"></a>
		</c:if>
		<c:if test="${returnFlag=='1' }">
			<a href="${ctx}/${_city.cityPinyinAbbr}/" class="go_back" data-title="返回上一级">首页</a>
		</c:if>
			<span class="search_title">地图及周边</span>			
			<a class="map_around1" href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/zhoubian/"></a>
		</header>
		
		<!-- 地图页主要内容板块 // -->
		<section class="map_main">
			<div id="mockContainer" class="map_container" lng="${info.longitude }" lat="${info.latitude }" projname="${info.projName }">
				<!-- div class="wap_map_scale">
					<div class="map_scale_big"></div>
					<div class="map_scale_small"></div>
				</div-->
			</div>
		</section>
		
		<!-- 面包屑 -->
		<div class="link_boxs map_link_boxs"><a href="${ctx}/${_city.cityPinyinAbbr}/">新房</a>&gt;<a href="${ctx}/${_city.cityPinyinAbbr}/loupan/"> ${_city.cityName }楼盘</a><span class="h_space1">&gt;</span><a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/"> ${buildName}</a><span class="h_space1">&gt;</span> 地图</div>
		
		 <c:if test="${info.phone400!=0 }">
		<!-- 400电话 -->
		<div class="hotline_phone">
			<a href="tel:4008882200,${info.phone400 }" class="hotline_phone_btn" phone="${info.phone400}"><span class="pnone_icon"></span>免费咨询 400-888-2200 转 ${info.phone400 }</a>
		</div>
		</c:if>
		<c:if test="${info.phone400==0 }">
		<div class="hotline_phone">
			<a href="tel:4006782020" class="hotline_phone_btn" phone="${info.phone400}"><span class="pnone_icon"></span>免费咨询 400-678-2020</a>
		</div>
		</c:if>
		
	</div>
	
	<!-- 电话弹层 // -->
	<div class="over"></div>
	<div class="alert_box" id="check_net">
     	<p class="p1">请在接通后拨分机号</p>
     	<p class="p1">${info.phone400}</p>
     	<div class="itos_btn1" id="check_net_confirm"><a href="javascript:;">好的</a></div>
		<div class="its_close_btn"></div>
    </div>
	<!-- // 电话弹层 -->
	<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_map_v1.0.js"></script>
	<%@ include file="/views/pv.jsp"%>
	<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>
