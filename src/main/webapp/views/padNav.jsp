<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmss");
Date date = new Date();
%>
<!-- <a href="javascript:;" class="nav-btn" role="main_menu" role-addclass="its_open"
		data-title="展开菜单">导航<i></i></a> -->
	<ul class="sub-nav">
	<!-- 此处修改为导航添加登录入口 -->
			<li class="login-box">
			<c:choose>
			<c:when test="${not empty fn:trim(username) }">
			<!-- 登录后登出入口 -->
				<p class="logout">
					<a class="user-name" href="${ctx }/my/zixun/all/">${username }</a>
					<a href="/login/logout/"><span>退出</span><i></i></a>
				</p>
			</c:when>
			<c:otherwise>
			<!-- 登录前登录入口 -->
				<p class="login">
					<a href="${ctx }/login/?data=<%=s.format(date)%>" class="login"><span><i class="icon"></i>点击登录</span></a>
				</p>
			</c:otherwise>
			</c:choose>
			</li>
			<!-- end -->
		<li><a href="${ctx}/${_city.cityPinyinAbbr}/" class="home"><i
				class="icon"></i>首页</a></li>
		<li><a href="${ctx}/${_city.cityPinyinAbbr}/loupan/"
			class="xinfang"><i class="icon"></i>楼盘</a></li>
		<li><a href="${ctx}/${_city.cityPinyinAbbr}/pafangtuan/"
			class="pafangtuan"><i class="icon"></i>看房团</a></li>
		<li><a href="${ctx}/${_city.cityPinyinAbbr}/daogou/"
			class="daogou"><i class="icon"></i>导购</a></li>
		<li><a href="${ctx}/${_city.cityPinyinAbbr}/baodian/"
			class="baodian"><i class="icon"></i>宝典</a></li>
		<li><a href="${ctx}/${_city.cityPinyinAbbr}/zixun/" class="news"><i
				class="icon"></i>新闻</a></li>
		<li><a href="${ctx}/tools/sydk/" class="cal"><i class="icon"></i>计算器</a></li>
		<li><a href="http://m.focus.cn/pinge/" class="cases" target = "_blank"><i class="icon"></i>装修图</a></li>
		<li><a href="${ctx}/${_city.cityPinyinAbbr}/tuangou/" class="tuangou"><i class="icon"></i>团购</a></li>
	</ul>