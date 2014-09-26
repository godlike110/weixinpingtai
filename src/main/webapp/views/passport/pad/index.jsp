<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
	<meta content="telephone=no" name="format-detection" />
	<%@ include file="/views/icon.jsp"%>
	<title>【北京楼盘|北京房价|北京新房】-搜狐焦点北京房产</title>
	<meta name="Keywords" content="北京楼盘, 北京房价, 北京新房">
	<meta name="Description" content="搜狐焦点北京房产网为广大网友提供了最新的北京楼盘信息，最准确的北京房价情况和最及时的北京新房资讯等，是买房、购房首选网站。 ">
	<link rel="stylesheet" type="text/css" href="http://192.168.242.44/sceapp/focus_static/wap/common/css/css_mobile_common_v1.1.css">
	<link rel="stylesheet" href="http://192.168.242.44/sceapp/focus_static/wap/pad/css/index.css" />
	<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/common/lib/zepto-1.1.3.min.js"></script>
	<script type="text/javascript">
	     var uuid = '${uuid}';
	</script>
</head>
<body>
	<c:if test="${empty uid}">
	<span class="red" id="please_login1" style="display:inline-block;margin:30px 0px 0px 30px;"><a align=center><font size=4>用户未登入，点击登入>></font></a></span>
	</c:if>
	<c:if test="${not empty uid}">
    <font color=blue>欢迎你：</font><font color=green>${uid}</font>
	<a href="javascript:;" id="user_quit1">退出登陆</a>
	</c:if>
	
	<div class="over"></div>
	<!-- 登录弹层 // -->
	<div class="login_container" id="login_container1">
     	<h2 class="login_container_tit"><span class="login_container_close fr"></span>登录</h2>
     	<div class="login_container_nr">
			<div class="login_container_nr_p">
				<input class="login_input1" id="login_user_input1" role-need="tip_status" type="text" value="" placeholder="账号/手机号" />
				<div class="input_shadow1"></div>
				<span class="clear_record1"></span>
			</div>
			<div class="login_container_nr_p">
				<input class="login_input1" id="login_password_input1" type="password" value="" placeholder="密码" />
				<div class="input_shadow1"></div>
				<span class="clear_record1"></span>
			</div>
			<div class="login_container_yanzhengma clearfix">
				<div class="fl"><img class="login_container_yanzhengma_img" src="/user/capture/get?uuid=${uuid}&time=1396345405630" alt="验证码" style="vertical-align:middle" width="150" height="37" /></div>
				<div class="login_container_yanzhengma_nr">
					<input class="login_input1" id="login_yanzhengma_input1" type="text" value="" placeholder="请输入4位验证码" />
					<div class="input_shadow1"></div>
					<span class="clear_record1"></span>
				</div>
			</div>
			<div class="login_container_nr_p2 tr">
				<div class="login_tip" id="login_tip1" style="display:none;"></div>
				<span id="login_account1">注册账号</span><span class="h_space1">|</span><a href="http://m.passport.sohu.com/f" target="_blank">忘记密码</a>
			</div>
			<div class="login_btn1" id="login_submit_btn1" role-need="going_login"><span class="normal">登录</span><span class="its_going">登录中…</span></div>
			<div class="login_user_box clearfix">
				<a href="http://passport.sohu.com/openlogin/request.action?appid=1028&provider=qq&ru=http://test.m.focus.cn/user/lrcenter&type=wap" class="qq_login fr"><em></em>QQ账号登录</a>
				<a href="http://passport.sohu.com/openlogin/request.action?appid=1028&provider=sina&ru=http://test.m.focus.cn/user/lrcenter&type=wap" class="sina_wb_login"><em></em>新浪微博账号登录</a>
			</div>
		</div>
    </div>
	<!-- // 登录弹层 -->
	
	<!-- 登录成功弹层 -->
	<div class="register_success_box" id="login_success_box1">
		<span class="register_success_btn">登录成功！</span>
	</div>
	<!-- // 登录成功弹层 -->
	
	<!-- 登录失败弹层 -->
	<div class="register_success_box" id="login_failed_box1">
		<span class="register_failed_btn">登录失败，<br />请稍候重试！</span>
	</div>
	<!-- // 登录失败弹层 -->
	
	<!-- 注册弹层 // -->
	<div class="login_container" id="register_container1">
     	<h2 class="login_container_tit"><span class="login_container_close fr"></span>注册</h2>
     	<div class="register_container_tab clearfix"><span class="fr use_email_register">使用邮箱注册</span><span class="fr use_phone_register hide">使用手机注册</span><span class="current_status current">手机注册</span></div>
		
		<div class="login_container_nr phone_register_box" title="手机注册">
			<div class="login_container_nr_p">
				<input class="login_input1" id="register_phone_user1" type="tel" value="" placeholder="手机号" />
				<div class="input_shadow1"></div>
				<span class="clear_record1"></span>
			</div>
			<div class="login_container_nr_p">
				<div class="fr register_tab_passpord">显示</div>
				<div class="login_container_passpord_nr">
					<input class="login_input1" id="register_phone_password1" type="password" value="" placeholder="密码" />
					<div class="input_shadow1"></div>
					<span class="clear_record1"></span>
				</div>
			</div>
			<div class="login_container_nr_p2 tr" style="display:none;">
				<div class="login_tip"></div>
			</div>
			<div class="login_btn1" id="phone_register_btn1"><span class="normal">注册</span><span class="its_going">注册中…</span></div>
			<div class="goto_login_box clearfix">
				<label><input class="agree_sohu_input agree_sohu_input_ok" need-add="agree_sohu_input_ok" id="register_phone_agree1" type="checkbox" checked="checked" /><a href="http://m.passport.sohu.com/agreement" target="_blank">同意搜狐服务协议</a></label>
				<span class="goto_login fr" id="goto_login1">登录<em></em></span>
			</div>
		</div>
		
		<div class="login_container_nr email_register_box hide" title="邮箱注册">
			<div class="login_container_nr_p">
				<input class="login_input1" id="register_email_user1" type="text" value="" placeholder="邮箱" />
				<div class="input_shadow1"></div>
				<span class="clear_record1"></span>
				<ul class="email_register_tishi"></ul>
			</div>
			<div class="login_container_nr_p">
				<div class="fr register_tab_passpord">显示</div>
				<div class="login_container_passpord_nr">
					<input class="login_input1" id="register_email_password1" type="password" value="" placeholder="密码" />
					<div class="input_shadow1"></div>
					<span class="clear_record1"></span>
				</div>
			</div>
			<div class="login_container_yanzhengma clearfix" style="display:block;">
				<div class="fl"><img class="login_container_yanzhengma_img" src="/user/capture/get?uuid=${uuid}&time=1396345405630" alt="验证码" style="vertical-align:middle" width="150" height="37" /></div>
				<div class="login_container_yanzhengma_nr">
					<input class="login_input1" id="register_yanzhengma_input1" type="text" value="" placeholder="请输入4位验证码" />
				</div>
			</div>
			<div class="login_container_nr_p2 tr" style="display:none;">
				<div class="login_tip"></div>
			</div>
			<div class="login_btn1" id="email_register_btn1"><span class="normal">注册</span><span class="its_going">注册中…</span></div>
			<div class="goto_login_box clearfix">
				<label><input class="agree_sohu_input agree_sohu_input_ok" id="register_email_agree1" type="checkbox" checked="checked" /><a href="http://m.passport.sohu.com/agreement" target="_blank">同意搜狐服务协议</a></label>
				<span class="goto_login fr">登录<em></em></span>
			</div>
		</div>
		
    </div>
	<!-- // 注册弹层 -->
	
	<!-- 手机短信验证码 -->
	<div class="login_container" id="phone_yanzheng_container1">
     	<h2 class="login_container_tit"><span class="login_container_close fr"></span>注册</h2>
		<div class="login_container_nr">
			<div class="register_container_nr_p1">
				请输入手机号<span id="phone_yanzheng_number1"></span><br/>收到的短信验证码
			</div>
			<div class="login_container_yanzhengma clearfix" style="display:block;">
				<div class="fr yanzhengma_tips" style="display:block;"><span class="red" id="phone_yanzhengma_tips1">60</span>秒后再次获取</div>
				<div class="fr yanzhengma_tips2 red" style="display:none;" id="get_phone_yanzhengma1">获取激活码</div>
				<div class="register_yanzhengma_nr">
					<input class="login_input1" type="number" value="" placeholder="输入短信激活码" />
				</div>
			</div>
			<div class="login_container_nr_p2 tr" style="display:none;">
				<div class="login_tip"></div>
			</div>
			<div class="login_btn1" id="phone_yanzheng_btn1"><span class="normal">提交</span><span class="its_going">提交中…</span></div>
		</div>
    </div>
	<!-- // 手机短信验证码 -->
	
	<!-- 邮箱验证码 -->
	<div class="login_container" id="email_yanzheng_container1">
     	<h2 class="login_container_tit"><span class="login_container_close fr"></span>注册</h2>
		<div class="login_container_nr">
			<div class="register_container_nr_p1">
				请输入激活码<br/>激活码已发送到您的邮箱！
			</div>
			<div class="login_container_yanzhengma clearfix" style="display:block;">
				<div class="fr yanzhengma_tips" style="display:block;"><span class="red" id="email_yanzhengma_tips1">60</span>秒后再次获取</div>
				<div class="fr yanzhengma_tips2 red" style="display:none;" id="get_email_yanzhengma1">获取激活码</div>
				<div class="register_yanzhengma_nr">
					<input class="login_input1" type="text" value="" placeholder="输入邮箱激活码" />
				</div>
			</div>
			<div class="login_container_nr_p2 tr" style="display:none;">
				<div class="login_tip"></div>
			</div>
			<div class="login_btn1" id="email_yanzheng_btn1"><span class="normal">提交</span><span class="its_going">提交中…</span></div>
		</div>
    </div>
	<!-- // 邮箱验证码 -->
	
	<!-- 注册成功弹层 -->
	<div class="register_success_box" id="register_success_box1">
		<span class="register_success_btn">注册成功！</span>
	</div>
	<!-- // 注册成功弹层 -->
	
	<!-- 注册失败弹层 -->
	<div class="register_success_box" id="register_failed_box1">
		<span class="register_failed_btn">注册失败，<br />请稍候重试！</span>
	</div>
	<!-- // 注册失败弹层 -->

	<script type="text/javascript" src="http://192.168.242.44/sceapp/focus_static/wap/phone/snippets/wap_passport_v1.0.js"></script>
<%@ include file="/views/pv.jsp"%>
</body>
</html>
