<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>	
    <c:if test="${not empty buildingList }">
    <h2 class="h2_tit1">推荐楼盘</h2>
		<div class="recommend_house_lists">
			<div class="recommend_house_lists_container swiper-container">
	    		<ul class="featured_property_box_list swiper-wrapper">			
	    		<c:forEach var="bl" items="${buildingList}" varStatus="status">					
									<li class="swiper-slide">
										<a href="${ctx }/${cityName}/loupan/${bl.groupId}/" url="" class="clearfix">
											<div class="fl its_img"><img alt="${bl.projName }" src="${bl.url }"></div>
											<div class="its_content">
												<p class="it_name">${bl.projName }
												<%-- <c:choose>
													   <c:when test="${fn:length(bl.projName) > 6}">
															  ${fn:substring(bl.projName, 0, 6)}...
													   </c:when>
													   <c:otherwise>
													   		  ${bl.projName }
													   </c:otherwise>
												</c:choose> --%>
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
												</p>
												<p class="it_address">${bl.projAddress}</p>
												<p class="it_price">${bl.avgPriceShow}</p>
												<c:if test="${ not empty fn:trim(bl.discount)}">
												<p class="it_zhekou"><em class="zhekou1"></em>${bl.discount}</p>
												</c:if>
											</div>
										</a>
									</li>
				</c:forEach>
				</ul>  		
			</div>
			<div class="pagination"></div>
		</div>
	</c:if>