<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
		<meta content="telephone=no" name="format-detection" />
		<title>${detail.projName }详细信息-${_city.cityName }${detail.projName }详细信息-搜狐焦点</title>
		<meta name="Keywords" content="${detail.projName }详细信息-${_city.cityName }${detail.projName }详细信息-搜狐焦点">
		<meta name="Description" content="搜狐焦点${_city.cityName }房产网为广大网友提供了最新的${_city.cityName }楼盘信息，最准确的${_city.cityName }房价情况和最及时的${_city.cityName }新房资讯等，是买房、购房首选网站。  ">
		<%@ include file="/views/icon.jsp" %>
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
		<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
		<link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
		<script type="text/javascript">
		var city = ${cityStr};
		var groupId = ${groupId}; 
		var phone400 = ${phone400};
		</script>
	</head>
	
	<body class="bg_all layout_fixed">
		<section class="container">
	  	<!-- 新版样式导航 -->
		<jsp:include page="/views/padHeader.jsp">
			<jsp:param value="详情" name="page_title" />
		</jsp:include>
			
			<section class="content clearfix">
				<article class="art_text information_mod">
					<h3>${detail.projName }</h3>
					<div class="line"></div>
					<ul class="article_list">
						<li>
							<dl>
								<dt>所属片区：</dt>
								<dd>
								    <c:choose>
                    					<c:when test="${empty fn:trim(detail.districtName) }">暂无</c:when>
                    					<c:otherwise>${detail.districtName }</c:otherwise>
                    				</c:choose>
                    			</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>参考价格：</dt>
								<dd>
								    <c:choose>
        								<c:when test="${fn:trim(detail.avgPrice) || detail.avgPrice == '0'}"><em>价格待定</em></c:when>
        								<c:otherwise><em>${detail.avgPrice }</em>元/平米</c:otherwise>
        							</c:choose>
							    </dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>楼盘类型：</dt>
								<dd>
								    <c:choose>
                					<c:when test="${empty fn:trim(detail.projTypeDetail) }">暂无</c:when>
                					<c:otherwise>${detail.projTypeDetail }</c:otherwise>
                					</c:choose>
            					</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>楼盘位置：</dt>
								<dd>
								    <c:choose>
                					<c:when test="${empty fn:trim(detail.projAddress) }">暂无</c:when>
                					<c:otherwise>${detail.projAddress }</c:otherwise>
                				</c:choose>
            				</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>开盘时间：</dt>
								<dd>
								    <c:choose>
                    					<c:when test="${empty fn:trim(detail.saleDateDetail) }">暂无</c:when>
                    					<c:otherwise>${detail.saleDateDetail }</c:otherwise>
                    				</c:choose>
                    			</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>入住时间：</dt>
								<dd>
								    <c:choose>
                    					<c:when test="${empty fn:trim(detail.moveInDateDetail) }">暂无</c:when>
                    					<c:otherwise>${detail.moveInDateDetail }</c:otherwise>
                    				</c:choose>
                    			</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>装修情况：</dt><dd>									<c:choose>
					<c:when test="${empty fn:trim(detail.decoLevelDetail) }">暂无</c:when>
					<c:otherwise>${detail.decoLevelDetail }</c:otherwise>
				</c:choose></dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>建筑类型：</dt>
								<dd>
								    <c:choose>
                    					<c:when test="${empty fn:trim(detail.constructTypeDetail) }">暂无</c:when>
                    					<c:otherwise>${detail.constructTypeDetail }</c:otherwise>
                    				</c:choose>
                    			</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>售楼地址：</dt>
								<dd>
								    <c:choose>
                    					<c:when test="${empty fn:trim(detail.saleLocate) }">暂无</c:when>
                    					<c:otherwise>${detail.saleLocate }</c:otherwise>
                    				</c:choose>
                    			</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>开发商：</dt>
								<dd>
								    <c:choose>
                    					<c:when test="${empty fn:trim(detail.kfsName) }">暂无</c:when>
                    					<c:otherwise>${detail.kfsName }</c:otherwise>
                    				</c:choose>
                    			</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>物业公司：</dt>
								<dd>
								    <c:choose>
                    					<c:when test="${empty fn:trim(detail.operationCorp) }">暂无</c:when>
                    					<c:otherwise>${detail.operationCorp }</c:otherwise>
                    				</c:choose>
                    			</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>物业费：</dt>
								<dd>
								    <c:choose>
                    					<c:when test="${empty fn:trim(detail.operationFee) }">暂无</c:when>
                    					<c:otherwise>${detail.operationFee }</c:otherwise>
                    				</c:choose>
            				    </dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>建筑面积：</dt>
								<dd>
								    <c:choose>
                    					<c:when test="${empty fn:trim(detail.constructArea) }">暂无</c:when>
                    					<c:otherwise>${detail.constructArea }</c:otherwise>
                    				</c:choose>
                    			</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>占地面积：</dt>
								<dd>
								    <c:choose>
                    					<c:when test="${empty fn:trim(detail.landArea) }">暂无</c:when>
                    					<c:otherwise>${detail.landArea }</c:otherwise>
                    				</c:choose>
                    			</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>绿化率：</dt>
								<dd>
								    <c:choose>
                    					<c:when test="${empty fn:trim(detail.greenRatio) }">暂无</c:when>
                    					<c:otherwise>${detail.greenRatio }</c:otherwise>
                    				</c:choose>
                    			</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>容积率：</dt>
								<dd>
								    <c:choose>
                    					<c:when test="${empty fn:trim(detail.constructRatio) }">暂无</c:when>
                    					<c:otherwise>${detail.constructRatio }</c:otherwise>
                    				</c:choose>
                    			</dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>土地年限：</dt>
								<dd>
								    <c:choose>
                    					<c:when test="${empty fn:trim(detail.soilUseYears) }">暂无</c:when>
                    					<c:otherwise>${detail.soilUseYears}</c:otherwise>
                    				</c:choose>
                    			</dd>
							</dl>
						</li>
					</ul>
				</article>
			</section>

			<section class="crumb">
				<div class="inner">
					<a href="${ctx}/${_city.cityPinyinAbbr}/">新房</a>&gt;<a href="${ctx}/${_city.cityPinyinAbbr}/loupan/"> ${_city.cityName }楼盘</a>&gt;<a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${detail.groupId}/">${buildName}</a>&gt;<span> 详细信息</span>
				</div>
			</section>
 <c:if test="${detail.phone400!=0 }">
			<footer class="position_fixed">
				<a href="tel:4008882200,${detail.phone400 }" class="free_advice"><b></b>免费咨询 400-888-2200 转 ${detail.phone400 }</a>
			</footer>
			</c:if>
			 <c:if test="${detail.phone400==0 }">
			<footer class="position_fixed">
				<a href="tel:4006782020" class="free_advice"><b></b>免费咨询 400-678-2020</a>
			</footer>
			</c:if>
			
		</section>
		<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
		<script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
		<%@ include file="/views/pv.jsp"%>
	</body>
</html>