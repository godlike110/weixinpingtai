<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>	
<c:if test="${ not empty  tg1}">
        <section class="public-module">
            <div class="public-module-title"><p><span class="base-bgcolor public-title-icon"></span>团购</p></div>
            <a href = "${ctx }/${_city.cityPinyinAbbr }/tuangou/${tg1.active_id}/" class = "loupan-tuangou-container">
                <div class="loupan-relative">
                    <div class="loupan-public-left ">${tg1.active_name}<div class="loupan-tuangou-description"><span class="loupan-icons loupan-tuangou-icon"></span>团购剩余：${tg1.active_start}</div>
                    </div> 
                    <span class="loupan-icons loupan-arrow-icon"></span>
                </div>  
                <span class="loupan-signup-button">立即抢购</span>                 
            </a>
        </section>
 </c:if>