<%@page import="cn.focus.dc.focuswap.model.TuanGou"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>${tuangou.active_name}－${_city.cityName }楼盘团购-${_city.cityName }搜狐焦点网</title>
<meta name="Keywords" content="${tuangou.active_name}">
<meta name="Description" content="${tuangou.active_desc}">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/tuangou_pad.css" />
<style>
@media screen and (max-width: 768px){
    .tuangou_pad_detail .right {
        width: 215px;
    }
    .tuangou_list_otherItem {
        height: auto;
        -webkit-box-orient:vertical;
        padding-bottom: 0;
    }
    .tuangou_list_otherItem .house_detail {
        margin-top: 10px;
    }
}
</style>
<script data-main="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/modulejs/app_tuangou" src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/modulejs/lib/require.js" data-module="detail"></script>
</head>
<body>
<!--顶部导航 -->
<!-- 新版样式导航 --> 
	<jsp:include page="/views/padHeader.jsp">
		<jsp:param value="详情" name="page_title" />
	</jsp:include>

<!--团购列表pad版部分-->
<section class="tuangou_pad_module">
    <div class="tuangou_pad_detail">
        <!--团购列表pad版详情左侧-->
        <div class="left">
            <div class="tuangou_list_main2">
                <div class="tuangou_list_title tuangou_list_title_big">${tuangou.active_name}</div>
                <a class="tuangou_list_img tuangou_list_img2 tuangou_list_img3">
                    <img src="${tuangou.photo}" alt="搜狐焦点">
                    <div class="tuangou_list_img_bar tuangou_list_img_bar2">
                        <p class="name">${tuangou.proj_name}</p>
                        <p class="price"><span>${tuangou.price}</span>${tuangou.price_unit}</p>
                    </div>
                </a>
                <div class="tuangou_list_intro tuangou_list_intro2">${tuangou.active_desc}</div>
                <div class="tuangou_list_lableGroup clearfix">
                    <a class="person"><i></i><span>${tuangou.apply_num}</span> 人已报名</a>
                    <a class="day"><i></i><span>${tuangou.timeLeft}</span> 天后结束</a>
                    <a class="service"><i></i>支持服务费退还</a>
                    <a class="service"><i></i>会员享受优惠</a>
                </div>
                <div class="tuangou_list_huodongIntro">活动说明：<a href="/${_city.cityPinyinAbbr }/tuangou/shuoming/">团购流程及注意事项</a></div>
                <div class="tuangou_list_btnGroup tuangou_list_btnGroup2">
                    <a href="${ctx }/${_city.cityPinyinAbbr }/tuangou/${tuangou.active_id}/apply/">立即报名</a>
                </div>
            </div>
            <div class="tuangou_list_share">
            </div>
            <div class="tuangou_list_main2">
                <div class="tuangou_list_title">团购楼盘</div>
                <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${tuangou.group_id}/" class="tuangou_list_img tuangou_list_img2">
                    <img src="http://restapi.amap.com/gss/simple?sid=3027&maplevel=6&amp;width=300&amp;height=150&amp;ia=1&amp;content=MAP&amp;encode=UTF-8&amp;xys=${tuangou.lng},${tuangou.lat}&amp;key=9e4b883b2a6d8482638c56b6f60078b7&iconheight=32&iconwidth=30&icons=http://webapi.amap.com/images/marker_sprite.png" alt="焦点楼盘团购">
                    <div class="tuangou_list_img_bar tuangou_list_img_bar2">
                        <p class="name">${tuangou.proj_name}</p>
                    </div>
                </a>
                <div class="tuangou_list_houseInfo">
                    <div class="tuangou_list_hosueItem">
                        <div class="name">楼盘地址：</div>
                        <div class="info">${tuangou.address}</div>
                    </div>
                    <div class="tuangou_list_hosueItem">
                        <div class="name">楼盘热线：</div>
                        <a class="info red" href="tel:${phone_dial}">${tuangou.phone}</a>
                    </div>
                    <div class="tuangou_list_hosueItem">
                     <c:if test="${not empty pft}">
                        <div class="name">免费看房：</div>
                        <a href="/${_city.cityPinyinAbbr}/pafangtuan/${pft.lineId}" class="info red">${pft.title}</a>
                     </c:if>
                    </div>
                </div>
                <div class="tuangou_list_btnGroup tuangou_list_btnGroup2">
                    <a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${tuangou.group_id}/" class="btnStyle2">查看详情</a>
                </div>
            </div>
        </div>
        <div class="tuangou_pad_lineY"></div>
        <!--团购列表pad版详情右侧-->
        <div class="right">
            <div class="tuangou_list_main2">
                <div class="tuangou_list_title">其它团购</div>
                <div class="tuangou_list_otherHouse">
                	<c:forEach items="${recommend}" var="item" varStatus="Status">
                
                    <a class="tuangou_list_otherItem" href="${ctx}/${_city.cityPinyinAbbr}/tuangou/${item.active_id}/">
                        <div class="house_img">
                            <img src="${item.photo}"><div class="house_where">${item.location}</div>
                            <div class="tuangou_list_img_bar">
                                <p class="name name2">${item.active_name}</p>
                            </div>
                        </div>
                        <div class="house_detail">
                            <p class="house_area">${item.active_desc}</p>
                            <div class="house_price"><span class="red">${item.price}</span>${item.price_unit}<div class="house_person">已报名 <span class="red">${item.apply_num}</span> 人</div></div>
                        </div>
                    </a>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</section>

<section class="crumb">
    <div class="inner">
       <a href="/${_city.cityPinyinAbbr }/">新房</a><span class="h_space1">></span><a href="/${_city.cityPinyinAbbr }/tuangou/${tuangou.active_id}/">团购详情</a>
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