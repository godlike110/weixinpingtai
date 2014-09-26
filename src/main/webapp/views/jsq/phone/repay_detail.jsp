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
    <link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
    <link href="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/css/phone_calculator_v1.0.css" rel="stylesheet" />
    <link href="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/css/repay_detail.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/swiper_v1.0.css">
    <link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
    <script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
	<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/snippets/phone_common_v1.0.js"></script>
	<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/swiper_v2.1.min.js"></script>
	<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
</head>
<body class="wap_home_all">
    <div class="wap_container">		
		<!-- 新版搜索导航样式 -->
		<header class="top-nav-new list-page-header clearfix">
		<c:choose>
			<c:when test="${sohunews}">
				
				<a href="javascript:history.back();" class="go_back" data-title="返回上一级"></a>
			</c:when>
			<c:when  test="${returnFlag=='1' }">
				<a href="${ctx }/${_city.cityPinyinAbbr }/" class="go_back" data-title="返回首页"></a>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${source=='0' }"><a href="${ctx}/tools/sydk/" class="go_back" data-title="返回上一级"></a></c:when>
					<c:when test="${source=='1' }"><a href="${ctx}/tools/gjjdk/" class="go_back" data-title="返回上一级"></a></c:when>
					<c:when test="${source=='2' }"><a href="${ctx}/tools/zhdk/" class="go_back" data-title="返回上一级"></a></c:when>
					<c:otherwise><a href="${ctx}/tools/sydk/" class="go_back" data-title="返回上一级"></a></c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
		<p><h1>还款详情</h1></p>
		<div class="header_tool">
			<a href="javascript:;" class="nav-btn" role="main_menu" role-addclass="its_open" data-title="展开菜单">导航<i></i></a>
		</div>
		<%@include file="/views/phoneNav.jsp" %>
		</header>
		
		<c:if test="${sohunews }"><c:set var="ctx" value="/sohunews"/></c:if>

        <section class="calculator_main">
			<div class="swiper-container calculator_nav_swiper">
				<ul class="swiper-wrapper">
					<li class="swiper-slide" data-index="0"><a href="${ctx}/tools/sydk/" data-url="${ctx}/tools/sydk/">商业贷款</a><em>◆</em></li>
			        <li class="swiper-slide" data-index="1"><a href="${ctx}/tools/gjjdk/" data-url="${ctx}/tools/gjjdk/">公积金贷款</a><em>◆</em></li>
			        <li class="swiper-slide" data-index="2"><a href="${ctx}/tools/zhdk/" data-url="${ctx}/tools/zhdk/">组合贷款</a><em>◆</em></li>
			        <li class="swiper-slide" data-index="3"><a href="${ctx}/tools/tqhd/" data-url="${ctx}/tools/tqhd/">提前还贷</a><em>◆</em></li>
			        <li class="swiper-slide" data-index="4"><a href="${ctx}/tools/gfnlpg/" data-url="${ctx}/tools/gfnlpg/">购房能力</a><em>◆</em></li>
			        <li class="swiper-slide" data-index="5"><a href="${ctx}/tools/sf/" data-url="${ctx}/tools/sf/">税费计算</a><em>◆</em></li>
				</ul>
				<div class="pagination"></div>
			</div>

            <!--详情-->
            <section class="prepay_detail_container">
                <p class="detail_title">还款详情</p>
                <div class="clearfix detail_list_container">
                    <p class="loan_year"><span class="big_circle"></span><span class="loan_year_text">20年 ( 共计还款240期 )</span></p>
                    <ul class="repay_list">
                        
                    </ul>
                    <p class="end_loan"><span class="big_circle"></span><span class="loan_year_text">恭喜您, 房奴的生活结束了!</span></p>
                </div>
            </section>
        </section>
	<c:if test="${!sohunews }">
        <!-- 面包屑 // -->
		<div class="link_boxs" style="margin-top: 0px;">
			<a href="${ctx}/${_city.cityPinyinAbbr}/">新房</a><span class="h_space1">&gt;</span>
			<c:if test="${from=='0' }"><a id="calculator_type" href="${ctx}/tools/sydk/">商业贷款</a></c:if>
			<c:if test="${from=='1' }"><a id="calculator_type" href="${ctx}/tools/gjjdk/">公积金贷款计算器</a></c:if>
			<c:if test="${from=='2' }"><a id="calculator_type" href="${ctx}/tools/zhdk/">组合贷款</a></c:if>
			
			<span class="h_space1">&gt;</span>还款详情
		</div>
		<!-- // 面包屑 -->
		
		<!-- 底部导航 // -->
		<footer class="foot_nav" style="margin-top:;">
			<div class="foot_nav_box1">
				<a href="#" class="first current" role="text" url="">新房</a>
				<a href="#" class="last" role="text" url="">二手房</a>
			</div>
			<div class="foot_nav_box2">
				<span class="wap_version" role="wap_version_menu">手机版</span>
				<ul class="nav_box_ul1 wap_version_menu_ul1">
					<li><a href="#" role="text" url="" class="current">手机版</a></li>
					<!-- <li><a href="#" role="text" url="">电脑版</a></li> -->
				</ul>
			</div>
		</footer>
		<!-- // 底部导航 -->
	</c:if>	
		<div class="back2top" data-title="返回顶部"></div>
    </div>
    
    <script src="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/snippets/common.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/jsq/phone/snippets/repay_detail.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
    <%@ include file="/views/pv.jsp"%>
</body>
</html>