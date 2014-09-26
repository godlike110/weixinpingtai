<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>    
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
		<meta content="telephone=no" name="format-detection" />
		<title>${housingGuide.title }_购房宝典-搜狐焦点${_city.cityName }房产</title>
		<meta name="keywords" content="购房宝典，购房指南"/>
		<meta name="description" content="${housingGuide.summary}"/>
		<%@ include file="/views/icon.jsp" %>
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
		<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
		<script type="text/javascript">
		    var city = ${city};
		</script>
	</head>
	
	<body class="bg_all">
		<section class="container">
			<section class="content clearfix">
				<article class="news_detail">
					<h1>${housingGuide.title }</h1>
					<p>
						<span class="resource">${housingGuide.catagoryName }&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${housingGuide.createTime }" pattern="yyyy年M月d日"/></span>
					</p> 
					<div class="main_contents">
						<img src="${housingGuide.picUrl}" alt="${housingGuide.title }" />
						<p>${housingGuide.content }</p>
					</div>
				</article>
			</section>
			
		</section>
			<div id="ad" style="position:fixed;left:0;bottom:0;right:0">
				<div class="advertisement_box">
					<a href="${ctx }/static/appwap.html" id="adImg1"><img id="adImg" class="img" src="http://a1.itc.cn/sceapp/focus_static/wap/images/advertisement_img1.png" width="320" height="65"/></a>
					<img class="closeAd" src="http://a1.itc.cn/sceapp/focus_static/wap/images/advertisement_close1.png" width="20" height="20">
				</div>
			</div>
		<!-- js -->
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/ipad.ad.js"></script>
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
		<%@ include file="/views/pv.jsp"%>
	</body>
</html>    
    
