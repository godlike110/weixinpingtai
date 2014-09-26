<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>团购说明</title>
<meta name="Keywords" content="北京楼盘,+楼盘信息" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/tuangou_pad.css" />
<script data-main="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/modulejs/app_tuangou" src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/modulejs/lib/require.js" data-module="default"></script>
</head>
<body>

<!-- 新版样式导航 --> 
	<jsp:include page="/views/padHeader.jsp">
		<jsp:param value="团购说明" name="page_title" />
	</jsp:include>

<!--团购列表pad版部分-->
<section class="tuangou_pad_module">
    <div class="tuangou_list_box tuangou_list_box2">
        <div class="tuangou_list_main2">
            <div class="tuangou_list_title" style="border-bottom: 1px solid #e9e9e9;">团购流程</div>
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
            <div class="tuangou_hd_phone" style="margin-bottom: 15px">团购咨询：<a class="red" href="tel:400-678-2020">400-678-2020</a></div>
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

<section class="crumb">
    <div class="inner">
        <a href="/${_city.cityPinyinAbbr }/">新房</a>&gt;<a href="/${_city.cityPinyinAbbr }/tuangou/shuoming/">楼盘团购说明</a>
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
</body>
</html>