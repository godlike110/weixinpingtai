<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="telephone=no" name="format-detection" />
<%@ include file="/views/icon.jsp"%>
<title>${news.title}_房产新闻-搜狐焦点${_city.cityName }房产</title>
<meta name="Keywords" content="${news.title}">
<meta name="Description" content="${news.title} ${zhaiyaoTDK}">
<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
<link rel="stylesheet"
	href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.0.css" />
<link rel="stylesheet"
	href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
<link rel="stylesheet" type="text/css"
	href="http://192.168.242.44/sceapp/focus_static/wap/common/css/swiper_v1.0.css">
<script
	src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.0.min.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/swiper_v2.1.min.js"></script>
</head>
<script>
	var city = ${city};
</script>
<body>
	<section class="container">
		<!-- 新版样式导航 -->
		<jsp:include page="/views/padHeader.jsp">
			<jsp:param value="详情" name="page_title" />
		</jsp:include>

		<section class="content clearfix">
			<article class="news_detail">
				<h1>${news.title}</h1>
				<p class="resource clearfix">
					<span class="fr">${news.time}</span> <span>${news.sourceName}</span>
				</p>
				<div class="line"></div>
				<div class="main_contents">
					<c:if test="${ not empty news.pageContent.newsSummary }">
						<p>摘要:${news.pageContent.newsSummary}</p>
					</c:if>
					${frontContent}
					<div class="back_content" style="display: none">
						${backContent}</div>
					<c:if test="${ not empty backContent}">
						<a href="javascript:;" class="more fold"><b></b>展开剩余文章</a>
					</c:if>
				</div>
			</article>
			<!-- 
				<div class="addfavorite"><span class="addfavorite_btn1">收藏</span><span class="webshare_btn1">分享</span></div>
				  -->
			<div class="fr share_dagou">
				<span>分享到：</span> <a class="bg1" href="${sWeibo }" target="_blank"></a>
				<!-- 
							<a class="bg2" href="${tWeibo }" target="_blank"></a>
							 -->
				<a class="bg3" href="${qZone }" target="_blank"></a>
			</div>

			<div class="add_line"></div>

			<section class="wap_module relative_news">
				<c:if test="${''!=infos}">
					<h2 class="h2_tit1">相关主题</h2>
					<!-- 
					<h3 class="h3_tit1"><span class="h3_tit1_t">栏目</span><a href="${ctx}/${_city.cityPinyinAbbr }/zixun/${type}">${topic}</a></h3>
					 -->
						${infos}
					</c:if>

				<h2 class="h2_tit1">
					今日热点<span class="lastest_time">最新2小时</span>
				</h2>
				<ul class="news_list_ul2">
					<c:forEach var="yaowen" items="${yaowen}" varStatus="status"
						end="4">
						<li><a href="${ctx}/${cityName }/zixun/${yaowen.newsId}/"
							role="touch_bg" url=""><p class="its_nr_p">${yaowen.title}</p></a></li>
					</c:forEach>
				</ul>
				<c:if test="${not empty buildingList}">				
						${buildingList}
					</c:if>

			</section>
		</section>

		<section class="crumb">
			<div class="inner">
				<a href="${ctx}/${_city.cityPinyinAbbr }/">新房</a>&gt;<a
					href="${ctx}/${_city.cityPinyinAbbr }/zixun/">新闻</a>&gt;<span>正文</span>
			</div>
		</section>

		<fmt:parseDate value="${news.time}" var="timeStr" pattern="yyyy年MM月dd日" type="date"></fmt:parseDate>
		<fmt:formatDate value="${timeStr }" var="timeUrl" pattern="yyyy-MM-dd"/>
		<footer>
			<span class="copyright">&copy;2014搜狐焦点</span>
			<div class="switch_version">
				<a href="javascript:;" class="version">Pad版</a>
				<div class="version_list">
					<c:if test="${mobile=='false'}">
						<a role="touch_bg" href="##" class="phone">手机版</a>
					</c:if>
					<a role="touch_bg" href="##" class="ipad current">Pad版</a> 
					<a role="touch_bg" data-go="false" href="http://${_city.sourceDomainName }/news/${timeUrl }/${news.newsId }.html?cfrom=mobile" class="pc">电脑版</a>
				</div>
			</div>
			<a href="javascript:;" id="to_top">返回顶部</a>
		</footer>
	</section>

	<!-- js -->
	<script>
		(function() {
			$('.fold').on('click', function() {
				$(this).hide();
				$(".back_content").show();
			});
		})();
	</script>
	<script
		src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
		<script>
$('.tel-num').click(function(){ var $this = $(this);
            $.post('/log/collect', { cityName: city.cityPinyinAbbr, phone400: $this.attr('data-phone400'), groupId: $this.attr('data-groupid') }, null);})

</script>
	<%@ include file="/views/pv.jsp"%>
</body>
</html>