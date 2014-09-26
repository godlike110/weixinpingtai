<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<div class="in-slider">
	<div class="in-slider-cont">
		<div class="swiper-container swiper1">
			<div class="swiper-wrapper">
				<c:forEach var="item" items="${focuslist}">
					<div class="swiper-slide" data-title="${item.title}">
						<c:if test="${item.absoluteUrl}">
							<a href="${item.url}"><img src="${item.picUrl}" /></a>
						</c:if>
						<c:if test="${item.absoluteUrl==false}">
							<a href="${ctx}/${_city.cityPinyinAbbr}/${item.url}"><img
								src="${item.picUrl}" /> </a>
						</c:if>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="in_slider_th">
			<span class="paddl8">
				<c:forEach var="item" items="${focuslist}" varStatus="status">
					<c:if test="${status.index==0}">${item.title}</c:if>
				</c:forEach>
			</span>
		</div>
	</div>
	<c:if test="${listSize > 0}">
	<div class="pagination1"></div>
	</c:if>
</div>
