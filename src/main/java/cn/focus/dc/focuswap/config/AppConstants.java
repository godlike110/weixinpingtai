package cn.focus.dc.focuswap.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.focus.dc.focuswap.service.XinWenSearchService.SearchType;

@Component
public class AppConstants {
    /**
     * 户型映射
     * 
     * @author rogantian
     */
    public static class Layout {
        private static String layout0Id = "-1";

        private static String layout0Desc = "主力户型";

        private static String layout1Id = "1";

        private static String layout1Desc = "一居";

        private static String layout2Id = "2";

        private static String layout2Desc = "两居";

        private static String layout3Id = "3";

        private static String layout3Desc = "三居";

        private static String layout4Id = "4";

        private static String layout4Desc = "四居";

        private static String layout5Id = "5";

        private static String layout5Desc = "五居";

        private static String layout6Id = "6";

        private static String layout6Desc = "六居";

        private static String layout7Id = "7";

        private static String layout7Desc = "七居";

        private static String layout8Id = "8";

        private static String layout8Desc = "八居";

        private static String layout9Id = "9";

        private static String layout9Desc = "九居";

        private static String layout10Id = "10";

        private static String layout10Desc = "十居";

        private static String layoutKaiJianId = "kaijian";

        private static String layoutKaiJianDesc = "开间";

        private static String layoutFuShiId = "fushi";

        private static String layoutFuShiDesc = "复式";

        private static String layoutDuDongId = "dudong";

        private static String layoutDuDongDesc = "独栋";

        private static String layoutLianPaiId = "lianpai";

        private static String layoutLianPaiDesc = "联排";

        private static String layoutDiePinId = "diepin";

        private static String layoutDiePinDesc = "叠拼";

        private static String layoutOthersId = "99";

        private static String layoutOthersDesc = "其它";

        private static String layoutAllId = "all";

        private static String layoutAllDesc = "总面积";

        // 楼盘图片
        private static String louzuoPicId = "-10";

        private static String louzuoPicDesc = "楼座图";

        private static String yangbanPicId = "-12";

        private static String yangbanPicDesc = "样板图";

        private static String zbptPicId = "-13";

        private static String zbptPicDesc = "周边配套";

        private static String jiaotongPicId = "-14";

        private static String jiaotongPicDesc = "交通图";

        private static String xiaoguoPicId = "-16";

        private static String xiaoguoPicDesc = "效果图";

        private static String jinduPicId = "-17";

        private static String jinduPicDesc = "施工进度图";

        private static String shijingPicId = "-18";

        private static String shijingPicDesc = "实景图";

        private static String sougouVR = "-20";

        private static String sougouVRDesc = "搜狗VR图 ";

        private static Map<String, String> layoutMap = new HashMap<String, String>();       
        
        static {
            layoutMap.put(layout0Id, layout0Desc);
            layoutMap.put(layout1Id, layout1Desc);
            layoutMap.put(layout2Id, layout2Desc);
            layoutMap.put(layout3Id, layout3Desc);
            layoutMap.put(layout4Id, layout4Desc);
            layoutMap.put(layout5Id, layout5Desc);
            layoutMap.put(layout6Id, layout6Desc);
            layoutMap.put(layout7Id, layout7Desc);
            layoutMap.put(layout8Id, layout8Desc);
            layoutMap.put(layout9Id, layout9Desc);
            layoutMap.put(layout10Id, layout10Desc);
            layoutMap.put(layoutKaiJianId, layoutKaiJianDesc);
            layoutMap.put(layoutFuShiId, layoutFuShiDesc);
            layoutMap.put(layoutDuDongId, layoutDuDongDesc);
            layoutMap.put(layoutLianPaiId, layoutLianPaiDesc);
            layoutMap.put(layoutDiePinId, layoutDiePinDesc);
            layoutMap.put(layoutOthersId, layoutOthersDesc);
            layoutMap.put(layoutAllId, layoutAllDesc);
            layoutMap.put(louzuoPicId, louzuoPicDesc);
            layoutMap.put(yangbanPicId, yangbanPicDesc);
            layoutMap.put(zbptPicId, zbptPicDesc);
            layoutMap.put(jiaotongPicId, jiaotongPicDesc);
            layoutMap.put(xiaoguoPicId, xiaoguoPicDesc);
            layoutMap.put(jinduPicId, jinduPicDesc);
            layoutMap.put(shijingPicId, shijingPicDesc);
            layoutMap.put(sougouVR, sougouVRDesc);
        }

        public static String getLayoutName(String layoutType) {
            return layoutMap.get(layoutType);
        }

        public static String getOtherLayoutId() {
            return layoutOthersId;
        }

        public static String getLayoutAllId() {
            return layoutAllId;
        }
    }
    
    /**
     * 团购状态说明
     * @author zhiweiwen
     *
     */
    public static class TuanGouStatus{
    	
    	
    	public static int SUCCESS=0;
    	public static String SUCCESS_STR = "报名成功";
    	
    	public static int REREGIST=1;
    	public static String REREGIST_STR = "重复报名";
    	
    	public static int ACTIVEEND=2;
    	public static String ACTIVEEND_STR = "活动结束";
    	
    	public static int ERR=99;
    	public static String ERR_STR = "未知错误";
    	
    	private static Map<Integer,String> statusMap = new HashMap<Integer,String>();
    	
    	static {
    		statusMap.put(SUCCESS, SUCCESS_STR);
    		statusMap.put(REREGIST, REREGIST_STR);
    		statusMap.put(ACTIVEEND,ACTIVEEND_STR);
    		statusMap.put(ERR, ERR_STR);
    		
    	}
    	
    	public static String getStatusStr(int key) {
    		return statusMap.get(key);
    	}
    	
    }

    /**
     * 城市页面映射
     * 
     * @author zhiweiwen 2013-11-15 下午5:02:55
     */
    public static class CityPageStatus {
        
        // 有新房和二手房
        public static int XF_AND_ESF = 1;

        // 新房
        public static int XF = 2;

        // 二手房
        public static int ESF = 3;
        
        private static Map<String, Integer> cityPageMap = new HashMap<String, Integer>();

        private static String ESF_URL = "esf.focus.cn/s/";

        private static String ESF_SUGGEST_URL = "esf.focus.cn/ajax/wj31_get_suggest_house.php?q={q}";
    
        static {
            cityPageMap.put("北京", XF_AND_ESF);
            cityPageMap.put("上海", XF_AND_ESF);
            cityPageMap.put("广州", XF_AND_ESF);
            cityPageMap.put("深圳", XF_AND_ESF);
            cityPageMap.put("天津", XF_AND_ESF);
            cityPageMap.put("成都", XF_AND_ESF);
            cityPageMap.put("重庆", XF_AND_ESF);
            cityPageMap.put("石家庄", XF_AND_ESF);
            // cityPageMap.put("西安", XF);
            // cityPageMap.put("南京", XF);
            // cityPageMap.put("武汉", XF);
            // cityPageMap.put("杭州", XF);
            // cityPageMap.put("长沙", XF);
            // cityPageMap.put("哈尔滨", XF);
            // cityPageMap.put("昆明", XF);
            // cityPageMap.put("海南", XF);
            // cityPageMap.put("青岛", XF);
            // cityPageMap.put("大连", XF);
            // cityPageMap.put("无锡", XF);
            // cityPageMap.put("宁波", XF);
            // cityPageMap.put("南宁", XF);
            // cityPageMap.put("常州", XF);
            cityPageMap.put("沈阳", XF_AND_ESF);
            cityPageMap.put("濮阳", XF_AND_ESF);

        }

        public static int getCityStatus(String cityName) {
            if (null == cityPageMap.get(cityName)) {
                return XF;
            }
            return cityPageMap.get(cityName);
        }

        public static String getEsfUrl() {
            return ESF_URL;
        }

        public static String getEsfSuggestUrl() {
            return ESF_SUGGEST_URL;
        }
    }

    /**
     * 楼盘价格
     * @author zhiweiwen
     * 2014-2-17
     * 上午11:29:16
     */
    public static class BuldingPrice {
        // 均价
        public static int AVGPRICE = 1;

        // 最低价
        public static int MINPRICE = 2;

        // 最高价
        public static int MAXPRICE = 3;

        // 总价
        public static int ALLPRICE = 4;

		public static String getPriceForShow(int price, int type) {
            switch (type) {
            case 1:
            	return "<span>均价 <em class=\"colorcb\"><font color=\"#cb000e\">" + price + "</font></em>元/平米</span>";
               // return "均价  <em>" + price + "</em>元/平米";
            case 2:
            	return "<span>最低 <em class=\"colorcb\"><font color=\"#cb000e\">" + price + "</font></em>元/平米</span>";
            case 3:
            	return "<span>最高 <em class=\"colorcb\"><font color=\"#cb000e\">" + price + "</font></em>元/平米</span>";
            case 4:
            	return "<span>总价 <em class=\"colorcb\"><font color=\"#cb000e\">" + price + "</font></em>万/套</span>";
            case -2:
                return "<span><em class=\"colorcb\"><font color=\"#cb000e\">" + "价格待定" + "</font></em></span>";
            default:
                return "参考价格 : <em>待定</em>";
            }
        }
		
		public static String getPriceForShowMap(int price,int type) {
			 switch (type) {
	            case 1:
//	            	return "<span><em class=\"colorcb\"><font color=\"#cb000e\">" + price + "元/平米</font></em></span>";
	               // return "均价  <em>" + price + "</em>元/平米";
	            case 2:
//	            	return "<span><em class=\"colorcb\"><font color=\"#cb000e\">" + price + "元/平米</font></em></span>";
	            case 3:
	            	return "<span><em class=\"colorcb\"><font color=\"#cb000e\">" + price + "元/m²</font></em></span>";
	            case 4:
	            	return "<span><em class=\"colorcb\"><font color=\"#cb000e\">" + price + "万/套</font></em></span>";
	            case -2:
	                return "<span><em class=\"colorcb\"><font color=\"#cb000e\">" + "价格待定" + "</font></em></span>";
	            default:
	                return "参考价格 : <em>待定</em>";
	            }
		}

    }
    
    /**
     * 手机验证码登入
     * @author zhiweiwen
     *
     */
    public static class PhoneLogin {

    		private static Map<Integer,String> sendMap = new HashMap<Integer,String>();
    		private static Map<Integer,String> loginMap = new HashMap<Integer,String>();
    		
    		/*****************************发送状态开始***************************/
    		//发送成功
    		public static int SEND_SUCC = 0;    		
    		//参数错误
    		public static int PARAM_ERR = 1;    		
    		//code错误
    		public static int CODE_ERR = 2;    		
    		//手机号获取验证码间隔小于1分钟
    		public static int SEND_LESS_THAN_ONE_MIN = 3;    		
    		//手机号获取验证码次数超限
    		public static int SEND_OVERRUN = 4;    		
    		//ip获取验证码次数超限
    		public static int IP_SEND_OVERRUN = 5;   		
    		//手机登入次数超限
    		public static int LOGIN_OVERRUN = 6;    		
    		//ip登入次数超限
    		public static int ip_LOGIN_OVERRUN = 7;
    		//服务错误
    		public static int SERVER_ERR = -1;
    		//手机号码格式错误
    		public static int PHONE_FORMAT_ERR = -2;
    		/*****************************发送状态结束***************************/
    		
    		/*****************************登入状态开始***************************/
    		//登入成功
    		public static int LOGIN_SUCC = 0;
    		//缺少参数
    		public static int NEED_PARAM = 1;
    		//验证码错误
    		public static int LOGIN_CODE_ERR = -1;
    		//同一手机号校验次数超限
    		public static int LOGIN_CODE_OVERRUN = 2;
    		//同一手机号校发送验证码次数超限
    		public static int LONG_SEND_CODE_OVERRUN = 3;
    		//同一ip校验验证码次数超限 
    		public static int IP_LOGIN_OVERRUN = 4;
    		//服务错误
    		public static int LOGIN_SERVER_ERR = -2;
    		/*****************************登入状态结束***************************/
    		
    		static {
    			loginMap.put(LOGIN_SUCC, "登入成功");
    			loginMap.put(NEED_PARAM, "缺少参数");
    			loginMap.put(LOGIN_CODE_ERR, "验证码错误");
    			loginMap.put(LOGIN_CODE_OVERRUN, "同一手机号校验次数超限");
    			loginMap.put(LONG_SEND_CODE_OVERRUN, "同一手机号校发送验证码次数超限");
    			loginMap.put(IP_LOGIN_OVERRUN, "同一ip校验验证码次数超限");
    			loginMap.put(LOGIN_SERVER_ERR, "服务器错误");
    			Iterator key1 = loginMap.keySet().iterator();
    			while(key1.hasNext()) {
    				Integer a = (Integer) key1.next();
    				System.out.println(a+":"+ loginMap.get(a));
    			}
    			
    			sendMap.put(SEND_SUCC, "发送成功");
    			sendMap.put(PARAM_ERR, "参数错误");
    			sendMap.put(CODE_ERR, "code错误");
    			sendMap.put(SEND_LESS_THAN_ONE_MIN, "手机号获取验证码间隔小于1分钟");
    			sendMap.put(SEND_OVERRUN, "手机号获取验证码次数超限");
    			sendMap.put(IP_SEND_OVERRUN, "ip获取验证码次数超限");
    			sendMap.put(LOGIN_OVERRUN, "手机登入次数超限");
    			sendMap.put(ip_LOGIN_OVERRUN, "ip登入次数超限");
    			sendMap.put(SERVER_ERR, "服务错误");
    			sendMap.put(PHONE_FORMAT_ERR, "手机号码格式错误");
    			Iterator key = sendMap.keySet().iterator();
    			while(key.hasNext()) {
    				Integer a = (Integer) key.next();
    				System.out.println(a+":"+sendMap.get(a));
    			}
    		}
    		
    		public static String getsendStatus(int key) {
    			return sendMap.get(key);
    		}
    		
    		public static String getLoginStatus(int key) {
    			return loginMap.get(key);
    		}
    } 
    
    /**
     * passport状态数据
     * @author zhiweiwen
     *
     */
    public static class PassportStatus {
    	
    	//需要登入限制
    	public static final int NEED_LOGIN_LIMIT = 0;
    	//不需要登入限制
    	public static final int NONEED_LOGIN_LIMIT = 1;
    	
    	/**
    	 * 登入状态
    	 */
    	private static Map<Integer,String> loginMap = new HashMap<Integer,String>();
    	
    	/**
    	 * 邮箱注册
    	 */
    	private static Map<Integer,String> emailRegistMap = new HashMap<Integer,String>();
    	
    	/**
    	 * 手机注册
    	 */
    	private static Map<Integer,String> phoneRegistMap = new HashMap<Integer,String>();
    	
    	/**
    	 * 注册
    	 */
    	private static Map<Integer,String> registMap = new HashMap<Integer,String>();
    	
    	/**
    	 * 发送验证码
    	 */
    	private static Map<Integer,String> sendMap = new HashMap<Integer,String>();
    	
    	/**
    	 * 登入相关状态
    	 */
    	public static int LOGIN_SUCCESS  = 0;    	
    	public static String LOGIN_SUCCESS_STR = "登录成功";
    	public static int PASSWORD_WRONG = 3;
    	public static String PASSWORD_WRONG_STR = "密码错误";
    	public static final int UNKNOW_USERID = 20;
    	public static String UNKNOW_USERID_STR = "用户不存在";
    	public static final int LOGIN_EMAIL_ERR = 21;
    	public static String LOGIN_EMAIL_ERR_STR = "账号格式错误";
    	public static int LOGIN_OVERRRUN = 12;
    	public static String LOGIN_OVERRUN_STR = "登录20次了，请明天再来";
    	public static int ACC_LOGIN_OVERRRUN = 16;
    	public static String ACC_LOGIN_OVERRUN_STR = "今天登录6次了，请明天再来";
    	public static final int ACC_LOGIN_PASSWORDWRONG_OVERRUN = 17;
    	public static String ACC_LOGIN_PASSWORDWRONG_OVERRUN_STR = "密码错误6次,请明天再来";
    	public static int UNACTIVE_PHONE_USER = 7;
    	public static String UNACTIVE_PHONE_USER_STR = "未激活的手机注册账户";
    	public static int UNACTIVED_EMAIL_USER = 8;
    	public static String UNACTIVED_EMAIL_USER_STR = "未激活的外域账号";
    	public static int OUT_SERVICE = -1;
    	public static String OUT_SERVICE_STR = "服务异常";
    	
    	/**
    	 * 邮箱注册
    	 */
    	// 注册成功
    	public static final int REGIST_SUCCESS = 0;
    	public static String REGIST_SUCCESS_STR = "注册成功";
    	
    	// 验证码错误
    	public static final int VERICODE_ERROR = 2;
    	public static String VERICODE_ERROR_STR = "验证码错误";
    	
    	// 非法UID
    	public static final int ILLEGAL_USER = 3;
    	public static String ILLEGAL_USER_STR = "非法UID";
    	
    	// uid已经存在
    	public static int USER_EXIST = 4;
    	public static String USER_EXIST_STR = "用户已经存在";
    	
    	// 创建用户失败
    	public static int CREATE_USER_FAILED = 6;
    	public static String CREATE_USER_FAILED_STR = "创建用户失败";
    	
    	// 注册超限
    	public static final int EMAIL_SIGN_OVERRUN = 5;
    	public static String EMAIL_SIGN_OVERRUN_STR = "20次了，请明天再来吧";
    	
    	// 调用超限（5分钟调用超过了1000次）
    	public static int REGIST_OVERRUN = 10;
    	public static String REGIST_OVERRUN_STR = "调用次数超限";
    	
    	// 不能注册vip.sohu.com的账号
    	public static int REGIST_VIP_ILLEGAL = 11;
    	public static String REGIST_VIP_ILLEGAL_STR = "不能注册vip.sohu.com的账号";
    	
    	// 邮箱验证码错误次数超限
    	public static int EMAIL_VERIFY_OVERRUN = 12;
    	public static String EMAIL_VERIFY_OVERRUN_STR = "邮箱验证码错误次数超限";
    	
    	// 邮箱验证码错误或者已过期
    	public static final int EMAIL_VERIFY_WRONG = 13;
    	public static String EMAIL_VERIFY_WRONG_STR = "邮箱验证码错误或者已过期";
    	
    	/**
    	 * 手机注册
    	 */
    	// 手机注册成功
    	public static final int PHONE_REGIST_SUCCESS = 0;
    	public static String PHONE_REGIST_SUCCESS_STR = "手机注册成功";
    	
    	// 手机注册失败
    	public static final int PHONE_REGIST_FAILED = 3;
    	public static String PHONE_REGIST_FAILED_STR = "手机注册失败";
    	
    	// 手机号@sohu.com已经存在
    	public static int PHONE_EXISTED = 4;
    	public static String PHONE_EXISTED_STR = "手机号@sohu.com已经存在";
    	
    	// 手机号已经绑定了账号
    	public static int PHONE_ALREADY_BINDED = 5;
    	public static String PHONE_ALREADY_BINDED_STR = "手机号已经绑定了账号";
    	
    	// 发送验证码次数超限
    	public static int PHONE_VERIFY_OVERRUN = 6;
    	public static String PHONE_VERIFY_OVERRUN_STR = "发送验证码次数超限";
    	
    	// 校验验证码次数超限
    	public static int PHONE_CHECKVERIFY_OVERRUN = 7;
    	public static String PHONE_CHECKVERIFY_OVERRUN_STR = "校验验证码次数超限";
    	
    	// 验证码错误或者已经过期
    	public static final int PHONE_VERIFY_WRONG = 8;
    	public static String PHONE_VERIFY_WRONG_STR = "验证码错误或过期";
    	
    	/**
    	 * 手机绑定
    	 */
    	// 手机未被绑定
    	public static int PHONE_AVAILABLE = 3;
    	public static String PHONE_AVAILABLE_STR = "手机账号可用";
    	// 手机被绑定
    	public static int PHONE_DISABLE = 10;
    	public static String PHONE_DISABLE_STR = "手机账号被绑定";
    	
    	/**
    	 * 账号查询
    	 */
    	// 用户已经存在
    	public static int USERID_EXIST = 2;
    	public static String USERID_EXIST_STR = "用户已经存在";
    	
    	// 用户不存在
    	public static int USERID_AVAILABLE = 0;
    	public static String USERID_AVAILABLE_STR = "用户不存在";
    	
    	
    	/**
    	 * 类型
    	 */
    	// 手机账号
    	public static int TYPE_PHONE = 0;
    	// 邮箱账号
    	public static int TYPE_EMAIL = 1;
    	// 其他
    	public static int OTHER = 2;
    	
    	/**
    	 * 邮箱域
    	 */
    	public static final int OUTER_EMAIL = 2;
    	public static final int INNER_UNSPPORT = 1;
    	public static final int INNER_SUPPORT_EMAIL = 0;
    	
    	
    	/**
    	 * 注册状态
    	 */
    	public static final int SIGN_SUCCESS = 0;
    	public static final String SIGN_SUCCESS_STR = "注册成功";
    	public static final int SIGN_FAILED = 1;
    	public static final String SIGN_FAILED_STR = "注册失败";
    	public static final int PHONE_CODE_ERR = 3;
    	public static final String PHONE_CODE_ERR_STR = "手机激活码错误";
    	public static final int MAIL_CODE_ERR = 4;
    	public static final String MAIL_CODE_ERR_STR = "邮箱激活码错误";
    	public static final int SIGN_OVERRUN = 5;
    	public static final String SIGN_OVERRUN_STR = "20次了，请明天再来吧";
    	public static final int INNER_EMAIL_UPSPPORT = 16;
    	public static final String INNER_EMAIL_UPSPPORT_STR = "暂不支持此邮箱";
    	public static final int INNER_REGIST_SUCC = 17;
    	public static final String INNER_REGIST_SUCC_STR = "内域注册成功";
    	public static final int INNER_REGIST_FAILED = 18;
    	public static final String INNER_REGIST_FAILED_STR = "内域注册失败";
    	public static final int EMAIL_FORMAT_ERR = 20;
    	public static final String EMAIL_FORMAT_ERR_STR = "邮箱格式错误";
    	public static final int PASSWORD_FORMAT_ERR = 24;
    	public static final String PASSWORD_FORMAT_ERR_STR = "密码6-16位字母数字或符号";
    	public static final int PHONE_FORMAT_ERR = 22;
    	public static String PHONE_FORMAT_ERR_STR = "手机格式错误";
    	public static final int HAS_SENDCODE = 19;
    	public static final String HAS_SENDCODE_STR = "验证码已经发送";
    	public static final int CAPTURE_CHECK_FAILED = 23;
    	public static final String CAPTURE_CHECK_FAILED_STR = "图片验证码错误";
    	public static final int EMAIL_CAPTURE_CHECK_OVERRUN = 25;
    	public static final String EMAIL_CAPTURE_CHECK_OVERRUN_STR = "验证码输入错误超限，不再发送邮";
    	public static final int EMAIL_CAPTURECHECK_ERR_OVERRUN = 26;
    	public static final String EMAIL_CAPTURECHECK_ERR_OVERRUN_STR = "激活码输入错误次数太多，请明天再来";
    	
    	/**
    	 * 发送验证码状态
    	 */
    	public static final int SEND_SUCCESS = 0;
    	public static final String SEND_SUCCESS_STR = "发送成功";
    	public static final int SEND_FAILED = 1;
    	public static final String SEND_FAILED_STR = "发送失败";
    	public static final int SEND_OVERRUN = 3;
    	public static final String SEND_OVERRUN_STR = "今天已经发了6次激活码";
    	
    	static {
    		loginMap.put(LOGIN_SUCCESS, LOGIN_SUCCESS_STR);
    		loginMap.put(PASSWORD_WRONG, PASSWORD_WRONG_STR);
    		loginMap.put(UNACTIVE_PHONE_USER, UNACTIVE_PHONE_USER_STR);
    		loginMap.put(UNACTIVED_EMAIL_USER, UNACTIVED_EMAIL_USER_STR);
    		loginMap.put(OUT_SERVICE, OUT_SERVICE_STR);
    		loginMap.put(VERICODE_ERROR, VERICODE_ERROR_STR);
    		loginMap.put(LOGIN_OVERRRUN, LOGIN_OVERRUN_STR);
    		loginMap.put(ACC_LOGIN_OVERRRUN, ACC_LOGIN_OVERRUN_STR);
    		loginMap.put(UNKNOW_USERID, UNKNOW_USERID_STR);
    		loginMap.put(LOGIN_EMAIL_ERR, LOGIN_EMAIL_ERR_STR);
    		loginMap.put(ACC_LOGIN_PASSWORDWRONG_OVERRUN, ACC_LOGIN_PASSWORDWRONG_OVERRUN_STR);
    		
    		emailRegistMap.put(REGIST_SUCCESS, REGIST_SUCCESS_STR);
    		emailRegistMap.put(VERICODE_ERROR, VERICODE_ERROR_STR);
    		emailRegistMap.put(ILLEGAL_USER, ILLEGAL_USER_STR);
    		emailRegistMap.put(USER_EXIST, USER_EXIST_STR);
    		emailRegistMap.put(CREATE_USER_FAILED, CREATE_USER_FAILED_STR);
    		emailRegistMap.put(REGIST_OVERRUN, REGIST_OVERRUN_STR);
    		emailRegistMap.put(REGIST_VIP_ILLEGAL, REGIST_VIP_ILLEGAL_STR);
    		emailRegistMap.put(EMAIL_VERIFY_OVERRUN, EMAIL_VERIFY_OVERRUN_STR);
    		emailRegistMap.put(EMAIL_VERIFY_WRONG, EMAIL_VERIFY_WRONG_STR);
    		
    		phoneRegistMap.put(PHONE_REGIST_SUCCESS, PHONE_REGIST_SUCCESS_STR);
    		phoneRegistMap.put(PHONE_REGIST_FAILED, PHONE_REGIST_FAILED_STR);
    		phoneRegistMap.put(PHONE_EXISTED, PHONE_EXISTED_STR);
    		phoneRegistMap.put(PHONE_ALREADY_BINDED, PHONE_ALREADY_BINDED_STR);
    		phoneRegistMap.put(VERICODE_ERROR, VERICODE_ERROR_STR);
       		phoneRegistMap.put(PHONE_VERIFY_OVERRUN, PHONE_VERIFY_OVERRUN_STR);
       		phoneRegistMap.put(PHONE_CHECKVERIFY_OVERRUN, PHONE_CHECKVERIFY_OVERRUN_STR);
       		phoneRegistMap.put(PHONE_VERIFY_WRONG, PHONE_VERIFY_WRONG_STR);
       		
       		registMap.put(SIGN_SUCCESS, SIGN_SUCCESS_STR);
       		registMap.put(SIGN_FAILED, SIGN_FAILED_STR);
       		registMap.put(PHONE_CODE_ERR, PHONE_CODE_ERR_STR);
       		registMap.put(MAIL_CODE_ERR, MAIL_CODE_ERR_STR);
       		registMap.put(SIGN_OVERRUN, SIGN_OVERRUN_STR);
       		registMap.put(INNER_REGIST_SUCC, INNER_REGIST_SUCC_STR);
       		registMap.put(INNER_REGIST_FAILED, INNER_REGIST_FAILED_STR);
       		registMap.put(EMAIL_FORMAT_ERR, EMAIL_FORMAT_ERR_STR);
       		registMap.put(HAS_SENDCODE, HAS_SENDCODE_STR);
       		registMap.put(CAPTURE_CHECK_FAILED, CAPTURE_CHECK_FAILED_STR);
       		registMap.put(INNER_EMAIL_UPSPPORT, INNER_EMAIL_UPSPPORT_STR);
       		registMap.put(PHONE_FORMAT_ERR, PHONE_FORMAT_ERR_STR);
       		registMap.put(EMAIL_CAPTURE_CHECK_OVERRUN, EMAIL_CAPTURE_CHECK_OVERRUN_STR);
       		registMap.put(EMAIL_CAPTURECHECK_ERR_OVERRUN, EMAIL_CAPTURECHECK_ERR_OVERRUN_STR);
       		
       		sendMap.put(SEND_SUCCESS, SEND_SUCCESS_STR);
       		sendMap.put(SEND_FAILED, SEND_FAILED_STR);
       		sendMap.put(SEND_OVERRUN, SEND_OVERRUN_STR);
    	}
    	
    	/**
    	 * 获取登入状态
    	 * @param key
    	 * @return
    	 */
    	public static String getLoginStatus(int key) {
    		return loginMap.get(key);
    	}
    	
    	/**
    	 * 获取邮箱注册状态
    	 * @param key
    	 * @return
    	 */
    	public static String getEmailRegistStatus(int key) {
    		return emailRegistMap.get(key);
    	}
    	
    	/**
    	 * 获取手机注册状态
    	 * @param key
    	 * @return
    	 */
    	public static String getPhoneRegistStatus(int key) {
    		return phoneRegistMap.get(key);
    	}
    	
    	/**
    	 * 获取注册状态
    	 * @param key
    	 * @return
    	 */
    	public static String getRegistStatus(int key) {
    		return registMap.get(key);
    	}
    	
    	/**
    	 * 发送验证码状态
    	 * @param key
    	 * @return
    	 */
    	public static String getSendStatus(int key) {
    		return sendMap.get(key);
    	}
    	
    }
    
    /**
     * 内容分享
     * @author zhiweiwen
     * 2014-2-17
     * 上午11:41:34
     */
    public static class ContentShare {
        private static String SWB_URL = "http://service.weibo.com/share/share.php?appkey=1429547852&count=n&ralateUid=3687486675";
        private static String TWB_URL = "http://share.v.t.qq.com/index.php?c=share&a=index&f=f1&bm=11";
        private static String QZONE_URL = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?site=";
        
        private static String SWB_TYPE = "sWeibo";
        private static String QZONE_YTPE = "qZone";
        private static String TWB_TYPE = "tWeibo";
        
        private static Map<String,String> urlMap = new HashMap<String,String>();
        
        static {
            urlMap.put(SWB_TYPE, SWB_URL);
            urlMap.put(TWB_TYPE, TWB_URL);
            urlMap.put(QZONE_YTPE, QZONE_URL);
        }
        
        public static String getShareUrl(String type) {
            return urlMap.get(type);
        }
    }
    
    /**
     * 自住房城市信息
     * @author zhiweiwen
     *
     */
    public static class ZzfCityStatus {
    	
    	private static Map<String, Integer> cityStatusMap = new HashMap<String, Integer>();
    	private static int HAS_ZZF = 1;
    	private static int HASNO_ZZF = 0;
    	
        static {
        	cityStatusMap.put("bj", HAS_ZZF);
        }
        
        public static int getCityZzfStatus(String cityPingYinabbr) {
        	Integer status = cityStatusMap.get(cityPingYinabbr);
        	if(null != status) {
        		return status;
        	} 
        	return HASNO_ZZF;
        }
    	
    }

    /**
     * 装修级别映射
     * 
     * @author rogantian
     */
    public static class DecorateLevel {
        private static int level1 = 1;

        private static String level1Desc = "精装";

        private static int level2 = 2;

        private static String level2Desc = "简装";

        private static int level3 = 3;

        private static String level3Desc = "毛坯";

        private static int level4 = 4;

        private static String level4Desc = "豪华";

        private static Map<Integer, String> levelMap = new HashMap<Integer, String>();

        static {
            levelMap.put(level1, level1Desc);
            levelMap.put(level2, level2Desc);
            levelMap.put(level3, level3Desc);
            levelMap.put(level4, level4Desc);
        }

        public static String getLevelName(int level) {
            return levelMap.get(level);
        }
    }
    
    /**
     * 新版问答相关状态
     * @author rogantian
     * @date 2014-7-10
     * @email rogantianwz@gmail.com
     */
    public static class QAStatus {
        /**问题初始化*/
        public static final int QA_INIT = 1;

        /**问题不通过*/
        public static final int QA_REJECTED = 2;

        /**问题已回答*/
        public static final int QA_ANSWERED = 6;

        /**问题已删除*/
        public static final int QA_DELETED = -1;
    }

    /**
     * 中国地图上的经纬度坐标范围
     */
    // 经度
    public static long longtitudeMin = 73;

    public static long longtitudeMax = 136;

    // 纬度
    public static long latitudeMin = 18;

    public static long latitudeMax = 53;

    /***************************** 缓存相关BEGIN **********************************/
    
    //ip手机单日注册次数key
    public static final String WAP_PHONE_REGIST_COUNT_KEY = "wap:phone:regist:count:key";
    
    //ip手机单日注册次数过期时间
    public static final int WAP_PHONE_REGIST_COUNT_TIME = 24*3600;
    
    //ip手机单日登入次数key
    public static final String WAP_DAY_LOGIN_COUNT_KEY = "wap:day:login:count:key";
    
    //ip手机单日登入次数过期时间
    public static final int WAP_DAY_LOGIN_COUNT_TIME = 24*3600;
    
    //账号单日密码错误次数key
    public static final String WAP_DAY_LOGIN_PASSWORD_ERR_COUNT_KEY = "wap:day:login:passworderr:count:key";
    
    //账号单日密码错误次数time
    public static final int WAP_DAY_LOGIN_PASSWORD_ERR_COUNT_TIME = 24*3600;
    
    // 各城市站是否有导购的状态标志过期时间(秒)
    public static final int CE_WAP_PROPOSE_TIME = 1800;

    // 各城市站是否有导购的状态标志key
    public static final String CK_WAP_PROPOSE_STATUS = "wap:home:propose:status:";

    // 首页主编推荐楼盘列表缓存过期时间(秒)
    public static final int CE_HOME_RECOMMENDLIST_TIME = 3600;

    // 首页主编推荐楼盘列表缓存key
    public static final String CK_HOME_RECOMMENDLIST = "wap:home:recommendlist:";

    // 买房搜索条件缓存失效时间（秒）
    public static final int DATE_CENTER_INTERFACE_EXPIRE = 2 * 3600;

    // 买房搜索条件缓存key
    public static final String BUY_BUILD_CONDITION_KEY = "wap:buy:building:condition";

    // 楼盘搜索条件缓存时间
    public static final int CE_BUILDING_SEARCH_TIME = 2 * 3600;

    // 楼盘搜素条件缓存key
    public static final String CK_BUILDING_SEARCH_KEY = "wap:building:search:";

    // 最新动态列表放入缓存的有效期10分钟
    public static final int CE_RECENTNEWS_TIME = 10 * 60;

    // 最新动态缓存
    public static final String CK_RECENTNEWS_LIST = "wap:recentnews:list:";

    // 最新动态详细页缓存
    public static final String CK_CENTENTNEW_ITEM = "wap:recentnew:item:";

    // 新闻列表放入缓存的有效期一天
    public static final int CE_NEWS_TIME = 60 * 60 * 24;

    // 新闻列表缓存
    public static final String CK_NEWS_LIST = "wap:news:list:";

    // 新闻详细页缓存
    public static final String CK_NEW_ITEM = "wap:new:item:";

    // 城市信息缓存
    public static final String CK_CITY_DOMAIN_NAME="wap_city_cache";
    
    //城市信息缓存时间
    public static final int CE_CITY_DOMAIN_NAME=3600*24*30;
    // 条件缓存
    public static final String CK_CONDITION = "condition:";
    
    // 首页要闻缓存
    public static final String INDEX_YAOWEN = "index_yaowen_cache";
    
    // 首页要闻缓存过期时间(秒)
    public static final int INDEX_YAOWEN_TIME = 2 * 60 * 60;
    
    // 获取7天的楼盘Id
    public static final String GROUD_ID_FROM_SEVEN_DAYS = "index_yaowen_cache";
    
    // 获取7天的楼盘Id时间(秒)
    public static final int GROUD_ID_FROM_SEVEN_DAYS_TIME = 24 * 60 * 60;


    public static final int CK_CONDITION_TIME = 60;

    // 爬房团小贴士列表缓存key
    public static final String CK_PAFANGTUAN_TIPS_LIST = "wap:pafangtuan:tips:list:";

    // 爬房团小贴士列表缓存过期时间(秒)
    public static final int CE_PAFANGTUAN_TIPS_LIST = 2 * 60 * 60;
    
    // 距离当前最近两天的爬房团数据缓存key
    public static final String CK_PANGTUAN_2_DAYS_MAP = "wap:pafangtuan:2days:map:";
    
    // 距离当前最近两天的爬房团数据缓存时间
    public static final int CE_PANGTUAN_2_DAYS_MAP = 1 * 60 * 60;
    
    // 爬房团列表和首页爬房团空集合缓存时间
    public static final int CE_PANGTUAN_EMPTY_COLLECTION = 5*60 ;
    
    // 爬房团列表缓存key
    public static final String CK_PAFANGTUAN_LIST = "wap:pafangtuan:list:";

    // 爬房团列表缓存时间
    public static final int CE_PAFANGTUAN_LIST = 1 * 60 * 60;
    
    //爬房团包含groupIds集合缓存key
    public static final String CK_PAFANGTUAN_GROUPIDS = "wap:pafangtuan:groupids:";
    
    //爬房团包含groupIds集合缓存时间
    public static final int CE_PAFANGTUAN_GROUPIDS = 1 * 60 * 60;
    
    // 新版爬房团小贴士列表缓存key
    public static final String CK_PAFANGTUAN_TIPS_LIST_NEW = "wap:pafangtuan:tips:list:new:";

    // 新版爬房团小贴士列表缓存过期时间(秒)
    public static final int CE_PAFANGTUAN_TIPS_LIST_NEW = 2 * 60 * 60;
    
    public static final String CK_PAFANGTUAN_CITY_INFO = "wap:pafangtuan:cityinfo:";
    
    public static final int CE_PAFANGTUAN_CITY_INFO = 2 * 60 * 60;
    
    // 首页焦点图缓存key
    public static final String CK_HOME_JIAODIANTU_LIST = "wap:home:jiaodiantu:list:";
    
    // 首页焦点图缓存过期时间
    public static final int CE_HOME_JIAODIANTU_LIST = 1 * 60 * 60;
    
    //首页找房模块楼盘总数缓存key
    public static final String CK_HOME_ZHAOFANG_TOTAL = "wap:home:zhaofang:total:";
    
    //首页找房模块楼盘总数缓存过期时间
    public static final int CE_HOME_ZHAOFANG_TOTAL = 1 * 60 * 60;
    
    //首页最新开盘模块楼盘列表缓存key
    public static final String CK_HOME_ZXKP_LIST = "wap:home:zxkp:list:";
    
    //首页最新开盘模块楼盘列表缓存过期时间
    public static final int CE_HOME_ZXKP_LIST = 1 * 60 * 60;
    
    //首页小户型模块楼盘列表缓存key
    public static final String CK_HOME_XHX_LIST = "wap:home:xhx:list:";
    
    //首页小户型模块楼盘列表缓存过期时间
    public static final int CE_HOME_XHX_LIST = 1 * 60 * 60;
    
    //首页打折楼盘列表缓存key
    public static final String CK_HOME_DZLP_LIST = "wap:home:dzlp:list:";
    
    //首页打折楼盘列表缓存过期时间
    public static final int CE_HOME_DZLP_LIST = 1 * 60 * 60;
    

    /***************************** 缓存相关END ************************************/

    /**
     * 电商活动状态映射
     * 
     * @author zhiweiwen 2013-11-27 上午9:55:55
     */
    public static class ActiveStatus {

        // 接口返回报名成功
        private static int INTERFACE_SUCCESS = 0;

        // 接口返回重复报名
        private static int INTERFACE_REPEAT = 1;

        // 接口返回活动结束
        private static int INTERFACE_END = 2;

        // 接口返回未知错误
        private static int INTERFACE_ERR = 99;

        // 输出报名成功
        private static int SUCCESS = 1;

        // 输出报名失败
        private static int FAILED = 0;

        // 输出重复报名
        private static int REPEAT = 2;

        private static Map<Integer, Integer> statusMap = new HashMap<Integer, Integer>();

        static {
            statusMap.put(INTERFACE_SUCCESS, SUCCESS);
            statusMap.put(INTERFACE_REPEAT, REPEAT);
            statusMap.put(INTERFACE_END, FAILED);
            statusMap.put(INTERFACE_ERR, FAILED);
        }

        public static int getResult(int status) {
            return statusMap.get(status);
        }

    }

    /***************************** 接口BEGIN *************************************/

    @ConfigProperty("interface.news_center.normal_news_digest.url")
    @Secretkey("secretkey.news_center")
    public static String NEWS_CENTER_NORMAL_NEWS_DIGEST_URL;

    @ConfigProperty("interface.news_center.normal_news_content.url")
    @Secretkey("secretkey.news_center")
    public static String NEWS_CENTER_NORMAL_NEWS_CONTENT_URL;

    @ConfigProperty("interface.news_center.related_news.url")
    @Secretkey("secretkey.news_center")
    public static String NEWS_CENTER_RELATED_NEWS;

    @ConfigProperty("interface.news_center.secretkey")
    @Secretkey("secretkey.news_center")
    public static String NEWS_CENTER_SECRETKEY;

    // 数据中心楼盘详情接口
    @ConfigProperty("interface.building.building_info.url")
    @Secretkey("secretkey.building")
    public static String DATACENTER_BUILDING_INFO_URL;

    // 数据中心楼盘详细信息接口
    @ConfigProperty("interface.building.building_detail.url")
    @Secretkey("secretkey.building")
    public static String DATACENTER_BUILDING_DETAIL_URL;

    // 数据中心楼盘户型图接口
    @ConfigProperty("interface.building.building_layout_pic.url")
    @Secretkey("secretkey.building")
    public static String DATACENTER_BUILDING_LAYOUT_PIC__URL;

    // 数据中心楼盘接口
    @Secretkey("secretkey.building")
    @ConfigProperty("interface.building.building_pic_list.url")
    public static String DATACENTER_BUILDING_PIC_URL;

    // 数据中心根据楼盘的城市ID查询城市信息
    @Secretkey("secretkey.building")
    @ConfigProperty("interface.building.building_city.url")
    public static String DATACENTER_BUILDING_CITY_URL;

    // 数据中心根据id获取楼盘列表接口
    @ConfigProperty("interface.building.building_ids_list.url")
    @Secretkey("secretkey.building")
    public static String DATACENTER_BUILDING_LIST_BY_IDS_URL;

    // 数据中心查询楼盘基本信息接口
    @ConfigProperty("interface.building.building_base.url")
    @Secretkey("secretkey.building")
    public static String DATACENTER_BUILDING_BASE_QUERY_URL;

    // 数据中心查询楼盘搜索条件接口
    @ConfigProperty("interface.building.building_search_con.url")
    @Secretkey("secretkey.building")
    public static String DATACENTER_BUILDING_NAME_BUY_CONDITION;

    // 数据中心查询楼盘周边信息
    @ConfigProperty("interface.building.building_around.url")
    @Secretkey("secretkey.building")
    public static String DATACENTER_BUILDING_AROUND;

    // 7日电话最多的楼盘id
    @ConfigProperty("interface.building.seven_days.url")
    @Secretkey(isMd5 = false,value="secretkey.data_center")
    public static String DATA_CENTER_GET_7DAYS_URL;
    
    // 数据中心楼盘信息查询使用的secretkey
    @ConfigProperty("interface.building.secretkey")
    public static String DATACENTER_BUILDING_SECRETKEY;

    // 楼盘动态接口(from PHP)
    @ConfigProperty("interface.php_interface.loupan")
    public static String PHP_LOUPAN_MOBILE_URL;

    // 楼盘动态接口(from PHP)
    @ConfigProperty("interface.php_interface.loupan_detail")
    public static String PHP_LOUPAN_DETAIL_MOBILE_URL;

    // 楼盘动态接口html(from PHP)
    @ConfigProperty("interface.php_interface.loupan_detail_html")
    public static String PHP_LOUPAN_NEW_MOBILE_URL;

    // 新闻资讯接口(from PHP)
    @ConfigProperty("interface.news.url")
    public static String CHANNEL_NEWS_QUERY_URL;

    // 获取电商活动信息接口
    @ConfigProperty("interface.active.info.url")
    public static String ACTIVE_INFO_URL;

    // 电商报名接口
    @ConfigProperty("interface.active.regist.url")
    public static String ACTIVE_REGIST_URL;

    // 获取爬房团线路信息接口
    @ConfigProperty("interface.pafangtuan.line_info.url")
    public static String PAFANGTUAN_LINE_INFO_URL;

    // 获取爬房团小贴士接口
    @ConfigProperty("interface.pafangtuan.tips.url")
    public static String PAFANGTUAN_TIPS_URL;

    // 获取爬房团报名接口
    @ConfigProperty("interface.pafangtuan.signup.url")
    public static String PAFANGTUAN_SIGNUP_URL;

    // 获取爬房团接口secretkey
    @ConfigProperty("interface.pafangtuan.secretkey")
    public static String PAFANGTUAN_SECRETKEY;

    // 获取爬房团list
    @ConfigProperty("interface.pafangtuan.line_list.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String PAFANGTUAN_LIST;
    
    // 获取爬房团报名接口
    @ConfigProperty("interface.pafangtuan.signup.url_login")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String PAFANGTUAN_SIGNUP_URL_LOGIN;
    
    // 获取新房typeid映射关系
    @ConfigProperty("interface.xinfang.type_id_relation.url")
    public static String XINFANG_TYPE_ID_RELATION_URL;
    
    // 分享参数验证控制开关
    @ConfigProperty("interface.daogou.controller")
    public static String SHARE_AUTH_OPEN;
    
    // 获取新房导购列表
    @ConfigProperty("interface.xinfang.daogou_list.url")
    public static String XINFANG_TYPE_DAOGOU_LIST_URL;
    
    // 获取新房导购详情
    @ConfigProperty("interface.xinfang.daogou_content.url")
    public static String XINFANG_TYPE_DAOGOU_CONTENT_URL;
    
    // 高德逆地理编码url
    @ConfigProperty("interface.city.location.url")
    public static String CITY_LOCATION_URL;
    
    // 获取新房PC导购id转换为wap导购id
    @ConfigProperty("interface.xinfang.daogou_pc2mb.url")
    public static String XINFANG_DAOGOU_PC2MB_URL;
    
    // 图片验证码url
    @ConfigProperty("interface.capture.url")
    public static String CAPTURE_URL;
    
    // 图片验证码验证url
    @ConfigProperty("interface.capture.verifyurl")
    public static String CAPTURE_VERIFY_URL;
    
    // 焦点passpord接口获取用户信息
    @ConfigProperty("interface.focus_password.get_user.url")
    public static String FOCUS_PQSSWORD_GET_USER_URL;
    
    // 焦点passpord接口日志记录
    @ConfigProperty("interface.focus_password.log_record.url")
    public static String FOCUS_PQSSWORD_LOG_RECORD_URL;
    
  //新房API V4提供的添加收藏接口
    @ConfigProperty("interface.xinfang.fav_add.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_FAV_ADD_URL;
    
    
    //本地库收藏
    @ConfigProperty("interface.xinfang.fav_add_local.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_FAV_ADD_LOCAL_URL;
    
    //新房API V4提供的取消收藏接口
    @ConfigProperty("interface.xinfang.fav_delete.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_FAV_DELETE_URL;
    
    //本地库取消
    @ConfigProperty("interface.xinfang.fav_delete_local.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_FAV_DELETE_LOCAL_URL;
    
    //新房API V4提供的获取收藏列表接口
    @ConfigProperty("interface.xinfang.fav_list.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_FAV_LIST_URL;
    
    //新房API V4提供的获取业务是否被收藏的接口
    @ConfigProperty("interface.xinfang.is_fav.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_IS_FAV_URL;
    
    //新房API V4提供的添加有用接口
    @ConfigProperty("interface.xinfang.useful_add.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_USEFUL_ADD_URL;
    
    //新房API V4提供的取消有用接口
    @ConfigProperty("interface.xinfang.useful_delete.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_USEFUL_DELETE_URL;
    
    //新房API V4提供的获取业务是否有用的接口
    @ConfigProperty("interface.xinfang.is_useful.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_IS_USEFUL_URL;
    
    //新房API V4提供的获取添加楼盘关注的接口
    @ConfigProperty("interface.xinfang.house_fav_add.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_HOUSE_FAV_ADD_URL;
    
    //新房API V4提供的获取取消楼盘关注的接口
    @ConfigProperty("interface.xinfang.house_fav_delete.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_HOUSE_FAV_DELETE_URL;
    
    //新房API V4提供的获取查询楼盘关注列表的接口
    @ConfigProperty("interface.xinfang.house_fav_list.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_HOUSE_FAV_LIST_URL;
    
    //新房API V4提供的获取添加楼盘关注的接口
    @ConfigProperty("interface.xinfang.question_add.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_QUESTION_ADD_URL;
    
    //新房API V4提供的获取添加楼盘关注的接口
    @ConfigProperty("interface.xinfang.question_delete.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_QUESTION_DELETE_URL;
    
    //新房API V4提供的获取添加楼盘关注的接口
    @ConfigProperty("interface.xinfang.question_list.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_QUESTION_LIST_URL;
    
    //新房API V4提供的获取城市相关爬房团信息
    @ConfigProperty("interface.xinfang.pafangtuan_city_info.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_PAFANGTUAN_CITY_INFO_URL;
    
    //新房API V4提供的获取爬房团列表
    @ConfigProperty("interface.xinfang.pafangtuan_list.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_PAFANGTUAN_LIST_URL;
    
    //新房API V4提供的获取爬房团信息
    @ConfigProperty("interface.xinfang.pafangtuan_info.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_PAFANGTUAN_INFO_URL;

    //新房API V4提供的爬房团活动流程声明和责任声明
    @ConfigProperty("interface.xinfang.pafangtuan_declaration.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_PAFANGTUAN_DECLARATION_URL;
    
    //新房API V4提供的小贴士
    @ConfigProperty("interface.xinfang.pafangtuan_tips.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_PAFANGTUAN_TIPS_URL;
    
    //新房API V4提供的获取爬房团报名
    @ConfigProperty("interface.xinfang.pafangtuan_sign.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_PAFANGTUAN_SIGN_URL;


    //新房API V4提供的获取爬房团报名，不含UID
    @ConfigProperty("interface.xinfang.pafangtuan_sign_nouid.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_PAFANGTUAN_SIGN_NOUID_URL;
    
    //新房API V4提供的根据groupId获取导购
    @ConfigProperty("interface.xinfang.daogou_by_groupId.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_DAOGOU_BY_GROUPID_URL;
    
    //新房API V4提供的根据groupId获取导购
    @ConfigProperty("interface.xinfang.daogou_pc_by_groupId.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_DAOGOU_PC_BY_GROUPID_URL;
    
    //新房API v4提供的城市导购列表
    @ConfigProperty("interface.xinfang.daogoulist_by_cityId.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_DAOGOULIST_BY_CITYID_URL;
    
    //新房API v4提供的城市导购详细信息
    @ConfigProperty("interface.xinfang.daogou_detail.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_DAOGOU_DETAIL_URL;
    
    //新房API v4 editor comment interface url
    @ConfigProperty("interface.xinfang.editor_comment.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_EDITOR_COMMENT_URL;

    // 获取碎片化管理的首页焦点图列表
    @ConfigProperty("interface.xinfang.home_jiaodiantu.url")
    @AuthKey(type = AuthKeyType.XINFANG_API_V4, key="secretkey.xinfang_api_v4")
    public static String XIN_FANG_API_V4_HOME_JIAODIANTU_URL;
    
    //团购API 团购列表
    @ConfigProperty("interface.tuangou.tuangou_list.url")
    public static String TUANGOULIST_BY_CITYID_URL;

    //团购API 团购报名
    @AuthKey(type = AuthKeyType.PC_SIGN_AT_URL, key="secretkey.tuangou")
    @ConfigProperty("interface.tuangou.tuangou_register.url")
    public static String TUANGOU_REGISTER_URL;

    /***************************** 接口END ***************************************/

    /**************************** 字符串常量BEGIN ********************************/

    /** 城市对应新房二手房状态 */
    public static Map<String, Integer> cityStatusMap;

    public static final String PAGE_NO = "pageNo";

    public static final String PAGE_SIZE = "pageSize";

    public static final String CITY_ID = "cityid";

    public static final String GROUP_ID = "groupId";

    public static final String BUILD_ID = "buildid";

    public static final String HASNEXT = "hasNext";

    public static final String CHANNEL_ID = "channelId";

    public static final String LIMIT = "limit";

    public static final String ERRORCODE = "errorCode";

    public static final String DATA = "data";

    public static final String DEFUALT_CITY = "全国";

    public static final String DEFUALT_ANONYMOUS_USER = "匿名";
    
    public static final String UPLOAD_AUTH = "jf9e3ynvnvb83295jfknkgjkfjdsJie";
    
    public static final String FOCUS_USER = "focus_user";
    
    public static final String FOCUS_USER_NAME = "username";

    /**************************** 字符串常量END **********************************/

    /**************************** 城市常量END **********************************/

    // 沈阳城市ID
    public static final int CITYID_SY = 999;

    public static final String CITYNAME_SY = "沈阳";

    public static final String CITYPINYIN_SY = "sy";

    // 濮阳城市ID
    public static final int CITYID_PY = 888;

    public static final String CITYNAME_PY = "濮阳";

    public static final String CITYPINYIN_PY = "puyang";

    /**************************** 城市常量END **********************************/

    // 搜索条件
    public static int CONDITION_DISTRICTS = 1;

    public static int CONDITION_TYPES = 2;

    public static int CONDITION_PRICES = 3;

    public static int CONDITION_AREAS = 4;

    public static int CONDITION_LINES = 5;

    public static int CONDITION_RECOMENDS = 6;

    // 搜索条件索引
    public static int CONDITION_DISTRICTS_INDEX = 3;

    public static int CONDITION_TYPES_INDEX = 4;

    public static int CONDITON_PRICES_INDEX = 0;

    public static int CONDITION_AREAS_INDEX = 1;

    public static int CONDITION_LINES_INDEX = 5;

    public static int CONDITIO_RECONENTDS_INDEX = 2;

    // 搜索类型
    public static int SEARCH_TYPE_XINFANG = 1;

    public static int SEARCH_TYPE_ESF = 2;
    
    // 个城市导购存在状态
    public static int NO_PROPOSE = 0;
    
    public static int HAS_PROPOSE = 1;

    // 搜索条件名称
    public static String CONDITION_DISTRICTS_NAME = "热点板块";

    public static String CONDITION_TYPES_NAME = "类型";

    public static String CONDITION_PRICES_NAME = "价格";

    public static String CONDITION_AREAS_NAME = "区域";

    public static String CONDITION_LINES_NAME = "轨道交通";

    public static String CONDITION_RECOMENDS_NAME = "特色楼盘";

    // PHP func的名称
    public static final String PHP_FUNC_HOUSE_NEWS_LIST = "getinfolist";

    // 默认空白列表页背景图片地址
    public static String DEFAULT_PIC_URL = "http://a1.itc.cn/sceapp/focus_static/wap/images/house_img_bg.png";

    // 默认空白详细页图片地址
    public static String DEFAULT_DETAIL_PIC_URL = "http://a1.itc.cn/sceapp/focus_static/wap/images/loupan_default.jpg";

    // 刷新全部缓存访问地址的认证字串
    public static final String FLUSH_CACHE_AUTH = "cef75f381b87b191bfe48dfd8b76ca54";

    // 公共问答账号ID
    public static final int QA_PUBLIC_UID = 1010;

    // 公共问答账号NAME
    public static final String QA_PUBLIC_USER_NAME = "搜狐购房小助手";

    // 城市选择列表，默认城市
    public static final String DEFAULT_SELECT_CITY = "北京";

    // 搜索历史COOKIE KEY
    public static final String COOKIE_SEARCH_HISTORY = "focus_wap_searchHistory";
    
    // redis KEY startwith
    public static final String FOCUS_WAP_REDIS = "focus_wap_redis";

    // 公益订单号
    public static final String GONGYI_ORDER_ID_REDIS = "wap:gongyi:orderid";

    // 首页打折楼盘,最新开盘,小户型模块数据默认显示查询页码
    public static final int HOME_BUILDING_LIST_PAGENO = 1;

    // 首页打折楼盘,最新开盘,小户型模块数据默认显示条数
    public static final int HOME_BUILDING_LIST_PAGESIZE = 3;

    // 站点url
    public static final String DOMAIL = "m.focus.cn/";
    
    // ppsmu cookie 时间
    public static int WAP_PPSMU_TIME = 15*24*3600;

    // 日志线程数
    @ConfigProperty("log_thread_count")
    public static int LOG_THREAD_COUNT;

    // 日志区分test和product环境
    @ConfigProperty("log_open")
    public static Boolean LOG_OPEN;

    /**************************** 导购图片width常量BEGIN ******************************/
    public static class Focus_img_width {
        public static Map<String, String> widthMap = new HashMap<String, String>();
        static{
            widthMap.put(DAOGOULIST_FOCUS_IMG_WIDTH+"pad", "600");
            widthMap.put(DAOGOULIST_FOCUS_IMG_WIDTH+"phone", "600");
            widthMap.put(INDEX_FOCUS_IMG_WIDTH+"pad", "360");
            widthMap.put(INDEX_FOCUS_IMG_WIDTH+"phone", "240");
        }
    }
    public static final String DAOGOULIST_FOCUS_IMG_WIDTH = "daogou_list_";
       
    public static final String INDEX_FOCUS_IMG_WIDTH = "index_list_";
    
    public static final String FOCUS_IMG_SET = "c_zoom";
    
    /**************************** 导购图片width常量END ********************************/
    
    //密钥map
    public static Map<String, SecretkeyPair> secretKeyMap = new HashMap<String, SecretkeyPair>();
    
    public static Map<String, AuthKeyPair> authKeyMap = new HashMap<String, AuthKeyPair>();
    
    
    /**************************** SCE常量BEGIN ******************************/
    @ConfigProperty("sce.appid")
    public static String SCE_APP_ID;
    
    @ConfigProperty("sce.secret")
    public static String SCE_APP_SECRET;
    
    public static final String SCE_API_ENDPOINT = "http://sceapi.apps.sohuno.com/";
    
    /**************************** SCE常量END ********************************/
    
    /**************************** 制作SITEMAP使用的常量BEGIN ********************************/
    public static String SITE_MAP_HOST_NAME = "m.focus.cn";
    /**************************** 制作SITEMAP使用的常量END ********************************/
    
    /**************************** PAPPORT PROXY常量BEGIN ******************************/
    // 焦点用户key
    public static final String FOCUS_USER_KEY = "WAP_FOCUS_USER_KEY";
    
    // 焦点用户存储时间
    public static final int FOCUS_USER_SAVE_TIME = -1;
    
    // 上传白名单 KEY
    public static final String UPLOAD_WHITELIST_KEY = "wap_upload_whitelist_key";
    
    // 自定义页面缓存key
    public static final String FILE_SAVE_KEY = "wap_file_save_key";
    
    //焦点在SOHU-PASSPORT的appid
    public static final int PASSPORT_APPID=1028;
    
    //焦点在SOHU-PASSPORT的key
    public static final String PASSPORT_KEY = "T9?Z~q2Z1UsqsX`3[Mbn>rZC-46=8G";

    //focus_wap在焦点的常量
    public static final int PASSPORT_FOCUS_APPID=1007;
    
    //focus_wap在焦点的key
    public static final String PASSPORT_FOCUS_KEY = "09bf0f33153bbe16411095339126863b";
    
    //焦点通行证Sign校验key
    public static final String PASSPORT_FOCUS_SIGN_KEY = "auTJ8964./Ljeg.19<-F1lKFqyn=1u";
    
    //焦点自住房页面key
    public static final String ZZF_PAGE_KEY = "wap_zzf_home_key";
    
    //焦点文件存储时间
    public static final int FILE_SAVE_TIME = -1;
    
    public static final String ZZF_PAGE_PATH_KEY = "wap_zzf_home_path_key";
    
    public static final String PASSPORT_CHECK_USER_URL = "http://internal.passport.sohu.com/interface/checkuser";
    
    public static final String PASSPORT_CHECK_BIND_URL = "http://internal.passport.sohu.com/interface/wapgetuserid";
    
    public static final String PASSPORT_THIRD_LOGIN_URL = "http://passport.sohu.com/openlogin/request.action?appid="+PASSPORT_APPID+"&provider={provider}&ru=${ru}&type=wap";
    
    public static final String PASSPORT_EMAIL_CODE_URL = "http://internal.passport.sohu.com/interface/sendemailcaptcha";
    
    public static final String PASSPORT_MOBILE_CODE_URL = "http://internal.passport.sohu.com/interface/sendcaptcha";
    
    public static final String PASSPORT_LOGIN_URL = "http://internal.passport.sohu.com/interface/wap_auth.jsp";
    
    public static final String PASSPORT_REGISTER_FROM_MOBILE_URL = "http://internal.passport.sohu.com/interface/register/mobilecaptcha";
    
    public static final String PASSPORT_REGISTER_FROM_EMAIL_URL = "http://internal.passport.sohu.com/interface/reguser";
    
    // sohu-passport 昵称验证接口
    public static final String NICKNAME_CHECK_URL = "http://internal.passport.sohu.com/interface/checkuniqname";
    
    public static final String UPDATE_NICKNAME_URL = "http://internal.passport.sohu.com/interface/updateuser";
    public static final String GET_USER_INFO = "http://internal.passport.sohu.com/interface/getuserinfo";   
    public static final String PHONE_LOGINCODE_SENDURL = "http://internal.passport.sohu.com/interface/getMobCode";
    public static final String PHONE_LOGIN_WITH_CODE_URL = "http://passport.sohu.com/act/authMobCode";
    // 自动生成的昵称前缀
    public static final String NICKNAME_PREFIX = "焦点网友";
    /**
     * 注册昵称状态
     */
    public static final int CHECK_NICKNAME_NEVER_REGISTER = 0;
    
    public static final int CHECK_NICKNAME_USED = 3;
    
    public static final int CHECK_NICKNAME_ILLEGAL_NICKNAME = 4;
    
    public static final int CHECK_NICKNAME_BAD_FORMAT = 5;
    
    public static final int CHECK_NICKNAME_NULL = 7;
    
    /**************************** PAPPORT PROXY常量END ********************************/
    
    public static class Xinwen{
        public static Map<String, SearchType> xinwenMap = new HashMap<String, SearchType>();
        public static Map<Integer, String> localNameMap = new HashMap<Integer, String>();
        public static Map<Integer, Integer> localIdMap = new HashMap<Integer, Integer>();
        static{
            localNameMap.put(83, "土地");
            localNameMap.put(1, "土地");
            localIdMap.put(83, 454);
            localIdMap.put(1, 454);
            
            xinwenMap.put("新闻--风云对话",SearchType.TOP);
            xinwenMap.put("新闻--首页",SearchType.TOP);
            xinwenMap.put("新闻--区域报告",SearchType.TOP);
            xinwenMap.put("新闻--焦点关注",SearchType.TOP);
            xinwenMap.put("新闻--滚动",SearchType.TOP);
            xinwenMap.put("新闻--热点",SearchType.TOP);
            xinwenMap.put("新闻--新盘路演",SearchType.TOP);
            xinwenMap.put("新闻--一周新闻排行",SearchType.TOP);
            xinwenMap.put("新闻--一月新闻排行",SearchType.TOP);
            xinwenMap.put("新闻--一年新闻排行",SearchType.TOP);
            xinwenMap.put("新闻--图片新闻",SearchType.TOP);
            xinwenMap.put("新闻--头条",SearchType.TOP);
            xinwenMap.put("新闻--各种专题",SearchType.TOP);
            xinwenMap.put("企业--首页",SearchType.TOP);
            xinwenMap.put("企业--一年新闻排行",SearchType.TOP);
            xinwenMap.put("企业--一月新闻排行",SearchType.TOP);
            xinwenMap.put("企业--一周新闻排行",SearchType.TOP);
            xinwenMap.put("企业--头条",SearchType.TOP);
            xinwenMap.put("企业--滚动",SearchType.TOP);
            
            xinwenMap.put("新闻--市场动态",SearchType.MARKET);
            
            xinwenMap.put("新闻--本地新闻",SearchType.LOCAL);
            
            xinwenMap.put("新闻--宏观政策",SearchType.POLICY);
            
            xinwenMap.put("新闻--地产评论",SearchType.POINT);
            
            xinwenMap.put("新闻--土地市场",SearchType.MORE);
            xinwenMap.put("新闻--房地产金融",SearchType.MORE);
            xinwenMap.put("新闻--建筑建材",SearchType.MORE);
            xinwenMap.put("新闻--展会活动",SearchType.MORE);
            xinwenMap.put("企业--精英语录",SearchType.MORE);
            xinwenMap.put("企业--各种专题",SearchType.MORE);
            xinwenMap.put("企业--企业动态",SearchType.MORE);
            xinwenMap.put("企业--社会责任",SearchType.MORE);
            xinwenMap.put("企业--股票财报",SearchType.MORE);
            xinwenMap.put("购房资讯--首页",SearchType.MORE);
            xinwenMap.put("购房资讯--优质楼盘最新动态",SearchType.MORE);
            xinwenMap.put("购房资讯--楼盘动态",SearchType.MORE);
            xinwenMap.put("写字楼--首页",SearchType.MORE);
            xinwenMap.put("写字楼--一周新闻排行",SearchType.MORE);
            xinwenMap.put("写字楼--一月新闻排行",SearchType.MORE);
            xinwenMap.put("写字楼--一年新闻排行",SearchType.MORE);
            xinwenMap.put("写字楼--滚动新闻",SearchType.MORE);
            xinwenMap.put("写字楼--新闻",SearchType.MORE);
            xinwenMap.put("写字楼--专题列表",SearchType.MORE);
            xinwenMap.put("写字楼--各种专题",SearchType.MORE);
            xinwenMap.put("写字楼--高端访谈",SearchType.MORE);
            xinwenMap.put("写字楼--专家分析",SearchType.MORE);
            xinwenMap.put("写字楼--投资指南",SearchType.MORE);
            xinwenMap.put("写字楼--项目动态",SearchType.MORE);
            xinwenMap.put("写字楼--市场动态",SearchType.MORE);
            xinwenMap.put("写字楼空间--写字楼风水",SearchType.MORE);
            xinwenMap.put("写字楼空间--写字楼装修",SearchType.MORE);
            xinwenMap.put("写字楼空间--世界名楼",SearchType.MORE);           
            xinwenMap.put("商铺--首页",SearchType.MORE);
            xinwenMap.put("商铺--滚动",SearchType.MORE);
            xinwenMap.put("商铺--市场动态",SearchType.MORE);
            xinwenMap.put("商铺--专家分析",SearchType.MORE);
            xinwenMap.put("商铺--项目动态",SearchType.MORE);
            xinwenMap.put("商铺--商铺知识",SearchType.MORE);
            
            xinwenMap.put("别墅--首页",SearchType.MORE);
            xinwenMap.put("别墅--头条",SearchType.MORE);
            xinwenMap.put("别墅--图片新闻",SearchType.MORE);
            xinwenMap.put("别墅--别墅图库",SearchType.MORE);
            xinwenMap.put("别墅--新闻",SearchType.MORE);
            xinwenMap.put("别墅资讯--别墅鉴赏",SearchType.MORE);
            xinwenMap.put("别墅--品质生活",SearchType.MORE);
            xinwenMap.put("别墅--别墅业内",SearchType.MORE);
            xinwenMap.put("别墅--各种栏目",SearchType.MORE);
            xinwenMap.put("别墅--各种专题",SearchType.MORE);
            xinwenMap.put("别墅--各种新闻",SearchType.MORE);
            
            xinwenMap.put("产业地产--首页",SearchType.MORE);
            xinwenMap.put("产业地产--滚动",SearchType.MORE);
            xinwenMap.put("产业地产--专题汇总",SearchType.MORE);
            xinwenMap.put("产业地产--产业新区",SearchType.MORE);
            xinwenMap.put("产业地产--项目动态",SearchType.MORE);
            xinwenMap.put("产业地产--各种栏目",SearchType.MORE);
            xinwenMap.put("产业地产--各种新闻",SearchType.MORE);
            xinwenMap.put("产业地产--各种主题",SearchType.MORE);
            
            xinwenMap.put("商业地产--首页",SearchType.MORE);
            xinwenMap.put("商业地产--滚动",SearchType.MORE);
            xinwenMap.put("商业地产--焦点原创",SearchType.MORE);
            xinwenMap.put("商业地产--行业政策",SearchType.MORE);
            xinwenMap.put("商业地产--地产金融",SearchType.MORE);
            xinwenMap.put("商业地产--企业动态",SearchType.MORE);
            xinwenMap.put("商业地产--土地信息",SearchType.MORE);
            xinwenMap.put("商业地产--展会活动",SearchType.MORE);
            xinwenMap.put("商业地产--楼盘动态",SearchType.MORE);
            
            xinwenMap.put("购物中心--首页",SearchType.MORE);
            xinwenMap.put("购物中心--行业资讯",SearchType.MORE);
            xinwenMap.put("购物中心--市场动态",SearchType.MORE);
            xinwenMap.put("购物中心--品牌扩展",SearchType.MORE);
            xinwenMap.put("购物中心--招商运营",SearchType.MORE);
        }
    }
}
