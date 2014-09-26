<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>团购报名</title>
<meta name="Keywords" content="北京楼盘,+楼盘信息" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/tuangou_pad.css" />
<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
<script data-main="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/modulejs/app_tuangou" src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/modulejs/lib/require.js" data-module="reg"></script>
</head>
<body data-city="${_city.cityPinyinAbbr}" data-activeid="${activeid}">

<!-- 新版样式导航 --> 
	<jsp:include page="/views/padHeader.jsp">
		<jsp:param value="团购报名" name="page_title" />
	</jsp:include>


<!--团购列表pad版部分-->
<section class="tuangou_pad_module" style="background: #fafafa">
    <div class="tuangou_pad_window">
        <h2>团购报名</h2>
        <div class="tuangou_reg_info">
            <div class="tuangou_reg_item"><span class="name">团购名称：</span><span class="info">${tuangou.active_name}</span></div>
            <div class="tuangou_reg_item"><span class="name">团购优惠：</span><span class="info">${tuangou.active_desc}</span></div>
        </div>
        <div class="tuangou_reg_form" id="tuangou_reg_form">
            <form>
                <div class="tuangou_reg_formItem"><i class="username"></i><input type="text" placeholder="请输入姓名" id="username"><div class="clear"></div></div>
                <div class="tuangou_reg_formTips"><i class="username"></i><span></span></div>
                <div class="tuangou_reg_formItem"><i class="phonenum"></i><input type="tel" placeholder="请输入手机号" id="phonenum" maxlength="11"><div class="clear"></div></div>
                <div class="tuangou_reg_formTips"><i class="username"></i><span></span></div>
                <div class="tuangou_list_btnGroup" style="margin-top: 30px"><a id="tuangou_list_register">立即报名</a></div>
            </form>
        </div>
    </div>
</section>

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
</footer>

<!-- 团购详情弹层 -->
<div class="tuangou_mak">
    <div class="tuangou_mak_tips"></div>
</div>

</body>
</html>