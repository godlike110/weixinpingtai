<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	<meta content="telephone=no" name="format-detection" />
    <meta name="apple-mobile-web-app-title" content="组合贷款计算器-搜狐焦点网" />
    <meta name="keywords" content="组合贷款计算器,组合贷款计算器最新2014"/>
    <meta name="description" content="搜狐焦点网为购房者提供2014最新组合贷款计算器，根据商业贷款总额、公积金贷款总额、按揭年数、银行利率等方式计算出等额本息、等额本金贷款还款额度" />
    <title>组合贷款计算器_组合贷款计算器最新2014-搜狐焦点</title>
    <link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css"/>
    <link href="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/css/common.css" rel="stylesheet" />
    <link href="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/css/combination_loan.css" rel="stylesheet" />
    <link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
    <script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
</head>
<body>
    <!-- 新版样式导航 -->
	<jsp:include page="/views/padHeader.jsp">
		<jsp:param value="组合贷款计算器" name="page_title" />
	</jsp:include>
    <!--左侧导航-->
    <nav class="left_navigator">
        <a href="${ctx}/tools/sydk/" class="normal_nav" id="business_nav">商业贷款</a>
        <a href="${ctx}/tools/gjjdk/" class="normal_nav" id="fund_nav">公积金贷款</a>
        <a href="${ctx}/tools/zhdk/" class="normal_nav selected_nav" id="combination_nav">组合贷款</a>
        <a href="${ctx}/tools/sf/" class="normal_nav">税费计算</a>
        <a href="${ctx}/tools/tqhd/" class="normal_nav">提前还贷</a>
        <a href="${ctx}/tools/gfnlpg/" class="normal_nav">购房能力评估</a>
    </nav>
    <!--计算器内容-->
    <section class="container_container">
        <!--计算器-->
        <div class="calculator_container">
            <!--商业贷款总额-->
            <div class="form_item no_margin_top">
                <span class="text_label">商业贷款总额：</span>
                <input role="touch_bg" type="number" id="total_business_loan" class="text" data-type="number" placeholder="0" maxlength="5" />
                <span>万元</span>
                <div class="error_msg" data-for="total_business_loan">输入项不能为空</div>
            </div>
            <!--公积金贷款总额-->
            <div class="form_item">
                <span class="text_label">公积金贷款总额：</span>
                <input role="touch_bg" type="number" id="total_fund_loan" class="text" data-type="number" placeholder="0" maxlength="5" />
                <span>万元</span>
                <div class="error_msg" data-for="total_fund_loan">输入项不能为空</div>
            </div>
            <hr class="form_seperator" />
            <!--按揭年数-->
            <div class="form_item">
                <span class="text_label">按揭年数：</span>
                <select id="loan_time_select" role="touch_bg" class="common_select">
                    <option value="12">1年(12期)</option>
			        <option value="24">2年(24期)</option>
			        <option value="36">3年(36期)</option>
			        <option value="48">4年(48期)</option>
			        <option value="60">5年(60期)</option>
			        <option value="72">6年(72期)</option>
			        <option value="84">7年(84期)</option>
			        <option value="96">8年(96期)</option>
                    <option value="108">9年(108期)</option>
			        <option value="120">10年(120期)</option>
			        <option value="132">11年(132期)</option>
			        <option value="144">12年(144期)</option>
			        <option value="156">13年(156期)</option>
			        <option value="168">14年(168期)</option>
			        <option value="180">15年(180期)</option>
			        <option value="192">16年(192期)</option>
                    <option value="204">17年(204期)</option>
			        <option value="216">18年(216期)</option>
			        <option value="228">19年(228期)</option>
			        <option value="240" selected="selected">20年(240期)</option>
			        <!--<option value="252">21年(252期)</option>
			        <option value="264">22年(264期)</option>
			        <option value="276">23年(276期)</option>
                    <option value="288">24年(288期)</option>-->
			        <option value="300">25年(300期)</option>
			        <!--<option value="312">26年(312期)</option>
			        <option value="324">27年(324期)</option>
			        <option value="336">28年(336期)</option>
			        <option value="348">29年(348期)</option>-->
			        <option value="360">30年(360期)</option>
                </select>
                <span class="icon select_icon"></span>
            </div>
            <div class="form_item">银行利率：</div>
            <!--银行利率-->            
            <div class="form_item">                
                <span class="text_label">公积金：</span>
                <input type="number" role="touch_bg" id="fund_rate" class="text small_text" value="4.50" data-value="4.50" data-type="number" />
                <span>%</span>
                <div class="error_msg" data-for="fund_rate">请输入正确的利率</div>
            </div>
            <div class="form_item">
                <span class="text_label">商业：</span>
                <input type="number" role="touch_bg" id="business_rate" class="text small_text" value="6.55" data-value="6.55" data-type="number" />
                <span>%</span>
                <button type="button" id="config">设置</button>
                <div class="error_msg" data-for="business_rate">请输入正确的利率</div>
            </div>
            <!--利率设置-->
            <div class="tooltip_container rate_tooltip_container" data-for="config">
                <div class="tooltip_triangle"></div>
                <div class="tooltip">
                    <div class="rate_container">
                        <span class="rate_time">12年7月6日：</span>
                        <div class="rate_btn_container">
                            <button type="button" class="rate_btn rate_btn_selected" data-date="120706" data-discount="1">基准利率</button>
                            <button type="button" class="rate_btn" data-date="120706" data-discount="0.9">优惠10%</button>
                            <button type="button" class="rate_btn" data-date="120706" data-discount="1.05">上浮5%</button>
                            <button type="button" class="rate_btn" data-date="120706" data-discount="1.1">上浮10%</button>
                        </div>
                    </div>
                    <div>
                        <span class="rate_time">12年6月8日：</span>
                        <div class="rate_btn_container">
                            <button type="button" class="rate_btn" data-date="120608" data-discount="1">基准利率</button>
                            <button type="button" class="rate_btn" data-date="120608" data-discount="0.9">优惠10%</button>
                            <button type="button" class="rate_btn" data-date="120608" data-discount="1.05">上浮5%</button>
                            <button type="button" class="rate_btn" data-date="120608" data-discount="1.1">上浮10%</button>
                        </div>
                    </div>
                </div>
            </div>
            <hr class="form_seperator" />
            <!--还款方式-->
            <div class="form_item">
                <p>还款方式：</p>
                <div class="margin_top_15">
                    <button role="touch_bg" type="button" data-name="repay_mode" id="average_interest" class="radio_button left_radio_button radio_button_checked">等额本息
                        <span class="icon triangle"></span>
                    </button>
                    <button role="touch_bg" type="button" data-name="repay_mode" id="average_capital" class="radio_button right_radio_button">等额本金
                        <span class="icon triangle"></span>
                    </button>                
                </div>
                <p class="hint_text">每月还款额固定，所还总利息较多，适合收入稳定者。 </p>
            </div>
            <hr class="form_seperator" />
            <!--开始计算-->
            <button type="button" class="start_calculate" id="start_calculate">开始计算</button>
            <button type="button" id="doubt_btn"><span class="icon doubt"></span></button>
            <div class="tooltip_container" data-for="doubt_btn">
                <div class="tooltip_triangle calculate_tooltip_triangle"></div>
                <div class="tooltip">
                    <p>公积金贷款不足以支付购房款时，不足部分可向银行同时申请商业性贷款。两种贷款执行各自的贷款利率，最长贷款年限30年。</p>
                </div>
            </div>
        </div>
        <!--计算结果-->
        <div class="result">
            <p class="result_title">计算结果</p>
            <div class="result_container">
				<dl class="clearfix">
					<dt class="result_left_label fl">贷款总额：</dt>
					<dd class="result_right_text fr" id="total_loan_text">0元</dd>
				</dl>
				<dl class="clearfix">
					<dt class="result_left_label fl">还款总额：</dt>
					<dd class="result_right_text fr" id="total_repay_text">0元</dd>
				</dl>
				<dl class="clearfix">
					<dt class="result_left_label fl">支付利息：</dt>
					<dd class="result_right_text fr" id="interest_text">0元</dd>
				</dl>
			</div>
            <hr class="seperator" />
            <div class="result_container">
                <dl class="clearfix display_none" data-price="unit_price">
					<dt class="result_left_label fl">首期付款：</dt>
					<dd class="result_right_text fr" id="first_pay_text">0元</dd>
				</dl>
				<dl class="clearfix">
					<dt class="result_left_label fl">按揭年数：</dt>
					<dd id="loan_time_text" class="result_right_text fr">20年(240期)</dd>
				</dl>
				<dl class="clearfix" data-type="average_interest">
					<dt class="result_left_label fl">月均还款：</dt>
					<dd class="result_right_text fr" id="repay_permonth_text">0元</dd>
				</dl>
                <dl class="clearfix display_none" data-type="average_capital">
					<dt class="result_left_label fl">首月还款：</dt>
					<dd class="result_right_text fr" id="first_month_pay_text">0元</dd>
				</dl>
                <dl class="clearfix display_none" data-type="average_capital">
					<dt class="result_left_label fl">末月还款：</dt>
					<dd class="result_right_text fr" id="last_month_pay_text">0元</dd>
				</dl>
			</div>
            <hr class="seperator" />
            <!--查看还款详情-->
            <div class="detail_container display_none">
                <a href="javascript:;" target="_blank" id="detail_btn"><span class="icon detail_icon"></span>查看还款详情</a>
                <hr class="seperator" />
            </div>
            <div class="result_container">
				<span class="result_left_label">我们帮到您了吗？</span>
			</div>
            <div class="margin_top_15">
				<button type="button" class="bottom_btn" id="useful_btn"><em><span></span><span class="helpful">+1</span></em>位用户觉得有用</button>
                <button type="button" class="bottom_btn" id="unuseful_btn">还款超出预算啦</button>
			</div>
            <div class="tooltip_container" data-for="useful_btn">
                <div class="tooltip_triangle bottom_triangle"></div>
                <div class="tooltip bottom_tooltip">
                    <p>您可以添加计算器到主屏幕，或保存到浏览器书签中便于以后使用。</p>
                </div>
            </div>    
            <div class="tooltip_container" data-for="unuseful_btn">
                <div class="tooltip_triangle bottom_unuseful_triangle"></div>
                <div class="tooltip bottom_tooltip">
                    <p>您可以尝试调整按揭年数，或贷款总额再次进行计算。</p>
                </div>
            </div>         
        </div>
    </section>
    <!--footer-->
    <section class="crumb">
		<div class="inner">
			<a class="inner_link" href="${ctx}/${_city.cityPinyinAbbr}/">新房</a>&gt;<a class="inner_link" href="${ctx}/tools/zhdk/">组合贷款计算器</a>
		</div>
	</section>
    <footer class="clearfix">
		<span class="copyright">&copy;2014搜狐焦点</span>
		<div class="switch_version">
			<a href="javascript:;" class="version">Pad版</a>
			<div class="version_list">
				<a role="touch_bg" href="javascript:;" class="phone">手机版</a>
				<a role="touch_bg" href="javascript:;" class="ipad current">Pad版</a>
				<a role="touch_bg" href="http://house.focus.cn/fangdaijisuanqi/zuhedaikuan/?cfrom=mobile" class="pc">电脑版</a>
				<%-- <a role="touch_bg" href="http://${_city.sourceDomainName}/common/modules/housemarket/housemarket3_debx.php?cfrom=mobile" class="pc">电脑版</a> --%>
			</div>
		</div>
		<a href="javascript:;" id="to_top" style="display: inline;">返回顶部</a>
	</footer>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/snippets/common.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/snippets/combination_loan.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/snippets/filterinput.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
    <%@ include file="/views/pv.jsp"%>
</body>
</html>