<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>${_dict_city.cityName }新房-${_dict_city.cityName }买房-搜狐焦点</title>
<meta name="Keywords" content="${_dict_city.cityName }楼盘, ${_dict_city.cityName }房价, ${_dict_city.cityName }新房">
<meta name="Description" content="搜狐焦点${_dict_city.cityName }房产网为广大网友提供了最新的${_dict_city.cityName }楼盘信息，最准确的${_dict_city.cityName }房价情况和最及时的${_dict_city.cityName }新房资讯等，是买房、购房首选网站。">
<%@ include file="/views/icon.jsp" %>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/js/getlocation.js"></script>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.2&amp;key=9e4b883b2a6d8482638c56b6f60078b7"></script>	
<script>
var city = ${cityStr};
</script>
</head>
<body class="wap_selectcity_all">
	<div class="wap_container">
		<!-- 选择城市页—顶部导航 -->
		<header class="top_nav">
			<a href="${ctx}/${_dict_city.cityPinyinAbbr}/" class="go_back" data-title="返回上一级"></a>
			<span class="search_title">选择城市</span>
			<a class="city_location" href="javascript:wapLBS();"></a>
		</header>
		
		<!-- 选择城市页主要内容板块 // -->
		<section class="no_page_main">
			
			<div data-title="当前定位">
				<header class="public_title1">
					<h2 class="its_tit1">当前定位<em class="its_icon1"></em></h2>
				</header>
				<ul class="city_list show">
					<li class="border_e9e9e9 has_forbid"><a id="currentCity" href="javascript:;">您已禁止浏览器定位</a></li>
				</ul>
			</div>
			
			<div data-title="热门城市">
				<header class="public_title1">
					<h2 class="its_tit1"><a name="letter_hot">热门城市</a><em class="its_icon1"></em></h2>
				</header>
				<ul class="city_list show select_this_city" id="hotcity">
					<li class="border_e9e9e9 has_forbid"><a href="javascript:;" role="text" url="">北京</a></li>
					<li class="border_e9e9e9 has_forbid"><a href="javascript:;" role="text" url="">上海</a></li>
					<li class="border_e9e9e9 has_forbid"><a href="javascript:;" role="text" url="">海南</a></li>
					<li class="border_e9e9e9 has_forbid"><a href="javascript:;" role="text" url="">石家庄</a></li>
					<li class="border_e9e9e9 has_forbid"><a href="javascript:;" role="text" url="">重庆</a></li>
					<li class="border_e9e9e9 has_forbid"><a href="javascript:;" role="text" url="">昆明</a></li>
					<li class="border_e9e9e9 has_forbid"><a href="javascript:;" role="text" url="">南京</a></li>
					<li class="border_e9e9e9 has_forbid"><a href="javascript:;" role="text" url="">广州</a></li>
					<li class="border_e9e9e9 has_forbid"><a href="javascript:;" role="text" url="">长沙</a></li>
				</ul>
			</div>
			
			<div data-title="所有城市">
				<header class="public_title1">
					<h2 class="its_tit1">所有城市<em class="its_icon1"></em></h2>
				</header>
				<ul class="all_city_lists">
				<c:forEach var="map" items="${order_city }" varStatus="status">
				 <c:if test="${status.first}">
				   	<li>
						<h3 class="city_letter_tit border_e9e9e9"><a name="letter_${map.key }">${map.key }</a></h3>
						<ul class="city_list select_this_city" style="display:block;">
						<c:forEach var="citylist" items="${map.value }">
							<li class="border_e9e9e9 has_forbid"><a href="javascript:;" role="text" url="">${citylist.cityName }</a></li>
						</c:forEach>
						</ul>
					</li>
				 </c:if>
				<c:if test="${status.first==false}">
					<li>
						<h3 class="city_letter_tit border_e9e9e9"><a name="letter_${map.key }">${map.key }</a></h3>
						<ul class="city_list select_this_city">
						<c:forEach var="citylist" items="${map.value }">
							<li class="border_e9e9e9 has_forbid"><a href="javascript:;" role="text" url="">${citylist.cityName }</a></li>
						</c:forEach>
						</ul>
					</li>
				</c:if>

				</c:forEach>
				</ul>
				
				<div class="all_city_lists_box_bg"></div>
				
				<ul class="all_city_lists_box">
					<li><a href="#letter_hot">热门</a></li>
					<c:forEach var="map" items="${order_city }">
					<li><a href="#letter_${map.key }">${map.key }</a></li>
					</c:forEach>
				</ul>
			
			</div>
			
		</section>
		
		<!-- 面包屑 -->
		<div class="link_boxs"><a href="${ctx}/${_dict_city.cityPinyinAbbr}/">新房</a><span class="h_space1">&gt;</span> 城市选择</div>
		
		<!-- 底部导航 // -->
		<footer class="foot_nav">
			<div class="foot_nav_copyright">©2014 搜狐焦点</div>
				<div class="foot_nav_box2">
					<span class="wap_version" role="wap_version_menu">手机版</span>
					<ul class="nav_box_ul1 wap_version_menu_ul1">
						<li><a href="#" role="wap_version_text" url="" data-nr="PC">电脑版</a></li>
						<li><a href="#" role="wap_version_text" url="" data-nr="PHONE" class="current">手机版</a></li>
					</ul>
				</div>
		</footer>
		<!-- // 底部导航 -->
		
	</div>
	
	<div class="back2top" data-title="返回顶部"></div>
	
	<!-- 定位弹层 —— 往下 // -->
	<div class="over"></div>
	<!-- 定位城市无wap版弹层 // -->
	<div class="alert_box" id="city_location_no_wap1">
     	<p class="p1">您所在的城市尚未开通</p>
     	<p class="p1">移动版</p>
     	<div class="itos_btn1" id="goto_pc1"><a href="http://${_dict_city.domainName }/?cfrom=mobile">进入电脑版</a></div>
    </div>
	<!-- // 定位城市无wap版弹层 -->
	
	<!-- 定位禁用弹层 // -->
	<div class="alert_box signup_fail_box" id="forbid_city_location1">
     	<h3 class="signup_fail_tit red1"><em class="signup_fail_icon"></em>您已禁止浏览器定位！</h3>
     	<p class="p2">请手动选择城市</p>
    </div>
	<!-- // 定位禁用弹层 -->
	
	<!-- 定位失败弹层 // -->
	<div class="alert_box signup_fail_box" id="city_location_fail1">
     	<h3 class="signup_fail_tit red1"><em class="signup_fail_icon"></em>定位失败！</h3>
     	<p class="p2">请检查网络和GPS设置</p>
    </div>
	<!-- // 定位失败弹层 -->

<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_city_select_v1.0.js"></script>
<script type="text/javascript">
	console.log( '${ctx}' );
	// console.log( city_select_baseUrl );
	$("#currentCity").click(function(e){
		var url = "${ctx}/city/locate?preventCache=" + new Date().getTime();
		var that = $(this);
		var cityName = that.text();
		$.post(url, {cityName:cityName}, function(data){
			var result = JSON.parse(data);
			//alert(data);
			if (result && result.errorCode == 0) {
				 window.location.href="${ctx}/" + result.data.cityLocated.cityPinyinAbbr + "/";
			}
		});
	});

	$(".select_this_city a").click(function(e){
		var url = "${ctx}/city/locate?preventCache=" + new Date().getTime();
		var that = $(this);
		var cityName = that.text();
		$.post(url, {cityName:cityName}, function(data){
			var result = JSON.parse(data);
			//alert(data);
			if (result && result.errorCode == 0) {
				 window.location.href="${ctx}/" + result.data.cityLocated.cityPinyinAbbr + "/";
			}
		});
		
	});
	
	wapLBS();
</script>
<%@ include file="/views/pv.jsp"%>
</body>
</html>