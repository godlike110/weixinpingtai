package cn.focus.dc.focuswap.service;

import static cn.focus.dc.focuswap.config.AppConstants.DEFUALT_ANONYMOUS_USER;
import static cn.focus.dc.focuswap.config.AppConstants.DEFUALT_CITY;
import static cn.focus.dc.focuswap.config.AppConstants.QA_PUBLIC_UID;
import static cn.focus.dc.focuswap.config.AppConstants.QA_PUBLIC_USER_NAME;

import cn.focus.dc.building.model.ProjInfo;
import cn.focus.dc.commons.model.UserBasic;
import cn.focus.dc.commons.service.CommonsService;
import cn.focus.dc.focuswap.config.QuestionStatusConstants;
import cn.focus.dc.focuswap.dao.QuestionDAO;
import cn.focus.dc.focuswap.dao.UserDetailDAO;
import cn.focus.dc.focuswap.model.BuildingInfo;
import cn.focus.dc.focuswap.model.CMSManager;
import cn.focus.dc.focuswap.model.DictCity;
import cn.focus.dc.focuswap.model.QuestionInfo;
import cn.focus.dc.focuswap.model.QuestionInfoExt;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author rogantian
 */
@Service
public class QuestionService {

    private static Log logger = LogFactory.getLog(QuestionService.class);

    @Autowired
    private QuestionDAO questionDao;

    @Autowired
    private CMSManagerService cmsManagerService;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserDetailDAO userDetailDao;
    
    @Autowired
    private BuildingService buildingService;
    
    @Autowired
    private CommonsService commonService;
    
    public static final String HAS_NEXT = "hasNext";
    
    public static final String QUESTION_LIST = "data";
    
    private static final Pattern HEAD_PIC_CHECK_PATTERN = Pattern.compile("^http://.*$", Pattern.CASE_INSENSITIVE);
    
    private static final String DEFAULT_USER_NAME = "搜狐焦点网友";
    
    private static final String DEFAULT_EDITOR_NAME = "搜狐焦点编辑";
    
    private static final String DEFAULT_INIT_ANSWER = "等待回复";
    
    private static final Pattern USER_NAME_HIDDEN_PATTERN = Pattern.compile("@.*$");
    
    public static enum QA_SHOW_STATUS {
        ALL, //全部 
        YIHUIDA,  //已回答
        WEIHUIDA,  //未回答
        ANSWERED  //已回答，但是不包括已拒绝的部分
    }

    
    /**
     * 根据id查询问题的详细内容
     * 
     * @param id
     * @return
     */
    public QuestionInfo findById(int id) {
        return questionDao.findById(id);
    }

    public List<QuestionInfo> getQuestionInfoList(int groupId, int pageNo, int pageSize) {
        return questionDao.getList(groupId, pageNo, pageSize);
    }
    
    /**
     * 使用proj和cityId来查询是因为quesion表有这两个字段的索引
     * @param cityId
     * @param projId
     * @param uid
     * @return
     */
    public List<QuestionInfo> getBuildingQuestion(int cityId, int projId, Integer uid) {
        if (projId <= 0 || cityId <= 0) {
            return Collections.emptyList();
        }
        
        return questionDao.getQuestionsByCityIdAndProjId(cityId, projId, uid, 0, 4);
    }
    
    /**
     * 新版获取问答列表，用于楼盘下边的咨询列表显示，规则：包括所有已回答的和当前登录用户未回答的问题（都不包括被拒绝的），按时间倒序排列
     * @param groupId
     * @param pageNo 从1开始计数
     * @param pageSize
     * @param dec 是否装饰QuestionInfo
     * @return
     */
    public Map<String, Object> getQuestionInfoListByCreateTime(BuildingInfo build, DictCity city, Integer uid, int pageNo, int pageSize) {
        if (pageNo < 1) {
            pageNo = 1;
        }
        
        if (pageSize < 1) {
            pageSize = 10;
        }
        
        List<QuestionInfo> list = null;
        boolean hasNext = false;
        
        list = questionDao.getQuestionsByCityIdAndProjId(build.getCityId(), build.getProjId(), uid, (pageNo-1) * pageSize, pageSize + 1);
        
        if (null != list && list.size() > 0) {
            int loopSize = 0;
            Map<Integer, DictCity> cachedCity = new HashMap<Integer, DictCity>();
            if (null != city) {
                cachedCity.put(city.getCityId(), city);
            }
            Iterator<QuestionInfo> it = list.iterator();
            while (it.hasNext()) {
                QuestionInfo q = it.next();
                loopSize ++;
                if (loopSize > pageSize) {
                    hasNext = true;
                    it.remove();
                    break;
                }
                
                Integer editorId = q.getEditorId();
                StringBuilder editorDesc = new StringBuilder();
                if (null != editorId) {
                    CMSManager cmsManager = cmsManagerService.getCMSManager(editorId);
                    if (null != cmsManager) {
                        DictCity editorCity = cachedCity.get(cmsManager.getCityId());
                        if (null == editorCity) {
                            editorCity = cityService.getCity(q.getCityId());
                        }
                        if (null != editorCity) {
                            editorDesc.append(editorCity.getCityName()).append(" ");
                            cachedCity.put(cmsManager.getCityId(), editorCity);
                        } else {
                            editorDesc.append(DEFUALT_CITY).append("站 ");
                        }
                        editorDesc.append(cmsManager.getPosition()).append(" ").append(cmsManager.getName());
                    }
                }
                q.setEditorDesc(editorDesc.toString());
            }
        } else {
            list = Collections.emptyList();
        }
        
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put(HAS_NEXT, hasNext);
        ret.put(QUESTION_LIST, list);
        return ret;
    }

    public void decorate(QuestionInfo questionInfo, ProjInfo projInfo) throws ParseException {
        // Answer
        short questionStatus = questionInfo.getStatus();
        if ((questionStatus != QuestionStatusConstants.QA_Q_UN_PASS_AUDIT 
                && questionStatus != QuestionStatusConstants.QA_PASS_AUDIT)
                || StringUtils.isBlank(questionInfo.getAnswer())) {
//            questionInfo.setAnswer(" 小编正在查询信息，请稍候 ");
            questionInfo.setAnswer(DEFAULT_INIT_ANSWER);
        }

        Integer cityId = questionInfo.getCityId();

        // build对象
        String buildName = null;
        int groupId = -1;
        try {
            if (null != projInfo) {
                buildName = projInfo.getProjName();
                groupId = projInfo.getGroupId();
                StringBuilder sb = new StringBuilder();
                sb.append("楼盘：").append(buildName);
                questionInfo.setBuildName(sb.toString());
                questionInfo.setGroupId(groupId);
            }

        } catch (Exception e) {
            logger.error("", e);
        }

        // cityName
        if (null != cityId) {
            DictCity buildCity = cityService.getCity(cityId);
            if (null != buildCity) {
                String cityName = buildCity.getCityName();
                if (null != cityName) {
                    questionInfo.setCityName(cityName);
                } else {
                    questionInfo.setCityName(DEFUALT_CITY);
                }
            }
        }

        // Editor
        StringBuilder editorDesc = new StringBuilder();
        Integer editorId = questionInfo.getEditorId();
        if (editorId != null) {
            
            CMSManager cmsManager = cmsManagerService.getCMSManager(editorId);
            if (cmsManager != null) {
                DictCity editorCity = cityService.getCity(cmsManager.getCityId());
                if (editorCity != null) {
                    editorDesc.append(editorCity.getCityName()).append(" ");
                } else {
                    editorDesc.append(DEFUALT_CITY).append("站 ");
                }
                editorDesc.append(cmsManager.getPosition()).append(" ").append(cmsManager.getName());
            }
        }
        questionInfo.setEditorDesc(editorDesc.toString());

        // User
        String userName = StringUtils.EMPTY;
        Integer uid = questionInfo.getUid();
        boolean isAnonymous = questionInfo.getIsAnonymous() == 1;

        if (null == uid || uid == QA_PUBLIC_UID) {
            userName = QA_PUBLIC_USER_NAME;
        } else if (!isAnonymous) {
//            UserDetail userDetail = userDetailDao.get(uid, uid);
            UserBasic qUser = commonService.getUserBasicByUid(uid);
            if (qUser != null) {
                userName = qUser.getNickName();
            }
        } else {
            userName = DEFUALT_ANONYMOUS_USER;
        }
        questionInfo.setUserName(userName);
        // SimpleDateFormat sf = new SimpleDateFormat("yyyy年M月d日");
        // String daCR = sf.format(questionInfo.getCreateTime());
        // questionInfo.setCreateTime(sf.parse(daCR));
        // String daUP = sf.format(questionInfo.getUpdateTime());
        // questionInfo.setUpdateTime(sf.parse(daUP));
    }
    
    /**
     * 默认根据回答时间倒序按照各种规则分页返回问答列表（用于搜狐新闻客户端的咨询列表展示以及“我的咨询”列表展示
     * @param cityId
     * @param pageNo 起始页为1
     * @param pageSize 默认为10
     * @param uid 如果uid不为空，则表示只返回和该用户相关的问答
     * @param qaStatus 问答的状态，默认为AppConstatns.QAStatus.QA_ANSWERED 表示已回答
     * @param userInfo 如果userInfo不为空，则表示只返回和该用户相关的回答
     * @param createTimeSort 如果为true则按createTime倒序排序，否则为默认的排序方式（按回答时间倒序）
     * @return 返回的之中包含两个key，“data”表示问答列表数据，“hasNext”表示是否还有后续数据
     */
    public Map<String, Object> getQuestionListByOption(Integer cityId, int pageNo, int pageSize, QA_SHOW_STATUS showStatus, UserBasic userInfo, boolean createTimeSort) {
        if (null == cityId && userInfo == null) { //我的“咨询列表”时cityId为null，“搜狐新闻客户端合作”是userInfo为null，二者不能同时为null
            return null;
        }
        
        if (pageNo < 1) {
            pageNo = 1;
        }
        
        if (pageSize < 1) {
            pageSize = 5;
        }
        
        List<QuestionInfoExt> list = null;
        boolean hasNext = false;
        
        if (null == showStatus) {
            showStatus = QA_SHOW_STATUS.ALL;
        }
        
        Integer uid = null;
        if (null != userInfo && userInfo.getUid() > 0) {
            uid = userInfo.getUid();
        }
        
        list = questionDao.getListExt(cityId, (pageNo-1) * pageSize, pageSize + 1, showStatus.ordinal(), uid, createTimeSort);
        if (null != list && list.size() > 0) {
            if (list.size() > pageSize) {
                hasNext = true;
                list.remove(list.size() - 1);
            }
            
            Set<Integer> editorIds = new HashSet<Integer>();
            //Map<Integer, Set<Integer>> shardMap = new HashMap<Integer, Set<Integer>>();
            StringBuilder groupIdsBuilder = new StringBuilder();
            Set<Integer> uidSet = new HashSet<Integer>();
            
            for (QuestionInfoExt question : list) {
                Integer editorId = question.getEditorId();
                if (null != editorId && editorId > 0) {
                    editorIds.add(question.getEditorId());
                }
                
                groupIdsBuilder.append(question.getGroupId()).append(",");
                
                /**
                 * 匿名提问不显示
                 */
                if (1 == question.getIsAnonymous()) {
                    continue;
                }
                
                uidSet.add(question.getUid());
                
                /*Integer uid = question.getUid();
                Integer shard = uid % 100 + 1;
                Set<Integer> uidSet = shardMap.get(shard);
                if (null == uidSet) {
                    uidSet = new HashSet<Integer>();
                }
                uidSet.add(uid);
                shardMap.put(shard, uidSet);*/
                
            }
            
            Map<Integer, CMSManager> editors = new HashMap<Integer, CMSManager>();
            if (editorIds.size() > 0) {
                List<CMSManager> editorList = cmsManagerService.getCMSManagerList(editorIds);
                if (null != editorList) {
                    for (CMSManager manager : editorList) {
                        editors.put(manager.getId(), manager);
                    }
                }
            }
            
            Map<Integer, cn.focus.dc.commons.model.UserBasic> users = new HashMap<Integer, cn.focus.dc.commons.model.UserBasic>();
            if (uidSet.size() == 1 && uidSet.contains(uid)) {
                users.put(uid, userInfo);
            } else {
                users = commonService.getUserBasicByUids(uidSet);
            }
            /*Iterator<Entry<Integer, Set<Integer>>> it = shardMap.entrySet().iterator();
            while (it.hasNext()) {
                Entry<Integer, Set<Integer>> entry = it.next();
                List<UserDetail> userList =userDetailDao.getList(entry.getValue(), entry.getKey()-1);
                if (null != userList) {
                    for (UserDetail user : userList) {
                        users.put(user.getUid(), user);
                    }
                }
            }*/
            
            
            Map<Integer, String> buildNames = new HashMap<Integer, String>();
            if (groupIdsBuilder.length() > 0) {
                List<BuildingInfo> buildInfoList = buildingService.getBuildingInfoList(groupIdsBuilder.deleteCharAt(groupIdsBuilder.length()-1).toString());
                if (null != buildInfoList) {
                    for (BuildingInfo build : buildInfoList) {
                        buildNames.put(build.getGroupId(), build.getProjName());
                    }
                }
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
            
            for (QuestionInfoExt question : list) {
                CMSManager editor = editors.get(question.getEditorId());
                if (null != editor) {
                    question.setEditorName(editor.getName());
                    String headPic = editor.getPicUrl();
                    if (null != headPic && HEAD_PIC_CHECK_PATTERN.matcher(headPic).matches()) {
                        question.setEditorHeadPic(headPic);
                    } else {
                        question.setEditorHeadPic(StringUtils.EMPTY);
                    }
                } else {
                    question.setEditorName(DEFAULT_EDITOR_NAME);
                    question.setEditorHeadPic(StringUtils.EMPTY);
                }
                
                cn.focus.dc.commons.model.UserBasic user = users.get(question.getUid());
                
                String userName = null;
                String headImg = null;
                if (null != user) {
                    userName = user.getNickName();
                    userName = USER_NAME_HIDDEN_PATTERN.matcher(userName).replaceFirst(StringUtils.EMPTY);
                    headImg = user.getHeadImg();
                }
                
                if (StringUtils.isNotBlank(userName)) {
                    question.setUserName(userName);
                } else {
                    question.setUserName(DEFAULT_USER_NAME);
                }
                
                if (StringUtils.isNotBlank(headImg) && HEAD_PIC_CHECK_PATTERN.matcher(headImg).matches()) {
                    question.setUserHeadPic(headImg);
                } else {
                    question.setUserHeadPic(StringUtils.EMPTY);
                }
                
               /* if (null != user) {
                    userName = user.getNickName();
                    userName = USER_NAME_HIDDEN_PATTERN.matcher(userName).replaceFirst(StringUtils.EMPTY);
                    question.setUserName(userName);
                    headImg = user.getHeadImg();
                    if (null != headImg && HEAD_PIC_CHECK_PATTERN.matcher(headImg).matches()){
                        question.setUserHeadPic(headImg);
                    } else {
                        question.setUserHeadPic(StringUtils.EMPTY);
                    }
                } else {
                    question.setUserName(DEFAULT_USER_NAME);
                    question.setUserHeadPic(StringUtils.EMPTY);
                }*/
                
                question.setBuildName(buildNames.get(question.getGroupId()));
                Date answerTime = question.getAnswerTime();
                if (null != answerTime) {
                    question.setAnswerTimeStr(sdf.format(answerTime));
                }
                
                Date createTime = question.getCreateTime();
                if (null != createTime) {
                    question.setQuestionTimeStr(sdf.format(question.getCreateTime()));
                }
            }
            
            
        }
        
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put(HAS_NEXT, hasNext);
        ret.put(QUESTION_LIST, list);
        
        return ret;
    }
    
    /**
     * 新版获取问答相关信息的接口，目前只获取问答本身以及提问者的信息，如果还有其它需求则可以通过给函数添加一个控制参数来实现
     * @param id
     * @return
     * @author rogantian
     */
    public QuestionInfoExt getQuestionExt(int id) {
        if (id < 1) {
            return null;
        }
        QuestionInfoExt question = questionDao.findByIdNew(id);
        Integer uid = question.getUid();
        UserBasic user = null;
        if (null != uid) {
            user = commonService.getUserBasicByUid(uid);
        }
        
        if (null != user) {
            question.setUserName(user.getNickName());
            String headImg = user.getHeadImg();
            if (null != headImg && HEAD_PIC_CHECK_PATTERN.matcher(headImg).matches()){
                question.setUserHeadPic(headImg);
            } else {
                question.setUserHeadPic(StringUtils.EMPTY);
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日");
        question.setQuestionTimeStr(sdf.format(question.getCreateTime()));
        return question;
    }
    
    /**
     * 查看某用户是否在某楼盘咨询过，这里以city和projId来查询是因为数据库有这两个字段的索引
     * @param cityId
     * @param projId
     * @param uid
     * @return
     */
    public boolean didUserAsked(int cityId, int projId, Integer uid) {
        if (null == uid) {
            return false;
        }
        
        Integer askedCount = questionDao.userAskedCount(cityId, projId, uid);
        
        return (null != askedCount && askedCount > 0);
    }
}
