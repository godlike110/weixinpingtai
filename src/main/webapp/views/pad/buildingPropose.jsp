<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
		<meta content="telephone=no" name="format-detection" />
		<title>${bp.title}_楼盘导购-搜狐焦点${_city.cityName }房产</title>
		<meta name="Keywords" content="楼盘导购，${_city.cityName }房产，${_city.cityName }楼盘" />
		<meta name="Description" content="${bp.promotion}" />
		<%@ include file="/views/icon.jsp" %>
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
		<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
		<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
		<script>
			var city = ${city};
		</script>
	</head>
	
	<body>
		<section class="container">
			<!-- 新版样式导航 -->
		<jsp:include page="/views/padHeader.jsp">
			<jsp:param value="详情" name="page_title" />
		</jsp:include>
			
			<section class="content clearfix">
				<article class="news_detail">
					<h1>${bp.title}</h1>
					<p>
						<span class="resource fl">编辑：${bp.editorName}</span>
						<span class="resource fr">${bp.pubDate}</span>
					</p>
					
					<div style="clear:both"></div>
					<div class="main_contents2">
						${bp.content}						
						<div class="fr share_dagou">
							<span>分享到：</span>
							<a class="bg1" href="${sWeibo }" target="_blank"></a>
							<!-- 
							<a class="bg2" href="${tWeibo }" target="_blank"></a>
							 -->
							<a class="bg3" href="${qZone }" target="_blank"></a>
						</div>
												
					<div style="clear:both"></div>
					</div>
				</article>

				<c:if test="${not empty bp.pros}">
				<article>
					<h2 class="border_wrap">相关楼盘</h2>
					<ul class="article_list article_line article_img p20">
						
						<c:forEach var="bl" items="${bp.pros}" varStatus="status">
							<li role="touch_bg">
								<a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${bl.groupId}?fromId=dg_${bp.id}" class="clearfix">
									<img src="${bl.url }" alt="${bl.projName }" class="img fl" />
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
											${bl.projAddress}
											
											<span class="price">${bl.priceForShow}</span>

										</dd>
										<c:if test="${ not empty fn:trim(bl.discount)}">
										<dd>
											<span class="mark discount">${bl.discount}</span>
										</dd>
										</c:if>
									</dl>
								</a>
							</li>
						</c:forEach>
					</ul>
				</article>
				</c:if>
			</section>
			
			<section class="crumb">
				<div class="inner">
					<a href="/${_city.cityPinyinAbbr }/">新房</a>&gt;<a href="${ctx }/${_city.cityPinyinAbbr }/daogou/">楼盘导购</a>&gt;<span>导购正文</span>
				</div>
			</section>			

			<footer class="clearfix">
					<span class="copyright">&copy;2014搜狐焦点</span>
					<div class="switch_version fr">
						<a href="javascript:;" class="version">Pad版</a>
						<div class="version_list">
							<a role="touch_bg" href="" class="phone">手机版</a>
							<a role="touch_bg" href="" class="ipad current">Pad版</a>
							<a role="touch_bg" href="" class="pc">电脑版</a>
						</div>
					</div>
					<a href="javascript:;" id="to_top">返回顶部</a>
				</footer>
		</section>
		
		<!-- js -->
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
		<%@ include file="/views/pv.jsp"%>
	</body>
</html>