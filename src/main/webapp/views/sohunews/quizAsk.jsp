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
    <link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css" />
    <link href="http://192.168.242.44/sceapp/focus_static/wap/sohunews/css/consultOnline.css" rel="stylesheet" />
</head>
<body>
    <div class="header clearfix">
        <h1 class="title">在线咨询</h1>
        <a href="${ctx}/sohunews/zixun/?cityName=${_city.cityPinyinAbbr}" data-touched="true" class="fr close_link">
            <img src="http://192.168.242.44/sceapp/focus_static/wap/phone/img/close.png" class="close_icon" alt="close" />
        </a>
        <span class="arrow"></span>
    </div>
    <section class="section_content">
        <textarea class="consult_input" maxlength="35" name="question" id="question" placeholder="不超过35个字"></textarea>
        <div>
            相关楼盘：<div class="loupan_container">${building.projName}<span class="expand_icon"></span><span class="loupan_arrow"></span></div>
            <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${building.groupId}" class="loupan_detail">
                <p class="loupan_price">
                <c:choose>
                	<c:when test="${priceType == 1}" >均价<span class="red">${priceShow}</span>元/平米</c:when>
                	<c:when test="${priceType == 2}" >最低<span class="red">${priceShow}</span>元/平米</c:when>
                	<c:when test="${priceType == 3}" >最高<span class="red">${priceShow}</span>元/平米</c:when>
                	<c:when test="${priceType == 4}" >总价<span class="red">${priceShow}</span>元/套</c:when>
                	<c:otherwise><span class="red">价格待定</span></c:otherwise>
                </c:choose>
                </p>
                <dl class="loupan_dl">
                    <dt class="fl">楼盘位置：</dt>
                    <dd>
                    <c:choose>
                    	<c:when  test="${not empty building.areaDetail}">${building.areaDetail}</c:when>
                    	<c:otherwise>暂无</c:otherwise>
                    </c:choose>
                    </dd>
                    <dd class="way">
                    <c:choose>
                    	<c:when  test="${not empty building.projAddress}">${building.projAddress}</c:when>
                    	<c:otherwise>暂无</c:otherwise>
                    </c:choose>
                    </dd>
                </dl>
                <dl class="loupan_dl">
                    <dt class="fl">开盘时间：</dt>
                    <dd>
                    <c:choose>
                    	<c:when  test="${not empty building.saleDateDetail}">${building.saleDateDetail}</c:when>
                    	<c:otherwise>暂无</c:otherwise>
                    </c:choose>
                    </dd>
                </dl>
                <dl class="loupan_dl">
                    <dt class="fl">入住时间：</dt>
                    <dd>
                    <c:choose>
                    	<c:when  test="${not empty building.moveInDateDetail}">${building.moveInDateDetail}</c:when>
                    	<c:otherwise>暂无</c:otherwise>
                    </c:choose>
                    </dd>
                </dl>
                <c:if test="${not empty fn:trim(building.discount) }">
                <span class="loupan_discount_text loupan_discount">${building.discount}</span>
                </c:if>
                <button type="button" class="view_detail" data-touched="true">查看楼盘详情</button>
            </a>
        </div>
        <button type="submit" disabled="disabled" class="submit_btn">提交</button>
    </section>
    <!--弹层-->
    <div class="success_popup">
     	<div class="clearfix success_popup_header">
             <span>成功</span>
             <div class="fr close_btn dialog_close"></div>            
     	</div>
		 <div class="success_popup_content">
            <p>提问成功！</p>
            <p>焦点专家将尽快为您解答，请耐心等待</p>
        </div>
		<a href="${ctx}/sohunews/zixun/?cityName=${_city.cityPinyinAbbr}" class="success_popup_action_btn close_btn" data-touched="true" data-touchClass="success_popup_action_btn_touched">关闭</a>
        <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${building.groupId}" class="success_popup_action_btn" data-touched="true" data-touchClass="success_popup_action_btn_touched">查看楼盘详情</a>
    </div>
    <div class="failed_popup">
        <p>问题发布失败，请重试!</p>
    </div>
    <script>var city = {"cityAbbr":"${_city.cityPinyinAbbr}"};var groupId = '${groupId}';</script>
    <script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/sohunews/snippets/common.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/sohunews/snippets/dialog.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/sohunews/snippets/consultOnline.js"></script>
    <%@ include file="/views/pv.jsp"%>
</body>
</html>