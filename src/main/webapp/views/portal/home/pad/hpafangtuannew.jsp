<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<c:if test="${not empty first}">
<div class="f_Table">
	<div class="f_Table_nav2" name="pftuan">
		<div class="f_th_w"><span class="f_th"><a href="/hn/pafangtuan/" style="display: inline-block;"><h2 style="font-size: 20px;">看房团</h2></a><i class="f_th_ico"></i></span></div>
		<ul class="f_Table_nav_c2">
			<li  class="f_Table_nav_hov">
				<a href="javascript:void(0)" name="one" class="colorcb">${firstTime}</a>
			</li>
			<c:if test="${not empty second }">
				<li>
					<a href="javascript:void(0)" name="two">${secondTime }</a>
				</li>
			</c:if>
		</ul>
	</div>
	
	<div class="f_Tabl_con" name="one">
		<c:forEach var="pft" items="${first}" end="3">
			<a href="${ctx}/${_city.cityPinyinAbbr}/pafangtuan/${pft.lineId }/" class="f_info_p">
				<div class="f_info_img"><img src="${pft.pic}" alt = "${pft.title}"></div>
				<div class="f_info_c">
					<h3>
						<c:choose>
							<c:when test="${fn:length(pft.title) > 16}"><c:out value="${fn:substring(pft.title, 0, 16)}" /></c:when>
							<c:otherwise><c:out value="${pft.title}" /></c:otherwise>
						</c:choose>
					</h3>
					<div class="f_info_c_Line"><i class="f_info_c_Line_ico"></i><span class="f_info_c_Line_People">已报名<span class="colorcb">${pft.signUpNum}</span>人</span><span class="f_info_c_Line_l">线路包含${pft.buildNum}个楼盘</span></div>
					<div class="f_info_c_b">
						<c:choose>
							<c:when test="${fn:length(pft.lightspot) > 19}"><c:out value="${fn:substring(pft.lightspot, 0, 19)}" /></c:when>
							<c:otherwise><c:out value="${pft.lightspot}" /></c:otherwise>
						</c:choose>
					</div>
				</div>
			</a>
		</c:forEach>
	</div>
	
	<c:if test="${not empty second }">
		<div class="f_Tabl_con" name="two">
			<c:forEach var="pft" items="${second}" end="3">
				<a href="${ctx}/${_city.cityPinyinAbbr}/pafangtuan/${pft.lineId }/" class="f_info_p">
					<div class="f_info_img"><img src="${pft.pic}"></div>
					<div class="f_info_c">
						<h3>
							<c:choose>
								<c:when test="${fn:length(pft.title) > 16}"><c:out value="${fn:substring(pft.title, 0, 16)}" /></c:when>
								<c:otherwise><c:out value="${pft.title}" /></c:otherwise>
							</c:choose>
						</h3>
						<div class="f_info_c_Line"><i class="f_info_c_Line_ico"></i><span class="f_info_c_Line_People">已报名<span class="colorcb">${pft.signUpNum}</span>人</span><span class="f_info_c_Line_l">线路包含${pft.buildNum}个楼盘</span></div>
						<div class="f_info_c_b">
							<c:choose>
								<c:when test="${fn:length(pft.lightspot) > 19}"><c:out value="${fn:substring(pft.lightspot, 0, 19)}" /></c:when>
								<c:otherwise><c:out value="${pft.lightspot}" /></c:otherwise>
							</c:choose>
						</div>
					</div>
				</a>
			</c:forEach>
		</div>
	</c:if>
	
	<div class="f_Tabl_con_btn">
		<a href="${ctx}/${_city.cityPinyinAbbr}/pafangtuan/" id="paftMore">查看全部${count }个看房团<i class="f_Tabl_con_btn_ico"></i></a>
	</div>
</div>
</c:if>