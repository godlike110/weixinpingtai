<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta content="telephone=no" name="format-detection" />
<title>${detail.projName }详细信息-${_city.cityName }${detail.projName }详细信息-搜狐焦点</title>
<meta name="Keywords" content="${detail.projName }详细信息-${_city.cityName }${detail.projName }详细信息-搜狐焦点">
<meta name="Description" content="搜狐焦点${_city.cityName }房产网为广大网友提供了最新的${_city.cityName }楼盘信息，最准确的${_city.cityName }房价情况和最及时的${_city.cityName }新房资讯等，是买房、购房首选网站。  ">
<%@ include file="/views/icon.jsp" %>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css">
<link href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/header-new.css" rel="stylesheet" />
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_common_v1.0.js"></script>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/top.js?c_id=1219&spno=0179" id="statScript"></script></c:if>
<script type="text/javascript">
var city = ${cityStr};
var groupId = ${groupId}; 
var phone400 = ${phone400};
</script>
</head>
<body class="wap_detail_info_all">
	<div class="wap_container">
			<!-- 新版样式导航 -->
		<jsp:include page="/views/phoneHeader.jsp">
			<jsp:param value="详情" name="page_title" />
		</jsp:include>
		
		<!-- 地图页主要内容板块 // -->
		<section class="detail_info_main">
			<div class="detail_info_container">
				<h1>${detail.projName }</h1>
				<ul class="detail_info_ul">
					<li class="clearfix">
						<div class="detail_info_li_tit">所属片区：</div>
						<div class="detail_info_li_nr">				<c:choose>
					<c:when test="${empty fn:trim(detail.districtName) }">暂无</c:when>
					<c:otherwise>${detail.districtName }</c:otherwise>
				</c:choose></div>
					</li>
					<li class="clearfix">
						<div class="detail_info_li_tit">参考价格：</div>
						<div class="detail_info_li_nr">
														<c:choose>
								<c:when test="${fn:trim(detail.avgPrice) || detail.avgPrice == '0'}"><p><span class="red1">价格待定</span></p></c:when>
								<c:otherwise><p class="gray1">${detail.avgPrice }元/平米</c:otherwise>
							</c:choose>
							
						</div>
					</li>
					<li class="clearfix">
						<div class="detail_info_li_tit">楼盘类型：</div>
						<div class="detail_info_li_nr">
						
				<c:choose>
					<c:when test="${empty fn:trim(detail.projTypeDetail) }">暂无</c:when>
					<c:otherwise>${detail.projTypeDetail }</c:otherwise>
				</c:choose>
						
						</div>
					</li>
					<li class="clearfix">
						<div class="detail_info_li_tit">楼盘位置：</div>
						<div class="detail_info_li_nr">
						
				<c:choose>
					<c:when test="${empty fn:trim(detail.projAddress) }">暂无</c:when>
					<c:otherwise>${detail.projAddress }</c:otherwise>
				</c:choose>
						
						</div>
					</li>
					<li class="clearfix">
						<div class="detail_info_li_tit">开盘时间：</div>
						<div class="detail_info_li_nr">
						
							
											<c:choose>
					<c:when test="${empty fn:trim(detail.saleDateDetail) }">暂无</c:when>
					<c:otherwise>${detail.saleDateDetail }</c:otherwise>
				</c:choose>
						
						</div>
					</li>
					<li class="clearfix">
						<div class="detail_info_li_tit">入住时间：</div>
						<div class="detail_info_li_nr">
						
							
										<c:choose>
					<c:when test="${empty fn:trim(detail.moveInDateDetail) }">暂无</c:when>
					<c:otherwise>${detail.moveInDateDetail }</c:otherwise>
				</c:choose>
						
						</div>
					</li>
					<li class="clearfix">
						<div class="detail_info_li_tit">装修情况：</div>
						<div class="detail_info_li_nr">
						
							
									<c:choose>
					<c:when test="${empty fn:trim(detail.decoLevelDetail) }">暂无</c:when>
					<c:otherwise>${detail.decoLevelDetail }</c:otherwise>
				</c:choose>
						
						</div>
					</li>
					<li class="clearfix">
						<div class="detail_info_li_tit">建筑类型：</div>
						<div class="detail_info_li_nr">
						
							
										<c:choose>
					<c:when test="${empty fn:trim(detail.constructTypeDetail) }">暂无</c:when>
					<c:otherwise>${detail.constructTypeDetail }</c:otherwise>
				</c:choose>
						
						</div>
					</li>
					<li class="clearfix">
						<div class="detail_info_li_tit">售楼地址：</div>
						<div class="detail_info_li_nr">
						
				<c:choose>
					<c:when test="${empty fn:trim(detail.saleLocate) }">暂无</c:when>
					<c:otherwise>${detail.saleLocate }</c:otherwise>
				</c:choose>
						
						</div>
					</li>
					<li class="clearfix">
						<div class="detail_info_li_tit">开发商：</div>
						<div class="detail_info_li_nr">
						
				<c:choose>
					<c:when test="${empty fn:trim(detail.kfsName) }">暂无</c:when>
					<c:otherwise>${detail.kfsName }</c:otherwise>
				</c:choose>
						
						</div>
					</li>
					<li class="clearfix">
						<div class="detail_info_li_tit">物业公司：</div>
						<div class="detail_info_li_nr">
						
				<c:choose>
					<c:when test="${empty fn:trim(detail.operationCorp) }">暂无</c:when>
					<c:otherwise>${detail.operationCorp }</c:otherwise>
				</c:choose>
						
						</div>
					</li>
					<li class="clearfix">
						<div class="detail_info_li_tit">物业费：</div>
						<div class="detail_info_li_nr">
						
				<c:choose>
					<c:when test="${empty fn:trim(detail.operationFee) }">暂无</c:when>
					<c:otherwise>${detail.operationFee }</c:otherwise>
				</c:choose>
						</div>
					</li>
					<li class="clearfix">
						<div class="detail_info_li_tit">建筑面积：</div>
						<div class="detail_info_li_nr">
						
				<c:choose>
					<c:when test="${empty fn:trim(detail.constructArea) }">暂无</c:when>
					<c:otherwise>${detail.constructArea }</c:otherwise>
				</c:choose>
						
						</div>
					</li>
					<li class="clearfix">
						<div class="detail_info_li_tit">占地面积：</div>
						<div class="detail_info_li_nr">
						
				<c:choose>
					<c:when test="${empty fn:trim(detail.landArea) }">暂无</c:when>
					<c:otherwise>${detail.landArea }</c:otherwise>
				</c:choose>
						
						</div>
					</li>
					<li class="clearfix">
						<div class="detail_info_li_tit">绿化率：</div>
						<div class="detail_info_li_nr">
						
				<c:choose>
					<c:when test="${empty fn:trim(detail.greenRatio) }">暂无</c:when>
					<c:otherwise>${detail.greenRatio }</c:otherwise>
				</c:choose>
						
						</div>
					</li>
					<li class="clearfix">
						<div class="detail_info_li_tit">容积率：</div>
						<div class="detail_info_li_nr">
						
				<c:choose>
					<c:when test="${empty fn:trim(detail.constructRatio) }">暂无</c:when>
					<c:otherwise>${detail.constructRatio }</c:otherwise>
				</c:choose>
						
						</div>
					</li>
					<li class="clearfix">
						<div class="detail_info_li_tit">土地年限：</div>
						<div class="detail_info_li_nr">
				<c:choose>
					<c:when test="${empty fn:trim(detail.soilUseYears) }">暂无</c:when>
					<c:otherwise>${detail.soilUseYears}</c:otherwise>
				</c:choose>
						
						</div>
					</li>
				</ul>				
				
			</div>
		</section>
		
		<!-- 面包屑 -->
		<div class="link_boxs"><a href="${ctx}/${_city.cityPinyinAbbr}/">新房</a><span class="h_space1">&gt;</span><a href="${ctx}/${_city.cityPinyinAbbr}/loupan/"> ${_city.cityName }楼盘</a><span class="h_space1">&gt;</span><a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${detail.groupId}/"> ${buildName}</a><span class="h_space1">&gt;</span> 楼盘详细</div>
		
		 <c:if test="${ detail.phone400!=0 }">
		<!-- 400电话 -->
		<div class="hotline_phone">
			<a href="tel:4008882200,${detail.phone400 }" class="hotline_phone_btn" phone="${detail.phone400}"><span class="pnone_icon"></span>免费咨询 400-888-2200 转 ${detail.phone400 }</a>
		</div>
		</c:if>
		<c:if test="${detail.phone400==0 }">
				<!-- 400电话 -->
		<div class="hotline_phone">
			<a href="tel:4006782020" class="hotline_phone_btn" phone="${detail.phone400}"><span class="pnone_icon"></span>免费咨询 400-678-2020</a>
		</div>
		</c:if>
		<div class="back2top" data-title="返回顶部"></div>
		
	</div>
	
	<!-- 电话弹层 // -->
	<div class="over"></div>
	<div class="alert_box" id="check_net">
     	<p class="p1">请在接通后拨分机号</p>
     	<p class="p1">${detail.phone400 }</p>
     	<div class="itos_btn1" id="check_net_confirm"><a href="javascript:;">好的</a></div>
		<div class="its_close_btn"></div>
    </div>
	<!-- // 电话弹层 -->
	<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
	<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
	<%@ include file="/views/pv.jsp"%>
	<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
</body>
</html>
    