<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<c:if test="${not empty pft}">
<section class="public-module">
            <div class="public-module-title"><p><span class="base-bgcolor public-title-icon"></span>看房团</p></div>
            <a href="${ctx}/${_city.cityPinyinAbbr}/pafangtuan/${pft.lineId}/" class="loupan-tuangou-container">
                <div class="loupan-relative">
                    <div class="loupan-public-left ">
                        ${pft.title}
                        <div class="loupan-tuangou-description">
                            <span class="loupan-icons loupan-pafangtuan-icon"></span>${pft.activeDate}
                            <span class="loupan-icons loupan-user-icon"></span>${pft.signUpNum}人报名
                        </div>
                    </div> 
                    <span class="loupan-icons loupan-arrow-icon"></span>
                </div>  
                <span class="loupan-signup-button">立即报名</span>                 
            </a>
        </section>
</c:if>	