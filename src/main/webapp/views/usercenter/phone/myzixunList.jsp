<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<%@ include file="/views/icon.jsp"%>
<title>我的咨询</title>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_zixun.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
</head>
<body>
<!-- 新版样式导航 -->
<jsp:include page="/views/phoneHeader.jsp">
	<jsp:param value="我的咨询" name="page_title" />
</jsp:include>

<section class="zixun_info_main">  
        <div class="zixun_info_btngroup">
        <a href="${ctx}/my/zixun/all" <c:if test='${model==0}'> class="active"</c:if>>全部</a>
        <a href="${ctx}/my/zixun/yihuida" <c:if test='${model==1}'>class="active"</c:if>>已回答</a>
        <a href="${ctx}/my/zixun/weihuida" <c:if test='${model==2}'>class="active"</c:if>>未回答</a>
    </div>
    <c:forEach items="${quizList }" var="quiz">
    <div class="zixun_info_ask">
        <div class="zixun_info_ask_item">
                <div class="zixun_info_user_img"><img src="${quiz.userHeadPic}"></div>
            <div class="zixun_info_user_content">
                <p class="name">${quiz.userName}<span>${quiz.questionTimeStr}</span></p>
                <p class="ask">${quiz.question}</p>
                <c:if test="${quiz.status==1}"><p class="status">等待答复</p></c:if>
                <c:if test="${quiz.status==2}"><p class="status">您的问题与房产或楼盘无关</p></c:if>
            </div>
        </div>
        <c:if test="${quiz.status==6}">
        <div class="zixun_info_ask_item zixun_info_ask_over">
                <div class="zixun_info_user_img"><img src="${quiz.editorHeadPic}"></div>
            <div class="zixun_info_user_content">
                <p class="name">${quiz.editorName}<span>${quiz.answerTimeStr}</span></p>
                <p class="ask asked">${quiz.answer}<span class="btn"></span></p>
            </div>
        </div>
        </c:if>
    </div>
    <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${quiz.groupId}/"class="zixun_info_ask_lable">楼盘：${quiz.buildName}</a>
    <div class="zixun_info_ask_border"></div>
    </c:forEach>

        <c:if test="${hasNext}">
    <div class="pull font_gray">
                <div class="icon fl"></div>
                <p>滑动加载更多</p>
        </div>
    </c:if>
    <div class="zixun_info_tips no_result_tips">暂无咨询记录</div>
</section>

<%-- <!-- 面包屑 -->
<div class="link_boxs"><a href="${ctx}/${_city.cityPinyinAbbr}/">新房</a><span class="h_space1">&gt;</span>我的咨询</div>

<!-- 底部导航 // -->
<footer class="foot_nav">
    <div class="foot_nav_copyright">©2014 搜狐焦点</div>
    <div class="foot_nav_box2">
        <span class="wap_version" role="wap_version_menu">手机版</span>
        <ul class="nav_box_ul1 wap_version_menu_ul1">
            <li><a href="#" role="wap_version_text" data-nr="PC">电脑版</a></li>
            <li><a href="#" role="wap_version_text" data-nr="PHONE" class="current">手机版</a></li>
            <li><a href="#" role="wap_version_text" data-nr="PAD">Pad版</a></li> 
        </ul>
    </div>
</footer> --%>

<div class="back2top" data-title="返回顶部"></div>
<%@ include file="/views/pv.jsp"%>
</body>
<script>var hasNext = '${hasNext}';var city = {"cityAbbr":"${_city.cityPinyinAbbr}"};</script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_zixun_my.js"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</html>