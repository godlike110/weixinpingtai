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
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<script type="text/javascript">
var city = ${city};
</script>
</head>
<body class="wap_hotquestion_all wap_baodian_share_all">
	<div class="wap_container">
		<!-- 宝典分享页 -->
		<section class="hotquestion_list_main">
			<div class="hotquestion_list_container">	
				<section class="dynamic_info_container" data-title="宝典分享">
					<div class="dynamic_info_ask">
						<h1 class="its_biaoti">${housingGuide.title }</h1>
						<p class="its_writer_time">${housingGuide.catagoryName }<span class="h_space2"><fmt:formatDate value="${housingGuide.createTime }" pattern="yyyy年M月d日"/></span></p>
						<div class="dynamic_info_nr">
							<div class="img_box1"><img src="${housingGuide.picUrl}" alt="${housingGuide.title }" /></div>
							<p>${housingGuide.content }</p>
						</div>
					</div>
				</section>
			</div>
		</section>
		<!-- 广告banner -->
		<div id="ad">
			<div class="advertisement_box">
				<a href="${ctx }/static/appwap.html" id="adImg1"><img id="adImg" class="img"
					src="http://192.168.242.44/sceapp/focus_static/wap/phone/img/advertisement_img1.png"
					width="320" height="65" alt="搜狐焦点图片"/></a> <img class="closeAd"
					src="http://192.168.242.44/sceapp/focus_static/wap/phone/img/advertisement_close1.png"
					width="24" height="24" alt="搜狐焦点图片"/>
			</div>
		</div>
	</div>
	<%@ include file="/views/pv.jsp"%>
</body>
</html>