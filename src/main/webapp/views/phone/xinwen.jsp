<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<%@ include file="/views/icon.jsp"%>
<title>${news.title}_房产新闻-搜狐焦点${_city.cityName }房产</title>
<meta name="Keywords" content="${news.title}">
<meta name="Description" content="${news.title} ${zhaiyaoTDK}">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/swiper_v1.0.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/swiper_v2.1.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
<script>
var city = ${city};
</script>
</head>
<body class="wap_hotquestion_all wap_baodian_info_all">
	<div class="wap_container">
			<!-- 新版样式导航 -->
		<jsp:include page="/views/phoneHeader.jsp">
			<jsp:param value="详情" name="page_title" />
		</jsp:include>
		
		<!-- 资讯正文页主要内容板块 // -->
		<section class="hotquestion_list_main zixunnews_main">
			<div class="hotquestion_list_container">	
				<section class="dynamic_info_container" data-title="资讯正文">
					<div class="dynamic_info_ask">
						<h1 class="its_biaoti">${news.title}</h1>
						<p class="its_writer_time"><span class="fr">${news.time}</span>${news.sourceName}</p>
						<div class="borderTopBottom_e9e9e9_2"></div>
						<div class="dynamic_info_nr">
							<!-- 默认前面展示前三段话，后面产品可能还会动态调整第一次展示的段数，后端直接控制输出就行 -->
							<c:if test="${ not empty news.pageContent.newsSummary }"><p>摘要:${news.pageContent.newsSummary}</p></c:if>			
							${frontContent}
							<!-- 收起隐藏的文章内容 // -->
							<div class="hide_content_box">
								${backContent}
							</div>
							<!-- // 收起隐藏的文章内容 -->
						</div>
					</div>
					<c:if test="${ not empty backContent}">
					<div class="look_more unfold_surplus_content"><a href="javascript:;" role="text" url=""><em class="look_more_icon2"></em>展开剩余文章</a></div>
					</c:if>
					
					<div class="addfavorite hide"><span class="addfavorite_btn1">收藏</span><span class="webshare_btn1">分享</span></div>
					<div class="share_wb">分享到：
						<a href="${sWeibo }" target="_blank" class="sina_wb"></a>
						<!-- 
						<a href="${tWeibo }" target="_blank" class="tencent_wb"></a>
						 -->
						<a href="${qZone }" target="_blank" class="qq_zone"></a></div>
					
					<div class="borderTopBottom_e9e9e9_2"></div>
				</section>
				<section class="wap_module relative_news">				
					<c:if test="${''!=infos}">		
					<h2 class="h2_tit1">相关主题</h2>
					<!-- 
					<h3 class="h3_tit1"><span class="h3_tit1_t">栏目</span><a href="${ctx}/${_city.cityPinyinAbbr }/zixun/${type}">${topic}</a></h3>
					 -->
						${infos}
					</c:if>
					<h2 class="h2_tit1">今日热点<span class="lastest_time">最新2小时</span></h2>
					<ul class="news_list_ul2">
						<c:forEach var="yaowen" items="${yaowen}" varStatus="status" end="4">
								<li>
								<a href="${ctx}/${cityName }/zixun/${yaowen.newsId}/" role="text" url="">
								<p class="its_nr_p">${yaowen.title }
								<%-- <c:choose>
									   <c:when test="${fn:length(yaowen.title) > 17}">
											  ${fn:substring(yaowen.title, 0, 17)}...
									   </c:when>
									   <c:otherwise>
									   		  ${yaowen.title }
									   </c:otherwise>
								</c:choose> --%>
								</p>
								</a>
								</li>
						</c:forEach>
					</ul>
					<c:if test="${not empty buildingList}">				
						${buildingList}
					</c:if>						
				</section>
			</div>
		</section>
		
		<!-- 安卓手机拨号 -->
		<div class="md-overlay"></div>
		<div class="over"></div>
		<div class="alert_box" id="check_net">
 			<p class="p1">请在接通后拨分机号</p>
 			<p class="p1">46911</p>
 			<div class="itos_btn1" id="check_net_confirm"><a href="javascript:;">好的</a></div>
			<div class="its_close_btn"></div>
		</div>
		
		
		<!-- 面包屑 -->
		<div class="link_boxs"><a href="${ctx}/${_city.cityPinyinAbbr }/">新房</a><span class="h_space1">></span><a href="${ctx}/${_city.cityPinyinAbbr }/zixun/">新闻</a><span class="h_space1">></span>正文</div>
		
		<fmt:parseDate value="${news.time}" var="timeStr" pattern="yyyy年MM月dd日" type="date"></fmt:parseDate>
		<fmt:formatDate value="${timeStr }" var="timeUrl" pattern="yyyy-MM-dd"/>
		<!-- 底部导航 // -->
		<footer class="foot_nav">
			<div class="foot_nav_copyright">©2014 搜狐焦点</div>
			<div class="foot_nav_box2">
				<span class="wap_version" role="wap_version_menu">手机版</span>
				<ul class="nav_box_ul1 wap_version_menu_ul1">
					<li><a href="##" role="wap_version_text" url="" data-nr="PHONE" class="current">手机版</a></li>
					<li><a href="http://${_city.sourceDomainName }/news/${timeUrl }/${news.newsId }.html?cfrom=mobile" data-go="false" role="wap_version_text" url="" data-nr="PC">电脑版</a></li>
				</ul>
			</div>
		</footer>
		<!-- // 底部导航 -->
		
		<div class="back2top" data-title="返回顶部"></div>		
	</div>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
		<script>
$('.tel-num').click(function(){ var $this = $(this);
            $.post('/log/collect', { cityName: city.cityPinyinAbbr, phone400: $this.attr('data-phone400'), groupId: $this.attr('data-groupid') }, null);})

</script>
	<%@ include file="/views/pv.jsp"%>
	<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>