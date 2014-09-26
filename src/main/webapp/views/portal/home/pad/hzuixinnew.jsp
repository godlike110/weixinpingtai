<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>	
<c:if test="${not empty newHouse}">
				<div class="f_Tabl_con" name="zuixkp">
				<c:forEach var="proj" items="${newHouse}">
				
					<div class="f_info_p">
						<div class="f_info_img"><a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${proj.groupId}/"><img src="${proj.url }" alt = "${proj.projNameNoSplit}"></a></div>
						<div class="f_info_c">
							<h3><a href="#">${proj.projNameNoSplit}</a>
							          <c:choose>
          <c:when test="${proj.saleStatus == 0}">
          <span class="status sale">在售</span>
          </c:when>
          <c:when test="${proj.saleStatus == 1}">
          <span class="status wait">待售</span>
          </c:when>
          <c:when test="${proj.saleStatus == 2}">
          <span class="status remain">尾盘</span>
          </c:when>
          <c:otherwise>
          <span class="status over">售罄</span>
          </c:otherwise>
          </c:choose>
							</h3>
							<div class="f_info_c_Line2 yuan_lSM"><span class="f_info_c_Line_People">${proj.avgPriceShow}</span>${proj.projAddress}</div>
							<div class="f_info_c_b">
							<i class="f_info_c_Line_ico3"></i><span class="f_info_c_Line_l">
							
														<c:choose>
							<c:when test="${fn:length(proj.saleDateDetail) > 14}">
							<c:out value="${fn:substring(proj.saleDateDetail, 0, 13)}" />...</c:when>
							<c:otherwise><c:out value="${proj.saleDateDetail}" /></c:otherwise>
							</c:choose>
							
							</span>
							
							

							
							
							
							</div>
						</div>
					</div>
				</c:forEach>
					<div class="f_Tabl_con_btn">
						<a href="${ctx}/${_city.cityPinyinAbbr}/loupan/k_____ZXKP_/">查看全部${total}个最新楼盘<i class="f_Tabl_con_btn_ico"></i></a>
					</div>					
				</div>
</c:if>         