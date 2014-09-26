<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>
<%@page import="cn.focus.dc.focuswap.service.SearchService.SearchType"%>
<c:set var="DISTRICT" value="<%= SearchType.DISTRICT%>"></c:set>
<c:set var="HOT" value="<%= SearchType.HOT%>"></c:set>
<c:set var="PRICE" value="<%= SearchType.PRICE%>"></c:set>
<c:set var="SUBWAY" value="<%= SearchType.SUBWAY%>"></c:set>
<c:set var="SPECIAL" value="<%= SearchType.SPECIAL%>"></c:set>
<c:set var="TYPE" value="<%= SearchType.TYPE%>"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
<meta content="telephone=no" name="format-detection" />
<%@ include file="/views/icon.jsp"%>
<title>${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }楼盘_${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }新楼盘_${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }房价-${_city.cityName }搜狐焦点网</title>
<meta name="Keywords" content="${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }楼盘，${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }新楼盘，${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }房价" />
<meta name="Description" content="${_city.cityName }搜狐焦点网楼盘搜索频道为您提供${_city.cityName }${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName}楼盘查询、${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName}新楼盘查询、${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName }房价，让您快速方便的找房，更多${_city.cityName }${showChosenConMap[DISTRICT].condName }${showChosenConMap[HOT].condName}楼盘信息，尽在${_city.cityName }搜狐焦点网。"/>
<%@ include file="/views/icon.jsp"%>
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css" />
<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/phone/css/wap_public_v1.0.css" />
<link href="http://192.168.242.44/sceapp/focus_static/wap/sohunews/css/filterloupan.css" rel="stylesheet" />
<script>
    //var city = { "ajaxUrl": "/bj/loupan/filterajax/k5______", "cityCoding": "010", "cityId": 1, "cityName": "北京", "cityPinyin": "beijing", "cityPinyinAbbr": "bj", "domainName": "house.focus.cn/?cfrom=mobile", "priority": 1, "sourceDomainName": "house.focus.cn", "type": "1" };
    //ajaxurl:sohunews/loupan/filterAjax?lpChoose=?&cityName=?&pageNo=?&pageSize=?
    var city = ${city};
    var hasNext = ${hasNext};
    var lpChoose = "${lpChoose}";
</script>
</head>
<body>
	<div class="header clearfix">
        <h1 class="title">楼盘</h1>
        <a href="${ctx }/sohunews/citySelect/?cityName=${_city.cityPinyinAbbr}&from=loupan" data-touched="true" class="fr" id="selectCity">
            <span class="city">${_city.cityName }</span>
            <img src="http://192.168.242.44/sceapp/focus_static/wap/phone/img/location.png" class="location_icon" alt="loaction" />
        </a>
        <span class="arrow"></span>
	</div>    
    <section class="section_content">        
        <div class="select_wrapper clearfix">
            <!--筛选条件第一排-->
            <c:if test="${ fn:length(conditions[DISTRICT]) > 0 }">
            <div class="select_container"><div class="select_item" id="area" data-touched="true">
            <span class="select_text"><c:if test="${ not empty showChosenConMap[DISTRICT] }">${showChosenConMap[DISTRICT].condName }</c:if><c:if test="${ empty showChosenConMap[DISTRICT] }">区域</c:if></span><span class="select_item_icon"></span><span class="select_arrow"></span></div></div>            
            </c:if>
            <c:if test="${fn:length(conditions[HOT]) > 0  }">
            <div class="select_container"><div class="select_item" id="hotForum" data-touched="true">
            <span class="select_text"><c:if test="${ not empty showChosenConMap[HOT] }">${showChosenConMap[HOT].condName }</c:if><c:if test="${ empty showChosenConMap[HOT] }">热点板块</c:if></span><span class="select_item_icon"></span><span class="select_arrow"></span></div></div>
            </c:if>
            <c:if test="${fn:length(conditions[PRICE]) > 0  }">
            <div class="select_container"><div class="select_item" id="price" data-touched="true">
            <span class="select_text"><c:if test="${ not empty showChosenConMap[PRICE] }">${showChosenConMap[PRICE].condName }</c:if><c:if test="${ empty showChosenConMap[PRICE] }">价格</c:if></span><span class="select_item_icon"></span><span class="select_arrow"></span></div></div>
            </c:if>
            <!--区域下拉菜单-->
            <c:if test="${ fn:length(conditions[DISTRICT]) > 0 }">
            <div id="area_menu" class="select_content clearfix">
              <c:forEach items="${conditions[DISTRICT] }" var="districts" varStatus="status">
            	 <c:choose>
            	 <c:when test="${not empty fn:trim(districts.select) &&  districts.select == 'selected' }">
            	 <div class="select_content_button_container"><a class="current_select_content_button select_content_button" href = "${districts.linkUrl}">${districts.condName }</a></div>
            	 </c:when>
            	 <c:when test="${ empty showChosenConMap[DISTRICT] && districts.condName == '不限' }">
            	 <div class="select_content_button_container"><a class="current_select_content_button select_content_button" href = "${districts.linkUrl}">${districts.condName }</a></div>
            	 </c:when>
            	 <c:otherwise>
            	 <div class="select_content_button_container"><a class="select_content_button" href = "${districts.linkUrl}">${districts.condName }</a></div>
            	 </c:otherwise>
            	 </c:choose>
              </c:forEach>
            </div>
            </c:if>
            <!--区域下拉菜单-->
            <!--热点版块-->
            <c:if test="${fn:length(conditions[HOT]) > 0  }">
            <div id="hotForum_menu" class="select_content clearfix">
            <c:forEach items="${conditions[HOT] }" var="hots">
            <c:choose>
            	 <c:when test="${not empty fn:trim(hots.select) &&  hots.select == 'selected' }">
            	 <div class="select_content_button_container"><a class="current_select_content_button select_content_button" href = "${hots.linkUrl}">${hots.condName }</a></div>
            	 </c:when>
            	 <c:when test="${ empty showChosenConMap[HOT] && hots.condName == '不限' }">
            	 <div class="select_content_button_container"><a class="current_select_content_button select_content_button" href = "${hots.linkUrl}">${hots.condName }</a></div>
            	 </c:when>
            	 <c:otherwise>
            	 <div class="select_content_button_container"><a class="select_content_button" href = "${hots.linkUrl}">${hots.condName }</a></div>
            	 </c:otherwise>
            	 </c:choose>
            </c:forEach>
            </div>
            </c:if>
            <!--热点版块-->
            <!--价格-->
            <c:if test="${fn:length(conditions[PRICE]) > 0 }">
            <div id="price_menu" class="select_content clearfix">
            <c:forEach items="${conditions[PRICE] }" var="prices">
            <c:choose>
            	 <c:when test="${not empty fn:trim(prices.select) &&  prices.select == 'selected' }">
            	 <div class="select_content_button_container"><a class="current_select_content_button select_content_button" href = "${prices.linkUrl}">${prices.condName }</a></div>
            	 </c:when>
            	 <c:when test="${empty showChosenConMap[PRICE] && prices.condName == '不限' }">
            	 <div class="select_content_button_container"><a class="current_select_content_button select_content_button" href = "${prices.linkUrl}">${prices.condName }</a></div>
            	 </c:when>
            	 <c:otherwise>
            	 <div class="select_content_button_container"><a class="select_content_button" href = "${prices.linkUrl}">${prices.condName }</a></div>
            	 </c:otherwise>
            	 </c:choose>
            </c:forEach>
            </div>
            </c:if>
            <!--价格-->
            <c:if test="${fn:length(conditions[TYPE]) > 0 }">
            <div class="select_container"><div id="type" class="select_item" data-touched="true">
            <span class="select_text"><c:if test="${ not empty showChosenConMap[TYPE] }">${showChosenConMap[TYPE].condName }</c:if><c:if test="${ empty showChosenConMap[TYPE] }">类型</c:if></span><span class="select_item_icon"></span><span class="select_arrow"></span></div></div>
            </c:if>
            <c:if test="${fn:length(conditions[SPECIAL]) > 0 }">
            <div class="select_container"><div id="feature" class="select_item" data-touched="true">
            <span class="select_text"><c:if test="${ not empty showChosenConMap[SPECIAL] }">${showChosenConMap[SPECIAL].condName }</c:if><c:if test="${ empty showChosenConMap[SPECIAL] }">特色</c:if></span><span class="select_item_icon"></span><span class="select_arrow"></span></div></div>
            </c:if>
            <c:if test="${fn:length(conditions[SUBWAY]) > 0 }">
            <div class="select_container"><div id="traffic" class="select_item" data-touched="true">
            <span class="select_text"><c:if test="${ not empty showChosenConMap[SUBWAY] }">${showChosenConMap[SUBWAY].condName }</c:if><c:if test="${ empty showChosenConMap[SUBWAY] }">轨道交通</c:if></span><span class="select_item_icon"></span><span class="select_arrow"></span></div></div>
           	</c:if>
            <!--类型-->
            <c:if test="${fn:length(conditions[TYPE]) > 0 }">
            <div id="type_menu" class="select_content clearfix">
            <c:forEach items="${conditions[TYPE] }" var="types">
            <c:choose>
            	 <c:when test="${not empty fn:trim(types.select) &&  types.select == 'selected' }">
            	 <div class="select_content_button_container"><a class="current_select_content_button select_content_button" href = "${types.linkUrl}">${types.condName }</a></div>
            	 </c:when>
            	 <c:when test="${empty showChosenConMap[TYPE] && types.condName == '不限' }">
            	 <div class="select_content_button_container"><a class="current_select_content_button select_content_button" href = "${types.linkUrl}">${types.condName }</a></div>
            	 </c:when>
            	 <c:otherwise>
            	 <div class="select_content_button_container"><a class="select_content_button" href = "${types.linkUrl}">${types.condName }</a></div>
            	 </c:otherwise>
            	 </c:choose>
            </c:forEach>
            </div>
            </c:if>
            <!--类型-->
            <!--特色-->
            <c:if test="${fn:length(conditions[SPECIAL]) > 0 }">
            <div id="feature_menu" class="select_content clearfix">
            <c:forEach items="${conditions[SPECIAL] }" var="specials">
            <c:choose>
            	 <c:when test="${not empty fn:trim(specials.select) &&  specials.select == 'selected' }">
            	 <div class="select_content_button_container"><a class="current_select_content_button select_content_button" href = "${specials.linkUrl}">${specials.condName }</a></div>
            	 </c:when>
            	 <c:when test="${ empty showChosenConMap[SPECIAL] && specials.condName == '不限' }">
            	 <div class="select_content_button_container"><a class="current_select_content_button select_content_button" href = "${specials.linkUrl}">${specials.condName }</a></div>
            	 </c:when>
            	 <c:otherwise>
            	 <div class="select_content_button_container"><a class="select_content_button" href = "${specials.linkUrl}">${specials.condName }</a></div>
            	 </c:otherwise>
            	 </c:choose>
            </c:forEach>
            </div>
            </c:if>
            <!--特色-->
            <!--轨道交通-->
            <c:if test="${fn:length(conditions[SUBWAY]) > 0 }">
            <div id="traffic_menu" class="select_content clearfix">
            <c:forEach items="${conditions[SUBWAY] }" var="subways">
            <c:choose>
            	 <c:when test="${not empty fn:trim(subways.select) &&  subways.select == 'selected' }">
            	 <div class="select_content_button_container"><a class="current_select_content_button select_content_button" href = "${subways.linkUrl}">${subways.condName }</a></div>
            	 </c:when>
            	 <c:when test="${ empty showChosenConMap[SUBWAY] && subways.condName == '不限' }">
            	 <div class="select_content_button_container"><a class="current_select_content_button select_content_button" href = "${subways.linkUrl}">${subways.condName }</a></div>
            	 </c:when>
            	 <c:otherwise>
            	 <div class="select_content_button_container"><a class="select_content_button" href = "${subways.linkUrl}">${subways.condName }</a></div>
            	 </c:otherwise>
            	 </c:choose>
            </c:forEach>
            </div>
            </c:if>
            <c:if test="${ fn:length(showChosenConMap) > 0 }">
            <!--轨道交通-->
            <a href="${ctx }/sohunews/loupan/?cityName=${_city.cityPinyinAbbr}" id="clearCondition" class="select_item clearCondition" data-touched="true"><img class="delete_icon" onerror="this.parentNode.removeChild(this)" src="http://192.168.242.44/sceapp/focus_static/wap/phone/img/delete.png" alt="">清空条件</a>
            </c:if>
            <c:if test="${ fn:length(showChosenConMap) <= 0 }">
            <!--轨道交通-->
            <a href="javascript:;" id="clearCondition" class="select_item clearCondition"><img class="delete_icon" onerror="this.parentNode.removeChild(this)" src="http://192.168.242.44/sceapp/focus_static/wap/phone/img/delete.png" alt="">清空条件</a>
            </c:if>
        </div>

		<c:if test="${total > 0 }">
        <div class="loupan_wrapper">
            <h2 class="loupan_count">共${total }个楼盘</h2>
            <ul class="loupan_container">
            <c:if test="${not empty espjs && fn:length(espjs) > 0 }">
             <c:forEach items="${espjs }" var="espj" varStatus="status">
             <li data-touched="true">
                    <a href="${espj.buildingUrl }" class="loupan_link clearfix">
                        <div class="loupan_img_container fl"><img src="${espj.url }" alt="${espj.projName }" /></div>
                        <dl>
                            <dt class="loupan_title"><span class="title_text">${espj.projName }</span>
                            <c:choose>
										   <c:when test="${espj.saleStatus == 0}">
												 <span class="status sale">在售</span>
										   </c:when>
										   <c:when test="${espj.saleStatus == 1}">
												  <span class="status wait">待售</span>
										   </c:when>
										   <c:when test="${espj.saleStatus == 2}">
												  <span class="status remain">尾盘</span>
										   </c:when>
										   <c:otherwise>
												 <span class="status over">售罄</span>
										   </c:otherwise>
								</c:choose></dt>
							<dd class="loupan_description">${espj.projAddress }</dd>
                            <dd class="price">${espj.avgPriceShow }</dd>
                            <c:if test = "${not empty fn:trim(espj.discount) }">
                            <dd><span class="loupan_discount_text loupan_discount">${espj.discount }</span></dd>
                            </c:if>
						</dl>
                    </a>
                </li>
             </c:forEach>
            </c:if>
            </ul>
            <div class="back2top" data-title="返回顶部"></div>
            <!-- pull up -->
			<div class="pull font_gray">
				<div class="icon fl"></div>
				<p>滑动加载更多</p>
			</div>
        </div>
        </c:if>
        <c:if test="${total <= 0 }">
        <div class="no_result"><img class="no_result_img" src="http://192.168.242.44/sceapp/focus_static/wap/phone/img/filter_no_result1.png" alt="" />暂无符合条件的楼盘，试试其他条件吧</div>
        </c:if>
    </section>
    <script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/sohunews/snippets/common.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/sohunews/snippets/filterloupan.js"></script>
    <%@ include file="/views/pv.jsp"%>
</body>
</html>