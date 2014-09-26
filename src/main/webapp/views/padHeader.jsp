<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<%
	String pagetitle = request.getParameter("page_title");
%>
<!-- 新版导航开始 -->
<header class="top-nav-new page-list-header clearfix">
	<c:choose>
		<c:when test="${not empty fn:trim(returnFlag) && returnFlag=='0' }">
			<a href="javascript:history.back();" class="back fl"></a>
		</c:when>
		<c:otherwise>
			<a href="${ctx }/${_city.cityPinyinAbbr }/" class="back fl">首页</a>
		</c:otherwise>
	</c:choose>
	<p><h1><%=pagetitle%></h1></p>
	 <a href="javascript:;" class="nav-btn" role="main_menu" role-addclass="its_open"
		data-title="展开菜单">导航<i></i></a>
	<%@include file="/views/padNav.jsp" %>
</header>
