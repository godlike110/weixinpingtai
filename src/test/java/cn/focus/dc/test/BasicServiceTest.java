package cn.focus.dc.test;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.config.AuthKeyPair;
import cn.focus.dc.focuswap.config.AuthKeyType;

import org.junit.Before;

public class BasicServiceTest {

    @Before
    public void setup(){
        AppConstants.XIN_FANG_API_V4_FAV_ADD_URL = "http://10.10.90.156/xinfang_dev/v4/wap/fav/add?itemId={itemId}&srcUid={srcUid}&type={type}&uid={uid}";
        AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/fav/add", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_FAV_DELETE_URL = "http://10.10.90.156/xinfang_dev/v4/wap/fav/delete?itemId={itemId}&type={type}&uid={uid}";
        AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/fav/delete", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_FAV_LIST_URL = "http://10.10.90.156/xinfang_dev/v4/wap/fav/list?type={type}&uid={uid}";
        AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/fav/list", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_IS_FAV_URL = "http://10.10.90.156/xinfang_dev/v4/wap/fav/isfav?itemId={itemId}&type={type}&uid={uid}";
        AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/fav/isfav", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_USEFUL_ADD_URL = "http://10.10.90.156/xinfang_dev/v4/wap/useful/add?itemId={itemId}&srcUid={srcUid}&type={type}&uid={uid}";
        AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/useful/add", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_USEFUL_DELETE_URL = "http://10.10.90.156/xinfang_dev/v4/wap/useful/delete?itemId={itemId}&type={type}&uid={uid}";
        AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/useful/delete", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_IS_USEFUL_URL = "http://10.10.90.156/xinfang_dev/v4/wap/useful/isuseful?itemId={itemId}&type={type}&uid={uid}";
        AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/useful/isuseful", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_HOUSE_FAV_ADD_URL = "http://10.10.90.156/xinfang_dev/v4/wap/house/fav/add?groupId={groupId}&uid={uid}";
        AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/house/fav/add", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_HOUSE_FAV_DELETE_URL = "http://10.10.90.156/xinfang_dev/v4/wap/house/fav/delete?groupIds={groupIds}&uid={uid}";
        AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/house/fav/delete", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_HOUSE_FAV_LIST_URL = "http://10.10.90.156/xinfang_dev/v4/wap/house/fav/list?pageNo={pageNo}&pageSize={pageSize}&uid={uid}";
        AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/house/fav/list", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_QUESTION_ADD_URL = "http://10.10.90.156/xinfang_dev/v4/wap/question/add?groupId={groupId}&isAnonymnous={isAnonymnous}&question={question}&uid={uid}";
        AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/question/add", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_QUESTION_DELETE_URL = "http://10.10.90.156/xinfang_dev/v4/wap/question/delete?questionIds={questionIds}&uid={uid}";
        AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/question/delete", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_QUESTION_LIST_URL = "http://10.10.90.156/xinfang_dev/v4/wap/question/user?pageNo={pageNo}&pageSize={pageSize}&uid={uid}";
        AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/question/user", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_PAFANGTUAN_CITY_INFO_URL = "http://10.10.90.156/xinfang_dev/v4/wap/houselooking/cityinfo?cityId={cityId}";
        AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/houselooking/cityinfo", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        //AppConstants.XIN_FANG_API_V4_PAFANGTUAN_LIST_URL = "http://10.10.90.156/xinfang_dev/v4/wap/houselooking/list?cityId={cityId}&endDate={endDate}&pageNo={pageNo}&pageSize={pageSize}";
        //AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/houselooking/list", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_PAFANGTUAN_LIST_URL = "http://focus-xinfang-app.sohusce.com/v4/wap/houselooking/list?cityId={cityId}&endDate={endDate}&pageNo={pageNo}&pageSize={pageSize}";
        AppConstants.authKeyMap.put("focus-xinfang-app.sohusce.com/v4/wap/houselooking/list", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        //AppConstants.XIN_FANG_API_V4_EDITOR_COMMENT_URL = "http://focus-xinfang-app.sohusce.com/wap/editorrecommend/list?groupId={groupId}";
        //AppConstants.authKeyMap.put("focus-xinfang-app.sohusce.com/wap/editorrecommend/list", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_PAFANGTUAN_INFO_URL = "http://10.10.90.156/xinfang_dev/v4/wap/houselooking/detail?cityId={cityId}&lineId={lineId}";
        AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/houselooking/detail", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_PAFANGTUAN_SIGN_URL = "http://10.10.90.156/xinfang_dev/v4/wap/houselooking/signup?cityId={cityId}&lineId={lineId}&mobile={mobile}&name={name}";
        AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/houselooking/signup", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_DAOGOU_BY_GROUPID_URL = "http://10.10.90.156/xinfang_dev/v4/wap/buildpropose/list?groupId={groupId}&limit={limit}";
        AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/buildpropose/list", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_DAOGOULIST_BY_CITYID_URL = "http://10.10.90.156/xinfang_dev/v4/wap/buildpropose/pcdetail?cityId={cityId}&proposeId={proposeId}";
        AppConstants.authKeyMap.put("10.10.90.156/xinfang_dev/v4/wap/buildpropose/pcdetail", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
   
        AppConstants.XIN_FANG_API_V4_EDITOR_COMMENT_URL = "http://10.1.77.2:8080/wap/editorrecommend/list?groupId={groupId}";
        AppConstants.authKeyMap.put("10.1.77.2:8080/wap/editorrecommend/list", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
    }
}
