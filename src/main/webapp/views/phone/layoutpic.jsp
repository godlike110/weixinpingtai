<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>${info.projName }-${_city.cityName }${info.projName }户型图相关信息-搜狐焦点</title>
<meta name="Keywords" content="${info.projName }-${_city.cityName }${info.projName }户型图相关信息-搜狐焦点">
<meta name="Description" content="搜狐焦点${_city.cityName }房产网为广大网友提供了最新的${_city.cityName }楼盘信息，最准确的${_city.cityName }房价情况和最及时的${_city.cityName }新房资讯等，是买房、购房首选网站。  ">
<%@ include file="/views/icon.jsp" %>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_apartmentpicture_v1.0.css">
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
<script type="text/javascript">
var city = ${cityStr};
var groupId = ${groupId}; 
var phone400 = ${phone400};
var type = "${defaultTypeId}";
var photos = ${dataStr};
</script>
</head>
<body class="wap_apartmentpicture_all">
	<div class="wap_container">
		<!-- 户型图—顶部导航 -->
		<header class="top_nav">
		<c:if test="${returnFlag=='0' }">
		<a href="javascript:history.back();" class="go_back" data-title="返回上一级"></a>
		</c:if>
		<c:if test="${returnFlag=='1' }">
			<a href="${ctx}/${_city.cityPinyinAbbr}/" class="go_back" data-title="返回上一级">首页</a>
		</c:if>
			<span class="search_title">户型图</span>
			<span class="select_menu pa right0 top0" role="select_menu" role-addClass="select_menu_shouqi1" data-title="户型筛选">筛选<em class="select_menu_btn"></em></span>		
			<ul class="nav_box_ul1 apartmentpicture_menu_ul1">
				<li><a href="#" role-current="current" class="current first">全部户型</a></li>
				<c:forEach var="photos" items="${data}">
				<li><a href="#">${photos.minMaxArea}</a></li>
				</c:forEach>
			</ul>
		</header>
		
		<!-- 户型图主要内容板块 // -->
		<section class="wap_main apartmentpicture_main">			
			<section class="wap_module apartment_picture_container" data-title="户型图-模块-内容">
				<div class="loupan_img_container" id="scroller">
					<ul class="loupan_img_ul">
					   <c:forEach var="photos" items="${data}">
					   <c:if test="${defaultTypeId==photos.type}">
					    <c:forEach var="photo" items="${photos.photoes}">
						<li class="its_img_box">
							<div class="its_name1">
								<p class="ots_tit">${photo.title}</p>
								<p class="ots_huxing1">${photo.typeClassId } &nbsp &nbsp ${photo.buildArea}</p>
							</div>
							<div class="its_img1_father">
								<img class="its_img1" src="http://192.168.242.44/sceapp/focus_static/wap/phone/img/phone_default_img.gif" data-src="${photo.realPath}" alt="搜狐焦点图片" />
								<a href="${photo.realPath}" role="text" url="${photo.realPath}" target="_blank" class="goto_newpic_page"></a>
							</div>
						</li>
						</c:forEach>
						</c:if>
						</c:forEach>
					</ul>
					<div class="loupan_point_boxs tc">1/${pics.count}</div>
				</div>
			</section>
			
            <!-- 面包屑 -->
			<div class="link_boxs">
				<a href="${ctx}/${_city.cityPinyinAbbr}/">新房</a><span class="h_space1">&gt;</span><a href="${ctx}/${_city.cityPinyinAbbr}/loupan/"> ${_city.cityName }楼盘</a><span class="h_space1">&gt;</span><a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/"> ${buildName}</a><span class="h_space1">&gt;</span> 户型图
			</div>
		 <c:if test="${info.phone400!=0 }">
		<!-- 400电话 -->
		<div class="hotline_phone">
			<a href="tel:4008882200,${info.phone400 }" class="hotline_phone_btn" phone="${info.phone400}"><span class="pnone_icon"></span>免费咨询 400-888-2200 转 ${info.phone400 }</a>
		</div> 
		</c:if>
		<c:if test="${info.phone400==0 }">
				<!-- 400电话 -->
		<div class="hotline_phone">
			<a href="tel:4006782020" class="hotline_phone_btn" phone="${info.phone400}"><span class="pnone_icon"></span>免费咨询 400-678-2020</a>
		</div>
		</c:if>
			<div class="back2top" data-title="返回顶部"></div>
			
		</section>
		<!-- // 户型图主要内容板块 -->
	</div>

	<!-- 电话弹层 // -->
	<div class="over"></div>
	<div class="alert_box" id="check_net">
     	<p class="p1">请在接通后拨分机号</p>
     	<p class="p1">${info.phone400 }</p>
     	<div class="itos_btn1" id="check_net_confirm"><a href="javascript:;">好的</a></div>
		<div class="its_close_btn"></div>
    </div>

	<!-- // 电话弹层 -->
	
	<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_apartment_picture_v1.0.js"></script>
	<%@ include file="/views/pv.jsp"%>
	<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>
