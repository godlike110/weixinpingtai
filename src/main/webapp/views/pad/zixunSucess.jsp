<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta content="telephone=no" name="format-detection" />
    <%@ include file="/views/icon.jsp"%>
    <title>提问成功</title>
    <meta name="Keywords" content="北京楼盘,+楼盘信息" />
    <meta name="Description" content="搜狐焦点北京房产网为广大网友提供了最新的北京楼盘信息，最准确的北京房价情况和最及时的北京新房资讯等，是买房、购房首选网站。 "/>
    <link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
    <link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
    <link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/consultSuccessfully.css" rel="stylesheet" />
    <link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
</head>
<body>
    <!-- 新版样式导航 -->
	<jsp:include page="/views/padHeader.jsp">
		<jsp:param value="提问成功" name="page_title" />
	</jsp:include>
    <div class="section_content">
        <a href="${ctx}/${_city.cityPinyinAbbr }/loupan/${building.groupId}/" class="loupan_container clearfix" role="touch_bg">
            <div class="fl loupan_img_container"><img src="${building.url}" alt="${building.projName}" /></div>
            <dl class="fl">
                <dt class="loupan_title text_overflow">${building.projName}</dt>
                <dd class="loupan_position text_overflow">${building.districtName} ${building.projAddress}</dd>
                <dd>
                <c:choose>
                	<c:when test="${priceType == 1}" >均价<span class="red">${priceShow}</span>元/平米</c:when>
                	<c:when test="${priceType == 2}" >最低<span class="red">${priceShow}</span>元/平米</c:when>
                	<c:when test="${priceType == 3}" >最高<span class="red">${priceShow}</span>元/平米</c:when>
                	<c:when test="${priceType == 4}" >总价<span class="red">${priceShow}</span>元/套</c:when>
                	<c:otherwise><span class="red">价格待定</span></c:otherwise>
                </c:choose>
                </dd>
            </dl>
            <div class="fr">
                <img class="loupan_arrow" src="http://192.168.242.44/sceapp/focus_static/wap/pad/img/icon-right.png" alt="" />
            </div>
        </a>

        <div class="question">
            <div class="portrait_container fl">
                <img class="consult_portrait" src="${question.userHeadPic}">
            </div>
            <div class="question_detail">
                <div class="question_summary clearfix">
                    <span>${question.userName}</span>
                    <span class="datetime">${question.questionTimeStr}</span>
                </div>
                <div class="question_content">${question.question}</div>    
                <div class="question_state">等待回复</div>            
            </div>
        </div>
		<a href="${ctx }/${_city.cityPinyinAbbr}/loupan/${building.groupId}/" role="touch_bg" class="goback_browse_btn" data-touchClass="goback_browse_btn_touched">3s后跳转至楼盘详情页</a>
    </div>
    <section class="crumb">
		<div class="inner">
			<a href="${ctx }/${_city.cityPinyinAbbr }/">新房</a>&gt;<a href="${ctx }/${_city.cityPinyinAbbr }/loupan/">${_city.cityName}楼盘</a>&gt;<a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${groupId}/">${building.projName}</a>&gt;<span>咨询成功</span>
		</div>
	</section>
    <footer class="clearfix">
		<span class="copyright">&copy;2014搜狐焦点</span>
		<a href="javascript:;" id="to_top">返回顶部</a>
    </footer>

    <script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
    <script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/consultSuccessfully.js"></script>
    <%@ include file="/views/pv.jsp"%>
</body>
</html>