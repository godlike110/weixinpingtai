<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<%@ include file="/views/icon.jsp"%>
<title>手机号码输入</title>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_login.css">
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
</head>
<body>

<!-- 新版样式导航 -->
		<jsp:include page="/views/phoneHeader.jsp">
			<jsp:param value="填写手机号" name="page_title" />
		</jsp:include>

<section class="login_module">
    <div class="login_tips_words">欢迎登录搜狐焦点</div>
    <div class="login_form">
        <div class="login_form_phone"><i class=""></i><input type="tel" placeholder="请输入手机号" maxlength="11" name="phone" id="login_phone" data-type="number"><div class="clear"></div></div>
        <div class="login_form_warnTips">请输入正确手机号</div>
        <div class="login_form_btn" id="login_form_next">下一步</div>
        <div class="login_other_login">其它方式登录</div>
    </div>
</section>

<!-- 其他登录模块 -->
<%@ include file="/views/thirdlogin.jsp"%>

<!-- 底部导航 -->
<footer class="foot_nav">
    <div class="foot_nav_copyright">©2014 搜狐焦点</div>
    <div class="foot_nav_box2">
    </div>
</footer>
<%@ include file="/views/pv.jsp"%>
</body>
<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/pad_filterinput.js" type="text/javascript"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_login_common.js" type="text/javascript"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_login.js" type="text/javascript"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</html>