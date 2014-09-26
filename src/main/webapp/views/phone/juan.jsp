<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>“温暖童心”公益活动捐赠页面</title>
<%@ include file="/views/icon.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/gongyi/css/index.css"/>

</head>
<body>
<!-- header -->
<header class="header">
	<a id="back" href="/"></a>
	<h1>焦点公益</h1>
	<div class="banner"><img src="http://192.168.242.44/sceapp/focus_static/wap/gongyi/img/banner.jpg" title="温暖童心" alt="温暖童心" /></div>
</header>
<!-- .content -->
<div class="content">
	<div class="donation">
		<span class="circleLabel"></span>
		<form method="post" name="form" action="${ctx}/gongyi/juan" id="donationForm">
            <p class="red">先心病儿童救助</p>
            <p class="gray">捐助<input type="number" name="pay_amount" class="ui-text" value="10"  step="0.1"/>元</p>
            <p><input type="submit" value="我要捐款" id="tijiao" /></p>
			<p class="gray"><input type="checkbox" class="agree" checked="checked" /><label>同意并接受<a href="${ctx}/gongyi/agreement/">《易宝公益圈服务协议》</a></label></p>
		</form>
	</div>
	<article>
		<span class="circleLabel"></span>
		<h2 class="red">“温暖童心”项目介绍</h2>
		<div class="summary">有一群孩子，他们刚出生，心就受伤了；<br/>
由于家庭贫困，家里拿不出钱给他们看病；<br/>
于是，这些孩子不得不带着受伤的心艰难前行；<br/>
一旦治愈，他们便能和健康的孩子一样蹦蹦跳跳！</div>
<div class="details gray"><p>先心病是我国目前位于首位的出生缺陷疾病，每年新增约30万例先心病患儿，约占出生新生儿总数的8-12‰。然而，每年仅9万例患儿有机会接受手术。</p>
<p>2012年4月，搜狐焦点在中华思源工程扶贫基金会发起设立了专项公益基金——搜狐焦点公益基金，开展了“温暖童心”项目，即对0-18岁罹患先天性心脏病的孤儿和贫困家庭儿童进行医疗救助，重点救助孤贫复杂先心患儿。</p>
<p>以保护生命与健康为使命，以一份专业的努力改变孩子的命运，是搜狐焦点公益基金建立以来一直的承诺。无论孩子未来的路是坎坷、是平坦，风雨兼程中，能以健康的身体走下去，对他们而言才是公平的起跑线。</div>
<div class="sponsor red">在此，我们倡议：献出我们的爱心，集合大众的力量，呵护这些孤贫的病患儿童脆弱的生命。</div>
	</article>
	<footer class="gray">特别捐款通道由<a href="#" target="_blank"><img src="http://192.168.242.44/sceapp/focus_static/wap/gongyi/img/yibao.png" title="易宝公益圈" alt="易宝公益圈" /></a>提供</footer>
</div>
<div class="winTip" id="tips1">
	<div class="in">
		<p class="gray">同意并接受《易宝公益圈服务协议》</p>
		<div class="agreeBtn"><input type="button" value="确定" /></div>
	</div>
</div>
<div class="winTip" id="tips2">
	<div class="in">
		<p class="gray">请输入捐款金额</p>
		<div class="agreeBtn"><input type="button" value="确定" /></div>
	</div>
</div>
<div class="mask"></div>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/gongyi/js/index.js"></script>
<%@ include file="/views/pv.jsp"%>
</body>
</html>