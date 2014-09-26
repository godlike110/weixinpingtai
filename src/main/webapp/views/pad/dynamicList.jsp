<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="telephone=no" name="format-detection" />
<title>${building.projName}最新动态-${_city.cityName
	}${building.projName}楼盘资讯-搜狐焦点</title>
<meta name="keywords"
	content="${building.projName}最新动态，${_city.cityName }${building.projName}楼盘资讯">
<meta name="description"
	content="${_city.cityName }${building.projName}最新动态为您提供最新、最及时的${building.projName}最新动态信息，${building.projName}相关资讯。让您更快捷、更方便的了解${building.projName}相关信息，${building.projName}更多信息尽在搜狐焦点网">
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
	var groupId = ${building.groupId};
	var phone400 = ${building.phone400};
</script>
</head>

<body class="bg_all layout_fixed refer_list_body">
	<section class="container bg_all" id="dynamic_wrapper">
		<div class="scroller">
			<jsp:include page="/views/padHeader.jsp">
				<jsp:param value="最新动态" name="page_title" />
			</jsp:include>

			<section class="content clearfix">
				<article class="art_text">
					<ul class="article_list article_line dynamic_mod">
						<c:forEach var="rw" items="${recentNewsList}" varStatus="status">
							<li role="touch_bg"
								href="${ctx }/${_city.cityPinyinAbbr }/loupan/${building.groupId}/dongtai/${rw.infoid}/">
								<a href="javascript:;">
									<dl>
										<dt class="clearfix">
											${rw.infoname }<span class="fr">${rw.infotime }</span>
										</dt>
										<c:if test="${not empty rw.infosummary}">
											<dd>${rw.infosummary }</dd>
										</c:if>
										<dd>${rw.infocontent }</dd>
									</dl>
							</a>
							</li>
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
					<a href="/${_city.cityPinyinAbbr }/">新房</a>&gt; <a
						href="${ctx }/${_city.cityPinyinAbbr }/loupan/">${_city.cityName
						}楼盘</a>&gt; <a
						href="${ctx }/${_city.cityPinyinAbbr }/loupan/${building.groupId}/">${building.projName
						}</a>&gt; <span>楼盘动态</span>
				</div>

			</section>
		</div>
	</section>
	<%-- <c:if test="${building.phone400 != 0 }">
		<footer class="position_fixed">
			<a href="tel:4008882200,${building.phone400}" class="free_advice"><b></b>免费咨询
				400-888-2200 转 ${building.phone400}</a>
		</footer>
	</c:if>
	<c:if test="${building.phone400==0 }">
		<footer class="position_fixed">
			<a href="tel:4006782020" class="free_advice"><b></b>免费咨询
				400-678-2020</a>
		</footer>
	</c:if> --%>
	<c:if test="${building.phone400 != 0 }">
		<footer class="position_fixed">
			<div><a href="tel:4008882200,${building.phone400}" class="footer_ban_tel"><b></b>联系售楼处</a></div>
			<div><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${building.groupId}/woyaozixun" class="footer_ban_download"><b></b>在线咨询</a></div>
		</footer>
	</c:if>
	<c:if test="${building.phone400==0 }">
		<footer class="position_fixed">
			<div><a href="tel:4006782020" class="free_advice"><b></b>联系售楼处 </a></div>
			<div><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${building.groupId}/woyaozixun" class="footer_ban_download"><b></b>在线咨询</a></div>
		</footer>
	</c:if>
	<a href="javascript:;" id="to_top">返回顶部</a>

	<!-- js -->
	<script
		src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
	<script
		src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
	<script
		src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.dynamic.gh_1.0.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
	<%@ include file="/views/pv.jsp"%>
</body>
</html>