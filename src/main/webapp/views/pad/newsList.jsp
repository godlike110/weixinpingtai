<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
		<meta content="telephone=no" name="format-detection" />
		<title>【新闻|房产新闻】-搜狐焦点${_city.cityName }房产</title>
		<meta name="Keywords" content="${_city.cityName }房产,新闻 ,房产新闻">
		<meta name="Description" content="搜狐焦点${_city.cityName }房产新闻频道每天提供最全面最及时的北京房产新闻、${_city.cityName }房产楼市资讯，为您解读${_city.cityName }房产最新政策，预测${_city.cityName }房价走势，快速了解房产的最新进展情况。" >
		<%@ include file="/views/icon.jsp" %>
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
		<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
		<script>
			var city = ${city};
		</script>
	</head>
	
	<body>
		<section class="container" id="news_wrapper">
			<div class="scroller">
				<header class="clearfix">
					
						<a href="${ctx }/${_city.cityPinyinAbbr }/" class="back fl" >首页</a>
					
					<h1>房产新闻</h1>
					<div class="menu menu_right fr" id="menu">
						<a href="javascript:;" class="choose">选择</a>
						<div class="menu_list">
							<a href="${ctx }/${_city.cityPinyinAbbr }/loupan/" role="touch_bg">楼盘</a>
							<c:if test="${total > 0 }">
							<a href="${ctx}/${_city.cityPinyinAbbr}/daogou/" role="touch_bg">导购</a>
							</c:if>
							<a href="${ctx }/${_city.cityPinyinAbbr }/zixun/" role="touch_bg" class="current">新闻</a>
							<a href="${ctx }/${_city.cityPinyinAbbr }/baodian/" role="touch_bg" >宝典</a>
							<a href="${ctx }/${_city.cityPinyinAbbr }/pafangtuan/" role="touch_bg">看房团</a>
						</div>
					</div>
				</header>
				
				<section class="content clearfix">
					<article class="no_padd">
						<ul class="article_list article_img article_line p20">
							<c:forEach var="item" items="${newslist}" varStatus="status">
							<li role="touch_bg">
								<a href="${ctx }/${_city.cityPinyinAbbr }/xinwen/${item.newsId}/" class="clearfix">
									<img src="${item.listPic}" alt="${item.title}" class="img fl" />
									<dl>
										<dt>${item.title}</dt>
										<dd>${item.description}</dd>
										<dd class="seprate">${item.time}</dd>
									</dl>
								</a>
							</li>
							</c:forEach>
						</ul>
						<c:if test="${hasNext}">
						<div id="has_more">
							<div class="line line_nop"></div>
							<a href="javascript:;" class="load_more">滑动加载更多</a>
							<a href="javascript:;" class="loading"><b class="rotating"></b>加载中...</a>
						</div>
						</c:if>
					</article>
				</section>

				<section class="crumb">
					<div class="inner">
						<a href="/${_city.cityPinyinAbbr }/">新房</a>&gt;<span>房产新闻</span>
					</div>
				</section>
				
				<footer>
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
			</div>
		</section>
		
		<a href="javascript:;" id="to_top">返回顶部</a>
		
		<!-- js -->
		<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.newslist.gh_1.0.js"></script>
		<%@ include file="/views/pv.jsp"%>
	</body>
</html>