<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${_city.cityName }看房团-${_city.cityName }搜狐焦点网</title>
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
<meta content="telephone=no" name="format-detection" />
<meta name="Keywords" content="${_city.cityName }看房团"/>
<meta name="Description" content="${_city.cityName }搜狐焦点看房团为大家设计最佳看房路线，带大家去看心仪的楼盘。同时，${_city.cityName }看房团还将不定期的邀请房地产专业人士陪同，为大家解读户型，指点装修问题。"/>
<%@ include file="/views/icon.jsp" %>
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pft/css/plubic.css"/>
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pft/css/wap_list_phone_portrait.css"/>
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pft/css/jquery.prompt_phone_portrait.css"/>
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pft/css/component_phone.css"/>
<c:choose>
	<c:when test="${sohunews}"><link href="http://192.168.242.44/sceapp/focus_static/wap/sohunews/css/pft_header.css" rel="stylesheet" /></c:when>
	<c:otherwise>
		<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
		<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
	</c:otherwise>
</c:choose>

</head>
<body>
<!-- 新版样式导航 --> 
<c:choose>
	<c:when test="${sohunews}">
		<div class="header clearfix">
        	<h1 class="title">看房团</h1>
        	<a href="${ctx}/sohunews/citySelect/?cityName=${_city.cityPinyinAbbr}&from=kanfangtuan" data-touched="true" class="fr" id="selectCity">
            	<span class="city">${_city.cityName}</span>
            	<img src="http://192.168.242.44/sceapp/focus_static/wap/phone/img/location.png" class="location_icon" alt="loaction" />
        	</a>
        	<span class="arrow"></span>
		</div>
	</c:when>
	<c:otherwise>
		<jsp:include page="/views/phoneHeader.jsp">
			<jsp:param value="看房团" name="page_title" />
		</jsp:include>
	</c:otherwise>
</c:choose>
<!--顶部 end--> 
<!--内容-->
<div class="box">
  	<div class="box1">
    	<ul>
        	<c:forEach items="${pfts }" var="pft" varStatus="status">
        	<li>
           	  <h1><span>已报名 <font class="text_color_red">${pft.signUpNum }</font> 人</span>还有${pft.applyRemainedDays }天结束</h1>
                <div class="box1_box">
                <a href="${ctx }/${_city.cityPinyinAbbr }/pafangtuan/${pft.lineId}/"><h2>${pft.title }</h2>
                <h3>${pft.lightspot }</h3>
                <div class="map">
                <img src="${pft.lnglatsUrl }" width="100%" />
                </div>
                </a>
                <div class="info">
                	<c:forEach items="${pft.builds }" var="build" varStatus="buildIndex">
                		<p><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${build.groupId}/"><span>
                		<c:choose>
                			<c:when test="${build.refPrice=='0' || empty fn:trim(build.refPrice)}"><font class="text_color_red">价格待定</font></c:when>
                			<c:otherwise><font class="text_color_red">${build.refPrice }</font></c:otherwise>
                		</c:choose>
                		</span>${build.nameAbbr }</a></p>
                	</c:forEach>
                </div>
                <div class="box1_button">
                	<a href="${ctx }/${_city.cityPinyinAbbr }/pafangtuan/${pft.lineId}/" class="box1_button_left">查看路线</a>
                	<a href="javascript:;" class="md-trigger box1_button_right" data-modal="modal-3">我要报名</a>
                </div>
                <div class="clear"></div>
                </div>
                <div class="clear"></div>
            </li>
        	</c:forEach>
            <div class="clear"></div>
        </ul>
		<c:if test="${hasNext == 'true'}">
        <div id="has_more" class="has_more1">
		    <a href="javascript:;" class="load_more" style="display: block;">滑动加载更多</a>
		    <a href="javascript:;" class="loading" style="display: none;"><b class="rotating"></b>加载中...</a>
	    </div>
		</c:if>
    </div>
</div>
<!--内容 end--> 
<!--面包屑-->
<c:if test="${!sohunews}">
<div class="position">
  <p><a color="#aaa" href="${ctx }/${_city.cityPinyinAbbr}"><font color="#aaa">新房</font></a> > <a color="#aaa" href="javascript:;"><font color="#aaa">看房团</font></a></p>
</div>
<!--面包屑 end--> 
<!--版权 返回顶部-->
<footer class="clearfix">
	<span class="copyright">©2014搜狐焦点</span>
	<div class="switch_version">
		<a href="javascript:;" class="version">手机版</a>
		<div class="version_list">
			<a role="touch_bg" href="javascript:;" class="phone current">手机版</a>
			<c:choose>
			<c:when test="${_city.cityPinyinAbbr ==  'suzhou' }">
			<a role="touch_bg" data-go="false" href="http://kanfangtuan.focus.cn/${_city.cityPinyinAbbr }?cfrom=mobile" class="pc">电脑版</a>
			</c:when>
			<c:otherwise>
			<a role="touch_bg" data-go="false" href="http://kanfangtuan.focus.cn/${_city.cityPinyin }?cfrom=mobile" class="pc">电脑版</a>
			</c:otherwise>
			</c:choose>
			
		</div>
	</div>
	<a href="javascript:;" id="to_top" style="display: block;">返回顶部</a>
</footer>
</c:if>
<!--版权 返回顶部 end-->
 <div class="md-modalform" id="modal-3">
  <div class="md-content1">
    <h3>报名</h3>
    <div>
      <div class="md-content1_form">
         <h4>报名成功后工作人员会电话与您联系</h4>
         <ul> 
         	<li>
                <input type="text" id="realname" name="realname" class="text" tabindex="7" placeholder="姓名最多6个汉字或12个英文字母"/>
                <div class="clear"></div>
                 
                <div id="realname_error"></div>
            </li>
         	<li>

                <input type="tel" id="mobile" name="mobile" class="text" tabindex="11" placeholder="请输入11位手机号" />
                <div class="clear"></div>
                <div id="mobile_error"></div>
            </li>
            <input type="checkbox" name="protocol" id="protocol" tabindex="7"  style=" display:none;"/><label for="protocol" style=" display:none;">网站用户注册协议</label>
         	<li class="form_button">
                <a class="grey_button"   pid="tmp5"/>取消</a>
                    <pre class="brush: jscript;" id="tmp5">
                    $.Prompt("<p>登录失败，请重试</p>",1000);
                    </pre>
                 <a class="red_button"   pid="tmp6"/>报名</a>
                    <pre class="brush: jscript;" id="tmp6">
                    $.Prompt("<span>报名成功！<br>楼盘的销售人员会和您联系，<br>请保持电话畅通</span>",1000);
                    </pre>
            </li>
         </ul>
      </div>
    </div>
  </div>
</div>
<div class="md-overlay"></div>
<!-- the overlay element -->
<script src="http://192.168.242.44/sceapp/focus_static/wap/pft/js/modernizr.custom.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/jquery.1.7.2.min.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/jquery.prompt.js"></script>
<script type="text/javascript">
function pupopen(){
document.body.style.overflow='hidden';//ie9ie10chrome
document.documentElement.style.overflow='hidden';//ie6,ie7,ie8
} function pupclose(){
document.body.style.overflow='';//ie9ie10chrome
document.documentElement.style.overflow='';//ie6,ie7,ie8
}
</script>
<script type="text/javascript">
var hasNext = ${hasNext};
var city=${city};
//报名url组装  {cityName}/pafangtuan/{lineId}/signup
var signupUrl = city.singleUrl+"/{lineId}/signup";
var endDate="${endDate}";
</script> 
<script src="http://192.168.242.44/sceapp/focus_static/wap/pft/js/classie.js"></script> 
<script src="http://192.168.242.44/sceapp/focus_static/wap/pft/js/modalEffects.js"></script> 
<!--验证-->
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/pft/js/Validate.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/pft/js/Validate.form.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/pft/js/common.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/pft/js/wap_list_phone.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
<%@ include file="/views/pv.jsp"%>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>
