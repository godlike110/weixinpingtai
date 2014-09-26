<%@page import="cn.focus.dc.focuswap.model.TuanGou"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>${tuangou.active_name}－${_city.cityName  }楼盘团购-${_city.cityName }搜狐焦点网</title>
<meta name="Keywords" content="${tuangou.active_name}">
<meta name="Description" content="${tuangou.active_desc}">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/tuangou.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<script data-main="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/modulejs/app_tuangou" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/modulejs/lib/require.js" data-module="detail"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
</head>
<body>
<!--顶部导航 -->
<!-- 新版样式导航 --> 
	<jsp:include page="/views/phoneHeader.jsp">
		<jsp:param value="详情" name="page_title" />
	</jsp:include>

<!-- 团购页—详情模块 -->
<section class="tuangou_module">
    <div class="tuangou_list_box tuangou_list_box2">
        <div class="tuangou_list_main2">
            <div class="tuangou_list_title">${tuangou.active_name}</div>
            <div class="tuangou_list_img tuangou_list_img2 tuangou_list_img3">
                <img src="${tuangou.photo}" alt="搜狐焦点">
                <div class="tuangou_list_img_bar">
                    <p class="name">${tuangou.proj_name}</p>
                    <p class="price"><span>${tuangou.price}</span>${tuangou.price_unit}</p>
                </div>
            </div>
            <div class="tuangou_list_intro">${tuangou.active_desc}</div>
            <div class="tuangou_list_lableGroup clearfix">
                <a class="person"><i></i><span>${tuangou.apply_num}</span> 人已报名</a>
                <a class="day"><i></i><span>${tuangou.timeLeft}</span> 天后结束</a>
                <a class="service"><i></i>支持服务费退还</a>
                <a class="service"><i></i>会员享受优惠</a>
            </div>
            <div class="tuangou_list_huodongIntro">活动说明：<a href="/${_city.cityPinyinAbbr }/tuangou/shuoming/"><span>团购流程及注意事项</span></a></div>
            <div class="tuangou_list_btnGroup tuangou_list_btnGroup2">
                <a href="${ctx }/${_city.cityPinyinAbbr }/tuangou/${tuangou.active_id}/apply/">立即报名</a>
            </div>
        </div>
        <div class="tuangou_list_share">
        </div>
    </div>

    <div class="tuangou_list_box tuangou_list_box2">
        <div class="tuangou_list_main2">
            <div class="tuangou_list_title">团购楼盘</div>
            
            <a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${tuangou.group_id}/" class="tuangou_list_img tuangou_list_img2">
                <img src="http://restapi.amap.com/gss/simple?sid=3027&maplevel=6&amp;width=300&amp;height=150&amp;ia=1&amp;content=MAP&amp;encode=UTF-8&amp;xys=${tuangou.lng},${tuangou.lat}&amp;key=9e4b883b2a6d8482638c56b6f60078b7&iconheight=32&iconwidth=30&icons=http://webapi.amap.com/images/marker_sprite.png" alt="搜狐焦点">
                <div class="tuangou_list_img_bar">
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
                    	<div class="name">免费看房：</div><a class="info red" href="/${_city.cityPinyinAbbr}/pafangtuan/${pft.lineId}">${pft.title}</a></div>
                    </c:if>
                </div>
            </div>
            <div class="tuangou_list_btnGroup tuangou_list_btnGroup3">
                <a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${tuangou.group_id}/" class="btnStyle1">查看详情</a>
            </div>
        </div>
    </div>

    
    <div class="tuangou_list_box tuangou_list_box2" style="margin-top: 20px">
        <div class="tuangou_list_main2">
            <div class="tuangou_list_title">其它团购</div>
            <div class="tuangou_list_otherHouse">
               	<c:forEach items="${recommend}" var="item" varStatus="Status">
                <a href="${ctx}/${_city.cityPinyinAbbr}/tuangou/${item.active_id}/" class="tuangou_list_otherItem">
                    <div class="house_img"><img src="${item.photo}"　alt="搜狐焦点"><div class="house_where">${item.location}</div></div>
                    <div class="house_detail">
                        <p class="house_title text_more">${item.active_name}</p>
                        <div class="house_price"><span class="red">${item.price}</span>${item.price_unit}<div class="house_person">已报名 <span class="red">${item.apply_num}</span> 人</div></div>
                        <p class="house_area">${item.active_desc}</p>
                    </div>
                </a>
                </c:forEach>
            </div>
        </div>
    </div>   
    
</section>

<!-- 面包屑 -->
<div class="link_boxs"><a href="/${_city.cityPinyinAbbr }/">新房</a><span class="h_space1">></span><a href="/${_city.cityPinyinAbbr }/tuangou/${tuangou.active_id}/">团购详情</a></div>

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

<!-- 拨号提醒模块 -->
<div class="tuangou_phone_tips">
    <div class="mask"></div>
    <div class="window">
        <p>接通<span></span>后，</p>
        <p>请手动拨打分机号<span></span></p>
        <a>好的</a>
    </div>
</div>
<div class="back2top" data-title="返回顶部"></div>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>
