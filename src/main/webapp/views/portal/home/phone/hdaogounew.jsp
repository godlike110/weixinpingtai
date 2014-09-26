<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!-- 楼盘导购 开始 -->
			
<c:if test="${not empty list}">
	<div class="sh_con_wp_rl10" style="display:none" title="two"> 
    	<div class="f_Tabl_con2"> 
        	<c:forEach var="dg" items="${list}" varStatus="num">
        	<c:if test="${num.index==0}">
           	<a class="f_info_p paddbo15" href="${ctx}/${_city.cityPinyinAbbr}/daogou/${dg.id}/"> 
            		<div class="f_info_img">
             			<img src="${dg.pic}" alt = "${dg.title}"/>
            		</div> 
            		<div class="f_info_c"> 
             			<p class="it_name line-h20">${dg.title}</p> 
             			<div class="f_info_c_b_3">${dg.promotion}</div> 
            		</div> 
           </a> 
           </c:if>
           <c:if test="${num.index>0}">
           <a class="f_info_li2" href="${ctx}/${_city.cityPinyinAbbr}/daogou/${dg.id}/">${dg.title}</a> 
           </c:if>
           </c:forEach>
        </div> 
       
        <div class="f_Tabl_con_btn" name="baodAll">
        	<a href="${ctx}/${_city.cityPinyinAbbr}/daogou/">进入导购频道<i class="f_Tabl_con_btn_ico"></i></a>
        </div> 
	</div> 
</c:if>
<!-- 楼盘导购结束 -->