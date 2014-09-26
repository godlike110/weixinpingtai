<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<div class="f_Table_nav_r">
	<span class="f_th"><a href="${ctx}/${_city.cityPinyinAbbr}/zixun/" style="display: inline-block;"><h2 style="font-size: 20px;">今日头条</h2></a><i class="f_th_ico"></i></span>
</div>
<div class="f_Tabl_con paddtop20">
	<c:forEach var="news" items="${newsList}" varStatus="num">
		<c:if test="${num.index==0}">
			<c:if test="${''!=logo}">
				<a class="f_info_li_index_r" href="<c:choose><c:when test="${not empty fn:trim(news.newsId) }">${ctx}/${_city.cityPinyinAbbr}/zixun/${news.newsId}/</c:when><c:otherwise>${news.newsUrl }</c:otherwise></c:choose>">
				
					<div class="f_info_r_img m_w100" >
						<img src="${news.imgLogo}" alt = "${news.title}">
					</div>
					<div class="f_info_r_c m_w100">
						<h3>${news.title}</h3>
						<div class="index_toutiao_r_p">
							<p class="f_info_c_b">${news.description}</p>
						</div>
					</div>
				</a>
			</c:if>
			<c:if test="${''==logo}">
				<a class="f_info_li_index" href="<c:choose><c:when test="${not empty fn:trim(news.newsId) }">${ctx}/${_city.cityPinyinAbbr}/zixun/${news.newsId}/</c:when><c:otherwise>${news.newsUrl }</c:otherwise></c:choose>">${news.title}</a>
			</c:if>
		</c:if>
		<c:if test="${num.index>0}">
			<a class="f_info_li_index" href="<c:choose><c:when test="${not empty fn:trim(news.newsId) }">${ctx}/${_city.cityPinyinAbbr}/zixun/${news.newsId}/</c:when><c:otherwise>${news.newsUrl }</c:otherwise></c:choose>">${news.title}</a>
		</c:if>
	</c:forEach>
</div>
<div class="f_Tabl_con_btn">
	<a href="${ctx}/${_city.cityPinyinAbbr}/zixun/" id="newsMore">查看更多新闻<i class="f_Tabl_con_btn_ico"></i></a>
</div>