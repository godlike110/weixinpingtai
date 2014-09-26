<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> <%@ include file="/common/taglibs.jsp"%>
  <c:if test="${layoutCount != 0 }">
          <section class="public-module">
            <div class="public-module-title"><p><span class="base-bgcolor public-title-icon"></span>楼盘户型</p></div>
            <ul class="loupan-huxing-container">
            <c:forEach var="l" items="${layouts}" end="2" varStatus="num">
                <li>
                    <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${groupId}/huxingtu/${l.type}" class="loupan-huxing-item loupan-relative">
                        <div class="loupan-public-left ">
                            <div class="loupan-huxing-img-container"><img src="${l.picUrl}" alt=""/></div>
                            <div class="loupan-huxing-description">${l.typeStr}
                                <div class="loupan-huxing-detail">${l.typeClassStr}<br/>${l.minMaxArea}</div>
                            </div>
                            <span class="base-fontcolor loupan-right loupan-huxing-price">${l.minMaxPrice}</span>
                        </div> 
                        <span class="loupan-icons loupan-arrow-icon"></span>                 
                    </a>
                </li>
            </c:forEach>
            </ul>
            <c:if test="${layoutCount>3}">
            <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${groupId}/huxingtu/" class="public-module-more" data-btn="showMore"><span>查看全部${layoutCount}种户型</span><span class="public-more-icon1"></span></a>
            </c:if>
        </section>
        </c:if>
  
  
  
  
  