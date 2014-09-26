<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta name="viewport" content="initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
<meta name="apple-touch-fullscreen" content="YES"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="format-detection" content="telephone=no">
<meta name="Keywords" content="${_city.cityName}楼盘, ${_city.cityName}房价, ${_city.cityName}新房">
<meta name="Description" content="搜狐焦点${_city.cityName}房产网为广大网友提供了最新的${_city.cityName}楼盘信息，最准确的${_city.cityName}房价情况和最及时的${_city.cityName}新房资讯等，是买房、购房首选网站。 ">
<title>【${_city.cityName}楼盘|${_city.cityName}房价|${_city.cityName}新房】-搜狐焦点${_city.cityName}房产</title>
<%@ include file="/views/icon.jsp" %>
<link href="http://192.168.242.44/sceapp/focus_static/wap/wb/pad/css/common.css" rel="stylesheet" type="text/css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/wb/pad/css/style.css" rel="stylesheet" type="text/css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/wb/pad/css/media.css" rel="stylesheet" type="text/css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/wb/common/css/swiper_v1.0.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css"/>
</head>

<body >
<div id="wrap_s">
<!-- 新版导航 -->
<header class="top-nav-new clearfix">
		<div class="header-left">
			<h1 class="logo-icon"></h1>
			<a href="${ctx}/city/select/" class="city-selector"
				data-url="${ctx}/city/select/">${_city.cityName}<i></i></a>
		</div>
		<div class="header-right clearfix">
			<div class="search-container">
				<input class="search-input" placeholder="输入楼盘名称/地点">
				<div class="search_Prompt" id="searchPop1">
					<div class="search_Prompt_c">
						<i class="arrow"></i>
						<dl name="hot">
							<dt class="In_the_search">大家都在搜</dt>
							<dd class="">
								<a href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____DZLP_/'>打折楼盘</a>
							</dd>
							<dd class="">
								<a href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____ZXKP_/'>最新开盘</a>
							</dd>
							<dd class="">
								<a href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____XHX_/'>小户型</a>
							</dd>
							<dd class="">
								<a href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____XF_/'>现房</a>
							</dd>
							<dd class="">
								<a href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____GYPB_/'>公园旁边</a>
							</dd>
							<dd class="">
								<a href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____DZJ_/'>低总价</a>
							</dd>
							<dd class="">
								<a href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____JYSQ_/'>教育社区</a>
							</dd>
						</dl>
						<h3 class="Search_in">你搜过的</h3>
						<ul name="history"></ul>
					</div>
				</div>
				<div class="search_Prompt" id="searchPop2">
					<div class="search_Prompt_c">
						<i class="arrow"></i>
						<ul name="lianxiang">

						</ul>
					</div>
				</div>
			</div>
			<div class="header-tools">
				<a href="#" class="search-btn olp-bg-search2"></a> <a
					href="${ctx}/static/appwap.html" class="app-btn"></a> 
					<a href="#" class="nav-btn" role="main_menu" role-addclass="its_open" data-title="展开菜单">导航<i></i></a>
			</div>
		</div>
		<%@include file="/views/padNav.jsp" %>
	</header>
<!-- 新版导航 -->


<section>
<div class="center1">
<div class="sh_con">
<div class="sh_con_wr5" id="wrapper_1">	
<div class="sh_con_wp"><!-- border_r_c -->
<!-- 焦点图区域开始 -->
${focus}
<!-- 焦点图区域结束 -->
<!-- 新房开始 -->
<div class="f_Table">
<div class="f_Table_nav1" name="xinfang">
<div class="f_th_w"><span class="f_th"><a href="javascript:void(0)" style="display: inline-block;"><h2 style="font-size: 20px;">新房</h2></a><i class="f_th_ico"></i></span></div>
<ul class="f_Table_nav_c1">
<c:if test="${''!=zhaofang}">
<li class="f_Table_nav_hov"><a href="javascript:void(0)" name="zhaofang" class="colorcb" style="display: inline-block;"><h3>找房</h3></a></li></c:if>
<c:if test="${''!=zuixinkaipan}">
<li><a href="javascript:void(0)" name="zuixkp" style="display: inline-block;"><h3>最新开盘</h3></a></li></c:if>
<c:if test="${''!=xiaohuxing}">
<li><a href="javascript:void(0)" name="xiaohx" style="display: inline-block;"><h3>小户型</h3></a></li></c:if>
<c:if test="${''!=dazheloupan}">
<li><a href="javascript:void(0)" name="dazyh" style="display: inline-block;"><h3>打折优惠</h3></a></li></c:if>
</ul>
</div>
<!-- 找房开始 -->
${zhaofang}
<!-- 找房结束 -->
<!-- 最新开盘开始 -->
${zuixinkaipan}
<!-- 最新开盘结束 -->
<!-- 小户型开始 -->
${xiaohuxing}
<!-- 小户型结束 -->
<!-- 打折优惠开始 -->
${dazheloupan}
<!-- 打折优惠结束 -->
</div>
<!-- 新房结束 -->
<!-- 看房团开始 -->
<c:if test="${''!=pft}">${pft}</c:if>
<!-- 看房团结束 -->
<!-- 焦点原创开始 -->
<div class="f_Table">
<div class="f_Table_nav3" name="jiaodyc">
<div class="f_th_w"><span class="f_th"><a href="javascript:void(0)" style="display: inline-block;"><h2 style="font-size: 20px;">焦点原创</h2></a><i class="f_th_ico"></i></span></div>
<ul class="f_Table_nav_c3">
<c:if test="${''!=baodian}">
<li class="f_Table_nav_hov"><a href="javascript:void(0)" name="goufbd" class="colorcb" style="display: inline-block;"><h3>购房宝典</h3></a></li></c:if>
<c:if test="${''!=daogou}">
<li><a href="javascript:void(0)" name="loupdg" style="display: inline-block;"><h3>楼盘导购</h3></a></li></c:if>
</ul>
</div>
<!-- 购房宝典开始 -->
${baodian}
<!-- 购房宝典结束 -->
<!-- 楼盘导购开始 -->
${daogou}
<!-- 楼盘导购结束 -->
</div>
<!-- 焦点原创结束 -->
</div>
</div>
<div class="sh_con_wl5" id="wrapper_2">
<div class="sh_con_wp">
<div class="f_Table_r">
<!-- 今日头条开始 -->
${news}
<!-- 今日头条结束 -->
<!-- 计算器开始 -->
<div class="f_Table_nav_r paddtop30">
<span class="f_th"><a href="javascript:void(0)"><h2 style="font-size: 20px;">房贷计算</h2></a><i class="f_th_ico"></i></span>
</div>
<div class="Mortgage">
<ul>
<li name="shangdaidaik"><a href="/tools/sydk/"><i class="Mortgage_cio1"></i><p>商贷贷款计算</p></a></li>
<li name="gongjjdaik"><a href="/tools/gjjdk/"><i class="Mortgage_cio2"></i><p>公积金贷款计算</p></a></li>
<li name="zuhedaik"><a href="/tools/zhdk/"><i class="Mortgage_cio3"></i><p>组合贷款计算</p></a></li>
<li name="shuifjs"><a href="/tools/sf/"><i class="Mortgage_cio4"></i><p>税费计算</p></a></li>
<li name="tiqhdjs"><a href="/tools/tqhd/"><i class="Mortgage_cio5"></i><p>提前还贷计算</p></a></li>
<li name="goufnlpg"><a href="/tools/gfnlpg/"><i class="Mortgage_cio6"></i><p>购房能力评估</p></a></li>
</ul>
</div>
<!-- 计算器结束 -->
</div>
</div>
</div>
</div>
</div>
</section>
<!-- 地理位置的定位 -->
<section name="prompt">
<div id="Prompt" class="Prompt" style="display:none;">
<div id="p_nogps" class="Prompt_c_w">
<div class="Prompt_c1">
<div class="Prompt_c1_th">
<i class="Prompt_c1_th_ico1"></i>定位失败！
</div>
<div class="Prompt_c1_p"> 请检查网络和GPS设置。</div>
</div>
</div>

<div id="p_nopermission" class="Prompt_c_w">
<div class="Prompt_c1">
<div class="Prompt_c1_th">
<i class="Prompt_c1_th_ico1"></i>您已禁止浏览器定位！
</div>
<div class="Prompt_c1_p">请手动选择城市。</div>
</div>
</div>

<div id="p_nopage" class="Prompt_c_w">
<div class="Prompt_c1">
<div class="Prompt_c1_th2"> 您所在的城市尚未开通移动版！</div>
<div class="Prompt_c1_p2">进入电脑版</div>
<i class="Delete1">X</i>
</div>
</div>

<div id="p_succ" class="Prompt_c_w">
<div class="Prompt_c1">
<div class="Prompt_c1_th2">
<i class="Prompt_c1_th_ico2"></i><span class="color46">加载成功！</span>
</div>
</div>
</div>

<div id="p_nonet" class="Prompt_c_w">
<div class="Prompt_c1">
<div class="Prompt_c1_th">
<i class="Prompt_c1_th_ico1"></i>加载失败！
</div>
<div class="Prompt_c1_p"> 请检查网络后重试。</div>
</div>
</div>
</div>
</section>
<!--bottom导航-->
<div class="foot_nav_fixed_b55"></div>
<div style="width:100%; position:static; bottom:0; z-index:99;">
<footer class="foot_nav">
<div class="foot_nav_box2">
<span class="wap_version" role="wap_version_menu"><a href="javascript:void(0)">Pad版</a></span>
<ul class="nav_box_ul1 wap_version_menu_ul1 show" style="display:none">
<li><a href="http://${_city.sourceDomainName }?cfrom=mobile" role="wap_version_text" url="http://${_city.sourceDomainName }?cfrom=mobile" data-nr="PC">电脑版</a></li>
<li><a href="javascript:void(0)" role="wap_version_text" url="" data-nr="PHONE" >手机版</a></li>
<li><a href="javascript:void(0)" role="wap_version_text" url="" data-nr="PAD" class="current">Pad版</a></li>
</ul>
</div>
</footer>
</div>
</div>
<script>var city = ${cityStr};</script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/wb/common/lib/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/wb/common/lib/common.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/wb/common/lib/iscroll.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/wb/common/lib/swiper_v2.1.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/wb/pad/snippets/index.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<c:if test="${'true'==needLBS}">
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.2&amp;key=9e4b883b2a6d8482638c56b6f60078b7"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/js/getlocation.js"></script>
<!-- <script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script> -->
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
<script>getlocation(${needLBS},navigator.cookieEnabled);</script>
</c:if>
<%@ include file="/views/pv.jsp"%>
</body>
</html>