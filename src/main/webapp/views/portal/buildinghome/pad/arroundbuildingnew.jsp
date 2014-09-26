<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>			
      <div class="box10">
      <c:if test="${ not empty  arroundbuilding}">
        <h2>周边楼盘</h2>
        <div class="box10_content">
          <ul>
          <c:forEach var="proj" items="${arroundbuilding}">
            <li> <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${proj.groupId}/">
              <div class="box10_imgbox"> <img src="${proj.logUrl}" width="216" height="162" alt = "${proj.projName}"/>
                <div class="box10_box1"></div>
                <div class="box10_box2"><span>${proj.avgPriceShow}</span></div>
              </div>
              <h3>${proj.projName}</h3>
              <h4><span>${proj.avgPriceShow}</span></h4>
              <div class="clear"></div>
              </a> </li>
              </c:forEach>
          </ul>
        </div>
        </c:if>
    </div>