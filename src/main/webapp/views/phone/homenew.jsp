<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="apple-touch-fullscreen" content="YES" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="format-detection" content="telephone=no" />
<meta name="Keywords" content="${_city.cityName}楼盘, ${_city.cityName}房价, ${_city.cityName}新房">
<meta name="Description" content="搜狐焦点${_city.cityName}房产网为广大网友提供了最新的${_city.cityName}楼盘信息，最准确的${_city.cityName}房价情况和最及时的${_city.cityName}新房资讯等，是买房、购房首选网站。 ">
<title>【${_city.cityName}楼盘|${_city.cityName}房价|${_city.cityName}新房】-搜狐焦点${_city.cityName}房产</title>
<%@ include file="/views/icon.jsp" %>
<link href="http://192.168.242.44/sceapp/focus_static/wap/wb/phone/css/common.css" rel="stylesheet" type="text/css" />
<link href="http://192.168.242.44/sceapp/focus_static/wap/wb/phone/css/style.css" rel="stylesheet" type="text/css" />
<link href="http://192.168.242.44/sceapp/focus_static/wap/wb/common/css/swiper_v1.0.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css"/>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
</head>
<body>

<div id="wrap_s">
	<div id="ad">
		<div class="advertisement_box">
			<a href="${ctx}/gongyi/" target="_blank" id="adImg1"><img src="http://192.168.242.44/sceapp/focus_static/wap/phone/img/advertisement_img2.jpg"/></a>
			<span class="closeAd"></span>
			<span class="advertisement_box_fade"></span>
		</div>
	</div>
<!-- 新版头部 -->
<header class="top-nav-new">
		<h1 class="logo-icon"></h1>
		<a href="${ctx}/city/select/" class="city-selector" data-url="${ctx}/city/select/">${_city.cityName}<i></i></a>
		<div class="header_tool">
			<a href="#" class="search-btn olp-bg-search2"></a>
			<a href="${ctx}/static/appwap.html" class="app-btn"></a>
			<a href="#" class="nav-btn" role="main_menu" role-addclass="its_open" data-title="展开菜单">导航<i></i></a>
		</div>
		<%@include file="/views/phoneNav.jsp" %>
</header>

<section>
<div id="search" style="display: none;">
<div class="Search_wrap">
<div class="Search2_w">
<span class="Search2_D"><a href="javascript:void(0)">取消</a></span>
<input value="请输入楼盘名/地址" class="Search2_r" />
<div class="Search2_r_btn"></div>
</div>
</div>
<div class="Search2_b" name="lianxiang"></div>
<div class="Search2_b2" name="hothis">
<div class="Search2_w_top">
<div class="Search2_w_top_w5">
<a href="javascript:void(0)" class="Search2_hov" name="hot">热门</a>
</div>
<div class="Search2_w_top_w5">
<a href="javascript:void(0)" name="history">历史</a>
</div>
</div>
<ul name="history"></ul>
<ul name="hot">
<li onmouseover='tHotOver(this)' onmouseout='tHotOut(this)' onclick='search(this)'><a href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____DZLP_/'>打折楼盘</a></li>
<li onmouseover='tHotOver(this)' onmouseout='tHotOut(this)' onclick='search(this)'><a href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____ZXKP_/'>最新开盘</a></li>
<li onmouseover='tHotOver(this)' onmouseout='tHotOut(this)' onclick='search(this)'><a href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____XHX_/'>小户型</a></li>
<li onmouseover='tHotOver(this)' onmouseout='tHotOut(this)' onclick='search(this)'><a href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____XF_/'>现房</a></li>
<li onmouseover='tHotOver(this)' onmouseout='tHotOut(this)' onclick='search(this)'><a href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____GYPB_/'>公园旁边</a></li>
<li onmouseover='tHotOver(this)' onmouseout='tHotOut(this)' onclick='search(this)'><a href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____DZJ_/'>低总价</a></li>
<li onmouseover='tHotOver(this)' onmouseout='tHotOut(this)' onclick='search(this)'><a href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____JYSQ_/'>教育社区</a></li>
</ul>
</div>
</div>
<!-- 新版头部！ -->
<div class="center1" id="center">
<div class="sh_con">
<div class="sh_con_w100">
<div class="border_r_c">
${focus}
<div class="f_Table">
<div class="f_Table_nav" name="xinfang">
<span class="f_th"><a href="javascript:void(0)" style="display: inline-block;"><h2 style="font-size: 24px;">新房</h2></a></span>
<ul class="f_Table_nav_c">
<c:if test="${''!=zhaofang}">
<li><a href="javascript:void(0)" class="colorcb" name="zhaofang" style="display: inline-block;"><h3>找房</h3></a><span class="f_Table_nav_hov"></span></li>
</c:if>
<c:if test="${''!=zuixinkaipan}">
<li><a href="javascript:void(0)" name="zuixlp" style="display: inline-block;"><h3>最新开盘</h3></a><span></span></li>
</c:if>
<c:if test="${''!=xiaohuxing}">
<li><a href="javascript:void(0)" name="xiaohx" style="display: inline-block;"><h3>小户型</h3></a><span></span></li>
</c:if>
<c:if test="${''!=dazheyouhui}">
<li><a href="javascript:void(0)" name="dazyh" style="display: inline-block;"><h3>打折优惠</h3></a><span></span></li>
</c:if>
</ul>
</div>
${zhaofang} ${zuixinkaipan} ${xiaohuxing} ${dazheloupan}
</div>
<c:if test="${''!=pft}">${pft}</c:if>
<c:if test="${zzfstatus=='1'}">
<div id="zizhufang_box1">
<div class="advertisement_box">
<a href="http://focusbeijing.kuaizhan.com/36/78/p1785789845ea4a" target="_blank" id="zizhufang_img1"><img id="adImg" class="img" src="http://192.168.242.44/sceapp/focus_static/wap/phone/img/zizhufang_phone640.jpg" width="320" height="60"></a>
</div>
</div>
</c:if>
<c:if test="${''!=news}">${news}</c:if>
<div class="f_Table">
<div class="f_Table_nav" name="jiaodyc">
<span class="f_th"><a href="javascript:void(0)" style="display: inline-block;" ><h2 style="font-size: 24px;">焦点原创</h2></a></span>
<ul class="f_Table_nav_c">
<c:if test="${''!=baodian}">
<li><a href="javascript:void(0)" class="colorcb"
name="one" style="display: inline-block;"><h3>购房宝典</h3></a><span class="f_Table_nav_hov"></span></li>
</c:if>
<c:if test="${''!=daogou}">
<li class="ml_114"><a href="javascript:void(0)"
name="two" style="display: inline-block;"><h3>楼盘导购</h3></a><span></span></li>
</c:if>
</ul>
</div>
${baodian} ${daogou}
</div>
</div>
</div>
</div>
<!-- 计算器开始 -->
<div class="f_Table">
	<div class="f_Table_nav">
		<span class="f_th"><a href="javascript:void(0)"><h2 style="font-size: 24px;">房贷计算</h2></a></span>
	</div>
	<div class="Mortgage_t_b">
		<div class="Mortgage">
			<ul>
				<a href="${ctx}/tools/sydk/"><li name="shangdaidaik"><i class="Mortgage_cio1"></i><p>商贷贷款计算</p></li></a>
				<a href="${ctx}/tools/gjjdk/"><li name="gongjjdaik"><i class="Mortgage_cio2"></i><p>公积金贷款计算</p></li></a>
				<a href="${ctx}/tools/zhdk/"><li name="zuhedaik"><i class="Mortgage_cio3"></i><p>组合贷款计算</p></li></a>
			</ul>
		</div>
	</div>
</div>
<!-- 计算器结束 -->

<!-- 定位相关开始 -->
<section name="prompt">
<div id="Prompt" class="Prompt" style="display: none;">
<div id="p_nogps" class="Prompt_c_w">
<div class="Prompt_c1">
<div class="Prompt_c1_th">
<i class="Prompt_c1_th_ico1"></i>定位失败！
</div>
<div class="Prompt_c1_p">请检查网络和GPS设置。</div>
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
<div class="Prompt_c1_th2">您所在的城市尚未开通移动版！</div>
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
<div class="Prompt_c1_p">请检查网络后重试。</div>
</div>
</div>
</div>
</section>
<!-- 定位相关结束 -->
<!--bottom导航-->
<footer class="clearfix">
<div class="switch_version switch_version_active">
<a href="javascript:void(0)" class="version">手机版</a>
<div class="version_list">
<a role="touch_bg" data-nr="PHONE" href="javascript:void(0)" class="phone current">手机版</a>
<a role="touch_bg" data-go="false" data-nr="PC" href="http://${_city.sourceDomainName }?cfrom=mobile" class="pc">电脑版</a>
</div>
</div>
</footer>
<div class="back2top" data-title="返回顶部" ></div>
</div>
</section>
</div>
<script>var city = ${cityStr};</script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/wb/common/lib/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/wb/common/lib/swiper_v2.1.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/wb/common/lib/common.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/wb/phone/snippets/index.js"></script>
<c:if test="${'true'==needLBS}">
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.2&amp;key=9e4b883b2a6d8482638c56b6f60078b7"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/js/getlocation.js"></script>
<script>getlocation(${needLBS},navigator.cookieEnabled);</script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
</c:if>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
<%@ include file="/views/pv.jsp"%>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>