<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>	
    		<c:if test="${not empty relate_news }">	
    				<ul class="news_list_ul2">
    					<c:forEach var="relateNews" items="${relate_news}" varStatus="status">
								<li><a href="${ctx}/${cityName }/zixun/${relateNews.relatedId}/" role="touch_bg" url=""><p class="its_nr_p">${relateNews.title}</p></a></li>
						</c:forEach>						
					</ul>    		
			</c:if>