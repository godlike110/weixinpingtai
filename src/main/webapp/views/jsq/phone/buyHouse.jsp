<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, width=device-width" />
<meta name="format-detection" content="telephone=no,address=no,email=no" />
<meta name="keywords" content="购房贷款计算器,购房能力评估计算器最新2014" />
<meta name="description"
	content="搜狐焦点网为购房者提供2014最新购房能力评估计算器，根据您购房资金、贷款年限、家庭的月收入，每月用于购房支出、购房面积、购房城市区域等计算出您可购买的房屋总价和单价." />
<title>购房贷款计算器_购房能力评估计算器最新2014-搜狐焦点</title>
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css"
	href="http://192.168.242.44/sceapp/focus_static/wap/common/css/swiper_v1.0.css">
<link rel="stylesheet" type="text/css"
	href="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/css/phone_calculator_v1.0.css">
<link rel="stylesheet" type="text/css"
	href="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/css/moblie.css">
	<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
	<script>var sohunews = "${sohunews}";</script>
</head>
<body class="SP_buyHouse">

	<!-- 新版样式导航 -->
	<c:choose>
		<c:when test="${sohunews}">
			<c:set var="ctx" value="/sohunews"/>
			<jsp:include page="../phone/phoneHeader.jsp">
				<jsp:param value="logo" name="page_title" />
			</jsp:include>
		</c:when>
		<c:otherwise>
			<jsp:include page="../phone/phoneHeader.jsp">
				<jsp:param value="购房能力评估" name="page_title" />
			</jsp:include>
		</c:otherwise>
	</c:choose>	
	<div class="calculator_main">

		<div class="swiper-container calculator_nav_swiper">
			<ul class="swiper-wrapper">
				<li class="swiper-slide" data-index="0"><a
					href="${ctx}/tools/sydk/" data-url="${ctx}/tools/sydk/">商业贷款</a><em>◆</em></li>
				<li class="swiper-slide" data-index="1"><a
					href="${ctx}/tools/gjjdk/" data-url="${ctx}/tools/gjjdk/">公积金贷款</a><em>◆</em></li>
				<li class="swiper-slide" data-index="2"><a
					href="${ctx}/tools/zhdk/" data-url="${ctx}/tools/zhdk/">组合贷款</a><em>◆</em></li>
				<li class="swiper-slide" data-index="3"><a
					href="${ctx}/tools/tqhd/" data-url="${ctx}/tools/tqhd/">提前还贷</a><em>◆</em></li>
				<li class="swiper-slide" data-index="4" data-selected="true"><a
					href="${ctx}/tools/gfnlpg/" data-url="${ctx}/tools/gfnlpg/">购房能力</a><em>◆</em></li>
				<li class="swiper-slide" data-index="5"><a
					href="${ctx}/tools/sf/" data-url="${ctx}/tools/sf/">税费计算</a><em>◆</em></li>
			</ul>
			<div class="pagination"></div>
		</div>
		<div class="S_main">
			<div class="S_cal">
				<div class="list_group">
					<div class="group">
						<div class="line">
							<div class="S_inputText1">
								<strong class="tit">购房资金：</strong>
								<div class="rtext">
									<input class="input1 font_em5" role="text-number" type="tel"
										maxlength="5" placeholder="0" required max="100000" min="0"
										id="buyCons">
								</div>
							</div>
							<strong class="mompany">万元</strong>
						</div>
						<div class="tips">可用于购房的现金、存款、有价证券和可以筹措到的资金总和</div>
						<div class="tips_error" style="display: none"
							id="buyConsErrorTips"></div>
						<div class="line select">
							<div class="S_inputText1">
								<strong class="tit">期望贷款年限：</strong>
								<div class="rtext">
									<select class="select1 font_em7" id="years">
									</select> <em class="arrow"></em>
								</div>
							</div>
							<strong class="mompany"></strong>
						</div>
					</div>
					<div class="group">
						<div class="line">
							<div class="S_inputText1">
								<strong class="tit">家庭月收入：</strong>
								<div class="rtext">
									<input class="input1 font_em6" role="text-number" type="tel"
										placeholder="0" required max="1000000000" min="0"
										id="familyIncome">
								</div>
							</div>
							<strong class="mompany">元</strong>
						</div>
						<div class="tips_error" style="display: none"
							id="familyIncomeErrorTips"></div>
						<div class="line">
							<div class="S_inputText1">
								<strong class="tit">每月用于购房支出：</strong>
								<div class="rtext">
									<input class="input1 font_em9" role="text-number" type="tel"
										placeholder="0" required max="1000000000" min="0"
										id="payManay">
								</div>
							</div>
							<strong class="mompany">元</strong>
						</div>
						<div class="tips">建议为家庭月收入40%左右</div>
						<div class="tips_error" style="display: none;"
							id="payManayErrorTips">每月用于购房支出大于家庭月收入</div>
					</div>
					<div class="group">
						<div class="line">
							<div class="S_inputText1">
								<strong class="tit">期望购房面积：</strong>
								<div class="rtext">
									<input class="input1 font_em7" role="text-number" type="tel"
										placeholder="0" required max="1000000000" min="0"
										id="houseSize">
								</div>
							</div>
							<strong class="mompany">平方米</strong>
						</div>
						<div class="tips_error" style="display: none"
							id="houseSizeErrorTips"></div>
						<div class="line select">
							<div class="S_inputText1">
								<strong class="tit">期望购房城市：</strong>
								<div class="rtext">
									<select class="select1 font_em7" id="city">
									</select> <em class="arrow"></em>
								</div>
							</div>
							<strong class="mompany"></strong>
						</div>
						<div class="line select">
							<div class="S_inputText1">
								<strong class="tit">期望购房区域：</strong>
								<div class="rtext">
									<select class="select1 font_em7" id="area">
									</select> <em class="arrow"></em>
								</div>
							</div>
							<strong class="mompany"></strong>
						</div>
					</div>
				</div>
				<div class="btn">
					<a href="#" class="S_btn_1" id="submit">开始评估</a>
				</div>
			</div>
			<div class="S_cal result" id="result">
				<h3 class="tit tit2">评估结果</h3>
				<div class="list_group2 list_group">
					<dl>
						<dt>可购买房屋总价：</dt>
						<dd id="payAll">0元</dd>
					</dl>
					<dl>
						<dt>可购买房屋单价：</dt>
						<dd id="paySize">0元/平方米</dd>
					</dl>
				</div>
				<div id="serchResult" style="display: none;">
					<h3 class="tit">
						为您找到<span id="titleResult">56</span>个推荐楼盘
					</h3>
					<div class="list_house">
						<ul id='houseList'></ul>
						<div class="more">
							<a href="#" target="_blank" id="listMore">更多房源信息</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<c:if test="${!sohunews}">	
	<!-- 面包屑 // -->
	<div class="link_boxs" style="margin-top: 0px;">
		<a href="${ctx}/${_city.cityPinyinAbbr}/">新房</a><span class="h_space1">&gt;</span>购房能力评估
	</div>
	<!-- // 面包屑 -->

	<!-- 底部导航 // -->
	<footer class="foot_nav" style="margin-top:;">
		<div class="foot_nav_box1">
			<a href="#" class="first current" role="text" url="">新房</a> <a
				href="#" class="last" role="text" url="">二手房</a>
		</div>
		<div class="foot_nav_box2">
			<span class="wap_version" role="wap_version_menu">手机版</span>
			<ul class="nav_box_ul1 wap_version_menu_ul1">
				<li><a href="#" role="text" url="" class="current">手机版</a></li>
				<li><a
					href="http://house.focus.cn/fangdaijisuanqi/goufangnenglipinggu/?cfrom=mobile"
					role="text" data-go="false" 
					url="http://house.focus.cn/fangdaijisuanqi/goufangnenglipinggu/?cfrom=mobile">电脑版</a></li>
				<%-- <li><a
					href="http://${_city.sourceDomainName}/common/modules/housemarket/housemarket3_gfnlpg.php?cfrom=mobile"
					role="text"
					url="http://${_city.sourceDomainName}/common/modules/housemarket/housemarket3_gfnlpg.php?cfrom=mobile">电脑版</a></li> --%>
			</ul>
		</div>
	</footer>
	<!-- // 底部导航 -->
	</c:if>
	<div class="back2top" data-title="返回顶部"></div>

</body>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/snippets/phone_common_v1.0.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/swiper_v2.1.min.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/snippets/common.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/snippets/filterinput.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/jsq/common/lib/lib-common.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/snippets/buyHouse.js"></script>
<script
	src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
<%@ include file="/views/pv.jsp"%>
</html>
