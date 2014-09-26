<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<%
	String pagetitle = request.getParameter("page_title");
%>

<!-- 新版搜索导航样式 -->
<header class="top-nav-new list-page-header clearfix">
	<%if("logo".equals(pagetitle)) {%>
	<h1 class="logo-icon"></h1>
	<%} else {%>
	<c:choose>
		<c:when test="${not empty fn:trim(returnFlag) && returnFlag=='0' }">
			<a href="javascript:history.back();" class="go_back"
				data-title="返回上一级"></a>
		</c:when>
		<c:otherwise>
			<a href="${ctx }/${_city.cityPinyinAbbr }/" class="go_back"
				data-title="返回上一级"></a>
		</c:otherwise>
	</c:choose>
	<p><h1><%=pagetitle%></h1></p>
	<%} %>
	<div class="header_tool">
		<a href="javascript:;" class="nav-btn" role="main_menu" role-addclass="its_open"
			data-title="展开菜单">导航<i></i></a>
	</div>
	<%@include file="/views/phoneNav.jsp" %>
</header>
