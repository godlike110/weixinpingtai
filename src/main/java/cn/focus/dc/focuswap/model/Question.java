package cn.focus.dc.focuswap.model;

import cn.focus.dc.commons.model.BaseObject;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class Question extends BaseObject {

    private static final long serialVersionUID = 3342034065323356935L;

    @JSONField(name = "questionId")
    private Long id;

    @JSONField(name = "uid")
    private Integer uid;

    private String question;

    private Integer groupId;
    
    private String answer;

    private Integer cityId;

    private Integer buildId;

    private Short status;

    private Integer editorId;

    private Short isAnswered;

    private Short isAnonymous;

    private Integer collectionsCount;

    private Integer usefulCount;

    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;
    private String createTimeStr;

    @JSONField(format = "yyyy-MM-dd")
    private Date updateTime;
    private String updateTimeStr;

    public Question() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getBuildId() {
        return buildId;
    }

    public void setBuildId(Integer buildId) {
        this.buildId = buildId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getEditorId() {
        return editorId;
    }

    public void setEditorId(Integer editorId) {
        this.editorId = editorId;
    }

    public Short getIsAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(Short isAnswered) {
        this.isAnswered = isAnswered;
    }

    public Short getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Short isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public Integer getCollectionsCount() {
        return collectionsCount;
    }

    public void setCollectionsCount(Integer collectionsCount) {
        this.collectionsCount = collectionsCount;
    }

    public Integer getUsefulCount() {
        return usefulCount;
    }

    public void setUsefulCount(Integer usefulCount) {
        this.usefulCount = usefulCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

        
}
