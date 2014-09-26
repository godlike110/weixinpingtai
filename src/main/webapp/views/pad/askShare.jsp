<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
		<meta content="telephone=no" name="format-detection" />
		<title>${questionInfo.question}-购房咨询-搜狐焦点</title>
		<meta name="keywords" content="${questionInfo.question }" />
		<meta name="description" content="${summary }"/>
		<%@ include file="/views/icon.jsp" %>
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
		<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
		<script type="text/javascript">
		    var city = ${city};
		</script>
	</head>
	
	<body class="bg_all">
		<section class="container">
			
			<section class="content clearfix">
				<article class="detail_mod">
					<p>会员<span class="member">${questionInfo.userName }</span><fmt:formatDate value="${questionInfo.createTime }" pattern="yyyy年M月d日"/></p>
					<div class="question">
						<p>${questionInfo.question }</p>
					</div>
					<div class="line"></div>
					<p><span class="member">${questionInfo.editorDesc }</span><fmt:formatDate value="${questionInfo.updateTime }" pattern="yyyy年M月d日"/></p>
					<div class="answer">
						<p>答：${questionInfo.answer }</p>
						<p class="clearfix">
							<span class="count fr">有用<em>${questionInfo.usefulCount }</em></span>
						</p>
					</div>
				</article>

				<article>
					<h2 class="border_wrap">相关楼盘</h2>
					<ul class="article_list article_line article_img p20">
						<li role="touch_bg">
						<c:if test="${not empty _city }">
							<a href="${ctx }/${_city.cityPinyinAbbr}/loupan/${bl.groupId}/" class="clearfix">
						</c:if>
								<img src="${bl.url}" alt="${bl.projName}" class="img fl" />
								<dl>
									<dt>${bl.projName }
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
									<dd class="relate_link">${bl.projAddress}
										<c:choose>
										   <c:when test="${bl.avgPrice != '' && bl.avgPrice != '0'}">
												  <span class="price"><em>${bl.avgPrice}</em>元/平米</span>
										   </c:when>
										   <c:otherwise>
												  <span class="price"><em>价格待定</em></span>            
										   </c:otherwise>
										</c:choose>
									</dd>
									<c:if test="${bl.discount != ' '}">
									<dd>
										<span class="mark discount">${bl.discount}</span>
									</dd>
									</c:if>
								</dl>
							<c:if test="${not empty _city }">
							</a>
							</c:if>
						</li>
					</ul>
				</article>
			</section>
				<div id="ad" style="position:fixed;left:0;bottom:0;right:0">
					<div class="advertisement_box">
						<a href="${ctx }/static/appwap.html" id="adImg1"><img id="adImg" class="img" src="http://a1.itc.cn/sceapp/focus_static/wap/images/advertisement_img1.png" width="320" height="65"/></a>
						<img class="closeAd" src="http://a1.itc.cn/sceapp/focus_static/wap/images/advertisement_close1.png" width="20" height="20">		
					</div>
				</div>
		</section>
		
		<!-- js -->
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/ipad.ad.js"></script>
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
		<%@ include file="/views/pv.jsp"%>
	</body>
</html>