<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>								
      <div class="box4">
        <c:if test="${not empty  recentNews}">
        <h2>最新动态 <span>${recentNews.infotime}</span></h2>
        <div class="box4_content">
          <h3>${recentNews.infoname}</h3>
          <p>${recentNews.infocontent}</p>
        </div>
        </c:if>
      </div>
      <div class="box2_2"><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${info.groupId}/dongtai/${recentNews.infoid}/" >查看全文</a></div>