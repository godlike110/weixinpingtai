<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<meta content="telephone=no" name="format-detection" />
<title>${questionInfo.question}-购房咨询-搜狐焦点</title>
<meta name="keywords" content="${questionInfo.question }" />
<meta name="description" content="${summary }" />
<%@ include file="/views/icon.jsp"%>
<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
<link rel="stylesheet"
	href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
<link rel="stylesheet"
	href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
<script
	src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript">
	var city = ${city};
	var groupId = ${bl.groupId};
	var phone400 = ${bl.phone400};
</script>
</head>

<body class="refer_list_body layout_fixed">
	<section class="container layout_fixed">
		<!-- 新版样式导航 -->
		<jsp:include page="/views/padHeader.jsp">
			<jsp:param value="详情" name="page_title" />
		</jsp:include>

		<section class="content clearfix">
			<article class="detail_mod">
				<p>
					<span class="member">${questionInfo.userName }</span>
					<fmt:formatDate value="${questionInfo.createTime }" pattern="yyyy年M月d日" />
				</p>
				<div class="question">
					<p>${questionInfo.question }</p>
				</div>
				<div class="line"></div>
				<c:choose>
					<c:when test="${questionInfo.status == 6}">
						<p>
							<span class="member">${questionInfo.editorDesc }</span>
							<fmt:formatDate value="${questionInfo.updateTime }" pattern="yyyy年M月d日" />
						</p>
						<div class="answer"><p>答：${questionInfo.answer }</p></div>
					</c:when>
					<c:otherwise><div class="answer"><p>${questionInfo.answer }</p></div></c:otherwise>
				</c:choose>
			</article>

			<article>
				<h2 class="border_wrap">相关楼盘</h2>
				<ul class="article_list article_line article_img p20">
					<li role="touch_bg"><a
						href="${ctx }/${_city.cityPinyinAbbr}/loupan/${bl.groupId}/"
						class="clearfix"> <img src="${bl.url}" alt="${bl.projName}"
							class="img fl" />
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
								<dd class="relate_link">
									${bl.projAddress} <span class="price">${bl.priceForShow}</span>

								</dd>
								<c:if test="${ not empty fn:trim(bl.discount)}">
									<dd>
										<span class="mark discount">${bl.discount}</span>
									</dd>
								</c:if>
							</dl>
					</a></li>
				</ul>
			</article>
		</section>

		<section class="crumb">
			<div class="inner">
				<a href="/${_city.cityPinyinAbbr }/">新房</a>&gt; <a
					href="${ctx }/${_city.cityPinyinAbbr }/loupan/">${_city.cityName
					}楼盘</a>&gt; <a
					href="${ctx }/${_city.cityPinyinAbbr }/loupan/${bl.groupId}/">${bl.projName
					}</a>&gt; <span>楼盘咨询</span>
			</div>
		</section>
	</section>

	<c:if test="${bl.phone400 != 0 }">
		<footer class="position_fixed">
			<div><a href="tel:4008882200,${bl.phone400}" class="footer_ban_tel"><b></b>联系售楼处</a></div>
			<div><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${bl.groupId}/woyaozixun" class="footer_ban_download"><b></b>在线咨询</a></div>
		</footer>
	</c:if>
	<c:if test="${bl.phone400==0 }">
		<footer class="position_fixed">
			<div><a href="tel:4006782020" class="free_advice"><b></b>联系售楼处 </a></div>
			<div><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${bl.groupId}/woyaozixun" class="footer_ban_download"><b></b>在线咨询</a></div>
		</footer>
	</c:if>
	<!-- js -->
	<script
		src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
	<script
		src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
	<%@ include file="/views/pv.jsp"%>
</body>
</html>