<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>【新闻|房产新闻】-搜狐焦点${_city.cityName }房产</title>
<meta name="Keywords" content="${_city.cityName }房产,新闻 ,房产新闻">
<meta name="Description" content="搜狐焦点${_city.cityName }房产新闻频道每天提供最全面最及时的北京房产新闻、${_city.cityName }房产楼市资讯，为您解读${_city.cityName }房产最新政策，预测${_city.cityName }房价走势，快速了解房产的最新进展情况。" >
<%@ include file="/views/icon.jsp" %>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<script>
var city = ${city};
</script>
</head>
<body class="wap_hotquestion_all wap_news_all">
	<div class="wap_container scroller" id="wrapper">
		<div id="scroller">
			<!-- 新闻列表页—顶部导航 -->
			<header class="top_nav">
			
					<a href="${ctx }/${_city.cityPinyinAbbr }/" class="go_back" data-title="返回上一级">首页</a>
				
				<span class="search_title"><h1>房产新闻</h1></span>
				<span class="menu_list" role="main_menu" role-addclass="its_open" data-title="展开列表"></span>
				<ul class="nav_box_ul1 main_menu_ul1">
					<li><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/" role="text" url="" >楼盘</a></li>
					<c:if test="${total > 0 }">
					<li><a href="${ctx}/${_city.cityPinyinAbbr}/daogou/" role="text" url="">导购</a></li>
					</c:if>
					<li><a href="${ctx }/${_city.cityPinyinAbbr }/zixun/" role="text" url="" role-current="current" class="current">新闻</a></li>
					<li><a href="${ctx }/${_city.cityPinyinAbbr }/baodian/" role="text" url="" >宝典</a></li>
					<li><a href="${ctx }/${_city.cityPinyinAbbr }/pafangtuan/" role="text" url="" >看房团</a></li>
				</ul>	
			</header>
			
			<!-- 新闻列表页主要内容板块 // -->
			<section class="hotquestion_list_main">
				<div class="hotquestion_list_container">	
					<section class="dynamic_info_container" data-title="新闻列表">
						<div class="dynamic_info_ask">
							<ul class="search_list_container">
								<c:forEach var="item" items="${newslist}" varStatus="status">
								<li>
									<a href="${ctx }/${_city.cityPinyinAbbr }/xinwen/${item.newsId}/" role="text" url="${ctx }/${_city.cityPinyinAbbr }/xinwen/${item.newsId}/" class="clearfix">
										<div class="fl its_img"><img alt="${item.title}" src="${item.listPic}"></div>
										<div class="its_content">
											<h2 class="news_biaoti1">
												${item.title}
												<span class="news_biaoti1_date">${item.time}</span>
											</h2>
											<p class="news_nr1">${item.description}</p>
										</div>
									</a>
								</li>
								</c:forEach>	
							</ul>
						</div>
						<!-- pull up -->
						<c:if test="${hasNext}">
						<div class="pull font_gray">
							<div class="icon fl"></div>
							<p>滑动加载更多</p>
						</div>
						</c:if>
					</section>
				</div>
			</section>
			
			<!-- 面包屑 -->
			<div class="link_boxs"><a href="/${_city.cityPinyinAbbr }/">新房</a><span class="h_space1">></span>房产新闻</div>
			
			<!-- 底部导航 // -->
			<footer class="foot_nav">
				<div class="foot_nav_copyright">©2014 搜狐焦点</div>
				<div class="foot_nav_box2">
					<span class="wap_version" role="wap_version_menu">手机版</span>
					<ul class="nav_box_ul1 wap_version_menu_ul1">
						<c:if test="${mobile=='false'}">
							<li><a href="#" role="wap_version_text" url="" data-nr="PAD">Pad版</a></li>
						</c:if>
						<li><a href="#" role="wap_version_text" url="" data-nr="PHONE" class="current">手机版</a></li>
						<li><a href="#" role="wap_version_text" url="" data-nr="PC">电脑版</a></li>
					</ul>
				</div>
			</footer>
			<!-- // 底部导航 -->
		</div>
	</div>
		
	<div class="back2top" data-title="返回顶部"></div>
	
	<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_news_list_v1.0.js"></script>
	<%@ include file="/views/pv.jsp"%>
</body>
</html>