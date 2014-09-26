<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>	
    <c:if test="${not empty buildingList }">
    <h2 class="h2_tit1">推荐楼盘</h2>
   <div class="recommend_house_lists">
		<div class="recommend_house_lists_container swiper-container">
			<ul class="article_list article_img swiper-wrapper">				
	    		<c:forEach var="bl" items="${buildingList}" varStatus="status">					
									<li class="swiper-slide">
										<a href="${ctx }/${cityName}/loupan/${bl.groupId}/" url="" class="clearfix">
											<img class="img fl" alt="${bl.projName }" src="${bl.url }">
											<dl>
											<dt>
												<c:choose>
													   <c:when test="${fn:length(bl.projName) > 6}">
															  ${fn:substring(bl.projName, 0, 6)}...
													   </c:when>
													   <c:otherwise>
													   		  ${bl.projName }
													   </c:otherwise>
												</c:choose>
												<c:choose>
													   <c:when test="${bl.saleStatus == 0}">
															  <em class='in_sale' data-title=在售></em>
													   </c:when>
													   <c:when test="${bl.saleStatus == 1}">
															  <em class='wait_sale' data-title=待售></em>
													   </c:when>
													   <c:when test="${bl.saleStatus == 2}">
															  <em class='last_sale' data-title=尾盘></em>
													   </c:when>
													   <c:otherwise>
															  <em class='out_sale' data-title=售罄></em>
													   </c:otherwise>
												</c:choose>
											</dt>
											<dd class="clearfix">${bl.projAddress}<span class="price fr">${bl.avgPriceShow}</span></dd>
											<dd>
												<c:if test="${ not empty fn:trim(bl.discount)}">
													<span class="mark discount">${bl.discount}</span>
												</c:if>
											</dd>
											</dl>																				
										</a>
									</li>
				</c:forEach>
				</ul>  		
			</div>
			<div class="pagination"></div>
		</div>
	</c:if>