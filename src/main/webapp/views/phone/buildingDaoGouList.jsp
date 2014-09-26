<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<%@ include file="/views/icon.jsp"%>
<title>【${_city.cityName }楼盘|${_city.cityName }房价|${_city.cityName }新房】-搜狐焦点${_city.cityName}房产</title>
<meta name="Keywords" content="${_city.cityName}楼盘, ${_city.cityName}房价, ${_city.cityName}新房">
<meta name="Description" content="搜狐焦点${_city.cityName}房产网为广大网友提供了最新的${_city.cityName}楼盘信息，最准确的${_city.cityName}房价情况和最及时的${_city.cityName}新房资讯等，是买房、购房首选网站。 ">
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
<script>
	//var city = {"ajaxUrl":"/bj/zixun/listajax/TOP/","cityCoding":"010","cityId":1,"cityName":"北京","cityPinyin":"beijing","cityPinyinAbbr":"bj","domainName":"house.focus.cn/?cfrom=mobile","priority":1,"singleUrl":"/bj/zixun/","type":"1"};
    var city = ${city};
	//var hasNext = "";
var hasNext = ${hasNext};
</script>
</head>
<body class="wap_daogou_all wap_baodian_all">
	<div class="wap_container">
			<!-- 新版样式导航 -->
			<jsp:include page="/views/phoneHeader.jsp">
				<jsp:param value="楼盘导购" name="page_title" />
			</jsp:include>
			
			<!-- 楼盘导购列表页主要内容板块 // -->
			<section class="hotquestion_list_main">
				<div class="hotquestion_list_container">	
					<section class="dynamic_info_container" data-title="楼盘导购列表">
						<div class="dynamic_info_ask">
							<ul class="daogoulist_ul1">
							<c:forEach var="dg" items="${dgList }" varStatus="status">
								<li>
									<a href="${ctx }/${_city.cityPinyinAbbr }/daogou/${dg.id}/" role="text">
										<div class="its_biaoti">
											<div class="fr"><span class="its_yuedushu">${dg.totalPublish }</span><span class="its_sees">${dg.totalViews }</span></div>
											<div class="edit_photo"><img src="${dg.editorPic }" alt="${dg.editorName }" /></div>
											<span class="editor">${dg.editorTitle } ${dg.editorName }</span>
										</div>
										<div class="img_box1">
											<img src="${dg.pic }" alt="${dg.title }" />
											<div class="its_abouts "><p>${dg.title }</p></div>
										</div>
										<p class="its_summary"><span class="fr its_time">${dg.onlineTime }</span>${dg.tab }</p>
									</a>
								</li>
							</c:forEach>
							</ul>
						</div>
						<!-- pull up -->
						<c:if test="${hasNext == 'true' }">
						<div class="pull font_gray">
							<div class="icon fl"></div>
							<p>滑动加载更多</p>
						</div>
						</c:if>
					</section>
				</div>
			</section>
			
			<!-- 面包屑 -->
			<div class="link_boxs"><a href="${ctx }/${_city.cityPinyinAbbr }/">新房</a><span class="h_space1">></span>
			楼盘导购
			</div>
			<!-- 底部导航 // -->
			<footer class="foot_nav">
				<div class="foot_nav_copyright">©2014 搜狐焦点</div>
				<div class="foot_nav_box2">
					<span class="wap_version" role="wap_version_menu">手机版</span>
					<ul class="nav_box_ul1 wap_version_menu_ul1">
						<li><a href="javascript:;" role="wap_version_text" url="" data-nr="PHONE" class="current">手机版</a></li>
						<li><a href="http://${_city.sourceDomainName }/daogou?cfrom=mobile" role="wap_version_text" data-go="false" url="" data-nr="PC">电脑版</a></li>
					</ul>
				</div>
			</footer>
			<!-- // 底部导航 -->

	</div>
	
	<div class="back2top" data-title="返回顶部"></div>
	
	<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_daogoulist_new_v1.0.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
<%@ include file="/views/pv.jsp"%>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>