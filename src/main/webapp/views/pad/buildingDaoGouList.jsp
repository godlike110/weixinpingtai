<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta content="telephone=no" name="format-detection">
<title>【楼盘导购|选房技巧|楼盘选购】-搜狐焦点${_city.cityName}房产</title>
<meta name="Keywords" content="楼盘导购,选房技巧，楼盘选购">
<meta name="Description"
	content="搜狐焦点${_city.cityName}房产楼盘导购频道为您提供真实、海量的楼盘信息，根据您的需求量身定制楼盘选购文章，告诉您选房技巧，包括如何选好房以及好房子的标准是什么等信息，让您在最短的时间内选出最适合您的房子。 ">
<%@ include file="/views/icon.jsp"%>
<link rel="stylesheet"
	href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
<link rel="stylesheet"
	href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
<link
	href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/news_whj.css"
	rel="stylesheet" />
<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
<script
	src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script>
	//var city = { "ajaxUrl": "http://www.test.m.focus.cn/bj/daogou/listajax/", "cityCoding": "010", "cityId": 1, "cityName": "北京", "cityPinyin": "beijing", "cityPinyinAbbr": "bj", "domainName": "house.focus.cn/?cfrom=mobile", "priority": 1, "singleUrl": "/bj/daogou/", "type": "1" };
	var city = ${city};
	var hasNext = ${hasNext};
</script>
<script id="articleTemplate" type="text/html">
        <li role="touch_bg" class="article article_whj">
			<a href="json.Url" class="article_link clearfix">
				<div class="fangchan_news_list_img">
                    <img src="json.HouseImage" alt="json.Title">
                    <div class="article_description">
                        <div class="portrait"><img class="portrait_img" alt="" src="json.Portrait" /></div>
                        <span class="article_description_text position">json.Position</span>
                        <span class="article_description_text">json.Name</span>
                        <div class="fr">
                            <img class="verticle_middle" style="width: 15px; height: 17.5px;" src="http://192.168.242.44/sceapp/focus_static/wap/pad/img/document.png">
                            <span class="verticle_middle">json.CommentNumber</span>
                            <img class="verticle_middle" style="width: 22px; height: 13px;" src="http://192.168.242.44/sceapp/focus_static/wap/pad/img/eye.png">
                            <span class="verticle_middle">json.FollowNumber</span>
                        </div>
                    </div>
				</div>
				<dl>
					<dt class="its_tit">json.Title</dt>
					<dd class="its_writer seprate"><span class="fr">json.Time</span>json.KeyWords</dd>
				</dl>
			</a>
		</li>
    </script>
</head>
<body class="list_news_body">

	<!-- 新版样式导航 -->
	<jsp:include page="/views/padHeader.jsp">
		<jsp:param value="楼盘导购" name="page_title" />
	</jsp:include>

	<div class="placeholder_top_bar"></div>
	<ul class="article_list article_list_whj clearfix">

		<c:forEach items="${dgList }" var="dg" varStatus="index">
			<li role="touch_bg" class="article article_whj"><a
				href="${ctx }/${_city.cityPinyinAbbr}/daogou/${dg.id }/"
				class="article_link clearfix">
					<div class="fangchan_news_list_img">
						<img src="${dg.pic }" alt="${dg.title }">
						<div class="article_description">
							<div class="portrait">
								<img class="portrait_img" alt="头像" src="${dg.editorPic }" />
							</div>
							<span class="article_description_text position">${dg.editorTitle
								}</span> <span class="article_description_text">${dg.editorName
								}</span>
							<div class="fr">
								<img class="verticle_middle"
									style="width: 15px; height: 17.5px;"
									src="http://192.168.242.44/sceapp/focus_static/wap/pad/img/document.png">
								<span class="verticle_middle">${dg.totalPublish }</span> <img
									class="verticle_middle" style="width: 22px; height: 13px;"
									src="http://192.168.242.44/sceapp/focus_static/wap/pad/img/eye.png">
								<span class="verticle_middle">${dg.totalViews }</span>
							</div>
						</div>
					</div>
					<dl>
						<dt class="its_tit">${dg.title }</dt>
						<dd class="its_writer seprate">
							<span class="fr">${dg.onlineTime }</span>${dg.tab }
						</dd>
					</dl>
			</a></li>
		</c:forEach>
	</ul>
	<hr class="placeholder_right_bar" />
	<section class="content bordertop0 clearfix">
		<c:if test="${hasNext == 'true' }">
			<div id="has_more" class="has_more1">
				<a href="javascript:;" class="load_more" style="display: block;">滑动加载更多</a>
				<a href="javascript:;" class="loading" style="display: none;"><b
					class="rotating"></b>加载中...</a>
			</div>
		</c:if>
		<!-- 面包屑 -->
		<section class="crumb">
			<div class="inner">
				<a href="${ctx }/${_city.cityPinyinAbbr}/">新房</a>&gt;<a
					href="javascript:;">楼盘导购</a>
			</div>
		</section>
		<footer class="clearfix">
			<span class="copyright">&copy;2014搜狐焦点</span>
			<div class="switch_version">
				<a href="javascript:;" class="version">Pad版</a>
				<div class="version_list">
					<a role="touch_bg" href="javascript:;" class="phone">手机版</a> 
					<a role="touch_bg" href="javascript:;" class="ipad current">Pad版</a>
					<a role="touch_bg" href="http://${_city.sourceDomainName }/daogou?cfrom=mobile" class="pc">电脑版</a>
				</div>
			</div>
			<a href="javascript:;" id="to_top">返回顶部</a>
		</footer>
		<!-- js -->
		<script
			src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
		<script
			src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.newslist.whj.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
<%@ include file="/views/pv.jsp"%>
</body>
</html>
