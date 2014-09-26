<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<%@ include file="/views/icon.jsp"%>
<!-- 保存到主屏默认的标题 -->
<title>${chineseName}_房产新闻-搜狐焦点${_city.cityName }房产</title>
<meta name="Keywords" content="${chineseName }房产新闻,房地产资讯 ">
<meta name="Description"
	content="搜狐焦点${_city.cityName }房产新闻${chineseName }频道您提供最全面最及时的中国房地产资讯信息，内容涵盖房地产市场、房地产金融、土地政策、房地产营销、房展会、建筑设计等，是了解中国房产信息、房地产动态的权威网络媒体。">
<link rel="stylesheet" type="text/css"
	href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css"
	href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
	<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
<script>
	var city = ${city};
	var hasNext = "${hasNext}";
</script>
</head>
<body class="wap_hotquestion_all wap_news_all">
	<div class="wap_container">
		<!-- 新版样式导航 -->
		<jsp:include page="/views/phoneHeader.jsp">
			<jsp:param value="房产新闻" name="page_title" />
		</jsp:include>
		<ul class="son_nav">
			<li><a href="${ctx }/${_city.cityPinyinAbbr }/zixun/"
				<c:if test="${type=='TOP' }">class="current"</c:if>>要闻</a></li>
			<li><a href="${ctx }/${_city.cityPinyinAbbr }/zixun/shichang/"
				<c:if test="${type=='MARKET' }">class="current"</c:if>>市场</a></li>
			<li><a href="${ctx }/${_city.cityPinyinAbbr }/zixun/zhengce/"
				<c:if test="${type=='POLICY' }">class="current"</c:if>>政策</a></li>
			<li><a href="${ctx }/${_city.cityPinyinAbbr }/zixun/guandian/"
				<c:if test="${type=='POINT' }">class="current"</c:if>>观点</a></li>
			<li><a href="${ctx }/${_city.cityPinyinAbbr }/zixun/bendi/"
				<c:if test="${type=='LOCAL' }">class="current"</c:if>>${localName
					}</a></li>
			<li><a href="${ctx }/${_city.cityPinyinAbbr }/zixun/more/"
				<c:if test="${type=='MORE' }">class="current"</c:if>>更多</a></li>
		</ul>
		<!-- 新闻列表页主要内容板块 // -->
		<section class="hotquestion_list_main">
			<div class="hotquestion_list_container">
				<section class="dynamic_info_container" data-title="新闻列表">
					<ul class="house_news_list">
						<c:forEach var="item" items="${newslist}" varStatus="status">
							<li
								<c:if test="${ not empty fn:trim(item.esNews.imgLogo)}">class="its_hasimg"</c:if>>
								<a
								href="${ctx }/${_city.cityPinyinAbbr }/zixun/${item.esNews.newsId}/"
								role="text" url="" class="clearfix"> <c:if
										test="${ not empty fn:trim(item.esNews.imgLogo)}">
										<div class="fl its_img">
											<img alt="${item.esNews.title}" src="${item.esNews.imgLogo}">
										</div>
									</c:if>
									<div class="its_content">
										<div class="its_biaoti1">${item.esNews.title}</div>
										<p class="its_date1">
											<span class="its_biaoti1_date fr">${item.showTime}</span>${item.esNews.sourceName}
										</p>
									</div>
							</a>
							</li>
						</c:forEach>
					</ul>
					<!-- pull up -->
					<div class="pull font_gray">
						<div class="icon fl"></div>
						<p>滑动加载更多</p>
					</div>
				</section>
			</div>
		</section>

		<!-- 面包屑 -->
		<div class="link_boxs">
			<a href="/${_city.cityPinyinAbbr }/">新房</a><span class="h_space1">></span>新闻
		</div>

		<!-- 底部导航 // -->
		<footer class="foot_nav">
			<div class="foot_nav_copyright">©2014 搜狐焦点</div>
			<div class="foot_nav_box2">
				<span class="wap_version" role="wap_version_menu">手机版</span>
				<ul class="nav_box_ul1 wap_version_menu_ul1">
					<li><a href="##" role="wap_version_text" url="" data-nr="PHONE" class="current">手机版</a></li>
					<li><a href="http://${_city.sourceDomainName }/newscenter/xwsy.html?cfrom=mobile" data-go="false" role="wap_version_text" url="" data-nr="PC">电脑版</a></li>
				</ul>
			</div>
		</footer>
		<!-- // 底部导航 -->

		<div class="back2top" data-title="返回顶部"></div>
	</div>

	<script type="text/javascript"
		src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_housenews_v1.0.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
	<%@ include file="/views/pv.jsp"%>
	<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>