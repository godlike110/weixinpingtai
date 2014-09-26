<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>	
    		<c:if test="${not empty relate_news }">	
    				<ul class="news_list_ul2">
    					<c:forEach var="relateNews" items="${relate_news}" varStatus="status">
								<li><a href="${ctx}/${cityName }/zixun/${relateNews.relatedId}/" role="text" url="">
								<p class="its_nr_p">
								<c:choose>
									   <c:when test="${fn:length(relateNews.title) > 17}">
											  ${fn:substring(relateNews.title, 0, 17)}...
									   </c:when>
									   <c:otherwise>
									   		  ${relateNews.title }
									   </c:otherwise>
								</c:choose>
								</p>
								</a>
								</li>
						</c:forEach>						
					</ul>    		
			</c:if>