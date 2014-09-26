<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
		<meta content="telephone=no" name="format-detection" />
		<title>楼盘导购-搜狐焦点${_city.cityName }房产</title>
		<meta name="Keywords" content="楼盘导购，${_city.cityName }房产，${_city.cityName }楼盘" />
		<meta name="Description" content="搜狐焦点${_city.cityName }房产网为广大网友提供了最新的${_city.cityName }楼盘信息，最准确的${_city.cityName }房价情况和最及时的${_city.cityName }新房资讯等，是买房、购房首选网站。 ">
		<%@ include file="/views/icon.jsp" %>
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
		<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
		<script>
			var city = ${city};
		</script>
	</head>
	
	<body class="bg_all">
		<section class="container">
			<header class="clearfix">
				<a href="${ctx }/${_city.cityPinyinAbbr }/" class="back fl">首页</a>
				<div class="logo clearfix">
					<a href="${ctx }/${_city.cityPinyinAbbr }/" class="fl"><img src="http://a1.itc.cn/sceapp/focus_static/wap/images/logo.png" alt="" /></a>
				</div>
				<a href="${ctx }/${_city.cityPinyinAbbr }/" class="main fr">首页</a>
			</header>
			
			<section class="content bg_all clearfix">
				<div class="no_result_blo">
					<span>您想看的导购，暂时还没有手机版</span>
				</div>
			</section>

			<footer class="position_fixed">
				<span class="copyright">&copy;2014搜狐焦点</span>
				<div class="switch_version">
					<a href="javascript:;" class="version">Pad版</a>
					<div class="version_list">
						<a role="touch_bg" href="#" class="phone">手机版</a>
						<a role="touch_bg" href="#" class="ipad current">Pad版</a>
						<a role="touch_bg" href="#" class="pc">电脑版</a>
					</div>
				</div>
			</footer>
		</section>
		
		<!-- js -->
		<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
		<%@ include file="/views/pv.jsp"%>
	</body>
</html>