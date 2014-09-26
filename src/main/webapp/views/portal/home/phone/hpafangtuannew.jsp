<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<c:if test="${not empty first}">
<div class="f_Table"> 
	<div class="f_Table_nav" name="pftuan">
		<span class="f_th"><a href="javascript:void(0)" style="display: inline-block;"><h2 style="font-size: 24px;">看房团</h2></a></span> 
	    <ul class="f_Table_nav_c">
	    	<li><a href="javascript:void(0)" class="colorcb" name="one">${firstTime }</a><span class="f_Table_nav_hov"></span></li>
	    	<c:if test="${not empty second }">
	    		<li class="ml_114"><a href="javascript:void(0)" name="two">${secondTime }</a><span></span></li>
	    	</c:if>
	    </ul>
	</div>
	<div class="sh_con_wp_rl10" title="one">
		<div class="f_Tabl_con">
			<c:forEach var="pft" items="${first}" end="3">
				<div class="f_info_p"> 
					<a href="${ctx}/${_city.cityPinyinAbbr}/pafangtuan/${pft.lineId}">
						<div class="f_info_img">
						  	<img src="${pft.pic}" alt = "${pft.title}"/>
						</div>
						<div class="f_info_c">
							<p class="it_name">${pft.title}</p>
							<div class="f_info_c_b color444">
								<span class="f_info_c_r">报名<span class="colorcb">${pft.signUpNum}</span>人</span> 线路包含<span class="colorcb">${pft.buildNum}</span> 个楼盘
							</div>
							<div class="f_info_c_b_1">${pft.lightspot}</div>
						</div>
					</a>
				</div>
			</c:forEach>
		</div>
		<div class="f_Tabl_con_btn" name="paftAll">
			<a href="${ctx}/${_city.cityPinyinAbbr}/pafangtuan/">查看全部${count}个看房团<i class="f_Tabl_con_btn_ico"></i></a> 
		</div> 
	</div>
	
	<c:if test="${not empty second }">
		<div class="sh_con_wp_rl10" style="display:none" title="two">
			<div class="f_Tabl_con">
				<c:forEach var="pft" items="${second}" end="3">
					<div class="f_info_p"> 
						<a href="${ctx}/${_city.cityPinyinAbbr}/pafangtuan/${pft.lineId}">
							<div class="f_info_img">
							  	<img src="${pft.pic}" />
							</div>
							<div class="f_info_c">
								<p class="it_name">${pft.title}</p>
								<div class="f_info_c_b color444">
									<span class="f_info_c_r">报名<span class="colorcb">${pft.signUpNum}</span>人</span> 线路包含<span class="colorcb">${pft.buildNum}</span> 个楼盘
								</div>
								<div class="f_info_c_b_1">${pft.lightspot}</div>
							</div>
						</a>
					</div>
				</c:forEach>
			</div>
			<div class="f_Tabl_con_btn" name="paftAll">
				<a href="${ctx}/${_city.cityPinyinAbbr}/pafangtuan/">查看全部${count}个看房团<i class="f_Tabl_con_btn_ico"></i></a> 
			</div> 
		</div>
	</c:if>
</div> 
</c:if>