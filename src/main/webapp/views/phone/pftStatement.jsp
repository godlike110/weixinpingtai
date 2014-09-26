<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection">
<title>${_city.cityName }看房团-${_city.cityName }搜狐焦点网</title>
<meta name="Keywords" content="${_city.cityName }看房团"/>
<meta name="Description" content="${_city.cityName }搜狐焦点看房团为大家设计最佳看房路线，带大家去看心仪的楼盘。同时，${_city.cityName }看房团还将不定期的邀请房地产专业人士陪同，为大家解读户型，指点装修问题。"/>
<%@ include file="/views/icon.jsp" %>
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css"/>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css"/>
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pft/css/component_phone.css"/>
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
<script type="text/javascript">
var city = ${city};
//var city = {"cityCoding":"010","cityId":1,"cityName":"北京","cityPinyin":"beijing","cityPinyinAbbr":"bj","domainName":"house.focus.cn/?cfrom=mobile","priority":1,"sourceDomainName":"house.focus.cn","type":"1"};
</script>
</head>
<body class="wap_hotquestion_all wap_baodian_info_all">
	<div class="wap_container">
			<!-- 新版样式导航 -->
		<jsp:include page="/views/phoneHeader.jsp">
			<jsp:param value="免责声明" name="page_title" />
		</jsp:include>
		
		<!-- 新闻正文页主要内容板块 // -->
		<section class="news_info_main">
			<div class="hotquestion_list_container">	
				<section class="dynamic_info_container" data-title="免责声明">
					<div class="dynamic_info_ask">
					
					<c:forEach items="${declarations }" var="declaration" varStatus="status">
						<h1 class="its_biaoti">${declaration.title }</h1>
						
						<div class="dynamic_info_nr">
						<p></p>
						
							<p>${declaration.content }</p>
<p></p>
						</div>
						</c:forEach>
					</div>
				</section>
				
			</div>
		</section>
		
		<!-- 面包屑 -->
		<div class="link_boxs">
		<p><a color="#aaa" href="${ctx }/${_city.cityPinyinAbbr}/"><font color="#aaa">新房</font></a><span class="h_space1">&gt;</span><a color="#aaa" href="${ctx }/${_city.cityPinyinAbbr}/pafangtuan/"><font color="#aaa">看房团</font></a><span class="h_space1">&gt;</span><a color="#aaa" href="${ctx }/${_city.cityPinyinAbbr}/pafangtuan/${lineId}"><font color="#aaa">看房详情</font></a><span class="h_space1">&gt;<a color="#aaa" href="javascript:;"><font color="#aaa">免责声明</font></a></p></div>
		
		<!-- 底部导航 // -->
		
		<footer class="clearfix">
			<span class="copyright">©2014搜狐焦点</span>
			<div class="switch_version">
				<a href="javascript:;" class="version">手机版</a>
				<div class="version_list">
					<a role="touch_bg" href="javascript:;" class="phone current">手机版</a>
					<c:choose>
					<c:when test="${_city.cityPinyinAbbr ==  'suzhou' }">
					<a role="touch_bg"  data-go="false" href="http://kanfangtuan.focus.cn/${_city.cityPinyinAbbr }/route_${lineId }?cfrom=mobile" class="pc">电脑版</a>
					</c:when>
					<c:otherwise>
					<a role="touch_bg" data-go="false" href="http://kanfangtuan.focus.cn/${_city.cityPinyin }/route_${lineId }?cfrom=mobile" class="pc">电脑版</a>
					</c:otherwise>
					</c:choose>
					
				</div>
			</div>
		</footer>
		<!-- // 底部导航 -->
		
		<div class="back2top" data-title="返回顶部" style="display: none;"></div>
		
	</div>
	<script type="text/javascript">var _focus_pv_id ="focus.wap.all";</script>
	<script type="text/javascript" src="http://js.sohu.com/mail/pv/pv_v210.js"></script>
	<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/pft/js/common.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
<%@ include file="/views/pv.jsp"%>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body></html>