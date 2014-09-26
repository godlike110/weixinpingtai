<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
<meta content="telephone=no" name="format-detection" />
<%@ include file="/views/icon.jsp"%>
<title>${_city.cityName}楼盘在线咨询_${_city.cityName}购房买房在线咨询-${_city.cityName}搜狐焦点网</title>
<meta name="Keywords" content="${_city.cityName}楼盘在线咨询,${_city.cityName}购房在线咨询" />
<meta name="Description" content="${_city.cityName}搜狐焦点网是${_city.cityName}最权威的专业咨询平台，为您提供${_city.cityName}楼盘折扣，周边配套，买房流程，置业疑惑等在线咨询，搜狐焦点专家有问必答"/>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css" />
<link href="http://192.168.242.44/sceapp/focus_static/wap/sohunews/css/consultLoupan.css" rel="stylesheet" />
<script>
    var city = {"cityAbbr":"${_city.cityPinyinAbbr}"};
    var hasNext = '${hasNext}';
</script>
</head>
<body>
	<div class="header clearfix">
        <h1 class="title">在线咨询</h1>
        <div class="fr">
        	<c:if test="${logined}">
        	<a href="${ctx}/my/zixun/all" data-touched="true" class="header_sub_btn">
                <span class="city">我的</span>
                <img src="http://192.168.242.44/sceapp/focus_static/wap/phone/img/user.png" class="user_icon" alt="user" />
            </a>
            </c:if>
            <a href="${ctx }/sohunews/citySelect/?cityName=${_city.cityPinyinAbbr}&from=zixun" data-touched="true" class="header_sub_btn">
                <span class="city">${_city.cityName}</span>
                <img src="http://192.168.242.44/sceapp/focus_static/wap/phone/img/location.png" class="location_icon" alt="loaction" />
            </a>
        </div>        
        <span class="arrow"></span>
	</div>
	<c:if test="${!logined}">
	<div class="announcement">登录后可查看咨询记录</div>
	</c:if>
    <section class="section_content">
    	<ul class="consult_list">
    	<c:forEach items="${quizList}" var="quiz">
    		<li>
                <div class="question">
                    <div class="portrait_container fl">
                        <img class="portrait" src="${quiz.userHeadPic}" />
                    </div>
                    <div class="question_detail">
                        <div class="question_summary clearfix">
                            <span>${quiz.userName}</span>
                            <span class="datetime">${quiz.questionTimeStr}</span>
                        </div>
                        <div class="question_content">${quiz.question}<button type="button" class="expand_btn"><span class="expand_icon"></span></button></div>                
                    </div>
                </div>
                <div class="answer">
                    <span class="answer_arrow"></span>
                    <div class="portrait_container fl">
                        <img class="portrait" src="${quiz.editorHeadPic}"/>
                    </div>
                    <div class="question_detail">
                        <div class="question_summary clearfix">
                            <span>${quiz.editorName}</span>
                            <span class="datetime">${quiz.answerTimeStr}</span>
                        </div>
                        <div class="question_content">${quiz.answer}<button type="button" class="expand_btn"><span class="expand_icon"></span></button></div>                
                    </div>
                </div>
                <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${quiz.groupId}/" data-touched="true" data-touchClass="loupan_summary_touched" class="loupan_summary">${quiz.buildName}</a>
                <div class="action_div">
                    <a class="action_btn viewLoupanBtn" href="${ctx}/${_city.cityPinyinAbbr}/loupan/${quiz.groupId}/" data-touched="true" data-touchClass="viewLoupanBtn_touched">查询楼盘</a>
                    <a class="action_btn askQuestionBtn" href="${ctx}/sohunews/zixun/ask/?groupId=${quiz.groupId}" data-touched="true" data-touchClass="askQuestionBtn_touched">我要提问</a>
                </div>
            </li>
    	</c:forEach>        
    	</ul>
        <div class="back2top" data-title="返回顶部"></div>
        <!-- pull up -->
        <c:if test="${hasNext}">
		<div class="pull font_gray">
			<div class="icon fl"></div>
			<p>滑动加载更多</p>
		</div>
		</c:if>
    </section>
    <script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/sohunews/snippets/common.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/sohunews/snippets/consultLoupan.js"></script>
    <%@ include file="/views/pv.jsp"%>
</body>
</html>