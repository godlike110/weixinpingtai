<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>		
<!-- 购房宝典-模块 开始 -->
<c:if test="${not empty guideList}">
<div class="f_Tabl_con" name="goufbd">
 	<c:forEach var="proj" items="${guideList}" varStatus="num">
		<c:if test="${num.index==0}">
	<a class="f_info_p border_bottom_e9" href="${ctx}/${_city.cityPinyinAbbr}/baodian/${proj.id}/">
		<div class="f_info_img"><img src="${proj.picUrl}" alt = "${proj.title}"></div>
		<div class="f_info_c2">
			<h3>${proj.title}</h3>
			<div class="yuanchuang_text_c">
				<p class="f_info_c_b">${proj.summary}</p>
			</div>
		</div>
	</a>
	</c:if>
	
	<c:if test="${num.index>0}">
	<a class="f_info_li_index" href="${ctx}/${_city.cityPinyinAbbr}/baodian/${proj.id}/">${proj.title}</a>
	</c:if>
	</c:forEach>
</div>
<div class="f_Tabl_con_btn" data-for="goufbd">
    <a href="${ctx}/${_city.cityPinyinAbbr}/baodian/" id="baodMore">查看更多宝典<i class="f_Tabl_con_btn_ico"></i></a>
</div>
</c:if>
<!-- // 购房宝典-模块结束 -->