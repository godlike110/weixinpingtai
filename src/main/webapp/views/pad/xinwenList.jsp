<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
		<meta content="telephone=no" name="format-detection" />
		<%@ include file="/views/icon.jsp"%>
		<title>${chineseName}_房产新闻-搜狐焦点${_city.cityName }房产</title>
		<meta name="Keywords" content="${chineseName }房产新闻,房地产资讯 ">
		<meta name="Description" content="搜狐焦点${_city.cityName }房产新闻${chineseName }频道您提供最全面最及时的中国房地产资讯信息，内容涵盖房地产市场、房地产金融、土地政策、房地产营销、房展会、建筑设计等，是了解中国房产信息、房地产动态的权威网络媒体。">
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.0.css" />
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
		<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
		<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.0.min.js"></script>
		<script>
			var city = ${city};
			var hasNext = "${hasNext}";
		</script>
	</head>
	
	<body class="overflow_x_hidden">
		<section class="container">
		<!-- 新版样式导航 -->
		<jsp:include page="/views/padHeader.jsp">
			<jsp:param value="新闻" name="page_title" />
		</jsp:include>
			<ul class="son_nav">
				<li><a href="${ctx }/${_city.cityPinyinAbbr }/zixun/" <c:if test="${type=='TOP' }">class="current"</c:if>>要闻</a></li>
				<li><a href="${ctx }/${_city.cityPinyinAbbr }/zixun/shichang/" <c:if test="${type=='MARKET' }">class="current"</c:if> >市场</a></li>
				<li><a href="${ctx }/${_city.cityPinyinAbbr }/zixun/zhengce/" <c:if test="${type=='POLICY' }">class="current"</c:if>>政策</a></li>
				<li><a href="${ctx }/${_city.cityPinyinAbbr }/zixun/guandian/" <c:if test="${type=='POINT' }">class="current"</c:if>>观点</a></li>
				<li><a href="${ctx }/${_city.cityPinyinAbbr }/zixun/bendi/" <c:if test="${type=='LOCAL' }">class="current"</c:if>>${localName }</a></li>
				<li><a href="${ctx }/${_city.cityPinyinAbbr }/zixun/more/" <c:if test="${type=='MORE' }">class="current"</c:if>>更多</a></li>
			</ul>
			
			<section class="content bordertop0 clearfix">
				<article class="no_padd2">
					<ul class="article_list fangchan_news_list article_img article_line clearfix">
						<c:forEach var="item" items="${newslist}" varStatus="status">							
						<li role="touch_bg">
							<a href="${ctx }/${_city.cityPinyinAbbr }/zixun/${item.esNews.newsId}/" class="clearfix">
								<c:if test="${ not empty fn:trim(item.esNews.imgLogo)}">
								<div class="fangchan_news_list_img"><img alt="${item.esNews.title}" src="${item.esNews.imgLogo}"></div>
								</c:if>
								<dl>
									<dt class="its_tit">${item.esNews.title}</dt>
									<dd class="its_summary">${item.esNews.partContent}</dd>
									<dd class="its_writer seprate"><span class="fr">${item.showTime}</span>${item.esNews.sourceName}</dd>
								</dl>
							</a>
						</li>
						</c:forEach>
					</ul>
					<div class="line line_nop"></div>
					<div id="has_more" class="has_more1">
						<a href="javascript:;" class="load_more" style="display: block;">滑动加载更多</a>
						<a href="javascript:;" class="loading" style="display: none;"><b class="rotating"></b>加载中...</a>
					</div>
				</article>
			</section>

			<section class="crumb">
				<div class="inner">
					<a href="/${_city.cityPinyinAbbr }/">新房</a>&gt;新闻
				</div>
			</section>
			
			<footer class="clearfix">
				<span class="copyright">©2014搜狐焦点</span>
				<div class="switch_version fr">
					<a href="javascript:;" class="version">Pad版</a>
					<div class="version_list">
						<a role="touch_bg" href="" class="phone">手机版</a>
						<a role="touch_bg" href="" class="ipad current">Pad版</a>
						<a role="touch_bg" data-go="false" href="http://${_city.sourceDomainName }/newscenter/xwsy.html?cfrom=mobile" class="pc">电脑版</a>
					</div>
				</div>
				<a href="javascript:;" id="to_top">返回顶部</a>
			</footer>
		</section>
		
		<!-- js -->
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/pad_housenews.hzy_1.0.js"></script>
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
		<%@ include file="/views/pv.jsp"%>
	</body>
</html>