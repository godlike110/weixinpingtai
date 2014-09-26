<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
        <meta content="telephone=no" name="format-detection"/>
        <title>${dg.title }_楼盘导购-搜狐焦点${_city.cityName }房产</title>
        <meta name="Keywords" content="楼盘导购，${_city.cityName }房产，${_city.cityName }楼盘"/>
        <meta name="Description" content="${dg.title }"/>
        <%@ include file="/views/icon.jsp"%>
        <link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css"/>
	    <link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css"/>
	    <link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/news_whj.css">
	    <link href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/header-new.css" rel="stylesheet" />
	    <script src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
	    <script>
			var city = ${city};
		</script>
    </head>
    <body class="wap_daogou_info_all">
        <!-- 新版样式导航 -->
		<jsp:include page="/views/padHeader.jsp">
			<jsp:param value="详情" name="page_title" />
		</jsp:include>
        <section class="detail_news_container">
            <!--文章头-->
            <div class="top_img_container">
                <div class="img_wrapper"><img class="top_img" src="${dg.pic }" alt="${dg.title }" /></div>
                <div class="edit_photo_shadow" ></div>
                <div class="author_container">
                    <div class="author verticle_middle">
                        <img class="author_img" alt="头像" src="${dg.editorPic }" />
                    </div>                    
                    <span class="author_description_text author_position verticle_middle">${dg.editorTitle }</span>
                    <span class="author_description_text verticle_middle">${dg.editorName }</span>
                    <span class="author_description_text verticle_middle author_right">
                        <img class="verticle_middle" style="width: 15px; height: 17.5px;" src="http://192.168.242.44/sceapp/focus_static/wap/pad/img/document_white.png">
                        <span class="verticle_middle">${dg.totalPublish }</span>
                        <img class="verticle_middle" style="width: 22px; height: 13px;" src="http://192.168.242.44/sceapp/focus_static/wap/pad/img/eye_white.png">
                        <span class="verticle_middle">${dg.totalViews }</span>
                    </span>
                </div>
            </div>
            <!--正文-->
            <h1 class="article_title">${dg.title }</h1>
            <div class="article_summary">${dg.onlineTime }</div>
            <article class="article_content">${dg.content }</article>
            <article class="article_content">
            <!--模块开始-->
            <c:forEach items="${dg.contentList }" var="cont" varStatus="status"> 
                <h2>${cont.buildPicName }</h2>
            <img class="article_content_img" alt="${cont.buildPicName }" src="${cont.buildPicBig }" />
            ${cont.content }
            <!--推荐楼盘-->
            <a role="touch_bg" class="suggested_house clearfix" href="${ctx }/${_city.cityPinyinAbbr }/loupan/${cont.groupId}/?fromId=dg_${dg.id}">                
                <span></span>
                <div class="clearfix">
                    <img class="suggested_house_img" src="${cont.buildPic }" alt="${cont.buildPicName }" />
                    <div class="suggest_house_description">
                        <div class="suggested_house_title">${cont.buildPicName }</div>
                        <div class="suggested_house_position">
                            <span>${cont.buildLocation} ${cont.buildAddress }</span>
                            <c:if test="${not empty fn:trim(cont.buildPrice) }">
								<span class="suggested_house_price"><em>${cont.buildPrice }</em></span>
							</c:if>
							<c:if test="${empty fn:trim(cont.buildPrice) }">
							<span class="suggested_house_price"><em>价格待定</em></span>
							</c:if>
                        </div>
                    </div>
                    <div class="fr">
                        <img src="http://192.168.242.44/sceapp/focus_static/wap/pad/img/icon-right.png" alt="" />
                    </div>
                </div>          
            </a>
            <!--电话咨询-->
            <c:choose>
            <c:when test="${not empty fn:trim(cont.phone400) }">
            <a role="touch_bg" href="tel:${cont.phone400 }" data-phone400="${cont.phoneFenji}" data-groupId="${cont.groupId}" class="call">
                <img src="http://192.168.242.44/sceapp/focus_static/wap/pad/img/phone_red.png" alt="" />
                免费电话咨询
            </a>
            </c:when>
            <c:otherwise>
            <a role="touch_bg" href="tel:4008882200" class="call">
                <img src="http://192.168.242.44/sceapp/focus_static/wap/pad/img/phone_red.png" alt="" />
                客服免费电话咨询
            </a>
            </c:otherwise>
            </c:choose>
            </c:forEach>
            <!--模块结束-->
           </article>
            <div class="share_wb">
                分享到：
			    <a href="${sWeibo }" target="_blank" class="sina_wb"></a>
			    <a href="${qZone }" target="_blank" class="qq_zone"></a>
            </div>
            <c:if test="${not empty fn:trim(dg.tab) }">
            <div class="tag">
                <span>标签: </span>
                <span class="gray">${dg.tab }</span>
            </div>
            </c:if>
        </section>
        <section class="crumb">
		    <div class="inner">
			    <a href="${ctx }/${_city.cityPinyinAbbr }/">新房</a>&gt;<a href="${ctx }/${_city.cityPinyinAbbr }/daogou/">楼盘导购</a>&gt;<a href="javascript:;">导购正文</a>
		    </div>
	    </section>
        <footer class="clearfix">
		    <span class="copyright">&copy;2014搜狐焦点</span>
		    <div class="switch_version">
			    <a href="javascript:;" class="version">Pad版</a>
			    <div class="version_list">
				    <a role="touch_bg" href="javascript:;" class="phone">手机版</a>
				    <a role="touch_bg" href="javascript:;" class="ipad current">Pad版</a>
					<a role="touch_bg" href="http://${_city.sourceDomainName }/daogou/${dg.id }.html?cfrom=mobile" class="pc">电脑版</a>
			    </div>
		    </div>
		    <a href="javascript:;" id="to_top">返回顶部</a>
	    </footer>
        <script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.newsdetail.whj.js"></script>
        <script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/sohu.focus.mobile.common.gh_1.0.js"></script>
    <script src="http://192.168.242.44/sceapp/focus_static/wap/pad/snippets/header-new.js"></script>
    <%@ include file="/views/pv.jsp"%>
    </body>
</html>
