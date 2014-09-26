<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
		<meta content="telephone=no" name="format-detection" />
		<title>${info.projName }地图-${_city.cityName }${info.projName }位置和周边-搜狐焦点</title>
		<%@ include file="/views/icon.jsp" %>
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/wzwmap.css">
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
		<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
		<script type="text/javascript"  src="http://webapi.amap.com/maps?v=1.2&key=9e4b883b2a6d8482638c56b6f60078b7"></script>
		<script type="text/javascript">
		var city = ${cityStr};
		var groupId = ${groupId}; 
		var phone400 = ${phone400};
		</script>
	</head>
	
	<body>
		<section class="container">
			<header>
														<c:if test="${returnFlag=='0' }">
		<a href="javascript:history.back();" class="back" data-title="返回上一级"></a>
		</c:if>
		<c:if test="${returnFlag=='1' }">
			<a href="${ctx}/${_city.cityPinyinAbbr}/" class="back" data-title="返回上一级">首页</a>
		</c:if>
				<h1>地图及周边</h1>
				<a href="javascript:;" class="map_icon">周边</a>
			</header>

			<section class="content clearfix">
				<div class="map_wrap scroll_mod">
					<div id="mockContainer" class="map_container" lng="${info.longitude }" lat="${info.latitude }" projname="${info.projName }"></div>
					<div class="zoom">
						<a href="javascript:;" class="zoom_in">放大</a>
						<a href="javascript:;" class="zoom_out">缩小</a>
					</div>
				</div>
				<aside class="scroll_mod" id="map_aside">
					<div class="scroller">
						<ul id="filter_items">
							<li id="life_ul">
								<dl>
									<dt>生活服务</dt>
									<dd id="shopping">购物</dd>
									<dd id="bank">银行</dd>
								</dl>
							</li>
							<li id="traffic_ul">
								<dl>
									<dt>交通</dt>
									<dd id="subway">地铁</dd>
									<dd id="busStn">公交</dd>
								</dl>
							</li>
							<li id="edu_ul">
								<dl>
									<dt>教育</dt>
									<dd id="kindergarten">幼儿园</dd>
									<dd id="primarySch">小学</dd>
									<dd id="middleSch">中学</dd>
								</dl>
							</li>
							<li id="hel_ul">
								<dl>
									<dt>健康</dt>
									<dd id="hospital">医院</dd>
									<dd id="park">公园</dd>
								</dl>
							</li>
						</ul>
					</div>
					<div id="mockContainer1" style="display:none;"></div>
				</aside>
			</section>

			<section class="crumb">
				<div class="inner">
					<a href="${ctx}/${_city.cityPinyinAbbr}/">新房</a>&gt;<a href="${ctx}/${_city.cityPinyinAbbr}/loupan/"> ${_city.cityName }楼盘</a>&gt;<a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/"> ${buildName}</a>&gt; 地图
				</div>
			</section>
 <c:if test="${info.phone400!=0 }">
			<footer>
				<a href="tel:4008882200,${info.phone400 }" class="free_advice"><b></b>免费咨询 400-888-2200 转 ${info.phone400 }</a>
			</footer>
			</c:if>
			
			<c:if test="${info.phone400==0 }">
			<footer class="position_fixed">
				<a href="tel:4006782020" class="free_advice"><b></b>免费咨询 400-678-2020</a>
			</footer>
			</c:if>
		</section>
		<!-- js -->
		<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.map.gh_1.0.js"></script>
		<%@ include file="/views/pv.jsp"%>
	</body>
</html>
