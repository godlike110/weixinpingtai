<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>

<c:if test="${not empty disCouentHouse}">
         <div name="divTest" title="dazyh" style="display:none"> 
          <div class="sh_con_wp_rl10"> 
           <div class="f_Tabl_con"> 
<c:forEach var="proj" items="${disCouentHouse}">
            <div class="f_info_p"> 
            <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${proj.groupId}/">
             <div class="f_info_img">
              <img src="${proj.url}" alt = "${proj.projNameNoSplit}"/>
             </div> 
             <div class="f_info_c"> 
              <p class="it_name">${proj.projNameNoSplit}
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
              </p> 
              <div class="f_info_c_b">
               ${proj.projAddress}
              </div> 
              <div class="f_info_c_b">
               ${proj.avgPriceShow}
              </div> 
              <div class="f_info_c_Line">
               <i class="f_info_c_Line_ico"></i>
               <span class="f_info_c_Line_l">${proj.discount}</span>
              </div> 
             </div> 
             </a>
            </div> 
</c:forEach>
           </div> 
           <div class="f_Tabl_con_btn" name="zhaofAll"> 
            <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/k_____DZLP_/">查看全部${total}个打折楼盘<i class="f_Tabl_con_btn_ico"></i></a> 
           </div> 
          </div> 
         </div> 
        </c:if>