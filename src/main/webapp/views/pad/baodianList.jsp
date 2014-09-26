<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="telephone=no" name="format-detection" />
<title>【购房宝典|购房须知|购房指南】-搜狐焦点${_city.cityName }房产</title>
<meta name="Keywords" content="${_city.cityName }房产，购房宝典,购房须知 ,购房指南 ">
<meta name="Description"
	content="搜狐焦点${_city.cityName }房产购房宝典频道为您提供购房须知，买房技巧，买房注意事项，购房指南，购房攻略等信息。">
<%@ include file="/views/icon.jsp"%>
<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
<link rel="stylesheet"
	href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
<link rel="stylesheet"
	href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
<script
	src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script>
	var city = ${city};
</script>
</head>

<body>
	<section class="container" id="baodian_wrapper">
		<div class="scroller">

			<jsp:include page="/views/padHeader.jsp">
				<jsp:param value="购房宝典" name="page_title" />
			</jsp:include>

			<section class="content clearfix">
				<article class="no_padd">
					<ul class="article_list article_img article_img2 article_line p20">
						<c:forEach var="housingGuide" items="${guideList}"
							varStatus="status">
							<li role="touch_bg"><a
								href="${ctx }/${_city.cityPinyinAbbr }/baodian/${housingGuide.id}/"
								class="clearfix"> <img src="${housingGuide.picUrl}"
									alt="${housingGuide.title }" class="img fl" />
									<dl>
										<dt>${housingGuide.title }</dt>
										<c:if test="${not empty housingGuide.summary  }">
											<dd>${housingGuide.summary }</dd>
										</c:if>
										<dd class="seprate">
											${housingGuide.catagoryName } <span> <fmt:formatDate
													value="${housingGuide.createTime }" pattern="M月d日" />
											</span>
										</dd>
									</dl>
							</a></li>
						</c:forEach>
					</ul>
					<c:if test="${hasNext}">
						<div id="has_more">
							<div class="line line_nop"></div>
							<a href="javascript:;" class="load_more">滑动加载更多</a> <a
								href="javascript:;" class="loading"><b class="rotating"></b>加载中...</a>
						</div>
					</c:if>
				</article>
			</section>

			<section class="crumb">
				<div class="inner">
					<a href="/${_city.cityPinyinAbbr }/">新房</a>&gt;<span>购房宝典</span>
				</div>
			</section>

			<footer>
				<span class="copyright">&copy;2014搜狐焦点</span>
				<div class="switch_version">
					<a href="javascript:;" class="version">Pad版</a>
					<div class="version_list">
						<a role="touch_bg" href="#" class="phone">手机版</a> <a
							role="touch_bg" href="#" class="ipad current">Pad版</a> <a
							role="touch_bg" href="#" class="pc">电脑版</a>
					</div>
				</div>
			</footer>
		</div>
	</section>
	<a href="javascript:;" id="to_top">返回顶部</a>

	<!-- js -->
	<script
		src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
	<script
		src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
	<script
		src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.baodian.gh_1.0.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
	<%@ include file="/views/pv.jsp"%>
</body>
</html>