<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>${recentNews.title}-搜狐焦点</title>
<meta name="keywords" content="${recentNews.title}" />
<meta name="description" content="${recentNews.infosummary}" />
<%@ include file="/views/icon.jsp" %>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
<script type="text/javascript">
var city = ${city};
var groupId = ${group_id};
var phone400 = ${phone400};
</script>
</head>
<body class="wap_hotquestion_all wap_hotquestion_all2 refer_list_body">
	<div class="wap_container">
			<!-- 新版样式导航 -->
		<jsp:include page="/views/phoneHeader.jsp">
			<jsp:param value="详情" name="page_title" />
		</jsp:include>
		
		<!-- 楼盘动态正文页主要内容板块 // -->
		<section class="hotquestion_list_main">
			<div class="hotquestion_list_container">	
				<section class="dynamic_info_container" data-title="楼盘动态正文">
					<div class="dynamic_info_ask">
						<p class="its_biaoti">${recentNews.infoname}</p>
						<p class="its_writer_time"><span class="fr">${recentNews.infotime}</span>消息来源：${recentNews.infoauthor}</p>
						<div class="dynamic_info_nr">
							<c:if test="${recentNews.infosummary }">
							<p>${recentNews.infosummary }</p>
							</c:if>
							<p>${recentNews.infocontent }</p>
						</div>
					</div>
				</section>
				
				<c:if test="${not empty recentNews.pros}">
				<section class="dynamic_info_container" data-title="相关楼盘">
					<header class="public_title1 public_title1_noborder" data-title="相关楼盘-标题">
						<h2 class="its_tit1">相关楼盘<em class="its_icon1"></em></h2>
					</header>
					<ul class="search_list_container" data-title="搜索结果-列表">
						<c:forEach var="bl" items="${recentNews.pros}" varStatus="status">
							<li>
							<a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${bl.groupId}/" role="text" url="" class="clearfix">
								<div class="fl its_img"><img alt="${bl.projName }" src="${bl.url }"></div>
								<div class="its_content">
									<p class="it_name">${bl.projName }
									<%-- <c:choose>
										   <c:when test="${fn:length(bl.projName) > 6}">
												  ${fn:substring(bl.projName, 0, 6)}...
										   </c:when>
										   <c:otherwise>
										   		  ${bl.projName }
										   </c:otherwise>
									</c:choose> --%>
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
		<div class="link_boxs"><a href="/${_city.cityPinyinAbbr }/">新房</a>
			<span class="h_space1">></span><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/">${_city.cityName }楼盘</a>
			<span class="h_space1">></span><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${group_id}/">
			<c:choose>
				   <c:when test="${fn:length(groupName) > 4}">
						  ${fn:substring(groupName, 0, 4)}...
				   </c:when>
				   <c:otherwise>
				   		  ${groupName}
				   </c:otherwise>
			</c:choose>
			</a>
			<span class="h_space1">></span>楼盘动态</div>

		<!-- 400电话 -->
		<%-- <c:if test="${phone400 != 0 }">
		<div class="hotline_phone">
			<a href="tel:4008882200,${phone400}" class="hotline_phone_btn" phone="${phone400}"><span class="pnone_icon"></span>免费咨询 400-888-2200 转 ${phone400}</a>
		</div>
		</c:if>			
		<c:if test="${phone400==0 }">
			<div class="hotline_phone">
				<a href="tel:4006782020" class="hotline_phone_btn" phone="${phone400}"><span class="pnone_icon"></span>免费咨询 400-678-2020</a>
			</div>
		</c:if> --%>
		
		
		<c:if test="${phone400 != 0 }">
			<div class="hotline_phone">
				<div>
					<a href="tel:4008882200,${phone400}" class="footer_ban_tel" phone="${phone400}"><b></b>联系售楼处</a>
				</div>
				<div>
					<a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${group_id}/woyaozixun" class="footer_ban_download"><b></b>在线咨询</a>
				</div>
			</div>
		</c:if>
		<c:if test="${phone400==0 }">
			<div class="hotline_phone">
				<div>
					<a href="tel:4006782020" class="footer_ban_tel"><b></b>联系售楼处</a>
				</div>
				<div>
					<a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${group_id}/woyaozixun" class="footer_ban_download"><b></b>在线咨询</a>
				</div>
			</div>
		</c:if>
		
		<div class="back2top" data-title="返回顶部"></div>
		
	</div>
	
	<!-- 电话弹层 // -->
	<div class="over"></div>
	<div class="alert_box" id="check_net">
     	<p class="p1">请在接通后拨分机号</p>
     	<p class="p1">${phone400}</p>
     	<div class="itos_btn1" id="check_net_confirm"><a href="javascript:;">好的</a></div>
		<div class="its_close_btn"></div>
    </div>
	<!-- // 电话弹层 -->
	<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
	<%@ include file="/views/pv.jsp"%>
	<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>