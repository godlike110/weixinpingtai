package cn.focus.dc.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.config.AuthKeyPair;
import cn.focus.dc.focuswap.config.AuthKeyType;
import cn.focus.dc.focuswap.service.XinFangCommonApiService;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.EditorComment;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.FavBuildingModel;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.FavQuestionModel;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.OperateType;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.Pafangtuan;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.PftCityInfo;
import cn.focus.dc.focuswap.service.XinFangCommonApiService.XinfangDaogou;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
/**
 * 测试使用的用户uid有：12345、23456
 * @author rogantian
 * @date 2014-4-22
 * @email rogantianwz@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/resources/applicationContext-api.xml"})
public class XinFangCommonApiServiceTest {

    @Autowired
    private XinFangCommonApiService apiService;
    
    @Before
    public void setup(){
        AppConstants.XIN_FANG_API_V4_FAV_ADD_URL = "http://xinfang-local-test.apps.sohuno.com/v4/wap/fav/pcadd?itemId={itemId}&srcUid={srcUid}&type={type}&uid={uid}";
        AppConstants.authKeyMap.put("xinfang-local-test.apps.sohuno.com/v4/wap/fav/pcadd", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_FAV_ADD_LOCAL_URL="http://xinfang-local-test.apps.sohuno.com/v4/wap/fav/add?itemId={itemId}&srcUid={srcUid}&type={type}&uid={uid}";
        AppConstants.authKeyMap.put("xinfang-local-test.apps.sohuno.com/v4/wap/fav/add", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        
        AppConstants.XIN_FANG_API_V4_FAV_DELETE_URL = "http://xinfang-local-test.apps.sohuno.com/v4/wap/fav/pcdelete?itemId={itemId}&type={type}&uid={uid}";
        AppConstants.authKeyMap.put("xinfang-local-test.apps.sohuno.com/v4/wap/fav/pcdelete", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        
        AppConstants.XIN_FANG_API_V4_FAV_DELETE_LOCAL_URL = "http://xinfang-local-test.apps.sohuno.com/v4/wap/fav/delete?itemId={itemId}&type={type}&uid={uid}";
        AppConstants.authKeyMap.put("xinfang-local-test.apps.sohuno.com/v4/wap/fav/delete", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        
        AppConstants.XIN_FANG_API_V4_FAV_LIST_URL = "http://xinfang-local-test.apps.sohuno.com/v4/wap/fav/pclist?type={type}&uid={uid}";
        AppConstants.authKeyMap.put("xinfang-local-test.apps.sohuno.com/v4/wap/fav/pclist", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
        AppConstants.XIN_FANG_API_V4_IS_FAV_URL = "http://xinfang-local-test.apps.sohuno.com/v4/wap/fav/isfav?itemId={itemId}&type={type}&uid={uid}";
        AppConstants.authKeyMap.put("xinfang-local-test.apps.sohuno.com/v4/wap/fav/isfav", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
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
        
        AppConstants.XIN_FANG_API_V4_QUESTION_ADD_URL = "http://xinfang-local-test.apps.sohuno.com/v4/wap/question/add?buildId={buildId}&cityId={cityId}&groupId={groupId}&isAnonymous={isAnonymous}&question={question}&uid={uid}";
        AppConstants.authKeyMap.put("xinfang-local-test.apps.sohuno.com/v4/wap/question/add", new AuthKeyPair(AuthKeyType.XINFANG_API_V4, "09dd6ff1af8c4ca29a85b8cd158a98df"));
        
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
    
    /**
     * 添加业务收藏
     */
    @Test
    @Ignore
    public void testAddFav() {
//        boolean ret = apiService.addFav(OperateType.QUESTION, 89, 12345, 235);
//        
//        Assert.assertTrue(ret);

        //导购收藏
//    	boolean ret=apiService.addFav(OperateType.BUILDING_PROPOSE,89, 12345, 235);
//        Assert.assertTrue(ret);
    }
    
    /**
     * 取消业务收藏
     */
    @Test
    @Ignore
    public void testDeleteFav() {
//        boolean ret = apiService.deleteFav(OperateType.QUESTION, 90, 12345);
//        Assert.assertTrue(ret);
        
//        boolean ret = apiService.deleteFav(OperateType.BUILDING_PROPOSE, 90, 12345);
//        Assert.assertTrue(ret);
    }
    
    /**
     * 业务收藏列表
     */
    @Test
    @Ignore
    public void testFavList()
    {
    	Object ret = apiService.favList(OperateType.BUILDING_PROPOSE,12345);
    	Assert.assertNull(ret);
    }
    
    
    /**
     * 业务是否收藏
     */
    @Test
    @Ignore
    public void testIsFav() {
    	
    	boolean add = apiService.addFav(OperateType.DAOGOU_FROM_PC, 359,12329300, 282);
        Assert.assertTrue(add);
        
        boolean delete = apiService.deleteFav(OperateType.DAOGOU_FROM_PC, 359, 12329300);
        Assert.assertTrue(delete);
        
        add = apiService.addFav(OperateType.DAOGOU_FROM_PC,359, 12329300, 282);
        Assert.assertTrue(add);
        
        JSONObject object = apiService.favList(OperateType.DAOGOU_FROM_PC,12329300);
    	Assert.assertNotNull(object);

        
        boolean ret = apiService.isFav(OperateType.DAOGOU_FROM_PC, 359, 12329300);
        Assert.assertTrue(ret);
        
        delete = apiService.deleteFav(OperateType.DAOGOU_FROM_PC, 359, 12329300);
        Assert.assertTrue(delete);

        object = apiService.favList(OperateType.DAOGOU_FROM_PC,12329300);
        Assert.assertNotNull(object);
        
        boolean ret2 = apiService.isFav(OperateType.DAOGOU_FROM_PC, 359, 12329300);
        Assert.assertFalse(ret2);
        

        //本地
        add = apiService.addFavLocal(OperateType.BUILDING_PROPOSE, 13,12329300, 335);
        Assert.assertTrue(add);
        
        delete = apiService.deleteFavLocal(OperateType.BUILDING_PROPOSE, 13, 12329300);
        Assert.assertTrue(delete);
        
        add = apiService.addFavLocal(OperateType.BUILDING_PROPOSE,13, 12329300, 335);
        Assert.assertTrue(add);
        
        object = apiService.favList(OperateType.BUILDING_PROPOSE,12329300);
    	Assert.assertNotNull(object);

        
        ret = apiService.isFav(OperateType.BUILDING_PROPOSE, 13, 12329300);
        Assert.assertTrue(ret);
        
        delete = apiService.deleteFavLocal(OperateType.BUILDING_PROPOSE, 13, 12329300);
        Assert.assertTrue(delete);

        object = apiService.favList(OperateType.BUILDING_PROPOSE,12329300);
        Assert.assertNotNull(object);
        
        ret2 = apiService.isFav(OperateType.BUILDING_PROPOSE, 13, 12329300);
        Assert.assertFalse(ret2);
        
        
    }
    
    /**
     * 添加业务有用
     */
    @Test
    @Ignore
    public void testAddUseful() {
        boolean ret = apiService.addUseful(OperateType.QUESTION, 99, 12345, 235);
        Assert.assertTrue(ret);
    }
    
    /**
     * 取消业务有用
     */
    @Test
    @Ignore
    public void testDeleteUseful() {
        boolean ret = apiService.deleteUseful(OperateType.QUESTION, 99, 12345);
        Assert.assertTrue(ret);
    }
    
    /**
     * 业务是否有用
     */
    @Test
    @Ignore
    public void testIsUserful() {
        boolean add = apiService.addUseful(OperateType.QUESTION, 99, 12345, 235);
        Assert.assertTrue(add);
        boolean ret = apiService.isUseful(OperateType.QUESTION, 99, 12345);
        Assert.assertTrue(ret);
        boolean delete = apiService.deleteUseful(OperateType.QUESTION, 99, 12345);
        Assert.assertTrue(delete);
        boolean ret2 = apiService.isUseful(OperateType.QUESTION, 99, 12345);
        Assert.assertFalse(ret2);
        
    }
    
    /**
     * 添加楼盘关注
     */
    @Test
    @Ignore
    public void testAddHouseFav() {
        boolean ret = apiService.addHouseFav(12345, 8048);
        Assert.assertTrue(ret);
    }
    
    /**
     * 批量删除楼盘关注
     */
    @Test
    @Ignore
    public void testDeleteHouseFav() {
        //添加一个楼盘关注
        boolean add1 = apiService.addHouseFav(12345, 8048);
        Assert.assertTrue(add1);
        
        //删除一个楼盘关注
        int delete1 = apiService.deleteHouseFav(12345, new Integer[]{8048});
        Assert.assertEquals(0, delete1);
        
        /***************I'm the split line****************/
        
        //添加两个楼盘关注
        boolean add2 = apiService.addHouseFav(12345, 8343);
        Assert.assertTrue(add2);
        boolean add3 = apiService.addHouseFav(12345, 8048);
        Assert.assertTrue(add3);
        
        //删除两个楼盘关注
        int delete2 = apiService.deleteHouseFav(12345, new Integer[]{8048,8343});
        Assert.assertEquals(0, delete2);
        
        /***************I'm the split line****************/
        
        //添加一个楼盘关注
        boolean add4 = apiService.addHouseFav(12345, 8343);
        Assert.assertTrue(add4);
        
        //删除两个楼盘关注，其中一个是关注过的，另一个没有关注过
        int delete3 = apiService.deleteHouseFav(12345, new Integer[]{8048,8343});
        Assert.assertEquals(1, delete3);
        
        /***************I'm the split line****************/
        
        //恢复现场
        //nothing
    }
    
    /**
     * 楼盘关注列表
     */
    @Test
    @Ignore
    public void testHouseFavList() {
        //测试只有一个关注时
        boolean ret = apiService.addHouseFav(12345, 8048);
        Assert.assertTrue(ret);
        
        List<FavBuildingModel> list = apiService.houseFavList(12345, 1, 5);
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals((Integer)8048, list.get(0).getGroupId());
        
        /***************I'm the split line****************/
        
        //测试有两个关注时
        boolean ret2 = apiService.addHouseFav(12345, 8343);
        Assert.assertTrue(ret2);
        
        List<FavBuildingModel> list2 = apiService.houseFavList(12345, 1, 5);
        Assert.assertNotNull(list2);
        Assert.assertEquals(2, list2.size());
        Integer groupIdA = list2.get(0).getGroupId();
        Integer groupIdB = list2.get(1).getGroupId();
        Assert.assertEquals((Integer)(8343-8048), Integer.valueOf(Math.abs(groupIdA - groupIdB)));
        
        /***************I'm the split line****************/
        
        //测试有两个关注，但是pageSize为1的翻页情况
        List<FavBuildingModel> list3 = apiService.houseFavList(12345, 1, 1);
        Assert.assertNotNull(list3);
        Assert.assertEquals(1, list3.size());
        
        List<FavBuildingModel> list4 = apiService.houseFavList(12345, 2, 1);
        Assert.assertNotNull(list4);
        Assert.assertEquals(1, list4.size());
        
        /***************I'm the split line****************/
        
        //恢复现场，删除测试时添加的关注
        int delete = apiService.deleteHouseFav(12345, new Integer[]{8048,8343});
        Assert.assertEquals(0, delete);
    }
    
    /**
     * 添加问答
     */
    @Test
    //@Ignore
    public void testAddQuestion() {
        int ret = apiService.addQuestion(12345, 8048, "这是一个测试3", true, 1, 1);
        System.out.println("questionId:" + ret);
        Assert.assertTrue(ret > 0);
    }
    
    /**
     * 批量删除问答
     */
    @Test
    @Ignore
    public void testDeleteQuestion() {
        //先添加一个
        int add1 = apiService.addQuestion(12345, 8048, "这是一个测试1", false, 1, 1);
        Assert.assertTrue(add1 > 0);
        
        //测试删除一个
        boolean ret1 = apiService.deleteQuestions(12345, new Integer[]{add1});
        Assert.assertTrue(ret1);
        
        /***************I'm the split line****************/
        
        //添加两个
        int add2 = apiService.addQuestion(12345, 8343, "这是一个测试2", false, 1, 1);
        Assert.assertTrue(add2 > 0);
        
        int add3 = apiService.addQuestion(12345, 8343, "这是一个测试3", false, 1, 1);
        Assert.assertTrue(add3 > 0);
        
        /***************I'm the split line****************/
        
        //测试删除两个
        boolean ret2 = apiService.deleteQuestions(12345, new Integer[]{add2, add3});
        Assert.assertTrue(ret2);
        
        //测试删除三个已经被删除了的问答
        boolean ret3 = apiService.deleteQuestions(12345, new Integer[]{add1, add2, add3});
        Assert.assertTrue(ret3);
        
    }
    
    /**
     * 查询问答列表
     */
    @Test
    @Ignore
    public void testQuestionList() {
        //测试只有一个问答时
        int add1 = apiService.addQuestion(23456, 8343, "测试1", false, 1, 1);
        Assert.assertTrue(add1 > 0);
        
        List<FavQuestionModel> list1 = apiService.questionList(23456, 1, 5);
        Assert.assertNotNull(list1);
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals((Integer)8343, list1.get(0).getGroupId());
        
        /***************I'm the split line****************/
        
        //测试有两个问答时
        int add2 = apiService.addQuestion(23456, 8343, "测试2", false, 1, 1);
        Assert.assertTrue(add2 > 0);
        
        List<FavQuestionModel> list2 = apiService.questionList(23456, 1, 5);
        Assert.assertNotNull(list2);
        Assert.assertEquals(2, list2.size());
        Assert.assertEquals((Integer)Math.abs(add1 - add2), Integer.valueOf(Math.abs(list2.get(0).getId() - list2.get(1).getId())));
        
        /***************I'm the split line****************/
        
        //测试有两个问答，但是pageSize为1的翻页情况
        List<FavQuestionModel> list3 = apiService.questionList(23456, 1, 1);
        Assert.assertNotNull(list3);
        Assert.assertEquals(1, list3.size());
        
        List<FavQuestionModel> list4 = apiService.questionList(23456, 2, 1);
        Assert.assertNotNull(list4);
        Assert.assertEquals(1, list4.size());
        
        /***************I'm the split line****************/
        
        //回复现场，删除测试时添加的问答
        boolean delete = apiService.deleteQuestions(12345, new Integer[]{add1, add2});
        Assert.assertTrue(delete);
    }
    
    /**
     * editor comment
     */
    @Test
    @Ignore
    public void testEditorComment() {
    	EditorComment ec = apiService.getEditorComment(2934);
    	
    	Assert.assertNotNull(ec);
    }
    /**
     * 爬房团城市信息
     */
    @Test
    @Ignore
    public void testPafangtuanCityInfo() {
    	 PftCityInfo info = apiService.getPafangtuanCitiInfo(1);
    	 Assert.assertNotNull(info);
    }
    
    /**
     * 爬房团列表
     */
    @Test
    @Ignore
    public void testPafangTuanList() {
    	Date date = new Date();
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	c.add(c.MONTH, 2);
    	
    	List<Pafangtuan> list = apiService.getPftList(1, 50, 0,c.getTime().getTime()/1000);
    	Collections.sort(list);
    	for(Pafangtuan pft:list) {
    		System.out.println(pft.getActiveDate());
    	}
    	Assert.assertNotNull(list);
    	
    }
    
    /**
     * 爬房团信息
     */
    @Test
    @Ignore
    public void testPafangTuanInfo() {
    	Pafangtuan info = apiService.getPftInfo(1, 12);
    	Assert.assertNotNull(info);
    }
    
    @Test
    @Ignore
    public void testPafangtuanSign() {
//    	boolean result = apiService.pftSign(1, 1,41, "13021042455", "小黄");
//    	Assert.assertTrue(result);
    }
    
    @Test
    @Ignore
    public void testDaogouByGroupId() {
    	List<XinfangDaogou> list = apiService.getDaogouByGroupid(180830, 10);
    	Assert.assertNotNull(list);
    }
    
/**
 * 测试首页爬房团数据相关    
 * @param cityId
 * @return
 */
/*    @Test
    @Ignore
    public void getPFTList() {
        
        Map<String,Integer> pftMap = new HashMap<String,Integer>();
        Map<String,List> pftListMap = new HashMap<String,List>();
        try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String now = sdf.format(date);
        long nowtime = Long.parseLong(now);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(c.MONTH, 2);
        long time = c.getTime().getTime()/1000;
        Date monthtime = new Date(time);
        List<Pafangtuan> list = apiService.getPftList(1, 50, 0, time);
        Assert.assertNotNull(list);
        Collections.sort(list);
        long tmptime = 0l;
        boolean flag = false;
        int count = 0;
        List<Pafangtuan> pftList = new ArrayList<Pafangtuan>();
        long activeDateLong = Long.valueOf(pft.getActiveDate());
        for(Pafangtuan pft:list) {
            if(pft.getActiveDate()<nowtime) {
                continue;
            } else {
                if(!flag && count <2) {
                flag = true;
                tmptime = pft.getActiveDate();
                pftList.add(pft);
                } else {
                    if(pft.getActiveDate()==tmptime) {
                        pftList.add(pft);
                    } else {
                        pftMap.put("firtsize", pftList.size());
                        flag=false;
                        count ++;
                    }
                }
            }
        }
        pftListMap.put("one",pftList.subList(0, pftMap.get("firtsize")-1));
        pftListMap.put("two", pftList.subList(pftMap.get("firtsize"), pftList.size()-1));
        }catch(Exception e) {
            //logger.error("", e);
        }
        Assert.assertNotNull(pftListMap);
        
    }*/
    
}

