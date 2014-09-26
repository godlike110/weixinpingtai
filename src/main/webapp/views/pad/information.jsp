<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
		<meta content="telephone=no" name="format-detection" />
		<title>${news.title}_房产资讯-搜狐焦点${_city.cityName }房产</title>
		<meta name="Keywords" content="${news.tagsAndKeyword }" />
		<meta name="Description" content="${news.newsDesc }" />
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
			<header class="clearfix">
				<div class="logo_text">资讯正文</div>
				<a href="${ctx}/${_city.cityPinyinAbbr}/" class="main">首页</a>
			</header>
			
			<section class="content clearfix">
				<article class="news_detail">
					<h1>${news.title}</h1>
					<c:if test="${not empty news.sourceName || not empty news.author}">
						<p class="resource clearfix">
							<c:if test="${not empty news.sourceName}"><span>消息来源: ${news.sourceName}</span></c:if>	
							<c:if test="${not empty news.author }"><span class="fr">作者: ${news.author}</span></c:if>
						</p>
					</c:if>
					<c:if test="${not empty news.time}">
						<p class="resource clearfix">
							<span>${news.time}</span>
						</p>
					</c:if>
					<c:if test="${not empty news.sourceName || not empty news.author || not empty news.time}">
						<div class="line"></div>
					</c:if>
					<div class="main_contents">
						<c:if test="${ not empty news.pageContent.newsSummary }"><p>摘要:${news.pageContent.newsSummary}</p></c:if>	
						
						${frontContent}
						<div class="back_content" style="display:none">
							${backContent}
						</div>
						<c:if test="${ not empty backContent}">
						<a href="javascript:;" class="more fold"><b></b>展开剩余</a>
						</c:if>
					</div>
				</article>

				<article class="related_loupan">
					<c:if test="${not empty infos}">
					<ul class="article_list article_line">
						${infos}
					</ul>
					</c:if>
				</article>
			</section>

			<section class="crumb">
				<div class="inner">
					<a href="/${_city.cityPinyinAbbr }/">新房</a>&gt;<span>资讯正文</span>
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
				<a href="javascript:;" id="to_top">返回顶部</a>
			</footer>
		</section>
		
		<!-- js -->
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
		<script>
			(function() {
				$('.fold').on('click', function() {
					$(this).hide();
					$(".back_content").show();
				});
			})();
		</script>
		<%@ include file="/views/pv.jsp"%>
	</body>
</html>