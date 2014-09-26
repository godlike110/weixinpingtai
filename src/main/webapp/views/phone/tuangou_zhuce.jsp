<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>团购报名</title>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/tuangou.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<script data-main="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/modulejs/app_tuangou" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/modulejs/lib/require.js" data-module="register"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
</head>

<body data-city="${_city.cityPinyinAbbr}" data-activeid="${activeid}">

<!-- 新版样式导航 --> 
	<jsp:include page="/views/phoneHeader.jsp">
		<jsp:param value="团购报名" name="page_title" />
	</jsp:include>


<section class="tuangou_module">
    <div class="tuangou_reg_info">
        <div class="tuangou_reg_item"><span class="name">团购名称：</span><span class="info">${tuangou.active_name}</span></div>
        <div class="tuangou_reg_item"><span class="name">团购优惠：</span><span class="info">${tuangou.active_desc}</span></div>
    </div>
    <div class="tuangou_reg_form" id="tuangou_reg_form">
        <form>
            <div class="tuangou_reg_formItem"><i class="username"></i><input type="text" placeholder="请输入姓名" id="username"><div class="clear"></div></div>
            <div class="tuangou_reg_formTips"><i class="username"></i><span>您还没有输入姓名</span></div>
            <div class="tuangou_reg_formItem"><i class="phonenum"></i><input type="tel" placeholder="请输入手机号" id="phonenum" maxlength="11"><div class="clear"></div></div>
            <div class="tuangou_reg_formTips"><i class="username"></i><span>您还没有输入手机号码</span></div>
            <div class="tuangou_list_btnGroup" style="margin-top: 30px">
                <a id="tuangou_list_register">立即报名</a>
            </div>
        </form>
    </div>
</section>

<!-- 面包屑 -->
<div class="link_boxs"><a href="/${_city.cityPinyinAbbr }/">新房</a><span class="h_space1">></span><a href="/${_city.cityPinyinAbbr }/tuangou/">团购</a></div>

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

<!-- 团购详情弹层 -->
<div class="tuangou_mak">
    <div class="tuangou_mak_tips">恭喜您报名成功！</div>
</div>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>
