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
		<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
		<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
		<script>
			var city = ${city};
		</script>
	</head>
	
	<body class="bg_all layout_fixed">
		<section class="container">
				<!-- 新版样式导航 -->
		<jsp:include page="/views/padHeader.jsp">
			<jsp:param value="详情" name="page_title" />
		</jsp:include>
			
			<section class="content clearfix">
				<article class="news_detail">
					<h1>${housingGuide.title }</h1>
					<p>
						<span class="resource">${housingGuide.catagoryName }&nbsp;&nbsp;&nbsp;&nbsp;
						<fmt:formatDate value="${housingGuide.createTime }" pattern="yyyy年M月d日"/>
						</span>
					</p> 
					<div class="main_contents">
						<img src="${housingGuide.picUrl}" alt="${housingGuide.title }" />
						<p>${housingGuide.content }</p>
					</div>
				</article>
			</section>

			<section class="crumb">
				<div class="inner">
					<a href="/${_city.cityPinyinAbbr }/">新房</a>&gt;<a href="${ctx }/${_city.cityPinyinAbbr }/baodian/">购房宝典</a>&gt;<span>购房宝典正文</span>
				</div>
			</section>

			<footer class="position_fixed">
				<span class="copyright">&copy;2014搜狐焦点</span>
				<div class="switch_version">
					<a href="javascript:;" class="version">Pad版</a>
					<div class="version_list">
						<a role="touch_bg" href="#" class="phone">手机版</a>
						<a role="touch_bg" href="#" class="ipad current">Pad版</a>
						<a role="touch_bg" href="#" class="pc">电脑版</a>
					</div>
				</div>
			</footer>
		</section>
		<a href="javascript:;" id="to_top">返回顶部</a>
		
		<!-- js -->
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
		<%@ include file="/views/pv.jsp"%>
	</body>
</html>    
    
