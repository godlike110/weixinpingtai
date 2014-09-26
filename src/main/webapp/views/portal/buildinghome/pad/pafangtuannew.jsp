<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<c:if test="${not empty list}">
	<div class="box5">
		<h2>近期看房团</h2>
        <div class="box5_content">
        	<ul>
        		<c:forEach var="pft" items="${list}" end="1" varStatus="status">
        			<li>
        				<a href="${ctx}/${_city.cityPinyinAbbr}/pafangtuan/${pft.lineId}/">
        					<img src="${pft.pic}" width="216" height="162" alt = "${pft.title}"/>
        					<h3>${pft.title}</h3>
              				<h4>已报名<span>${pft.signUpNum}</span>人</h4>
              				<div class="clear"></div>
        				</a>
        			</li>
        			<c:if test="${!status.last }">
        				<div class="box5_content_line clear"></div>
        			</c:if>
        		</c:forEach>
        	</ul>
        </div>
	</div>
</c:if>