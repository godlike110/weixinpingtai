<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<div class="box7">
	<c:if test="${not empty  questions}">
		<h2>楼盘咨询</h2>
		<div class="box7_content">
			<ul>
				<c:forEach var="question" items="${questions}" varStatus="num"
					end="2">
					<li>
						<a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/zixun/${question.id}/">
							<div class="box7_q">问：${question.question}</div>
							<div class="box7_date">${question.createTimeStr}</div>
							<c:choose>
                				<c:when test="${question.status==6}">
									<div class="box7_a">答：${question.answer}</div>
									<div class="box7_date">${question.updateTimeStr}</div>
                				</c:when>
                				<c:otherwise>
                					<div class="box7_a">等待回复</div>
                				</c:otherwise>
                			</c:choose>
						</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</c:if>
</div>
<c:if test="${checkAll==2}">
	<div class="box2_2">
		<a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/zixun/">查看全部</a>
	</div>
</c:if>
<c:if test="${checkAll==3}">
	<div class="box2_2">
		<a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/zixun/">查看全部（您已咨询过）</a>
	</div>
</c:if>
