<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>

<div name="divTest" title="zhaofang"> 
          <div class="f_Tabl_con"> 
           <div class="f_Tabl_con_index" name="quyzf"> 
            <dl class="border_bottom_e9 border_top_e9">
			<dt><span>区域找房</span></dt>
				<div class="Table_index">
					<div class="Table_index_ico"></div>
					<div class="ml_l20">
					<c:forEach var="hotCond" items="${hotList}">
						<dd>
							<a href="${ctx}/${_city.cityPinyinAbbr}/loupan/k${hotCond.condValue}______/">
								<c:choose><c:when test="${fn:length(hotCond.condName) > 3}"><c:out value="${fn:substring(hotCond.condName, 0, 2)}" />...</c:when><c:otherwise><c:out value="${hotCond.condName}" /></c:otherwise></c:choose>
							</a>
						</dd>
					</c:forEach>
					
					</div>
				</div>
				<i class="f_Tabl_con_ico_more mr-r10"></i>
			</dl>
 
           </div> 
           <div class="f_Tabl_con_index_b" id="price_range"> 
            <dl class="border_bottom_e9 border_top_f">
				<dt><span>价格区间</span></dt>
				<div class="Table_index">
					<div class="Table_index_ico"></div>
					<div class="ml_r15">
					<c:forEach var="priceCond" items="${priceList}">
						<dd><a href="${ctx}/${_city.cityPinyinAbbr}/loupan/k__${priceCond.condValue}____/">${priceCond.condName}</a></dd>
					</c:forEach>
					</div>
				</div>
				<i class="f_Tabl_con_ico mr-r10"></i>
			</dl>  
           </div> 
           
           <div class="f_Tabl_con_index_b" id="feature_area"> 
            <dl class="border_bottom_e9 border_top_f">
				<dt><span style="margin-top: -11px;">特色</span></dt>
				<div class="Table_index" style="height: 78px;">
					<div class="Table_index_ico"></div>
					<div class="ml_r15">
						<dd><a href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____XF_/'>现房</a></dd>
						<dd><a href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____GYPB_/'>公园旁边</a></dd>
						<dd><a href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____DZJ_/'>低总价</a></dd>
                        <dd><a href='${ctx}/${_city.cityPinyinAbbr}/loupan/k_____JYSQ_/'>教育社区</a></dd>
					</div>
				</div>
				<i class="f_Tabl_con_ico mr-r10" style="display: none;"></i>
			</dl>            
           </div> 
           
           <div class="f_Tabl_con_btn_m_index" name="zhaofAll"> 
            <div class="f_Tabl_con_btn">
             <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/">查看全部${total}个楼盘<i class="f_Tabl_con_btn_ico"></i></a>
            </div> 
           </div> 
          </div> 
         </div>         
