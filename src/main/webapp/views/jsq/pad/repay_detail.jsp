<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	<meta content="telephone=no" name="format-detection" />
    <meta name="apple-mobile-web-app-title" content="房贷计算器-还款详情-搜狐焦点网" />
    <meta name="keywords" content="房贷计算器,等额本金还款详情"/>
    <meta name="description" content="商业贷款计算器，搜狐焦点网商业贷款计算器为购房者提供2014最新商业贷款计算工具，根据购房面积，贷款额度，贷款利率，贷款年限等条件在线计算出还款金额相关信息。" />
    <title>房贷计算器 -还款详情 -搜狐焦点网</title>
    <link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css"/>
    <link href="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/css/common.css" rel="stylesheet" />
    <link href="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/css/repay_detail.css" rel="stylesheet" />
    <link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
    <script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
</head>
<body>
    <!-- 新版导航开始 -->
	<header class="top-nav-new page-list-header clearfix">
	   <c:if test="${returnFlag=='0' }">
	   <c:if test="${source=='0' }"><a href="${ctx}/tools/sydk/" class="back fl"></a></c:if>
	   <c:if test="${source=='1' }"><a href="${ctx}/tools/gjjdk/" class="back fl"></a></c:if>
	   <c:if test="${source=='2' }"><a href="${ctx}/tools/zhdk/" class="back fl"></a></c:if>
	   </c:if>
		<c:if test="${returnFlag=='1' }">
		<a href="${ctx }/${_city.cityPinyinAbbr }/" class="back fl">首页</a>
		</c:if>
		<p><h1>还款细节</h1></p>
		
		<a href="javascript:;" class="nav-btn" role="main_menu" role-addclass="its_open"
			data-title="展开菜单">导航<i></i></a>
		<%@include file="/views/padNav.jsp" %>
	</header>
    
    
    <!--左侧导航-->
    <nav class="left_navigator">
        <a href="${ctx}/tools/sydk/" class="normal_nav selected_nav" id="business_nav">商业贷款</a>
        <a href="${ctx}/tools/gjjdk/" class="normal_nav" id="fund_nav">公积金贷款</a>
        <a href="${ctx}/tools/zhdk/" class="normal_nav" id="combination_nav">组合贷款</a>
        <a href="${ctx}/tools/sf/" class="normal_nav">税费计算</a>
        <a href="${ctx}/tools/tqhd/" class="normal_nav">提前还贷</a>
        <a href="${ctx}/tools/gfnlpg/" class="normal_nav">购房能力评估</a>
    </nav>
    <!--详情-->
    <div>
    <section class="prepay_detail_container">
        <p class="detail_title">还款详情</p>
        <div class="clearfix detail_list_container">
            <p class="loan_year"><span class="big_circle"></span><span class="loan_year_text">20年(共计还款240期)</span></p>
            <ul class="repay_list">
                
            </ul>
            <p class="end_loan"><span class="big_circle"></span><span class="loan_year_text">恭喜您, 房奴的生活结束了!</span></p>
        </div>
    </section>
        </div>
    <!--footer-->
    <section class="crumb">
		<div class="inner">
			<a class="inner_link" href="${ctx}/${_city.cityPinyinAbbr}/">新房</a>&gt;<a  class="inner_link" id="calculator_type" href="${ctx}/tools/gjjdk/">公积金贷款计算器</a>&gt;<a class="inner_link" href="javascript:;">还款详情</a>
		</div>
	</section>
    <footer class="clearfix">
		<span class="copyright">&copy;2014搜狐焦点</span>
		<div class="switch_version">
			<a href="javascript:;" class="version">Pad版</a>
			<div class="version_list" style="top: -103px;">
				<a role="touch_bg" href="javascript:;" class="phone">手机版</a>
				<a role="touch_bg" href="javascript:;" class="ipad current">Pad版</a>
				<!-- <a role="touch_bg" href="javascript:;" class="pc">电脑版</a> -->
			</div>
		</div>
		<a href="javascript:;" id="to_top" style="display: inline;">返回顶部</a>
	</footer>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/snippets/repay_detail.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
    <%@ include file="/views/pv.jsp"%>
</body>
</html>