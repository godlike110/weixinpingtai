<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, width=device-width" />
<meta name="format-detection" content="telephone=no,address=no,email=no"/>
<meta name="keywords" content="" />
<meta name="description" content="" />
<title>购房能力评估_推荐房源信息</title>
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css"/>
<link href="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/css/common.css" rel="stylesheet" />
<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/css/pad.css">
<script>
var House = {
	price : "${price}",
	cityId : "${cityId}",
	area : "${area}"
}
</script>
</head>
<body>
 <!-- 新版样式导航 -->
	<jsp:include page="/views/padHeader.jsp">
		<jsp:param value="推荐楼盘" name="page_title" />
	</jsp:include>

<!--左侧导航-->
<nav class="left_navigator">
    <a href="${ctx}/tools/sydk/" class="normal_nav" id="business_nav">商业贷款</a>
    <a href="${ctx}/tools/gjjdk/" class="normal_nav" id="fund_nav">公积金贷款</a>
    <a href="${ctx}/tools/zhdk" class="normal_nav" id="combination_nav">组合贷款</a>
    <a href="${ctx}/tools/sf/" class="normal_nav">税费计算</a>
    <a href="${ctx}/tools/tqhd/" class="normal_nav">提前还贷</a>
    <a href="${ctx}/tools/gfnlpg/" class="normal_nav selected_nav">购房能力评估</a>
</nav>
<!--计算器内容-->
<div class="S_main">
	<div class="S_cal result">
		<h3 class="tit">推荐楼盘信息</h3>
		<div class="list_house">
			<ul id="houseList"></ul>
			
		</div>

	</div>
    <div id="has_more" class="has_more1">
		<a href="javascript:;" class="load_more" style="display: block;">滑动加载更多</a>
		<a href="javascript:;" class="loading" style="display: none;"><b class="rotating"></b>加载中...</a>
	</div>
    
</div>
    
<!--footer-->
<section class="crumb">
	<div class="inner">
		<a class="inner_link" href="${ctx}/${_city.cityPinyinAbbr}/">新房</a>&gt;<a class="inner_link" href="${ctx}/tools/gfnlpg/">购房能力评估</a>&gt;<a class="inner_link" href="javascript:;">推荐楼盘</a>
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
</body>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/jsq/common/lib/lib-common.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/jsq/pad/snippets/buyHouseList.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
<%@ include file="/views/pv.jsp"%>
</html>
