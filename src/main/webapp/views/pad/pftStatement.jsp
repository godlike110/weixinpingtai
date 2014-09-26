<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<!-- saved from url=(0038)http://m.focus.cn/bj/xinwen/19502802/# -->
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
		<meta content="telephone=no" name="format-detection">
		<title>${_city.cityName }看房团-${_city.cityName }搜狐焦点网</title>
		<meta name="Keywords" content="${_city.cityName }看房团"/>
		<meta name="Description" content="${_city.cityName }搜狐焦点看房团为大家设计最佳看房路线，带大家去看心仪的楼盘。同时，${_city.cityName }看房团还将不定期的邀请房地产专业人士陪同，为大家解读户型，指点装修问题。"/>
		<meta name="apple-mobile-web-app-title" content="搜狐焦点房产">
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css"/>
		<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
		<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
		<script type="text/javascript">
			var city = ${city};
		</script>
</head>
	
	<body class="bg_all layout_fixed">
		<section class="container">
			<!-- 新版样式导航 -->
		<jsp:include page="/views/padHeader.jsp">
			<jsp:param value="免责声明" name="page_title" />
		</jsp:include>
			
			<section class="content clearfix">
				<article class="news_detail">
				
					<c:forEach items="${declarations }" var="declaration" varStatus="status">
					<h1>${declaration.title }</h1>
					<div class="main_contents no_text_indent">
					<p>${declaration.content }</p>
					</div>
					</c:forEach>
					
				</article>
			</section>

			<section class="crumb">
				<div class="inner">
					<p><a color="#aaa" href="${ctx }/${_city.cityPinyinAbbr}/"><font color="#aaa">新房</font></a>&gt;<a color="#aaa" href="${ctx }/${_city.cityPinyinAbbr}/pafangtuan/"><font color="#aaa">看房团</font></a>&gt;<a color="#aaa" href="${ctx }/${_city.cityPinyinAbbr}/pafangtuan/${lineId}"><font color="#aaa">爬房详情</font></a>&gt;<a color="#aaa" href="javascript:;"><font color="#aaa">免责声明</font></a></p>
				</div>
			</section>

			<footer class="position_fixed">
				<span class="copyright">©2014搜狐焦点</span>
				<div class="switch_version">
					<a href="javascript:;" class="version">Pad版</a>
					<div class="version_list">
						<a role="touch_bg" href="javascript:;" class="phone">手机版</a>
						<a role="touch_bg" href="javascript:;" class="ipad current">Pad版</a>
						<c:choose>
						<c:when test="${_city.cityPinyinAbbr ==  'suzhou' }">
						<a role="touch_bg" href="http://kanfangtuan.focus.cn/${_city.cityPinyinAbbr }/route_${lineId }?cfrom=mobile" data-go="false" class="pc">电脑版</a>
						</c:when>
						<c:otherwise>
						<a role="touch_bg" href="http://kanfangtuan.focus.cn/${_city.cityPinyin }/route_${lineId }?cfrom=mobile" data-go="false" class="pc">电脑版</a>
						</c:otherwise>
						</c:choose>
					</div>
				</div>
			</footer>
		</section>
		<a href="javascript:;" id="to_top" style="display: none;">返回顶部</a>
		
		<!-- js -->
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
		<script type="text/javascript">var _focus_pv_id = "focus.wap.all";</script>
		<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/pft/js/pv_v210.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/pft/js/statement_pad.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
	<%@ include file="/views/pv.jsp"%>
    </body></html>