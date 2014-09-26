<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, width=device-width" />
<meta name="format-detection" content="telephone=no,address=no,email=no"/>
<meta name="keywords" content="税费计算器,购房买房税费计算器,新房税费计算器" />
<meta name="description" content="搜狐焦点网为购房者提供2014最新税费计算器，根据单价、面积计算出房屋总价、印刷税、公证费、契税、委托办理产权手续费、房屋买卖手续费等费用." />
<title>税费计算器_购房买房税费计算器_新房税费计算器-搜狐焦点</title>
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css"/>
<link href="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/css/common.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/css/pad.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
</head>
<body class="SP_taxation">
    <!-- 新版样式导航 -->
	<jsp:include page="/views/padHeader.jsp">
		<jsp:param value="税费计算器" name="page_title" />
	</jsp:include>
    <!--左侧导航-->
    <nav class="left_navigator">
        <a href="${ctx}/tools/sydk/" class="normal_nav" id="business_nav">商业贷款</a>
        <a href="${ctx}/tools/gjjdk/" class="normal_nav" id="fund_nav">公积金贷款</a>
        <a href="${ctx}/tools/zhdk/" class="normal_nav" id="combination_nav">组合贷款</a>
        <a href="${ctx}/tools/sf/" class="normal_nav selected_nav">税费计算</a>
        <a href="${ctx}/tools/tqhd/" class="normal_nav">提前还贷</a>
        <a href="${ctx}/tools/gfnlpg/" class="normal_nav">购房能力评估</a>
    </nav>

<div class="S_main">
	<div class="S_cal">
		<div class="list_group">
			<div class="group">
				<div class="line">
					<div class="S_inputText1"><strong class="tit">房屋单价：</strong>
						<div class="rtext">
							<input class="input1 font_em5" type="number" data-type="number" placeholder="0" required max="1000000000" min="0" id="price" maxlength="12" data-type="number">
						</div>
					</div>
					<strong class="mompany">元/平方米</strong></div>
				<div class="tips_error" style="display:none" id="priceErrorTips"></div>
				<div class="line">
					<div class="S_inputText1"><strong class="tit">房屋面积：</strong>
						<div class="rtext">
							<input class="input1 font_em5" type="number" data-type="number" placeholder="0" required id="area" max="1000000000" min="0">
						</div>
					</div>
					<strong class="mompany">平方米</strong></div>
				<div class="tips_error" style="display:none" id="areaErrorTips"></div>
			</div>
		</div>
		<div class="btn">
            <!--<button type="button" class="S_btn_1" node_type="counting">开始计算</button> 
            <button type="button" id="doubt_btn" class=""><span class="icon doubt"></span></button>
            <div class="S_layer_arr" style="display:none" node_type="help1"></div>-->
            <button type="button" class="S_btn_1"  node_type="counting">开始计算</button>
            <button type="button" id="doubt_btn"><span class="icon doubt"></span></button>
            <div class="tooltip_container" data-for="doubt_btn">
                <div class="tooltip_triangle calculate_tooltip_triangle"></div>
                <div class="tooltip">
                    <p>新房交易时需要缴纳的税费。主要包含：契税、交易手续费等。</p>
                </div>
            </div>
		</div>
	</div>
	<!--<div class="S_layer_box P_1_2" style="display:none" node_type="help2">
	    <div class="arr"></div>
	    <div class="bd">
		    <p class="text center">　　新房交易时需要缴纳的税费。主要包含：契税、交易手续费等。</p>
	    </div>
    </div>-->
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
		<div class="list_text1"><ul><li>印花税：<br>
一般为房屋总价的0.05%，订立合同时直接缴纳，通常跟随着首付一起交。</li><li>契税：<br>
一般为房屋总价的1.5%，通常跟随着首付一起交，由开发商代收。</li><li>交易手续费：<br />一般是每平方米3元，交易签证时缴纳。</li><li>公证费和权属登记费：<br>
公证费为一般为房屋总价的0.3%，权属登记费为80元/套。</li></ul></div>
		
	</div>
</div>
<!--footer-->
<section class="crumb">
	<div class="inner">
		<a class="inner_link" href="${ctx}/${_city.cityPinyinAbbr}/">新房</a>&gt;<a class="inner_link" href="${ctx}/tools/sf/">税费计算器</a>
	</div>
</section>
<footer class="clearfix">
	<span class="copyright">&copy;2014搜狐焦点</span>
	<div class="switch_version">
		<a href="javascript:;" class="version">Pad版</a>
		<div class="version_list">
			<a role="touch_bg" href="javascript:;" class="phone">手机版</a>
			<a role="touch_bg" href="javascript:;" class="ipad current">Pad版</a>
			<a role="touch_bg" href="http://house.focus.cn/fangdaijisuanqi/shuifei/?cfrom=mobile" class="pc">电脑版</a>
			<%-- <a role="touch_bg" href="http://${_city.sourceDomainName}/common/modules/housemarket/housemarket3_sfjsq.php?cfrom=mobile" class="pc">电脑版</a> --%>
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
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/snippets/shuifei.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
<%@ include file="/views/pv.jsp"%>
</html>
