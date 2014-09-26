<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
		<meta content="telephone=no" name="format-detection" />
		<title>${news.title }_房产新闻-搜狐焦点${_city.cityName }房产</title>
		<meta name="Keywords" content="房产新闻，房产资讯" />
		<meta name="Description" content="${news.title}" />
		<%@ include file="/views/icon.jsp" %>
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
		<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
		<script>
			var city = ${city};
		</script>
	</head>
	
	<body class="bg_all layout_fixed">
		<section class="container">
			<header>
				<c:choose>
						<c:when test="${returnFlag == 1}">
								<a href="${ctx }/${_city.cityPinyinAbbr }/" class="back fl" >首页</a>
						</c:when>
						<c:when test="${returnFlag == 0}">
								<a href="javascript:history.back();" class="back fl" >返回</a>
						</c:when>
				</c:choose>	
				<div class="logo_text">新闻正文</div>
				<a href="${ctx}/${_city.cityPinyinAbbr}/" class="main">首页</a>
			</header>
			
			<section class="content clearfix">
				<article class="news_detail">
					<h1>${news.title }</h1>
					<p class="resource clearfix">

				    <c:choose>
				    <c:when test="${not empty news.media}">
						<span>消息来源：${news.media}</span>
				    </c:when>
				    <c:when test="${empty news.media}">
						<span>消息来源：搜狐焦点房产</span>
				    </c:when>
				    </c:choose>
						<span class="fr">${news.time}</span>
					</p> 
					<div class="main_contents no_text_indent">
						${news.content }
					</div>
				</article>
			</section>

			<section class="crumb">
				<div class="inner">
					<a href="${ctx }/${_city.cityPinyinAbbr }/">新房</a>&gt;<a href="${ctx }/${_city.cityPinyinAbbr }/zixun/">房产新闻</a>&gt;<span>房产新闻正文</span>
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
		<%@ include file="/views/pv.jsp"%>
	</body>
</html>    