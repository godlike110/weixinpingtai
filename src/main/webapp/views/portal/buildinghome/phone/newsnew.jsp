<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>	
  
          <section class="public-module">
            <div class="public-module-title"><p><span class="base-bgcolor public-title-icon"></span>楼盘动态
                <span class="loupan-subheading">&nbsp;最新开盘</span><span class="loupan-subheading">打折信息</span></p></div>
            <c:if test="${empty  recentNews}">
    		暂无最新动态
    		</c:if>
    		<c:if test="${ not empty  recentNews}">
    		<c:forEach var="news" items="${recentNews}">
            <a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${info.groupId}/dongtai/${news.infoid}/" class="loupan-news-container loupan-relative">
                <div class="loupan-public-left ">
                    <span class="loupan-news-icon">打折</span>${news.infoname}<p class="loupan-discount-description">
                        ${news.infocontent}
                    </p>
                    <p class="loupan-discount-description">${news.infotime}</p>
                </div> 
                <span class="loupan-icons loupan-arrow-icon"></span>   
                            
            </a>
            </c:forEach>
            </c:if>
        </section>