<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
      <div class="box6">
        <c:if test="${layoutCount != 0 }">
        <h2>户型图</h2>
        <c:forEach var="l" items="${layouts}" end="1" varStatus="num">
        <c:if test="${num.index==0}">
        <div class="box6_content"> <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${groupId}/huxingtu/${l.type}/"> <img src="${pic}" width="100%" alt = "${l.minMaxArea }"/>
          <div class="box6_box1"></div>
          <div class="box6_box2">${l.minMaxArea }</div>
          </a> </div>
        </c:if>
        </c:forEach>
        <div class="box6_ul">
          <ul>
          <c:forEach var="l" items="${layouts}" end="4" varStatus="num">
     	<c:if test="${num.index!=0 && num.index<3}">
            <li><a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${groupId}/huxingtu/${l.type}/"><span><img src="http://192.168.242.44/sceapp/focus_static/wap/loupan/images/icon_right_arrow.png" width="24" height="24" alt = "${l.minMaxArea }"/></span>${l.minMaxArea }</a></li>
         </c:if>
         </c:forEach>
          </ul>
        </div>
        </c:if>
      </div>
      <c:if test="${layoutCount>3}">
      <div class="box2_2"><a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${groupId}/huxingtu/-1/">查看全部</a></div>
      </c:if>