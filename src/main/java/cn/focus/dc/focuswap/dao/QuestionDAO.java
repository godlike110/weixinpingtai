package cn.focus.dc.focuswap.dao;

import cn.focus.dc.focuswap.config.AppConstants;
import cn.focus.dc.focuswap.config.QuestionStatusConstants;
import cn.focus.dc.focuswap.model.Question;
import cn.focus.dc.focuswap.model.QuestionInfo;
import cn.focus.dc.focuswap.model.QuestionInfoExt;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "xinfang")
public interface QuestionDAO {

    @SQL("SELECT id, uid, question, answer, city_id, build_id,group_id, editor_id, status, is_answered, "
            + "is_anonymous, useful_count,collections_count, create_time, update_time "
            + " FROM question WHERE id = :1")
    QuestionInfo findById(long questionId);

    /**
     * Description:根据城市id和楼盘id获取热点问题列表，用于楼盘首页的显示（规则是：所有已回答的问题+ 未处理中我提问的问题）
     * 
     * @author qiaowang
     * @param cityId
     * @param projId
     * @return List<Question>
     */
    @SQL("SELECT id, uid, question, answer, city_id, build_id,group_id, editor_id, status, is_answered,"
            + " is_anonymous, useful_count, collections_count, create_time, update_time "
            + "FROM question WHERE city_id = :1 AND build_id = :2 AND ( status =" + AppConstants.QAStatus.QA_ANSWERED
            + "#if(:3 == null){ )}" 
            + "#if(:3 != null){ OR ( status = " +  AppConstants.QAStatus.QA_INIT + " AND + uid = :3))}"  
            + " order by create_time desc limit :4, :5")
    List<QuestionInfo> getQuestionsByCityIdAndProjId(int cityId, int projId, Integer uid, int index, int size);

    /**
     * 分页根据城市id和楼盘id获取热点问题列表 status>=3表示问题已审查通过，回答包括未审核，审核通过和审核未通过
     * 
     * @param buildId
     * @param cityId
     * @param index
     * @param size
     * @return
     */
    @SQL("SELECT id, uid, question, answer, city_id, build_id,group_id, editor_id, status, is_answered,"
            + " is_anonymous, useful_count, collections_count, create_time, update_time"
            + " FROM question WHERE group_id=:1 and status>="
            + QuestionStatusConstants.QA_Q_PASS_NO_ANSWER + " order by useful_count desc LIMIT :2,:3")
    List<QuestionInfo> getList(int groupId, int index, int size);
    
    /**
     * 根据cityId，分页获取最新回答的问题，按回答时间倒序
     * @param index
     * @param size
     * @return
     */
    @SQL("SELECT id, uid, question, answer, city_id, build_id,group_id, editor_id, status, is_answered,"
            + " is_anonymous, useful_count, collections_count, create_time, update_time, answer_time"
            + " FROM question where "
            + "#if (:4 == 0){ status in (" + AppConstants.QAStatus.QA_INIT + "," + AppConstants.QAStatus.QA_REJECTED + "," + AppConstants.QAStatus.QA_ANSWERED + ")}" //全部：包括未回答、已拒绝、已回答三种
            + "#if (:4 == 1){ status in (" + AppConstants.QAStatus.QA_REJECTED + "," + AppConstants.QAStatus.QA_ANSWERED + ")}"//已回答： 包括已拒绝、已回答二种
            + "#if (:4 == 2){ status = " + AppConstants.QAStatus.QA_INIT + "}" //未回答：只有未回答一种
            + "#if (:4 == 3){ status = " + AppConstants.QAStatus.QA_ANSWERED + "}" //已回答：只包括已回答部分，不包括已拒绝
            + "#if(:1 != null){ and city_id = :1}"
            + "#if(:5 != null){ and uid = :5} order by"
            + "#if(! :6){ answer_time desc}"
            + "#if (:6) { create_time desc}  LIMIT :2,:3")
    List<QuestionInfoExt> getListExt(Integer cityId, int index, int size, int showStatus, Integer uid, boolean createTimeSort);
    
    /**
     * 同findById(long questionId)方法一样，不同的是返回类型，使用在不同的地方
     * @param questionId
     * @return
     */
    @SQL("SELECT id, uid, question, answer, city_id, build_id,group_id, editor_id, status, is_answered, "
            + "is_anonymous, useful_count,collections_count, create_time, update_time "
            + " FROM question WHERE id = :1")
    QuestionInfoExt findByIdNew(long questionId);
    
    /**
     * 查询某用户在某楼盘提问的次数，楼盘详情页处使用
     * @param cityId
     * @param projId
     * @param uid
     * @return
     */
    @SQL("SELECT COUNT(id) FROM question where city_id = :1 AND build_id = :2 AND uid = :3")
    Integer userAskedCount(int cityId, int projId, Integer uid);
    
    
}
