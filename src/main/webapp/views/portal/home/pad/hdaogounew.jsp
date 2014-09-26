<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!-- 楼盘导购 开始 -->
			
<c:if test="${not empty list}">
<div class="f_Tabl_con" name="loupdg">
        	<c:forEach var="dg" items="${list}" varStatus="num">
    	                <c:if test="${num.index==0}">
	<a class="f_info_p border_bottom_e9" href="${ctx}/${_city.cityPinyinAbbr}/daogou/${dg.id}/">
		<div class="f_info_img"><img src="${dg.pic}" alt = "${dg.title}"></div>
		<div class="f_info_c2">
			<h3>${dg.title}</h3>
			<div class="yuanchuang_text_c">
				<p class="f_info_c_b">${dg.promotion}</p>
			</div>
		</div>
	</a>
	</c:if>
	<c:if test="${num.index>0}">
	<a class="f_info_li_index" href="${ctx}/${_city.cityPinyinAbbr}/daogou/${dg.id}/">${dg.title}</a>
	</c:if>
	</c:forEach>
</div>	
   <div class="f_Tabl_con_btn f_Tabl_con_btn_hide" data-for="loupdg">
    <a href="${ctx}/${_city.cityPinyinAbbr}/daogou/" id="daogouMore">查看更多导购<i class="f_Tabl_con_btn_ico"></i></a>
</div>
</c:if>
<!-- 楼盘导购结束 -->