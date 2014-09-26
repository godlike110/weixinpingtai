<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<%@ include file="/views/icon.jsp"%>
<title>登录</title>
<meta name="Keywords" content="北京楼盘,+楼盘信息" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/pad_login.css" />
</head>
<body data-url="${from}">
<!-- 新版样式导航 -->
		<jsp:include page="/views/padHeader.jsp">
			<jsp:param value="登录" name="page_title" />
		</jsp:include>

<!--登录pad版部分-->
<section class="login_pad_module">
    <div class="login_pad_banner"><img src="http://192.168.242.44/sceapp/focus_static/wap/pad/images/banner.png"></div>
    <div class="login_pad_loginBox">
        <div class="login_pad_welcome">欢迎登录搜狐焦点</div>
        <div class="login_pad_formBox clearfix">
            <div class="login_pad_inputBox">
                <div class="login_pad_inputItem"><i class="phoneico"></i><input placeholder="请输入手机号" maxlength="11" id="loginPhone" type="number" data-type="number"><i class="clear"></i></div>
                <div class="login_pad_errorTips">请输入正确手机号</div>
                <div class="login_pad_inputItem login_pad_inputItem2"><i class="lockico"></i><input placeholder="请输入验证码" maxlength="5" id="loginCode" type="number" data-type="number"><i class="clear"></i><div class="getCode" id="getCode">获取验证码</div></div>
                <div class="login_pad_errorTips">请输入正确验证码</div>
                <div class="login_pad_inputItem login_pad_inputBtn">登录</div>
            </div>
            <div class="login_pad_inputOther">
                <div class="tips_title">第三方账号登录</div>
                <a href="http://passport.sohu.com/openlogin/request.action?provider=sina&appid=1028&ru=${fromSina}&hun=0" class="sina">新浪微博登录</a>
                <a href="http://passport.sohu.com/openlogin/request.action?provider=qq&appid=1028&ru=${fromQQ}&hun=0" class="qq">腾讯QQ登录</a>
            </div>
        </div>
    </div>
</section>

<footer class="clearfix">
    <span class="copyright">&copy;2014搜狐焦点</span>
    <div class="switch_version fr">
        <a href="javascript:;" class="version">Pad版</a>
        <div class="version_list">
            <a role="touch_bg" href="#" class="phone">手机版</a>
            <a role="touch_bg" href="#" class="ipad current">Pad版</a>
        </div>
    </div>
</footer>

<div class="login_pad_UItips">
    <div class="login_pad_UItips_w1">短信验证码获取次数超限</div>
</div>

<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/pad_filterinput.js" type="text/javascript"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/pad_login.js" type="text/javascript"></script>
<%@ include file="/views/pv.jsp"%>
</body>
</html>
