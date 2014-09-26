<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>购房问答-${_city.cityName }新房_买房注意事项-搜狐焦点</title>
<meta name="keywords" content="购房问答、${_city.cityName }新房_买房注意事项" />
<meta name="description" content="购房问答，搜狐焦点${_city.cityName }买房问答频道为您提供最全面的购房相关信息与咨询，包括楼盘相关信息、楼盘相关新闻、买房注意事项，购房更多信息尽在搜狐焦点网。" />
<%@ include file="/views/icon.jsp" %>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
<script>
var city = ${city};
var groupId = ${bl.groupId}; 
var phone400 = ${bl.phone400};
</script>
</head>
<body class="wap_hotquestion_all wap_hotquestion_all2 refer_list_body">
	<div class="wap_container scroller" id="wrapper">
		<div id="scroller">
			<!-- 新版样式导航 -->
		<jsp:include page="/views/phoneHeader.jsp">
			<jsp:param value="楼盘咨询" name="page_title" />
		</jsp:include>
			
			<!-- 楼盘咨询列表页主要内容板块 // -->
			<section class="hotquestion_list_main">
				<div class="hotquestion_list_container">	
					<section class="hotquestion_container" data-title="楼盘咨询列表">
						<ul class="hotquestion_list_ul">
						<c:forEach var="item" items="${questionList}" varStatus="status">
							<li>
								<a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${bl.groupId}/zixun/${item.id}/" role="text" class="clearfix">
									<div class="fl its_question_icon"><em class="question_icon"></em></div>
									<div class="its_content">
										<p class="ask_paragram">${item.question}</p>
											<c:choose>
												<c:when test="${item.status==6}">
													<p class="its_writer_time clearfix">${item.editorDesc }<span class="h_space2"><fmt:formatDate value="${item.updateTime }" pattern="M月d日"/></span></p>
													<p class="answer_paragram">答：${fn:substring(item.answer, 0, 75)}</p>
												</c:when>
												<c:otherwise>
													<p class="answer_paragram">等待回复</p>
												</c:otherwise>
											</c:choose>
									</div>
								</a>
							</li>
						</c:forEach>				
						</ul>
						<c:if test="${hasNext}">
						<div class="pull font_gray">
							<div class="icon fl"></div>
							<p>滑动加载更多</p>
						</div>
						</c:if>
					</section>				
						
				</div>
			</section>
			
			<!-- 面包屑 -->
			<div class="link_boxs"><a href="/${_city.cityPinyinAbbr }/">新房</a>
			<span class="h_space1">></span><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/">${_city.cityName }楼盘</a>
			<span class="h_space1">></span><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${bl.groupId}/">
			<c:choose>
				   <c:when test="${fn:length(bl.projName) > 4}">
						  ${fn:substring(bl.projName, 0, 4)}...
				   </c:when>
				   <c:otherwise>
				   		  ${bl.projName }
				   </c:otherwise>
			</c:choose>
			</a>
			<span class="h_space1">></span>楼盘咨询</div>
		
		</div>
	</div>
	
	<!-- 返回顶部 // -->
	<div class="back2top" data-title="返回顶部"></div>
	
	<!-- 400电话 -->
	<c:if test="${bl.phone400 != 0 }">
		<div class="hotline_phone">
			<div><a href="tel:4008882200,${bl.phone400}" class="footer_ban_tel"><b></b>联系售楼处</a></div>
			<div><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${bl.groupId}/woyaozixun" class="footer_ban_download"><b></b>在线咨询</a></div>
		</div>
	</c:if>			
	<c:if test="${bl.phone400==0 }">
		<div class="hotline_phone">
			<div><a href="tel:4006782020" class="footer_ban_tel"><b></b>联系售楼处</a></div>
			<div><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${bl.groupId}/woyaozixun" class="footer_ban_download"><b></b>在线咨询</a></div>
		</div>
	</c:if>
	<!-- 电话弹层 // -->
	<div class="over"></div>
	<div class="alert_box" id="check_net">
     	<p class="p1">请在接通后拨分机号</p>
     	<p class="p1">${bl.phone400 }</p>
     	<div class="itos_btn1" id="check_net_confirm"><a href="javascript:;">好的</a></div>
		<div class="its_close_btn"></div>
    </div>
	<!-- // 电话弹层 -->
	
	<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_hotquestion_list_v1.0.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
	<%@ include file="/views/pv.jsp"%>
	<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>