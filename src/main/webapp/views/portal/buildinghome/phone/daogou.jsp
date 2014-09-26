<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> <%@ include file="/common/taglibs.jsp"%>

  	<div class="box9">
  	<c:if test="${ not empty  dg}">
    	<h2>楼盘导购</h2>
        <div class="box9_content">
        	 <ul>
        	 <c:forEach var="dl" items="${dg}">
             	<li>
                	<a href="${ctx }/${_city.cityPinyinAbbr }/daogou/${dl.id}/"><div class="box9_content_name"> <strong><img src="${dl.editorPic}" width="38" height="38" alt = "${dl.editorName}"/></strong><span>${dl.editorName}  编辑</span>
        				<div class="clear"></div>
      				</div>
                    <h3>${dl.title}</h3>
                    <p>${dl.content}</p></a>
                </li>
                </c:forEach>
             </ul>
        </div>
        </c:if>
    </div>