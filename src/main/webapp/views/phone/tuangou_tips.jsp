<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>团购说明</title>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/tuangou.css">
<script data-main="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/modulejs/app_tuangou" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/modulejs/lib/require.js" data-module="default"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
</head>
<body>

<!-- 新版样式导航 --> 
	<jsp:include page="/views/phoneHeader.jsp">
		<jsp:param value="团购说明" name="page_title" />
	</jsp:include>


<!-- 团购页—活动模块 -->
<section class="tuangou_module">
    <div class="tuangou_list_box tuangou_list_box2">
        <div class="tuangou_list_main2">
            <div class="tuangou_list_title">团购流程</div>
            <div class="tuangou_list_line"></div>
            <div class="tuangou_hd_method">
                <div class="lineY"></div>
                <div class="tuangou_hd_tips">
                    <div class="rank2">1</div>
                    <div class="item">点击立即报名按钮，填写您的手机号和姓名，进行报名。</div>
                </div>
                <div class="tuangou_hd_tips">
                    <div class="rank2">2</div>
                    <div class="item">收到您的报名信息后，我们的工作人员将会电话与您联系。</div>
                </div>
                <div class="tuangou_hd_tips">
                    <div class="rank2">3</div>
                    <div class="item">按照工作人员引导，办理焦点 VIP 爱家卡。在您购房时出示爱家卡，即可享受优惠。</div>
                </div>
            </div>
            <div class="tuangou_hd_phone" style="margin-bottom: 25px">团购咨询：<a class="red" href="tel:400-678-2020">400-678-2020</a></div>
        </div>

        <div class="tuangou_list_main2">
            <div class="tuangou_list_title">团购注意事项</div>
            <div class="tuangou_list_line"></div>
            <div class="tuangou_hd_method">
                <div class="tuangou_hd_tips">
                    <div class="rank">1.</div>
                    <div class="item">您所办理的爱家卡，每张卡只能应对一套房、不可累计使用。</div>
                </div>
                <div class="tuangou_hd_tips">
                    <div class="rank">2.</div>
                    <div class="item">购买搜狐VIP爱家卡的会员，在签订购房合同前，放弃购房者，有权利申请服务费退还服务（不计利息）。退款时间及方式：未购房者可填写退款申请表进行退款，退款自未购房者申请之日起30个工作日内，退还至交款卡账户。在正式签署购房合同后，服务费不予退还。
                    </div>
                </div>
                <div class="tuangou_hd_tips">
                    <div class="rank">3.</div>
                    <div class="item">搜狐焦点网仅向搜狐VIP爱家卡会员提供得到购房优惠的服务，但任何时候在任何情况下，将不对购房者购买指定房源的购买行为和/或购房者购买到的房屋的质量等问题承担任何担保和/或连带责任。</div>                </div>
                </div>
            </div>
        </div>
    </div>

</section>

<!-- 面包屑 -->
<div class="link_boxs"><a href="/${_city.cityPinyinAbbr }/">新房</a><span class="h_space1">></span><a href="/${_city.cityPinyinAbbr }/tuangou/${tuangou.active_id}/">楼盘团购说明</a></div>

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
<div class="tuangou_phone_tips">
    <div class="mask"></div>
    <div class="window">
        <p>接通<span></span>后，</p>
        <p>请手动拨打分机号<span></span></p>
        <a>好的</a>
    </div>
</div>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if> 
</body>
</html>