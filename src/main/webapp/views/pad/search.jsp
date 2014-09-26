<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="telephone=no" name="format-detection" />
<title>【${_city.cityName }${group_name }楼盘】-搜狐焦点${_city.cityName
	}房产</title>
<meta name="Keywords" content="${group_name }楼盘">
<meta name="Description"
	content="搜狐焦点${_city.cityName }房产网为广大网友提供了最新的${group_name }楼盘信息，最准确的${group_name }房价情况和最及时的${group_name }新房资讯等，是买房、购房首选网站。 ">
<%@ include file="/views/icon.jsp"%>
<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
<link rel="stylesheet"
	href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
<link rel="stylesheet"
	href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
<script
	src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script>
	var city = ${cityStr};
</script>
</head>

<body class="bg_all">
	<section class="container">
		<!-- 新版样式导航 -->
		<jsp:include page="/views/padHeader.jsp">
			<jsp:param value="搜索" name="page_title" />
		</jsp:include>

		<section class="content">
			<article class="article_search">
				<div class="search_wrap">
					<form name="searchForm" method="post"
						action="${ctx }/${_city.cityPinyinAbbr }/search/list/"
						class="search_form clearfix">
						<div class="form_item">
							<input type="text" name="group_name" placeholder="输入楼盘名称/地点"
								autocomplete="off" value="${group_name }" />
						</div>
						<input type="button" value="搜索" /> <a href="javascript:;"
							class="empty">清空</a>
					</form>
					<div class="suggest">
						<div class="suggest_item suggest_tip clearfix">
							<span>历史记录</span><a class="sempty fr" href="javascript:;">清空</a>
						</div>
						<div class="suggest_item suggest_tip_close clearfix">
							<a href="javascript:;" class="close fr">关闭</a>
						</div>
					</div>
				</div>
				<div class="results">
					在${_city.cityName }共找到${total}个
					<h1 class="search_text">${group_name }</h1>
					楼盘
				</div>
			</article>

			<div class="bg_all" id="search_wrapper">
				<div class="scroller">
					<article>
						<ul class="article_list article_img article_line article_result">
							<c:forEach var="bl" items="${buildingList}" varStatus="status">
								<li role="touch_bg"><a href="${bl.buildingUrl }"
									class="clearfix"> <img src="${bl.url }"
										alt="${bl.projName }" class="img fl" />
										<dl>
											<dt>
												${bl.projName }
												<c:choose>
													<c:when test="${bl.saleStatus == 0}">
														<span class="status sale">在售</span>
													</c:when>
													<c:when test="${bl.saleStatus == 1}">
														<span class="status wait">待售</span>
													</c:when>
													<c:when test="${bl.saleStatus == 2}">
														<span class="status remain">尾盘</span>
													</c:when>
													<c:otherwise>
														<span class="status over">售罄</span>
													</c:otherwise>
												</c:choose>
											</dt>
											<dd class="clearfix">
												${bl.projAddress}<span class="price fr">${bl.avgPriceShow}</span>
											</dd>
											<c:if test="${not empty fn:trim(bl.discount) }">
												<dd>
													<span class="mark discount">${bl.discount}</span>
												</dd>
											</c:if>
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

					<section class="crumb">
						<div class="inner">
							<a href="/${_city.cityPinyinAbbr }/">新房</a>&gt;<span>${group_name
								}楼盘</span>
						</div>
					</section>
				</div>
			</div>
		</section>

		<footer>
			<span class="copyright">&copy;2014搜狐焦点</span>
			<div class="switch_version">
				<a href="javascript:;" class="version">Pad版</a>
				<div class="version_list">
					<a href="" role="touch_bg" class="search_phone">手机版</a> <a href=""
						role="touch_bg" class="search_ipad current">Pad版</a> <a href=""
						role="touch_bg" class="search_pc">电脑版</a>
				</div>
			</div>
			<a href="javascript:;" id="to_top">返回顶部</a>
		</footer>
	</section>

	<div class="alert_box">
		<p>请输入关键词后再搜索</p>
	</div>

	<!-- js -->
	<script
		src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
	<script
		src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
	<script
		src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.search.gh_1.0.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
	<%@ include file="/views/pv.jsp"%>
</body>
</html>