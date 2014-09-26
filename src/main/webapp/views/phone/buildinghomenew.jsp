<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>

﻿<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
        <meta content="telephone=no" name="format-detection"/>
        <meta name="apple-mobile-web-app-capable" content="yes" />
        <meta name="apple-mobile-web-app-title" content="搜狐焦点房产" />
        <meta name="Keywords" content="${info.projName }，${_city.cityName }${info.projName }" />
        <meta name="Description" content="搜狐焦点${_city.cityName }${info.projName }楼盘频道为您介绍${info.projName }楼盘最新动态、开盘日期、售楼处销售电话、${info.projName }房价走势、户型图、地址、周边交通等楼盘信息。" />
        <link href="http://192.168.242.44/sceapp/focus_static/wap/mobile/phone/dist/stylesheets/phone_base_all.min.css" rel="stylesheet" />
        <link href="http://192.168.242.44/sceapp/focus_static/wap/mobile/phone/stylesheets/apps/loupan/loupan_detail.css" rel="stylesheet" />
        <title>${info.projName }-楼盘详情-${_city.cityName }搜狐焦点</title>
        
        <script type="text/javascript">
            var city = ${cityStr};
			var groupId = ${groupId};
			var phone400 = ${phone400};
        </script>
    </head>
    <body>
        <!--头部-->
        <header class="public-header">
            <a href="javascript: history.go(-1);" class="public-header-icon public-header-back"></a>
            <h1 class="public-header-title">详情</h1>
            <div class="public-header-icon public-header-menu"></div>
             
			<div class="public-navigator-container clearfix">
            <div class="public-navigator-header">
                <a href="${ctx }/login/" class="public-navigator-header-link">点击登陆</a>
                <a href="/my" class="public-navigator-header-link">个人中心</a>
            </div>
            <a href="${ctx}/${_city.cityPinyinAbbr}/" class="public-navigator-link ">首页</a>
            <a href="${ctx}/${_city.cityPinyinAbbr}/daotou/" class="public-navigator-link ">导购</a>
            <a href="${ctx}/${_city.cityPinyinAbbr}/" class="public-navigator-link ">特卖</a>
            <a href="${ctx}/${_city.cityPinyinAbbr}/sydk/" class="public-navigator-link ">计算器</a>
            <a href="${ctx}/${_city.cityPinyinAbbr}/zixun/" class="public-navigator-link ">新闻</a>
            <a href="${ctx}/${_city.cityPinyinAbbr}/baodian/" class="public-navigator-link ">宝典</a>
            <a href="${ctx}/${_city.cityPinyinAbbr}/pinge" class="public-navigator-link ">装修图</a>
            <a href="${ctx}/${_city.cityPinyinAbbr}/pafangtuan" class="public-navigator-link ">看房团</a>
            <a href="/static/appwap.html" class="public-navigator-link ">app下载</a>
        </div>
            
        </header>
        <!--楼盘模块-->
        <div class="loupan-module">
            <span class="loupan-module-item">楼盘动态</span>
            <span class="loupan-module-item">详细信息</span>
            <span class="loupan-module-item">主力户型</span>
            <span class="loupan-module-item">位置周边</span>
        </div>
        <!--楼盘图片-->
        <section class="loupan-image-container">
            <img src="${info.logUrl}" alt="${info.projName }" />
            <div class="loupan-image-action-bar">
                <a href="javascript:;" class="loupan-image-action-button"><span class="loupan-icons loupan-image-favourite-icon"></span></a>
                <a href="javascript:;" class="loupan-image-action-button"><span class="loupan-icons loupan-image-share-icon"></span></a>
            </div>
            <div class="loupan-image-count">${buildingpiccount}图</div>
        </section>
        <!--楼盘简介-->
        <section class="loupan-description">
            <div class="loupan-description-header">
                <h1 class="loupan-description-name">${info.projName }</h1>
                <span class="loupan-description-type">
                <c:choose>
          <c:when test="${info.saleStatus == 0}">
          在售
          </c:when>
          <c:when test="${info.saleStatus == 1}">
          待售
          </c:when>
          <c:when test="${info.saleStatus == 2}">
          尾盘
          </c:when>
          <c:otherwise>
          售罄
          </c:otherwise>
          </c:choose>
                
                </span>
            </div>
            <dl class="loupan-nowrap">
                <dt class="loupan-description-property">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：</dt>
                <dd class="loupan-description-value"><span class="base-fontcolor">${info.priceForShow}</span></dd>
                <a href="${ctx}/${_city.cityPinyinAbbr}/sydk" class="loupan-right base-fontcolor loupan-description-calculator"><span class="loupan-icons loupan-description-calculator-icon"></span>计算月供</a>
            </dl>
            <dl class="loupan-nowrap">     
                <dt class="loupan-description-property">区&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;域：</dt>
                <dd class="loupan-description-value">${info.districtName}</dd>
            </dl>
            <dl class="loupan-nowrap">
                <dt class="loupan-description-property">开盘时间：</dt>
                <dd class="loupan-description-value">
                    <c:if test="${info.saleDateDetail != '' }">
     					 ${info.saleDateDetail}
      						</c:if>
     			    <c:if test="${info.saleDateDetail == '' }">
      					暂无
      				</c:if>
                </dd>
            </dl>
            <dl class="loupan-nowrap">
                <dt class="loupan-description-property">入住时间：</dt>
                <dd class="loupan-description-value">
       <c:if test="${info.moveInDateDetail != '' }">
      ${info.moveInDateDetail}
      </c:if>
      <c:if test="${info.moveInDateDetail == '' }">
      暂无
      </c:if>
                </dd>
            </dl>
        </section>
        <!--拔打电话-->
        <div class="loupan-phone-top-border"></div>
        <section class="loupan-phone">            
            <div class="loupan-phone-right-45 ">联系售楼处: <span class="base-fontcolor">400-8080-0400转32198</span></div>
            <p class="loupan-phone-description loupan-phone-right-45">开盘排号、购买优惠、政策行情、楼盘信息等可电话咨询</p>  
            <p class="loupan-phone-called-count loupan-phone-right-45"><span class="base-fontcolor">1234</span>人已拨打</p>
            <a href="tel:40080800400,32198" class="loupan-phone-button"><span class="loupan-icons loupan-phone-icon"></span></a>          
        </section>
        <div class="loupan-phone-bottom-border"></div>
        <!--团购-->
        ${tuangou}

        <!--看房团-->
        ${pft}

        <!--楼盘动态-->
${hotnews}

        <!--详细信息-->
        <section class="public-module">
            <div class="public-module-title"><p><span class="base-bgcolor public-title-icon"></span>详细信息</p></div>
            <div class="loupan-detail-box">
                <dl class="loupan-nowrap">
                    <dt class="loupan-description-property">项目类型：</dt>
                    <dd class="loupan-description-value">
                    <c:choose>
					<c:when test="${empty fn:trim(info.projTypeDetail) }">暂无</c:when>
					<c:otherwise>${info.projTypeDetail }</c:otherwise>
				</c:choose>
                    </dd>
                </dl>
                <dl class="loupan-nowrap">     
                    <dt class="loupan-description-property">楼盘位置：</dt>
                    <dd class="loupan-description-value"><c:choose>
					<c:when test="${empty fn:trim(info.projAddress) }">暂无</c:when>
					<c:otherwise>${info.projAddress }</c:otherwise>
				    </c:choose>
				<a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/ditu/" class="base-fontcolor"><span class="loupan-icons loupan-map-icon"></span>查看地图</a></dd>
                </dl>
                <dl class="loupan-nowrap">
                    <dt class="loupan-description-property">开盘时间：</dt>
                    <dd class="loupan-description-value">
                    				<c:choose>
					<c:when test="${empty fn:trim(info.saleDateDetail) }">暂无</c:when>
					<c:otherwise>${info.saleDateDetail }</c:otherwise>
				</c:choose>
                    </dd>
                </dl>
                <dl class="loupan-nowrap">
                    <dt class="loupan-description-property">入住时间：</dt>
                    <dd class="loupan-description-value">
                    <c:choose>
					<c:when test="${empty fn:trim(info.moveInDateDetail) }">暂无</c:when>
					<c:otherwise>${info.moveInDateDetail }</c:otherwise>
				</c:choose>
                    </dd>
                </dl>
                <dl class="loupan-nowrap">
                    <dt class="loupan-description-property">开发商：</dt>
                    <dd class="loupan-description-value">
                    <c:choose>
					<c:when test="${empty fn:trim(info.kfsName) }">暂无</c:when>
					<c:otherwise>${info.kfsName }</c:otherwise>
				</c:choose>
                    </dd>
                </dl>
                <dl class="loupan-nowrap">
                    <dt class="loupan-description-property">土地年限：</dt>
                    <dd class="loupan-description-value">
                    <c:choose>
					<c:when test="${empty fn:trim(info.soilUseYears) }">暂无</c:when>
					<c:otherwise>${info.soilUseYears}</c:otherwise>
				</c:choose>
                    </dd>
                </dl>
            </div>
            <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/xiangxi/" class="public-module-more" data-btn="showMore"><span>点击查看全部</span><span class="public-more-icon1"></span></a>
        </section>

        <!--楼盘户型-->
${layouts}

        <!--置业助手-->


        <!--网友提问-->
${questions}

        <!--位置及周边-->
        <section class="public-module">
            <div class="public-module-title"><p><span class="base-bgcolor public-title-icon"></span>位置及周边</p></div>
            <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/ditu/" class="loupan-block"> 
                <img src="http://restapi.amap.com/gss/simple?sid=3027&maplevel=6&amp;width=300&amp;height=150&amp;ia=1&amp;content=MAP&amp;encode=UTF-8&amp;xys=${info.longitude},${info.latitude}&amp;key=9e4b883b2a6d8482638c56b6f60078b7&iconheight=32&iconwidth=30&icons=http://webapi.amap.com/images/marker_sprite.png" width="100%" alt="${info.projName }" width="100%" alt="西山壹号院">
            </a>
            <a href="${ctx}/${_city.cityPinyinAbbr}/loupan/${info.groupId}/ditu/" class="loupan-news-container loupan-relative">
                <div class="loupan-public-left ">
                    <dl class="loupan-nowrap">
                    <dt class="loupan-position-property">地址：</dt>
                    <dd class="loupan-description-value">最大兴野生动物园以南3公里路西</dd>
                </dl>
                <dl class="loupan-nowrap">     
                    <dt class="loupan-position-property">交通：</dt>
                    <dd class="loupan-description-value">10条地铁，20个公交站点</dd>
                </dl>
                <dl class="loupan-nowrap">
                    <dt class="loupan-position-property">学校：</dt>
                    <dd class="loupan-description-value">4所幼儿园，2所小学，2所中学</dd>
                </dl>
                <dl class="loupan-nowrap">
                    <dt class="loupan-position-property">医院：</dt>
                    <dd class="loupan-description-value">2所医院</dd>
                </dl>
                </div> 
                <span class="loupan-icons loupan-arrow-icon"></span>                 
            </a>
        </section>

        <!--推荐楼盘-->


        <!--浏览历史-->
        <section class="public-module">
            <div class="public-module-title"><p><span class="base-bgcolor public-title-icon"></span>浏览历史</p></div>
            <a href="/bj/loupan" class="loupan-news-container loupan-relative">
                <div class="loupan-history-left text-nowrap">
                    永定河孔雀城
                </div> 
                <span class="base-fontcolor loupan-right loupan-history-price">8500元/平米</span>                  
            </a>
            <a href="/bj/loupan" class="loupan-news-container loupan-relative">
                <div class="loupan-history-left text-nowrap">
                    永定河孔雀城
                </div> 
                <span class="base-fontcolor loupan-right loupan-history-price">8500元/平米</span>                
            </a>
            <a href="/bj/loupan" class="loupan-news-container loupan-relative">
                <div class="loupan-history-left text-nowrap">
                    永定河孔雀城
                </div> 
                <span class="base-fontcolor loupan-right loupan-history-price">8500元/平米</span>                
            </a>
        </section>

        <!--电话-->

		<c:if test="${info.phone400 != 0  && empty special}">
			<div class="loupan-hotline-container">
				<div><a href="tel:4008882200,${info.phone400}" class="loupan-hotline-link" phone="${info.phone400 }" data_href="tel:4008882200,${info.phone400}"><span class="loupan-icons loupan-hotline-icon"></span>联系售楼处 400-888-2200转${info.phone400}</a></div>
				<div><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${info.groupId}/woyaozixun" class="footer_ban_download"><b></b>在线咨询</a></div>
			</div>
		</c:if>
		<c:if test="${info.phone400 != 0  && not empty special}">
			<div class="loupan-hotline-container">
				<div><a href="tel:4001080000,${info.phone400}" class="loupan-hotline-link" phone="${info.phone400 }" data_href="tel:4001080000,${info.phone400}"><span class="loupan-icons loupan-hotline-icon"></span>联系售楼处 400-888-2200转${info.phone400}</a></div>
				<div><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${info.groupId}/woyaozixun" class="footer_ban_download"><b></b>在线咨询</a></div>
			</div>
		</c:if>
		<c:if test="${info.phone400==0 }">
			<div class="loupan-hotline-container">
				<div><a href="tel:4006782020" class="loupan-hotline-link" phone="" data_href="tel:4006782020"><span class="loupan-icons loupan-hotline-icon"></span>联系售楼处 400-888-2200转${info.phone400}</a></div>
				<div><a href="${ctx }/${_city.cityPinyinAbbr }/loupan/${info.groupId}/woyaozixun" class="footer_ban_download"><b></b>在线咨询</a></div>
			</div>
		</c:if>
        

        <!--面包屑-->
        <section class="public-bread">
            <a class="public-bread-link" href="${ctx}/${_city.cityPinyinAbbr}/">新房</a>><a class="public-bread-link" href="${ctx}/${_city.cityPinyinAbbr}/loupan">${_city.cityName }楼盘</a>><span class="public-bread-current">楼盘详情</span>
        </section>

        <!--底部-->
        <footer>
            <div class="footer-nav">
                <!-- <div class="footer-link">友链<span class="icon"></span></div> -->
                <div class="footer-os">
                    <a class="item active">手机版</a>
                    <a class="item" href="http://house.focus.cn?cfrom=mobile">电脑版</a>
                </div>
            </div>
            <div class="footer-company">©2014 focus.cn</div>
        </footer>

		<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/mobile/phone/dist/scripts/phone_base_all.min.js"></script>
        <script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/mobile/phone/scripts/apps/loupan/loupan_detail.js"></script>
        <script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/loupan/js/modernizr.custom.min.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/loupan/js/classie.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/loupan/js/modalEffects.js"></script>
<script type="text/javascript"  src="http://webapi.amap.com/maps?v=1.2&key=9e4b883b2a6d8482638c56b6f60078b7"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/loupan/js/buildinghome.map.js"></script>
<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/loupan/js/wap_phone.js"></script>
<script src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/header-new.js"></script>
<%@ include file="/views/pv.jsp"%>
<c:if test="${wap_cuhcn}"><script src="http://iphone.wo.com.cn/top/bottom.js"></script></c:if>
        
    </body>
</html>