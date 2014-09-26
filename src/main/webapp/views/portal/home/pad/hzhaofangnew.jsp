<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>

				<div class="f_Tabl_con" name="zhaofang">
					<dl class="border_bottom_e9" >
						<dt>区域</dt>
						<div class="Area_list_r" name="quyzf" style="overflow-y:hidden;">
						<c:forEach var="hotCond" items="${hotList}">
							<dd><a href="${ctx}/${_city.cityPinyinAbbr}/loupan/k${hotCond.condValue}______/">
							<c:choose><c:when test="${fn:length(hotCond.condName) > 3}"><c:out value="${fn:substring(hotCond.condName, 0, 2)}" />...</c:when><c:otherwise><c:out value="${hotCond.condName}" /></c:otherwise></c:choose>
							</a></dd>				
							<i name="ico"></i>
							</c:forEach>
						</div>
					</dl>

					<dl class="border_bottom_e9 border_top_f">
						<dt>价格</dt>
						<div class="Area_list_r2" style="overflow-y:hidden;">
							<ul>								
						<c:forEach var="priceCond" items="${priceList}">
						<li><a href="${ctx}/${_city.cityPinyinAbbr}/loupan/k__${priceCond.condValue}____/">${priceCond.condName}</a></li>
					</c:forEach>
											
							</ul>
							<i name="ico"></i>
						</div>
					</dl>

					<dl class="border_top_f">
						<dt>特色</dt>
						<div class="Area_list_r3">
							<ul>							
						<li onmouseover='tHotOver(this)' onmouseout='tHotOut(this)'
							onclick='search(this)'><a
							href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____XF_/'>现房</a></li>
						<li onmouseover='tHotOver(this)' onmouseout='tHotOut(this)'
							onclick='search(this)'><a
							href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____DZLP_/'>打折优惠</a></li>
						<li onmouseover='tHotOver(this)' onmouseout='tHotOut(this)'
							onclick='search(this)'><a
							href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____ZXKP_/'>最新开盘</a></li>
						<li onmouseover='tHotOver(this)' onmouseout='tHotOut(this)'
							onclick='search(this)'><a
							href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____XHX_/'>小户型</a></li>
						<li onmouseover='tHotOver(this)' onmouseout='tHotOut(this)'
							onclick='search(this)'><a
							href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____GYPB_/'>公园旁边</a></li>
						<li onmouseover='tHotOver(this)' onmouseout='tHotOut(this)'
							onclick='search(this)'><a
							href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____DZJ_/'>低总价</a></li>
						<li onmouseover='tHotOver(this)' onmouseout='tHotOut(this)'
							onclick='search(this)'><a
							href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____JYSQ_/'>教育社区</a></li>
							
							</ul>
						</div>
					</dl>
					<div class="f_Tabl_con_btn">
					<a href="${ctx}/${_city.cityPinyinAbbr}/loupan/" id="sellingMore">查看全部${total}个在售楼盘<i class="f_Tabl_con_btn_ico"></i></a>
					</div>
				</div>