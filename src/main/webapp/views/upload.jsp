<%@ page contentType="text/html;charset=utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>wap站上传</title>

<style>
.submit {
	height: 30px;
	color: blue;
	size: 10px;
	margin-left: 100px;
}
.input {
height: 25px;
color: blue;
}
</style>

</head>
<body>
	<br />
	<br>
	<font color=blue size=4>wap站自定义页面上传，规则如下：</font>
	<br />
	<br />
	<font color=red size=3>1,上传页面必须为html格式页面。</font>
	<br />
	<br />
	<font color=red size=3>2,自定义url
		，比如上传自住房页面，我们希望访问url为：m.focus.cn/zzf.html,在这个输入框中输入"/"(默认已经输入),比如希望访问的
		url为m.focus.cn/zzf/zzf.html 在输入框输入"/zzf/",以此类推。 </font>
	<br />
	<br />
	<br />
	<form action="/upload/doUpload" method="post" enctype="multipart/form-data">
		<br /> <font color=black size=3>请选择上传页面:  &nbsp&nbsp&nbsp</font><input type="file" name="file" /><br />
		<br /> <font color=black size=3>请制定访问url:  &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</font><input class="input" type="text" name="path" value="/" /><br />
		<input type=hidden value="${env}" name="env">
		<br /> <input class="submit" type="submit" value="提交">
	</form>
</body>
</html>
