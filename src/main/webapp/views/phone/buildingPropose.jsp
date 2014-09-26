<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>${bp.title}_楼盘导购-搜狐焦点${_city.cityName }房产</title>
<meta name="Keywords" content="楼盘导购，${_city.cityName }房产，${_city.cityName }楼盘" />
<meta name="Description" content="${bp.promotion}" />
<%@ include file="/views/icon.jsp" %>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<script>
var city = ${city};
</script>
</head>
<body class="wap_daogou_info_all">
	<div class="wap_container">
			<!-- 新版样式导航 -->
		<jsp:include page="/views/phoneHeader.jsp">
			<jsp:param value="详情" name="page_title" />
		</jsp:include>
		
		<!-- 主要内容板块 // -->
		<section class="hotquestion_list_main">
			<div class="hotquestion_list_container">	
				<section class="dynamic_info_container" data-title="楼盘导购正文">
					<div class="dynamic_info_ask">
						<h1 class="its_biaoti">${bp.title}</h1>
						<p class="its_writer_time"><span class="fr">${bp.pubDate}</span>编辑：${bp.editorName}</p>
						<div class="dynamic_info_nr">
							${bp.content}
						</div>
						<div class="share_wb">分享到：
						<a href="${sWeibo }" target="_blank" class="sina_wb"></a>
						<!-- 
						<a href="${tWeibo }" target="_blank" class="tencent_wb"></a>
						 -->
						<a href="${qZone }" target="_blank" class="qq_zone"></a></div>
					</div>
				</section>
				
					<c:if test="${not empty bp.pros}">
				<section class="dynamic_info_container" data-title="相关楼盘">
					<header class="public_title1 public_title1_noborder" data-title="相关楼盘-标题">
						<h2 class="its_tit1">相关楼盘<em class="its_icon1"></em></h2>
					</header>
					<ul class="search_list_container" data-title="搜索结果-列表">
						<c:forEach var="bl" items="${bp.pros}" varStatus="status">
							<li>
							<a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${bl.groupId}?fromId=dg_${bp.id}" role="text" url="" class="clearfix">
								<div class="fl its_img"><img alt="${bl.projName }" src="${bl.url }"></div>
								<div class="its_content">
									<p class="it_name">
									<c:choose>
										   <c:when test="${fn:length(bl.projName) > 6}">
												  ${fn:substring(bl.projName, 0, 6)}...
										   </c:when>
										   <c:otherwise>
										   		  ${bl.projName }
										   </c:otherwise>
									</c:choose>
													
									<c:choose>
										   <c:when test="${bl.saleStatus == 0}">
												  <em class='in_sale' data-title=在售></em>
										   </c:when>
										   <c:when test="${bl.saleStatus == 1}">
												  <em class='wait_sale' data-title=待售></em>
										   </c:when>
										   <c:when test="${bl.saleStatus == 2}">
												  <em class='last_sale' data-title=尾盘></em>
										   </c:when>
										   <c:otherwise>
												  <em class='out_sale' data-title=售罄></em>
										   </c:otherwise>
									</c:choose>
									</p>
									<p class="it_address">${bl.projAddress}</p>
									<p class="it_price">${bl.priceForShow}</p>			

									<c:if test="${ not empty fn:trim(bl.discount)}">
									<p class="it_zhekou"><em class="zhekou1"></em>${bl.discount}</p>
									</c:if>
								</div>
							</a>
							</li>
						</c:forEach>
						
					</ul>
				</section>
				</c:if>
				
			</div>
		</section>
		
		<!-- 面包屑 -->
		<div class="link_boxs"><a href="/${_city.cityPinyinAbbr }/">新房</a><span class="h_space1">></span><a href="${ctx }/${_city.cityPinyinAbbr }/daogou/">楼盘导购</a><span class="h_space1">></span>导购正文</div>
		
		<!-- 底导 -->
		<footer class="foot_nav">
			<div class="foot_nav_copyright">©2014 搜狐焦点</div>
			<div class="foot_nav_box2">
				<span class="wap_version" role="wap_version_menu">手机版</span>
				<ul class="nav_box_ul1 wap_version_menu_ul1">
					<c:if test="${mobile=='false'}">
							<li><a href="#" role="wap_version_text" url="" data-nr="PAD">Pad版</a></li>
					</c:if>
					<li><a href="#" role="wap_version_text" url="" data-nr="PHONE" class="current">手机版</a></li>
					<li><a href="#" role="wap_version_text" url="" data-nr="PC">电脑版</a></li>
				</ul>
			</div>
		</footer>
		
		<div class="back2top" data-title="返回顶部"></div>
		
	</div>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
	<%@ include file="/views/pv.jsp"%>
</body>
</html>