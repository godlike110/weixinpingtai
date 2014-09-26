<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>${_city.cityName }楼盘团购_${_city.cityName }团购楼盘_${_city.cityName }新房买房团购_${_city.cityName }搜狐焦点网</title>
<meta name="Keywords" content="${_city.cityName }楼盘团购,${_city.cityName }团购楼盘,${_city.cityName }新房团购">
<meta name="Description" content="${_city.cityName }搜狐焦点网为您提供${_city.cityName }楼盘团购、${_city.cityName }团购楼盘、${_city.cityName }新房团购等买房团购信息">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/tuangou.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<script data-main="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/modulejs/app_tuangou" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/modulejs/lib/require.js" data-module="list"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
</head>
<body data-city="${_city.cityPinyinAbbr}">

<!-- 新版样式导航 --> 
	<jsp:include page="/views/phoneHeader.jsp">
		<jsp:param value="楼盘团购" name="page_title" />
	</jsp:include>

<!--列表部分 -->
<section class="tuangou_module">
<c:forEach var="item" items="${tgList}" varStatus="status">
	<c:if test="${item.timeLeft>=0}">
    <div class="tuangou_list_box">
        <div class="tuangou_list_top">
            <div class="tuangou_list_top_last">还有${item.timeLeft}天结束</div>
            <div class="tuangou_list_top_num">已报名 <span class="red">${item.apply_num}</span> 人</div>
        </div>
        <div class="tuangou_list_main">
            <div class="tuangou_list_title">${item.active_name}</div>
            <a href="${ctx }/${_city.cityPinyinAbbr }/tuangou/${item.active_id}/" class="tuangou_list_img">
                <img src="${item.photo}" alt="搜狐焦点">
                <div class="tuangou_list_img_area">${item.location}</div>
                <div class="tuangou_list_img_bar">
                    <p class="name">${item.proj_name}</p>
                    <p class="price"><span>${item.price}</span>${item.price_unit}</p>
                </div>
            </a>
            <div class="tuangou_list_intro">${item.active_desc}</div>
            <div class="tuangou_list_btnGroup">
                <a href="${ctx }/${_city.cityPinyinAbbr }/tuangou/${item.active_id}/">查看详情</a>
                <a href="${ctx }/${_city.cityPinyinAbbr }/tuangou/${item.active_id}/apply/">立即报名</a>
            </div>
        </div>
    </div>
    </c:if>
    </c:forEach>
</section>

<div class="pull font_gray">
    <div class="icon fl"></div>
    <p>加载更多中....</p>
</div>
<!-- 面包屑 -->
<div class="link_boxs"><a href="${ctx }/${_city.cityPinyinAbbr }">新房</a><span class="h_space1">></span><a href="${ctx }/${_city.cityPinyinAbbr }/tuangou/">团购</a></div>

<!-- 底部导航 -->
<footer class="foot_nav">
    <div class="foot_nav_copyright">©2014 搜狐焦点</div>
    <!--div class="foot_nav_box2">
        <span class="wap_version" role="wap_version_menu">手机版</span>
        <ul class="nav_box_ul1 wap_version_menu_ul1">
            <li><a href="#" role="wap_version_text" data-nr="PC">电脑版</a></li>
            <li><a href="#" role="wap_version_text" data-nr="PHONE" class="current">手机版</a></li>
            <li><a href="#" role="wap_version_text" data-nr="PAD">Pad版</a></li>
        </ul>
    </div-->
</footer>

<div class="back2top" data-title="返回顶部"></div>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>
