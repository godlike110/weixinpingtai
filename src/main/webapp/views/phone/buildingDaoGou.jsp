<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<%@ include file="/views/icon.jsp"%>
<title>【${_city.cityName }楼盘|${_city.cityName }房价|${_city.cityName }新房】-搜狐焦点${_city.cityName }房产</title>
<meta name="Keywords" content="${_city.cityName }楼盘, ${_city.cityName }房价, ${_city.cityName }新房">
<meta name="Description" content="${dg.title }">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
<script>
var city = ${city};
</script>
</head>
<body class="wap_daogou_info_all wap_baodian_all">
	<div class="wap_container">
			<!-- 新版样式导航 -->
		<jsp:include page="/views/phoneHeader.jsp">
			<jsp:param value="详情" name="page_title" />
		</jsp:include>
		
		<!-- 主要内容板块 // -->
		<section class="hotquestion_list_main">
			<div class="hotquestion_list_container">	
				<section class="daogouinfo_new_container" data-title="楼盘导购正文">
						<div class="daogouinfo_new_imgbox">
							<div class="daogouinfo_new_img1">
								<img src="${dg.pic }" alt="${dg.title }" id = "wx_img1" />
								<div class="edit_photo_shadow1"></div>
							</div>
							<div class="daogouinfo_new_editors">
								<div class="edit_photo">
									<img src="${dg.editorPic }" alt="${dg.editorName }" />
								</div>
								<div class="editor">
									<span class="fr"><span class="its_yuedushu">${dg.totalPublish }</span><span class="its_sees">${dg.totalViews }</span></span>
									<span>${dg.editorTitle } ${dg.editorName }</span>
								</div>
							</div>
						</div>						
						<p class="its_biaoti">${dg.title }</p>
						<p class="its_writer_time"><span class="its_date">${dg.onlineTime }</span></p>
						<div class="dynamic_info_nr">${dg.content }
						</div>
						
						<c:forEach items="${dg.contentList }" var="cont" varStatus="status">
						<h2>${cont.buildPicName }</h2>
						<div class="img_box1"><img src="${cont.buildPicBig }" alt="${cont.buildPicName }" /></div>
						<p class="its_pra">${cont.content }</p>
						<h3 class="its_loupaninfo"></h3>
						<ul class="search_list_container" data-title="搜索结果-列表">
							<li>
								<a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${cont.groupId}/?fromId=dg_${dg.id}" role="text" url="" class="clearfix">
									<div class="fl its_img"><img alt="${cont.buildPicName }" src="${cont.buildPic }"></div>
									<div class="its_content">
										<p class="it_name">${cont.buildPicName }</p>
										<p class="it_address">${cont.buildLocation} ${cont.buildAddress }</p>
										<c:if test="${not empty fn:trim(cont.buildPrice) }">
										<p class="it_price">
										<em>${cont.buildPrice }</em></p>
										</c:if>
										<c:if test="${empty fn:trim(cont.buildPrice) }">
										<p class="it_price">
										<em>价格待定</em></p>
										</c:if>
									</div>
								</a>
							</li>
						</ul>
						<div class="look_more">
						<c:choose>
						<c:when test="${not empty fn:trim(cont.phone400) }">
						<a href="tel:${cont.phone400 }" data-phone400="${cont.phoneFenji}" data-groupId="${cont.groupId}" role="text" url="" class=""><em class="phone_ask_icon"></em>免费电话咨询</a>
						</c:when>
						<c:otherwise><a href="tel:4008882200" role="text" url="" class=""><em class="phone_ask_icon"></em>客服免费电话咨询</a></c:otherwise>
						</c:choose>
						</div>
						</c:forEach>
						<div class="addfavorite hide">
							<!--  <span class="addfavorite_btn1">收藏</span>-->
							<span class="webshare_btn1">分享</span>
						</div>
						<!-- 模仿pad版 -->
						<div class="share_wb">分享到：
							<a href="${sWeibo }" target="_blank" class="sina_wb"></a>
							<a href="${qZone }" target="_blank" class="qq_zone"></a>
						</div>
						<c:if test="${not empty fn:trim(dg.tab) }">
						<div class="daogoulist_tags">标签：<em>${dg.tab }</em></div>
						</c:if>
					</section>
				</div>
			</section>
				
				
			</div>
		</section>
		

		<!-- 面包屑 -->
		<div class="link_boxs"><a href="${ctx }/${_city.cityPinyinAbbr }/">新房</a><span class="h_space1">></span>
		<a href="${ctx }/${_city.cityPinyinAbbr }/daogou/">楼盘导购</a><span class="h_space1">></span>
		导购正文
		</div>
		
		<div class="over"></div>
        <div class="alert_box" id="check_net">
     	    <p class="p1">请在接通后拨分机号</p>
     	    <p class="p1">1234</p>
     	    <div class="itos_btn1" id="check_net_confirm"><a href="javascript:;">好的</a></div>
		    <div class="its_close_btn"></div>
        </div>
		
		
		
		<!-- 底导 -->
		<footer class="foot_nav">
			<div class="foot_nav_copyright">©2014 搜狐焦点</div>
			<div class="foot_nav_box2">
				<span class="wap_version" role="wap_version_menu">手机版</span>
				<ul class="nav_box_ul1 wap_version_menu_ul1">
					<li><a href="javascript:;" role="wap_version_text" url="" data-nr="PHONE" class="current">手机版</a></li>
					<li><a href="http://${_city.sourceDomainName }/daogou/${dg.id }.html?cfrom=mobile" role="wap_version_text" data-go="false" url="" data-nr="PC" >电脑版</a></li>
				</ul>
			</div>
		</footer>
		<div class="back2top" data-title="返回顶部"></div>
	</div>
	<script type="text/javascript">

	function getById(id){ return document.getElementById(id); }
	$().ready(function(){
		function _WXShare(){
			var imgObj = getById("wx_img1"),
				imgUrl= imgObj.getAttribute("src"),
				width=300,
				height=300,
				title='${dg.title }',
				desc='${dg.content }',
				url=document.location.href,
				appid=''; //APPID(一般不填)
			//内置方法
			function _ShareFriend() {
				WeixinJSBridge.invoke('sendAppMessage',{
					  'appid': appid,
					  'img_url': imgUrl,
					  'img_width': width,
					  'img_height': height,
					  'link': url,
					  'desc': desc,
					  'title': title
					  }, function(res){
						_report('send_msg', res.err_msg);
				  })
			}
			function _ShareTL() {
				WeixinJSBridge.invoke('shareTimeline',{
					  'img_url': imgUrl,
					  'img_width': width,
					  'img_height': height,
					  'link': url,
					  'desc': desc,
					  'title': title
					  }, function(res) {
						_report('timeline', res.err_msg);
					  });
			}
			// 当微信内置浏览器初始化后会触发WeixinJSBridgeReady事件。
			document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
				WeixinJSBridge.on('menu:share:appmessage', function(argv){ //发送给好友
					_ShareFriend();
				});
				WeixinJSBridge.on('menu:share:timeline', function(argv){ //分享到朋友圈
					_ShareTL();
				});
			}, false);
		}
		_WXShare();	
	});
	</script>
	<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_daogouinfo.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
<%@ include file="/views/pv.jsp"%>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>