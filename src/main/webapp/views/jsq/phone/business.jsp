<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<meta name="apple-mobile-web-app-title" content="搜狐焦点房产">
<!-- 保存到主屏默认的标题 -->
<title>商业贷款计算器最新2014_个人住房贷款计算器 -搜狐焦点</title>
<%@ include file="/views/icon.jsp"%>
<meta name="Keywords" content="个人住房贷款计算器,商业贷款计算器">
<meta name="Description"
	content="搜狐焦点网为购房者提供2014最新个人商业贷款计算器，按照贷款总额、按揭年数、银行利率等方式计算出等额本息、等额本金的贷款还款额度。 ">
<link rel="stylesheet" type="text/css"
	href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link
	href="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/css/phone_calculator_v1.0.css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="http://192.168.242.44/sceapp/focus_static/wap/common/css/swiper_v1.0.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/snippets/phone_common_v1.0.js"></script>
<script type="text/javascript"
	src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/swiper_v2.1.min.js"></script>
	<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
<script>var sohunews = "${sohunews}";</script>
</head>
<body class="wap_home_all">
	<div class="wap_container">
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
					<jsp:param value="商业贷款计算器" name="page_title" />
				</jsp:include>
			</c:otherwise>
		</c:choose>
		<section class="calculator_main">
			<div class="swiper-container calculator_nav_swiper">
				<ul class="swiper-wrapper">
					<li class="swiper-slide" data-index="0" data-selected="true"><a href="${ctx}/tools/sydk/">商业贷款</a><em>◆</em></li>
			        <li class="swiper-slide" data-index="1"><a href="${ctx}/tools/gjjdk/" data-url="${ctx}/tools/gjjdk/">公积金贷款</a><em>◆</em></li>
			        <li class="swiper-slide" data-index="2"><a href="${ctx}/tools/zhdk/" data-url="${ctx}/tools/zhdk/">组合贷款</a><em>◆</em></li>
			        <li class="swiper-slide" data-index="3"><a href="${ctx}/tools/tqhd/" data-url="${ctx}/tools/tqhd/">提前还贷</a><em>◆</em></li>
			        <li class="swiper-slide" data-index="4"><a href="${ctx}/tools/gfnlpg/" data-url="${ctx}/tools/gfnlpg/">购房能力</a><em>◆</em></li>
			        <li class="swiper-slide" data-index="5"><a href="${ctx}/tools/sf/" data-url="${ctx}/tools/sf/">税费计算</a><em>◆</em></li>
				</ul>
				<div class="pagination"></div>
			</div>
			<div class="calculator_container">
				<div class="calculator_method">
					<h2>计算方式：</h2>
					<div class="calculator_method_tabs marginbottom20 input_skin1"
						id="calculator_method_box1">
						<span class="current" role="calculatorMethod_text">总价计算<em
							class="arrow"></em></span> <span class="second "
							role="calculatorMethod_text">单价计算<em class="arrow"></em></span>
					</div>
					<div class="calculator_method_container">
						<div class="calculator_method_nr_box" style="display: block;">
							<div class="daikuan_total clearfix marginbottom20">
								<div class="wy fr">万元</div>
								<div class="daikuan_total_inputbox input_skin1">
									<div class="daikuan_total_input_father">
										<input type="tel" role="text-number"
											class="daikuan_total_input" id="total_loan" placeholder="0"
											maxlength="5" />
									</div>
									<span>贷款总额：</span>
								</div>
								<div class="error_msg" data-for="total_loan">输入项不能为空</div>
							</div>
							<div class="borderf2f2f2 marginbottom20"></div>
							<div
								class="daikuan_total_inputbox daikuan_total_inputbox2 input_skin1 marginbottom20">
								<div class="daikuan_total_input_father">
									<select class="daikuan_total_input daikuan_total_select"
										id="loan_time_select">
										<option data-val="12">1年(12期)</option>
										<option data-val="24">2年(24期)</option>
										<option data-val="36">3年(36期)</option>
										<option data-val="48">4年(48期)</option>
										<option data-val="60">5年(60期)</option>
										<option data-val="72">6年(72期)</option>
										<option data-val="84">7年(84期)</option>
										<option data-val="96">8年(96期)</option>
										<option data-val="108">9年(108期)</option>
										<option data-val="120">10年(120期)</option>
										<option data-val="132">11年(132期)</option>
										<option data-val="144">12年(144期)</option>
										<option data-val="156">13年(156期)</option>
										<option data-val="168">14年(168期)</option>
										<option data-val="180">15年(180期)</option>
										<option data-val="192">16年(192期)</option>
										<option data-val="204">17年(204期)</option>
										<option data-val="216">18年(216期)</option>
										<option data-val="228">19年(228期)</option>
										<option data-val="240" selected>20年(240期)</option>
										<option data-val="300">25年(300期)</option>
										<option data-val="360">30年(360期)</option>
									</select> <em class="arrow"></em>
								</div>
								<span>按揭年数：</span>
							</div>
							<div class="daikuan_total clearfix marginbottom20">
								<div class="fr bank_rate">
									<span class="percentage">%</span><span class="set_rate"
										role="setRate_do">设置</span>
								</div>
								<div
									class="daikuan_total_inputbox daikuan_total_inputbox3 input_skin1">
									<div class="daikuan_total_input_father">
										<input type="number" role="text-number"
											class="daikuan_total_input" id="rate" placeholder="0"
											maxlength="9" value="6.55" data-value="6.55" />
									</div>
									<span>银行利率：</span>
								</div>
								<div class="error_msg" data-for="rate">输入项不能为空</div>
							</div>
							<div class="set_rate_container set_rate_boxs" style="display:;">
								<!-- 初始隐藏 -->
								<em class="arrow"></em>
								<div class="set_rate_nr clearfix">
									<div class="set_rate_nr_l fl">12年7月6日：</div>
									<ul class="set_rate_nr_r clearfix" data-type="total_price">
										<li class="current" data-date="120706" data-discount="1">基准利率</li>
										<li data-date="120706" data-discount="0.9">优惠10%</li>
										<li data-date="120706" data-discount="1.05">上浮5%</li>
										<li data-date="120706" data-discount="1.1">上浮10%</li>
									</ul>
								</div>
								<div class="set_rate_nr clearfix">
									<div class="set_rate_nr_l fl">12年6月8日：</div>
									<ul class="set_rate_nr_r clearfix" data-type="total_price">
										<li data-date="120608" data-discount="1">基准利率</li>
										<li data-date="120608" data-discount="0.9">优惠10%</li>
										<li data-date="120608" data-discount="1.05">上浮5%</li>
										<li data-date="120608" data-discount="1.1">上浮10%</li>
									</ul>
								</div>
							</div>
							<div class="borderf2f2f2 margintop20 marginbottom20"></div>
						</div>
						<div class="calculator_method_nr_box" style="display:;">
							<div class="daikuan_total daikuan_danjia clearfix marginbottom20">
								<div class="wy fr">元/平方米</div>
								<div class="daikuan_total_inputbox input_skin1">
									<div class="daikuan_total_input_father">
										<input type="tel" role="text-number"
											class="daikuan_total_input" id="house_price" placeholder="0"
											maxlength="9" max="10000" min="0" />
									</div>
									<span>单价：</span>
								</div>
								<div class="error_msg" data-for="house_price">输入项不能为空</div>
							</div>
							<div class="daikuan_total daikuan_danjia clearfix marginbottom20">
								<div class="wy fr">平方米</div>
								<div class="daikuan_total_inputbox input_skin1">
									<div class="daikuan_total_input_father">
										<input type="tel" role="text-number"
											class="daikuan_total_input" id="house_size" placeholder="0"
											maxlength="9" max="10000" min="0" />
									</div>
									<span>面积：</span>
								</div>
								<div class="error_msg" data-for="house_size">输入项不能为空</div>
							</div>
							<div class="borderf2f2f2 marginbottom20"></div>
							<div
								class="daikuan_total_inputbox daikuan_total_inputbox2 input_skin1 marginbottom20">
								<div class="daikuan_total_input_father">
									<select class="daikuan_total_input daikuan_total_select"
										id="loan_percent">
										<option data-val="20%">2成</option>
										<option data-val="30%">3成</option>
										<option data-val="40%">4成</option>
										<option data-val="50%">5成</option>
										<option data-val="60%">6成</option>
										<option data-val="70%" selected>7成</option>
										<option data-val="80%">8成</option>
										<option data-val="90%">9成</option>
									</select> <em class="arrow"></em>
								</div>
								<span>按揭成数：</span>
							</div>
							<div
								class="daikuan_total_inputbox daikuan_total_inputbox2 input_skin1 marginbottom20">
								<div class="daikuan_total_input_father">
									<select class="daikuan_total_input daikuan_total_select"
										id="unit_pirce_loan_time_select">
										<option data-val="12">1年(12期)</option>
										<option data-val="24">2年(24期)</option>
										<option data-val="36">3年(36期)</option>
										<option data-val="48">4年(48期)</option>
										<option data-val="60">5年(60期)</option>
										<option data-val="72">6年(72期)</option>
										<option data-val="84">7年(84期)</option>
										<option data-val="96">8年(96期)</option>
										<option data-val="108">9年(108期)</option>
										<option data-val="120">10年(120期)</option>
										<option data-val="132">11年(132期)</option>
										<option data-val="144">12年(144期)</option>
										<option data-val="156">13年(156期)</option>
										<option data-val="168">14年(168期)</option>
										<option data-val="180">15年(180期)</option>
										<option data-val="192">16年(192期)</option>
										<option data-val="204">17年(204期)</option>
										<option data-val="216">18年(216期)</option>
										<option data-val="228">19年(228期)</option>
										<option data-val="240" selected>20年(240期)</option>
										<option data-val="300">25年(300期)</option>
										<option data-val="360">30年(360期)</option>
									</select> <em class="arrow"></em>
								</div>
								<span>按揭年数：</span>
							</div>
							<div class="daikuan_total clearfix marginbottom20">
								<div class="fr bank_rate">
									<span class="percentage">%</span><span class="set_rate"
										role="setRate_do" id="config">设置</span>
								</div>
								<div
									class="daikuan_total_inputbox daikuan_total_inputbox3 input_skin1">
									<div class="daikuan_total_input_father">
										<input type="number" role="text-number"
											class="daikuan_total_input" id="unit_price_rate"
											placeholder="0" maxlength="9" max="10000" min="0"
											value="6.55" data-value="6.55" />
									</div>
									<span>银行利率：</span>
								</div>
								<div class="error_msg" data-for="unit_price_rate">输入项不能为空</div>
							</div>
							<div class="set_rate_container set_rate_boxs"
								style="display: none;">
								<!-- 初始隐藏 -->
								<em class="arrow"></em>
								<div class="set_rate_nr clearfix">
									<div class="set_rate_nr_l fl">12年7月6日：</div>
									<ul class="set_rate_nr_r clearfix" data-type="unit_price">
										<li class="current" data-date="120706" data-discount="1">基准利率</li>
										<li data-date="120706" data-discount="0.9">优惠10%</li>
										<li data-date="120706" data-discount="1.05">上浮5%</li>
										<li data-date="120706" data-discount="1.1">上浮10%</li>
									</ul>
								</div>
								<div class="set_rate_nr clearfix">
									<div class="set_rate_nr_l fl">12年6月8日：</div>
									<ul class="set_rate_nr_r clearfix" data-type="unit_price">
										<li data-date="120608" data-discount="1">基准利率</li>
										<li data-date="120608" data-discount="0.9">优惠10%</li>
										<li data-date="120608" data-discount="1.05">上浮5%</li>
										<li data-date="120608" data-discount="1.1">上浮10%</li>
									</ul>
								</div>
							</div>
							<div class="borderf2f2f2 margintop20 marginbottom20"></div>

						</div>
					</div>

					<div id="repayment_method1">
						<h2>还款方式：</h2>
						<div class="calculator_method_tabs marginbottom20 input_skin1">
							<span class="son_tab current" data-method="same_rate"
								role="calculatorMethodTab_text">等额本息<em class="arrow"></em></span>
							<span class="son_tab second" data-method="same_money"
								role="calculatorMethodTab_text">等额本金<em class="arrow"></em></span>
						</div>
						<div id="repayment_method1_nr"
							class="repayment_method1_nr father_box">
							<div class="son" style="display: block;">每月还款额固定，所还总利息较多，适合收入稳定者。</div>
							<div class="son">每月还款额递减，所还总利息较低，前期还款额较大。</div>
						</div>
					</div>

					<div class="borderf2f2f2 margintop20 marginbottom20"></div>
					<div class="calculator_btn_container">
						<div class="calculator_btn_box">
							<span class="calculator_btn" id="start_calculate"
								data-method="same_rate" role="start_calculator_btn">开始计算</span>
							<div role="calculator_btn_tip" class="calculator_btn_tip_wrapper">
								<em class="its_say"></em>
							</div>

						</div>
					</div>
					<div class="set_rate_container calculator_btn_nr"
						id="calculator_btn_tip1" style="display:;">
						<!-- 初始隐藏 -->
						<em class="arrow"></em>
						<p>以购买的产权住房为抵押与银行进行借贷，比公积金贷款还的多。最长贷款年限30年，且还款年限不得超过65岁。</p>
					</div>

					<div class="calculator_result_container"
						id="calculator_result_container1">
						<h2>计算结果</h2>
						<div class="calculator_result_tab_box" style="display:block;" data-title="等额本息—计算结果">
							<div class="clearfix calculator_result_nr display_none" data-type="first_pay">
								<span class="fl calculator_result_tit">房屋总价：</span>
								<div class="calculator_result_nr_p" data-id="house_total_price">0元</div>
							</div>
							<div class="clearfix calculator_result_nr">
								<span class="fl calculator_result_tit">贷款总额：</span>
								<div class="calculator_result_nr_p" id="total_loan_text">0元</div>
							</div>
							<div class="clearfix calculator_result_nr">
								<span class="fl calculator_result_tit">还款总额：</span>
								<div class="calculator_result_nr_p" id="total_repay_text">0元</div>
							</div>
							<div class="clearfix calculator_result_nr">
								<span class="fl calculator_result_tit">支付利息：</span>
								<div class="calculator_result_nr_p" id="interest_text">0元</div>
							</div>

							<div class="border606060 marginbottom20"></div>
							<div class="clearfix calculator_result_nr display_none"
								data-type="first_pay">
								<span class="fl calculator_result_tit">首期付款：</span>
								<div class="calculator_result_nr_p" id="first_pay_text">0元</div>
							</div>
							<div class="clearfix calculator_result_nr">
								<span class="fl calculator_result_tit">按揭年数：</span>
								<div class="calculator_result_nr_p" id="loan_time_text">20年(240期)</div>
							</div>
							<div class="clearfix calculator_result_nr">
								<span class="fl calculator_result_tit">月均还款：</span>
								<div class="calculator_result_nr_p" id="repay_permonth_text">0元</div>
							</div>
						</div>
						<div class="calculator_result_tab_box" data-title="等额本金—计算结果">
							<div class="clearfix calculator_result_nr display_none" data-type="first_pay">
								<span class="fl calculator_result_tit">房屋总价：</span>
								<div class="calculator_result_nr_p" data-id="house_total_price">0元</div>
							</div>
							<div class="clearfix calculator_result_nr">
								<span class="fl calculator_result_tit">贷款总额：</span>
								<div class="calculator_result_nr_p"
									id="average_capital_total_loan_text">0元</div>
							</div>
							<div class="clearfix calculator_result_nr">
								<span class="fl calculator_result_tit">还款总额：</span>
								<div class="calculator_result_nr_p"
									id="total_average_capital_repay_text">0元</div>
							</div>
							<div class="clearfix calculator_result_nr">
								<span class="fl calculator_result_tit">支付利息：</span>
								<div class="calculator_result_nr_p"
									id="average_capital_interest_text">0元</div>
							</div>

							<div class="border606060 marginbottom20"></div>

							<div class="clearfix calculator_result_nr display_none"
								data-type="first_pay">
								<span class="fl calculator_result_tit">首期付款：</span>
								<div class="calculator_result_nr_p" id="first_pay_text">0元</div>
							</div>
							<div class="clearfix calculator_result_nr">
								<span class="fl calculator_result_tit">按揭年数：</span>
								<div class="calculator_result_nr_p"
									id="average_capital_loan_time_text">20年(240期)</div>
							</div>
							<div class="clearfix calculator_result_nr">
								<span class="fl calculator_result_tit">首月还款：</span>
								<div class="calculator_result_nr_p" id="first_month_pay_text">0元</div>
							</div>
							<div class="clearfix calculator_result_nr">
								<span class="fl calculator_result_tit">末月还款：</span>
								<div class="calculator_result_nr_p" id="last_month_pay_text">0元</div>
							</div>
							<div class="border606060 marginbottom20"></div>

							<!-- 查看还款详情 —— 总价计算 -->
							<!--http://sohu.com?method=all&total=1000000&totaltime=25&rate=6.55%-->
							<div class="look_daikuan_detail">
								<a role="lookDaikuanDetail_text" href="javascript:;"
									id="view_detail_link" title="等额本金—查看还款详情"><em></em>查看还款详情</a>
							</div>
							<!-- 查看还款详情 —— 单价计算  <div class="look_daikuan_detail"><a href="/m/a.jsp?method=single&price=95000&area=130&percentage=70%&totaltime=25&rate=6.55%"></a></div> -->
						</div>
						<div class="border606060 marginbottom20"></div>

						<div class="calculator_result_nr">我们帮到您了么？</div>
						<div class="calculator_result_about clearfix marginbottom20"
							id="calculator_result_about1">
							<div class="fr its_btn" role="calculator_result_about">还款超出预算啦</div>
							<div class="fl its_btn width160 it_have_use"
								role="calculator_result_about">
								<span class="red1 it_have_users"><em>+1</em></span>位用户觉得有用
							</div>
						</div>
						<div
							class="set_rate_container calculator_btn_nr calculator_result_about_nr show"
							id="calculator_result_about_nr1">
							<!-- 初始隐藏 -->
							<div class="calculator_result_about_nr_box">
								<em class="arrow"></em>
								<p>您可以尝试调整按揭年数，或贷款总额再次进行计算。</p>
							</div>
							<div class="calculator_result_about_nr_box have_use_box">
								<em class="arrow"></em>
								<p>您可以添加计算器到主屏幕，或保存到浏览器书签中便于以后使用。</p>
							</div>
						</div>

					</div>

				</div>
			</div>


		</section>
	<c:if test="${!sohunews}">
		<!-- 面包屑 // -->
		<div class="link_boxs" style="margin-top: 0px;">
			<a href="${ctx}/${_city.cityPinyinAbbr}/">新房</a><span
				class="h_space1">&gt;</span>商业贷款计算器
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
					<li><a href="http://house.focus.cn/fangdaijisuanqi/shangyedaikuan/?cfrom=mobile" role="text"
						url="http://house.focus.cn/fangdaijisuanqi/shangyedaikuan/?cfrom=mobile" data-go="false">电脑版</a></li>
				</ul>
			</div>
		</footer>
		<!-- // 底部导航 -->
	</c:if>
		<div class="back2top" data-title="返回顶部"></div>

	</div>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/snippets/phone_commercial_loan_v1.0.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/snippets/filterinput.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/snippets/common.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
	<%@ include file="/views/pv.jsp"%>
</body>
</html>