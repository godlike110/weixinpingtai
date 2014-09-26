<%@page import="cn.focus.dc.focuswap.model.TuanGou"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>楼盘团购</title>
<meta name="Keywords" content="北京楼盘,+楼盘信息" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/tuangou_pad.css" />
<script data-main="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/modulejs/app_tuangou" src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/modulejs/lib/require.js" data-module="default"></script>
</head>
<body>

<!-- 新版样式导航 --> 
	<jsp:include page="/views/padHeader.jsp">
		<jsp:param value="楼盘团购" name="page_title" />
	</jsp:include>

<!--团购列表pad版部分-->
<section class="tuangou_pad_module tuangou_pad_module_no">
</section>

<section class="crumb">
    <div class="inner">
        <a href="${ctx }/${_city.cityPinyinAbbr }/">新房</a>&gt;<a href="${ctx }/${_city.cityPinyinAbbr }/tuangou/">楼盘团购</a>
    </div>
</section>


<footer class="clearfix">
    <span class="copyright">&copy;2014搜狐焦点</span>
    <!--div class="switch_version fr">
        <a href="javascript:;" class="version">Pad版</a>
        <div class="version_list">
            <a role="touch_bg" href="#" class="phone">手机版</a>
            <a role="touch_bg" href="#" class="ipad current">Pad版</a>
            <a role="touch_bg" href="#" class="pc">电脑版</a>
        </div>
    </div-->
</footer>
</body>
</html>