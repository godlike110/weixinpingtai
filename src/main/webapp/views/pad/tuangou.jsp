<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>${_city.cityName }楼盘团购_${_city.cityName }团购楼盘_${_city.cityName}新房买房团购_${_city.cityName }搜狐焦点网</title>
<meta name="Keywords" content="${_city.cityName }楼盘团购,${_city.cityName }团购楼盘,${_city.cityName }新房团购">
<meta name="Description" content="${_city.cityName }搜狐焦点网为您提供${_city.cityName }楼盘团购、${_city.cityName }团购楼盘、${_city.cityName }新房团购等买房团购信息">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/tuangou_pad.css" />
<style>
@media screen and (max-width: 768px){
    .tuangou_pad_module  {
        padding: 20px 50px;
    }
    .tuangou_pad_list li {
        margin-left: 20px;
        margin-bottom: 20px;
    }
}
</style>
<script data-main="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/modulejs/app_tuangou" src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/modulejs/lib/require.js" data-module="list"></script>
</head>
<body data-city="${_city.cityPinyinAbbr}">
		<!-- 新版样式导航 -->
		<jsp:include page="/views/padHeader.jsp">
			<jsp:param value="楼盘团购" name="page_title" />
		</jsp:include>

<!--团购列表pad版部分-->
<section class="tuangou_pad_module">
    <div class="tuangou_pad_list">
        <ul class="clearfix">
        <c:forEach var="item" items="${tgList}" varStatus="status">
	<c:if test="${item.timeLeft>=0}">
            <li>
                <div class="tuangou_list_top">
                    <div class="tuangou_list_top_last">还有${item.timeLeft}天结束</div>
                    <div class="tuangou_list_top_num">已报名 <span class="red">${item.apply_num}</span> 人</div>
                </div>
                <div class="tuangou_list_main">
                    <div class="tuangou_list_title">${item.active_name}</div>
                    <a href="${ctx }/${_city.cityPinyinAbbr }/tuangou/${item.active_id}/" class="tuangou_list_img">
                        <img src="${item.photo}" alt="">
                        <div class="tuangou_list_img_area">${item.location}</div>
                        <div class="tuangou_list_img_bar">
                            <p class="name">${item.proj_name}</p>
                            <p class="price"><span>${item.price}</span>${item.price_unit}</p>
                        </div>
                    </a>
                    <div class="tuangou_list_intro">${item.active_desc}</div>
                    <div class="tuangou_list_btnGroup">
                        <a href="${ctx }/${_city.cityPinyinAbbr }/tuangou/${item.active_id}/" class="btnStyle1">查看详情</a>
                        <a href="${ctx }/${_city.cityPinyinAbbr }/tuangou/${item.active_id}/apply/">立即报名</a>
                    </div>
                </div>
            </li>
                </c:if>
    </c:forEach>
        </ul>
    </div>
</section>

<div id="more">
    <a href="javascript:;" class="load_more"><b class="rotating"></b>加载更多中...</a>
</div>

<section class="crumb">
    <div class="inner">
<a href="/${_city.cityPinyinAbbr }/">新房</a><span class="h_space1">></span><a href="/${_city.cityPinyinAbbr }/tuangou/">团购</a>
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
    <a href="javascript:;" id="to_top">返回顶部</a>
</footer>
</body>
</html>

</html>