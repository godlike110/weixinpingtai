<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>wap站上传结果页</title>

</head>
<body>
<c:if test="${not empty url }">
<font color=blue size=5>上传成功，点击以下链接进行访问：</font>
<font color=gray size=5><a href="${ctx}${url}">访问入口>></a></font>

</c:if>
<c:if test="${empty url }">
    <font color=blue size=5>上传失败 请联系网站后台</font>
</c:if>

</body>
</html>
