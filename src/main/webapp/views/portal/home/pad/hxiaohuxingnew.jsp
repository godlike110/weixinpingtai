<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<c:if test="${not empty littleHouse}">


				<div class="f_Tabl_con" name="xiaohx">
				<c:forEach var="proj" items="${littleHouse}">
					<div class="f_info_p">
						<div class="f_info_img"><a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${proj.groupId}/"><img src="${proj.url}" alt = "${proj.projNameNoSplit}"></a></div>
						<div class="f_info_c">
							<h3><a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${proj.groupId}/">${proj.projNameNoSplit}</a>
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
							<i class="f_info_c_Line_ico2"></i><span class="f_info_c_Line_l">

								<c:if test="${proj.roomOne != '' }">一居室${proj.roomOne}	</c:if>
								<c:if test="${proj.roomTwo != '' }">二居室${proj.roomTwo}	</c:if>
								<c:if test="${proj.roomTwo == '' && proj.roomOne == ''  }">暂无数据</c:if>
							
							</span>
							</div>
						</div>
					</div>
</c:forEach>
					<div class="f_Tabl_con_btn">
						<a href="${ctx}/${_city.cityPinyinAbbr}/loupan/k_____XHX_/">查看全部${total}个小户型<i class="f_Tabl_con_btn_ico"></i></a>
					</div>					
				</div>
         
         </c:if>