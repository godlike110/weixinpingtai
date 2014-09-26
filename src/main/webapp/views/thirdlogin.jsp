<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="/common/taglibs.jsp"%>

<div class="login_other_method">
    <div class="login_groupBtn">
        <a href="http://passport.sohu.com/openlogin/request.action?provider=sina&appid=1028&ru=${fromSina}&hun=0">新浪微博登录</a>
        <a href="http://passport.sohu.com/openlogin/request.action?provider=qq&appid=1028&ru=${fromQQ}&hun=0">腾讯QQ登录</a>
        <a class="close">取消</a>
    </div>
</div>