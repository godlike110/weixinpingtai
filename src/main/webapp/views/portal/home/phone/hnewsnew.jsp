<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<div class="f_Table">
	<div class="f_Table_nav">
		<span class="f_th"><a href="javascript:void(0)"><h2 style="font-size: 24px;">新闻</h2></a></span>
	</div>
	<div class="sh_con_wp_rl10">
		<div class="f_Tabl_con2">
			<c:forEach var="news" items="${newsList}" varStatus="num">
				<c:if test="${num.index==0}">
					<c:if test="${''!=logo}">
						<a class="f_info_p paddbo15" href="<c:choose><c:when test="${not empty fn:trim(news.newsId) }">${ctx}/${_city.cityPinyinAbbr}/zixun/${news.newsId}/</c:when><c:otherwise>${news.newsUrl }</c:otherwise></c:choose>">
							<div class="f_info_img">
								<img src="${news.imgLogo}" alt = "${news.title }"/>
							</div>
							<div class="f_info_c">
								<p class="it_name line-h20">${news.title }</p>
								<div class="f_info_c_b_3">${news.description}</div>
							</div>
						</a>
					</c:if>
					<c:if test="${''==logo}">
						<a class="f_info_li2" href="<c:choose><c:when test="${not empty fn:trim(news.newsId) }">${ctx}/${_city.cityPinyinAbbr}/zixun/${news.newsId}/</c:when><c:otherwise>${news.newsUrl }</c:otherwise></c:choose>">${news.title }</a>
					</c:if>
				</c:if>
				<c:if test="${num.index>0}">
					<a class="f_info_li2" href="<c:choose><c:when test="${not empty fn:trim(news.newsId) }">${ctx}/${_city.cityPinyinAbbr}/zixun/${news.newsId}/</c:when><c:otherwise>${news.newsUrl }</c:otherwise></c:choose>">${news.title}</a>
				</c:if>
			</c:forEach>
		</div>
		<div class="f_Tabl_con_btn" name="newsAll">
			<a href="${ctx}/${_city.cityPinyinAbbr}/zixun/">进入新闻频道<i class="f_Tabl_con_btn_ico"></i></a>
		</div>
	</div>
</div>
