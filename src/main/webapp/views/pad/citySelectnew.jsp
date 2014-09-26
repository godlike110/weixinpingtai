<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
		<meta content="telephone=no" name="format-detection" />
		<title>${_dict_city.cityName }新房-${_dict_city.cityName }买房-搜狐焦点</title>
		<%@ include file="/views/icon.jsp" %>
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
		<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
		<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/js/getlocation.js"></script>
        <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.2&amp;key=9e4b883b2a6d8482638c56b6f60078b7"></script>	
        <script>
			var city = ${cityStr};
		</script>
	</head>
	
	<body>
		<section class="container">
			<header class="clearfix">
				<a href="${ctx}/${_dict_city.cityPinyinAbbr}/" class="back fl"></a>
				<h1>选择城市</h1>
				<a href="javascript:wapLBS();" class="local fr">定位</a>
			</header>
			
			<section class="content clearfix">
				<div class="city_list_mod">
					<div class="mod_t">
						<h2 class="border_wrap">当前定位</h2>
					</div>
					<div class="mod_c">
						<p id="currentCity">您已禁止浏览器定位</p>
					</div>
					<div class="mod_t mod_tt" id="hot_city">
						<h2 class="border_wrap">热门城市</h2>
					</div>
					<div class="mod_c">
						<a  class="item">北京</a>
						<a  class="item">上海</a>
					<a class="item">海南</a>
					<a class="item">石家庄</a>
					<a class="item">重庆</a>
					<a class="item">昆明</a>
					<a class="item">南京</a>
					<a class="item">广州</a>
					<a class="item">长沙</a>
					</div>
					<div class="mod_t mod_tt">
						<h2 class="border_wrap">字母排序</h2>
					</div>
					<div class="mod_c">
					<c:forEach items="${order_city}" var="map" varStatus="status">
					<c:if test="${status.first==true}">
						<div class="group group_open" id="${map.key }">
							<a href="javascript:;" class="category">${map.key }</a>
							<c:forEach items="${map.value }" var="citylist">
							<a class="item">${citylist.cityName}</a>
							</c:forEach>
						</div>
					</c:if>
					<c:if test="${status.first==false}">
						<div class="group group_open" id="${map.key }">
							<a href="javascript:;" class="category">${map.key }</a>
							<c:forEach items="${map.value }" var="citylist">
							<a class="item">${citylist.cityName}</a>
							</c:forEach>
						</div>
						</c:if>
					</c:forEach>
					</div>

					<div class="shortcut">
						<a href="#hot_city">热门</a>
						<c:forEach items="${order_city}" var="map">
						<a href="#${map.key}">${map.key}</a>
						</c:forEach>
					</div>
				</div>
			</section>

			<section class="crumb">
				<div class="inner">
					<a href="${ctx}/${_dict_city.cityPinyinAbbr}/">新房</a>&gt;<span> 城市选择</span>
				</div>
			</section>

			<footer>
				<span class="copyright">&copy;2014搜狐焦点</span>
				<div class="switch_version fr">
					<a href="javascript:;" class="version">Pad版</a>
					<div class="version_list">
						<a role="touch_bg" href="#" class="phone">手机版</a>
						<a role="touch_bg" href="#" class="ipad current">Pad版</a>
						<a role="touch_bg" href="#" class="pc">电脑版</a>
					</div>
				</div>
				
			</footer>

			<section class="popup_layer" id="popup-1">
                <div class="mask"></div>
                <div class="popup">
                    <div class="inner">
                        <h4 class="wrong">定位失败！</h4>
                        <p>请检查网络和GPS设置。</p>
                    </div>
                </div>
			</section>
			<section class="popup_layer" id="popup-2">
                <div class="mask"></div>
                <div class="popup">
                    <div class="inner">
                        <h4 class="wrong">您已禁止浏览器定位！</h4>
                        <p>手动选择城市。</p>
                    </div>
                </div>
			</section>
			<section class="popup_layer" id="popup-3">
                <div class="mask"></div>
                <div class="popup">
                    <div class="inner inner_center">
                        <p>您所在的城市尚未开通移动版！</p>
                        <a href="http://${_dict_city.domainName }/?cfrom=mobile" class="btn">进入电脑版</a>
                    </div>
                </div>
			</section>
		</section>
		
		<!-- js -->
		<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/js/wappad.js"></script>
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
		<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
	<script>	
	
	
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
	
	$(".item").click(function(e){
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
