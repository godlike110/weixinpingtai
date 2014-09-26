<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, width=device-width" />
<meta name="format-detection" content="telephone=no,address=no,email=no"/>
<meta name="keywords" content="提前还款计算器,提前还贷计算器最新2014" />
<meta name="description" content="搜狐焦点网为购房者提供2014最新提前还贷计算器，根据贷款类型、贷款总额、原还款期限、银行利率、以及还款时间、还款方式，计算出下月起月还款额、节省利息支出等提前还贷款费用." />
<title>提前还款计算器_提前还贷计算器最新2014-搜狐焦点</title>
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css"/>
<link href="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/css/common.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/css/pad.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
</head>
<body class="SP_loanTerms">
    <!-- 新版样式导航 -->
	<jsp:include page="/views/padHeader.jsp">
		<jsp:param value="提前还贷计算器" name="page_title" />
	</jsp:include>
    <!--左侧导航-->
    <nav class="left_navigator">
        <a href="${ctx}/tools/sydk/" class="normal_nav" id="business_nav">商业贷款</a>
        <a href="${ctx}/tools/gjjdk/" class="normal_nav" id="fund_nav">公积金贷款</a>
        <a href="${ctx}/tools/zhdk/" class="normal_nav" id="combination_nav">组合贷款</a>
        <a href="${ctx}/tools/sf/" class="normal_nav">税费计算</a>
        <a href="${ctx}/tools/tqhd/" class="normal_nav selected_nav">提前还贷</a>
        <a href="${ctx}/tools/gfnlpg/" class="normal_nav">购房能力评估</a>
    </nav>
<div class="S_main"> 
	<!-- 计算器 -->
	<div class="S_cal">
		<div class="list_group">
			<h4>贷款类别：</h4>
			<div class="group">
				<div class="line">
					<ul class="S_tab_2">
						<li class="cur"><a href="javascript:;" node_data="0" node_type="actionType">商业贷款</a></li>
						<li><a href="javascript:;" node_data="1"  node_type="actionType">公积金贷款</a></li>
					</ul>
					<strong class="mompany"> </strong></div>
				<div class="line">
					<div class="S_inputText1"><strong class="tit">原贷款额：</strong>
						<div class="rtext">
							<input class="input1 font_em5" data-type="number" type="number" placeholder="0" required id="priceAll" maxlength="5" />
						</div>
					</div>
					<strong class="mompany">万元</strong>
				</div>
				<div class="tips_error" style="display:none" id="priceAllErrorTips"></div>
				<div class="line select">
					<div class="S_inputText1"><strong class="tit">原贷款年限：</strong>
						<div class="rtext">
							<select class="select1 font_em6" id="years">
							</select>
                            <span class="icon select_icon"></span>
						</div>
					</div>
					<strong class="mompany"></strong></div>
			</div>
			<h4>原贷款利率：</h4>
			<div class="group">
				<div class="line select P_loanTerms_1">
					<div class="S_inputText1">
						<div class="rtext">
							<select class="select1" id="danNianXian">
							</select>
                            <span class="icon select_icon"></span>
						</div>
					</div>
					<div class="mompany">
						<input class="input" type="number" data-type="number"  placeholder="0" required id="priCon">
						<strong>%</strong>
					</div>                    
				</div>
                <div class="tips_error" style="display: none;" id="priCon_error"></div>
			</div>
			<h4>提前还款方式：</h4>
			<div class="group P_2_1">
				<div class="line">
					<ul class="S_tab_2">
						<li class="noarr cur"><a href="javascript:;" node_type="firstloanTerms" node_data="0">一次提前还清</a></li>
						<li class="arr"><a href="javascript:;" node_type="firstloanTerms" node_data="1">部分提前还清</a></li>
					</ul>
					<strong class="mompany"> </strong></div>
				<div class="S_layer_box" id="loanTermsCons" style="display:none;">
					<div class="S_layer_arr"></div>
					<div class="bd L_form"> <strong class="tit">提前还款额：</strong>
						<input class="input" type="number" data-type="number" maxlength="5" placeholder="0" required id="pricePay">
						<strong class="yuan">万元</strong><em>不含当月应还款</em>
						<div class="tips_error" style="display:none" id="pricePayErrorTips"></div>
					</div>
				</div>
			</div>
			<div class="group">
				<div class="line">
					<div class="S_inputText1 S_inputText2" id="first_pay_time">
                        <strong class="tit">第一次还款时间：</strong>
						<select class="select1" id="firstYears">
						</select>
                        <span class="icon select_icon"></span>
                        <select class="select1" id="firstYearsMou">
						</select>
                        <span class="icon select_icon"></span>
					</div>
					<strong class="mompany"></strong> 
				</div>
                <div class="tips_error" style="display:none" id="first_pay_time_error">第一次还款时间晚于预计还款时间</div>
				<div class="line">
					<div class="S_inputText1 S_inputText2" id="predict_pay_time">
                        <strong class="tit">预计提前还款时间：</strong>
						<select class="select1" id="payYears">
						</select>
                        <span class="icon select_icon"></span>
                        <select class="select1" id="payYearsMou">
						</select>
                        <span class="icon select_icon"></span>
					</div>
					<strong class="mompany"></strong>
				</div>
                <div class="tips_error" style="display:none" id="predict_pay_time_error">预计提前还款时间大于原贷款最晚还款时间</div>
			</div>
		</div>
		<div class="btn">
            <button type="button" id="submit" class="S_btn_1">开始计算</button>
			<!--<div class="P_1_1btn"><a href="javascript:;" class="S_icon_1" node_type="help"><em> </em></a>
				<div class="S_layer_arr" style="display:none" node_type="help1"></div>
			</div>-->
            <button type="button" id="doubt_btn"><span class="icon doubt"></span></button>
            <div class="tooltip_container" data-for="doubt_btn">
                <div class="tooltip_triangle calculate_tooltip_triangle"></div>
                <div class="tooltip">
                    <p class="repayment_tooltip" >组合贷款的用户，提前还款时优先偿还商贷较为合算；<br />
				公积金或享受七折利率贷款的用户建议不必急于提前还款；<br />
				银行一般规定在的贷款发放满一年以后可申请提前还贷；</p>
                </div>
            </div>
		</div>
	</div>
	<!--<div class="S_layer_box P_1_2" style="display:none" node_type="help2">
		<div class="arr"></div>
		<div class="bd">
			<p class="text" style="padding-left:20%;">组合贷款用户，提前还款时优先偿还商贷较为合算；<br />
				公积金或享受七折利率贷款的用户建议不必急于提前还款；<br />
				银行一般规定在的贷款发放满一年以后可申请提前还贷；</p>
		</div>
	</div>-->
	<!-- /计算器 --> 
	<!-- 计算结果 -->
	<div class="S_cal result">
		<h3 class="tit">计算结果</h3>
		<ul class="S_tab_1"  id="sdnxorjshkTab" style="display:none">
			<li class="cur"><a href="javascript:;" data="0">缩短年限</a></li>
			<li><a href="javascript:;" data="1">减少月贷款</a></li>
		</ul>
		<div class="list_text1 P_2_2"  style="display:none" id="sdnxorjshkTabTip">
			<ul>
				<li>缩短还款年限，月还款额基本不变。</li>
			</ul>
		</div>
		<div class="list_group2">
			<dl>
				<dt>已还贷总额：</dt>
				<dd id='ResultPayeAll'>0元</dd>
			</dl>
		</div>
		<div class="list_group2 nofirst">
			<dl>
				<dt>原最后还款时间：</dt>
				<dd id="ResultLastPay"></dd>
			</dl>
			<dl>
				<dt>新最后还款时间：</dt>
				<dd id="ResultNewPayDate"></dd>
			</dl>
		</div>
		<div class="list_group2 nofirst">
			<dl>
				<dt>原每月还款额：</dt>
				<dd id="ResultFirstPay">0元</dd>
			</dl>
			<dl>
				<dt>该月一次还款额：</dt>
				<dd id="ResultNowPay">0元</dd>
			</dl>
			<dl>
				<dt>下月起还款额：</dt>
				<dd id="ResultNextPay">0元</dd>
			</dl>
		</div>
		<div class="list_group2 nofirst">
			<dl>
				<dt>节省利息支出：</dt>
				<dd id="ResultSaveLixi">0元</dd>
			</dl>
		</div>
	</div>
	<!-- /计算结果 --> 
</div>
<!--footer-->
<section class="crumb">
	<div class="inner">
		<a class="inner_link" href="${ctx}/${_city.cityPinyinAbbr}/">新房</a>&gt;<a class="inner_link" href="${ctx}/tools/tqhd/">提前还贷计算器</a>
	</div>
</section>
<footer class="clearfix">
	<span class="copyright">&copy;2014搜狐焦点</span>
	<div class="switch_version">
		<a href="javascript:;" class="version">Pad版</a>
		<div class="version_list">
			<a role="touch_bg" href="javascript:;" class="phone">手机版</a>
			<a role="touch_bg" href="javascript:;" class="ipad current">Pad版</a>
			<a role="touch_bg" href="http://house.focus.cn/fangdaijisuanqi/tiqianhuankuan/?cfrom=mobile" class="pc">电脑版</a>
			<%-- <a role="touch_bg" href="http://${_city.sourceDomainName}/common/modules/housemarket/housemarket3_tqhd.php?cfrom=mobile" class="pc">电脑版</a> --%>
		</div>
	</div>
	<a href="javascript:;" id="to_top" style="display: inline;">返回顶部</a>
</footer>
</body>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/snippets/common.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/snippets/filterinput.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/jsq/common/lib/lib-common.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/snippets/repayment.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
<%@ include file="/views/pv.jsp"%>
</html>
