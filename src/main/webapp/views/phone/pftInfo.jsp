<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${_city.cityName }看房团-${_city.cityName }搜狐焦点网</title>
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
<meta content="telephone=no" name="format-detection" />
<meta name="Keywords" content="${_city.cityName }看房团"/>
<meta name="Description" content="${_city.cityName }搜狐焦点看房团为大家设计最佳看房路线，带大家去看心仪的楼盘。同时，${_city.cityName }看房团还将不定期的邀请房地产专业人士陪同，为大家解读户型，指点装修问题。"/>
<%@ include file="/views/icon.jsp" %>
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pft/css/plubic.css">
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pft/css/jquery.prompt_phone_portrait.css">
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pft/css/wap_phone.css">
<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pft/css/component_phone.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css"/>
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
</head>

<body>

	<!-- 新版样式导航 -->
		<jsp:include page="/views/phoneHeader.jsp">
			<jsp:param value="详情" name="page_title" />
		</jsp:include>
<!--内容-->
<div class="box1"> <a href="javascript:;"><img src="${pft.lnglatsUrl }" width="100%" alt = "${pft.title }"/></a> </div>
<div class="box pl10 pr10 pt10"> 
  <!--左侧--> 
  
  <!--地图信息-->
  
  <div class="box2">
    <h1>${pft.title }</h1>
    <p>${pft.lightspot }</p>
    <ul>
      <li><span>开团时间</span>
        <p>${pft.activeDate }</p>
      </li>
      <li><span>报名截止</span>
        <p id="end_time">${pft.lastSignUpDate }</p>
      </li>
      <li><span>已报名</span>
        <p class="box2_icon"><strong class="text_color_red">${pft.signUpNum }</strong> 人</p>
      </li>
      <li class="width100"><span>活动说明</span><a href="${ctx }/${_city.cityPinyinAbbr}/pafangtuan/statement/${pft.lineId}" class="md-trigger">
        <p class="text_color_blue">活动流程及免责声明</p>
        </a></li>
      
      <c:choose>
            <c:when test="${not empty fn:trim(phone2) }">
            <li class="width100"><span>热线电话</span>
            <a href="javascript:;" id="hotline" data_href="tel:${phone2}"><p class="text_color_blue">${phone }</p></a>
            </li>
            </c:when>
         </c:choose>
     	
      <div class="clear"></div>
    </ul>
    <div class="clear"></div>
    <c:choose>
    <c:when test="${not empty fn:trim(pft.applyRemainedDays) && (pft.applyRemainedDays > 0) }">
    	<div class="box2_registration width100"><a class="md-trigger" data-modal="modal-2">我要报名</a></div>
    </c:when>
    <c:otherwise>
    	<div class="box2_registration width100"><a class="registrationend" >报名已结束</a></div>
    </c:otherwise>
    </c:choose>
  </div>
  <!--地图信息 end--> 
  
  
  <!--免费现场活动-->
  <!--  
  <c:if test="${pft.signUpStatus == '1' }">
  <c:if test="${houseCollege }">
  <div class="box3">
    <h2>免费现场活动</h2>
    <div class="box3_content">
      <div class="box3_content_name"> <strong><img src="${pft.houseCollege.pic }" width="80" height="80" /></strong><span>编辑：${pft.houseCollege.nickName }      ${pft.houseCollege.title }</span>
        <div class="clear"></div>
      </div>
      <c:forEach items="${pft.houseCollege.outline }" var="outline" varStatus="status">
          <p>${outline }</p>
      </c:forEach>
    </div>
  </div>
  </c:if>
  </c:if>
  -->
  <!--免费现场活动 end--> 
  <!--推荐楼盘-->
  <c:if test="${not empty fn:trim(recommondPftBuild) }">
  <div class="box4">
    <h2>推荐楼盘</h2>
    <a href="${ctx }/${_city.cityPinyinAbbr}/loupan/${recommondPftBuild.groupId}/">
    <div class="box4_content"><img src="${recommondPftBuild.mainPic }" width="100%" alt = "${recommondPftBuild.name }"/>
      <div class="box4_box1"></div>
      <div class="box4_box2"><span>
      <c:choose>
      	  		<c:when test="${recommondPftBuild.refPrice !='0' }"><font class="text_color_red">${recommondPftBuild.refPrice }</font></c:when>
          		<c:otherwise><font class="text_color_red">价格待定</font></c:otherwise>
          </c:choose>
      </span>${recommondPftBuild.name }</div>
      
    </div>
    <p>${recommondPftBuild.recommendReason }</p>
    </a>
  </div>
 </c:if>
  <!--推荐楼盘--> 
  <div class="clear"></div>
  <!--本线路楼盘-->
  
  <c:if test="${fn:length(pft.builds) > 1  }">
  <div class="box5">
    <h2>本线路楼盘</h2>
    <div class="box5_content">
      <ul>
      <c:forEach items="${pft.builds }" var="build" varStatus="status" begin="1">
       <li> 
          <a href="${ctx }/${_city.cityPinyinAbbr}/loupan/${build.groupId}/"><img src="${build.mainPic }" width="120" height="90" alt = "${build.name }"/>
          <h3>${build.name }</h3>
          <p>${build.address }</p>
          <div class="box5_price">
          <c:choose>
          		<c:when test="${build.refPrice == '0' }"><font class="text_color_red">价格待定</font></c:when>
          		<c:otherwise><font class="text_color_red">${build.refPrice }</font></c:otherwise>
          </c:choose>
          </div>
          
          <c:choose>
                 <c:when test="${not empty fn:trim(build.discount) && build.discount !='0' }">
                 <div class="box5_discount">${build.discount }</div>
                 </c:when>
          </c:choose>

          <div class="clear"></div>
          <div class="box_li_content">${build.recommendReason }</div>
          </a>
          <div class="box_li_tel"><a class="loupan_hotline" data-phone400="${build.phoneFenji}" data-groupId="${build.groupId}" data_href="tel:${build.phone2 }">楼盘热线</a></div>
        </li>
      </c:forEach>
      </ul>
    </div>
  </div>
  </c:if>
  <!--本线路楼盘 end--> 
  
  <!--其他路线-->
  <c:if test="${fn:length(otherPfts) > 0}">
  <div class="box6">
    <h2>其他线路</h2>
    <div class="box6_content">
      <ul>
      <c:forEach items="${otherPfts }" var="otherPft" varStatus="status">
      <li> <a href="${ctx }/${_city.cityPinyinAbbr}/pafangtuan/${otherPft.lineId}/"><img src="${otherPft.builds[0].mainPic }" width="100" height="75" alt = "${otherPft.title }"/>
          <h3>${otherPft.title }</h3>
          <h4>
            <p class="box6_content_left">路线包含<span>${fn:length(otherPft.builds) }</span>个楼盘</p>
            <p class="box6_content_right">报名<span>${otherPft.signUpNum }</span>人</p>
            <div class="clear"></div>
          </h4>
          <p>${otherPft.lightspot }</p>
          </a>
          <div class="clear"></div>
		  <c:choose>
		  <c:when test="${fn:length(otherPfts)==status.index+1 }">
		  </c:when>
		  <c:otherwise>
		  	<div class="box6_content_line clear"></div>
		  </c:otherwise>
		  </c:choose>
        </li>
      </c:forEach>
      </ul>
    </div>
  </div>
  </c:if>
  <!--近期看房团--> 
  
  <!--小贴士-->
  <div class="box7">
    <h2>小贴士</h2>
    <div class="box7_content">
      <ul>
      <c:forEach items="${tips }" var="tip" varStatus="status">
      	<li>
          <h3>${tip.title }</h3>
          <p>${tip.content }</p>
          
          <c:choose>
          <c:when test="${status.index+1 < fn:length(tips) }">
          	<div class="box7_line"></div>
          </c:when>
          </c:choose>
		  
        </li>
        
      </c:forEach>
      </ul>
    </div>
  </div>
  <!--小贴士--> 
  
</div>
<!--内容 end--> 
<!--面包屑-->
<div class="position">
  <p><a color="#aaa" href="${ctx }/${_city.cityPinyinAbbr}"><font color="#aaa">新房</font></a> > <a color="#aaa" href="${ctx }/${_city.cityPinyinAbbr}/pafangtuan"><font color="#aaa">看房团</font></a> > <a color="#aaa" href="javascript:;"><font color="#aaa">看房详情</font></a></p>
</div>
<!--面包屑 end--> 
<!--版权 返回顶部-->
<!--<div class="copy">
  <p><span class="gotop"><a href="#">手机版</a></span>©2014 搜狐焦点</p>
</div>-->
    <div class="over"></div>
    <div class="alert_box" id="check_net">
     	<p class="p1">请在接通后拨分机号</p>
     	<p class="p1">1234</p>
     	<div class="itos_btn1" id="check_net_confirm"><a href="javascript:;">好的</a></div>
		<div class="its_close_btn"></div>
    </div>
    <footer class="clearfix">
		<span class="copyright">&copy;2014搜狐焦点</span>
		<div class="switch_version">
			<a href="javascript:;" class="version">手机版</a>
			<div class="version_list">
				<a role="touch_bg" href="javascript:;" class="phone current">手机版</a>
				<c:choose>
				<c:when test="${_city.cityPinyinAbbr ==  'suzhou' }">
				<a role="touch_bg" data-go="false" href="http://kanfangtuan.focus.cn/${_city.cityPinyinAbbr }/route_${pft.lineId }?cfrom=mobile" class="pc">电脑版</a>
				</c:when>
				<c:otherwise>
				<a role="touch_bg" data-go="false" href="http://kanfangtuan.focus.cn/${_city.cityPinyin }/route_${pft.lineId }?cfrom=mobile" class="pc">电脑版</a>
				</c:otherwise>
				</c:choose>
			</div>
		</div>
		<a href="javascript:;" id="to_top" style="display:none;">返回顶部</a>
	</footer>
<!--版权 返回顶部 end--> 

<!--弹出-->
 
<div class="md-modalform" id="modal-2">
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
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/jquery.prompt.js"></script>
<script type="text/javascript">
var city=${city};
var signupUrl = city.singleUrl+"/{lineId}/signup";
</script> 
<script src="http://192.168.242.44/sceapp/focus_static/wap/pft/js/classie.js"></script> 
<script src="http://192.168.242.44/sceapp/focus_static/wap/pft/js/modalEffects.js"></script> 
<!--验证--> 
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/pft/js/Validate.js"></script> 
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/pft/js/Validate.form.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/pft/js/common.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/pft/js/article_phone.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
<%@ include file="/views/pv.jsp"%>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>
