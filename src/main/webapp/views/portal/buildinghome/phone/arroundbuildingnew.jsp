<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
  	<div class="box10">
  	<c:if test="${ not empty  arroundbuilding}">
    	<h2>周边楼盘</h2>
        <div class="box10_content">
        	 <ul>
        	 <c:forEach var="proj" items="${arroundbuilding}">
             	<li><a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${proj.groupId}/"><span><strong>${proj.avgPriceShow}</strong></span>${proj.projName}</a></li>
			 </c:forEach>
             </ul>
        </div>
       </c:if>  
    </div>
    
   <c:if test="${ not empty  arroundbuilding}"> 
            <section class="public-module">
            <div class="public-module-tab" id="public-module-tab2">
                <div class="public-module-tab-box">
                    <a class="item active">周边楼盘</a>
                    <a class="item">推荐楼盘</a>
                </div>
            </div>
            <div class="public-module-box">
                <ul class="loupan-huxing-container show loupan-suggest-loupan">
                <c:forEach var="proj" items="${arroundbuilding}">
                    <li>
                        <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${proj.groupId}/" class="loupan-huxing-item loupan-relative">
                            <div>
                                <div class="loupan-huxing-img-container"><img src="${proj.logUrl}" alt=""></div>
                                <div class="loupan-suggest-description text-nowrap">${proj.projAddress}
                                    <div class="loupan-suggest-detail">districtName<br>现房<br/><span class="loupan-low-pay-icon loupan-icons"></span>低总价</div>
                                </div>
                                <span class="base-fontcolor loupan-right loupan-huxing-price">${proj.avgPriceShow}</span>
                            </div>                
                        </a>
                    </li>
                    </c:forEach>
                </ul>
                <ul class="loupan-huxing-container loupan-suggest-loupan">

                </ul>
            </div>
        </section>
        </c:if>
    