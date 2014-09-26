<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, width=device-width" />
<meta name="format-detection" content="telephone=no,address=no,email=no" />
<meta name="keywords" content="税费计算器,购房买房税费计算器,新房税费计算器" />
<meta name="description"
	content="搜狐焦点网为购房者提供2014最新税费计算器，根据单价、面积计算出房屋总价、印刷税、公证费、契税、委托办理产权手续费、房屋买卖手续费等费用." />
<title>税费计算器_购房买房税费计算器_新房税费计算器-搜狐焦点</title>
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
</head>
<body class="SP_taxation">
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
				<jsp:param value="税费计算器" name="page_title" />
			</jsp:include>
		</c:otherwise>
	</c:choose>
	<div class="calculator_main">
		<div class="swiper-container calculator_nav_swiper">
			<ul class="swiper-wrapper">
					<li class="swiper-slide" data-index="0"><a href="${ctx}/tools/sydk/" data-url="${ctx}/tools/sydk/">商业贷款</a><em>◆</em></li>
			        <li class="swiper-slide" data-index="1"><a href="${ctx}/tools/gjjdk/" data-url="${ctx}/tools/gjjdk/">公积金贷款</a><em>◆</em></li>
			        <li class="swiper-slide" data-index="2"><a href="${ctx}/tools/zhdk/" data-url="${ctx}/tools/zhdk/">组合贷款</a><em>◆</em></li>
			        <li class="swiper-slide" data-index="3"><a href="${ctx}/tools/tqhd/" data-url="${ctx}/tools/tqhd/">提前还贷</a><em>◆</em></li>
			        <li class="swiper-slide" data-index="4"><a href="${ctx}/tools/gfnlpg/" data-url="${ctx}/tools/gfnlpg/">购房能力</a><em>◆</em></li>
			        <li class="swiper-slide" data-index="5" data-selected="true"><a href="${ctx}/tools/sf/" data-url="${ctx}/tools/sf/">税费计算</a><em>◆</em></li>
			</ul>
			<div class="pagination"></div>
		</div>
		<div class="S_main">
			<div class="S_cal">
				<div class="list_group">
					<div class="group">
						<div class="line">
							<div class="S_inputText1">
								<strong class="tit">单价：</strong>
								<div class="rtext">
									<input class="input1 font_em3" role="text-number" type="tel"
										placeholder="0" required max="1000000000" min="0" id="price"
										maxlength="12">
								</div>
							</div>
							<strong class="mompany">元/平方米</strong>
						</div>
						<div class="tips_error" style="display: none" id="priceErrorTips"></div>
						<div class="line">
							<div class="S_inputText1">
								<strong class="tit">面积：</strong>
								<div class="rtext">
									<input class="input1 font_em3" role="text-number" type="tel"
										placeholder="0" required id="area" max="1000000000" min="0">
								</div>
							</div>
							<strong class="mompany">平方米</strong>
						</div>
						<div class="tips_error" style="display: none" id="areaErrorTips"></div>
					</div>
				</div>
				<div class="btn">
					<a href="#" class="S_btn_1" node_type="counting">开始计算</a>
					<div class="P_1_1btn">
						<a href="#" class="S_icon_1" node_type="help"><em> </em></a>
						<div class="S_layer_arr" style="display: none" node_type="help1"></div>
					</div>
				</div>
			</div>
			<div class="S_layer_box P_1_2" style="display: none"
				node_type="help2">
				<div class="arr"></div>
				<div class="bd">
					<p class="text">新房交易时需要缴纳的税费。主要包含：契税、交易手续费等。</p>
				</div>
			</div>
			<div class="S_cal result" node-type="result">
				<h3 class="tit tit2">新房税费</h3>
				<div class="list_group2">
					<dl>
						<dt>房屋总价：</dt>
						<dd node-type="totalPrices">0元</dd>
					</dl>
					<dl>
						<dt>印花税：</dt>
						<dd node-type="flwshui">0元</dd>
					</dl>
					<dl>
						<dt>公证费：</dt>
						<dd node-type="FairFees">0元</dd>
					</dl>
					<dl>
						<dt>契税：</dt>
						<dd node-type="deedTax">0元</dd>
					</dl>
				</div>
				<div class="list_group2 nofirst">
					<dl>
						<dt>房屋买卖手续费：</dt>
						<dd node-type="poundage">0元</dd>
					</dl>
					<dl>
						<dt>委托办理产权手续费：</dt>
						<dd node-type="chanqu">0元</dd>
					</dl>
				</div>
				<div class="list_text1 list_group">
					<ul>
						<li>印花税：<br /> 一般为房屋总价的0.05%，订立合同时直接缴纳，通常跟随着首付一起交。
						</li>
						<li>契税：<br /> 一般为房屋总价的1.5%，通常跟随着首付一起交，由开发商代收。
						</li>
						<li>交易手续费：<br /> 一般是每平方米3元，交易签证时缴纳。
						</li>
						<li>公证费和权属登记费：<br /> 公证费为一般为房屋总价的0.3%，权属登记费为80元/套。
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<c:if test="${!sohunews}">
	<!-- 面包屑 // -->
	<div class="link_boxs" style="margin-top: 0px;">
		<a href="${ctx}/${_city.cityPinyinAbbr}/">新房</a><span class="h_space1">&gt;</span>税费计算器
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
				<%-- <li><a
					href="http://${_city.sourceDomainName}/common/modules/housemarket/housemarket3_sfjsq.php?cfrom=mobile"
					role="text"
					url="http://${_city.sourceDomainName}/common/modules/housemarket/housemarket3_sfjsq.php?cfrom=mobile">电脑版</a></li> --%>
					<li><a
					href="http://house.focus.cn/fangdaijisuanqi/shuifei/?cfrom=mobile"
					role="text" data-go="false" 
					url="http://house.focus.cn/fangdaijisuanqi/shuifei/?cfrom=mobile">电脑版</a></li>
					
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
	src="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/snippets/shuifei.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
<%@ include file="/views/pv.jsp"%>
</html>
