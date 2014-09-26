<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>		
<!-- 购房宝典-模块 开始 -->

<div class="sh_con_wp_rl10"  title="one"> 
	<div class="f_Tabl_con2"> 
 	<c:forEach var="proj" items="${guideList}" varStatus="num">
 		<c:if test="${num.index==0}">
  			<a class="f_info_p paddbo15" href="${ctx}/${_city.cityPinyinAbbr}/baodian/${proj.id}/"> 
   					<div class="f_info_img">
    					<img src="${proj.picUrl}" alt = "${proj.title}"/>
   					</div>
   					<div class="f_info_c"> 
    					<p class="it_name line-h20">${proj.title}</p> 
    					<div class="f_info_c_b_3">${proj.summary}</div> 
   					</div> 
  			</a> 
  		</c:if>
		<c:if test="${num.index>0}">
  			<a class="f_info_li2" href="${ctx}/${_city.cityPinyinAbbr}/baodian/${proj.id}/">${proj.title}</a>
  		</c:if>
 	</c:forEach>
 	</div>
 	<div class="f_Tabl_con_btn" name="baodAll"> 
  		<a href="${ctx}/${_city.cityPinyinAbbr}/baodian/">进入宝典频道<i class="f_Tabl_con_btn_ico"></i></a> 
 	</div> 
</div> 
<!-- // 购房宝典-模块结束 -->