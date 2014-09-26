<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<meta name="apple-mobile-web-app-title" content="搜狐焦点房产"> <!-- 保存到主屏默认的标题 -->
<title>【${_dict_city.cityName}楼盘|${_dict_city.cityName}房价|${_dict_city.cityName}新房】-搜狐焦点看房团</title>
<meta name="Keywords" content="${_dict_city.cityName}楼盘, ${_dict_city.cityName}房价, ${_dict_city.cityName}新房">
<meta name="Description" content="搜狐焦点${_dict_city.cityName}房产网为广大网友提供了最新的${_dict_city.cityName}楼盘信息，最准确的${_dict_city.cityName}房价情况和最及时的${_dict_city.cityName}新房资讯等，是买房、购房首选网站。 ">
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript"  src="http://webapi.amap.com/maps?v=1.2&key=9e4b883b2a6d8482638c56b6f60078b7"></script>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_climb_v1.0.css">
<link rel="stylesheet" type="text/css" href="http://api.amap.com/Public/css/demo.Default.css" />
<script>
var lnglats = "${lnglats}";
var projNames = "${projNames}";
var ja = ${ja};
</script>
<body>
			

		<div class="its_tit1">路线地图<em class="its_icon1"></em></div>
		<div class="clear"></div>

		<div class="map" id="amap" style="width:500px;height:250px" >
		</div>
		
		

	
	<script type="text/javascript" src="/wap_climb_map_v1.0.js"></script>
</body>
</html>