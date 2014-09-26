<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>${questionInfo.question}-购房咨询-搜狐焦点</title>
<meta name="keywords" content="${questionInfo.question }" />
<meta name="description" content="${summary }"/>
<%@ include file="/views/icon.jsp" %>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<script type="text/javascript">
var city = ${city};
</script>
</head>
<body class="wap_hotquestion_all wap_hotquestion_all2 wap_ask_share_all">
	<div class="wap_container">
		
		<!-- 楼盘咨询分享页 -->
		<section class="hotquestion_list_main">
			<div class="hotquestion_list_container">	
				<section class="hotquestion_container" data-title="楼盘咨询正文">
					<div class="hotquestion_info_ask border_e9e9e9">
						<p class="its_writer_time">${questionInfo.userName }<span class="h_space2"><fmt:formatDate value="${questionInfo.createTime }" pattern="yyyy年M月d日"/></span></p>
						<p class="answer_paragram">${questionInfo.question }</p>
					</div>
					<div class="hotquestion_info_answer">
						<p class="its_writer_time">${questionInfo.editorDesc }<span class="h_space2"><fmt:formatDate value="${questionInfo.updateTime }" pattern="yyyy年M月d日"/></span></p>
						<p class="answer_paragram">答：${questionInfo.answer }</p>
						<div class="tr"><span class="have_use">有用<em class="red1">${questionInfo.usefulCount }</em></span></div>
					</div>
				</section>
				
				<section class="hotquestion_container" data-title="相关楼盘">
					<header class="public_title1 public_title1_noborder" data-title="相关楼盘-标题">
						<h2 class="its_tit1">相关楼盘<em class="its_icon1"></em></h2>
					</header>
					<ul class="search_list_container" data-title="搜索结果-列表">
							<li>
							<c:if test="${not empty _city }">
							<a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${bl.groupId}/" role="text" url="" class="clearfix">
							</c:if>
								<div class="fl its_img"><img alt="${bl.projName }" src="${bl.url }"></div>
								<div class="its_content">
									<p class="it_name">${bl.projName }									
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
									<c:choose>
										   <c:when test="${bl.avgPrice != '' && bl.avgPrice != '0'}">
												  <p class="it_price"><em>${bl.avgPrice}</em>元/平米</p>
										   </c:when>
										   <c:otherwise>
										   		  <p class="it_price"><em>价格待定</em></p>          
										   </c:otherwise>
									</c:choose>
									<c:if test="${bl.discount != ' '}">
									<p class="it_zhekou"><em class="zhekou1"></em>${bl.discount}</p>
									</c:if>
								</div>
							<c:if test="${not empty _city }">
							</a>
							</c:if>
							</li>
					</ul>
				</section>
			</div>
		</section>
		
		<!-- 广告banner -->
		<div id="ad">
			<div class="advertisement_box">
				<a href="${ctx }/static/appwap.html" id="adImg1"><img id="adImg" class="img"
					src="http://192.168.242.44/sceapp/focus_static/wap/phone/img/advertisement_img1.png"
					width="320" height="65" alt="搜狐焦点图片"/></a> <img class="closeAd"
					src="http://192.168.242.44/sceapp/focus_static/wap/phone/img/advertisement_close1.png"
					width="24" height="24" alt="搜狐焦点图片"/>
			</div>
		</div>
	</div>
	<%@ include file="/views/pv.jsp"%>
</body>
</html>