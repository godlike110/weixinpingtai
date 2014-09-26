<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<%@ include file="/views/icon.jsp"%>
<title>提问成功</title>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_zixun.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
</head>
<body>
<!-- 新版样式导航 -->
		<jsp:include page="/views/phoneHeader.jsp">
			<jsp:param value="提问成功" name="page_title" />
		</jsp:include>

<!-- 咨询页—成功页-->
<section class="zixun_info_main">
	<div class="zixun_info_list">
    	<ul>
        	<li><a href="${ctx}/${_city.cityPinyinAbbr }/loupan/${building.groupId}/">
            	<div class="zixun_info_img"><img src="${building.url}" alt="${building.projName}"></div>
                <div class="zixun_info_box">
                	<p class="name">${building.projName}</p>
                    <p class="address">${building.districtName} ${building.projAddress}</p>
                    <p class="price">
                    <c:choose>
	                	<c:when test="${priceType == 1}" >均价<span>${priceShow}</span>元/平米</c:when>
	                	<c:when test="${priceType == 2}" >最低<span>${priceShow}</span>元/平米</c:when>
	                	<c:when test="${priceType == 3}" >最高<span>${priceShow}</span>元/平米</c:when>
	                	<c:when test="${priceType == 4}" >总价<span>${priceShow}</span>元/套</c:when>
	                	<c:otherwise><span>价格待定</span></c:otherwise>
                	</c:choose>
                    </p>
                </div>
                <div class="zixun_info_btn"></div>
            </a></li>
        </ul>
    </div>
    
    <div class="zixun_info_ask">
    	<div class="zixun_info_ask_item">
        	<div class="zixun_info_user_img"><img src="${question.userHeadPic}"></div>
            <div class="zixun_info_user_content">
            	<p class="name">${question.userName}<span>${question.questionTimeStr}</span></p>
                <p class="ask">${question.question}</p>
                <p class="status">等待答复</p>
            </div>
        </div>
    </div>
    
	<a class="zixun_info_return" href="${ctx }/${_city.cityPinyinAbbr}/loupan/${building.groupId}/">3s后跳转至楼盘详情页</a>
</section>

<!-- 面包屑 -->
<div class="link_boxs">
	<a href="${ctx }/${_city.cityPinyinAbbr }/">新房</a><span class="h_space1">&gt;</span><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/">${_city.cityName}楼盘</a><span class="h_space1">&gt;</span>
	<a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${groupId}/">${building.projName}</a><span class="h_space1">&gt;</span>咨询成功
</div>
<!-- 底部导航 // -->
<footer class="foot_nav">
    <div class="foot_nav_copyright">&copy;2014 搜狐焦点</div>
</footer>

<div class="back2top" data-title="返回顶部"></div>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/consultSuccessfully.js"></script>
<%@ include file="/views/pv.jsp"%>
</body>
</html>
