<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
		<meta content="telephone=no" name="format-detection" />
		<title>${info.projName }-${_city.cityName }${info.projName }户型图相关信息-搜狐焦点</title>
		<%@ include file="/views/icon.jsp" %>
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
		<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
		<script type="text/javascript">
		    var city = ${cityStr};
		    var groupId = ${groupId}; 
		    var phone400 = ${phone400};
			var type = "${defaultTypeId}";
			var photos = ${dataStr};
			var pageinfo = "${pageinfo}";
		</script>
	</head>
	
	<body>
		<section class="container">
			<header>
														<c:if test="${returnFlag=='0' }">
		<a href="javascript:history.back();" class="back" data-title="返回上一级"></a>
		</c:if>
		<c:if test="${returnFlag=='1' }">
			<a href="${ctx}/${_city.cityPinyinAbbr}/" class="back" data-title="返回上一级">首页</a>
		</c:if>
				<h1>户型图</h1>
				<div class="menu menu_right map_menu" id="menu">
					<a href="javascript:;" class="filter">筛选</a>
					<div class="menu_list">
						<a href="javascript:;" data-type="11111">全部户型</a>
						<c:forEach var="photos" items="${data}">
						<a href="javascript:;" class="touched" data-type="${photos.type}">${photos.minMaxArea}</a>
						</c:forEach>
					</div>
				</div>
			</header>
			
			<section class="content clearfix">
				<div id="house_image_wrap">
					<div class="count count_rs">
						<span>1</span>/<span>${photos.count}</span>
					</div>
					<div class="img_list" id="house_image_scroll">
						<ul class="clearfix"></ul>
					</div>
				</div>
			</section>

			<section class="crumb">
				<div class="inner">
					<a href="${ctx}/${_city.cityPinyinAbbr}/">新房</a>&gt;<a href="${ctx}/${_city.cityPinyinAbbr}/loupan/"> ${_city.cityName }楼盘</a>&gt;<a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/"> ${buildName}</a>&gt;<span>户型图</span>
				</div>
			</section>
 			<c:if test="${info.phone400!=0 }">
			<footer>
				<a href="tel:4008882200,${info.phone400 }" class="free_advice"><b></b>免费咨询 400-888-2200 转 ${info.phone400 }</a>
			</footer>
			</c:if>
			
		 <c:if test="${info.phone400==0 }">
			<footer class="position_fixed">
				<a href="tel:4006782020" class="free_advice"><b></b>免费咨询 400-678-2020</a>
			</footer>
			</c:if>
			
		</section>
		
		<!-- js -->
		<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/iscroll_v4.2.5.js"></script>
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.house_image.gh_1.0.js"></script>
		<%@ include file="/views/pv.jsp"%>
	</body>
</html>