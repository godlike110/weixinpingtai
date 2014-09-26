 <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> <%@ include file="/common/taglibs.jsp"%>
 
  <div class="box3">
  <c:if test="${ not empty  comment}">
    <h2>主编评语</h2>
    
    <div class="box3_content">
      <div class="box3_content_name"> <strong><img src="${comment.picUrl}" width="38" height="38" /></strong><span>${comment.nickName}  ${comment.editorTitle}</span>
        <div class="clear"></div>
      </div>
      <p>${comment.recommendContent}</p>
    </div>
    </c:if>
  </div>