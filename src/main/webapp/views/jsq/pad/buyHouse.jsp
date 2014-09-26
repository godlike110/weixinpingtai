<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, width=device-width" />
<meta name="format-detection" content="telephone=no,address=no,email=no"/>
<meta name="keywords" content="购房贷款计算器,购房能力评估计算器最新2014" />
<meta name="description" content="搜狐焦点网为购房者提供2014最新购房能力评估计算器，根据您购房资金、贷款年限、家庭的月收入，每月用于购房支出、购房面积、购房城市区域等计算出您可购买的房屋总价和单价." />
<title>购房贷款计算器_购房能力评估计算器最新2014-搜狐焦点</title>
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css"/>
<link href="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/css/common.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/css/pad.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
</head>
<body class="SP_buyHouse">
	<!-- 新版样式导航 -->
	<jsp:include page="/views/padHeader.jsp">
		<jsp:param value="购房能力评估" name="page_title" />
	</jsp:include>

	<!--左侧导航-->
	<nav class="left_navigator">
		<a href="${ctx}/tools/sydk/" class="normal_nav" id="business_nav">商业贷款</a>
		<a href="${ctx}/tools/gjjdk/" class="normal_nav" id="fund_nav">公积金贷款</a>
		<a href="${ctx}/tools/zhdk/" class="normal_nav" id="combination_nav">组合贷款</a>
		<a href="${ctx}/tools/sf/" class="normal_nav">税费计算</a> <a
			href="${ctx}/tools/tqhd/" class="normal_nav">提前还贷</a> <a
			href="${ctx}/tools/gfnlpg/" class="normal_nav selected_nav">购房能力评估</a>
	</nav>
	<!--计算器内容-->
	<div class="S_main">
		<div class="S_cal">
			<div class="list_group">
				<div class="group">
					<div class="line">
						<div class="S_inputText1">
							<strong class="tit">购房资金：</strong>
							<div class="rtext">
								<input class="input1 font_em5" type="number" data-type="number"
									maxlength="5" placeholder="0" required max="100000" min="0"
									id="buyCons">
							</div>
						</div>
						<strong class="mompany">万元</strong>
					</div>
					<div class="tips">可用于购房的现金、存款、有价证券和可以筹措到的资金总和</div>
					<div class="tips_error" style="display: none" id="buyConsErrorTips"></div>
					<div class="line select">
						<div class="S_inputText1">
							<strong class="tit">期望贷款年限：</strong>
							<div class="rtext">
								<select class="select1 font_em7" id="years">
								</select> <span class="icon select_icon"></span>
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
								<input class="input1 font_em6" type="number" data-type="number"
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
								<input class="input1 font_em9" type="number" data-type="number"
									placeholder="0" required max="1000000000" min="0" id="payManay">
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
								<input class="input1 font_em7" type="number" data-type="number"
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
								</select> <span class="icon select_icon"></span>
							</div>
						</div>
						<strong class="mompany"></strong>
					</div>
					<div class="line select">
						<div class="S_inputText1">
							<strong class="tit">期望购房区域：</strong>
							<div class="rtext">
								<select class="select1 font_em7" id="area">
								</select> <span class="icon select_icon"></span>
							</div>
						</div>
						<strong class="mompany"></strong>
					</div>
				</div>
			</div>
			<div class="btn">
				<button type="button" class="S_btn_1" id="submit">开始评估</button>
			</div>
		</div>
		<div class="S_cal result" id="result">
			<h3 class="tit tit2">评估结果</h3>
			<div class="list_group2">
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
	<!--footer-->
	<section class="crumb">
		<div class="inner">
			<a class="inner_link" href="${ctx}/${_city.cityPinyinAbbr}/">新房</a>&gt;<a
				class="inner_link" href="${ctx}/tools/gfnlpg/">购房能力评估</a>
		</div>
	</section>
	<footer class="clearfix">
		<span class="copyright">&copy;2014搜狐焦点</span>
		<div class="switch_version">
			<a href="javascript:;" class="version">Pad版</a>
			<div class="version_list">
				<a role="touch_bg" href="javascript:;" class="phone">手机版</a> <a
					role="touch_bg" href="javascript:;" class="ipad current">Pad版</a> 
				<a role="touch_bg"
					href="http://house.focus.cn/fangdaijisuanqi/goufangnenglipinggu/?cfrom=mobile"
					class="pc">电脑版</a>
				<%-- <a role="touch_bg"
					href="http://${_city.sourceDomainName}/common/modules/housemarket/housemarket3_gfnlpg.php?cfrom=mobile"
					class="pc">电脑版</a> --%>
			</div>
		</div>
		<a href="javascript:;" id="to_top" style="display: inline;">返回顶部</a>
	</footer>
</body>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script
	src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
<script
	src="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/snippets/common.js"></script>
<script
	src="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/snippets/filterinput.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/jsq/common/lib/lib-common.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/snippets/buyHouse.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
<%@ include file="/views/pv.jsp"%>
</html>
