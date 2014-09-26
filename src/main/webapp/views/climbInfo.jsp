<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>【${_dict_city.cityName}楼盘|${_dict_city.cityName}房价|${_dict_city.cityName}新房】-搜狐焦点看房团</title>
<meta name="Keywords" content="${_dict_city.cityName}楼盘, ${_dict_city.cityName}房价, ${_dict_city.cityName}新房">
<meta name="Description" content="搜狐焦点${_dict_city.cityName}房产网为广大网友提供了最新的${_dict_city.cityName}楼盘信息，最准确的${_dict_city.cityName}房价情况和最及时的${_dict_city.cityName}新房资讯等，是买房、购房首选网站。 ">
<%@ include file="/views/icon.jsp" %>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript"  src="http://webapi.amap.com/maps?v=1.2&key=9e4b883b2a6d8482638c56b6f60078b7"></script>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_climb_v1.0.css">
<script>
var lnglats = "${lnglats}";
var projNames = "${projNames}";
var signupurl = "${signupurl}";
var outOfCity = ${outOfCity};
var cityNamePY = "${_city.cityPinyinAbbr}";
var lineId = "${lineInfo.line_id}";
</script>
<body>
		<header class="top_nav">
			看房团详情
			<span class="home_btn fr" role="home" url="http://m.focus.cn"></span>		
		</header>
		<section class="section1">
			<ul>
				<li>
					<div class="icon1 h1">${sendDate} 看房团</div>
				</li>
				<li>
					<div class="icon2 h1">${lineInfo.title}</div>
					<div class="h2">${lineInfo.sub_title }</div>
					<div class="detail">报名截止时间：${closeDate}</div>
				</li>
			</ul>		
		</section>
		<div class="its_tit1">本线路楼盘<em class="its_icon1"></em></div>
		<div class="clear"></div>
		
		<section class="section2">
			<ul>
				<c:forEach var="pj" items="${lineInfo.project}">
					<li class="clearfix" role="url" url="http://m.focus.cn/building/show/${pj.group_id}">
						<div class="h2">${pj.name}</div>
						<div class="icn fl"></div>
						<c:choose>
							<c:when test="${not empty fn:trim(pj.discount) && pj.discount != '0'}" >
								<div class="h3 fl">${pj.discount}</div>
							</c:when>
							<c:otherwise>
								<div class="h3 fl">现场决定优惠</div>
							</c:otherwise>
						</c:choose>
						<div class="price fr">
							<c:choose>
								<c:when test="${empty fn:trim(pj.price) || pj.price == '0'}"><span class="red">价格待定</span></c:when>
								<c:otherwise><span class="red">${pj.price}</span>元/平米</c:otherwise>
							</c:choose>
							<%-- <span class="red">${pj.price}</span>元/平米 --%>
						</div>
					</li>
				</c:forEach>
			</ul>
		</section>
		<div class="its_tit1">路线地图<em class="its_icon1"></em></div>
		<div class="clear"></div>
		
		<div class="map" id="amap">
		</div>
		
		<div class="its_tit1">小贴士<em class="its_icon1"></em></div>
		<div class="clear"></div>
		
		<section class="tip">
			<ul>
				<c:forEach var="t" items="${tips}">
					<li>
						<div class="title">${t.question_content}</div>
						<p>${t.answer_content}</p>
					</li>
				</c:forEach>
			</ul>
		</section>
		<c:choose>
			<c:when test="${lineInfo.status == 1}">
				<footer class="footer footer_bg1" role="foot" ok=1>
					立即报名<span class="t">（已报名${lineInfo.join_nums}人）</span>
				</footer>
			</c:when>
			<c:otherwise>
				<footer class="footer footer_bg2" role="foot" ok=2>报名已结束</footer>
			</c:otherwise>
		</c:choose>
		<div class="favorable_signup"">
		<div class="cc" role="cc"></div>
		<div class="h1">${sendDate} ${lineInfo.title}</div> 
			<p class="it_ok2">请正确填写个人信息，以便我们与您取得联系，进行看房团相关确认。</p>
						<ul class="favorable_ul1">
							<li class="clearfix">
								<div class="ots_name1 fl tr textNowrap">姓名</div>
								<div class="ots_inp1" add-class="ots_inp1_focused">
									<input class="ots_inp1_input ots_inp1_name" type="text" id="name">
								</div>
								<div class="ots_tip1">
									<p class="it_ok">姓名最多6个汉字或12个英文字母</p>
									<div class="error_info">最多6个汉字或12个英文字母</div>
								</div>
							</li>
							<li class="clearfix">
								<div class="ots_name1 fl tr textNowrap">手机号</div>
								<div class="ots_inp1">
									<input class="ots_inp1_input ots_inp1_telephone" maxlength="11" type="tel" id="tel">
								</div>
								<div class="ots_tip1">
									<p class="it_ok">请输入11位手机号</p>
									<div class="error_info">请输入正确的手机号</div>
								</div>
							</li>
						</ul>
						<div class="signup_btn1" role="btn"><div class="ots_btn">报名</div></div>
					</div>
					<div class="alert_box" id="signup_success1">
						<h3 class=""><em class="signup_success_icon"></em>报名成功！</h3>		
						<p class="p2">楼盘的销售人员会和您联系，请保持电话畅通。</p>
						<p class="p2">当楼盘有新动态时会第一时间短信通知您。</p>
					</div>
					<div class="alert_box" id="signup_fail1">
						<h3 class="red1"><em class="signup_fail_icon"></em>报名失败！</h3>
						<p class="p2">报名失败，请稍后重试</p>
					</div>
		<div class="over"></div>
		<!--  
	<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_climb_v1.0.js"></script>
	-->
	<script type="text/javascript" src="/wap_climb_v1.0.js"></script>
</body>
</html>