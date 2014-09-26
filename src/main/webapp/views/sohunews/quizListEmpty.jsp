<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta content="telephone=no" name="format-detection" />
    <%@ include file="/views/icon.jsp"%>
    <title>${_city.cityName}楼盘在线咨询_${_city.cityName}购房买房在线咨询-${_city.cityName}搜狐焦点网</title>
<meta name="Keywords" content="${_city.cityName}楼盘在线咨询,${_city.cityName}购房在线咨询" />
<meta name="Description" content="${_city.cityName}搜狐焦点网是${_city.cityName}最权威的专业咨询平台，为您提供${_city.cityName}楼盘折扣，周边配套，买房流程，置业疑惑等在线咨询，搜狐焦点专家有问必答"/>
    <link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
    <link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css" />
    <link href="http://192.168.242.44/sceapp/focus_static/wap/sohunews/css/noConsult.css" rel="stylesheet" />
</head>
<body>
    <div class="header clearfix">
        <h1 class="title">在线咨询</h1>
        <div class="fr">
            <a href="${ctx }/sohunews/citySelect/?cityName=${_city.cityPinyinAbbr}&from=zixun" data-touched="true" class="header_sub_btn">
                <span class="city">${_city.cityName}</span>
                <img src="http://192.168.242.44/sceapp/focus_static/wap/phone/img/location.png" class="location_icon" alt="loaction" />
            </a>
        </div> 
        <span class="arrow"></span>
    </div>
    <section class="section_content">
        <div class="no_consult_container">
         你好，小编正在回答问题中，请耐心等候......
	</div>
    </section>

    <script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/sohunews/snippets/common.js"></script>
    <%@ include file="/views/pv.jsp"%>
</body>
</html>