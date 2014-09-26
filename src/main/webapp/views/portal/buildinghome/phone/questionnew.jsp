<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>	

        <section class="public-module">
            <div class="public-module-title"><p><span class="base-bgcolor public-title-icon"></span>网友提问
                <span class="base-fontcolor loupan-right"><span class="loupan-icons loupan-ask-icon"></span>我要提问</span></p></div>
          <c:if test="${not empty  questions}">
            <ul class="loupan-ask-list">
            <c:forEach var="question" items="${questions}" varStatus="num" end="3">
                <li class="loupan-ask-item">
                    <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/zixun/${question.id}/" class="loupan-block loupan-relative">
                    <div class="loupan-public-left ">
                        <p>问：${question.question}</p>
                        <p class="loupan-ask-time">${question.createTimeStr}</p>
                    </div> 
                    <span class="loupan-icons loupan-arrow-icon"></span>                 
                    </a>                
                </li>
                </c:forEach>
            </ul>
            <c:if test="${checkAll>3}">
            <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/zixun/" class="public-module-more" data-btn="showMore"><span>点击查看全部</span><span class="public-more-icon1"></span></a>
            </c:if>
        </c:if>
        </section>

