<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<%-- <title>${info.projName }-${_city.cityName }${info.projName }相关信息-搜狐焦点</title>
<meta name="Keywords" content="${_city.cityName }楼盘, ${_city.cityName }房价, ${_city.cityName }新房">
<meta name="Description" content="搜狐焦点${_city.cityName }房产网为广大网友提供了最新的${_city.cityName }楼盘信息，最准确的${_city.cityName }房价情况和最及时的${_city.cityName }新房资讯等，是买房、购房首选网站。 "> --%>
<meta name="Keywords" content="${info.projName }，${_city.cityName }${info.projName }">
<meta name="Description" content="搜狐焦点${_city.cityName }${info.projName }楼盘频道为您介绍${info.projName }楼盘最新动态、开盘日期、售楼处销售电话、${info.projName }房价走势、户型图、地址、周边交通等楼盘信息。">
<title>${info.projName }-楼盘详情-${_city.cityName }搜狐焦点</title>
<%@ include file="/views/icon.jsp" %>
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/loupan/style/plubic.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
<script src="http://192.168.242.44/sceapp/focus_static/wap/loupan/js/modernizr.custom.min.js"></script>
<script type="text/javascript"  src="http://webapi.amap.com/maps?v=1.2&key=9e4b883b2a6d8482638c56b6f60078b7"></script>
<!--竖屏-->
<link rel="stylesheet" media="all and (orientation:portrait)" href="http://192.168.242.44/sceapp/focus_static/wap/loupan/style/wap_detail_pad_portrait.css">
<link rel="stylesheet" media="all and (orientation:portrait)" href="http://192.168.242.44/sceapp/focus_static/wap/loupan/style/component_pad_portrait.css">
<!--横屏-->
<link rel="stylesheet" media="all and (orientation:landscape)" href="http://192.168.242.44/sceapp/focus_static/wap/loupan/style/wap_detail_pad_landscape.css">
<link rel="stylesheet" media="all and (orientation:landscape)" href="http://192.168.242.44/sceapp/focus_static/wap/loupan/style/component_pad_landscape.css">
<script type="text/javascript">
var city = ${cityStr};
var groupId = ${groupId};
var phone400 = ${phone400};
</script>
</head>

<body class="refer_list_body">

	<!-- 新版样式导航 -->
		<jsp:include page="/views/padHeader.jsp">
			<jsp:param value="详情" name="page_title" />
		</jsp:include>

<section class="content_box">
<aside class="scroll_mod" id="left_side" >
  <div class="scroller">
  <div class="box_left_box">
 <!-- 头图开始 -->
      <div class="box1"> <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/tupian/-1/"> <img src="${info.logUrl}" width="100%" alt="${info.projName }"/>
        <div class="box_box1"></div>
        <div class="box_box2"><span class="text_color_og">${info.priceForShow}</span></div>
        <div class="box_box3"></div>
        <div class="box_box4"><strong> <img src="http://192.168.242.44/sceapp/focus_static/wap/loupan/images/icon_composition_pad.png" width="24" height="24" /></strong>
        
        ${buildingpiccount}
        
        </div>
        </a> </div>
 <!-- 头图结束 -->
 <!-- 楼盘基本信息开始 -->
      <div class="box2">
        <h1>${info.projName} 
		
          <c:choose>
          <c:when test="${info.saleStatus == 0}">
          <span class="status sale">在售</span>
          </c:when>
          <c:when test="${info.saleStatus == 1}">
          <span class="status wait">待售</span>
          </c:when>
          <c:when test="${info.saleStatus == 2}">
          <span class="status remain">尾盘</span>
          </c:when>
          <c:otherwise>
          <span class="status over">售罄</span>
          </c:otherwise>
          </c:choose>
</h1>
        <ul>
          <li><span>位&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;置：</span><p>
						 <c:choose><c:when test="${empty fn:trim(info.areaDetail)}">暂无</c:when>
						<c:otherwise>${info.areaDetail}</c:otherwise></c:choose>
						</p></li>
          <div class="clear"></div>
          <li><span>&nbsp;</span>
            <strong><c:choose><c:when test="${empty fn:trim(info.projAddress)}">暂无</c:when>
						<c:otherwise>${info.projAddress}</c:otherwise></c:choose></strong>
          </li>
          <div class="clear"></div>
          <li><span>开盘时间：</span><p><c:choose><c:when test="${empty fn:trim(info. saleDateDetail)}">暂无</c:when>
						<c:otherwise>${info. saleDateDetail }</c:otherwise></c:choose></p></li>
          <div class="clear"></div>
          <li><span>入住时间：</span><p><c:choose><c:when test="${empty fn:trim(info. moveInDateDetail)}">暂无</c:when>
						<c:otherwise>${info. moveInDateDetail }</c:otherwise></c:choose></p></li>
          <div class="clear"></div>
        </ul>
        <h3><c:choose><c:when test="${empty fn:trim(info. projTypeDetail)}">暂无</c:when>
						<c:otherwise>${info. projTypeDetail }</c:otherwise></c:choose></h3>
        <div class="box2_box1">
          <!--div class="box2_box1_box1"><a href="#">分享</a></div>-->
          <!--div class="box2_box1_box2"><a href="#">收藏</a></div>-->
          <div class="clear"></div>
        </div>
      </div>
      <div class="box2_2"><a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/xiangxi/" >查看详情</a></div>
 <!-- 楼盘基本信息结束 -->
      
<!--主编评语开始 -->
${editorcomment}
 <!-- 主编评语结束 -->
 <!-- 楼盘动态开始 -->
${hotnews}
 <!-- 楼盘动态结束 -->
      
<!-- 户型图开始 -->
${layouts}
<!-- 户型图结束 --> 
      
<!-- 楼盘咨询开始 -->
${questions}
<!-- 楼盘咨询结束 --> 
      
<!-- 位置及周边开始 -->
        <c:if test="${empty ditu }">
      <div class="box8">
    
        <h2>位置及周边</h2>
        
        <div class="box8_content"> <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/ditu/"> <img src="http://restapi.amap.com/gss/simple?sid=3027&maplevel=6&amp;width=300&amp;height=150&amp;ia=1&amp;content=MAP&amp;encode=UTF-8&amp;xys=${info.longitude},${info.latitude}&amp;key=9e4b883b2a6d8482638c56b6f60078b7&iconheight=32&iconwidth=30&icons=http://webapi.amap.com/images/marker_sprite.png" width="100%" width="100%" alt="${info.projName }"/>
          <div class="box8_box1"></div>
          <div class="box8_box2">${info.projAddress}</div>
          </a> </div>
        <div class="box8_ul"> <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/ditu/">
          <ul id="arround" type="pad"></ul>
          </a> </div>
      </div>
        </c:if>
        
      <%-- <c:if test="${not empty ditu }">
      <div class="box8">
    
        <h2>位置及周边</h2>
        
        <div class="box8_content"> <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/ditu/">
          <div class="box8_box1"></div>
          <div class="box8_box2">${info.projAddress}</div>
          </a> </div>
        <div class="box8_ul"> <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/ditu/">
          <ul id="arround" type="pad"></ul>
          </a> </div>
      </div>
      </c:if> --%>
<!-- 位置及周边结束 --> 
      
    </div>

  </div>
</aside>
<section class="scroll_mod wrapper bg_all" id="right_side">
  <div class="scroller">

 	<div class="box_right_box"> 
<!-- 近期看房团开始 -->
${pft}
<!-- 近期看房团结束 --> 
<!-- 楼盘导购开始 -->
${propose}
<!--楼盘导购结束--> 
<!-- 周边楼盘开始 -->
${arroundbuilding}
<!-- 周边楼盘结束 --> 
 </div>
  </div>
</section>
<div class="position">
  <p><a color=#aaa href="${ctx}/${_city.cityPinyinAbbr}/"><font color=#aaa>新房</font></a> > <a color=#aaa href="${ctx}/${_city.cityPinyinAbbr}/loupan/"><font color=#aaa>${_city.cityName }楼盘</font></a> > <a color=#aaa href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/"> <font color=#aaa>${buildName}</font></a></p>
</div>
<div>
<%-- <c:if test="${info.phone400!=0 && empty special}">
<div class="contact">
  <a href="tel:4008882200,${info.phone400 }" class="free_advice"><b></b>免费咨询 400-888-2200 转 ${info.phone400 }</a>
  <div class="clear"></div>
</div>
</c:if>
<c:if test="${info.phone400!=0 && not empty special}">
<div class="contact">
  <a href="tel:4001080000,${info.phone400 }" class="free_advice"><b></b>免费咨询 400-108-0000 转 ${info.phone400 }</a>
  <div class="clear"></div>
</div>
</c:if>

<c:if test="${info.phone400==0 }">
<div class="contact">
  <a href="tel:4006782020" class="free_advice"><b></b>免费咨询 400-678-2020</a>
  <div class="clear"></div>
</div>
</c:if> --%>
	<c:if test="${info.phone400 != 0 }">
		<footer class="position_fixed">
			<div><a href="tel:4008882200,${info.phone400}" class="footer_ban_tel"><b></b>联系售楼处</a></div>
			<div><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${info.groupId}/woyaozixun" class="footer_ban_download"><b></b>在线咨询</a></div>
		</footer>
	</c:if>
	<c:if test="${info.phone400 != 0 }">
		<footer class="position_fixed">
			<div><a href="tel:4001080000,${info.phone400}" class="footer_ban_tel"><b></b>联系售楼处</a></div>
			<div><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${info.groupId}/woyaozixun" class="footer_ban_download"><b></b>在线咨询</a></div>
		</footer>
	</c:if>
	<c:if test="${info.phone400==0 }">
		<footer class="position_fixed">
			<div><a href="tel:4006782020" class="free_advice"><b></b>联系售楼处 </a></div>
			<div><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${info.groupId}/woyaozixun" class="footer_ban_download"><b></b>在线咨询</a></div>
		</footer>
	</c:if>

</div>

<!--弹出-->

<div class="md-overlay"></div>
<div id="mockContainer" style="display:none;"></div><!-- for amap -->
<!-- the overlay element -->
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
$(".md-trigger").click(function(){
$("body").css("overflow", "hidden");
})
$(".md-overlay,.md-close").click(function(){
$("body").css("overflow", "visible");
})});
</script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/loupan/js/wap_pad.js"></script> 
<script src="http://192.168.242.44/sceapp/focus_static/wap/loupan/js/classie.js"></script> 
<script src="http://192.168.242.44/sceapp/focus_static/wap/loupan/js/modalEffects.js"></script> 
<script src="http://192.168.242.44/sceapp/focus_static/wap/loupan/js/iscroll.js"></script> 
<script src="http://192.168.242.44/sceapp/focus_static/wap/loupan/js/sohu.focus.mobile.detail.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/loupan/js/buildinghome.map.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
<%@ include file="/views/pv.jsp"%>
<!--弹出-->
</body>
</html>

