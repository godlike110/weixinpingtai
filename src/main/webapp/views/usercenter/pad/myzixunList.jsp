<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %><%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
	<meta content="telephone=no" name="format-detection" />
   <%@ include file="/views/icon.jsp"%>
    <title>我的咨询</title>
    <meta name="Keywords" content="北京楼盘,+楼盘信息" />
    <meta name="Description" content="搜狐焦点北京房产网为广大网友提供了最新的北京楼盘信息，最准确的北京房价情况和最及时的北京新房资讯等，是买房、购房首选网站。 "/>
    <link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
    <link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
    <link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/myConsult.css" rel="stylesheet" />
    <link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
</head>
<body>
	<!-- 新版样式导航 -->
	<jsp:include page="/views/padHeader.jsp">
		<jsp:param value="我的咨询" name="page_title" />
	</jsp:include>
    <section class="section_content">
        <div class="tabList">
            <a href="${ctx}/my/zixun/all" class="<c:if test='${model==0}'>selected_tab_item</c:if> tab_item">全部<span class="selected_tab_arrow"></span></a>
            <a href="${ctx}/my/zixun/yihuida" class="<c:if test='${model==1}'>selected_tab_item</c:if> tab_item ">已回答<span class="selected_tab_arrow"></span></a>
            <a href="${ctx}/my/zixun/weihuida" class="<c:if test='${model==2}'>selected_tab_item</c:if> tab_item">未回答<span class="selected_tab_arrow"></span></a>
        </div>
        <ul class="consult_list">
        <c:forEach items="${quizList }" var="quiz">
        	<li>
                 <div class="question">
                    <div class="portrait_container fl">
                        <img class="consult_portrait" src="${quiz.userHeadPic}" />
                    </div>
                    <div class="question_detail">
                        <div class="question_summary clearfix">
                            <span>${quiz.userName}</span>
                            <span class="datetime">${quiz.questionTimeStr}</span>
                        </div>
                        <div class="question_content">${quiz.question}</div>
                        <c:if test="${quiz.status==1}"><div class="question_state">等待回复</div> </c:if>
                        <c:if test="${quiz.status==2}"><div class="question_state">您的问题与房产或楼盘无关</div> </c:if>                
                    </div>
                </div>
                <c:if test="${quiz.status==6}">
                <div class="answer">
                    <span class="answer_arrow"></span>
                    <div class="portrait_container fl">
                        <img class="consult_portrait" src="${quiz.editorHeadPic}" />
                    </div>
                    <div class="question_detail">
                        <div class="question_summary clearfix">
                            <span>${quiz.editorName}</span>
                            <span class="datetime">${quiz.answerTimeStr}</span>
                        </div>
                        <div class="question_content">${quiz.answer}</div>                
                    </div>
                </div>
                </c:if>
                <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${quiz.groupId}/" class="related_loupan" role="touch_bg" data-touchClass="related_loupan_touched">楼盘：${quiz.buildName}</a>
            </li>
        </c:forEach>
        </ul>
    	<div class="no_consult_container">暂无咨询记录</div>
    </section>
    <c:if test="${hasNext}">
    <div id="has_more" class="has_more1">
		<a href="javascript:;" class="load_more" style="display: block;">滑动加载更多</a>
		<a href="javascript:;" class="loading" style="display: none;"><b class="rotating"></b>加载中...</a>
	</div>
	</c:if>
    <%-- <section class="crumb">
		<div class="inner">
			<a href="${ctx}/${_city.cityPinyinAbbr}/">新房</a>&gt;<span>我的咨询</span>
		</div>
	</section>
    <footer class="clearfix">
		<span class="copyright">&copy;2014搜狐焦点</span>
		<div class="switch_version fr">
			<a href="javascript:;" class="version">Pad版</a>
			<div class="version_list">
				<a role="touch_bg" href="#" class="phone">手机版</a>
                <a role="touch_bg" href="#" class="ipad current">Pad版</a>
				<a role="touch_bg" href="#" class="pc">电脑版</a>
			</div>
		</div>
		<a href="javascript:;" id="to_top">返回顶部</a>
    </footer> --%>
    <script>var hasNext = '${hasNext}';var city = {"cityAbbr":"${_city.cityPinyinAbbr}"};</script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
    <script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/myConsult.js"></script>
    <%@ include file="/views/pv.jsp"%>
</body>
</html>