<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta content="telephone=no" name="format-detection" />
    <%@ include file="/views/icon.jsp"%>
    <title>在线咨询</title>
    <meta name="Keywords" content="北京楼盘,+楼盘信息" />
    <meta name="Description" content="搜狐焦点北京房产网为广大网友提供了最新的北京楼盘信息，最准确的北京房价情况和最及时的北京新房资讯等，是买房、购房首选网站。 "/>
    <link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
    <link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
    <link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/consultOnline_pad.css" rel="stylesheet" />
    <link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
</head>
<body>
   	<!-- 新版样式导航 -->
		<jsp:include page="/views/padHeader.jsp">
			<jsp:param value="我要咨询" name="page_title" />
		</jsp:include>
		
    <section id="section_content">
        <textarea class="consult_input" name="question" placeholder="问题不超过35个字" maxlength="35"></textarea>
        <button type="submit" class="submit_btn" role="touch_bg" data-touchClass="submit_btn_touched" disabled="disabled">提交</button>
    </section>
    <section class="crumb">
		<div class="inner">
			<a href="${ctx }/${_city.cityPinyinAbbr }/">新房</a>&gt;<a href="${ctx }/${_city.cityPinyinAbbr }/loupan/">${_city.cityName}楼盘</a>&gt;<a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${groupId}/">${building.projName }</a>&gt;<span>我要咨询</span>
		</div>
	</section>
    <footer class="clearfix">
		<span class="copyright">&copy;2014搜狐焦点</span>
		<a href="javascript:;" id="to_top">返回顶部</a>
    </footer>
	<script>var city = {"cityAbbr":"${_city.cityPinyinAbbr}"};var groupId = '${groupId}';</script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
    <script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/consultOnline_pad.js"></script>
    <%@ include file="/views/pv.jsp"%>
</body>
</html>
